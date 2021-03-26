package de.akquinet.engineering.vaadin.postmessage.service;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import de.akquinet.engineering.vaadin.postmessage.dao.TransferDao;
import de.akquinet.engineering.vaadin.postmessage.model.Transfer;

public class TransferServicePlainEx extends TransferServicePlain implements
		TransferServiceEx {

	public TransferServicePlainEx() {
		super();
	}

	public TransferServicePlainEx(EntityManagerFactory entityManagerFactory,
			TransferDao transferDao) {
		super(entityManagerFactory, transferDao);
		this.transferDao = transferDao;
	}

	private TransferDao transferDao = null;
	TransferServiceEx backend = null;

	@Override
	protected TransferServiceEx obtainBackend() {
		if (backend == null) {
			backend = new TransferServiceImplEx(transferDao);
		}
		return backend;
	}

	@Override
	public void setTransferDao(TransferDao transferDao) {
		super.setTransferDao(transferDao);
		this.transferDao = transferDao;
	}

	@Override
	public Transfer getTransferForProcessAndAttribute(final String procid,
			final String attribute, final Map<String, Object> context) {
		return (Transfer) requireJpaTransaction(new Callback() {

			@Override
			public Transfer perform() {
				return obtainBackend().getTransferForProcessAndAttribute(
						procid, attribute, context);
			}
		}, context);
	}

}
