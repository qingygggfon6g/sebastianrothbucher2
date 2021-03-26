package de.akquinet.engineering.vaadin.postmessage.service;

import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.model.Transfer;

public interface TransferServiceEx extends TransferService {

	public Transfer getTransferForProcessAndAttribute(String procid,
			String attribute, Map<String, Object> context);

}
