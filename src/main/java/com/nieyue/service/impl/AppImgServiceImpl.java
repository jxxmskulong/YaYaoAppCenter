package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.AppImg;
import com.nieyue.dao.AppImgDao;
import com.nieyue.service.AppImgService;
@Service
public class AppImgServiceImpl implements AppImgService{
	@Resource
	AppImgDao appImgDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addAppImg(AppImg appImg) {
		appImg.setUpdateDate(new Date());
		boolean b = appImgDao.addAppImg(appImg);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delAppImg(Integer appImgId) {
		boolean b = appImgDao.delAppImg(appImgId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateAppImg(AppImg appImg) {
		appImg.setUpdateDate(new Date());
		boolean b = appImgDao.updateAppImg(appImg);
		return b;
	}

	@Override
	public AppImg loadAppImg(Integer appImgId) {
		AppImg r = appImgDao.loadAppImg(appImgId);
		return r;
	}

	@Override
	public int countAll(
			Integer appId,
			Integer orderNum,
			Date updateDate
			) {
		int c = appImgDao.countAll(appId,orderNum,updateDate);
		return c;
	}

	@Override
	public List<AppImg> browsePagingAppImg(
			Integer appId,
			Integer orderNum,
			Date updateDate,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<AppImg> l = appImgDao.browsePagingAppImg(
				appId,orderNum,updateDate,
				pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
