package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 财务
 * @author 聂跃
 * @date 2017年4月12日
 */
public class Finance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 财务id
	 */
	private Integer financeId;
	/**
	 * 充值金额
	 */
	private Double recharge;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 账户id外键
	 */
	private Integer acountId;
	
	public Finance() {
		super();
	}

	public Integer getFinanceId() {
		return financeId;
	}

	public void setFinanceId(Integer financeId) {
		this.financeId = financeId;
	}

	public Double getRecharge() {
		return recharge;
	}

	public void setRecharge(Double recharge) {
		this.recharge = recharge;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public Finance(Integer financeId, Double recharge, Date updateDate, Integer acountId) {
		super();
		this.financeId = financeId;
		this.recharge = recharge;
		this.updateDate = updateDate;
		this.acountId = acountId;
	}

	@Override
	public String toString() {
		return "Finance [financeId=" + financeId + ", recharge=" + recharge + ", updateDate=" + updateDate
				+ ", acountId=" + acountId + "]";
	}
	
}
