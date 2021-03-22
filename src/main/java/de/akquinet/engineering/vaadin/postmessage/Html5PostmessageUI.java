package de.akquinet.engineering.vaadin.postmessage;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.akquinet.engineering.vaadin.postmessage.dao.AddressDaoPlain;
import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.service.AddressServicePlain;
import de.akquinet.engineering.vaadin.postmessage.ui.std.presenter.AddressListPresenterEx;
import de.akquinet.engineering.vaadin.postmessage.ui.std.presenter.PresenterFactoryEx;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.VaadinViewFactoryEx;

/**
 * Main UI class
 */
@Title("Html5Postmessage")
public class Html5PostmessageUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {
		// TODO: X-Signon
		AddressListPresenterEx presenter = obtainPresenterFactory(request.getContextPath()).createAddressListPresenter(null, null);
		setContent((Component) presenter.getView().getComponent());
		// and go
		presenter.startPresenting();
	}

	PresenterFactoryEx presenterFactory = null;

	protected PresenterFactoryEx obtainPresenterFactory(String contextPath) {
		if (presenterFactory == null) {
			// simple, overwrite method for e.g. Spring / CDI / ...
			// Entity-Manager NUR Thread-Safe, wenn er injected wird wie hier
			AddressService addressService;
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Html5Postmessage");
			AddressDaoPlain addressDaoPlain = new AddressDaoPlain(entityManagerFactory);
			addressService = new AddressServicePlain(entityManagerFactory, addressDaoPlain);
			presenterFactory = new PresenterFactoryEx(new HashMap<String, Object>(), new VaadinViewFactoryEx(), addressService);
		}
		return presenterFactory;
	}

}