package de.akquinet.engineering.vaadin.postmessage.ui.std.presenter;

import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.Presenter;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.SubviewCapablePresenter;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.ViewFactoryEx;

public class PresenterFactoryEx extends PresenterFactory {

	private Map<String, Object> context;
	private ViewFactoryEx viewFactory;
	private AddressService addressService;

	public PresenterFactoryEx(Map<String, Object> context, ViewFactoryEx viewFactory, AddressService addressService) {
		super(context, viewFactory, addressService);
		this.context = context;
		this.viewFactory = viewFactory;
		this.addressService = addressService;
	}

	@Override
	public AddressListPresenterEx createAddressListPresenter(Presenter returnPresenter, SubviewCapablePresenter subviewCapablePresenter) {
		return new AddressListPresenterImplEx(context, viewFactory.createAddressListView(), this, addressService, subviewCapablePresenter);
	}

}
