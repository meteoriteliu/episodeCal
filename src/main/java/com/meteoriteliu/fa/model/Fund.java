package com.meteoriteliu.fa.model;

import com.meteoriteliu.fa.config.GsonExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Fund {
	@Id @GsonExclude
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable=false)
	int id;
	
	@Column
	String name;
	
	@Column
	@GsonExclude
	String type;
	
	@Column
	String code;

	@Column
	@CreatedDate
	@GsonExclude
	Date createDate;

	@Column
	@GsonExclude
	Date lastSyncDate;

	public Date getLastSyncDate() {
		return lastSyncDate;
	}

	public void setLastSyncDate(Date lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
