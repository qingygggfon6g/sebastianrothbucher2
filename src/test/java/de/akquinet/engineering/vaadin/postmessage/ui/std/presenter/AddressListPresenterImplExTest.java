package de.akquinet.engineering.vaadin.postmessage.ui.std.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Property;

import de.akquinet.engineering.vaadin.postmessage.model.Address;
import de.akquinet.engineering.vaadin.postmessage.model.Transfer;
import de.akquinet.engineering.vaadin.postmessage.service.AddressService;
import de.akquinet.engineering.vaadin.postmessage.service.TransferServiceEx;
import de.akquinet.engineering.vaadin.postmessage.ui.std.view.AddressListViewEx;
import de.akquinet.engineering.vaadin.postmessage.ui.view.View;

public class AddressListPresenterImplExTest {

	AddressListViewEx view;
	PresenterFactory presenterFactory;
	AddressService addressService;
	TransferServiceEx transferServiceEx;

	Map<String, Object> context = new HashMap<String, Object>();

	AddressListPresenterImplEx presenter;

	@Before
	public void setUp() {
		view = mock(AddressListViewEx.class);
		presenterFactory = mock(PresenterFactory.class);
		addressService = mock(AddressService.class);
		transferServiceEx = mock(TransferServiceEx.class);
		presenter = new AddressListPresenterImplEx(context, view,
				presenterFactory, addressService, transferServiceEx, null,
				"http://someserver", null);
	}

	@Test
	public void testStartPresenting() {
		presenter.startPresenting();
		verify(view).setCounterpart("http://someserver");
		verify(view).setUserName(null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testStartPresentingWithFragment() {
		presenter = new AddressListPresenterImplEx(context, view,
				presenterFactory, addressService, transferServiceEx, null,
				"http://someserver", "bonitaProcess=proc123");
		Transfer transfer = new Transfer();
		transfer.setProcid("proc123");
		transfer.setAttribute("userid");
		transfer.setValue("jerry");
		when(
				transferServiceEx.getTransferForProcessAndAttribute(
						eq("proc123"), eq("userid"), anyMap())).thenReturn(
				transfer);
		// prep done, now test
		presenter.startPresenting();
		verify(view).setCounterpart("http://someserver");
		verify(view).setUserName("jerry");
	}

	@Test
	public void testStartPresentingWithWrongFragment() {
		presenter = new AddressListPresenterImplEx(context, view,
				presenterFactory, addressService, transferServiceEx, null,
				"http://someserver", "blafragment");
		// prep done, now test
		presenter.startPresenting();
		verify(view).setCounterpart("http://someserver");
		verify(view).setUserName(null);
	}

	@Test
	public void testOnAddressChosen() {
		presenter.onAddressChosen();
		verify(view, never()).openSubView(any(View.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testOnAddAddress() {
		presenter.onAddAddress();
	}

	@Test
	public void testOnConfirm() {
		@SuppressWarnings("unchecked")
		Property<Address> prop = mock(Property.class);
		when(view.getAddressSelection()).thenReturn(prop);
		Address address = new Address();
		when(prop.getValue()).thenReturn(address);
		// prep done, now test
		presenter.onConfirm();
		verify(view).postChosenAddress(address);
	}

	@Test
	public void testOnConfirmNull() {
		@SuppressWarnings("unchecked")
		Property<Address> prop = mock(Property.class);
		when(view.getAddressSelection()).thenReturn(prop);
		when(prop.getValue()).thenReturn(null);
		// prep done, now test
		presenter.onConfirm();
		verify(view, never()).postChosenAddress(any(Address.class));
	}

	@Test
	public void testOnReceiveSearchDefault() {
		presenter.onReceiveSearchDefault("test");
		verify(view).setTypeahead("test");
	}

}
