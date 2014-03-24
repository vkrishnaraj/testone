package com.bagnet.nettracer.tracing.db.bagbuzz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import java.util.Date;

import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Table(name="bagbuzz")
public class BagBuzz {
	@Id
	@GeneratedValue
	public long getBagbuzz_id() {
		return bagbuzz_id;
	}
	public void setBagbuzz_id(long bagbuzzId) {
		bagbuzz_id = bagbuzzId;
	}
	@Column(length=30)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_timestamp() {
		return created_timestamp;
	}
	public void setCreated_timestamp(Date createdTimestamp) {
		created_timestamp = createdTimestamp;
	}

	@ManyToOne
	@JoinColumn(name = "category_ID", nullable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	long bagbuzz_id;
	String description;
	String data;
	Status status;
	Date created_timestamp;
	Category category;
}
