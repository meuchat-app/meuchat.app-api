package com.gabrielribeiro.server.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.WebApplicationInitializer;

import com.gabrielribeiro.server.model.MeuChat;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class ApplicationInitializer implements WebApplicationInitializer{
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
//	    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//	    context.register(AccountConfig.class);
//	    context.setServletContext(container);

	     servletContext.setAttribute("meuchat", new Eco());
		
	}
	
	
	
}
