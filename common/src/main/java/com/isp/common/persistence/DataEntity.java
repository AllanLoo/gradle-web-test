
package com.isp.common.persistence;

import java.util.Date;

import com.isp.common.utils.IdGen;




/**
 * 数据Entity类
 * @author Allan
 * @version 2015-06-22
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	protected String remarks;	// 备注
	protected String createById;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateById;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String recStatus; 	// 记录状态（0：正常；1：删除；）
	
	public DataEntity() {
		super();
		this.recStatus = REC_STATUS_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
}
