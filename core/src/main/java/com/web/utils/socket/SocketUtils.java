package com.web.utils.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;

/**
 * Socket connection util
 * @author ZhaoDaping
 *
 */
public class SocketUtils {

	private Socket socket = null;
	private String host = "";
	private int port = 8080;
	private BufferedReader is = null;
	private PrintWriter os = null;
	private String delimiter = "";
	private int resLen = 0;
	private int soTimeout = 30000;
	
	public SocketUtils(String host, int port){
		this.host = host;
		this.port = port;
		getConnection(host,port,soTimeout);
	}
	
	public SocketUtils(String host, int port,int soTimeout){
		this.host = host;
		this.port = port;
		this.soTimeout = soTimeout;
		getConnection(host,port,soTimeout);
	}
	
	/**
	 * send message default use enter for delimter
	 * @param msg
	 * @return 10 error else success
	 */
	public String send(String msg){
		String response = null;
		try {
			os.print(msg);
			os.flush();
			if(StringUtils.isEmpty(delimiter) && resLen == 0){
				response = is.readLine();
			}else if(StringUtils.isNotEmpty(delimiter)){
				StringBuffer sb = new StringBuffer();
				char chars[] = new char[128];  
				int len = 0;
				int index =  0;
				String temp = "";
				 while ((len=is.read(chars)) != -1) {  
			         temp = new String(chars, 0, len);  
			         if ((index = temp.indexOf(delimiter)) != -1) {  
			            sb.append(temp.substring(0, index));  
			            break;  
			         }  
			         sb.append(new String(chars, 0, len));  
			      }
				 response = sb.toString();
			}else {
				char chars[] = new char[resLen];  
			    int len = is.read(chars);  
			    response = new String(chars, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	
	/**
	 * send message
	 * @param msg
	 * @return
	 */
	public String sendAndClose(String msg){
		String ret = send(msg);
		close();
		return ret;
	}
	
	public void close(){
		try {
			if(null != os)os.close();
			if(null != is)is.close();
			if(null != socket)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * get connection by host and port
	 * @param host
	 * @param port
	 * @param soTimeout
	 * @return print writer
	 */
	public PrintWriter getConnection(String host, int port, int soTimeout) {
		
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), 2000);
			if (0 == soTimeout) {
				soTimeout = 15000;
			}
			socket.setSoTimeout(soTimeout);
			os = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return os;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public BufferedReader getIs() {
		return is;
	}

	public void setIs(BufferedReader is) {
		this.is = is;
	}

	public PrintWriter getOs() {
		return os;
	}

	public void setOs(PrintWriter os) {
		this.os = os;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public int getResLen() {
		return resLen;
	}

	public void setResLen(int resLen) {
		this.resLen = resLen;
	}

}
