package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.AppImg;

/**
 * 应用逻辑层接口
 * @author yy
 *
 */
public interface AppImgService {
	/** 新增应用 */	
	public boolean addAppImg(AppImg appImg) ;	
	/** 删除应用 */	
	public boolean delAppImg(Integer appImgId) ;
	/** 更新应用*/	
	public boolean updateAppImg(AppImg appImg);
	/** 装载应用 */	
	public AppImg loadAppImg(Integer appImgId);	
	/** 应用总共数目 */	
	public int countAll(
			Integer appId,
			Integer orderNum,
			Date updateDate
			);
	/** 分页应用信息 */
	public List<AppImg> browsePagingAppImg(
			Integer appId,
			Integer orderNum,
			Date updateDate,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
