package com.jis.springboot.web.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jis.springboot.web.app.models.entity.Cliente;
import com.jis.springboot.web.app.service.IClienteService;
import com.jis.springboot.web.app.service.IUploadFileService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	private Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping({ "", "/", "/listar" })
	public String listar(Model model, Authentication authentication, Locale locale) {

		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		
		if(authentication != null) {
			log.info(authentication.getName() + " dentro del Cliente controller");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			log.info("Accediendo al usuario logeado con el método estático : "+auth.getName());
		}
		
		if(hasRole("ROLE_ADMIN")) {
			log.info("El usuario logeado es de tipo administrador");
		}else {
			log.info("El usuario logeado no es de tipo administrador");
		}

		return "listar";
	}
	
	
	@GetMapping({ "/listar-rest" })
	@ResponseBody
	public List<Cliente> listarClientes(Model model, Authentication authentication, Locale locale) {
		return clienteService.findAll();
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable Long id, Map<String, Object> model, RedirectAttributes flash) {
		if (id == null || id < 0) {
			flash.addFlashAttribute("danger", "Id de cliente no encontrado!");
			return "redirect:/listar";

		}

		Cliente cliente = clienteService.findById(id);
		if (cliente == null) {
			flash.addFlashAttribute("danger", "Id de cliente no encontrado!");
			return "redirect:/listar";

		}

		model.put("cliente", cliente);
		return "form";
	}

	@GetMapping(value = "/uploads/{fileName:.+}")
	public ResponseEntity<Resource> verImagen(@PathVariable String fileName) {

		Resource r = null;
		try {
			r = uploadFileService.load(fileName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(r);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Cliente cliente, BindingResult result, @RequestParam("file") MultipartFile foto,
			SessionStatus sessionStatus, RedirectAttributes flash) {

		if (result.hasErrors())
			return "form";

		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getFoto() != null) {

				boolean isFotoDeleted = uploadFileService.delete(cliente.getFoto());
				flash.addFlashAttribute(isFotoDeleted ? "success" : "danger",
						isFotoDeleted ? "Se eliminó la foto" : "El cliente no existe!");

			}

			try {
				/*
				 * byte[] bytes = foto.getBytes(); Path rutaCompleta = Paths.get( rootPath +
				 * "//" +foto.getOriginalFilename()); Files.write(rutaCompleta, bytes);
				 */

				cliente.setFoto(uploadFileService.copy(foto));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String msj = cliente.getId() != null && cliente.getId() > 0 ? "Cliente editado con éxito!"
				: "Cliente agregado con éxito!";
		clienteService.save(cliente);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", msj);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if (id != null && id > 0) {
			Cliente cliente = clienteService.findById(id);
			clienteService.delete(id);

			uploadFileService.delete(cliente.getFoto());
			

		} else {
			flash.addFlashAttribute("danger", "El cliente no existe!");
			return "redirect:/listar";
		}

		flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		return "redirect:/listar";
	}

	@GetMapping("/ver/{clienteId}")
	public String verCliente(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findByIdWithFacturas(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("danger", "El cliente no existe");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		return "ver";
	}

	
	public boolean hasRole(String role) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
		} catch (Exception e) {

		}
		return false;
	}
	
}
