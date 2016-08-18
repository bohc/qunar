
package com.bhc.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import com.bhc.net.Result;
import com.bhc.net.SendRequest;
import com.bhc.net.VerificationcCode;

/**
 * 百度贴吧的发帖及其回贴
 * @author Legend、
 *
 */
public class BaiduTieBaNDWS {
	
	public static  String reply(String content, String postsUrl,String cookie) throws ClientProtocolException, IOException {
		
		
		//这是一些可有可无的头部信息，有的时候不需要，但是有的时候确需要，所以建议大家最好加上！
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Referer", postsUrl);
		headers.put("Host", "tieba.baidu.com");
		headers.put("Cookie",cookie);
		
		//这是从帖子中获取一些发帖时候必备的参数
		String html = EntityUtils.toString(SendRequest.sendGet(postsUrl, headers, null, "utf-8").getHttpEntity(),"utf-8");
		
		String needParametersResolve[] = HtmlParse.prase(html, "kw:'.+',ie:'utf-8',rich_text:'\\d+',floor_num:'\\d+',fid:'\\d+',tid:'\\d+',tfrom:'\\d+',user_type:'\\d+'").get(0).replaceAll("'", "").split(",");
		
		String floor_num = needParametersResolve[3].split(":")[1];
    	String fid = needParametersResolve[4].split(":")[1];
    	
    	String tid = needParametersResolve[5].split(":")[1];
    	String kw =needParametersResolve[0].split(":")[1];
    	
    	
		String vk_code = EntityUtils.toString(SendRequest.sendGet("http://tieba.baidu.com/f/user/json_vcode?lm="+fid+"&rs10=2&rs1=0&t=0.5954980056343667", null, null, "utf-8").getHttpEntity(),"utf-8");
		String code = vk_code.split("\"")[7];
		String tbs = EntityUtils.toString(SendRequest.sendGet("http://tieba.baidu.com/dc/common/tbs?t=0.17514605234768638", headers, null, "utf-8").getHttpEntity(),"utf-8").split("\"")[3];
		
		//这是下载验证码
		VerificationcCode.showGetVerificationcCode("http://tieba.baidu.com/cgi-bin/genimg?"+code, null,"e:/1.png");
    	
    	//设置提交所有的参数
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("add_post_submit", "发 表 ");
		parameters.put("kw", kw);
		parameters.put("floor_num", floor_num);
		parameters.put("ie", "utf-8");
		parameters.put("rich_text", "1");
		parameters.put("hasuploadpic", "0");
		parameters.put("fid",fid);
		parameters.put("rich_text", "1");
		parameters.put("tid", tid);
		parameters.put("hasuploadpic", "0");
		parameters.put("picsign", "");
		parameters.put("quote_id", "0");
		parameters.put("useSignName", "on");
		parameters.put("content", content);
		parameters.put("vcode_md5", code);
		parameters.put("tbs", tbs);
		parameters.put("vcode", JOptionPane.showInputDialog(null, "<html><img src=\"file:///e:/1.png\" width=\33\" height=\55\"><br><center>请输入验证码</center><br></html>"));
		
		//准备好一切，回帖
		Result res = SendRequest.sendPost("http://tieba.baidu.com/f/commit/post/add", headers, parameters, "utf-8");
		
		//回帖之后百度会返回一段json，说明是否回帖成功
		return EntityUtils.toString(res.getHttpEntity(),"utf-8");
	}

	//百度登陆
	public static  String testAccount(String name, String password) throws ClientProtocolException, IOException {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("password", password);
		parameters.put("username", name);
		String str = SendRequest.sendPost("https://passport.baidu.com/?login", null, parameters,"utf-8").getCookie();
		return str;
	}
	
}

