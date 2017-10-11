package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.App;

/**
 * 应用逻辑层接口
 * @author yy
 *
 */
public interface AppService {
	/** 新增应用 */	
	public boolean addApp(App app) ;	
	/** 删除应用 */	
	public boolean delApp(Integer appId) ;
	/** 更新应用*/	
	public boolean updateApp(App app);
	/** 装载应用 */	
	public App loadApp(Integer appId);	
	/** 装载small应用 */	
	public App loadSmallApp(Integer appId);	
	/** 应用总共数目 */	
	public int countAll(
			Integer platform,
			Date createDate,
			Date updateDate,
			Integer status);
	/** 分页应用信息 */
	public List<App> browsePagingApp(
			Integer platform,
			Date createDate,
			Date updateDate,
			Integer status,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
