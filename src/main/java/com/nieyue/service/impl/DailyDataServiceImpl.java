package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.App;
import com.nieyue.bean.DailyData;
import com.nieyue.dao.DailyDataDao;
import com.nieyue.service.AppService;
import com.nieyue.service.DailyDataService;
@Service
public class DailyDataServiceImpl implements DailyDataService{
	@Resource
	DailyDataDao dailyDataDao;
	@Resource
	AppService appService;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addDailyData(DailyData dailyData) {
		dailyData.setCreateDate(new Date());
		boolean b = dailyDataDao.addDailyData(dailyData);
		if(b){
			App app = appService.loadApp(dailyData.getAppId());
			app.setRegisterNumber(app.getRegisterNumber()+dailyData.getRegisterNumber().intValue());
			app.setRecharge(app.getRecharge()+dailyData.getRecharge());
			b=appService.updateApp(app);
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delDailyData(Integer dailyDataId) {
		boolean b = dailyDataDao.delDailyData(dailyDataId);
		DailyData dailyData = dailyDataDao.loadDailyData(dailyDataId, null, null, null);
		if(b){
			App app = appService.loadApp(dailyData.getAppId());
			app.setRegisterNumber(app.getRegisterNumber()-dailyData.getRegisterNumber().intValue());
			app.setRecharge(app.getRecharge()-dailyData.getRecharge());
			b=appService.updateApp(app);
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateDailyData(DailyData dailyData) {
		DailyData odd = dailyDataDao.loadDailyData(dailyData.getDailyDataId(), null, null, null);
		boolean b = dailyDataDao.updateDailyData(dailyData);
		if(b){
			App app = appService.loadApp(dailyData.getAppId());
			app.setRegisterNumber(app.getRegisterNumber()-odd.getRegisterNumber().intValue()+dailyData.getRegisterNumber().intValue());
			app.setRecharge(app.getRecharge()-odd.getRecharge()+dailyData.getRecharge());
			b=appService.updateApp(app);
		}
		return b;
	}

	@Override
	public DailyData loadDailyData(Integer dailyDataId,Date recordDate,Integer appId,Integer acountId) {
		DailyData r = dailyDataDao.loadDailyData(dailyDataId,recordDate,appId,acountId);
		return r;
	}

	@Override
	public int countAll(Date recordDate,Integer appId,Integer acountId) {
		int c = dailyDataDao.countAll(recordDate,appId,acountId);
		return c;
	}

	@Override
	public List<DailyData> browsePagingDailyData(Date recordDate,Integer appId,Integer acountId,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<DailyData> l = dailyDataDao.browsePagingDailyData(recordDate,appId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<DailyData> statisticsDailyData(Date startDate,Date endDate, Integer appId,Integer acountId, int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<DailyData> l = dailyDataDao.statisticsDailyData(startDate,endDate, appId, acountId, pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
