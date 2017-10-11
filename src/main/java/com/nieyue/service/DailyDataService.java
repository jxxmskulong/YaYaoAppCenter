package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.DailyData;

/**
 * 每日数据逻辑层接口
 * @author yy
 *
 */
public interface DailyDataService {
	/** 新增每日数据 */	
	public boolean addDailyData(DailyData dailyData) ;	
	/** 删除每日数据 */	
	public boolean delDailyData(Integer dailyDataId) ;
	/** 更新每日数据*/	
	public boolean updateDailyData(DailyData dailyData);
	/** 装载每日数据 */	
	public DailyData loadDailyData(Integer dailyDataId,Date recordDate,Integer appId,Integer acountId);	
	/** 每日数据总共数目 */	
	public int countAll(Date recordDate,Integer appId,Integer acountId);
	/** 分页每日数据信息 */
	public List<DailyData> browsePagingDailyData(Date recordDate,Integer appId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
	/** 统计每日数据信息 */
	public List<DailyData> statisticsDailyData(Date startDate,Date endDate,Integer appId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;		
}
