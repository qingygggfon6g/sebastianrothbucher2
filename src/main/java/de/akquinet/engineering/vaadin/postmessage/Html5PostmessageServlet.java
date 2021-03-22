package de.akquinet.engineering.vaadin.postmessage;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*")
@VaadinServletConfiguration(productionMode = false, ui = Html5PostmessageUI.class)
public class Html5PostmessageServlet extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
