package com.groot.service;

import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.web.bo.FtpArgs;

/**
 * 获取公共配置文件服务
 * @author admin
 *
 */
@Service
public class PropertiesService {

	/* (non-Javadoc)
	 * @see com.hkrt.rpc.service.PropertiesService#getImageFtpArgs()
	 */
	public FtpArgs getImageFtpArgs(){
		ResourceBundle resource =ResourceBundle.getBundle("ftp");
		FtpArgs args = new FtpArgs();
		args.host=resource.getString("image.host");
		args.port=resource.getString("image.port");
		args.pw=resource.getString("image.pw");
		args.user=resource.getString("image.user");
		args.bufferSize=resource.getString("ftp.bufferSize");
		return args;
	}
	
//	public FtpArgs getBankStatementFtpArgs(){
//		ResourceBundle resource =ResourceBundle.getBundle("ftp");
//		FtpArgs args = new FtpArgs();
//		args.host=resource.getString("statement.host");
//		args.port=resource.getString("statement.port");
//		args.pw=resource.getString("statement.pw");
//		args.user=resource.getString("statement.user");
//		args.bufferSize=resource.getString("ftp.bufferSize");
//		args.path=resource.getString("statement.rootpath");
//		args.temp=resource.getString("statement.tmp");
//		return args;
//	}
//	
//	public FtpArgs getAbcBankStatementFtpArgs(){
//		ResourceBundle resource =ResourceBundle.getBundle("ftp");
//		FtpArgs args = new FtpArgs();
//		args.host=resource.getString("abc.statement.host");
//		args.port=resource.getString("abc.statement.port");
//		args.pw=resource.getString("abc.statement.pw");
//		args.user=resource.getString("abc.statement.user");
//		args.bufferSize=resource.getString("ftp.bufferSize");
//		args.path=resource.getString("abc.statement.rootpath");
//		args.temp=resource.getString("abc.statement.tmp");
//		return args;
//	}
	
	/* (non-Javadoc)
	 * @see com.hkrt.rpc.service.PropertiesService#getMailArgs()
	 */
//	public Mail getMailArgs(){
//		ResourceBundle resource = ResourceBundle.getBundle("email");
//		Mail args = new Mail();
//		args.setSenter(resource.getString("email.senter"));
//		args.setSenterHost(resource.getString("email.senterHost"));
//		args.setSenterUserName(resource.getString("email.senterUserName"));
//		args.setSenterPasword(resource.getString("email.senterPasword"));
//		return args;
//	}
	
	/* (non-Javadoc)
	 * @see com.hkrt.rpc.service.PropertiesService#getServerArgs()
	 */
//	public ServerArgs getServerArgs(){
//		ResourceBundle resource = ResourceBundle.getBundle("init");
//		ServerArgs args = new ServerArgs();
//		args.ctsRequestType = resource.getString("cts.requestType");
//		args.ctsRealmName  =  resource.getString("cts.realmName");
//		args.ctsPort  =  resource.getString("cts.port");
//		args.ctsName  =  resource.getString("cts.name");
//		args.mpsRequestType  =  resource.getString("mps.requestType");
//		args.mpsRealmName  =  resource.getString("mps.realmName");
//		args.mpsPort  =  resource.getString("mps.port");
//		args.mps_name  =  resource.getString("mps.name");
//		args.mtsRequestType  =  resource.getString("mts.requestType");
//		args.mtsRealmName  =  resource.getString("mts.realmName");
//		args.mtsPort  =  resource.getString("mts.port");
//		args.mtsName  =  resource.getString("mts.name");
//		args.mmsRequestType  =  resource.getString("mms.requestType");
//		args.mmsRealmName  =  resource.getString("mms.realmName");
//		args.mmsPort  =  resource.getString("mms.port");
//		args.mmsName  =  resource.getString("mms.name");
//		args.mssRequestType  =  resource.getString("mss.requestType");
//		args.mssRealmName  =  resource.getString("mss.realmName");
//		args.mssPort  =  resource.getString("mss.port");
//		args.mssName  =  resource.getString("mss.name");
//		return args;
//	}
	
	/* (non-Javadoc)
	 * @see com.hkrt.rpc.service.PropertiesService#getMemcachedArgs()
	 */
//	public MemcachedArgs getMemcachedArgs(){
//		ResourceBundle bundle = ResourceBundle.getBundle("memcached");
//		MemcachedArgs args = new MemcachedArgs();
//		args.host = bundle.getString("memcached.server.host");
//		args.port = bundle.getString("memcached.server.port");
//		//args.ChlCd = bundle.getString("memcached.ChlCd");
//		args.connectionPoolSize = bundle.getString("memcached.connectionPoolSize");
//		args.failureMode = bundle.getString("memcached.failureMode");
//		return args;
//	}
	
	/* (non-Javadoc)
	 * @see com.hkrt.rpc.service.PropertiesService#getRedisArgs()
	 */
//	public RedisArgs getRedisArgs(){
//		ResourceBundle bundle = ResourceBundle.getBundle("redis");
//		RedisArgs args = new RedisArgs();
//		args.maxActive = bundle.getString("redis.pool.maxActive");
//		args.maxIdle = bundle.getString("redis.pool.maxIdle");
//		args.maxWait = bundle.getString("redis.pool.maxWait");
//		args.testOnBorrow = Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow"));
//		args.testOnReturn = Boolean.valueOf(bundle.getString("redis.pool.testOnReturn"));
//		args.ip = bundle.getString("redis.ip");
//		args.port = bundle.getString("redis.port");
//		return args;
//	}
	
//	public ServerArgs getCypArgs(){
//		ResourceBundle bundle = ResourceBundle.getBundle("cyp");
//		ServerArgs args = new ServerArgs();
//		args.ip = bundle.getString("cyp.address");
//		args.port = bundle.getString("cyp.port");
//		return args;
//	}
//	
//	public ServerArgs getEncryptorpArgs(){
//		ResourceBundle bundle = ResourceBundle.getBundle("cyp");
//		ServerArgs args = new ServerArgs();
//		args.ip = bundle.getString("encryptor.ip");
//		args.port = bundle.getString("encryptor.port");
//		return args;
//	}
//	
//	public ServerArgs getSmsArgs(){
//		ResourceBundle bundle = ResourceBundle.getBundle("sms");
//		ServerArgs args = new ServerArgs();
//		args.ip = bundle.getString("sms.addr");
//		args.port = bundle.getString("sms.mercode");
//		args.pw = bundle.getString("sms.merpass");
//		args.name = bundle.getString("sms.mersign");
//		return args;
//	}
}
