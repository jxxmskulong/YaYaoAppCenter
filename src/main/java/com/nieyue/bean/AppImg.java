package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用图片
 * @author yy
 *
 */
public class AppImg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 应用图片id
	 */
	private Integer appImgId;
	
	/**
	 * 应用图地址
	 */
	private String imgAddress;
	/**
	 * 排序数字
	 */
	private Integer orderNum;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 应用id,外键
	 */
	private Integer appId;
	public Integer getAppImgId() {
		return appImgId;
	}
	public void setAppImgId(Integer appImgId) {
		this.appImgId = appImgId;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public AppImg(Integer appImgId, String imgAddress, Integer orderNum, Date updateDate, Integer appId) {
		super();
		this.appImgId = appImgId;
		this.imgAddress = imgAddress;
		this.orderNum = orderNum;
		this.updateDate = updateDate;
		this.appId = appId;
	}
	public AppImg() {
		super();
	}

	
}
