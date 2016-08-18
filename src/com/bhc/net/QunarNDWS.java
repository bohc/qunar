package com.bhc.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.bhc.test.HtmlParse;

/**
 * 百度贴吧的发帖及其回贴
 * 
 * @author Legend、
 *
 */
public class QunarNDWS {

	public static String reply(String content, String postsUrl, String cookie) throws ClientProtocolException, IOException {

		// 这是一些可有可无的头部信息，有的时候不需要，但是有的时候确需要，所以建议大家最好加上！
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", postsUrl);
		headers.put("Host", "user.qunar.com");
		headers.put("Cookie", cookie);

		// 这是从帖子中获取一些发帖时候必备的参数
		String html = EntityUtils.toString(SendRequest.sendGet(postsUrl, headers, null, "utf-8").getHttpEntity(), "utf-8");

		String needParametersResolve[] = HtmlParse.prase(html, "kw:'.+',ie:'utf-8',rich_text:'\\d+',floor_num:'\\d+',fid:'\\d+',tid:'\\d+',tfrom:'\\d+',user_type:'\\d+'").get(0).replaceAll("'", "").split(",");

		String floor_num = needParametersResolve[3].split(":")[1];
		String fid = needParametersResolve[4].split(":")[1];

		String tid = needParametersResolve[5].split(":")[1];
		String kw = needParametersResolve[0].split(":")[1];

		String vk_code = EntityUtils.toString(SendRequest.sendGet("http://tieba.baidu.com/f/user/json_vcode?lm=" + fid + "&rs10=2&rs1=0&t=0.5954980056343667", null, null, "utf-8").getHttpEntity(), "utf-8");
		String code = vk_code.split("\"")[7];
		String tbs = EntityUtils.toString(SendRequest.sendGet("http://tieba.baidu.com/dc/common/tbs?t=0.17514605234768638", headers, null, "utf-8").getHttpEntity(), "utf-8").split("\"")[3];

		// 这是下载验证码
		VerificationcCode.showGetVerificationcCode("http://tieba.baidu.com/cgi-bin/genimg?" + code, null, "e:/1.png");

		// 设置提交所有的参数
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("add_post_submit", "发 表 ");
		parameters.put("kw", kw);
		parameters.put("floor_num", floor_num);
		parameters.put("ie", "utf-8");
		parameters.put("rich_text", "1");
		parameters.put("hasuploadpic", "0");
		parameters.put("fid", fid);
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

		// 准备好一切，回帖
		Result res = SendRequest.sendPost("http://tieba.baidu.com/f/commit/post/add", headers, parameters, "utf-8");

		// 回帖之后百度会返回一段json，说明是否回帖成功
		return EntityUtils.toString(res.getHttpEntity(), "utf-8");
	}

	// 去哪儿登陆
	public static String testAccount(String name, String password) throws ClientProtocolException, IOException {
		// 这是下载验证码
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("https").setHost("user.qunar.com").setPath("/captcha/api/image").setParameter("k", "{en7mni(z").setParameter("p", "ucenter_login").setParameter("c", "ef7d278eca6d25aa6aec7272d57f0a9a")
					.setParameter("t", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Result ri = VerificationcCode.showGetVerificationcCode(uri, null, "e:/1.png");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("remember", "0");
		parameters.put("captcha", "show");
		parameters.put("captchaRand", "show");
		parameters.put("yahoo", "");
		parameters.put("ret", "https://tb2cadmin.qunar.com/home.jsp");
		parameters.put("loginType", "0");
		parameters.put("username", password);
		parameters.put("password", name);
		parameters.put("vcode", JOptionPane.showInputDialog(null, "<html><img src=\"file:///e:/1.png\" width=\33\" height=\55\"><br><center>请输入验证码</center><br></html>"));
		parameters.put("chkrem", "0");
		parameters.put("answer", "0");

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", "https://user.qunar.com/captcha/api/image");
		headers.put("Host", "user.qunar.com");
		headers.put("Cookie", ri.getCookie());
		Result r = SendRequest.sendPost("https://user.qunar.com/passport/loginx.jsp", headers, parameters, "utf-8");
		System.out.println(r.getStatusCode());

		HttpEntity entity = r.getHttpEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				int i = -1;
				byte[] b = new byte[1024];
				StringBuffer sb = new StringBuffer();
				while ((i = instream.read(b)) != -1) {
					sb.append(new String(b, 0, i, "utf-8"));
				}
				String content = sb.toString();
				System.out.println(content);
			} finally {
				instream.close();
			}
		}
		EntityUtils.consume(entity);

