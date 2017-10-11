package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.AppImg;

/**
 * 应用图片数据库接口
 * @author yy
 *
 */
@Mapper
public interface AppImgDao {
	/** 新增应用图片*/	
	public boolean addAppImg(AppImg appImg) ;	
	/** 删除应用图片 */	
	public boolean delAppImg(Integer appImgId) ;
	/** 更新应用图片*/	
	public boolean updateAppImg(AppImg appImg);
	/** 装载应用图片 */	
	public AppImg loadAppImg(Integer appImgId);	
	/** 应用图片总共数目 */	
	public int countAll(
			@Param("appId")Integer appId,
			@Param("orderNum")Integer orderNum,
			@Param("updateDate")Date updateDate
			);	
	/** 分页应用图片信息 */
	public List<AppImg> browsePagingAppImg(
			@Param("appId")Integer appId,
			@Param("orderNum")Integer orderNum,
			@Param("updateDate")Date updateDate,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
