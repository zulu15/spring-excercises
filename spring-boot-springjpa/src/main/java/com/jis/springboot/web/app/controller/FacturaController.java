package com.jis.springboot.web.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jis.springboot.web.app.models.entity.Cliente;
import com.jis.springboot.web.app.models.entity.Factura;
import com.jis.springboot.web.app.models.entity.ItemFactura;
import com.jis.springboot.web.app.models.entity.Producto;
import com.jis.springboot.web.app.service.IClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FacturaController.class);
	
	@Autowired
	private IClienteService clienteService;

	
	@GetMapping("/ver/{id}")
	public String verFactura(@PathVariable Long id,
							Model model,
							RedirectAttributes flash) {
		
		Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id); //clienteService.findFacturaById(id);
		if(factura == null) {
			flash.addFlashAttribute("danger", "La factura no existe");
			return "redirect:/listar";
		}
		
		model.addAttribute("factura", factura);
		
		return "factura/ver";
	}
	
	
	@GetMapping("/form/{clienteId}")
	public String crearFactura(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findById(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("danger", "No se pudo encontrar el cliente");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);

		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos/{nombre}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String nombre) {
		return clienteService.findByName(nombre);
	}
	
	
	@PostMapping("/form")
	public String saveFactura(@Valid Factura factura, BindingResult bindingResult, 
											   Model model,	
											   @RequestParam(name="item_id[]", required = false) Long[] itemId,
											   @RequestParam(name="cantidad[]", required = false) Integer[] cantidad,
											   RedirectAttributes flash,
											   SessionStatus sessionStatus) {
		
		if(bindingResult.hasErrors()) {
			return "factura/form";
		}
		
		if(itemId == null || itemId.length == 0){
			model.addAttribute("danger", "Debe agregar items a su factura");
			return "factura/form";
		}
		
		
		
		for(int i=0; i<itemId.length;i++){
			LOGGER.info("Producto id: "+itemId[i]);
			LOGGER.info("Cantidad: "+cantidad[i]);

			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura itemFactura = new ItemFactura();
			itemFactura.setCantidad(cantidad[i]);
			itemFactura.setProducto(producto);
			factura.addItemFactura(itemFactura);
		}
		
		clienteService.saveFactura(factura);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", "Se guardo la factura correctamente");
		return "redirect:/ver/"+factura.getCliente().getId();
	}
	
	
	@GetMapping("eliminar/{id}")
	public String eliminarFactura(@PathVariable Long id, RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		if(factura != null) {
			clienteService.deleteFacturaById(id);
			flash.addFlashAttribute("success", "Se eliminó la factura correctamente");
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		
		flash.addFlashAttribute("danger", "No se encontró la factura");
		
		return "redirect:/listar";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
