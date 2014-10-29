package com.web.utils.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

import com.web.bo.AjaxMessage;
import com.web.bo.FtpArgs;
import com.web.utils.ExcelUtils;

public class FTPUtil {
	private static Logger logger = Logger.getLogger(FTPUtil.class.getName());

	private FTPClient ftpClient = null;

	private int max = 10;
	
	public FTPUtil(FtpArgs args){
		
		getFTPClient(args.host, args.pw,args.user, Integer.valueOf(args.port));
		// 设置PassiveMode传输
		ftpClient.enterLocalActiveMode();
		// 设置以二进制流的方式传输
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftpClient.setBufferSize(Integer.valueOf(args.bufferSize));
		logger.info(args.host);
		logger.info(args.port);
	}
	
	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public FTPClient getFTPClient(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		try {
			if(StringUtils.isEmpty(ftpPort+""))ftpPort=21;
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public String readConfigFileForFTP(String ftpUserName, String ftpPassword,
			String ftpPath, String ftpHost, int ftpPort, String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		FileInputStream inFile = null;
		InputStream in = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(fileName);
		} catch (FileNotFoundException e) {
			logger.info("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return "下载配置文件失败，请联系管理员.";
		} catch (SocketException e) {
			logger.info("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("文件读取错误。");
			e.printStackTrace();
			return "配置文件读取失败，请联系管理员.";
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
			} catch (IOException e) {
				logger.info("文件读取错误。");
				e.printStackTrace();
				return "配置文件读取失败，请联系管理员.";
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.info("in为空，不能读取。");
			return "配置文件读取失败，请联系管理员.";
		}
		return resultBuffer.toString();
	}
	
	/**
	 * 获取文件下名称列表
	 * @param ftpPath
	 * @return
	 */
	public String[] getNameList(String ftpPath){
		String[] fileList = new String[]{};
		try {
			ftpClient.changeWorkingDirectory("/");
			if(!"/".equals(ftpPath)){
				String path[] =ftpPath.split("/");
				for(String o:path){
					logger.info(ftpClient.printWorkingDirectory());
					boolean change = ftpClient.changeWorkingDirectory(o);
					if(!change){
						boolean make =ftpClient.makeDirectory(o);
						if(make){
							ftpClient.changeWorkingDirectory(o);
						}
					}
				}
			}
			fileList =ftpClient.listNames();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}
	
	/**
	 * 去 服务器的FTP路径下读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public AjaxMessage download(String ftpPath, String fileName,boolean skiphead,String charSet,boolean close) {
		AjaxMessage msg = new AjaxMessage();
		List<String> objs = new ArrayList<String>();
		msg.setIsSuccess(true);
		InputStream in = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF8"); // 中文支持
			/*FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
			conf.setServerLanguageCode("zh");
			ftpClient.configure(conf);*/
			//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory("/");
			if(!"/".equals(ftpPath)){
				String path[] =ftpPath.split("/");
				for(String o:path){
					logger.info(ftpClient.printWorkingDirectory());
					boolean change = ftpClient.changeWorkingDirectory(o);
					if(!change){
						boolean make =ftpClient.makeDirectory(o);
						if(make){
							ftpClient.changeWorkingDirectory(o);
						}else{
							msg.setValue("下载文件失败.");
							msg.setIsSuccess(false);
							return msg;
						}
					}
				}
			}
			in = ftpClient.retrieveFileStream(fileName);
			
			if (in != null) {
				BufferedReader br = new BufferedReader(new UnicodeReader(in, charSet));
				String data = null;
					if(skiphead){
						logger.info(br.readLine());
					}
					while ((data = br.readLine()) != null) {
						objs.add(data);
					}
					in.close();
					ftpClient.completePendingCommand();
			} else {
				logger.info("in为空，不能读取。");
				msg.setIsSuccess(false);
			}
		} catch (FileNotFoundException e) {
			logger.info("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			msg.setValue("下载文件失败."+e.getMessage());
			msg.setIsSuccess(false);
		} catch (SocketException e) {
			logger.info("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("文件读取错误。");
			e.printStackTrace();
			msg.setValue("下载文件失败."+e.getMessage());
			msg.setIsSuccess(false);
		}finally {
			try {
				if(close){
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		msg.setObj(objs);
		return msg;
	}
	
	
	/**
	 * 去 服务器的FTP路径下读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public AjaxMessage downloadExcel(String ftpPath, String fileName,boolean skiphead,String charSet,boolean close) {
		AjaxMessage msg = new AjaxMessage();
		List<String> objs = new ArrayList<String>();
		msg.setIsSuccess(true);
		InputStream in = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF8"); // 中文支持
			/*FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
			conf.setServerLanguageCode("zh");
			ftpClient.configure(conf);*/
			//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory("/");
			if(!"/".equals(ftpPath)){
				String path[] =ftpPath.split("/");
				for(String o:path){
					logger.info(ftpClient.printWorkingDirectory());
					boolean change = ftpClient.changeWorkingDirectory(o);
					if(!change){
						boolean make =ftpClient.makeDirectory(o);
						if(make){
							ftpClient.changeWorkingDirectory(o);
						}else{
							msg.setValue("下载文件失败.");
							msg.setIsSuccess(false);
							return msg;
						}
					}
				}
			}
			in = ftpClient.retrieveFileStream(fileName);
			
			if (in != null) {
				objs = ExcelUtils.read(in,skiphead);
				in.close();
				ftpClient.completePendingCommand();
			} else {
				logger.info("in为空，不能读取。");
				msg.setIsSuccess(false);
			}
		} catch (FileNotFoundException e) {
			logger.info("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			msg.setValue("下载文件失败."+e.getMessage());
			msg.setIsSuccess(false);
		} catch (SocketException e) {
			logger.info("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("文件读取错误。");
			e.printStackTrace();
			msg.setValue("下载文件失败."+e.getMessage());
			msg.setIsSuccess(false);
		}finally {
			try {
				if(close){
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		msg.setObj(objs);
		return msg;
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public AjaxMessage download(String ftpPath, String fileName,boolean skiphead) {
		String charSet = Charset.defaultCharset().name();
		return download(ftpPath,fileName,skiphead,charSet,true);
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public AjaxMessage download(String ftpPath, String fileName,boolean skiphead,String charSet) {
		return download(ftpPath,fileName,skiphead,charSet,true);
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public AjaxMessage download(String ftpPath, String fileName,boolean skiphead,boolean close) {
		String charSet = Charset.defaultCharset().name();
		return download(ftpPath,fileName,skiphead,charSet,close);
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public InputStream download(String ftpPath, String fileName) {
		InputStream in = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.cwd("/");
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			String path[] =ftpPath.split("/");
			for(String o:path){
				logger.info(ftpClient.printWorkingDirectory());
				boolean change = ftpClient.changeWorkingDirectory(o);
				if(!change){
					boolean make =ftpClient.makeDirectory(o);
					if(make){
						ftpClient.changeWorkingDirectory(o);
					}else{
						return in;
					}
				}
			}
			in = ftpClient.retrieveFileStream(fileName);
			return in;
		} catch (FileNotFoundException e) {
			logger.info("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return in;
		} catch (SocketException e) {
			logger.info("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("文件读取错误。");
			e.printStackTrace();
			return in;
		}
		return in;
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public File download(String ftpPath, String fileName,File file) {
		InputStream in = download(ftpPath,fileName);
		try {
			FileUtils.copyInputStreamToFile(in,file);
		} catch (IOException e) {
			e.printStackTrace();
			return file;
		} catch (Exception e){
			e.printStackTrace();
			return file;
		}
		return file;
	}
	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public boolean upload(String remotePath, String remoteFileName, String fileContent,
			String writeTempFielPath) {
		return upload(remotePath, remoteFileName, fileContent, writeTempFielPath,true);
	}

	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public boolean upload(String remotePath, String remoteFileName, String fileContent,
			String writeTempFielPath,boolean close) {
		logger.info("开始上传文件到FTP.");
		try {
			// FTPFile[] files = ftpClient.listFiles(new
			// String(remoteFileName));
			// 先把文件写在本地。在上传到FTP上最后在删除	
			boolean writeResult = write(remoteFileName, fileContent,
					writeTempFielPath);
			if (writeResult) {
				File f = new File(writeTempFielPath + "/" + remoteFileName);
				InputStream in = new FileInputStream(f);
				
				if(!upload(remotePath,remoteFileName,in,false)){
					return false;
				}
				logger.info("上传文件" + remoteFileName + "到FTP成功!");
				in.close();
				f.delete();
			} else {
				logger.info("写文件失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(close)ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean upload(String remotePath, String remoteFileName, InputStream in,boolean close) {

		logger.info("开始上传文件到FTP.");
		try {
			ftpClient.cwd("/");
			ftpClient.setControlEncoding("UTF-8");
			String path[] =remotePath.split("/");
			for(String o:path){
				logger.info(ftpClient.printWorkingDirectory());
				boolean change = ftpClient.changeWorkingDirectory(o);
				if(!change){
					boolean make =ftpClient.makeDirectory(o);
					if(make){
						ftpClient.changeWorkingDirectory(o);
					}else{
						return false;
					}
				}
			}
			
			long start=System.currentTimeMillis();
			boolean result = ftpClient.storeFile(remoteFileName, in);
			long end=System.currentTimeMillis();
			logger.info("上传文件" + remoteFileName + "到FTP,时间:"+(end-start)+"毫秒,结果:"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				in.close();
				if(close)ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	
	}
	
	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public boolean upload(String remotePath, String remoteFileName, InputStream in) {
		return upload(remotePath,remoteFileName,in,true);
	}
	
	
	/**
	 * 把配置文件先写到本地的一个文件中取
	 * 
	 * @param ftpPath
	 * @param str
	 * @return
	 */
	public boolean write(String fileName, String fileContext,
			String writeTempFielPath) {
		try {
			logger.info("开始写配置文件");
			File fo = new File(writeTempFielPath);
			if(!fo.exists()){
				fo.mkdirs();
			}
			File f = new File(writeTempFielPath + "/" + fileName);
			if (!f.exists()) {
				if (!f.createNewFile()) {
					logger.info("文件不存在，创建失败!");
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.write(fileContext.replaceAll("\n", "\r\n"));
			bw.flush();
			bw.close();
			return true;
		} catch (Exception e) {
			logger.info("写文件没有成功");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 关闭ftp服务器
	 * @return
	 */
	public boolean close(){
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 校验文件是否存在
	 * @param path
	 * @param fileName
	 * @return
	 */
	public boolean isExist(String path,String fileName){
		return isExist(path,fileName,3);
	}
	
	/**
	 * 校验文件是否存在
	 * 0 startwith 1 endwith 2 contains 3 equals
	 * @param path
	 * @param fileName
	 * @return
	 */
	public boolean isExist(String path,final String key,final int flag){
		FTPFileFilter f = new FTPFileFilter() {
			@Override
			public boolean accept(FTPFile file) {
				// TODO Auto-generated method stub
				if(flag==0){
					if(file.getName().startsWith(key)){
						return true;
					}
				}
				if(flag==1){
					if(file.getName().endsWith(key)){
						return true;
					}
				}
				if(flag==2){
					if(file.getName().contains(key)){
						return true;
					}
				}
				if(flag==3){
					if(file.getName().equals(key)){
						return true;
					}
				}
				return false;
			}
		};
		
		try {
			FTPFile[] files = ftpClient.listFiles(path,f);
			if(files.length != 0){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除文件
	 * @return
	 */
	public boolean deleteFile(String path,String fileName,boolean close){

		logger.info("开始删除文件夹.");
		boolean result = false;
		try {
			result =ftpClient.deleteFile(path+"/"+fileName);
			logger.info("result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(close){
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	
	}
	
	/**
	 * 删除文件
	 * @return
	 */
	public boolean deleteFile(String path,String fileName){
		return deleteFile(path,fileName,true);
	}
	
	/**
	 * 删除文件夹  只支持文件夹下没有子文件
	 * @return
	 */
	public boolean removeDirectory(String path){
		logger.info("开始删除文件夹.");
		boolean result = false;
		try {
			FTPFile[] files = ftpClient.listFiles(path);
			for (int i = 0; i < files.length; i++) {
				FTPFile file=files[i];
				ftpClient.deleteFile(path+"/"+file.getName());
			}
			
			 result =ftpClient.removeDirectory(path);
			logger.info("删除结果"+result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static void main(String[] args) {
		FtpArgs fargs = new FtpArgs();
		fargs.host="192.168.0.140";
		fargs.user="ftpclg";
		fargs.pw="clg123";
		fargs.port="2021";
		fargs.bufferSize="1048576";
		FTPUtil ftp = new FTPUtil(fargs);
		try {
			/*AjaxMessage msg = ftp.download("manage/top", "note.txt", true, false);
			System.out.println(ftp.getNameList("manage/top"));
			ftp.ftpClient.cwd("/");
			ftp.ftpClient.changeWorkingDirectory("manage");
			ftp.ftpClient.changeWorkingDirectory("top");
			ftp.ftpClient.cwd("/");
			System.out.println(ftp.ftpClient.printWorkingDirectory());*/
			//System.out.println(ftp.getNameList("manage/top").length);
			System.out.println(ftp.isExist("manage/top", ".t1x", 2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
