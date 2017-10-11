package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 应用
 * @author yy
 *
 */
public class App implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 应用id
	 */
	private Integer appId;
	/**
	 * 平台，1安卓，2IOS，3，H5
	 */
	private Integer platform;
	/**
	 * 应用来源
	 */
	private String source;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 应用名称
	 */
	private String title;
	/**
	 * 容量，单位MB
	 */
	private Double capacity;
	/**
	 *分成比例，默认0.5
	 */
	private Double divideIntoProportion;
	/**
	 *封面
	 */
	private String imgAddress;
	/**
	 *内容
	 */
	private String content;
	/**
	 *注册次数
	 */
	private Integer registerNumber;
	/**
	 *充值金额
	 */
	private Double recharge;
	/**
	 *跳转url
	 */
	private String url;
	/**
	 *下架0,上架1
	 */
	private Integer status;
	/**
	 * 应用创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 应用图片集合
	 */
	private List<AppImg> appImgList;

	public App() {
		super();
	}

	public App(Integer appId, Integer platform, String source, String version, String title, Double capacity,
			Double divideIntoProportion, String imgAddress, String content, Integer registerNumber, Double recharge,
			String url, Integer status, Date createDate, Date updateDate, List<AppImg> appImgList) {
		super();
		this.appId = appId;
		this.platform = platform;
		this.source = source;
		this.version = version;
		this.title = title;
		this.capacity = capacity;
		this.divideIntoProportion = divideIntoProportion;
		this.imgAddress = imgAddress;
		this.content = content;
		this.registerNumber = registerNumber;
		this.recharge = recharge;
		this.url = url;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.appImgList = appImgList;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public String getImgAddress() {
		return imgAddress;
	}

	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(Integer registerNumber) {
		this.registerNumber = registerNumber;
	}

	public Double getRecharge() {
		return recharge;
	}

	public void setRecharge(Double recharge) {
		this.recharge = recharge;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<AppImg> getAppImgList() {
		return appImgList;
	}

	public void setAppImgList(List<AppImg> appImgList) {
		this.appImgList = appImgList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getDivideIntoProportion() {
		return divideIntoProportion;
	}

	public void setDivideIntoProportion(Double divideIntoProportion) {
		this.divideIntoProportion = divideIntoProportion;
	}
	
}
