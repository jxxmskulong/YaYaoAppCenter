package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.AppImg;
import com.nieyue.service.AppImgService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 应用图片控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/appImg")
public class AppImgController {
	@Resource
	private AppImgService appImgService;
	
	/**
	 * 应用图片分页浏览
	 * @param orderName 应用图片排序数据库字段
	 * @param orderWay 应用图片排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingAppImg(
			@RequestParam(value="appId",required=false)Integer appId,
			@RequestParam(value="orderNum",required=false)Integer orderNum,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="app_img_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<AppImg> list = new ArrayList<AppImg>();
			list= appImgService.browsePagingAppImg(appId,orderNum,updateDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 应用图片修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateAppImg(@ModelAttribute AppImg appImg,HttpSession session)  {
		boolean um = appImgService.updateAppImg(appImg);
		return ResultUtil.getSR(um);
	}
	/**
	 * 应用图片增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addAppImg(@ModelAttribute AppImg appImg, HttpSession session) {
		boolean am =appImgService.addAppImg(appImg);
		return ResultUtil.getSR(am);
	}
	/**
	 * 应用图片删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delAppImg(@RequestParam("appImgId") Integer appImgId,HttpSession session)  {
		boolean dm = appImgService.delAppImg(appImgId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 应用图片浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="appId",required=false)Integer appId,
			@RequestParam(value="orderNum",required=false)Integer orderNum,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		int count = appImgService.countAll(appId,orderNum,updateDate);
		return count;
	}
	/**
	 * 应用图片单个加载
	 * @return
	 */
	@RequestMapping(value = "/{appImgId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadAppImg(@PathVariable("appImgId") Integer appImgId,HttpSession session)  {
		List<AppImg> list = new ArrayList<AppImg>();
		AppImg appImg = appImgService.loadAppImg(appImgId);
			if(appImg!=null &&!appImg.equals("")){
				list.add(appImg);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
