package com.groot.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.groot.module.domain.GrootBusheet;
import com.web.utils.net.FTPUtil;



@Transactional(readOnly=true)
@Service
public class ExcelExportUtils {
	
	@Autowired
	public BusheetService busheetService;
	@Autowired
	public PropertiesService propertiesService;
	
	
	
	public void export(List<?> data, String fileName, String sheetCode, HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> mapFill = new HashMap<String, Object>();
		mapFill.put("list", data);
		
		response.setContentType("application/vnd.ms-excel;charset=GB2312");
		GrootBusheet busheet = busheetService.getBusheetByCode(sheetCode);
		String rp= "exceltemplate";
		String path = request.getSession().getServletContext().getRealPath(rp);
	    File folder = new File(path);
	    if(!folder.exists()){
		   folder.mkdirs();
	    }
		XLSTransformer transformer = new XLSTransformer();
		try {
			InputStream in = new FTPUtil(propertiesService.getImageFtpArgs()).download(rp+"/"+busheet.getBusheetCode(), URLEncoder.encode(busheet.getXlsTemplet(),"utf-8"));
			
			if(null == in){
				return;
			}
			FileCopyUtils.copy(FileCopyUtils.copyToByteArray(in),new File(path+File.separator+busheet.getXlsTemplet()));
			transformer.transformXLS(path+File.separator+busheet.getXlsTemplet(), mapFill, path+File.separator+busheet.getXlsTemplet()+"_temp");
			try {
				response.setHeader("Content-disposition",
						"attachment;success=true;filename ="
								+ URLEncoder.encode(fileName, "utf-8"));
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				OutputStream fos = null;
				InputStream fis = null;
				File uploadFile = new File(path+File.separator+busheet.getXlsTemplet()+"_temp");
				fis = new FileInputStream(uploadFile);
				bis = new BufferedInputStream(fis);
				fos = response.getOutputStream();
				bos = new BufferedOutputStream(fos);
				// 弹出下载对话框
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.flush();
				fis.close();
				bis.close();
				fos.close();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
