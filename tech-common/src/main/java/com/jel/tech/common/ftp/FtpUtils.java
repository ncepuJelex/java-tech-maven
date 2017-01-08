package com.jel.tech.common.ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * @title FTP工具类
 * @author Jelex
 * @version 
 * @create_date 2017年1月7日
 * @modifyBy Jelex
 *
 */
public class FtpUtils {

	private FtpUtils() {}
	
	/**
	 * 
	 * @title 下载文件到本地指定目录
	 * @author Jelex
	 * @create_date 2017年1月7日
	 * @param connection FTP连接信息
	 * @param remoteDir 远程下载文件所在目录
	 * @param remoteFileName 下载文件名称
	 * @param localPath 文件本地保存目录
	 * <p>
	 * 注：当使用不能的系统做FTP服务器时，需要做不同的设置，比如当使用windows系统做FTP服务器时，可以配置如下选项：
	 * 	<blockquote><pre>
	 	ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);</pre></blockquote>以支持中文，不会出现乱码;
		当使用UNIX系统做服务器时，则不能设置上面选项，相应的设置会有所不同。
	 * @return
	 */
	public static boolean downloadFile(FTPConnection connection, String remoteDir,String remoteFileName,String localPath) {
		
		FTPClient ftp = new FTPClient();
		/*
		 * During file transfers, the data connection is busy, but the control connection is idle.
		 *  FTP servers know that the control connection is in use, so won't close it 
		 *  through lack of activity, but it's a lot harder for network routers to know that the control
		 *  and data connections are associated with each other. Some routers may treat the control connection
		 *  as idle, and disconnect it if the transfer over the data connection takes longer than the allowable
		 *  idle time for the router. One solution to this is to send a safe command (i.e. NOOP) over the 
		 *  control connection to reset the router's idle timer.
		 * 	This will cause the file upload/download methods to send 
		 * 	a NOOP approximately every 5 minutes.
		 */
//		ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
		ftp.setControlEncoding("UTF-8");
		
		/*ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);*/
		
		boolean success = true;
		
		int reply;
		OutputStream os = null;
		try {
			ftp.connect(connection.getHostName(), connection.getPort());
			ftp.login(connection.getUserName(), connection.getPassword());
			
			System.out.println(MessageFormat.format("Connected to 【{0}】.", connection.getHostName()));
			System.out.println(ftp.getReplyString());
			// After connection attempt, you should check the reply code to verify success.
		    reply = ftp.getReplyCode();
		    /*
		     * The FTP server will send a positive completion response
		     *  on the final successful completion of a command. 
		     *  return true if a reply code is a postive completion response, false if not.
		     */
		    if(!FTPReply.isPositiveCompletion(reply)) {
		    	ftp.logout();
			    ftp.disconnect();
			    System.err.println("FTP server refused connection.");
//		      	System.exit(1);
			    return false;
		    }
		    /*
		     * transfer files
		     */
		    ftp.enterLocalPassiveMode();
		   
	        ftp.changeWorkingDirectory(remoteDir);//cd命令到FTP服务器下载目录 
		    FTPFile[] files = ftp.listFiles();
		    
		    for(FTPFile file : files) {
		    	if(file.getName().equalsIgnoreCase(remoteFileName)) {
		    		if(!localPath.endsWith(File.separator)) {
		    			localPath = localPath.concat(File.separator);
		    		}
		    		os = FileUtils.openOutputStream(new File(localPath.concat(remoteFileName)));
		    		success = ftp.retrieveFile(remoteFileName, os);
		    		
		    		System.out.println(MessageFormat.format("Saved to the local file path:{0},fileName:{1}", localPath, remoteFileName));
		    	}
		    }
		    //登出服务器
		    ftp.logout();
		    
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing.
				}
			}
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
//			System.exit(success ? 0 : 1);
		}
		return success;
	}
	
	/**
	 * 
	 * @title 下载文件以字符串形式返回
	 * @author Jelex
	 * @create_date 2017年1月7日
	 * @param connection FTP连接信息
	 * @param remoteDir 远程下载文件所在目录
	 * @param remoteFileName 下载文件名称
	 * @return 文件字符串
	 * <p>注：当使用不能的系统做FTP服务器时，需要做不同的设置，比如当使用windows系统做FTP服务器时，可以配置如下选项：
	 * 	<blockquote><pre>
	 	ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);</pre></blockquote>以支持中文，不会出现乱码;
		当使用UNIX系统做服务器时，则不能设置上面选项，相应的设置会有所不同。
	 */
	public static String downloadAsString(FTPConnection connection, String remoteDir,String remoteFileName) {
		
		FTPClient ftp = new FTPClient();
		/*FTPClientConfig config = new FTPClientConfig();
		config.setDefaultDateFormatStr("yyyy-MM-dd HH:mm:ss");
		config.setServerLanguageCode("zh"); //中文
		config.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftp.configure(config);*/
		
//		ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
		ftp.setControlEncoding("UTF-8");
		
		/*ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);*/
		
		int reply;
		String fileStr = null;
		try {
			ftp.connect(connection.getHostName(), connection.getPort());
			ftp.login(connection.getUserName(), connection.getPassword());
			
			System.out.println(MessageFormat.format("Connected to 【{0}】.", connection.getHostName()));
			System.out.println(ftp.getReplyString());
			// After connection attempt, you should check the reply code to verify success.
			reply = ftp.getReplyCode();
			/*
			 * The FTP server will send a positive completion response
			 *  on the final successful completion of a command. 
			 *  return true if a reply code is a postive completion response, false if not.
			 */
			if(!FTPReply.isPositiveCompletion(reply)) {
				ftp.logout();
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				return null;
			}
			/*
			 * transfer files
			 */
			ftp.enterLocalPassiveMode();
			
			ftp.changeWorkingDirectory(remoteDir);//cd命令到FTP服务器下载目录 
			FTPFile[] files = ftp.listFiles();
			
			for(FTPFile file : files) {
				if(file.getName().equalsIgnoreCase(remoteFileName)) {
					InputStream inputStream = ftp.retrieveFileStream(remoteFileName);
					fileStr = IOUtils.toString(inputStream,"GBK");
					if(fileStr != null) {
						System.out.println(MessageFormat.format("Downloaded file string length:{0}", fileStr.length()));
					}
				}
			}
			//登出服务器
			ftp.logout();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing.
				}
			}
		}
		return fileStr;
	}
	
	/**
	 * 
	 * @title 上传文件到FTP服务器
	 * @author Jelex
	 * @create_date 2017年1月7日
	 * @param connection:FTP连接信息
	 * @param remoteDir:远程目录(上传目的地)
	 * @param fileName:上传文件名
	 * @param in:上传文件流(上传内容以流的形式上传)
	 * @return 上传成功或失败(true OR false)
	 */
	public static boolean uploadFile(FTPConnection connection, String remoteDir,String fileName,InputStream in) {
		
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		config.setDefaultDateFormatStr("yyyy-MM-dd HH:mm:ss");
		config.setServerLanguageCode("zh"); //中文
		config.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftp.configure(config);
		/*
		 * During file transfers, the data connection is busy, but the control connection is idle.
		 *  FTP servers know that the control connection is in use, so won't close it 
		 *  through lack of activity, but it's a lot harder for network routers to know that the control
		 *  and data connections are associated with each other. Some routers may treat the control connection
		 *  as idle, and disconnect it if the transfer over the data connection takes longer than the allowable
		 *  idle time for the router. One solution to this is to send a safe command (i.e. NOOP) over the 
		 *  control connection to reset the router's idle timer.
		 * 	This will cause the file upload/download methods to send 
		 * 	a NOOP approximately every 5 minutes.
		 */
//		ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
		ftp.setControlEncoding("UTF-8");
		
		boolean success = true;
		
		int reply;
		try {
			ftp.connect(connection.getHostName(), connection.getPort());
			ftp.login(connection.getUserName(), connection.getPassword());
			
			System.out.println(MessageFormat.format("Connected to 【{0}】.", connection.getHostName()));
			System.out.println(ftp.getReplyString());
			// After connection attempt, you should check the reply code to verify success.
		    reply = ftp.getReplyCode();
		    /*
		     * The FTP server will send a positive completion response
		     *  on the final successful completion of a command. 
		     *  return true if a reply code is a postive completion response, false if not.
		     */
		    if(!FTPReply.isPositiveCompletion(reply)) {
		    	ftp.logout();
		    	ftp.disconnect();
		    	System.err.println("FTP server refused connection.");
//		      	System.exit(1);
		    	return false;
		    }
		    
		    ftp.enterLocalPassiveMode();
		    /*
		     * create working directory
		     */
		    success = createWorkingDir(ftp, remoteDir);
		    /*
		     * upload file through inputstream
		     */
		    if(success) {
		    	System.out.println(MessageFormat.format("Working Directory:【{0}】", ftp.printWorkingDirectory()));
		    	success = ftp.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"), in);
		    	System.out.println(MessageFormat.format("FTP Uploading Result:【{0}】", success));
		    }
		    //登出服务器
		    ftp.logout();

		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing.
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
		}
		return success;
	}
	
	/**
	 * 
	 * @title 创建上传的工作目录（如果不存在）并进入到该目录中(需要有读写权限)
	 * @author Jelex
	 * @create_date 2017年1月7日
	 * @param remoteDir
	 */
	private static boolean createWorkingDir(FTPClient ftp, String remoteDir) {
		
		if(remoteDir != null) {
			try {
				//cd命令到远程目录,返回成功或者失败
				if(!ftp.changeWorkingDirectory(remoteDir)) {
					/*if(remoteDir.startsWith("/")) {
						ftp.changeWorkingDirectory("/");
					}*/
					//把远程目录按层次拆开
					String[] dirs = remoteDir.split("/");
					for (int i = 0; i < dirs.length; i++) {
						//cd命令到子目录
//						if(!ftp.changeWorkingDirectory(dirs[i])) {
						if(!ftp.changeWorkingDirectory(new String(dirs[i].getBytes("UTF-8"),"iso-8859-1"))) {
							//创建子目录,返回成功或失败
							if(ftp.makeDirectory(dirs[i])) {
								ftp.changeWorkingDirectory(dirs[i]);
							} else {
								System.err.println(MessageFormat.format("failed to make directory:{0}", dirs[i]));
								return false;
							}
						}
					}
				}
				return true;
            } catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

}
