package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Acount;
import com.nieyue.bean.Finance;
import com.nieyue.bean.Role;
import com.nieyue.exception.MySessionException;
import com.nieyue.service.AcountService;
import com.nieyue.service.FinanceService;
import com.nieyue.service.RoleService;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 账户控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/acount")
public class AcountController {
	@Resource
	private AcountService acountService;
	@Resource
	private RoleService roleService;
	@Resource
	private FinanceService financeService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 账户分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingAcount(
			@RequestParam(value="spreadId",required=false)Integer spreadId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="minScale",required=false)Double minScale,
			@RequestParam(value="maxScale",required=false)Double maxScale,
			@RequestParam(value="masterId",required=false)Integer masterId,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="acount_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<Acount> list = new ArrayList<Acount>();
			list= acountService.browsePagingAcount(spreadId,phone,nickname,minScale,maxScale,masterId,roleId,status,createDate,loginDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
				
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 账户修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateAcount(@ModelAttribute Acount acount,HttpSession session)  {
		//账户已经存在
		if(acountService.loginAcount(acount.getPhone(), null,acount.getAcountId())!=null
				//||acountService.loginAcount(acount.getEmail(), null,acount.getAcountId())!=null
				){
			return ResultUtil.getSR(false);
		}
		//System.err.println(acount);
		boolean um = acountService.updateAcount(acount);
		session.setAttribute("acount", acount);
		//System.err.println(um);
		return ResultUtil.getSR(um);
	}
	/**
	 * 账户修改真实姓名、手机号、微信号、支付宝账号
	 * @return
	 */
	@RequestMapping(value = "/updatePhone", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updatePhoneAcount(
			@RequestParam(value="acountId")Integer acountId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="wechat")String wechat,
			@RequestParam(value="realname")String realname,
			@RequestParam(value="alipay")String alipay,
			HttpSession session)  {
		//手机号登陆 账户已经存在
		if(!phone.equals("")&&((Acount)session.getAttribute("acount")).getPhone().equals(phone)
				&&acountService.loginAcount(phone, null,acountId)!=null){//不存在就错
			return ResultUtil.getSR(false);
		}
		//微信登陆 账户已经存在
		if((((Acount)session.getAttribute("acount")).getPhone()==null||((Acount)session.getAttribute("acount")).getPhone().equals(""))
				&&acountService.loginAcount(phone, null,acountId)==null){//存在就错
			return ResultUtil.getSR(false);
		}
		if(phone.equals("")){
			phone=null;
		}
		Acount newa = acountService.loadAcount(acountId);
			newa.setPhone(phone);
			newa.setWechat(wechat);
			newa.setRealname(realname);
			newa.setAlipay(alipay);
		boolean um = acountService.updateAcount(newa);
		session.setAttribute("acount", newa);
		return ResultUtil.getSR(um);
	}
	/**
	 * 账户增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addAcount(@ModelAttribute Acount acount, HttpSession session) {
		//账户已经存在
		if(acountService.loginAcount(acount.getPhone(), null,null)!=null ){
			return ResultUtil.getSR(false);
		}
		acount.setPassword(MyDESutil.getMD5(acount.getPassword()));
		boolean am = acountService.addAcount(acount);
		return ResultUtil.getSR(am);
	}
	/**
	 * 账户删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delAcount(
			@RequestParam("acountId") Integer acountId,
			HttpSession session)  {
		boolean dm = acountService.delAcount(acountId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 账户浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="spreadId",required=false)Integer spreadId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="minScale",required=false)Double minScale,
			@RequestParam(value="maxScale",required=false)Double maxScale,
			@RequestParam(value="masterId",required=false)Integer masterId,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			HttpSession session)  {
		int count = acountService.countAll(spreadId,phone,nickname,minScale,maxScale,masterId,roleId,status,createDate,loginDate);
		return count;
	}
	/**
	 * 账户单个加载
	 * @return
	 */
	@RequestMapping(value = "/{acountId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadAcount(@PathVariable("acountId") Integer acountId,HttpSession session)  {
		List<Acount> list = new ArrayList<Acount>();
		Acount acount = acountService.loadAcount(acountId);
		if(acount!=null &&!acount.equals("")){
				list.add(acount);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 管理员登录
	 * @return
	 * @throws MySessionException 
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loginAcount(
			@RequestParam(value="adminName") String adminName,
			@RequestParam(value="password") String password,
			@RequestParam(value="random",required=false) String random,
			HttpSession session) throws MySessionException  {
		//1代验证码
		/*String ran= (String) session.getAttribute("random");
		List<Acount> list = new ArrayList<Acount>();
		if(!ran.equals(random)){
			return ResultUtil.getSlefSRFailList(list);
		}*/
		Integer gtResult = (Integer) session.getAttribute("gtResult");
		List<Acount> list = new ArrayList<Acount>();
		//System.out.println(gtResult);
		if(gtResult!=1){
			return ResultUtil.getSlefSRFailList(list);
		}
		Acount acount = acountService.loginAcount(adminName, MyDESutil.getMD5(password),null);
		if(acount!=null&&!acount.equals("")){
			acount.setLoginDate(new Date());
			boolean b = acountService.updateAcount(acount);
			if(b){
			Integer roleId = acount.getRoleId();
			Role r = roleService.loadRole(roleId);
			if(r.getName().equals("用户")){
			throw new MySessionException();//没权限	
			}
			session.setAttribute("acount", acount);
			session.setAttribute("role", r);
			List<Finance> f = financeService.browsePagingFinance(acount.getAcountId(), 1, 1, "finance_id", "asc");
			session.setAttribute("finance", f.get(0));
			list.add(acount);
			return ResultUtil.getSlefSRSuccessList(list);
			}
		}else if(acount.getStatus().equals(1)){
			List<String> l1 = new ArrayList<String>();
			l1.add("账户锁定");
			return ResultUtil.getSlefSRFailList(l1);
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	/**
	 * web用户登录
	 * @return
	 */
	@RequestMapping(value = "/weblogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList webLoginAcount(
			@RequestParam("adminName") String adminName,
			@RequestParam("password") String password,
			@RequestParam(value="random",required=false) String random,
			HttpSession session)  {
		//1代验证码
//		String ran= (String) session.getAttribute("random");
//		List<Acount> list = new ArrayList<Acount>();
//		if(!ran.equals(random)){
//			return ResultUtil.getSlefSRFailList(list);
//		}
		List<Object> list = new ArrayList<Object>();
		Acount acount = acountService.loginAcount(adminName, MyDESutil.getMD5(password),null);
		//自动登陆
		if(acount==null|| acount.equals("")){
			acount=acountService.loginAcount(adminName, password, null);
		}
		if(acount!=null&&!acount.equals("")&&acount.getStatus().equals(0)){
			acount.setLoginDate(new Date());
			boolean b = acountService.updateAcount(acount);
			if(b){
			session.setAttribute("acount", acount);
			Integer roleId = acount.getRoleId();
			Role r = roleService.loadRole(roleId);
			session.setAttribute("role", r);
			List<Finance> f = financeService.browsePagingFinance(acount.getAcountId(), 1, 1, "finance_id", "asc");
			session.setAttribute("finance", f.get(0));
			list.add(acount);
			//return ResultUtil.getSlefSRSuccessList(list);
			return ResultUtil.getSlefSRSuccessList(list);
			}
		}else if(acount.getStatus().equals(1)){
			List<String> l1 = new ArrayList<String>();
			l1.add("账户锁定");
			return ResultUtil.getSlefSRFailList(l1);
		}
		return ResultUtil.getSlefSRFailList(list);
	}

	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value = "/islogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList isLoginAcount(
			HttpSession session)  {
		Acount acount = (Acount) session.getAttribute("acount");
		List<Acount> list = new ArrayList<Acount>();
		if(acount!=null && !acount.equals("")){
			list.add(acount);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/loginout", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loginoutAcount(
			HttpSession session)  {
		session.invalidate();
		return ResultUtil.getSlefSRSuccessList(null);
	}
	
}
