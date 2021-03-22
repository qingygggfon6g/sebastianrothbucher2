package de.akquinet.engineering.vaadin.postmessage.ui.std.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

import de.akquinet.engineering.vaadin.postmessage.TechnicalException;
import de.akquinet.engineering.vaadin.postmessage.model.Address;

public class AddressListViewImplEx extends AddressListViewImpl implements AddressListViewEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Button confirm = new Button("Confirm");

	public AddressListViewImplEx() {
		super();
	}

	private AddressListViewEx.Observer observer;

	@Override
	public void initializeUi() {
		super.initializeUi();
		addAddress.setVisible(false);
		confirm.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				observer.onConfirm();
			}
		});
		((ComponentContainer) getCompositionRoot()).addComponent(confirm);
		initTypeaheadCallback();
	}

	// --- communicate from Bonita to Vaadin (typeahead) ---

	private void initTypeaheadCallback() {
		String callbackName = getClass().getName() + ".typeaheadCallback";
		JavaScript.getCurrent().addFunction(callbackName, new JavaScriptFunction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void call(JSONArray arguments) throws JSONException {
				// we expect one parameter of type String
				if (arguments.length() >= 1) {
					observer.onReceiveSearchDefault(arguments.get(0).toString());
				}
			}
		});
		// TODO: register and do (key instead of origin)
	}

	@Override
	public void setTypeahead(String typeahead) {
		searchField.setValue(typeahead);
	}

	// --- communicate from Vaadin to Bonita (chosen Address) ---

	@Override
	public void postChosenAddress(Address address) {
		try {
			JSONObject output = new JSONObject();
			output.put("vaadinBonitaKey", "secret");
			output.put("id", address.getId());
			output.put("contactFirstName", address.getVorname());
			output.put("contactLastName", address.getNachname());
			// we use a shared secret instead of origin
			JavaScript.getCurrent().execute("parent.postMessage(JSON.stringify(" + output.toString() + "), '*');");
		} catch (JSONException e) {
			throw new TechnicalException("Error during postMessage", e);
		}
	}

	@Override
	public void setObserver(AddressListView.Observer observer) {
		if (!(observer instanceof AddressListViewEx.Observer)) {
			throw new IllegalArgumentException("Need " + AddressListViewEx.Observer.class.getName());
		}
		super.setObserver(observer);
		this.observer = (AddressListViewEx.Observer) observer;
	}

}
