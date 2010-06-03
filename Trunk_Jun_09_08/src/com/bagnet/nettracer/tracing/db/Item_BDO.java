package com.bagnet.nettracer.tracing.db;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "item_bdo")
@Proxy(lazy = false)
public class Item_BDO {

	private long id;
	private boolean canceled;
	private BDO bdo;
	private Item item;

	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	@ManyToOne(targetEntity = BDO.class)
	@JoinColumn(name = "bdo_ID", nullable = false)
	public BDO getBdo() {
		return bdo;
	}

	public void setBdo(BDO bdo) {
		this.bdo = bdo;
	}


	@ManyToOne(targetEntity = Item.class, cascade=CascadeType.ALL)
	@JoinColumn(name = "item_ID", nullable = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
