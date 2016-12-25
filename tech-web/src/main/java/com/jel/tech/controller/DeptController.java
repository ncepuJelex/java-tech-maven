package com.jel.tech.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.Document;

import com.jel.tech.common.date.DateUtils;
import com.jel.tech.common.json.JsonUtils;
import com.jel.tech.message.ResponseMessage;
import com.jel.tech.message.ResponseStatus;
import com.jel.tech.model.College;
import com.jel.tech.model.Dept;
import com.jel.tech.model.Tree;
import com.jel.tech.model.User;
import com.jel.tech.service.DeptService;

@Controller
@RequestMapping("/dept")
public class DeptController {

	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService deptService;
	
	@RequestMapping(value="/saveDept.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> saveCollege(@RequestBody College collegeAddForm, HttpServletRequest request) {
		ResponseMessage<String> msg = new ResponseMessage<>(); 
		
		Dept dept = new Dept();
		dept.setDeptName(collegeAddForm.getCollegeName());
		dept.setIcon("/tech-web/images/cart.gif");
		dept.setParentId(collegeAddForm.getParentId());
		dept.setRank((int)Math.ceil(Math.random()*100));
		
		try {
			deptService.saveCollege(dept);
			msg.setStatus(ResponseStatus.STATUS_OK);
			msg.setMsg("保存成功！");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("db error,sorry...");
		}
	}
	
