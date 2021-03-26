package de.akquinet.engineering.vaadin.postmessage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import de.akquinet.engineering.vaadinator.annotations.MapProperty;
import de.akquinet.engineering.vaadinator.annotations.ServiceBean;

@ServiceBean
@Entity
public class Transfer {
	
	@Id
	@Column(name="concat(procid, attribute)")
	private String rowid;
	
	// MapProperty is (so far) necessary to generate the Query
	
	@MapProperty
	private String procid;
	@MapProperty
	private String attribute;
	@MapProperty
	@Column(name = "val")
	private String value;

	public String getProcid() {
		return procid;
	}

	public void setProcid(String procid) {
		this.procid = procid;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
