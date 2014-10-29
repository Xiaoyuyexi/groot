package com.groot.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootBusheet;
import com.groot.module.domain.GrootSystemUser;
import com.groot.service.BusheetService;
import com.groot.service.BusheetWorkflowService;
import com.groot.service.PrintUtil;
import com.groot.service.PropertiesService;
import com.groot.service.SelectService;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.utils.net.FTPUtil;

@Controller
public class BusheetController {
	@Autowired
	public SelectService selectService;
	
	@Autowired
	public BusheetService busheetService;
	
	@Autowired
	public BusheetWorkflowService busheetWorkflowService;
	
	@Autowired
	public PropertiesService propertiesService;
	/**
	 *  单据管理页面
	 * 
	 * @return
	 */
	@RequiresPermissions("BusheetManager:menu")
	@RequestMapping(value = "/busheet_manager")
	public ModelAndView userManager() {
		return new ModelAndView("jsp/busheet_manager");
	}
	
	/**
	 *  单据编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/insert_busheet")
	public ModelAndView insertBusheet() {
		return new ModelAndView("jsp/busheet_info");
	}
	
	/**
	 *  单据编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/edit_busheet")
	public ModelAndView editBusheet(String id) {
		GrootBusheet busheet = busheetService.getBusheet(id);
		return new ModelAndView("jsp/busheet_info", "sheet", busheet);
	}
	
	/**
	 * 单据列表(分页)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBusheetListForPage", method = RequestMethod.POST)
	public DataSet<GrootBusheet> getBusheetListForPage(@RequestParam Map mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		String countSqlId = "SYSTEM.getBusheetCount";
		String listSqlId = "SYSTEM.getBusheetListForPage";
		DataSet<GrootBusheet> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	
	/**
	 * 更新或者添加一条记录
	 * @param busheet
	 * @return
	 */
	@RequiresPermissions("BusheetManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveBusheet", method = RequestMethod.POST)
	public int saveBusheet(GrootBusheet busheet, HttpSession session) {
		GrootSystemUser currentUser = (GrootSystemUser)session.getAttribute("USER_SESSION_KEY");
		if(busheet.getId() == null || "".equals(busheet.getId())){
			busheet.setId(com.web.utils.lang.StringUtils.randomUUID());
			return busheetService.insert(busheet, currentUser);
		} else {
			return busheetService.update(busheet, currentUser);
		}
	}
	
	
	/*
	 * 删除记录
	 */
	@RequiresPermissions("BusheetManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeBusheet", method = RequestMethod.POST)
	public int removeBusheet(GrootBusheet busheet) {
		if(busheetWorkflowService.selectBySheetId(busheet.getId())>0){
			return -1;
		}
		return busheetService.removeByPrimaryKey(busheet.getId());
	}
	/**
	 * 获取全部单据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBusheetAll", method = RequestMethod.POST)
	public List<GrootBusheet> getBusheetAll(){
		return busheetService.getBusheetAll();
	}
	
	
	/**
	 * 代理excel
	 * @param importFile
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	 @RequestMapping(value = "/downloadxls") 
	 public void  downloadExcel(HttpServletRequest request, HttpServletResponse response,String id){
		 GrootBusheet busheet = busheetService.getBusheet(id);
		 if(null == busheet)return;
		 try {
			 	String rp= "exceltemplate";
				String path = request.getSession().getServletContext().getRealPath(rp);
			    File folder = new File(path);
			    if(!folder.exists()){
				   folder.mkdirs();
			   }
				InputStream in = new FTPUtil(propertiesService.getImageFtpArgs()).download(rp+"/"+busheet.getBusheetCode(), URLEncoder.encode(busheet.getXlsTemplet(),"utf-8"));
				if(null == in){
					return;
				}
				try {
					response.setContentType("application/vnd.ms-excel;charset=GB2312");
					response.setHeader("Content-disposition",
							"attachment;success=true;filename ="
									+ URLEncoder.encode(busheet.getXlsTemplet(), "utf-8"));
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					OutputStream fos = null;
					bis = new BufferedInputStream(in);
					fos = response.getOutputStream();
					bos = new BufferedOutputStream(fos);
					// 弹出下载对话框
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead);
					}
					bos.flush();
					bis.close();
					fos.close();
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (ParsePropertyException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	
	/**
	 * 代理excel
	 * @param importFile
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	 @RequestMapping(value = "/uploadHkrtxls",method = RequestMethod.POST) 
	 public String  uploadHkrtExcel(@RequestParam(value = "file") CommonsMultipartFile importFile, HttpServletRequest request
			 , HttpServletResponse response, String id, HttpSession session){
		 GrootSystemUser currentUser = (GrootSystemUser)session.getAttribute("USER_SESSION_KEY");
		 JSONObject jObject = new JSONObject();
		 PrintUtil print = new PrintUtil(response,"text/html;charset=UTF-8");
		 jObject.put("value", "上传成功");
		 jObject.put("src", importFile.getOriginalFilename());
		 String fileName = importFile.getOriginalFilename();
		 String rp="exceltemplate";
		 GrootBusheet busheet = busheetService.getBusheet(id);
		 if(!validate(importFile)){
			 jObject.put("value", "上传文件类型错误!");
		 }
		 try {
			 /* String path = request.getSession().getServletContext().getRealPath(rp);
			    File folder = new File(path);
			    if(!folder.exists()){
				   folder.mkdirs();
			   }
	        	FileCopyUtils.copy(importFile.getBytes(),new File(path+File.separator+fileName));*/
			 
	        	if(!new FTPUtil(propertiesService.getImageFtpArgs()).upload(rp+"/"+busheet.getBusheetCode(), URLEncoder.encode(fileName,"utf-8"), importFile.getInputStream())){
	        		return "上传失败";
	        	}
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败";
			} 
		 
		 
		 busheet.setId(id);
		 busheet.setXlsTemplet(fileName);
		 
		 busheetService.update(busheet, currentUser);
		 print.print(jObject);
		 return null;
	 }
	 
	 public boolean validate(CommonsMultipartFile importFile){
		 if(!importFile.getOriginalFilename().endsWith("xls") && !importFile.getOriginalFilename().endsWith("xlsx")){
			 return false;
		 }
		 return true;
	 }
}
