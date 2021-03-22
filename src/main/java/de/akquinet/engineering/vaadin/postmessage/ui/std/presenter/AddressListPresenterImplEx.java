package de.akquinet.engineering.vaadin.postmessage.ui.std.presenter;

import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.model.Address;
import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.SubviewCapablePresenter;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.AddressListViewEx;

public class AddressListPresenterImplEx extends AddressListPresenterImpl implements AddressListPresenterEx, AddressListViewEx.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AddressListViewEx view;

	public AddressListPresenterImplEx(Map<String, Object> context, AddressListViewEx view, PresenterFactory presenterFactory, AddressService service,
			SubviewCapablePresenter subviewCapablePresenter) {
		super(context, view, presenterFactory, service, subviewCapablePresenter);
		this.view = view;
	}

	@Override
	public void onAddressChosen() {
		// we don't have an action, just pull when Choose is called
	}

	@Override
	public void onAddAddress() {
		throw new UnsupportedOperationException("Adding is not possible");
	}

	@Override
	public void onConfirm() {
		Address selection = view.getAddressSelection().getValue();
		if (selection != null) {
			view.postChosenAddress(selection);
		}
	}

	@Override
	public void onReceiveSearchDefault(String searchDefault) {
		view.setTypeahead(searchDefault);
	}
}
