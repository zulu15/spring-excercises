package com.jis.springboot.web.app.util.paginator;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int elementosPorPagina;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.elementosPorPagina = page.getSize();
		this.totalPaginas = page.getTotalPages();
		
		int desde,hasta;
		if(totalPaginas <= elementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
			
		}else {
			
		}
	}
	
	
	
}
