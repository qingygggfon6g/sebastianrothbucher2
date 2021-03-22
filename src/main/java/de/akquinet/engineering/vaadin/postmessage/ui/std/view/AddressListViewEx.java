package de.akquinet.engineering.vaadin.postmessage.ui.std.view;

import de.akquinet.engineering.vaadin.postmessage.model.Address;

public interface AddressListViewEx extends AddressListView {
	
	public void postChosenAddress(Address address);
	
	public void setTypeahead(String typeahead);
	
	public static interface Observer extends AddressListView.Observer {
		
		public void onConfirm();
		
		public void onReceiveSearchDefault(String searchDefault);
		
	}

}
