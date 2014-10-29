package com.web.utils.cache;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.web.bo.MemcachedArgs;
/**
 * @author  
 */
public class MemcachedUtils {
	
	/*private static String host = "127.0.0.1";
	private static String port = "11211";*/
	
	private static MemcachedArgs memcachedArgs=null;
	
	public static void init(MemcachedArgs args){
		if(null == memcachedArgs){
			memcachedArgs = args;
		}
	}
	
	private static MemcachedClientBuilder builder = null;//new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedArgs.host+":"+memcachedArgs.port));
	
	private static MemcachedClient memcachedClient =  null;
	
	public static void getMemcachedClient(){
		
		if(null == builder){
			builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedArgs.host+":"+memcachedArgs.port));
		}
		if(memcachedClient==null){
			try {
				memcachedClient = builder.build();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
	
    /**
     * 往缓存中set值
     * @param key
     * @param o
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public static boolean set(String key , Object o) throws TimeoutException, InterruptedException, MemcachedException{
    	getMemcachedClient();
    	return memcachedClient.set(key, 0, o);  
    }
    
    /**
     * 从缓存中取值
     * @param key
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public static Object get(String key) throws TimeoutException, InterruptedException, MemcachedException{
    	getMemcachedClient();
    	return memcachedClient.get(key);
    }
    
    /**
     * 替换缓存中某个值
     * @param key
     * @param o
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public static boolean replace(String key ,Object o) throws TimeoutException, InterruptedException, MemcachedException{
    	getMemcachedClient();
    	return memcachedClient.replace(key, 0, o);
    }
    
    /**
     * 删除缓存中的某个值
     * @param key
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public static boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException{
    	getMemcachedClient();
    	return memcachedClient.delete(key);
    }
    
    /**
     * 遍历缓存中存在的所有key值
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
	@SuppressWarnings("deprecation")
	public static List<String> getKeyList() throws TimeoutException, InterruptedException, MemcachedException{
		getMemcachedClient();
		List<String> keyList = new ArrayList<String>();
		KeyIterator it=memcachedClient.getKeyIterator(AddrUtil.getOneAddress(memcachedArgs.host+":"+memcachedArgs.port));
		while(it.hasNext())
		{
		   String key=it.next();
		   keyList.add(key);
		}
		return keyList;
    }
}
