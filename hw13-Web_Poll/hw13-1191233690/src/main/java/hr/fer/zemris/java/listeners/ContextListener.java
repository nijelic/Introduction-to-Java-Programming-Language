package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Remembers time as attribute.
 * 
 * @author Jelić, Nikola
 */
@WebListener
public class ContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("time", Long.valueOf(System.currentTimeMillis()).toString());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {	
	}
}
