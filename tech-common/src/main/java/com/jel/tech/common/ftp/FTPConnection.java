package com.jel.tech.common.ftp;

/**
 * 
 * @title FTP连接信息
 * @author Jelex
 * @version
 * @create_date 2017年1月7日
 * @modifyBy Jelex
 *
 */
public class FTPConnection {

	/**
	 * FTP连接的服务器地址名称
	 */
	private String hostName;
	/**
	 * 服务器端口
	 */
	private int port;
	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 登录密码
	 */
	private String password;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FTPConnection(String hostName, int port, String userName, String password) {
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	public FTPConnection() {
	}

}
