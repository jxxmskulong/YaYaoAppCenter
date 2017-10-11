package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.App;

/**
 * 应用数据库接口
 * @author yy
 *
 */
@Mapper
public interface AppDao {
	/** 新增应用*/	
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
			@Param("platform")Integer platform,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status
			);	
	/** 分页应用信息 */
	public List<App> browsePagingApp(
			@Param("platform")Integer platform,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