	@ResponseBody
	@RequestMapping("/jstree.do")
	public ResponseMessage<List<Tree>> jstree() {
		
		ResponseMessage<List<Tree>> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		
		List<Tree> deptTreeList = deptService.queryDepts();
		
		message.setOthers(deptTreeList);
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/httpclient.do")
	public ResponseMessage<User> testApacheRest() {
		
		ResponseMessage<User> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		User user = new User();
		user.setBirth(new Date());
		user.setUserName("王宝强");
		message.setOthers(user);
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/datatable.do")
	public ResponseMessage<List<Dept>> datatable() {
		
		ResponseMessage<List<Dept>> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		
		List<Dept> queryDeptList = deptService.queryDeptList();
		
		message.setOthers(queryDeptList);
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/datatable2.do")
	public ResponseMessage<String> datatable2() {
		
		ResponseMessage<String> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		
		List<Dept> queryDeptList = deptService.queryDeptList();
		
		message.setOthers(JsonUtils.toJson(queryDeptList));
		System.out.println(JsonUtils.toJson(queryDeptList));
		return message;
	}
	
	/*@ResponseBody
	@RequestMapping("/datatable3.do")
	public String tableDemoAjax(@RequestParam String aoData) {

		JSONArray jsonarray = JSONArray.fromObject(aoData);

		String sEcho = null;
		int iDisplayStart = 0; // 起始索引
		int iDisplayLength = 0; // 每页显示的行数

		for (int i = 0; i < jsonarray.size(); i++) {
			JSONObject obj = (JSONObject) jsonarray.get(i);
			if (obj.get("name").equals("sEcho"))
				sEcho = obj.get("value").toString();

			if (obj.get("name").equals("iDisplayStart"))
				iDisplayStart = obj.getInt("value");

			if (obj.get("name").equals("iDisplayLength"))
				iDisplayLength = obj.getInt("value");
		}

		// 生成20条测试数据
		List<String[]> lst = new ArrayList<String[]>();
		for (int i = 0; i < 20; i++) {
			String[] d = { "co1_data" + i, "col2_data" + i };
			lst.add(d);
		}
		
		List<Dept> queryDeptList = deptService.queryDeptList();
		
		JSONObject getObj = new JSONObject();
		getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
		getObj.put("iTotalRecords", queryDeptList.size());//实际的行数
		getObj.put("iTotalDisplayRecords", queryDeptList.size());//显示的行数,这个要和上面写的一样
		
		getObj.put("aaData", queryDeptList.subList(iDisplayStart,iDisplayStart + iDisplayLength));//要以JSON格式返回
		return getObj.toString();
	}*/
	
	@RequestMapping(value="/upload.do", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,@RequestParam CommonsMultipartFile [] files) throws Exception {
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		
		for(int i=0; i<files.length; i++) {
			files[i].transferTo(new File(rootDirectory + System.currentTimeMillis()+files[i].getName() + ".jpg"));
		}
		return "index2";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajaxFileUpload.do", method=RequestMethod.POST)
	public String ajaxFileUpload(HttpServletRequest request,@RequestParam CommonsMultipartFile file) throws Exception {
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		File destFile = new File(rootDirectory +File.separator + DateUtils.format(new Date(), "yyyyMMdd")+File.separator+file.getFileItem().getName());
		logger.info("uploaded file saved destination:{}", destFile.getAbsolutePath());
		if(!destFile.exists()) {
			destFile.mkdirs();
		}
		file.transferTo(destFile);
		return "file upload succes!";
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadCollegeImg.do", method=RequestMethod.POST)
	public ResponseMessage<String> uploadCollegeImg(@RequestParam(value="collegeImg", required=false) MultipartFile collegeImg,HttpServletRequest request) {
		ResponseMessage<String> message = new ResponseMessage<>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String imgDir = request.getSession().getServletContext().getContextPath();
		System.out.println(rootDirectory);
		String imgName = collegeImg.getOriginalFilename();
		System.out.println(collegeImg);
		
		File f = new File("D:\\imgs\\" + imgName);
		
		try {
//			collegeImg.transferTo(new File("D:\\Download\\" + imgName));
			collegeImg.transferTo(f);
		} catch (Exception e) {
			message.setMsg("上传失败！");
			return message;
		}
		
		Dept dept = new Dept();
		dept.setIcon(imgDir + "/images/" + imgName);
		dept.setRank((int)Math.ceil(Math.random()*100));
		
		try {
			deptService.saveCollegeSelective(dept);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		return message;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/abcSocket.do")
	public ResponseMessage<String> abcSocket() throws Exception {
		System.out.println("xxxx");
//		DeptController controller = new DeptController();
//		controller.downloadSocket();
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		message.setOthers("成功了,success");
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value="/abcSocket2.do")
	public String abcSocket2() throws Exception {
		System.out.println("xxxx");
//		DeptController controller = new DeptController();
//		controller.downloadSocket();
		
		return "success";
	}
	
	public String downloadSocket() throws Exception{
		StringBuffer responseXml = new StringBuffer();
		Socket clientSocket = null;
		try {
			clientSocket = new Socket("202.108.144.40", 3422);
			OutputStream os = clientSocket.getOutputStream();
			
			String requestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
					"<ABCB2I>"+
					  "<Header>"+
					   " <InsuSerial>ABC00120161031105042</InsuSerial>"+
					  "  <TransDate>20161031</TransDate>"+
					   " <TransTime>105047</TransTime>"+
					  "  <BankCode>03</BankCode>"+
					  "  <CorpNo>1135</CorpNo>"+
					  "  <TransCode>1017</TransCode>"+
					 "   <TransSide>0</TransSide>"+
					"  </Header>"+
					"  <App>"+
					"    <Req>"+
					"      <TransFlag>1</TransFlag>"+
					"      <FileType>02</FileType>"+
					"      <FileName>POLICY1135.20161031</FileName>"+
					"      <FileLen>00000000</FileLen>"+
					"      <FileTimeStamp>2016-10-31 10:50:54.319</FileTimeStamp>"+
					"    </Req>"+
					"  </App>"+ 
					"</ABCB2I>";
					
					String msgHeader = "X1.0" + getMessageHeaderLen(requestXml, 8) + "1135    0 0 0                                        00000000";

			
			//1.发送保险公司对账请求
			logger.info("ABC Check Step1:send bill check request--------------");
			//String msgHeader = "X1.0" + getMessageHeaderLen(requestXml, 8) + "1135    0 0 0                                        00000000";
			os.write(msgHeader.getBytes());
			os.write(requestXml.getBytes());
			os.flush();
			
			//发送请求下载文件报文信息
			
			//2.获取下载文件长度
			logger.info("ABC Check Step2:Get File Length From Bank--------------");
			InputStream in = clientSocket.getInputStream();
			byte[] fileHeaders = new byte[12];
			this.readFull(fileHeaders, in);
			
			//如果文件长度为EEEEEEEEEEEE，表示银行没有生成文件，直接接收返回报文
			String fileHeader = new String(fileHeaders, "UTF-8");
		
			if(fileHeader.equals("EEEEEEEEEEEE")){
				clientSocket.shutdownInput();
				clientSocket.shutdownOutput();
				throw new IllegalArgumentException("农行文件未生成！");
			}
			
			int fileSize =Integer.parseInt(new String(fileHeaders, "UTF-8").trim());
			
			//3.发送接收文件确认标识
			logger.info("ABC Check Step3:Send Receive Identifying To Bank--------------");
			byte[] retuenOk = "0000".getBytes("UTF-8");
			os.write(retuenOk);
			os.flush();
			
			//4.接受银行对账结果明细信息
			logger.info("ABC Check Step4:Get Check Content From Bank--------------");
			byte[] messageBodys = this.readPackageBody(fileSize, os, in);
			
			//5.接受银行的对账处理结果新
			// 接收返回报文
			logger.info("ABC Check Step5:Get Check Result From Bank--------------");
			Document pInXml = receive(clientSocket);
			
			
			responseXml.append(fileHeader);
			responseXml.append(new String(messageBodys, "UTF-8"));
		} catch (Exception e) {
			throw e;
		}finally {
			 // 关闭所有连接  
			if(clientSocket != null){
				try {  
		            if (clientSocket.getOutputStream() != null)  
		            	clientSocket.getOutputStream().close();  
		        } catch (IOException e) {  
		        
		        }  
		        
		        try {  
		            if (clientSocket.getInputStream() != null)  
		            	clientSocket.getInputStream().close();  
		        } catch (IOException e) {  
		        
		        } 
				
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return responseXml.toString();
	}
	

	/**
	 * @param requestFile:报文头信息
	 * @return 12位长度的（不足前面补0）的报文头信息长度
	 */
	public String getMessageHeaderLen(String requestFile) {
		
		return this.getMessageHeaderLen(requestFile, 12);
	}
	
	/**
	 * @param requestFile:报文头信息
	 * @return 12位长度的（不足前面补0）的报文头信息长度
	 */
	public String getMessageHeaderLen(String requestFile, int length) {
		
		int strLen = String.valueOf(requestFile.getBytes().length).length();
		StringBuffer sb = new StringBuffer();
		while(sb.length() < length-strLen) {
			sb.append("0");
		}
		return sb.append(requestFile.getBytes().length).toString();
	}
	
	private byte[] readPackageBody(int fileSize, OutputStream os, InputStream in) throws Exception{
		byte[] checkBodys = new byte[fileSize];
		int packetSum = 0;
		byte[] packageHeader = null;
		
		while(true){
			//数据包接收完毕
			if(packetSum == fileSize){
				break;
			}
			
			packageHeader = new byte[12];
			readFull(packageHeader, in);
			
			int packetLen = Integer.parseInt(new String(packageHeader,"UTF-8").trim());
			byte[] filePacket = new byte[packetLen];
			readFull(filePacket, in);
			
			System.arraycopy(filePacket, 0, checkBodys, packetSum, packetLen);
			packetSum = packetSum + packetLen;
			
			//接收报文确认
		    byte[] packetReturn = new byte[4];
		    packetReturn = "0000".getBytes("UTF-8");
		    os.write(packetReturn);
		    os.flush();
		}
		
		return checkBodys;
	}
	
	private void readFull(byte[] pByte, InputStream pIs) throws IOException {
		for (int tReadSize = 0; tReadSize < pByte.length;) {
			int tRead = pIs.read(pByte, tReadSize, pByte.length - tReadSize);
			if (-1 == tRead) {
				throw new IOException("读取数据出错！实际读入长度：" + tReadSize);
			}
			
			tReadSize += tRead;
		}
	}
	
	public Document getDocument(InputStream is) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

			docBuilderFactory.setValidating(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(is);

			doc.getDocumentElement().normalize();
		} catch (Exception err) {
			// log.error("log002", err, new Object[0]);
			err.printStackTrace();
		}

		return doc;
	}
	
	public Document receive(Socket pSocket) throws Exception{
		logger.info("Into BatUtils.receive()...");
		Document mXmlDoc = null;
		InputStream cInputStream = null;
		try {
			cInputStream = pSocket.getInputStream();
			// 处理报文头
			byte[] mHeadBytes = new byte[73];
			
			readFull(mHeadBytes, cInputStream);
			String cType = new String(mHeadBytes, 0, 1).trim();
			logger.info("报文类型：" + cType);
			
			String cVersion = new String(mHeadBytes, 1, 3).trim();
			logger.info("版本号：" + cVersion);
			
			int mBodyLength = Integer.parseInt(new String(mHeadBytes, 4, 8).trim());
			logger.info("返回报文长度：" + mBodyLength);
			
			String cInsu = new String(mHeadBytes, 12, 8).trim();
			logger.info("保险公司代码：" + cInsu);
			
			String cEcryFlag = new String(mHeadBytes, 20, 1).trim();
			logger.info("加密标志：" + cEcryFlag);
			
			String cCmprsFlag = new String(mHeadBytes, 22, 1).trim();
			logger.info("压缩标志：" + cCmprsFlag);

			// 处理报文体
			byte[] mBodyBytes = new byte[mBodyLength];
			readFull(mBodyBytes, cInputStream);
			
			mXmlDoc = this.getDocument(new ByteArrayInputStream(mBodyBytes));
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("接收报文流时出错！",e);
		}
		
		logger.info("Into BatUtils.receive()...");
		return mXmlDoc;
	}
}
