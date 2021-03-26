package de.akquinet.engineering.vaadin.postmessage.ui.std.presenter;

import java.util.HashMap;
import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.model.Address;
import de.akquinet.engineering.vaadin.postmessage.model.Transfer;
import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.service.TransferServiceEx;
import de.akquinet.engineering.vaadin.postmessage.ui.presenter.SubviewCapablePresenter;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.AddressListViewEx;

public class AddressListPresenterImplEx extends AddressListPresenterImpl
		implements AddressListPresenterEx, AddressListViewEx.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String BONITA_PROCESS = "bonitaProcess=";

	public static final String CONTEXT_USER = "context.user";

	private Map<String, Object> context;
	private AddressListViewEx view;
	private TransferServiceEx transferService;

	private String counterpart;
	private String fragment;

	public AddressListPresenterImplEx(Map<String, Object> context,
			AddressListViewEx view, PresenterFactory presenterFactory,
			AddressService service, TransferServiceEx transferService,
			SubviewCapablePresenter subviewCapablePresenter,
			String counterpart, String fragment) {
		super(context, view, presenterFactory, service, subviewCapablePresenter);
		this.context = context;
		this.view = view;
		this.transferService = transferService;
		this.counterpart = counterpart;
		this.fragment = fragment;
	}

	@Override
	public void startPresenting() {
		// X-Signon using the process given in the URI Fragment
		if (fragment != null) {
			if (fragment.contains(BONITA_PROCESS)) {
				String procid = fragment.substring(BONITA_PROCESS.length());
				Transfer transfer = transferService
						.getTransferForProcessAndAttribute(procid, "userid",
								new HashMap<String, Object>(context));
				if (transfer != null && transfer.getValue() != null) {
					context.put(CONTEXT_USER, transfer.getValue());
				}
			}
		}
		// ok, we can go
		super.startPresenting();
		// ensure the view knows where to turn to
		view.setCounterpart(counterpart);
		// message when there is no fragment
		if (context.containsKey(CONTEXT_USER)) {
			view.setUserName(context.get(CONTEXT_USER).toString());
		} else {
			view.setUserName(null);
			view.showInfoMessage("Only public info available");
		}
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