		String str = r.getCookie();
		return str;
	}

	// 去哪儿登陆
	public static String testAccount2(String name, String password) throws ClientProtocolException, IOException {
		// 这是下载验证码
		URI uri = null;// http://captcha1.pbs.qunar.com/api/image?k={g3njj(t&n=0.029391209789775885
		try {
			uri = new URIBuilder().setScheme("http").setHost("captcha1.pbs.qunar.com").setPath("/api/image").setParameter("k", "{g3njj(t").setParameter("n", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Long lflag = new Date().getTime();
		Result ri = VerificationcCode.showGetVerificationcCode(uri, null, "e:/1.png");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("remember", "0");
		parameters.put("username", name);
		parameters.put("password", password);
		parameters.put("vcode", JOptionPane.showInputDialog(null, "<html>" + "<img src=\"file:///e:/1.png\" width=\33\" height=\55\">" + "<br><center>请输入验证码</center><br></html>"));
		parameters.put("", "dujia.pro.qunar.com");
		parameters.put("", "/login/proxy.htm");
		parameters.put("", "crossDomainPost" + lflag);
		parameters.put("frameid", "crossDomainPost" + lflag);
		parameters.put("callbacktype", "1");
		parameters.put("ext", "pwdstrength");
		Map<String, String> headers = new HashMap<String, String>();
		//headers.put("Referer", "http://captcha1.pbs.qunar.com/api/image");
		//headers.put("Host", "captcha1.pbs.qunar.com");
		headers.put("Cookie", ri.getCookie());
		Result r = SendRequest.sendPost("http://user.qunar.com/webApi/logins.jsp", headers, parameters, "utf-8");
		System.out.println(r.getStatusCode());

		HttpEntity entity = r.getHttpEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				int i = -1;
				byte[] b = new byte[1024];
				StringBuffer sb = new StringBuffer();
				while ((i = instream.read(b)) != -1) {
					sb.append(new String(b, 0, i, "utf-8"));
				}
				String content = sb.toString();
				System.out.println(content);
			} finally {
				instream.close();
			}
		}
		EntityUtils.consume(entity);

		String str = r.getCookie();
		return str;
	}
	// 去哪儿登陆
	public static void getProxy(String cookie) throws ClientProtocolException, IOException {
		// 这是下载验证码
		URI uri = null;// http://captcha1.pbs.qunar.com/api/image?k={g3njj(t&n=0.029391209789775885
		try {
			uri = new URIBuilder().setScheme("http").setHost("captcha1.pbs.qunar.com").setPath("/api/image").setParameter("k", "{g3njj(t").setParameter("n", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Long lflag = new Date().getTime();
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		Result r = SendRequest.sendPost("http://dujia.pro.qunar.com", headers, parameters, "utf-8");
		System.out.println(r.getStatusCode());
		
		HttpEntity entity = r.getHttpEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				int i = -1;
				byte[] b = new byte[1024];
				StringBuffer sb = new StringBuffer();
				while ((i = instream.read(b)) != -1) {
					sb.append(new String(b, 0, i, "utf-8"));
				}
				String content = sb.toString();
				System.out.println(content);
			} finally {
				instream.close();
			}
		}
		EntityUtils.consume(entity);
	}

}
