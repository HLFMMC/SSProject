package com.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.IPUtil;
import com.JSONListFormat;
import com.web.admin.action.*;
//import com.web.admin.action.UserLogin;


/**
 * 
 * @author yc
 *
 */
@WebServlet(name = "webAction", urlPatterns = {"/webAction"}, loadOnStartup = 1)
public class WebAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());
	static Logger log = Logger.getLogger(WebAction.class.getName());
	
	@Override
	public void init() throws ServletException {
		try{
			System.out.println("===========================");
			System.out.println("web启动 ----成功");
			System.out.println("===========================");
		}catch(Exception e){
			System.out.println("===========================");
			System.out.println("web启动 ----失败");
			System.out.println("===========================");
			throw new ServletException();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		req.setCharacterEncoding("utf-8");
		
		String serverCode = req.getParameter("serverCode");
		if(serverCode == null)serverCode="";
	

    	String charset =req.getCharacterEncoding();
    	Enumeration<String> enumeration = req.getParameterNames();

    	while (enumeration.hasMoreElements()) {
			String str = enumeration.nextElement();
			String parameterStr = req.getParameter(str);
			if(charset==null){
				parameterStr = new String(parameterStr.getBytes("ISO-8859-1"), "UTF-8");
			}
			if(parameterStr.length()<50){
				System.out.println("[" + str + "]" + parameterStr);
			}else{
				System.out.println("[" + str + "][数据过长，只截取前50]" + parameterStr.substring(0, 50));
			}
			log.debug("[" + str + "]" + parameterStr);
		}
    	System.out.println("[IP]" + IPUtil.getIpAddress(req));
    	

		System.out.println(serverCode);
		try{
			
			if(   "userLogin".equals(serverCode)){new UserLogin().doPost(req, resp);}
			else if(   "userInfo".equals(serverCode)){new UserInfo().doPost(req, resp);}
			else if(   "userRegister".equals(serverCode)){new UserRegister().doPost(req, resp);}
			else if(   "userLogout".equals(serverCode)){new UserLogout().doPost(req, resp);}
			else if(   "userUpdate".equals(serverCode)){new UserUpdate().doPost(req, resp);}
			else if(   "uploadUserPic".equals(serverCode)){new UploadUserPic().doPost(req, resp);}
			
			//视频相关
			else if(   "myVideoList".equals(serverCode)){new MyVideoList().doPost(req, resp);}
			else if(   "myViewVideoList".equals(serverCode)){new MyViewVideoList().doPost(req, resp);}
			else if(   "myViewVideoAdd".equals(serverCode)){new MyViewVideoAdd().doPost(req, resp);}
			else if(   "videoList".equals(serverCode)){new VideoList().doPost(req, resp);}
			else if(   "saveVideoUpload".equals(serverCode)){new SaveVideoUpload().doPost(req, resp);}
			else if(   "uploadVideoPic".equals(serverCode)){new UploadVideoPic().doPost(req, resp);}
			else if(   "uploadVideo".equals(serverCode)){new UploadVideo().doPost(req, resp);}
			//悬赏
			else if(   "rewardAdd".equals(serverCode)){new RewardAdd().doPost(req, resp);}
			else if(   "rewardList".equals(serverCode)){new RewardList().doPost(req, resp);}
			else if(   "myRewardList".equals(serverCode)){new MyRewardList().doPost(req, resp);}
			
			//我的收藏
			else if(   "myCollectVideoList".equals(serverCode)){new MyCollectVideoList().doPost(req, resp);}
			else if(   "myCollectVideoAdd".equals(serverCode)){new MyCollectVideoAdd().doPost(req, resp);}
			else if(   "myCollectVideoDelete".equals(serverCode)){new MyCollectVideoDelete().doPost(req, resp);}
			
			else{
				JSONListFormat jFormat = new JSONListFormat();
				jFormat.setServerMsg("error - no serverCode");
				jFormat.setShowMsg("不存在该serverCode");
				PrintWriter out = resp.getWriter();
				out.println(jFormat.toString());
				out.close();
			}
			

			
		}catch(Exception e){
			logger.error(e.getMessage());
			JSONListFormat jFormat = new JSONListFormat();
			jFormat.setServerCode(serverCode);
			jFormat.setServerMsg("error--"+e.getMessage());
			jFormat.setShowMsg("系统出现异常，请联系系统管理员");
			PrintWriter out = resp.getWriter();
			out.println(jFormat.toString());
			out.close();
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
