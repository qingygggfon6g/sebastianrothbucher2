package de.akquinet.engineering.vaadin.postmessage.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import de.akquinet.engineering.vaadin.postmessage.dao.TransferDao;
import de.akquinet.engineering.vaadin.postmessage.model.Transfer;
import de.akquinet.engineering.vaadin.postmessage.model.TransferQuery;

public class TransferServiceImplExTest {

	TransferDao transferDao;

	TransferServiceImplEx service;

	@Before
	public void setUp() {
		transferDao = mock(TransferDao.class);
		service = new TransferServiceImplEx(transferDao);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetTransferForProcessAndAttribute() {
		Transfer transfer = new Transfer();
		when(transferDao.list(argThat(new BaseMatcher<TransferQuery>() {
			@Override
			public boolean matches(Object arg0) {
				return arg0 instanceof TransferQuery
						&& "proc123".equals(((TransferQuery) arg0).getProcid())
						&& "userid".equals(((TransferQuery) arg0)
								.getAttribute());
			}

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText("procid=proc123, attr=userid");
			}
		}), anyMap())).thenReturn(Collections.singletonList(transfer));
		// prep done, now test
		assertEquals(transfer, service.getTransferForProcessAndAttribute(
				"proc123", "userid", new HashMap<String, Object>()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetTransferForProcessAndAttributeNoResult() {
		when(transferDao.list(any(TransferQuery.class), anyMap())).thenReturn(
				new ArrayList<Transfer>());
		// prep done, now test
		assertNull(service.getTransferForProcessAndAttribute("proc123",
				"userid", new HashMap<String, Object>()));
	}

}
