package de.akquinet.engineering.vaadin.postmessage.service;

import java.util.List;
import java.util.Map;

import de.akquinet.engineering.vaadin.postmessage.dao.TransferDao;
import de.akquinet.engineering.vaadin.postmessage.model.Transfer;
import de.akquinet.engineering.vaadin.postmessage.model.TransferQuery;

public class TransferServiceImplEx extends TransferServiceImpl implements
		TransferServiceEx {

	public TransferServiceImplEx() {
		super();
	}

	public TransferServiceImplEx(TransferDao transferDao) {
		super(transferDao);
		this.transferDao = transferDao;
	}

	private TransferDao transferDao;

	@Override
	public void setTransferDao(TransferDao transferDao) {
		super.setTransferDao(transferDao);
		this.transferDao = transferDao;
	}

	@Override
	public Transfer getTransferForProcessAndAttribute(String procid,
			String attribute, Map<String, Object> context) {
		TransferQuery query = new TransferQuery();
		query.setProcid(procid);
		query.setAttribute(attribute);
		List<Transfer> list = transferDao.list(query, context);
		return list.size() == 1 ? list.get(0) : null;
	}

}
