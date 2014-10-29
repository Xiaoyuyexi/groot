package com.web.utils.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.web.bo.Mail;
import com.web.bo.RedisArgs;
import com.web.utils.lang.SerializeUtil;

public class RedisClientUtils {

	private static JedisPool pool;  
	
	public final static String REDIS_MAIL_KEY = "list.mail";
	public final static String REDIS_SMS_KEY = "list.sms";
	public final static String REDIS_MONITOR_KEY = "list.monitor";
	public final static String REDIS_TERMINAL_SIGN_KEY = "list.terminalSign";
	public final static String REDIS_MAIL_SMS_SWITCH_KEY = "mail.sms.switch";
	public final static String REDIS_RECEIPT_KEY = "list.receipt";
	public final static String REDIS_MAP_KEY_TRADE = "map.trade";
	public final static String REDIS_CONSUME_BO = "list.consumebo";
	public final static String REDIS_POS_ORDER_LOG = "list.pos.log";
	public final static String REDIS_ERROR_CODE = "ec";
	
	public final static String[] KEY_ARRAY = {REDIS_MAIL_KEY,
												REDIS_SMS_KEY,
												REDIS_CONSUME_BO,
												REDIS_MONITOR_KEY,
												REDIS_TERMINAL_SIGN_KEY,
												REDIS_MAIL_SMS_SWITCH_KEY,
												REDIS_RECEIPT_KEY,
												REDIS_MAP_KEY_TRADE,
												REDIS_POS_ORDER_LOG,
												REDIS_ERROR_CODE,
												"list.test"};
	
	public static RedisArgs redisArgs=null;
	public static void init(RedisArgs args){
		if(null == redisArgs){
			redisArgs = args;
		}
	}
	
	private static boolean checkKey(String key){
		for(String redisKey : KEY_ARRAY){
			if(redisKey.equals(key) || redisKey.startsWith(key)){
				return true;
			}
		}
		return false;
	}
	
	//测试用
//	private static JedisPool getPool(){
//		if(pool == null){
//			JedisPoolConfig config = new JedisPoolConfig();  
//			config.setMaxActive(3);  
//			config.setMaxIdle(3);
//			config.setMaxWait(3);
//			config.setTestOnBorrow(true);
//			config.setTestOnReturn(true);
//			config.setWhenExhaustedAction((byte)0);
//			pool = new JedisPool(config, "192.168.0.149", 6379);
//		}
//		return pool;
//	}
	
