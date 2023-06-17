package com.jis.springboot.web.interceptor.app.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("homeInterceptor")
public class HomeInterceptor implements HandlerInterceptor {

	@Value("${configuration.hora.apertura}")
	private Integer horaApertura;
	
	@Value("${configuration.hora.cierre}")
	private Integer horaCierre;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Calendar calendar = Calendar.getInstance();
		Integer horaActual = calendar.get(Calendar.HOUR_OF_DAY);
		
		System.out.println(horaApertura);
		System.out.println(horaCierre);
		
		if(horaActual >=horaApertura && horaActual < horaCierre ) {
			request.setAttribute("bienvenido", "Bienvenido!");
			return true;
		}
		
		
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(modelAndView != null) modelAndView.addObject("bienvenido", request.getAttribute("bienvenido"));
		

	}

	
}
