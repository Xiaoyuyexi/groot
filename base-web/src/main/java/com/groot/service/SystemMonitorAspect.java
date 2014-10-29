package com.groot.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groot.module.dao.GrootSystemMonitorDAO;
import com.groot.module.domain.GrootSystemMonitor;
import com.groot.module.domain.GrootSystemMonitorExample;
import com.groot.module.domain.GrootSystemUser;
import com.groot.vo.Constant;
import com.groot.vo.MonitorMessage;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.StringUtils;

@Aspect
public class SystemMonitorAspect {

	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private GrootSystemMonitorDAO systemMonitorDAO;
//	@Autowired
//	public PropertiesService propertiesServiceRpc;
	
	@Around("@annotation(monitor)" )  
	@Transactional
    public Object saveMonitorMessage(ProceedingJoinPoint point, SystemMonitor monitor){
		Object retVal = null;
		try {
//			RedisClientUtils.init(propertiesServiceRpc.getRedisArgs());
			retVal = point.proceed();
			if(retVal != null){
				if(retVal instanceof AjaxMessage){
					AjaxMessage ajaxMessage = (AjaxMessage)retVal;
					String flag = systemConfigService.getSystemValue("10000007");
					if("1".equals(flag)){
						if("00".equals(ajaxMessage.getCode())){
							Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
							String str = gson.toJson(ajaxMessage.getObjArray());
								
							MonitorMessage monitorVo = new MonitorMessage();
							monitorVo.setObjectArray(new String[] {str});
							monitorVo.setControllerName(point.getTarget().getClass().getSimpleName());
							monitorVo.setPermission(monitor.permission());
							monitorVo.setAccessTime(new Date());
							
							Object temp = SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
							if(temp != null){
								GrootSystemUser user = (GrootSystemUser) temp;
								monitorVo.setAccessUser(user.getLoginName());
							}
							
//							RedisClientUtils.leftPush(RedisClientUtils.REDIS_MONITOR_KEY, monitorVo);
							this.insertMonitorMessage(monitorVo);
						}
					}
					//避免监控需要的数据传到前台
					ajaxMessage.setObjArray(null);
					retVal = ajaxMessage;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	@Transactional
	private void insertMonitorMessage(MonitorMessage monitor){
		GrootSystemMonitor hsm = new GrootSystemMonitor();
		hsm.setId(StringUtils.randomUUID());
		hsm.setCreateUserId(monitor.getAccessUser());
		hsm.setControllerName(monitor.getControllerName());
		hsm.setPermission(monitor.getPermission());
		hsm.setCreateTime(monitor.getAccessTime());
		
		Object[] array = monitor.getObjectArray();
		if(array != null && array.length > 0){
			String strObj = (String)array[0];
			hsm.setMessage(strObj.getBytes());
		}
		systemMonitorDAO.insert(hsm);
	}
	
	//--------------解析监控数据
	@Scheduled(cron="0 0/1 * * * ? ")
	@Transactional
	public void processMonitorData(){
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DAY_OF_MONTH, -1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		try{
			process(start.getTime(), end.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void process(Date start, Date end){
		if((start != null) && (end != null)){
			GrootSystemMonitorExample example = new GrootSystemMonitorExample();
			example.createCriteria().andCreateTimeBetween(start, end);
			List<GrootSystemMonitor> monitors = systemMonitorDAO.selectByExampleWithBLOBs(example);
			for(GrootSystemMonitor monitor:monitors){
				JSONArray jarray = new JSONArray();
				@SuppressWarnings("static-access")
				Object[] os = jarray.fromObject(new String(monitor.getMessage())).toArray();
				for(Object o : os){
					if(o != null){
						Map<Object, Object> map = (Map<Object, Object>)o;
						processMap(map, monitor);
					}
				}
			}
		}
	}
	
	/**
	 * 递归判断
	 * @param map
	 * @param monitor
	 */
	@SuppressWarnings("unchecked")
	public void processMap(Map<Object, Object> map,GrootSystemMonitor monitor){
		for(Object oo:map.keySet()){
			Object value = map.get(oo);
			if(value instanceof JSONObject){
				processMap((Map<Object, Object>)value,monitor);
			}else {
//				GrootDataHistory history = new GrootDataHistory();
//				history.setId(StringUtils.randomUUID());
//				history.setKey((String)oo);
//				history.setValue(String.valueOf(value));
//				if(null!=map.get("id")){
//					history.setObjectId(String.valueOf(map.get("id")));
//				}
//				//history.setObjectId(monitor.getMessageId());
//				history.setPermissionsKey(monitor.getPermission());
//				history.setTime(monitor.getTime());
//				history.setUserName(monitor.getAccessUser());
//				history.setNote1(monitor.getId());
//				System.out.println((String)oo);
//				System.out.println(String.valueOf(value));
//				GrootDataHistoryDAO.insert(history);
			}
		}
	}
}