	//生产用
	private static JedisPool getPool(){
		if(pool == null){
			//ResourceBundle bundle = ResourceBundle.getBundle("redis");  
			if (redisArgs == null) {  
				throw new IllegalArgumentException(  
						"[redis.properties] is not found!");  
			}  
			JedisPoolConfig config = new JedisPoolConfig();  
			config.setMaxActive(Integer.valueOf(redisArgs.maxActive));  
			config.setMaxIdle(Integer.valueOf(redisArgs.maxIdle));  //bundle.getString("redis.pool.maxIdle")
			config.setMaxWait(Long.valueOf(redisArgs.maxWait));  //bundle.getString("redis.pool.maxWait")
			config.setTestOnBorrow(redisArgs.testOnBorrow);  //Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow"))
			config.setTestOnReturn(redisArgs.testOnReturn); //Boolean.valueOf(bundle.getString("redis.pool.testOnReturn"))
			config.setWhenExhaustedAction((byte)0);
			
			pool = new JedisPool(config, redisArgs.ip,  
					Integer.valueOf(redisArgs.port));  //bundle.getString("redis.ip") bundle.getString("redis.port")
		}
		return pool;
	}
	/**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
        	getPool().returnResource(jedis);
        }
    }
    /**
     * 在instance出错时，必须调用returnBrokenResource返还给pool，
     * 否则下次通过getResource得到的instance的缓冲区可能还存在数据
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis){
    	if(jedis != null){
    		getPool().returnBrokenResource(jedis);
    	}
    }
    /**
     * 关闭连接池
     * 
     */
    public static void poolDestroy() {
    	if (pool != null) {
    		pool.destroy();
    	}
    }
	
    
    //---------------string----------------
	/**
	 * 查询String类型的缓存数据
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static String getStringValue(String key) throws Exception{
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			String result = jedis.get(key);
			return result;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	/**
	 * 设置String类型的缓存数据
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static boolean setStringValue(String key, String str) throws Exception{
		if(!checkKey(key)){
			return false;
		}
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			jedis.set(key, str);
			return true;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
    //---------------string----------------

	
	//---------------list----------------
	/**
	 * 查询缓存数据长度
	 * @param key
	 * @return
	 */
	public static long getListSizeByKey(String key) throws Exception{
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			long size = jedis.llen(key.getBytes());
			return size;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	/**
	 * 删除key和缓存数据
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static Long removeKey(String key) throws Exception{
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			return jedis.del(key);
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	/**
	 * 删除list类型的缓存数据
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static Long removeValueOfList(String key,Object object) throws Exception{
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			return jedis.lrem(key.getBytes(), 0, SerializeUtil.serialize(object));
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	/**
	 * 查询list类型的缓存数据
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static List<Object> getListValueByKey(String key) throws Exception{
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		List<Object> valueList = new ArrayList<Object>();
		try{
			jedis = getPool().getResource();
			long size = jedis.llen(key.getBytes());
			if(size > 0){
				List<byte[]> tempList = jedis.lrange(key.getBytes() , 0, -1);
				if(tempList != null){
					for(byte[] temp : tempList){
						Object obj = SerializeUtil.unserialize(temp);
						valueList.add(obj);
					}
				}
			}
			return valueList;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
		
	}
	
	/**
	 * 添加对象
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public static boolean leftPush(String key, Object obj) throws Exception{
		if(StringUtils.isBlank(key) || obj == null){
			return false;
		}
		if(!checkKey(key)){
			return false;
		}
		Jedis jedis = null;
		try{
			jedis = getPool().getResource();
			jedis.lpush(key.getBytes(), SerializeUtil.serialize(obj));
			return true;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	/**
	 * 获取队列中下一个对象
	 * @return
	 * @throws Exception 
	 */
	public static Object rightPop(String key) throws Exception{
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		try{
			Object obj = null;
			jedis = getPool().getResource();
			long size = jedis.llen(key.getBytes());
			if(size > 0){
				byte[] temp = jedis.rpop(key.getBytes());
				if(temp != null){
					obj = SerializeUtil.unserialize(temp);
				}
			}
			return obj;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	
	/**
	 * 获取队列中下一个对象
	 * @return
	 * @throws Exception 
	 */
	public static Object rightBPop(String key,int timeOut) throws Exception{
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(key)){
			return null;
		}
		Jedis jedis = null;
		try{
			Object obj = null;
			jedis = getPool().getResource();
			long size = jedis.llen(key.getBytes());
			if(size > 0){
				List<byte[]> temp = jedis.brpop(0,key.getBytes());
				if(CollectionUtils.isNotEmpty(temp)){
					obj = SerializeUtil.unserialize(temp.get(1));
				}
			}
			return obj;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	/**
	 * 获取队列中下一个对象
	 * @return
	 * @throws Exception 
	 */
	public static Object rightBPop(String key) throws Exception{
		return rightBPop(key,0);
	}
	//---------------list----------------
	
	/**
	 * 获取系统信息
	 * @throws Exception 
	 */
	public static Map<String, String> getSystemInfo() throws Exception{
		Jedis jedis = null;
		try{
			//ResourceBundle bundle = ResourceBundle.getBundle("redis"); 
			jedis = getPool().getResource();
			Map<String, String> map = new HashMap<String, String>();
//			map.put("serverIp", jedis.getClient().getHost());
			map.put("serverIp", redisArgs.ip);
			String info = jedis.info();
			String[] tempArray = info.split("\r\n");
			for (int i = 0; i < tempArray.length; i++) {
				String temp = tempArray[i];
				if(temp.indexOf(":")!=-1){
					String[] infoArray = temp.split(":");
					map.put(infoArray[0], infoArray[1]);
				}
			}
			return map;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	
	
	
	//---------------map-----------------
	public static boolean setMapValueByKey(String mapName, String key, Object value) throws Exception{
		Jedis jedis = null;
		if(StringUtils.isBlank(mapName) 
				|| StringUtils.isBlank(key)
				|| value == null){
			return false;
		}
		if(!checkKey(mapName)){
			return false;
		}
		try{
			jedis = getPool().getResource();
			
			Map<byte[],byte[]> map = new HashMap<byte[],byte[]>();
	        map.put(key.getBytes(), SerializeUtil.serialize(value));
	        jedis.hmset(mapName.getBytes(), map);
	        return true;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	public static Object getMapValueByKey(String mapName, String key) throws Exception {
		Jedis jedis = null;
		Object obj = null;
		if(StringUtils.isBlank(mapName) || StringUtils.isBlank(key)){
			return null;
		}
		if(!checkKey(mapName)){
			return null;
		}
		try{
			jedis = getPool().getResource();
			
	        List<byte[]> rsmap = jedis.hmget(mapName.getBytes(), key.getBytes());
	        if(rsmap.get(0) != null){
	        	obj = SerializeUtil.unserialize(rsmap.get(0));
	        	return obj;
	        }
	        return null;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	
	public static boolean deleteMap(String mapName) throws Exception {
		Jedis jedis = null;
		if(!checkKey(mapName)){
			return false;
		}
		try{
			jedis = getPool().getResource();
		
			Iterator<byte[]> iter=jedis.hkeys(mapName.getBytes()).iterator();
			while (iter.hasNext()){
				byte[] key = iter.next();
				jedis.hdel(mapName.getBytes(), key);
			}
			return true;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	public static boolean deleteMapByKey(String mapName, String key) throws Exception {
		Jedis jedis = null;
		if(StringUtils.isBlank(mapName) || StringUtils.isBlank(key)){
			return false;
		}
		if(!checkKey(mapName)){
			return false;
		}
		try{
			jedis = getPool().getResource();
			jedis.hdel(mapName.getBytes(), key.getBytes());
			return true;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	public static List<Object> getMapValues(String mapName) throws Exception {
		Jedis jedis = null;
		List<Object> list = new ArrayList<Object>();
		if(!checkKey(mapName)){
			return null;
		}
		try{
			jedis = getPool().getResource();
			
	        List<byte[]> rsmap = jedis.hvals(mapName.getBytes());
	        for(byte[] rs : rsmap){
	        	if(rs != null){
	        		list.add(SerializeUtil.unserialize(rs));
	        	}
	        }
	        return list;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	public static List<String> getMapKeys(String mapName) throws Exception {
		Jedis jedis = null;
		List<String> list = new ArrayList<String>();
		if(!checkKey(mapName)){
			return null;
		}
		try{
			jedis = getPool().getResource();
			
			Set<byte[]> rsmap = jedis.hkeys(mapName.getBytes());
			for(byte[] rs : rsmap){
				if(rs != null){
					list.add(new String(rs, "GB2312"));
				}
			}
			return list;
		}catch (Exception e) {
			returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(jedis);
		}
	}
	//---------------map-----------------
	
	
	
	public static void main(String[] args){
		try {
			
			RedisClientUtils u = new RedisClientUtils();
			
			
			//------------------test list
//			HkrtTerminalBO a = new HkrtTerminalBO();
//			a.setSignCount(0);
//			a.setTerminalId("123456");
//			leftPush(RedisClientUtils.REDIS_TERMINAL_SIGN_KEY, a);
			
//			List<Object> list = u.getListValueByKey(RedisClientUtils.REDIS_TERMINAL_SIGN_KEY);
//			if(list.size() > 0){
//				if(list.get(0) instanceof HkrtTerminalBO){
//					HkrtTerminalBO bo = (HkrtTerminalBO) list.get(0);
//					System.out.println(bo.getSignCount()+" - "+bo.getTerminalId());
//				}
//			}
			
//			Object o = u.rightPop(RedisClientUtils.REDIS_TERMINAL_SIGN_KEY);
//			if(o instanceof HkrtTerminalBO){
//				HkrtTerminalBO bo = (HkrtTerminalBO) o;
//				System.out.println(bo.getSignCount()+" - "+bo.getTerminalId());
//			}
			
			
			
			//---------------------test map
			Mail mail = new Mail();
			mail.setAddressee("2222222");
//			u.setMapValueByKey(u.REDIS_MAP_KEY_TRADE, "m2", mail);
//			
//			Mail m1 = (Mail) u.getMapValueByKey(u.REDIS_MAP_KEY_TRADE, "m1");
//			if(m1 != null){
//				System.out.println(m1.getAddressee());
//			}
		
			List<Object> list1 = u.getMapValues(u.REDIS_MAP_KEY_TRADE);
			for(Object o : list1){
				if(o != null){
					Mail m2 = (Mail) o;
					System.out.println(m2.getAddressee());
				}
			}
			
			List<String> list2 = u.getMapKeys(u.REDIS_MAP_KEY_TRADE);
			for(String s : list2){
				System.out.println(s);
			}
			
			
//			u.deleteMapByKey(u.REDIS_MAP_KEY_TRADE, "m1");
			u.deleteMap(u.REDIS_MAP_KEY_TRADE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
