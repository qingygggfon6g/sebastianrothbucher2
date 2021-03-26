package de.akquinet.engineering.vaadin.postmessage.ui.std.presenter;

import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.service.TransferServiceEx;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.Presenter;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.SubviewCapablePresenter;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.ViewFactoryEx;

public class PresenterFactoryEx extends PresenterFactory {

	private Map<String, Object> context;
	private ViewFactoryEx viewFactory;
	private AddressService addressService;
	private TransferServiceEx transferService;

	private String counterpart;
	private String fragment;

	public PresenterFactoryEx(Map<String, Object> context,
			ViewFactoryEx viewFactory, AddressService addressService,
			TransferServiceEx transferService, String counterpart, String fragment) {
		super(context, viewFactory, addressService, transferService);
		this.context = context;
		this.viewFactory = viewFactory;
		this.addressService = addressService;
		this.transferService = transferService;
		this.counterpart = counterpart;
		this.fragment = fragment;
	}

	@Override
	public AddressListPresenterEx createAddressListPresenter(
			Presenter returnPresenter,
			SubviewCapablePresenter subviewCapablePresenter) {
		return new AddressListPresenterImplEx(context,
				viewFactory.createAddressListView(), this, addressService, transferService,
				subviewCapablePresenter, counterpart, fragment);
	}

}
