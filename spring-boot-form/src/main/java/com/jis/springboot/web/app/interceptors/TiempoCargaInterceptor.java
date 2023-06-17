package com.jis.springboot.web.app.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("tiempoCargaInterceptor")
public class TiempoCargaInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(TiempoCargaInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equalsIgnoreCase("post")) return true;
		
		LOGGER.info("Entrando a método preHandle...");
		LOGGER.info("Interceptando: "+handler);

		long tiempoInicio = System.currentTimeMillis();
		Thread.sleep(new Random().nextLong(100));
		request.setAttribute("tiempoInicio", tiempoInicio);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if(request.getMethod().equalsIgnoreCase("post")) return;

		long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		long tiempoFinal = System.currentTimeMillis();
		long tiempoDemora = tiempoFinal - tiempoInicio;
		//Se utiliza ya que si no hemos especificado una ruta va a intercetar todo incluse los recursos estatico como css y en ese caso modelAndView es null
		if(modelAndView != null) modelAndView.addObject("tiempoDemora", tiempoDemora);
		
		
		LOGGER.info("Saliendo método postHandle...");
		LOGGER.info("Tiempo demora: "+tiempoDemora);
	}

}
