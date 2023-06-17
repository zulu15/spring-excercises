package com.jis.springboot.web.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.jis.springboot.web.app.models.entity.Factura;
import com.jis.springboot.web.app.models.entity.ItemFactura;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RGBColor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

			Factura factura = (Factura) model.get("factura");
			
			PdfPTable tabla = new PdfPTable(1);
			//tabla.addCell("Datos del cliente");
			Locale locale = localeResolver.resolveLocale(request);
			 
			MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();
			
			
			PdfPCell cell = new PdfPCell(new Phrase(messageSourceAccessor.getMessage("text.factura.pdf.cliente.header")));
			cell.setBackgroundColor(new Color(184,218,255));
			cell.setPadding(8f);
			tabla.addCell(cell);
			tabla.addCell(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());
			tabla.addCell(factura.getCliente().getEmail());
			tabla.setSpacingAfter(20);
			
			PdfPTable tablaFactura = new PdfPTable(1);

			cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.pdf.factura.header", null, locale)));
			cell.setBackgroundColor(new Color(195,230,203));
			cell.setPadding(8f);
			tablaFactura.addCell(cell);
			tablaFactura.addCell(messageSource.getMessage("text.factura.pdf.factura.folio", null, locale) + " " + factura.getId());
			tablaFactura.addCell(messageSource.getMessage("text.factura.pdf.factura.descripcion", null, locale) + " "+ factura.getDescripcion());
			tablaFactura.addCell(messageSource.getMessage("text.factura.pdf.factura.fecha", null, locale) + " "+ factura.getFecha());
			tablaFactura.setSpacingAfter(20);
			document.add(tabla);
			document.add(tablaFactura);
			
			PdfPTable tablaLineas = new PdfPTable(4);
			tablaLineas.setWidths(new float[]{3.5f,1,1,1});
			tablaLineas.addCell(messageSource.getMessage("text.factura.pdf.factura.item.producto", null, locale));
			tablaLineas.addCell(messageSource.getMessage("text.factura.pdf.factura.item.precio", null, locale));
			tablaLineas.addCell(messageSource.getMessage("text.factura.pdf.factura.item.cantidad", null, locale));
			tablaLineas.addCell(messageSource.getMessage("text.factura.pdf.factura.item.total", null, locale));	
			
			for(ItemFactura item : factura.getItems()) {
				tablaLineas.addCell(item.getProducto().getNombre());
				tablaLineas.addCell(item.getProducto().getPrecio().toString());
				tablaLineas.addCell(item.getCantidad().toString());
				tablaLineas.addCell(item.calcularImporte().toString());
			}
			
			cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.pdf.factura.total", null, locale), FontFactory.getFont("Arial", 13, Font.BOLD, RGBColor.BLACK)));
			cell.setColspan(3);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tablaLineas.addCell(cell);
			cell = new PdfPCell(new Phrase(factura.getTotal().toString(),FontFactory.getFont("Arial", 13, Font.BOLD, RGBColor.BLACK)));
			tablaLineas.addCell(cell);
			document.add(tablaLineas);
			
			
	}

	
}
