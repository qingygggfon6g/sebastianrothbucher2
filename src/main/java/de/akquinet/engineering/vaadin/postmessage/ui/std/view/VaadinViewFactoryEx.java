package de.akquinet.engineering.vaadin.postmessage.ui.std.view;

public class VaadinViewFactoryEx extends VaadinViewFactory implements ViewFactoryEx {

	public VaadinViewFactoryEx() {
		super();
	}

	@Override
	public AddressListViewEx createAddressListView() {
		return new AddressListViewImplEx();
	}

}
