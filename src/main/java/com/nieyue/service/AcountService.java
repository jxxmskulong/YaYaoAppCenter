package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Acount;

/**
 * 账户逻辑层接口
 * @author yy
 *
 */
public interface AcountService {
	/** 新增账户 */	
	public boolean addAcount(Acount acount) ;	
	/** 微信账户登录 */
	public Acount weixinBaseAcountLogin(String uuid);
	/** 删除账户 */	
	public boolean delAcount(Integer acountId) ;
	/** 更新账户*/	
	public boolean updateAcount(Acount acount);
	/** 装载账户 */	
	public Acount loadAcount(Integer acountId);
	/** 登录管理员 */	
	public Acount loginAcount(String adminName,String password,Integer acountId);
	/** 账户总共数目 */	
	public int countAll(
			Integer spreadId,
			String phone,
			String nickname,
			Double minScale,
			Double maxScale,
			Integer masterId,
			Integer roleId,
			Integer status,
			Date createDate,
			Date loginDate
			);
	/** 分页账户信息 */
	public List<Acount> browsePagingAcount(
			Integer spreadId,
			String phone,
			String nickname,
			Double minScale,
			Double maxScale,
			Integer masterId,
			Integer roleId,
			Integer status,
			Date createDate,
			Date loginDate,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
