package app.meuchat.server.controller;

import org.springframework.web.WebApplicationInitializer;

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
