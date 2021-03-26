package de.akquinet.engineering.vaadin.postmessage.ui.std.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import de.akquinet.engineering.vaadin.postmessage.TechnicalException;
import de.akquinet.engineering.vaadin.postmessage.model.Address;
import de.akquinet.engineering.vaadin.postmessage.model.AddressProperties;

public class AddressListViewImplEx extends AddressListViewImpl implements
		AddressListViewEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Button confirm = new Button("Confirm");
	protected Label user = new Label();

	public AddressListViewImplEx() {
		super();
	}

	private String counterpart = null;
	private AddressListViewEx.Observer observer = null;

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
		user.setVisible(false);
		((ComponentContainer) getCompositionRoot()).addComponent(user);
		initTypeaheadCallback();
	}

	// --- communicate from Bonita to Vaadin (typeahead) ---

	private void initTypeaheadCallback() {
		String callbackName = getClass().getName() + ".typeaheadCallback";
		// Three things to keep in mind:
		// 1. the target knows which type our message is: see
		// "addressTransfer" above
		// 2. end users (without any idea of inner workings) get fooled to
		// disclose stuff: see "origin" below (2nd param)
		// 3. someone (with knowledge of JS) fakes some stuff into the
		// application: there is NO WAY to prevent that - never trust the
		// client!
		JavaScript.getCurrent().addFunction(callbackName,
				new JavaScriptFunction() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void call(JSONArray arguments) throws JSONException {
						// we expect two parameters
						// e.data (name transfer obj) and e.origin (String)
						if (arguments.length() >= 2) {
							if (arguments.get(0).toString()
									.contains("nameTransfer")
									&& counterpart.equals(arguments.get(1)
											.toString())) {
								JSONObject transfer = new JSONObject(arguments
										.get(0).toString());
								observer.onReceiveSearchDefault(transfer
										.getString("nameTransfer"));
							}
						}
					}
				});
		JavaScript.getCurrent().execute(
				"window.addEventListener(\"message\", function(e){"
						+ getClass().getName()
						+ ".typeaheadCallback(e.data, e.origin);}, false);");
	}

	@Override
	public void setTypeahead(String typeahead) {
		searchField.setValue(typeahead);
		/* Reset the filter for the container. */
		((Filterable) addressTable.getContainerDataSource())
				.removeAllContainerFilters();
		((Filterable) addressTable.getContainerDataSource())
				.addContainerFilter(new AddressFilter(typeahead));
	}

	/*
	 * A custom filter for searching names and companies in the container
	 */
	private class AddressFilter implements Filter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String needle;

		public AddressFilter(String needle) {
			this.needle = needle.toLowerCase();
		}

		@Override
		public boolean passesFilter(Object itemId, Item item) {
			String haystack = (""
					+ getPropertyVal(item,
							AddressProperties.NAME.getPropertyName())
					+ "---"
					+ getPropertyVal(item,
							AddressProperties.EMAIL.getPropertyName()) + "---")
					.toLowerCase();
			return haystack.contains(needle);
		}

		private String getPropertyVal(Item item, String name) {
			Property<?> prop = item.getItemProperty(name);
			if (prop == null) {
				return null;
			}
			return String.valueOf(prop.getValue());
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}

	// --- communicate from Vaadin to Bonita (chosen Address) ---

	@Override
	public void postChosenAddress(Address address) {
		try {
			JSONObject outputJson = new JSONObject();
			JSONObject addressJson = new JSONObject();
			addressJson.put("id", address.getId());
			addressJson.put("contactFirstName", address.getVorname());
			addressJson.put("contactLastName", address.getNachname());
			outputJson.put("addressTransfer", addressJson);
			// Three things to keep in mind:
			// 1. the target knows which type our message is: see
			// "addressTransfer" above
			// 2. end users (without any idea of inner workings) get fooled to
			// disclose stuff: see "origin" below (2nd param)
			// 3. someone (with knowledge of JS) fakes some stuff into the
			// application: there is NO WAY to prevent that - never trust the
			// client!
			JavaScript.getCurrent().execute(
					"parent.postMessage(JSON.stringify("
							+ outputJson.toString() + "), '" + counterpart
							+ "');");
		} catch (JSONException e) {
			throw new TechnicalException("Error during postMessage", e);
		}
	}

	@Override
	public void showInfoMessage(String message) {
		Notification.show(message, Notification.Type.HUMANIZED_MESSAGE);
	}

	@Override
	public void setUserName(String userName) {
		user.setVisible(userName != null);
		user.setValue("User: " + userName);
	}

	@Override
	public void setCounterpart(String counterpart) {
		this.counterpart = counterpart;
	}

	@Override
	public void setObserver(AddressListView.Observer observer) {
		if (!(observer instanceof AddressListViewEx.Observer)) {
			throw new IllegalArgumentException("Need "
					+ AddressListViewEx.Observer.class.getName());
		}
		super.setObserver(observer);
		this.observer = (AddressListViewEx.Observer) observer;
	}

}
