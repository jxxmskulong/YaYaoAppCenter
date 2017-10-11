package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日数据类
 * 
 * @author yy
 * 
 */
public class DailyData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 每日数据id
	 */
	private Integer dailyDataId;
	/**
	 * 注册数
	 */
	private Long registerNumber;
	/**
	 * 充值金额
	 */
	private Double recharge;
	/**
	 *记录时间
	 */
	private Date recordDate;
	/**
	 *创建时间
	 */
	private Date createDate;
	/**
	 * 活动ID
	 */
	private Integer appId;
	/**
	 * 账户ID
	 */
	private Integer acountId;
	public DailyData() {
		super();
	}
	public DailyData(Integer dailyDataId, Long registerNumber, Double recharge, Date recordDate, Date createDate,
			Integer appId, Integer acountId) {
		super();
		this.dailyDataId = dailyDataId;
		this.registerNumber = registerNumber;
		this.recharge = recharge;
		this.recordDate = recordDate;
		this.createDate = createDate;
		this.appId = appId;
		this.acountId = acountId;
	}
	public Integer getDailyDataId() {
		return dailyDataId;
	}
	public void setDailyDataId(Integer dailyDataId) {
		this.dailyDataId = dailyDataId;
	}
	public Long getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(Long registerNumber) {
		this.registerNumber = registerNumber;
	}
	public Double getRecharge() {
		return recharge;
	}
	public void setRecharge(Double recharge) {
		this.recharge = recharge;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getAcountId() {
		return acountId;
	}
	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "DailyData [dailyDataId=" + dailyDataId + ", registerNumber=" + registerNumber + ", recharge=" + recharge
				+ ", recordDate=" + recordDate + ", createDate=" + createDate + ", appId=" + appId + ", acountId="
				+ acountId + "]";
	}
	
}
