package com.bagnet.nettracer.tracing.db.taskmanager;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;

@Entity
@DiscriminatorValue("BAGBUZZ")
@Proxy(lazy = true)
public class BagBuzzTask extends GeneralTask{
	@ManyToOne(targetEntity = BagBuzz.class)
	@JoinColumn(name = "bagbuzz_id")
	public BagBuzz getBagBuzz() {
		return bagBuzz;
	}

	public void setBagBuzz(BagBuzz bagBuzz) {
		this.bagBuzz = bagBuzz;
	}

	BagBuzz bagBuzz;
	
	
}
