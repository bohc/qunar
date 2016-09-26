package com.bhc.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bhc.Main;
import com.bhc.bean.Day;
import com.bhc.bean.DupRecords;
import com.bhc.bean.Line;
import com.bhc.bean.Summary;
import com.bhc.bean.Team;
import com.bhc.bean.Vehicle;
import com.bhc.test.HttpUtil;
import com.bhc.util.BaseIni;
import com.bhc.util.CharacterSetToolkit;
import com.bhc.util.ComparatorLine;
import com.bhc.util.FileManger;
import com.bhc.util.FileUtil;
import com.bhc.util.ImageUtils;
import com.bhc.util.ParseHtml;

public class HttpLogin {
	private boolean downover = true;
	private String realpath = "";
	private String validatecode = "";
	private String tempimg = "";
	private CloseableHttpClient httpclient = null;
	private String valCode = "";
	private boolean loginstatus = false;
	public static String cookie = "";
	public static List<Cookie> cookies = new ArrayList<Cookie>();
	private Line line;
	private Header firstheader;
	private Main m;
	private String pid = "", supperid = "";
	private boolean isup = false;
	private ParseXmlDo pxd;
	private ParseXmlDoQly pxQLy;
	private ParseXmlDoNew pxdnew;
	private String dstrs;

	public HttpLogin(Main m) {
		super();
		realpath = System.getProperty("user.dir");// user.dir指定了当前的路径
		pxd = new ParseXmlDo();
		pxdnew = new ParseXmlDoNew();
		pxQLy = new ParseXmlDoQly();
		// getHttpClient();
		getHttpClientSSL();
		this.m = m;
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 10; i++) {
				Random rd = new Random();
				int rdm = rd.nextInt(6);
				System.out.println(rdm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					keepAlive = 60 * 1000;
				}
				return keepAlive;
			}
		};

		System.setProperty("javax.net.ssl.trustStore", FileUtil.currentDirectory() + "/jssecacerts");
		SSLSocketFactory sslsf = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File(FileUtil.currentDirectory() + "/jssecacerts"));
			try {
				trustStore.load(instream, "changeit".toCharArray());
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();

			sslsf = new SSLSocketFactory(sslcontext, SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestConfig globalconfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();

		httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(globalconfig).setKeepAliveStrategy(keepAliveStrat).setConnectionManager(cm).build();
	}

	public void getHttpClientSSL() {
		httpclient = HttpUtil.getHttpClient();
	}

	public void getVimage() {
		updateUI("开始获取验证码");
		URI uri = null;
		try {
			// uri = new
			// URIBuilder().setScheme("http").setHost("captcha1.pbs.qunar.com").setPath("/api/image").setParameter("k",
			// "{g3njj(t").setParameter("t", String.valueOf(new
			// Date().getTime())).build();
			uri = new URIBuilder().setScheme("https").setHost("user.qunar.com").setPath("/captcha/api/image").setParameter("k", "{en7mni(z").setParameter("p", "ucenter_login")
					.setParameter("c", "ef7d278eca6d25aa6aec7272d57f0a9a").setParameter("t", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpGet httpget = createHttpGet(uri);
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					validatecode = realpath + "/v.jpg";
					writImage(readInputStream(instream), validatecode);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			// valCode = new OCR().recognizeText(new File(validatecode), "jpg");
			updateUI("获取验证码完毕");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("获取验证码出错");
		} finally {
		}
	}

	// 去哪儿登陆
	public void loginForm2() throws ClientProtocolException, IOException {
		// 这是下载验证码
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("captcha1.pbs.qunar.com").setPath("/api/image").setParameter("k", "{g3njj(t").setParameter("n", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Long lflag = new Date().getTime();
		Result ri = VerificationcCode.showGetVerificationcCode(uri, null, realpath + "/1.png");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("remember", "1");
		parameters.put("username", m.line.getUsername());
		parameters.put("password", m.line.getPassword());
		parameters.put("loginType", "0");

		parameters.put("vcode", JOptionPane.showInputDialog(null, "<html><img src=\"file:///" + realpath + "/1.png\" width=\33\" height=\55\" id=\"vcodeImg\"><br><center>请输入验证码</center><br></html>"));
		updateUI(parameters.get("vcode"));
		parameters.put("", "dujia.pro.qunar.com");
		parameters.put("", "/login/proxy.htm");
		parameters.put("", "crossDomainPost" + lflag);
		parameters.put("frameid", "crossDomainPost" + lflag);
		parameters.put("callbacktype", "1");
		parameters.put("ext", "pwdstrength");

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", ri.getCookie());
		String purl = "http://user.qunar.com/webApi/logins.jsp";
		purl = "https://user.qunar.com/passport/loginx.jsp";
		Result r = SendRequest.sendPost(purl, headers, parameters, "utf-8");

		if (r.getStatusCode() == 200) {
			cookie = r.getCookie();
			cookies.clear();
			for (Cookie c : r.getCookies()) {
				Browser.setCookie(c.getName() + "=" + c.getValue(), "http://tb2cadmin.qunar.com");
				cookies.add(c);
			}
			for (Cookie c : ri.getCookies()) {
				Browser.setCookie(c.getName() + "=" + c.getValue(), "http://tb2cadmin.qunar.com");
				cookies.add(c);
			}
			if (cookie != null && cookie.length() > 0) {
				loginstatus = true;
				getSupperid();
			}
			// Map<String,Header> m=r.getHeaders();
		}
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

	// 去哪儿登陆
	public void loginForm3() throws ClientProtocolException, IOException {
		// 这是下载验证码‎‎
		/// 得到QN1=O5cLNle6mN4SPnH9RoVaAg==; expires=Thu, 31-Dec-37 23:55:55 GMT;
		// domain=qunar.com; path=/
		List<Header> hs = new ArrayList<Header>();
		Map<String, Object> pm = new HashMap<String, Object>();
		String url = "", html = "", urls = "";

		url = "https://user.qunar.com/passport/login.jsp";
		html = HttpUtil.doPostSSL(url, null, pm);
		urls = ParseHtml.parseImgCode(html);
		Map<String, String> ms = ParseHtml.URLRequest(urls);
		urls = ms.get("c");

		// 得到QN25=4cc0c32c-703b-4f1f-8599-87d9105aaec8-9f992f90;
		// Domain=.qunar.com; Path=/
		url = "https://user.qunar.com/captcha/api/image";
		pm.clear();
		pm.put("k", "{en7mni(z");
		pm.put("p", "ucenter_login");
		pm.put("c", urls);
		pm.put("t", String.valueOf(new Date().getTime()));
		HttpUtil.doPostSSL(url, null, pm);

		// 得到JSESSIONID=FFE3404E8FA2987DF51EF69E9CE853D7; Path=/; HttpOnly
		url = "https://rmcsdf.qunar.com/js/df.js";
		pm.clear();
		pm.put("orgId", "ucenter.login");
		pm.put("js_type", "0");
		html = HttpUtil.doPostSSL(url, null, pm);
		urls = ParseHtml.parseJsCode(html);

		// 得到_i=ueHd8L9YbOoXnY3XPEY3wwfOEuoX; Domain=.qunar.com; Expires=Sat,
		// 09-Sep-2084 09:31:10 GMT; Path=/
		// 得到_vi=vQpYffyLHk8N4rCqSjkLKw-qF9H4RI3B-TeHhF3Px3uc9A1a0dqy3IUOdR4ssZ5ywfmH595jYBzBD5x7TlXDwIhH6u1bBg1k9DH7Fd0lM0ho7XxyXMAPl8LcSfsABGgP21RvMyEzMHGrYIssc-S_Ow-M25pQLmY63i5MAnYpYxF4;Path=/;Domain=.qunar.com;Expires=Sun,
		// 20 Nov 2016 06:17:03 GMT;HTTPOnly
		url = "https://user.qunar.com/passport/addICK.jsp";
		pm.clear();
		pm.put("ssl", "");
		html = HttpUtil.doPostSSL(url, null, pm);

		// 更新JSESSIONID=9A615B9DF900B89152D7A07AC9008F8F; Path=/; HttpOnly
		url = "https://rmcsdf.qunar.com/js/device.js";
		pm.clear();
		pm.put("orgId", "ucenter.login");
		pm.put("sessionId", urls);
		HttpUtil.doPostSSL(url, null, pm);

		// 更新模拟验证用户名
		url = "https://rmcsdf.qunar.com/api/device/challenge.json";
		pm.clear();
		pm.put("callback", "callback_" + new Date().getTime());
		pm.put("sessionId", urls);
		pm.put("domain", "qunar.com");
		pm.put("orgId", "ucenter.login");
		html = HttpUtil.doPostSSL(url, null, pm);
		System.out.println(html);

		url = "https://user.qunar.com/passport/loginx.jsp";
		pm.clear();
		pm.put("remember", "1");
		pm.put("username", m.line.getUsername());
		pm.put("password", m.line.getPassword());
		pm.put("loginType", "0");
		pm.put("vcode", JOptionPane.showInputDialog(null, "<html><img src=\"file:///" + realpath + "/1.png\" width=\33\" height=\55\" id=\"vcodeImg\"><br><center>请输入验证码</center><br></html>"));
		updateUI(pm.get("vcode").toString());
		pm.put("ret", "https://tb2cadmin.qunar.com/");
		hs = HttpUtil.createHeader();
		String r = HttpUtil.doPostSSL(url, hs, pm);
		System.out.println(r);
	}

	// 去哪儿登陆
	public void loginForm4() throws ClientProtocolException, IOException {
		cookie = m.line.getCookiestr().trim();
		String[] scs = cookie.split(";");
		for (String str : scs) {
			Browser.setCookie(str, "http://tb2cadmin.qunar.com");
		}
		loginstatus = true;
	}

	@SuppressWarnings("deprecation")
	public void loginForm() {
		updateUI("开始初始化登录数据");
		String loginurl = "http://user.qunar.com/passport/loginx.jsp";
		// loginurl = "https://user.qunar.com/passport/loginx.jsp";
		try {
			HttpPost httpost = createHttpPost(loginurl);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("remember", "0"));
			nvps.add(new BasicNameValuePair("captcha", "show"));
			nvps.add(new BasicNameValuePair("captchaRand", "show"));
			nvps.add(new BasicNameValuePair("yahoo", ""));
			nvps.add(new BasicNameValuePair("ret", "https://tb2cadmin.qunar.com/home.jsp"));
			nvps.add(new BasicNameValuePair("loginType", "0"));
			nvps.add(new BasicNameValuePair("username", m.line.getUsername()));
			nvps.add(new BasicNameValuePair("password", m.line.getPassword()));
			nvps.add(new BasicNameValuePair("vcode", valCode));
			nvps.add(new BasicNameValuePair("chkrem", "0"));
			// nvps.add(new BasicNameValuePair("answer",
			// "tcxg7Nmq5CICJih99suh8B_wCn4Je-B-3VvdCdnqd2YLPmwZNIuhXxkuInSGc6ODXx71Nl5gjzFRqz7U74uh6RlqdaYG8eg0Nsc_3QluwC4ChPbSXwOa6pFi7bTG06wBXc-f0hUr3y4JanqSNksyqoljCPzJqzrSqptyaoVs3O5D5-vJQtbr74muw24DBig7NIehXxkuTPTGdCfJRVbreoVs3OZCbP6-_pcyN9_fbfFYeKOCYZ7tVplqdyoJreOJUF6q4AEc2roSja0S7kugA5EqyiVXtObGYgOsBh5x5y1CebaSFo0hY1pqvGIEs0bG"));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			updateUI("执行登录");
			HttpResponse response = httpclient.execute(httpost);

			Browser.clearSessions();
			Browser.setCookie("Cookie", cookie);
			// Header[] hs = response.getHeaders("Set-Cookie");
			// Header[] ths = new Header[hs.length + ghs.length];
			int i = 0;
			// for (; i < ghs.length; i++) {
			// ths[i] = ghs[i];
			// String[] temcookie = ghs[i].getValue().split(";");
			// for (int f = 0; f < temcookie.length; f++) {
			// Browser.setCookie(temcookie[f], "http://tb2cadmin.qunar.com");
			// }
			// System.out.println("已发送" + ths[i]);
			// }
			// for (int j = i; j < ths.length; j++) {
			// ths[j] = hs[j - i];
			// String[] temcookie = hs[j - i].getValue().split(";");
			// for (int f = 0; f < temcookie.length; f++) {
			// Browser.setCookie(temcookie[f], "http://tb2cadmin.qunar.com");
			// }
			// System.out.println(ths[j]);
			// }
			// ghs = ths.clone();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					if (m.line.isHttpmodel()) {
						if (!content.equals("")) {
							updateUI(content);
							String tstr = content.substring(content.indexOf("ret"));
							tstr = tstr.substring(0, tstr.indexOf(","));
							if (tstr.endsWith("true")) {
								loginstatus = true;
								getSupperid();
								updateUI("登录完成");
							} else {
								updateUI("登录失败" + content);
							}
						} else {
							updateUI("登录失败" + content);
						}
					} else if (m.line.isHttpsmodel()) {
						if (content.equals("")) {
							loginstatus = true;
							getSupperid();
							updateUI("登录完成");
						} else {
							updateUI("登录失败" + content);
						}
					}
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// http://user.qunar.com//webApi/proxy.jsp
	// /supplier/flashUpload.action
	public String postFile(String ifile) {
		String iurl = "";
		if ((iurl = postFileHttps(ifile)) != null) {
			return iurl;
		}

		if (!loginstatus) {
			updateUI("请先登录");
		} // https://tb2cadmin.qunar.com/supplier/flashUpload.action?sourceType=2
		String url = "https://tb2cadmin.qunar.com/supplier/flashUpload.action";
		String filePath = "F://cwb/aec379310a5.jpg";
		if (ifile != null) {
			filePath = ifile;
		}

		HttpPost httpPost = createHttpPost(url);

		MultipartEntity reqEntity = new MultipartEntity();
		httpPost.setEntity(reqEntity);

		/** file param name */
		FileBody bin = new FileBody(new File(filePath));
		reqEntity.addPart("Filedata", bin);
		try {
			/** String param name */
			// reqEntity.addPart("userId", new StringBody(supperid));
			reqEntity.addPart("sourceType", new StringBody("2"));

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				String imgurl = inputStream2String(responseEntity.getContent());

				String ss = imgurl.substring(imgurl.indexOf("\"ret\":") + "\"ret\":".length());
				ss = ss.substring(0, ss.indexOf(","));
				if (ss.equals("1")) {
					ss = imgurl.substring(imgurl.indexOf("\"imgUrl\":\"") + "\"imgUrl\":\"".length());
					ss = ss.substring(0, ss.indexOf(",") - ",".length());
					updateUI("图片:" + imgurl + "，上传完成");
					return ss;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String postFileHttps(String ifile) {
		if (!loginstatus) {
			updateUI("请先登录");
		} // https://tb2cadmin.qunar.com/supplier/flashUpload.action?sourceType=2
			// https://tb2cadmin.qunar.com/supplier/flashUpload.action?sourceType=2&ie=false
		String url = "https://tb2cadmin.qunar.com/supplier/flashUpload.action";
		String filePath = "F://cwb/aec379310a5.jpg";
		if (ifile != null) {
			filePath = ifile;
		}

		HttpPost httpPost = createHttpPost(url);

		MultipartEntity reqEntity = new MultipartEntity();
		httpPost.setEntity(reqEntity);

		/** file param name */
		FileBody bin = new FileBody(new File(filePath));
		reqEntity.addPart("Filedata", bin);
		try {
			/** String param name */
			reqEntity.addPart("sourceType", new StringBody("2"));
			String response = HttpClientUtil.postFile(httpPost, "UTF-8");
			if (response != null) {
				String imgurl = response;

				String ss = imgurl.substring(imgurl.indexOf("\"ret\":") + "\"ret\":".length());
				ss = ss.substring(0, ss.indexOf(","));
				if (ss.equals("1")) {
					ss = imgurl.substring(imgurl.indexOf("\"imgUrl\":\"") + "\"imgUrl\":\"".length());
					ss = ss.substring(0, ss.indexOf("\""));
					updateUI("图片:" + imgurl + "，上传完成");
					return ss;
				} else {
					updateUI(ss);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void getSourceLine(String url) {
		if (m.line.getCselect().equals("新网站")) {
			pxdnew.getHurl(url);
			line = pxdnew.getLine();
		} else if (m.line.getCselect().equals("qly网站")) {
			pxQLy.getHurl(url);
			line = pxQLy.getLine();
		} else {
			pxd.getHurl(url);
			line = pxd.getLine();
		}
	}

	public void postLine() {
		String teamno = line.getSummary().getTeamno();
		if (teamno == null || teamno.trim().length() == 0) {
			updateUI("团号不能为空");
			return;
		}
		teamno = teamno.trim();
		updateUI(teamno + "\t" + pid);
		if (m.line.isBtc1()) {
			boolean oup = false;// 记录修改前是不是属于上架状态
			searchExistLine(teamno, "warehouse", 1);
			if (pid.equals("")) {
				searchExistLine(teamno, "sell", 1);
				if (!pid.equals("")) {
					oup = true;
					if (m.line.isLbc() || m.line.isLdc() || m.line.isLoc()) {
						// downLine();//下架产品
					}
				}
			}

			if (m.line.isLbc()) {
				postLineBase();
			}
			if (m.line.isLdc()) {
				postLineDays();
			}
			if (m.line.isLpc()) {
				postLinePrice();
			}
			if (m.line.isLoc()) {
				postLineMarker();
			}
			if (m.line.isLic()) {
				// 只针对自由行
				// 产品详情里面的酒店信息和线路特色
				// postLineInfoDetail();
				// 产品详情里面的交通
				postLineTraffic();
			}
			if (m.line.isC1up()) {
				upLine();
			} else if (oup) {
				// upLine();
			}
		} else if (m.line.isBtc2()) {
			if (m.line.isC2up()) {
				searchExistLine(line.getSummary().getTeamno(), "warehouse", 1);
				upLine();
			} else if (m.line.isC2down()) {
				searchExistLine(line.getSummary().getTeamno(), "sell", 1);
				downLine();
			} else if (m.line.isC2del()) {
				searchExistLine(line.getSummary().getTeamno(), "warehouse", 1);
				if (pid.equals("")) {
					searchExistLine(line.getSummary().getTeamno(), "sell", 1);
				}
				delLine();
			} else if (m.line.isC2dn()) {
				getExistLines(teamno);
			} else if (m.line.isBtc2ac()) {
				searchExistLine(line.getSummary().getTeamno(), "sell", 1);
				if (!pid.equals("")) {
					cancelAct();
					postActInfo();
				}
			} else if (m.line.isBtc2acc()) {
				searchExistLine(line.getSummary().getTeamno(), "warehouse", 1);
				if (pid.equals("")) {
					searchExistLine(line.getSummary().getTeamno(), "sell", 1);
				}
				cancelAct();
			} else if (m.line.isLimitcountchk()) {
				searchExistLine(line.getSummary().getTeamno(), "warehouse", 1);
				if (pid.equals("")) {
					searchExistLine(line.getSummary().getTeamno(), "sell", 1);
				}
				setLineLimiteCount();
			}
		}
		pid = "";
	}

	public void postimgtest() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (!loginstatus) {
						updateUI("请先登录");
						return;
					}
					int i = 0;
					postFileHttps("F:/bohc/workspace/qunar/tempimg.jpg");
					i++;
					updateUI("上传完成第\t" + i + "\t张");
				} catch (Exception e) {
					updateUI("上传出错了:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}).start();
	}

	public String postLineBase() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}

		Summary summary = line.getSummary();

		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			updateUI("开始准备上传数据");
			HttpPost httpost = createHttpPost(loginurl);

			String pfunction = summary.getPfunction().trim();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 取得目的地
			StringBuffer arrive = new StringBuffer("");
			// 新网站和老网站用的是这个，已经被注释，不能再用
			// String[] ta = summary.getArrive().split(",");
			// QLY网站用的这个
			String[] ta = summary.getArrivecity().split("--");
			if (ta != null && ta.length > 0) {
				int i = 0;
				String pstr = "";

				if (m.line.getCselect().equals("旧网站") || m.line.getCselect().equals("qly网站")) {
					for (String s : ta) {
						try {
							if (!s.trim().equals("")) {
								if (i == 0) {
									i++;
									pstr = s.trim();
								}
								// String ts = pstr.trim() + "-" + s.trim();
								String tjson = getDestAreaInfo(s.trim());
								Map obj = readJson(tjson);
								List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) obj.get("result");
								StringBuffer sf = new StringBuffer("");
								for (LinkedHashMap<String, Object> ls : list) {
									String faname = ls.get("fullarrive").toString().trim();
									String dname = ls.get("display").toString().trim();
									String cname = ls.get("c").toString().trim();
									String id = ls.get("id").toString().trim();
									if (cname.length() - s.length() > 4) {
										continue;
									}
									if (!faname.startsWith(pstr) && i != 1) {
										continue;
									}
									i++;
									if (faname.indexOf(pstr.trim()) != -1) {
										if ((cname.indexOf(s.trim()) != -1 || s.trim().indexOf(cname.trim()) != -1) && ((cname.length() - s.length()) <= 3 || s.length() - cname.length() <= 3)) {
											if (sf.toString().equals("")) {
												sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
											} else {
												sf.append("," + id + "_@_" + dname + "_@_0_@_" + faname);
											}
											if (cname.trim().equals(s.trim())) {
												sf.setLength(0);
												sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
												break;
											}
										}
									}
								}
								if (!sf.toString().trim().equals("")) {
									if (arrive.toString().equals("")) {
										arrive.append(sf);
									} else {
										arrive.append("," + sf);
									}
									sf.setLength(0);
								}
							}
						} catch (Exception e) {
							updateUI(e.getMessage());
						}
					}
				} else if (m.line.getCselect().equals("新网站")) {
					for (String s : ta) {
						try {
							if (!s.trim().equals("")) {
								pstr = s.trim();
								// String ts = pstr.trim() + "-" + s.trim();
								String tjson = getDestAreaInfo(s.trim());
								Map obj = readJson(tjson);
								List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) obj.get("result");
								StringBuffer sf = new StringBuffer("");
								for (LinkedHashMap<String, Object> ls : list) {
									String faname = ls.get("fullarrive").toString().trim();
									String dname = ls.get("display").toString().trim();
									String cname = ls.get("c").toString().trim();
									String id = ls.get("id").toString().trim();

									if (!cname.equals(pstr)) {
										continue;
									}
									if (sf.toString().equals("")) {
										sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
									} else {
										sf.append("," + id + "_@_" + dname + "_@_0_@_" + faname);
									}
									if (cname.trim().equals(s.trim())) {
										sf.delete(0, sf.length());
										sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
										break;
									}
								}
								if (!sf.toString().trim().equals("")) {
									if (arrive.toString().equals("")) {
										arrive.append(sf);
									} else {
										arrive.append("," + sf);
									}
									sf.delete(0, arrive.length());
								}
							}
						} catch (Exception e) {
							updateUI(e.getMessage());
						}
					}
				} else {
					return null;
				}

			}
			updateUI(arrive.toString());

			// 取得出发地
			String departure = "";
			String dstr = summary.getDeparture();
			if (dstr != null && !dstr.trim().equals("")) {
				dstr = dstr.trim();
				String tjson = getStartAreaInfo(dstr.trim());
				tjson = tjson.substring(tjson.indexOf("(") + 1, tjson.lastIndexOf(")"));
				Map obj = readJson(tjson);
				List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) obj.get("result");
				for (LinkedHashMap<String, Object> ls : list) {
					String faname = ls.get("display").toString();
					String id = ls.get("id").toString();
					if (faname.indexOf(dstr.trim()) != -1 || dstr.trim().indexOf(faname.trim()) != -1) {
						departure = id + "_@_" + faname;
						break;
					}
				}
				updateUI(departure);
			}
			// 主图
			// String mainpic = "";
			// 图库
			String images = "[";

			String img = summary.getImage();
			String[] imgs = img.split(",");
			Random r = new Random();
			int rm = r.nextInt(imgs.length);
			if (imgs != null) {
				if (m.line.isC1nomain()) {
					for (int i = 0; i < imgs.length; i++) {
						// 如果调用本地主图，那么就不用网络上的主图
						if (m.line.isMainpicselect()) {
							rm = -1;
						}
						try {
							String iurl = "";
							String suffix = imgs[i].substring(imgs[i].lastIndexOf("."));
							tempimg = realpath + "/tempimg" + suffix;
							downLoadImage(imgs[i].trim());
							while (true) {
								if (downover) {
									break;
								}
								Thread.sleep(1 * 1000);
							}

							iurl = postFile(tempimg);
							if (i == 0) {
								if (rm == i) {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"1\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com"
											+ iurl + "\"}";
								} else {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"0\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com"
											+ iurl + "\"}";
								}
								nvps.add(new BasicNameValuePair("zhutu", rm + ""));
							} else {
								images += ",";
								if (rm == i) {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"1\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com"
											+ iurl + "\"}";
								} else {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"0\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com"
											+ iurl + "\"}";
								}
							}
							// if (rm == i) {
							// mainpic = iurl;
							// }
						} catch (Exception e) {
							updateUI(e.getMessage());
						}
					}

					// 只有在修改图片的时候才有用
					if (m.line.isMainpicselect()) {
						String mpic = m.line.getMainpictxt();
						List<String> tlist = new ArrayList<String>();
						listFiles(mpic, tlist);
						Random rd = new Random();
						int rdm = rd.nextInt(tlist.size());
						String tfpath = tlist.get(rdm);
						File tf = new File(tfpath);
						if (tf.exists()) {
							String iurl = postFile(tfpath);
							images += ",{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"1\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com" + iurl
									+ "\"}";
							// mainpic = iurl;
						}
					}
				}
			}
			images += "]";
			updateUI(images);

			if (pid.equals("")) {
				nvps.add(new BasicNameValuePair("method", "addProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "true"));
				nvps.add(new BasicNameValuePair("ret", ""));
				// nvps.add(new BasicNameValuePair("mainpic", mainpic));
				nvps.add(new BasicNameValuePair("images", images));
			} else {
				nvps.add(new BasicNameValuePair("method", "updateProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "false"));
				nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
				if (m.line.isC1nomain()) {
					// nvps.add(new BasicNameValuePair("mainpic", mainpic));
					nvps.add(new BasicNameValuePair("images", images));
				}
			}

			nvps.add(new BasicNameValuePair("next", "false"));
			nvps.add(new BasicNameValuePair("supperid", supperid));
			nvps.add(new BasicNameValuePair("id", pid));

			nvps.add(new BasicNameValuePair("p_function", pfunction));
			nvps.add(new BasicNameValuePair("arrive", arrive.toString()));
			nvps.add(new BasicNameValuePair("departure", departure));
			nvps.add(new BasicNameValuePair("main_pic_desc", ""));
			nvps.add(new BasicNameValuePair("image_desc", ""));
			nvps.add(new BasicNameValuePair("b2bFlag", ""));

			// 产品名称
			nvps.add(new BasicNameValuePair("title", concatStr(m.line.getSummary().getTitle().trim(), "{title}", summary.getTitle())));
			// 团号
			nvps.add(new BasicNameValuePair("team_no", summary.getTeamno().trim()));
			// 行程天数
			nvps.add(new BasicNameValuePair("day", summary.getDay().trim()));
			// 入住晚数
			nvps.add(new BasicNameValuePair("night", String.valueOf(Integer.parseInt(summary.getDay().trim()) - 1)));
			// 主题
			String linesubject = summary.getLinesubject();
			if (linesubject != null) {
				String[] strs = linesubject.split(", ");
				for (int i = 0; i < strs.length; i++) {
					if (i >= 3) {
						break;
					}
					String s = strs[i];
					nvps.add(new BasicNameValuePair("product_topic", s));
				}
			}

			// 自然日or工作日
			String advancedaytype = "realday", at = "";
			if (m.line.isAdck()) {
				at = m.line.getSummary().getAdvancedaytype().trim();
				// 提前报名
				nvps.add(new BasicNameValuePair("advance_day", m.line.getSummary().getAdvanceday().trim()));
			} else {
				at = summary.getAdvancedaytype().trim();
				// 提前报名
				nvps.add(new BasicNameValuePair("advance_day", summary.getAdvanceday().trim()));
			}
			if (at.equals("工作日")) {
				advancedaytype = "workday";
			}

			nvps.add(new BasicNameValuePair("advance_day_type", advancedaytype));
			nvps.add(new BasicNameValuePair("advance_hour", m.line.getSummary().getAdvancehour()));
			nvps.add(new BasicNameValuePair("advance_minute", m.line.getSummary().getAdvanceminute()));
			nvps.add(new BasicNameValuePair("advanceDesc", m.line.getSummary().getAdvancedesc()));

			/**
			 * 这一块现在已经不用传了 // 类别 String type = ""; String ttype =
			 * summary.getArrivetype().trim(); if (ttype.equals("国内游") ||
			 * ttype.equals("周边旅游")) { type = "0"; } else if
			 * (ttype.equals("出境游")) { type = "1"; } else { type = "2"; } //
			 * 现在是判断，只要是86开头的地方，都是国内游 if (line.getSummary().getPlaceid() != null
			 * && line.getSummary().getPlaceid().startsWith("86")) { type = "0";
			 * } nvps.add(new BasicNameValuePair("type", type));
			 **/

			/**
			 * 这一块现在已经不用传了 // 长/短途 String distancetype = ""; String dtype =
			 * summary.getDistancetype().trim(); if (dtype.equals("长途")) {
			 * distancetype = "0"; } else { distancetype = "1"; } nvps.add(new
			 * BasicNameValuePair("distance_type", distancetype));
			 */

			// 大交通
			nvps.add(new BasicNameValuePair("totraffic", summary.getFreetriptotraffic().trim() + "去"));
			nvps.add(new BasicNameValuePair("backtraffic", summary.getFreetripbacktraffic().trim() + "回"));

			// 下架日期
			if (m.line.isDowndatecheck2()) {
				// 开始日期
				nvps.add(new BasicNameValuePair("display_way", "1"));
				SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat edf = new SimpleDateFormat("dd.MM.yyyy");
				try {
					nvps.add(new BasicNameValuePair("publish_time", fdf.format(m.line.getDown_date_begin())));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("publish_time", edf.format(m.line.getDown_date_begin())));
				}
				// 截止日期
				try {
					nvps.add(new BasicNameValuePair("expire_time", fdf.format(m.line.getDown_date_end())));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("expire_time", edf.format(m.line.getDown_date_end())));
				}
			} else {
				// 发团日前下架0,指定日期上下架1
				nvps.add(new BasicNameValuePair("display_way", "0"));
				nvps.add(new BasicNameValuePair("publish_time", ""));
				nvps.add(new BasicNameValuePair("expire_time", ""));
			}

			if (m.line.isPayck()) {
				// 付款方式,即时支付0,确认后支付1
				if (m.line.getSummary().isPayway1()) {
					nvps.add(new BasicNameValuePair("pay_way", "0"));
				} else if (m.line.getSummary().isPayway2()) {
					nvps.add(new BasicNameValuePair("pay_way", "1"));
				} else if (m.line.getSummary().isPayway3()) {
					nvps.add(new BasicNameValuePair("pay_way", "4"));
				} else {
					nvps.add(new BasicNameValuePair("pay_way", "0"));
				}
			} else {
				nvps.add(new BasicNameValuePair("pay_way", "0"));
			}
			// 上下架状态,on+sale上架，offline下架
			nvps.add(new BasicNameValuePair("status", "offline"));
			// 服务电话
			nvps.add(new BasicNameValuePair("phoneinfo", m.line.getPhonenum()));

			/**
			 * 这一块现在已经不用传了 // 产品副标题 nvps.add(new
			 * BasicNameValuePair("recommendation",
			 * concatStr(m.line.getSummary().getRecommendation().trim(),
			 * "{title}", summary.getRecommendation())));
			 */

			if (pfunction.trim().equals("group")) {
				nvps.add(new BasicNameValuePair("group_departure", departure));

				if (m.line.isPromise_ironclad_group()) {
					nvps.add(new BasicNameValuePair("promise_ironclad_group", "on"));
				}
				if (m.line.isPromise_no_self_pay()) {
					nvps.add(new BasicNameValuePair("promise_no_self_pay", "on"));
				}
				if (m.line.isPromise_no_shopping()) {
					nvps.add(new BasicNameValuePair("promise_no_shopping", "on"));
				}
				if (m.line.isPromise_truthful_description()) {
					nvps.add(new BasicNameValuePair("promise_truthful_description", "on"));
				}
				if (m.line.isGroup_method0()) {
					nvps.add(new BasicNameValuePair("group_method", "0"));
				} else {
					nvps.add(new BasicNameValuePair("group_method", "1"));
				}
			} else if (pfunction.trim().equals("free")) {
				if (m.line.isPromise_guarantee_go()) {
					nvps.add(new BasicNameValuePair("promise_guarantee_go", "on"));
				}
				if (m.line.isPromise_booking_current_day()) {
					nvps.add(new BasicNameValuePair("promise_booking_current_day", "on"));
				}
				if (m.line.isPromise_truthful_description_free()) {
					nvps.add(new BasicNameValuePair("promise_truthful_description", "on"));
				}
				if (m.line.isPromise_refund_anytime_not_consume()) {
					nvps.add(new BasicNameValuePair("promise_refund_anytime_not_consume", "on"));
				}

				nvps.add(new BasicNameValuePair("free_type", "1"));
				nvps.add(new BasicNameValuePair("first_date", "2013-10-22"));
				nvps.add(new BasicNameValuePair("last_date", "2013-10-26"));
				// 自由行类别
				nvps.add(new BasicNameValuePair("freetrip_traffic", "on"));
				nvps.add(new BasicNameValuePair("freetrip_hotel", "on"));
				nvps.add(new BasicNameValuePair("freetrip_othername", ""));
				// 旅客信息
				nvps.add(new BasicNameValuePair("need_traveller_info", "1"));
				// 提前预订设置
				nvps.add(new BasicNameValuePair("advance_day_new", "0"));
				nvps.add(new BasicNameValuePair("advance_hour", "23"));
				nvps.add(new BasicNameValuePair("advance_minute", "59"));
				// 提前预订描述
				nvps.add(new BasicNameValuePair("advanceDesc", "请在当天的23点59分前预订"));
				// 出发地*
				nvps.add(new BasicNameValuePair("departure", concatStr(m.line.getSummary().getDeparture(), "{title}", summary.getDeparture().trim())));
				// 目的地*
				nvps.add(new BasicNameValuePair("freeArrive", "云南-昆明"));
				// 利润率
				nvps.add(new BasicNameValuePair("profit", "0"));
			}

			for (NameValuePair bp : nvps) {
				System.out.println(bp.getName() + ":" + bp.getValue());
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			updateUI("上传数据准备完成");
			updateUI("执行上传数据功能");

			HttpResponse response = httpclient.execute(httpost);

			HttpEntity entity = response.getEntity();
			String content = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					content = sb.toString();
					updateUI("" + content);
					pid = content.substring(content.indexOf("id=") + "id=".length(), content.lastIndexOf("&ret"));
				} catch (Exception e) {
					updateUI(e.getMessage());
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("基础数据上传完成");
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送HTTPS POST请求
	 * 
	 * @param 要访问的HTTPS地址
	 *            POST访问的参数Map对象 返回响应值
	 */
	public String sendHttpsRequestByPost(String url, Map<String, String> params) {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}

			public void verify(String arg0, SSLSocket arg1) throws IOException {
			}

			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
			}

			public void verify(String arg0, X509Certificate arg1) throws SSLException {
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			HttpPost httpPost = createHttpsPost(url);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				updateUI(responseContent);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	// 产品详情里面的交通信息（自由行）
	private void postLineTraffic() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String url = "http://tb2cadmin.qunar.com/supplier/traffic/add_traffic.qunar";
		try {
			updateUI("开始整理自由行产品详情里的交通数据数据");
			// HttpPost httpost = createHttpPost(loginurl);
			// List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			Map<String, String> gomap = new HashMap<String, String>();
			gomap.put("transfer", "0");
			gomap.put("traffic_type", "flight");
			gomap.put("dep_city", "");
			gomap.put("arr_city", "");
			gomap.put("number", "HU7311");
			gomap.put("arr_time_flag", "0");
			gomap.put("flight_cabin", "-1");

			JSONArray goarray = new JSONArray();
			goarray.put(gomap);

			Map<String, String> backmap = new HashMap<String, String>();
			backmap.put("transfer", "0");
			backmap.put("traffic_type", "flight");
			backmap.put("dep_city", "");
			backmap.put("arr_city", "");
			backmap.put("number", "HU7112");
			backmap.put("arr_time_flag", "0");
			backmap.put("flight_cabin", "-1");

			JSONArray backarray = new JSONArray();
			backarray.put(backmap);

			JSONObject json = new JSONObject();
			json.put("go", goarray);
			json.put("back", backarray);

			// updateUI(json.toString());

			// nvps.add(new BasicNameValuePair("traffic_info",
			// json.toString()));
			// nvps.add(new BasicNameValuePair("pId", pid));

			Map<String, String> m = new HashMap<String, String>();
			m.put("pId", pid);
			m.put("traffic_info", json.toString());

			// sendHttpsRequestByPost(url, m);

			String re = com.bhc.util.https.HttpClientUtil.doPost(url, m, "UTF-8", createHttpsPost(url));
			/**
			 * httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			 * HttpResponse response = httpclient.execute(httpost); HttpEntity
			 * entity = response.getEntity(); String content = ""; if (entity !=
			 * null) { InputStream instream = entity.getContent(); try { int i =
			 * -1; byte[] b = new byte[1024]; StringBuffer sb = new
			 * StringBuffer(); while ((i = instream.read(b)) != -1) {
			 * sb.append(new String(b, 0, i, "utf-8")); } content =
			 * sb.toString(); updateUI("" + content); } catch (Exception e) {
			 * updateUI(e.getMessage()); } finally { instream.close(); } }
			 * EntityUtils.consume(entity);
			 */
			updateUI("自由行产品详情的交通数据上传完成:" + re);
		} catch (Exception e) {
			updateUI("自由行产品详情的交通数据上传失败:" + e.getMessage());
		}
	}

	// 产品详情里面的酒店信息和线路特色（自由行）
	private void postLineInfoDetail() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			// 数据上架
			updateUI("开始整理产品详情数据");
			HttpPost httpost = createHttpPost(loginurl);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "updateProductFreeTrip"));
			nvps.add(new BasicNameValuePair("next", "false"));
			nvps.add(new BasicNameValuePair("pId", pid));
			nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
			nvps.add(new BasicNameValuePair("newFlag", "false"));
			nvps.add(new BasicNameValuePair("b2bFlag", "0"));
			nvps.add(new BasicNameValuePair("tranfer", "no"));
			nvps.add(new BasicNameValuePair("tranfer", "no"));

			// 线路特色
			combineFeature("feature", nvps);
			// 酒店信息
			combineHotels(nvps);

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String content = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					content = sb.toString();
					updateUI("" + content);
					pid = content.substring(content.indexOf("id=") + "id=".length(), content.lastIndexOf("&ret"));
				} catch (Exception e) {
					updateUI(e.getMessage());
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("产品详情数据上传完成");
		} catch (Exception e) {
			updateUI("产品详情数据上传出错");
			e.printStackTrace();
		}
	}

	// http://hs.qunar.com/api/hs/dujiahotel/typeahead?city_code=lijiang&hotel=丽江碧波&removeCallbackValue:callback&_:1448855652858
	// 拼装酒店信息，因为自由行和跟团游都有，但不在一个地方上传
	private void combineHotels(List<NameValuePair> nvps) {
		List<Day> list = line.getDays();
		for (int i = 0, j = 0; i < list.size(); i++) {
			Day d = list.get(i);
			if (d.getStarname().indexOf("酒店") == -1) {
				continue;
			}

			String cname = d.getStardesc();
			if (cname == null)
				return;
			int ind = -1;
			if ((ind = cname.indexOf("：")) == -1) {
				return;
			}
			String hname = cname.substring(ind + 1);
			String[] hos = hname.split(";|；");
			if (hos.length > 0) {
				hname = hos[0].replace("或同级", "");
			}
			String recityname = getQunarCityCode(cname.substring(0, ind));
			cname = analysisCityJson(recityname, cname.substring(0, ind));
			String rhotel = getQunarHotelInfo(cname, hname);
			String hcode = "";
			String hotelseq = "", citycode = "", cityname = "", address = "", grade = "", notes = "", baidupoint = "", googlepoint = "", enname = "", isdomestic = "", tradingarea = "";
			try {
				if (rhotel != null) {
					JSONObject jobj = new JSONObject(rhotel);
					JSONArray ja = jobj.getJSONArray("data");
					JSONObject tjo = null;
					for (int f = 0; f < ja.length(); f++) {
						tjo = ja.getJSONObject(i);
						if (tjo.getString("name").trim().equals(hname)) {
							break;
						}
					}
					if (tjo == null) {
						continue;
					}

					hcode = tjo.getString("hotel_seq");
					String dhoteljson = getQunarHotelDetailInfo(hcode);
					if (dhoteljson == null) {
						continue;
					}

					JSONObject jor = new JSONObject(dhoteljson);
					JSONObject jo = jor.getJSONArray("data").getJSONObject(0);
					hotelseq = jo.getString("hotelSeq");
					citycode = jo.getString("cityCode");
					cityname = jo.getString("name");
					address = jo.getString("address");
					grade = jo.getString("grade");
					notes = jo.getString("notes");
					baidupoint = jo.getString("baiduPoint");
					googlepoint = jo.getString("googlePoint");
					enname = jo.getString("enName");
					isdomestic = jo.getString("isDomestic");
					tradingarea = jo.getString("tradingArea");
				}
			} catch (Exception e) {
				continue;
			}

			j++;
			nvps.add(new BasicNameValuePair("hotel_" + j, "1"));
			nvps.add(new BasicNameValuePair("hotelimages_" + j, ""));
			nvps.add(new BasicNameValuePair("hotelSeq_" + j, hotelseq));
			nvps.add(new BasicNameValuePair("hotelFeature_" + j, "热闹"));
			nvps.add(new BasicNameValuePair("city_code_" + j, citycode));
			nvps.add(new BasicNameValuePair("baidu_point_" + j, baidupoint));
			nvps.add(new BasicNameValuePair("google_point_" + j, googlepoint));
			nvps.add(new BasicNameValuePair("isDomestic_" + j, isdomestic));
			nvps.add(new BasicNameValuePair("hotelcity_" + j, tradingarea));
			nvps.add(new BasicNameValuePair("hotelname_" + j, cityname));
			nvps.add(new BasicNameValuePair("hotelenglishname_" + j, enname));
			nvps.add(new BasicNameValuePair("hoteladdress_" + j, address));
			nvps.add(new BasicNameValuePair("hotelPhone_" + j, ""));
			nvps.add(new BasicNameValuePair("book_day_" + j, "1"));
			nvps.add(new BasicNameValuePair("star_" + j, ""));
			nvps.add(new BasicNameValuePair("grade_" + j, grade));
			nvps.add(new BasicNameValuePair("bedtype_" + j, "大床"));
			nvps.add(new BasicNameValuePair("roomtype_" + j, "5"));
			nvps.add(new BasicNameValuePair("rto_name_" + j, ""));
			nvps.add(new BasicNameValuePair("rto_english_name_" + j, ""));
			nvps.add(new BasicNameValuePair("breakfast_" + j, "2"));
			nvps.add(new BasicNameValuePair("breakfast_desc_" + j, "1"));
			nvps.add(new BasicNameValuePair("internet_" + j, "2"));
			nvps.add(new BasicNameValuePair("internet_desc_" + j, "1"));
			nvps.add(new BasicNameValuePair("hoteldesc_" + j, notes));
			nvps.add(new BasicNameValuePair("rn", ""));
			nvps.add(new BasicNameValuePair("ftext_" + j, ""));
			// 只取三组住宿信息
			if (j >= 3) {
				break;
			}
		}
	}

	// 拼装产品特色，因为自由行和跟团游都有，但不在这个地方上传
	private void combineFeature(String key, List<NameValuePair> nvps) {
		Summary summary = line.getSummary();
		// 产品特色
		List<String> flist = summary.getFeature();
		StringBuffer st = new StringBuffer("");
		if (flist != null && flist.size() > 0) {
			st.append("<p>");
			for (String sfu : flist) {
				while (true) {
					if (sfu.indexOf("-") == -1) {
						break;
					}
					sfu = sfu.substring(0, sfu.indexOf("-")) + "<br/>" + sfu.substring(sfu.indexOf("-") + 1);
				}

				// 以//n为换行符
				StringBuffer sbs = new StringBuffer();
				for (int i = 0; i < sfu.length(); i++) {
					sbs.append(sfu.substring(i, i + 1));
					if (sbs.toString().endsWith("\\\\n")) {
						sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>");
					}
				}
				sfu = sbs.toString();

				if (st.equals("<p>")) {
					st.append(sfu);
				} else {
					st.append("<br/>" + sfu);
				}
			}
			st.append("</p>");
		}
		// 上传特色图片
		String featurecon = m.line.getSummary().getFeatures();
		String featureimg = "";
		if (m.line.getSummary().isFeaturechecked()) {
			String ipath = m.line.getSummary().getTxtfeaturepath();
			featureimg = postFeatureImg(ipath);
		}
		featurecon = concatStr(featurecon, "{title}", st.toString());
		featurecon = concatStr(featurecon, "{特色图片}", featureimg);
		nvps.add(new BasicNameValuePair(key, com.bhc.util.StringUtils.parseCharacter(featurecon)));
	}

	// 备份的代码
	public String postLineBase_back() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}

		Summary summary = line.getSummary();

		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			updateUI("开始准备上传数据");
			HttpPost httpost = createHttpPost(loginurl);

			String pfunction = summary.getPfunction().trim();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 取得目的地
			StringBuffer arrive = new StringBuffer("");
			String[] ta = summary.getArrive().split(",");
			if (ta != null && ta.length > 0) {
				int i = 0;
				String pstr = "";
				for (String s : ta) {
					if (!s.trim().equals("")) {
						if (i == 0) {
							i++;
							pstr = s;
							continue;
						}
						String ts = pstr.trim() + "-" + s.trim();
						String tjson = getDestAreaInfo(s.trim());
						Map obj = readJson(tjson);
						List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) obj.get("result");
						StringBuffer sf = new StringBuffer("");
						for (LinkedHashMap<String, Object> ls : list) {
							String faname = ls.get("fullarrive").toString().trim();
							String dname = ls.get("display").toString().trim();
							String cname = ls.get("c").toString().trim();
							String id = ls.get("id").toString().trim();
							if (faname.indexOf(pstr.trim()) != -1) {
								if ((cname.indexOf(s.trim()) != -1 || s.trim().indexOf(cname.trim()) != -1) && ((cname.length() - s.length()) <= 3 || s.length() - cname.length() <= 3)) {
									if (sf.toString().equals("")) {
										sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
									} else {
										sf.append("," + id + "_@_" + dname + "_@_0_@_" + faname);
									}
									if (cname.trim().equals(s.trim())) {
										sf.delete(0, sf.length());
										sf.append(id + "_@_" + dname + "_@_0_@_" + faname);
										break;
									}
								}
							}
						}
						if (!sf.toString().trim().equals("")) {
							if (arrive.toString().equals("")) {
								arrive.append(sf);
							} else {
								arrive.append("," + sf);
							}
							sf.delete(0, arrive.length());
						}
					}
				}
			}
			updateUI(arrive.toString());

			// 取得出发地
			String departure = "";
			String dstr = summary.getDeparture();
			if (dstr != null && !dstr.trim().equals("")) {
				String tjson = getStartAreaInfo(dstr.trim());
				tjson = tjson.substring(tjson.indexOf("(") + 1, tjson.lastIndexOf(")"));
				Map obj = readJson(tjson);
				List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) obj.get("result");
				for (LinkedHashMap<String, Object> ls : list) {
					String faname = ls.get("display").toString();
					String id = ls.get("id").toString();
					if (faname.indexOf(dstr.trim()) != -1 || dstr.trim().indexOf(faname.trim()) != -1) {
						departure = id + "_@_" + faname;
						break;
					}
				}
				updateUI(departure);
			}
			// 主图
			String mainpic = "";
			// 图库
			String images = "";

			String img = summary.getImage();
			String[] imgs = img.split(",");
			Random r = new Random();
			int rm = r.nextInt(imgs.length);
			if (imgs != null) {
				if (m.line.isC1nomain()) {
					for (int i = 0; i < imgs.length; i++) {
						String iurl = "";
						String suffix = imgs[i].substring(imgs[i].lastIndexOf("."));
						tempimg = realpath + "/tempimg" + suffix;
						downLoadImage(imgs[i].trim());
						while (true) {
							if (downover) {
								break;
							}
							Thread.sleep(1 * 1000);
						}
						try {
							iurl = postFile(tempimg);
							if (i == 0) {
								images = iurl;
							} else {
								images += "," + iurl;
							}
							// if (rm == i) {
							// mainpic = iurl;
							// }
						} catch (Exception e) {
							updateUI(e.getMessage());
						}
					}
				}
			}
			if (pid.equals("")) {
				nvps.add(new BasicNameValuePair("method", "addProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "true"));
				nvps.add(new BasicNameValuePair("ret", ""));
				// nvps.add(new BasicNameValuePair("mainpic", mainpic));
				nvps.add(new BasicNameValuePair("images", images));
			} else {
				nvps.add(new BasicNameValuePair("method", "updateProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "false"));
				nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
				if (m.line.isC1noimg()) {
					// nvps.add(new BasicNameValuePair("mainpic", mainpic));
					nvps.add(new BasicNameValuePair("images", images));
				}
			}

			nvps.add(new BasicNameValuePair("next", "false"));
			nvps.add(new BasicNameValuePair("supperid", supperid));
			nvps.add(new BasicNameValuePair("id", pid));

			nvps.add(new BasicNameValuePair("p_function", pfunction));
			nvps.add(new BasicNameValuePair("arrive", arrive.toString()));
			nvps.add(new BasicNameValuePair("departure", departure));
			nvps.add(new BasicNameValuePair("main_pic_desc", ""));
			nvps.add(new BasicNameValuePair("image_desc", ""));

			// 产品名称
			nvps.add(new BasicNameValuePair("title", concatStr(m.line.getSummary().getTitle().trim(), "{title}", summary.getTitle())));
			// 团号
			nvps.add(new BasicNameValuePair("team_no", summary.getTeamno().trim()));
			// 行程天数
			nvps.add(new BasicNameValuePair("day", summary.getDay().trim()));
			// 入住晚数
			nvps.add(new BasicNameValuePair("night", String.valueOf(Integer.parseInt(summary.getDay().trim()) - 1)));

			// 自然日or工作日
			String advancedaytype = "realday", at = "";
			if (m.line.isAdck()) {
				try {
					at = m.line.getSummary().getAdvancedaytype().trim();
					// 提前报名
					nvps.add(new BasicNameValuePair("advance_day", m.line.getSummary().getAdvanceday().trim()));
				} catch (Exception e) {
					updateUI("选择了提前报名,但是没有数据");
				}
			} else {
				at = summary.getAdvancedaytype().trim();
				// 提前报名
				nvps.add(new BasicNameValuePair("advance_day", summary.getAdvanceday().trim()));
			}
			if (at.equals("工作日")) {
				advancedaytype = "workday";
			}

			nvps.add(new BasicNameValuePair("advance_day_type", advancedaytype));
			// 类别
			String type = "";
			String ttype = summary.getArrivetype().trim();
			if (ttype.equals("国内游")) {
				type = "0";
			} else if (ttype.equals("出境游")) {
				type = "1";
			} else {
				type = "2";
			}
			nvps.add(new BasicNameValuePair("type", type));
			// 长/短途
			String distancetype = "";
			String dtype = summary.getDistancetype().trim();
			if (dtype.equals("长途")) {
				distancetype = "0";
			} else {
				distancetype = "1";
			}
			nvps.add(new BasicNameValuePair("distance_type", distancetype));
			// 大交通
			nvps.add(new BasicNameValuePair("totraffic", summary.getFreetriptotraffic().trim() + "去"));
			nvps.add(new BasicNameValuePair("backtraffic", summary.getFreetripbacktraffic().trim() + "回"));
			// 下架日期
			// 发团日前下架0,指定日期上下架1
			nvps.add(new BasicNameValuePair("display_way", "0"));
			// 开始日期
			nvps.add(new BasicNameValuePair("publish_time", ""));
			// 截止日期
			nvps.add(new BasicNameValuePair("expire_time", ""));
			if (m.line.isPayck()) {
				// 付款方式,即时支付0,确认后支付1
				if (m.line.getSummary().isPayway1()) {
					nvps.add(new BasicNameValuePair("pay_way", "0"));
				}
				if (m.line.getSummary().isPayway2()) {
					nvps.add(new BasicNameValuePair("pay_way", "1"));
				}
			} else {
				nvps.add(new BasicNameValuePair("pay_way", "0"));
			}
			// 上下架状态,on+sale上架，offline下架
			nvps.add(new BasicNameValuePair("status", "offline"));
			// 服务电话
			nvps.add(new BasicNameValuePair("phoneinfo", "2175"));
			// 产品副标题
			nvps.add(new BasicNameValuePair("recommendation", concatStr(m.line.getSummary().getRecommendation().trim(), "{title}", summary.getTitle())));

			// 产品特色
			List<String> flist = summary.getFeature();
			StringBuffer st = new StringBuffer("");
			if (flist != null && flist.size() > 0) {
				st.append("<p>");
				for (String sfu : flist) {
					while (true) {
						if (sfu.indexOf("-") == -1) {
							break;
						}
						sfu = sfu.substring(0, sfu.indexOf("-")) + "<br/>" + sfu.substring(sfu.indexOf("-") + 1);
					}
					if (st.equals("<p>")) {
						st.append(sfu);
					} else {
						st.append("<br/>" + sfu);
					}
				}
				st.append("</p>");
			}
			nvps.add(new BasicNameValuePair("feature", concatStr(m.line.getSummary().getFeatures(), "{title}", st.toString())));

			if (pfunction.trim().equals("group")) {
				nvps.add(new BasicNameValuePair("group_departure", departure));
			} else if (pfunction.trim().equals("free")) {
				nvps.add(new BasicNameValuePair("free_type", "1"));
				nvps.add(new BasicNameValuePair("first_date", "2013-10-22"));
				nvps.add(new BasicNameValuePair("last_date", "2013-10-26"));
				// 自由行类别
				nvps.add(new BasicNameValuePair("freetrip_traffic", "on"));
				nvps.add(new BasicNameValuePair("freetrip_hotel", "on"));
				nvps.add(new BasicNameValuePair("freetrip_othername", ""));
				// 旅客信息
				nvps.add(new BasicNameValuePair("need_traveller_info", "1"));
				// 提前预订设置
				nvps.add(new BasicNameValuePair("advance_day_new", "0"));
				nvps.add(new BasicNameValuePair("advance_hour", "23"));
				nvps.add(new BasicNameValuePair("advance_minute", "59"));
				// 提前预订描述
				nvps.add(new BasicNameValuePair("advanceDesc", "请在当天的23点59分前预订"));

				// 目的地*
				nvps.add(new BasicNameValuePair("freeArrive", "云南-昆明"));
				// 利润率
				nvps.add(new BasicNameValuePair("profit", "0"));
			}

			// 出发地*
			nvps.add(new BasicNameValuePair("departure", m.line.getSummary().getDeparture().replaceAll("{title}", summary.getDeparture().trim())));

			for (NameValuePair bp : nvps) {
				System.out.println(bp.getName() + ":" + bp.getValue());
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			updateUI("上传数据准备完成");
			updateUI("执行上传数据功能");
			HttpResponse response = httpclient.execute(httpost);

			HttpEntity entity = response.getEntity();
			String content = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					content = sb.toString();
					updateUI("" + content);
					pid = content.substring(content.indexOf("id=") + "id=".length(), content.lastIndexOf("&ret"));
				} catch (Exception e) {
					updateUI(e.getMessage());
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("基础数据上传完成");
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String postLineDays() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		if (pid == null) {
			return null;
		}
		Summary summary = line.getSummary();
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			updateUI("开始准备上传数据");
			HttpPost httpost = createHttpPost(loginurl);
			String pfunction = summary.getPfunction().trim();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 上传行程数据
			updateUI("开始上传行程数据");

			// 如果为跟团游那么有特色数据和酒店
			if (pfunction.trim().equals("group")) {
				combineFeature("route_feature", nvps);
				nvps.add(new BasicNameValuePair("gather_change_flag", "on"));
				String gt = m.line.getGathertime();
				if (gt != null && gt.length() > 0) {
					// 01.01.1970 17:07:33.000 +0800
					int start = gt.indexOf(":");
					gt = gt.substring(start - 2);
					gt = gt.substring(0, 5);
				}
				nvps.add(new BasicNameValuePair("gather_time", gt));// 集合时间
				nvps.add(new BasicNameValuePair("gather_spot", m.line.getGatherspot()));// 集合地点
				nvps.add(new BasicNameValuePair("assembly", m.line.getAssembly()));
			}
			nvps.add(new BasicNameValuePair("method", "updatDailySchedule2"));
			nvps.add(new BasicNameValuePair("pId", pid));
			nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
			nvps.add(new BasicNameValuePair("next", "false"));
			nvps.add(new BasicNameValuePair("newFlag", "false"));
			nvps.add(new BasicNameValuePair("b2bFlag", "0"));
			nvps.add(new BasicNameValuePair("radio", "radio"));
			List<Day> list = line.getDays();
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Day d = list.get(j);

					String dst = d.getDaydescription();
					// 这个行程描述先不用
					dst = "";
					while (true) {
						int e = dst.indexOf("-");
						if (e == -1) {
							break;
						}
						dst = dst.substring(0, e) + "<br/>" + dst.substring(e + 1);
					}
					// 给行程关键字着色
					if (m.line.isDkeycheck()) {
						String dkey = m.line.getDkeytxt();
						if (dkey != null) {
							String[] keys = dkey.split(",");
							StringBuffer odst = new StringBuffer("");

							if (keys.length > 0) {
								for (String ks : keys) {
									ks = ks.trim();
									String str = "";
									StringBuffer tdst = new StringBuffer("");
									for (int i = 0; i < dst.length(); i++) {
										String tst = dst.substring(i, i + 1);
										tdst.append(tst);
										str += tst;
										if (ks.equals(str)) {
											tdst.delete(tdst.length() - str.length(), tdst.length());
											String sty = concatStr(m.line.getDkeystyle(), "{tag}", ks);
											tdst.append(sty);
										}
										if (!ks.startsWith(str)) {
											str = "";
											continue;
										}

									}
									dst = tdst.toString();
								}
							}
						}
					}

					StringBuffer daydescription = new StringBuffer(concatStr(m.line.getTxtdiscription(), "{title}", dst));

					// 首先判断只有处理图片，那么才执行
					// 图库
					String dayimgs = "";
					if (m.line.isC1noimg()) {
						String dayimg = d.getSightimage();
						// 如果指定使用本地的图片，那么找出本地的图片来上传
						if (m.line.isDaysimgcheck()) {
							m.line.getDayspic().clear();
							String txtimg = m.line.getTxtdiscription();
							String fdir = m.line.getViewimgdir();
							String tag = "{随机景点图片}";
							int itype = 0;// 指哪种图片,指取哪个字段做为比较
							String st = daydescription.toString();
							daydescription.delete(0, daydescription.length());
							if (txtimg.indexOf(tag) != -1) {
								fdir = m.line.getViewimgdir();
								String s = postImage(fdir, d, itype);//
								daydescription.delete(0, daydescription.length());
								if (!m.line.getFlashshowuse()) {
									daydescription.append(concatStr(st, tag, s));
									st = daydescription.toString();
								} else {
									st = concatStr(st, tag, "");
								}
							}

							tag = "{随机酒店图片}";
							if (txtimg.indexOf(tag) != -1) {
								itype = 1;
								fdir = m.line.getHotelimgdir();
								String s = postImage(fdir, d, itype);
								daydescription.delete(0, daydescription.length());
								if (!m.line.getFlashshowuse()) {
									daydescription.append(concatStr(st, tag, s));
									st = daydescription.toString();
								} else {
									st = concatStr(st, tag, "");
								}
							}
							tag = "{随机美食图片}";
							if (txtimg.indexOf(tag) != -1) {
								itype = 0;
								fdir = m.line.getFoodimgdir();
								String s = postImage(fdir, d, itype);
								daydescription.delete(0, daydescription.length());
								if (!m.line.getFlashshowuse()) {
									daydescription.append(concatStr(st, tag, s));
									st = daydescription.toString();
								} else {
									st = concatStr(st, tag, "");
								}
							}
							if (m.line.getFlashshowuse()) {
								tag = "{fimg}";
								List<String> pics = m.line.getDayspic();
								StringBuffer sbf = new StringBuffer();
								for (String strpic : pics) {
									if (sbf.length() == 0) {
										sbf.append("http://img1.qunarzz.com/" + strpic);
									} else {
										sbf.append("|").append("http://img1.qunarzz.com/" + strpic);
									}
								}
								daydescription.append(concatStr(st, tag, sbf.toString()));
							}
						} else {
							if (dayimg != null && !dayimg.trim().equals("")) {
								String[] dis = dayimg.split(",");
								for (int g = 0; g < dis.length; g++) {
									if (!m.line.isDaysimgcheck()) {
										String suffix = dis[g].trim().substring(dis[g].lastIndexOf("."));
										tempimg = realpath + "/tempimg" + suffix;
										downLoadImage(dis[g].trim());
										while (true) {
											if (downover) {
												break;
											}
											Thread.sleep(2 * 1000);
										}
									} else {
										tempimg = dis[g];
									}
									if (g == 0) {
										dayimgs = postFile(tempimg);
									} else {
										dayimgs += "," + postFile(tempimg);
									}
								}
							}
							// 现在新网站，旧网站都改成一样的了
							if (m.line.isImg_publish_style1()) {
								nvps.add(new BasicNameValuePair("images_" + (j + 1), dayimgs));
							} else if (m.line.isImg_publish_style2()) {
								nvps.add(new BasicNameValuePair("images_" + (j + 1), ""));
							} else {
								nvps.add(new BasicNameValuePair("images_" + (j + 1), ""));
							}
							nvps.add(new BasicNameValuePair("image_desc_" + (j + 1), ""));
						}
					} else {
						String st = daydescription.toString();
						st = concatStr(st, "{随机景点图片}", "");
						st = concatStr(st, "{随机酒店图片}", "");
						st = concatStr(st, "{随机美食图片}", "");
						st = concatStr(st, "{imgposition}", "");
						daydescription.delete(0, daydescription.length());
						daydescription.append(st);
					}

					String daddr = "{\"sights\":[{\"id\":\"\",\"sight\":\"" + d.getDaytitle() + "\"}]}";
					nvps.add(new BasicNameValuePair("tour_track_" + (j + 1), daddr));

					daydescription.append("<br/>").append(d.getActivity());
					nvps.add(new BasicNameValuePair("day_" + (j + 1), d.getDaynum()));
					nvps.add(new BasicNameValuePair("day_title_" + (j + 1), d.getDaytitle()));
					nvps.add(new BasicNameValuePair("day_hotel_star_" + (j + 1), d.getStarname()));
					nvps.add(new BasicNameValuePair("day_hotel_desc_" + (j + 1), d.getStardesc() + "<br/>" + com.bhc.util.StringUtils.parseCharacter(d.getInnfeature())));

					// 酒店情况数据
					String h_type = "1", h_name = "酒店", start = "", level = "";
					if (d.getStarname() != null) {
						h_name = d.getStarname();
						if (d.getStarname().trim().equals("客栈") || d.getStarname().trim().equals("农家院") || d.getStarname().trim().indexOf("酒店") != -1) {
							h_name = d.getStarname() + d.getInnfeature();
							h_type = "1";
							start = "0";
							level = "0";
						} else if (d.getStarname().trim().equals("住在交通工具上")) {
							h_type = "2";
						} else if (d.getStarname().trim().equals("酒店转机住宿")) {
							h_type = "3";
						} else {
							h_type = "4";
						}
					}
					StringBuffer inn = new StringBuffer("[");
					String[] hos = d.getStardesc().split(";|；");
					if (hos != null && hos.length > 0) {
						for (int i = 0; i < hos.length; i++) {
							inn.append("{\"hotel_type\":\"" + h_type + "\",\"name\":\"" + hos[i] + "\",\"hotel_seq\":\"\",\"city_code\":\"\",\"address\":\"" + h_name + "\",\"star\":\"" + start + "\",\"level\":\"" + level
									+ "\",\"occur_seq\":1,\"first\":true,\"last\":true},");
						}
					}
					if (inn.length() > 10) {
						inn.setLength(inn.length() - 1);
					}
					inn.append("]");
					updateUI(inn.toString());
					if (j == list.size() - 1) {// 如果是最后一天，那么没有住宿
						inn.setLength(0);
					}

					// updateUI(inn);

					if (m.line.getCselect().equals("qly网站")) {
						nvps.add(new BasicNameValuePair("shopping_" + (j + 1), d.getShopping()));
						nvps.add(new BasicNameValuePair("self_pay" + (j + 1), d.getSelfexpense()));
						nvps.add(new BasicNameValuePair("hotels_" + (j + 1), inn.toString()));
					}

					if (!m.line.isImg_publish_style1()) {
						if (dayimgs != null && dayimgs.length() > 10) {
							nvps.add(new BasicNameValuePair("images_" + (j + 1), ""));
							String[] imgs = dayimgs.split(",");
							if (imgs.length > 0) {
								StringBuffer sb = new StringBuffer();
								int c = 0;

								int col = 2;
								if (m.line.isImg_publish_style2()) {
									col = 2;
								} else {
									col = 1;
								}

								for (c = 0; c < imgs.length; c++) {
									if (c % col == 0) {
										sb.append("<div style=\"margin:0px 0px 3px 0px;\">");
									}
									if (m.line.isImg_publish_style2()) {
										sb.append("<img style=\"width:49%;clear:both\" src=\"http://img1.qunarzz.com" + imgs[c] + "\">");
									} else {
										sb.append("<img style=\"margin:1px 1px 1px 1px;;width:90%;clear:both\" src=\"http://img1.qunarzz.com" + imgs[c] + "\">");
									}
									if (c % col == 1) {
										sb.append("</div>\r\n");
									}
								}
								if (c % col != 0) {
									sb.append("</div>\r\n");
								}
								String tstr = m.line.getTxtdiscription();
								String txtdesc = "";
								if (tstr.indexOf("{imgposition}") != -1) {
									txtdesc = concatStr(daydescription.toString(), "{imgposition}", com.bhc.util.StringUtils.parseCharacter(d.getInnmark()) + sb.toString());

								} else {
									txtdesc = daydescription.toString() + com.bhc.util.StringUtils.parseCharacter(d.getInnmark()) + sb.toString();
								}
								// 以//n为换行符
								StringBuffer sbs = new StringBuffer();
								for (int i = 0; i < txtdesc.length(); i++) {
									sbs.append(txtdesc.substring(i, i + 1));
									if (sbs.toString().endsWith("\\\\n")) {
										sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>&nbsp;&nbsp;");
									}
								}
								txtdesc = sbs.toString();

								nvps.add(new BasicNameValuePair("description_" + (j + 1), txtdesc));
							}
						} else {
							daydescription.toString().replaceAll("", "");
							nvps.add(new BasicNameValuePair("description_" + (j + 1), concatStr(daydescription.toString(), "{imgposition}", "")));
						}
					} else {
						nvps.add(new BasicNameValuePair("description_" + (j + 1), concatStr(daydescription.toString(), "{imgposition}", "")));
					}

					if (d.getBreakfirst().equals("早餐")) {
						nvps.add(new BasicNameValuePair("zao_" + (j + 1), "on"));
						nvps.add(new BasicNameValuePair("breakfast_desc_" + (j + 1), d.getBreakfirstdesc()));
					}
					if (d.getLunch().equals("中餐")) {
						nvps.add(new BasicNameValuePair("zhong_" + (j + 1), "on"));
						nvps.add(new BasicNameValuePair("lunch_desc_" + (j + 1), d.getLunchdesc()));
					}
					if (d.getSupper().equals("晚餐")) {
						nvps.add(new BasicNameValuePair("wan_" + (j + 1), "on"));
						nvps.add(new BasicNameValuePair("dinner_desc_" + (j + 1), d.getSupperdesc()));
					}

					// 行程里的交通
					try {
						String[] tas = d.getDaytraffic().trim().split(" ");
						if (pfunction.trim().equals("group")) {
							nvps.add(new BasicNameValuePair("has_traffic_" + (j + 1), "yes"));
							StringBuffer sbt = new StringBuffer();
							if (tas != null) {
								sbt.append("[");
								for (String tstr : tas) {
									tstr = tstr.trim();
									if (tstr.equals("飞机")) {
										Pattern p = Pattern.compile("[0-9]?[0-9]:[0-9]?[0-9]");

										for (int i = 0, f = 0; i < line.getVehicleSet().size(); i++) {
											Vehicle ve = line.getVehicleSet().get(i);
											// 如果是第一天，那么取的是来时的航班
											if (j == 0) {
												if (!ve.getFlightype().equals("回程")) {
													continue;
												}
											} else {
												if (!ve.getFlightype().equals("去程")) {
													continue;
												}
											}

											String detime = ve.getDeptime();
											Matcher match = p.matcher(detime);
											if (match.find()) {
												detime = match.group();
											}
											detime = detime.equals("0000-00-00") ? "00:00" : detime;
											String arrtime = ve.getArrtime();
											match = p.matcher(arrtime);
											if (match.find()) {
												arrtime = match.group();
											}
											arrtime = arrtime.equals("0000-00-00") ? "00:00" : arrtime;

											sbt.append("{\"cases\":[{");
											sbt.append("\"references\":[{");
											sbt.append("\"traffic_type\":\"1\",\"flight_no\":\"" + ve.getFlightno() + "\",\"flight_cabin\":\"3\",\"departure\":\"" + ve.getDepcity() + "\",\"dep_airport\":\""
													+ ve.getDepairport() + "\",\"dep_time\":\"" + detime + "\",\"arrive\":\"" + ve.getArrcity() + "\",\"arr_airport\":\"" + ve.getArrairport() + "\",\"arr_time\":\""
													+ arrtime
													+ "\",\"arr_time_flag\":\"0\",\"flight_duration\":\"120\",\"duration_hours\":2,\"duration_minutes\":\"\",\"stop_comment\":\"\",\"flight_type\":\"\",\"transfer\":\"0\",\"arr_time_flag_cn\":\"\",\"flight\":true,\"traffic_type_cn\":\"飞机\",\"flight_cabin_cn\":\"经济舱\",\"stop_comment_tag\":false,\"transfer_tag\":0");
											sbt.append("}]");
											sbt.append(",\"cases_index\":1");
											sbt.append(",\"first\":true}]");
											sbt.append(",\"indexs\":1");
											sbt.append(",\"len\":2},");
											f++;
											if (f == 3) {
												break;
											}
										}
									} else if (tstr.equals("火车")) {
										sbt.append("{\"cases\":[{");
										sbt.append("\"references\":[{");
										sbt.append("\"traffic_seq\":2,\"traffic_type\":\"2\",\"train_no\":\"待定\",\"departure\":\"待定\",\"arrive\":\"待定\"");
										sbt.append("}]");
										sbt.append(",\"cases_index\":1");
										sbt.append(",\"first\":true}]");
										sbt.append(",\"indexs\":1");
										sbt.append(",\"len\":2},");
									} else if (tstr.equals("汽车")) {
										sbt.append("{\"cases\":[{");
										sbt.append("\"references\":[{");
										sbt.append(
												"\"traffic_type\":\"3\",\"car_type\":\"旅游大巴\",\"car_seat\":\"0\",\"departure\":\"待定\",\"dep_time\":\"00:00\",\"arrive\":\"参照行程\",\"arr_time\":\"00:00\",\"arr_time_flag\":\"0\",\"train_duration\":\"0\",\"duration_hours\":\"\",\"duration_minutes\":\"\",\"transfer\":\"0\",\"arr_time_flag_cn\":\"\",\"bus\":true,\"traffic_type_cn\":\"汽车\",\"car_seat_cn\":\"待定\",\"stop_comment_tag\":false,\"transfer_tag\":0");
										sbt.append("}]");
										sbt.append(",\"cases_index\":1");
										sbt.append(",\"first\":true}]");
										sbt.append(",\"indexs\":1");
										sbt.append(",\"len\":2},");
									} else if (tstr.equals("轮船")) {
										sbt.append("{\"cases\":[{");
										sbt.append("\"references\":[{");
										sbt.append("\"traffic_seq\":4,\"traffic_type\":\"4\",\"cruise_name\":\"待定\",\"departure\":\"待定\",\"arrive\":\"待定\"");
										sbt.append("}]");
										sbt.append(",\"cases_index\":1");
										sbt.append(",\"first\":true}]");
										sbt.append(",\"indexs\":1");
										sbt.append(",\"len\":2},");
									} else if (d.getOther() != null) {
										sbt.append("{\"cases\":[{");
										sbt.append("\"references\":[{");
										sbt.append("\"traffic_seq\":5,\"traffic_type\":\"5\",\"other\":\"待定\"");
										sbt.append("}]");
										sbt.append(",\"cases_index\":1");
										sbt.append(",\"first\":true}]");
										sbt.append(",\"indexs\":1");
										sbt.append(",\"len\":2},");
									}

								}
								if (sbt.substring(sbt.length() - 1, sbt.length()).equals(",")) {
									sbt.setLength(sbt.length() - 1);
								}
								sbt.append("]");
								nvps.add(new BasicNameValuePair("traffics_" + (j + 1), sbt.toString()));
							}
						} else {
							if (d.getDaytraffic().trim().equals("汽车")) {
								nvps.add(new BasicNameValuePair("qc_" + (j + 1), "on"));
							} else if (d.getDaytraffic().trim().equals("飞机")) {
								nvps.add(new BasicNameValuePair("fj_" + (j + 1), "on"));
							} else if (d.getDaytraffic().trim().equals("火车")) {
								nvps.add(new BasicNameValuePair("hc_" + (j + 1), "on"));
							} else if (d.getDaytraffic().trim().equals("汽车")) {
								nvps.add(new BasicNameValuePair("qc_" + (j + 1), "on"));
							} else if (d.getDaytraffic().trim().equals("轮船")) {
								nvps.add(new BasicNameValuePair("lc_" + (j + 1), "on"));
							} else if (d.getOther() != null) {
								nvps.add(new BasicNameValuePair("qc_" + (j + 1), "on"));
								nvps.add(new BasicNameValuePair("other_" + (j + 1), "on"));
							}
							nvps.add(new BasicNameValuePair("day_traffic_other_" + (j + 1), ""));
						}
					} catch (Exception e) {
						updateUI("日程交通工具上传出错");
					}
				}
			}

			// for (NameValuePair bp : nvps) {
			// if (bp.getName().startsWith("description_")) {
			// System.out.println(bp.getName() + ":" + bp.getValue());
			// }
			// }

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);

			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("行程数据上传完成");
			return rc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String postImage(String fdir, Day d, int s) {
		String dayimgs = "";
		try {
			String dayimg = "";
			File f = new File(fdir);
			File[] fs = f.listFiles();
			String dtk = d.getDaytitle();
			if (s == 1) {
				dtk = d.getStardesc();
				if (dtk == null || dtk.trim().equals("")) {
					return dayimgs;
				}
			}
			String[] dtks = dtk.split("－|-");
			if (dtks != null && dtks.length > 0) {
				StringBuffer sbf = new StringBuffer("");
				for (String tagname : dtks) {
					if (tagname.equals("")) {
						continue;
					}
					for (File ft : fs) {
						String sss = ft.getName().trim();
						if (!sss.toLowerCase().endsWith(".jpg") && !sss.toLowerCase().endsWith(".gif") && !sss.toLowerCase().endsWith(".png")) {
							continue;
						}
						String se = tagname.trim();
						System.out.println(se + "\t" + sss);
						if (sss.startsWith(se)) {
							if (sbf.length() == 0) {
								sbf.append(ft.getAbsolutePath());
							} else {
								sbf.append("," + ft.getAbsolutePath());
							}
						}
					}
				}
				dayimg = sbf.toString();
			}
			if (dayimg != null && !dayimg.trim().equals("")) {
				String[] dis = dayimg.split(",");
				for (int g = 0; g < dis.length; g++) {
					try {
						String purl = postFile(dis[g].trim());
						dayimgs += "<p><img src=\"http://img1.qunarzz.com" + purl + "\"/><br/>&nbsp;</p>";
						m.line.getDayspic().add(purl);
					} catch (Exception e) {
						updateUI(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			updateUI(e.getMessage());
		}
		return dayimgs;
	}

	public String postFeatureImg(String fdir) {
		String dayimgs = "";
		try {
			String dayimg = "";

			List<File> files = Arrays.asList(new File(fdir).listFiles());
			Collections.sort(files, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					if (o1.isDirectory() && o2.isFile())
						return -1;
					if (o1.isFile() && o2.isDirectory())
						return 1;
					return o1.getName().compareTo(o2.getName());
				}
			});

			StringBuffer sbf = new StringBuffer("");
			for (File ft : files) {
				if (sbf.length() == 0) {
					sbf.append(ft.getAbsolutePath());
				} else {
					sbf.append("," + ft.getAbsolutePath());
				}
			}
			dayimg = sbf.toString();

			if (dayimg != null && !dayimg.trim().equals("")) {
				String[] dis = dayimg.split(",");
				for (int g = 0; g < dis.length; g++) {
					String sss = dis[g].trim();
					if (!sss.toLowerCase().endsWith(".jpg") && !sss.toLowerCase().endsWith(".gif") && !sss.toLowerCase().endsWith(".png")) {
						continue;
					}
					try {
						String purl = postFile(dis[g].trim());
						dayimgs += "<p><img src=\"http://img1.qunarzz.com" + purl + "\"/><br/>&nbsp;</p>";
					} catch (Exception e) {
						updateUI(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			updateUI(e.getMessage());
		}
		return dayimgs;
	}

	public String postLinePrice() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		if (pid == null) {
			return null;
		}
		Summary summary = line.getSummary();
		try {
			String pfunction = summary.getPfunction().trim();

			List<List<NameValuePair>> tlist = new ArrayList<List<NameValuePair>>();

			// 上传行程数据
			updateUI("开始上传价格数据");
			// if(pfunction.trim().equals("group")){

			boolean bs = false;
			if (m.line.getPricedates().size() > 0) {
				bs = true;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			 * 现在不用下架就可以更改数据了 for (int i = 0; i < 6; i++) {// 先把以前的价格下架 String
			 * dstr = sdf.format(calendar.getTime());
			 * searchExistLineTeams(dstr); calendar.add(calendar.MONTH, 1); }
			 */

			List<Team> list = line.getTeams();
			if (list != null && list.size() > 0) {
				List<NameValuePair> nvps = null;
				for (int j = 0; j < list.size(); j++) {
					Team t = list.get(j);
					//如果选择了时间段，那么只上传时间段内有数据
					if(m.line.isUsedateflag()){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						Calendar tc=Calendar.getInstance(Locale.getDefault());
						tc.setTime(sdf.parse(t.getTakeoffdate()));
						Calendar bc=Calendar.getInstance(Locale.getDefault());
						bc.setTime(m.line.getUdatebegin());
						Calendar ec=Calendar.getInstance(Locale.getDefault());
						ec.setTime(m.line.getUdateend());
						if(tc.after(ec) || tc.before(bc)){
							continue;
						}
					}
					
					
					
					String dt = t.getTakeoffdate().trim().substring(0, 10);
					if (bs) {
						boolean ex = false;
						for (String s : m.line.getPricedates()) {
							if (s.trim().compareTo(dt) == 0) {
								ex = true;
								break;
							}
						}
						if (!ex) {
							continue;
						}
					}

					nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("method", "operatProductTeams"));
					nvps.add(new BasicNameValuePair("op", "update"));
					nvps.add(new BasicNameValuePair("is_taocan", "off"));
					nvps.add(new BasicNameValuePair("pId", pid));

					nvps.add(new BasicNameValuePair("room_type", "2"));
					nvps.add(new BasicNameValuePair("profit", "0"));

					int fprice = 0;
					SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat edf = new SimpleDateFormat("dd.MM.yyyy");
					// 获取指定的日期价格变动
					// ，判断当前的日期是不是在指定的要调整的日期内，如果是那么加上指定的价格，可以为负，那么就是减去指定的价格
					if (m.line.isChangespecial()) {
						// sdate 指定开如日期, edate 结束日期, ldate 当前这个价格的日期
						Date sdate = null, edate = null, ldate = null;
						try {
							sdate = fdf.parse(fdf.format(m.line.getSpecialsatrttime()));
						} catch (Exception e) {
							try {
								sdate = fdf.parse(edf.format(m.line.getSpecialsatrttime()));
							} catch (Exception e1) {
							}
						}
						try {
							edate = fdf.parse(fdf.format(m.line.getSpecialendtime()));
						} catch (Exception e) {
							try {
								edate = fdf.parse(edf.format(m.line.getSpecialendtime()));
							} catch (Exception e1) {
							}
						}
						try {
							ldate = fdf.parse(t.getTakeoffdate());
						} catch (Exception e1) {
						}
						// 调整价格
						if (ldate.getTime() >= sdate.getTime() && ldate.getTime() < edate.getTime()) {
							fprice += Integer.parseInt(m.line.getPriceratedate());
						}
					}

					// 第二个价格调整
					if (m.line.isChangespecial2()) {
						Date sdate = null, edate = null, ldate = null;
						try {
							sdate = fdf.parse(fdf.format(m.line.getSpecialsatrttime2()));
						} catch (Exception e) {
							try {
								sdate = fdf.parse(edf.format(m.line.getSpecialsatrttime2()));
							} catch (Exception e1) {

							}
						}
						try {
							edate = fdf.parse(fdf.format(m.line.getSpecialendtime2()));
						} catch (Exception e) {
							try {
								edate = fdf.parse(edf.format(m.line.getSpecialendtime2()));
							} catch (Exception e1) {

							}
						}
						try {
							ldate = fdf.parse(t.getTakeoffdate());
						} catch (Exception e) {

						}
						try {
							if (ldate.getTime() >= sdate.getTime() && ldate.getTime() < edate.getTime()) {
								fprice += Integer.parseInt(m.line.getPrate());
							}
						} catch (Exception e) {

						}
					}

					// 第三个价格调整, 调整房差
					if (m.line.isChangespecial3()) {
						Date sdate = null, edate = null, ldate = null;
						try {
							sdate = fdf.parse(fdf.format(m.line.getSpecialsatrttime3()));
						} catch (Exception e) {
							try {
								sdate = fdf.parse(edf.format(m.line.getSpecialsatrttime3()));
							} catch (Exception e1) {

							}
						}
						try {
							edate = fdf.parse(fdf.format(m.line.getSpecialendtime3()));
						} catch (Exception e) {
							try {
								edate = fdf.parse(edf.format(m.line.getSpecialendtime3()));
							} catch (Exception e1) {

							}
						}
						try {
							ldate = fdf.parse(t.getTakeoffdate());
						} catch (Exception e) {

						}
						try {
							if (ldate.getTime() >= sdate.getTime() && ldate.getTime() < edate.getTime()) {
								// 这儿作了判断，如果调整的价格小于0，那么单房差为0
								int rprice = Integer.parseInt(t.getRoomsendprice());
								int dprice = Integer.parseInt(m.line.getPriceratedate3());
								rprice = rprice + dprice;
								if (rprice < 0) {
									rprice = 0;
								}
								t.setRoomsendprice(String.valueOf(rprice));
							}
						} catch (Exception e) {

						}
					}

					if (m.line.isChangespecial4()) {
						int changtimes = m.line.getSpecialdate();
						if (j != 0 && j <= changtimes) {
							try {
								int dprice = m.line.getSpecealprice();
								fprice += dprice;
							} catch (Exception e) {
							}
						}
					}

					if (m.line.isBtcprice()) {
						nvps.add(new BasicNameValuePair("market_price", String.valueOf(Integer.parseInt(m.line.getMarketprice()) + fprice)));
						nvps.add(new BasicNameValuePair("adult_price", String.valueOf(Integer.parseInt(m.line.getAdultprice()) + fprice)));
						nvps.add(new BasicNameValuePair("room_send_price", m.line.getSigledistance()));
						nvps.add(new BasicNameValuePair("is_child_price", "on"));
						nvps.add(new BasicNameValuePair("child_price", m.line.getKidsprice()));
					} else {
						if (m.line.getCselect().equals("新网站")) {
							int tprice = Integer.parseInt(t.getMarketprice());
							// tprice = tprice + (new Random().nextInt(10) *
							// 10);
							nvps.add(new BasicNameValuePair("market_price", String.valueOf(tprice + fprice)));
						} else {
							nvps.add(new BasicNameValuePair("market_price", String.valueOf(Integer.parseInt(t.getMarketprice()) + fprice)));
						}
						try {
							if (m.line.isAvgpriceck()) {// 这个是传平均价
								nvps.add(new BasicNameValuePair("adult_price", String.valueOf(Integer.parseInt(t.getQunarprice()) + fprice)));
							} else if (m.line.isMarketpriceck()) {// 这个是传成人价
								nvps.add(new BasicNameValuePair("adult_price", String.valueOf(Integer.parseInt(t.getAdultprice()) + fprice)));
							}

						} catch (Exception e) {
							System.out.println("没有取到成人价格");
						}
						nvps.add(new BasicNameValuePair("room_send_price", t.getRoomsendprice()));
						if (!t.getChildprice().equals("")) {
							nvps.add(new BasicNameValuePair("is_child_price", "on"));
						} else {
							nvps.add(new BasicNameValuePair("is_child_price", ""));
						}
						nvps.add(new BasicNameValuePair("child_price", t.getChildprice()));
					}

					// 线路库存数的设置
					if (m.line.getStorenum() != null && m.line.getStorenum().length() > 0) {
						nvps.add(new BasicNameValuePair("count", m.line.getStorenum()));
					} else {
						nvps.add(new BasicNameValuePair("count", t.getAvailablecount().trim()));
					}

					nvps.add(new BasicNameValuePair("min_buy_count", m.line.getMinnumtime()));
					nvps.add(new BasicNameValuePair("max_buy_count", m.line.getMaxnumtime()));

					if (summary.getTeamno().indexOf("D") != -1) {
						nvps.add(new BasicNameValuePair("price_desc", m.line.getExcludefee()));
					} else {
						nvps.add(new BasicNameValuePair("price_desc", m.line.getIncludefee()));
					}
					nvps.add(new BasicNameValuePair("child_price_desc", m.line.getKidspricedesc()));
					nvps.add(new BasicNameValuePair("dateString", dt));

					// 用来记录上传的次数
					nvps.add(new BasicNameValuePair("sendcount", "1"));
					// 用来记录最后一次的上传时间
					nvps.add(new BasicNameValuePair("sendtime", String.valueOf(System.currentTimeMillis())));
					tlist.add(nvps);
				}

				// 这个不是上传参数，是用来判断上传了几次，目前是25次，如果超过这个次数，那么可能是去哪儿不支持这个数据，路过
				Map<String, List<NameValuePair>> tmap = new HashMap<String, List<NameValuePair>>();
				for (Iterator<List<NameValuePair>> it = tlist.iterator(); it.hasNext();) {
					List<NameValuePair> nv = it.next();
					int c = 0, qprice = 0;
					String dstr = "";
					for (NameValuePair np : nv) {
						if (np.getName().trim().equals("adult_price")) {
							qprice = Integer.parseInt(np.getValue());
							c++;
						}
						if (np.getName().trim().equals("dateString")) {
							dstr = np.getValue();
							c++;
						}
						if (c == 2) {
							break;
						}
					}
					if (tmap.containsKey(String.valueOf(qprice))) {
						nv = tmap.get(String.valueOf(qprice));
						for (int i = 0; i < nv.size(); i++) {
							NameValuePair np = nv.get(i);
							if (np.getName().trim().equals("dateString")) {
								nv.set(i, new BasicNameValuePair("dateString", np.getValue() + "," + dstr));
								break;
							}
						}
					}
					tmap.put(String.valueOf(qprice), nv);
				}

				Stack<List<NameValuePair>> nvsstack = new Stack<List<NameValuePair>>();
				for (String key : tmap.keySet()) {
					nvsstack.push(tmap.get(key));
				}

				while (true) {
					if (nvsstack.isEmpty()) {
						break;
					}
					nvps = nvsstack.pop();

					postLinePrice(nvps);
				}
			}
			updateUI("价格数据上传完成,如果还有，那在" + (m.line.getFailinterrupt()) + "秒后继续上传");
			// 间隔设置的秒数
			Thread.sleep(m.line.getFailinterrupt() * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 上传上次上传失败的价格数据，所有的失败的都放在最后上传
	public void postErrorPrice() {
		if (!BaseIni.nvslink.isEmpty()) {
			List<NameValuePair> nvps = BaseIni.nvslink.peek();
			for (NameValuePair np : nvps) {
				if (np.getName().trim().equals("sendtime")) {
					Long d = Long.parseLong(np.getValue());
					if ((System.currentTimeMillis() - d) / 1000 >= line.getFailinterrupt()) {
						updateUI("错误队列取出上传,还有" + BaseIni.nvslink.size() + "条");
						nvps = BaseIni.nvslink.poll();
						postLinePrice(nvps);
					} else {
						updateUI("等待" + (System.currentTimeMillis() - d) + "秒后上传,还有" + BaseIni.nvslink.size() + "条");
						try {
							Thread.sleep((System.currentTimeMillis() - d));
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
	}

	// 上传价格
	public void postLinePrice(List<NameValuePair> nvps) {
		String loginurl = "http://tb2cadmin.qunar.com/supplier/productTeamOperation.do";
		String rc = "";
		HttpPost httpost = null;
		HttpResponse response = null;
		try {
			httpost = createHttpPost(loginurl);
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
		}

		Random r = new Random(new Date().getTime());
		int waittime = 0;
		if (m.line.getSendwaitbegin() >= 0) {
			waittime = r.nextInt(m.line.getSendwaitend() - m.line.getSendwaitbegin()) + m.line.getSendwaitbegin();
			try {
				updateUI("等待时间："+(waittime)+"秒");
				Thread.sleep(waittime * 1000);
			} catch (InterruptedException e) {
			}
		}
		if (rc == null || rc.trim().equals("")) {
			int c = 0;
			int sf = 0;// 判断是否已经到3次了，如果不到那继续排队，如果到了，那移除
			for (int i = 0; i < nvps.size(); i++) {
				NameValuePair np = nvps.get(i);
				if (np.getName().trim().equals("sendtime")) {
					nvps.set(i, new BasicNameValuePair("sendtime", String.valueOf(System.currentTimeMillis())));
					c++;
					if (c == 2) {
						break;
					}
				}
				if (np.getName().trim().equals("sendcount")) {
					int ec = Integer.parseInt(np.getValue());
					if (ec < 3) {
						nvps.set(i, new BasicNameValuePair("sendcount", String.valueOf(Integer.parseInt(np.getValue()) + 1)));
						c++;
						if (c == 2) {
							break;
						}
					} else {
						sf = 1;
					}
				}
			}
			if (sf == 1) {
				BaseIni.nvslink.offer(nvps);
			} else {
				// 在这里可以把当前失败的上传记录下来
			}
			updateUI("上传失败，进入待传队列：已经存在" + BaseIni.nvslink.size() + "个　错误：" + rc);
		} else {
			updateUI("间隔了" + waittime + "秒：" + rc);
		}
	}

	public String postLineMarker() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		if (pid == null) {
			return null;
		}
		Summary summary = line.getSummary();
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			String pfunction = summary.getPfunction().trim();
			List<NameValuePair> nvps = null;

			// 上传行程数据
			updateUI("开始上传线路备注数据");
			// if (pfunction.trim().equals("group")) {
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "updateProductOthers"));
			nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
			nvps.add(new BasicNameValuePair("newFlag", "false"));
			nvps.add(new BasicNameValuePair("pId", pid));
			nvps.add(new BasicNameValuePair("b2bFlag", null));
			nvps.add(new BasicNameValuePair("haveFeeIncludeIcon", "1"));
			String sfu = com.bhc.util.StringUtils.parseCharacter(summary.getFeeinclude());
			sfu = sfu == null ? "" : sfu;
			System.out.println(sfu);
			StringBuffer sbs = new StringBuffer();
			StringBuffer sbs2 = new StringBuffer();
			if (!m.line.getCselect().equals("qly网站")) {
				for (int i = 0, j = 1; i < sfu.length(); i++) {
					String s = sfu.substring(i, i + 1);
					if (s.equals("-")) {
						if (sbs.length() > 0) {
							String tstr = sbs.toString().trim();
							if (!tstr.equals("")) {
								String tstr2 = tstr.substring(0, 1);
								String tstr3 = tstr.substring(2);
								if (StringUtils.isNumeric(tstr2)) {
									sbs2.append("<p data-oritext=\"" + j + "、" + tstr3 + "\">").append(j + "、" + tstr3).append("</p>");
								} else {
									sbs2.append("<p data-oritext=\"" + j + "、" + sbs + "\">").append(j + "、" + sbs).append("</p>");
								}
							}
							j++;
						}
						sbs.setLength(0);
					} else {
						sbs.append(s);
					}
					if (i + 1 == sfu.length()) {
						if (sbs.length() > 0) {
							String tstr = sbs.toString().trim();
							if (!tstr.equals("")) {
								String tstr2 = tstr.substring(0, 1);
								String tstr3 = tstr.substring(2);
								if (StringUtils.isNumeric(tstr2)) {
									sbs2.append("<p data-oritext=\"" + j + "、" + tstr3 + "\">").append(j + "、" + tstr3).append("</p>");
								} else {
									sbs2.append("<p data-oritext=\"" + j + "、" + sbs + "\">").append(j + "、" + sbs).append("</p>");
								}
							}
						}
						sbs.setLength(0);
					}
				}
				sfu = sbs2.toString();
			}

			sbs.setLength(0);
			for (int i = 0; i < sfu.length(); i++) {
				sbs.append(sfu.substring(i, i + 1));
				if (sbs.toString().endsWith("\\\\n")) {
					sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>");
				}
			}
			sfu = sbs.toString();
			sbs.setLength(0);

			while (true) {
				if (sfu.endsWith("<br/>")) {
					sfu = sfu.substring(0, sfu.lastIndexOf("<br/>"));
				} else {
					break;
				}
			}

			nvps.add(new BasicNameValuePair("fee_include", concatStr(com.bhc.util.StringUtils.parseCharacter(m.line.getSummary().getFeeinclude()), "{title}", sfu)));

			sfu = com.bhc.util.StringUtils.parseCharacter(summary.getFeeexclude());
			sfu = sfu == null ? "" : sfu;

			sbs.setLength(0);
			sbs2.setLength(0);
			if (!m.line.getCselect().equals("qly网站")) {
				for (int i = 0, j = 1; i < sfu.length(); i++) {
					String s = sfu.substring(i, i + 1);
					if (s.equals("-")) {
						if (sbs.length() > 0) {
							String tstr = sbs.toString().trim();
							if (!tstr.equals("")) {
								String tstr2 = tstr.substring(0, 1);
								String tstr3 = tstr.substring(2);
								if (StringUtils.isNumeric(tstr2)) {
									sbs2.append("<p data-oritext=\"" + j + "、" + tstr3 + "\">").append(j + "、" + tstr3).append("</p>");
								} else {
									sbs2.append("<p data-oritext=\"" + j + "、" + sbs + "\">").append(j + "、" + sbs).append("</p>");
								}
							}
							j++;
						}
						sbs.setLength(0);
					} else {
						sbs.append(s);
					}
					if (i + 1 == sfu.length()) {
						if (sbs.length() > 0) {
							String tstr = sbs.toString().trim();
							if (!tstr.equals("")) {
								String tstr2 = tstr.substring(0, 1);
								String tstr3 = tstr.substring(2);
								if (StringUtils.isNumeric(tstr2)) {
									sbs2.append("<p data-oritext=\"" + j + "、" + tstr3 + "\">").append(j + "、" + tstr3).append("</p>");
								} else {
									sbs2.append("<p data-oritext=\"" + j + "、" + sbs + "\">").append(j + "、" + sbs).append("</p>");
								}
							}
						}
						sbs.setLength(0);
					}
				}
				sfu = sbs2.toString();
			}

			for (int i = 0; i < sfu.length(); i++) {
				sbs.append(sfu.substring(i, i + 1));
				if (sbs.toString().endsWith("\\\\n")) {
					sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>");
				}
			}
			sfu = sbs.toString();
			sbs.setLength(0);

			while (true) {
				if (sfu.endsWith("<br/>")) {
					sfu = sfu.substring(0, sfu.lastIndexOf("<br/>"));
				} else {
					break;
				}
			}

			nvps.add(new BasicNameValuePair("fee_exclude", concatStr(m.line.getSummary().getFeeexclude(), "{title}", sfu)));
			nvps.add(new BasicNameValuePair("refund_policy_type", "2"));
			nvps.add(new BasicNameValuePair("advance_day_free", "0"));
			nvps.add(new BasicNameValuePair("advance_day_part", ""));
			nvps.add(new BasicNameValuePair("advance_day_all", ""));

			sfu = summary.getAttention();
			sfu = sfu == null ? "" : sfu;
			while (true) {
				if (sfu.indexOf("-") == -1) {
					break;
				}
				sfu = sfu.substring(0, sfu.indexOf("-")) + "<br/>" + sfu.substring(sfu.indexOf("-") + 1);
			}
			for (int i = 0; i < sfu.length(); i++) {
				sbs.append(sfu.substring(i, i + 1));
				if (sbs.toString().endsWith("\\\\n")) {
					sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>");
				}
			}
			sfu = sbs.toString();
			sbs.setLength(0);

			while (true) {
				if (sfu.endsWith("<br/>")) {
					sfu = sfu.substring(0, sfu.lastIndexOf("<br/>"));
				} else {
					break;
				}
			}
			sfu += com.bhc.util.StringUtils.parseCharacter(summary.getPrebook());
			nvps.add(new BasicNameValuePair("attention", concatStr(com.bhc.util.StringUtils.parseCharacter(m.line.getSummary().getAttention()), "{title}", sfu)));

			sfu = summary.getTip();
			sfu = sfu == null ? "" : sfu;
			while (true) {
				if (sfu.indexOf("-") == -1) {
					break;
				}
				sfu = sfu.substring(0, sfu.indexOf("-")) + "<br/>" + sfu.substring(sfu.indexOf("-") + 1);
			}

			for (int i = 0; i < sfu.length(); i++) {
				sbs.append(sfu.substring(i, i + 1));
				if (sbs.toString().endsWith("\\\\n")) {
					sbs.delete(sbs.length() - "\\\\n".length(), sbs.length()).append("<br/>");
				}
			}
			sfu = sbs.toString();
			sbs.setLength(0);

			while (true) {
				if (sfu.endsWith("<br/>")) {
					sfu = sfu.substring(0, sfu.lastIndexOf("<br/>"));
				} else {
					break;
				}
			}

			nvps.add(new BasicNameValuePair("tip", concatStr(m.line.getSummary().getTip(), "{title}", sfu)));
			// }
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					sb.setLength(0);
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("线路备注数据上传完成");
			// return rc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String postActInfo() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		if (pid == null) {
			return null;
		}
		Summary summary = line.getSummary();
		String loginurl = "http://tb2cadmin.qunar.com/supplier/generalActivity/participateActivity.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			String pfunction = summary.getPfunction().trim();
			List<NameValuePair> nvps = null;

			// 上传行程数据
			updateUI("开始上传线路优惠数据");
			// if (pfunction.trim().equals("group")) {
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("multi", ""));
			nvps.add(new BasicNameValuePair("pids", pid));

			SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

			// 多人立减
			String disbegindate = m.line.getDisbegindate();
			String disenddate = m.line.getDisenddate();
			String discount = m.line.getDiscount();

			if (disbegindate != null) {
				try {
					nvps.add(new BasicNameValuePair("reduce_start_date", fdf.format(sdf.parse(disbegindate))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("reduce_start_date", fdf.format(fdf.parse(disbegindate))));
				}
			}
			if (disenddate != null) {
				try {
					nvps.add(new BasicNameValuePair("reduce_end_date", fdf.format(sdf.parse(disenddate))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("reduce_end_date", fdf.format(fdf.parse(disenddate))));
				}
			}
			nvps.add(new BasicNameValuePair("promotion", discount));

			if (m.line.isReduceprice()) {
				nvps.add(new BasicNameValuePair("reduceprice", "1"));
			}

			// 早订优惠
			String earlystartdate = m.line.getEarlystartdate();
			String earlyenddate = m.line.getEarlyenddate();
			String earlybookday = m.line.getEarlybookday();
			String earlydiscount = m.line.getEarlydiscount();
			if (m.line.isEarlybookcheck()) {
				nvps.add(new BasicNameValuePair("early_book_check", "0"));
			}
			if (earlystartdate != null) {
				try {
					nvps.add(new BasicNameValuePair("early_start_date", fdf.format(sdf.parse(earlystartdate))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("early_start_date", fdf.format(fdf.parse(earlystartdate))));
				}
			}
			if (earlyenddate != null) {
				try {
					nvps.add(new BasicNameValuePair("early_end_date", fdf.format(sdf.parse(earlyenddate))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("early_end_date", fdf.format(fdf.parse(earlyenddate))));
				}
			}
			StringBuffer sb = new StringBuffer();
			if (earlybookday != null) {
				String ds[] = earlybookday.split("\r\n");
				String dis[] = earlydiscount.split("\r\n");
				// [{"day":"11","discount":"12"}]
				sb.append("[");
				for (int i = 0; i < ds.length; i++) {
					if (i == 0) {
						sb.append("{\"day\":\"" + ds[i] + "\",\"discount\":\"" + dis[i] + "\"}");
					} else {
						sb.append(",{\"day\":\"" + ds[i] + "\",\"discount\":\"" + dis[i] + "\"}");
					}
				}
				sb.append("]");
			}
			nvps.add(new BasicNameValuePair("early_book", sb.toString()));
			// 优惠促销
			String favorabledisplaystart = m.line.getFavorabledisplaystart();
			String favorabledisplayend = m.line.getFavorabledisplayend();
			if (m.line.isFavorabledisplaycheck()) {
				nvps.add(new BasicNameValuePair("favorable_display_check", "0"));
			}
			if (m.line.isFavorabledisplaytype1()) {
				nvps.add(new BasicNameValuePair("favorable_display_type", "0"));
			}
			if (m.line.isFavorabledisplaytype2()) {
				nvps.add(new BasicNameValuePair("favorable_display_type", "1"));
			}
			if (m.line.isFavorabledisplaytype3()) {
				nvps.add(new BasicNameValuePair("favorable_display_type", "2"));
			}
			if (favorabledisplaystart != null) {
				try {
					nvps.add(new BasicNameValuePair("favorable_display_start", fdf.format(sdf.parse(favorabledisplaystart))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("favorable_display_start", fdf.format(fdf.parse(favorabledisplaystart))));
				}
			}
			if (favorabledisplayend != null) {
				try {
					nvps.add(new BasicNameValuePair("favorable_display_end", fdf.format(sdf.parse(favorabledisplayend))));
				} catch (Exception e) {
					nvps.add(new BasicNameValuePair("favorable_display_end", fdf.format(fdf.parse(favorabledisplayend))));
				}
			}
			nvps.add(new BasicNameValuePair("favorable_des", m.line.getFavorabledes()));
			// }
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sbs = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sbs.append(new String(b, 0, i, "utf-8"));
					}
					rc = sbs.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("线路优惠数据上传完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void validateUpdate() {
		// http://tb2cadmin.qunar.com/supplier/product.do?method=validateUpdate&type=up&ids=698364580
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		updateUI("验证是否可以上架");

		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "validateUpdate").setParameter("ids", pid).setParameter("type", "up").build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);
		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					updateUI("" + content);
					Map dmap = readJson(content);
					if (CharacterSetToolkit.stringNotNull(dmap.get("ret")).trim().equals("1")) {
						isup = true;
					} else {
						isup = false;
					}
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("验证信息上架完毕");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("验证信息上架出错");
		} finally {
		}
	}

	// 取得去哪儿的酒店信息
	private String getQunarHotelInfo(String citycode, String hname) {
		// http://hs.qunar.com/api/hs/dujiahotel/typeahead?city_code=lijiang&hotel=
		String re = null;
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		updateUI("获取酒店信息");
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("hs.qunar.com").setPath("/api/hs/dujiahotel/typeahead").setParameter("city_code", citycode).setParameter("hotel", hname).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					re = sb.toString();
					updateUI("" + re);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("取出酒店信息完成");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("取出酒店信息出错:" + e.getMessage());
		} finally {
		}
		return re;
	}

	// 取得去哪儿的酒店详细信息
	private String getQunarHotelDetailInfo(String hcode) {
		// https://tb2cadmin.qunar.com/qhotel.do?method=baseInfo&seq=
		String re = null;
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		updateUI("获取酒店详细信息");
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/qhotel.do").setParameter("method", "baseInfo").setParameter("seq", hcode).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					re = sb.toString();
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("取出酒店详细信息完成");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("取出酒店详细信息出错:" + e.getMessage());
		} finally {
		}
		return re;
	}

	// 取得去哪儿的地区编码
	private String getQunarCityCode(String cityname) {
		// http://hsuggest.qunar.com/hotel_city_suggestion?city=丽江
		String re = null;
		if (!loginstatus) {
			updateUI("请先登录");
			return re;
		}
		updateUI("获取去哪儿地区编码");
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("hsuggest.qunar.com").setPath("/hotel_city_suggestion").setParameter("city", cityname).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					re = sb.toString();
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("取出地区编码完成");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("取出地区编码出错:" + e.getMessage());
		} finally {
		}
		return re;
	}

	// 查询数据
	private String analysisCityJson(String sjson, String cityname) {
		String ccode = null;
		try {
			JSONObject jo = new JSONObject(sjson);
			JSONArray ja = jo.getJSONArray("result");
			StringBuffer sbs = new StringBuffer("查询城市：" + cityname + "\t返回结果：");
			if (ja.length() > 0) {
				for (int i = 0; i < ja.length(); i++) {
					JSONObject tjo = ja.getJSONObject(i);
					String scname = tjo.getString("c");
					if (scname.trim().equals(cityname)) {
						ccode = tjo.getString("o");
					}
					sbs.append(scname).append(",");
				}
			}
			sbs.setLength(sbs.length() - 1);
			updateUI(sbs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ccode;
	}

	public void getPhontoForServer(Label lab_show_phono_txt, Combo com_s_p) {
		// http://tb2cadmin.qunar.com/supplier/product.do?method=validateUpdate&type=up&ids=698364580
		// http://tb2cadmin.qunar.com/supplier/product/product.jsp?p_function=freetrip&free_type=normal
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		updateUI("取出服务电话");
		String pfunction = "";
		try {
			pfunction = line.getSummary().getPfunction().trim();
		} catch (Exception e) {
			pfunction = "group";
		}
		if (pfunction == null) {
			return;
		}
		URI uri = null;
		try {
			if (pfunction.trim().toLowerCase().equals("group")) {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product/product.jsp").setParameter("p_function", "group").build();
			} else {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product/product.jsp").setParameter("p_function", "free_trip").setParameter("free_type", "normal").build();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			httpclient = HttpUtil.getHttpClient();
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					// updateUI("" + content);
					readServerPhone(content, lab_show_phono_txt, com_s_p);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("取出服务电话信息完成");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("取出服务电话信息出错");
		} finally {
		}
	}

	public void upLine() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		validateUpdate();
		if (!isup) {
			updateUI("数据不能上架");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			List<NameValuePair> nvps = null;
			// 数据上架
			updateUI("指定数据开始上架");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "updateStatus"));
			nvps.add(new BasicNameValuePair("type", "up"));
			nvps.add(new BasicNameValuePair("ids", pid));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("指定数据上架完成");
			// return rc;
		} catch (Exception e) {
			updateUI("指定数据上架出错");
			e.printStackTrace();
		}
		isup = false;
	}

	// /supplier/generalActivity/cancelActivity.do?ids=1811394922 HTTP/1.1
	public void cancelAct() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/generalActivity/cancelActivity.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			List<NameValuePair> nvps = null;
			// 数据上架
			updateUI("指定数据取消优惠");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("ids", pid));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("指定数据取消优惠成功");
		} catch (Exception e) {
			updateUI("指定数据取消优惠出错");
			e.printStackTrace();
		}
	}

	// 产品取消优惠
	public void downLine() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			List<NameValuePair> nvps = null;
			// 数据上架
			updateUI("指定数据开始下架");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "updateStatus"));
			nvps.add(new BasicNameValuePair("type", "down"));
			nvps.add(new BasicNameValuePair("ids", pid));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("数据下架：" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("指定数据下架完成");
		} catch (Exception e) {
			updateUI("指定数据下架出错");
			e.printStackTrace();
		}
	}

	// 设置产品的限购数量
	public void setLineLimiteCount() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/api/modifyProductBuyLimitCount.qunar";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			List<NameValuePair> nvps = null;
			// 数据上架
			updateUI("设置产品的限购数量");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("count", m.line.getLimitcount() + ""));
			nvps.add(new BasicNameValuePair("id", pid));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("返回信息：" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("设置产品的限购数量完成");
		} catch (Exception e) {
			updateUI("设置产品的限购数量出错");
			e.printStackTrace();
		}
	}

	public void delLine() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/product.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);

			List<NameValuePair> nvps = null;
			// 数据上架
			updateUI("开始删除数据");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "updateStatus"));
			nvps.add(new BasicNameValuePair("type", "delete"));
			nvps.add(new BasicNameValuePair("ids", pid));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("删除数据完成");
		} catch (Exception e) {
			updateUI("删除数据出错");
			e.printStackTrace();
		}
		pid = "";
	}

	public void downLineTeam() {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		String loginurl = "http://tb2cadmin.qunar.com/supplier/productTeamOperation.do";
		try {
			HttpPost httpost = createHttpPost(loginurl);
			List<NameValuePair> nvps = null;
			// 数据上架http://tb2cadmin.qunar.com/supplier/productTeamOperation.do?method=operatProductTeams&op=offline&pId=2428625279&dateString=2013-12-31%2C
			updateUI("开始删除价格数据");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "operatProductTeams"));
			nvps.add(new BasicNameValuePair("op", "offline"));
			nvps.add(new BasicNameValuePair("pId", pid));
			nvps.add(new BasicNameValuePair("dateString", dstrs));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String rc = "";
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					rc = sb.toString();
					updateUI("" + rc);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("删除数据完成");
		} catch (Exception e) {
			updateUI("删除数据出错");
			e.printStackTrace();
		}
	}

	public void downLoadImage(String ifile) {
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		try {
			HttpGet httpget = createHttpGet(ifile);
			GetThread gthread = new GetThread(httpclient, httpget);
			gthread.start();
			gthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getSupperid() {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		updateUI("开始获取供应商ID");

		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product/product.jsp").setParameter("p_function", "freetrip").setParameter("free_type", "normal").build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					if (content != null) {
						String bstr = "<input type=\"hidden\" name=\"supperid\"  value=\"";
						int sdi = content.indexOf(bstr);
						if (sdi != -1) {
							content = content.substring(sdi + bstr.length());
							int edi = content.indexOf("\"/>");
							supperid = content = content.substring(0, edi);
						}
					}
					updateUI("供应商ID：" + content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("获取地区信息完毕");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("获取地区信息出错");
		} finally {
			try {
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getStartAreaInfo(String aname) {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		updateUI("开始获取地区信息");

		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "mergeCityAndSightForRouteOrigin").setParameter("lang", "zh")
					.setParameter("sa", "true").setParameter("code", aname).setParameter("callback", "jQuery172012178607676759229_" + String.valueOf(new Date().getTime()))
					.setParameter("_", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					updateUI("" + content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("获取地区信息完毕");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("获取地区信息出错");
		} finally {
			try {
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getDestAreaInfo(String aname) {
		if (!loginstatus) {
			updateUI("请先登录");
			return null;
		}
		updateUI("开始获取地区信息");

		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product/arrivesuggest.qunar").setParameter("code", aname).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);

		HttpResponse response = null;
		StringBuffer sb = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					updateUI("" + content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
			updateUI("获取地区信息完毕");
		} catch (Exception e) {
			e.printStackTrace();
			updateUI("获取地区信息出错");
		} finally {
			try {
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void searchExistLine(String tno, String type, int cur) {
		tno = tno.trim();
		StringBuffer sb = new StringBuffer();
		int c = 0;// 计算取出来多少
		try {
			URI uri = null;
			try {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "searchProduct").setParameter("type", type).setParameter("departure", "")
						.setParameter("arrive", "").setParameter("tno", tno).setParameter("pid", "").setParameter("pageNo", String.valueOf(cur)).setParameter("perPageNo", "40").setParameter("orderBy", "")
						.setParameter("t", String.valueOf(new Date().getTime())).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpGet httpget = createHttpGet(uri);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
				} finally {
					if (instream != null) {
						instream.close();
						instream = null;
					}
				}
			}
			EntityUtils.consume(entity);
			if (sb.length() > 0) {
				Map dmap = readJson(sb.toString());
				if (dmap.get("ret").toString().trim().equals("1")) {
					LinkedHashMap<String, Object> lm = (LinkedHashMap<String, Object>) dmap.get("data");
					List<LinkedHashMap<String, Object>> lt = (List<LinkedHashMap<String, Object>>) lm.get("content");
					for (LinkedHashMap<String, Object> lms : lt) {
						String remotenno = lms.get("teamNo").toString().trim();
						if (remotenno.equals(tno)) {
							pid = lms.get("id").toString().trim();
							break;
						}
						c++;
					}
				} else {
					pid = "";
				}
			}
		} catch (Exception e) {
			updateUI(e.getMessage());
		}
		if (pid.equals("") && c == 20) {
			searchExistLine(tno, type, ++cur);
		}
	}

	// 删除用到，得到所有的重复数据
	public void getExistLines(String ptitle, String type, List<DupRecords> plist) {
		ptitle = ptitle.trim();
		StringBuffer sb = new StringBuffer();
		try {
			URI uri = null;
			try {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "searchProduct").setParameter("type", type).setParameter("departure", "")
						.setParameter("arrive", "").setParameter("title", ptitle).setParameter("pid", "").setParameter("pageNo", "1").setParameter("perPageNo", "40").setParameter("orderBy", "")
						.setParameter("t", String.valueOf(new Date().getTime())).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpGet httpget = createHttpGet(uri);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
				} finally {
					if (instream != null) {
						instream.close();
						instream = null;
					}
				}
			}
			EntityUtils.consume(entity);
			if (sb.length() > 0) {
				Map dmap = readJson(sb.toString());
				if (dmap.get("ret").toString().trim().equals("1")) {
					LinkedHashMap<String, Object> lm = (LinkedHashMap<String, Object>) dmap.get("data");
					List<LinkedHashMap<String, Object>> lt = (List<LinkedHashMap<String, Object>>) lm.get("content");
					for (LinkedHashMap<String, Object> lms : lt) {
						String remotenno = lms.get("teamNo").toString().trim();
						if (remotenno.equals(ptitle)) {
							DupRecords dr = new DupRecords();
							dr.setTeamno(ptitle);
							dr.setCtime(lms.get("createTime").toString().replace("\n", "-"));
							dr.setPid(lms.get("id").toString().trim());
							plist.add(dr);
						}
					}
				}
			}
		} catch (Exception e) {
			updateUI(e.getMessage());
		}
	}

	public void getExistLines(String teamno) {
		teamno = teamno.trim();
		List<DupRecords> mlist = new ArrayList<DupRecords>();
		getExistLines(teamno, "warehouse", mlist);
		getExistLines(teamno, "sell", mlist);
		if (mlist.size() > 1) {
			ComparatorLine comparator = new ComparatorLine();
			Collections.sort(mlist, comparator);
			for (int i = 1; i < mlist.size() - 1; i++) {
				DupRecords drc = mlist.get(i);
				pid = drc.getPid();
				delLine();
				pid = "";
			}
		}
	}

	public StringBuffer getHtmlContent(String furl) {
		StringBuffer sb = null;
		HttpGet httpget = null;
		HttpResponse response;
		try {
			httpget = createHttpGet(furl);
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

	public StringBuffer getHtml(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 分别读纯文本和链接.
	 * 
	 * @param result
	 *            网页的内容
	 * @throws Exception
	 */
	public void readLink(StringBuffer result, List<String> list) {
		if (result == null)
			return;
		Parser parser;
		NodeList nodelist = null;
		parser = Parser.createParser(result.toString(), "utf8");
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { linkFilter });
		try {
			nodelist = parser.parse(lastFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		if (nodelist == null)
			return;
		Node[] nodes = nodelist.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (node instanceof LinkTag) {
				LinkTag lt = (LinkTag) node;
				String tag = lt.getLink();
				if (tag.startsWith("CityLineToXmlQnTTL")) {
					list.add("http://yn.gayosite.com/kuxun_api/" + lt.getLink());
				} else if (m.line.getCselect().equals("新网站")) {
					list.add(lt.getLink());
				} else if (m.line.getCselect().equals("qly网站")) {
					list.add(lt.getLink());
				}
			}
		}
	}

	public void searchExistLineTeams(String dstr) {
		dstr = dstr.trim();
		StringBuffer sb = new StringBuffer();
		try {
			URI uri = null;
			try {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product/getProductTeamsByMonth.jsp").setParameter("pId", pid).setParameter("ym", dstr).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpGet httpget = createHttpGet(uri);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
				} finally {
					if (instream != null) {
						instream.close();
						instream = null;
					}
				}
			}
			EntityUtils.consume(entity);
			if (sb.length() > 0) {
				Map dmap = readJson(sb.toString());
				// 如果没有读取到信息，那么返回
				if (dmap == null)
					return;
				if (dmap.get("ret").toString().trim().equals("1")) {
					LinkedHashMap<String, Object> lm = (LinkedHashMap<String, Object>) dmap.get("data");
					List<LinkedHashMap<String, Object>> lt = (List<LinkedHashMap<String, Object>>) lm.get("content");
					dstrs = "";
					StringBuffer sbs = new StringBuffer("");
					for (LinkedHashMap<String, Object> lms : lt) {
						String cdate = lms.get("date").toString().trim();
						if (sbs.length() == 0) {
							sbs.append(cdate);
						} else {
							sbs.append("," + cdate);
						}
					}
					dstrs = sbs.toString();
					if (dstrs != null && dstrs.trim().length() > 0) {
						downLineTeam();
					}
				} else {
					updateUI("获取价格数据出错");
				}
			}
		} catch (Exception e) {
			updateUI(e.getMessage());
		}
	}

	public HttpGet createHttpGet(URI uri) {
		HttpGet httpget = new HttpGet(uri);
		httpget.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpget.addHeader("Connection", "keep-alive");
		httpget.addHeader("Cache-Control", "max-age=0");
		httpget.addHeader("Cookie", cookie);

		for (Cookie c : cookies) {
			httpget.addHeader(c.getName(), c.getValue());
		}
		return httpget;
	}

	public HttpGet createHttpGet(String url) {
		url = url.replace("\\", "/");
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.addHeader("Accept-Charset", "utf-8,GB2312;q=0.7,*;q=0.7");
		httpget.addHeader("Connection", "keep-alive");
		httpget.addHeader("Cache-Control", "max-age=0");
		httpget.addHeader("Cookie", cookie);
		for (Cookie c : cookies) {
			httpget.addHeader(c.getName(), c.getValue());
		}
		return httpget;
	}

	public HttpPost createHttpPost(String url) {
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
		httpost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpost.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpost.addHeader("Accept-Charset", "utf-8,GB2312;q=0.7,*;q=0.7");
		httpost.addHeader("Connection", "keep-alive");
		httpost.addHeader("Cache-Control", "max-age=0");
		httpost.addHeader("Cookie", cookie);
		for (Cookie c : cookies) {
			httpost.addHeader(c.getName(), c.getValue());
		}
		return httpost;
	}

	public HttpPost createHttpsPost(String url) {
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("Host", "tb2cadmin.qunar.com");
		httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
		httpost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpost.addHeader("Accept-Encoding", "gzip, deflate");
		httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpost.addHeader("Referer", "https://tb2cadmin.qunar.com/supplier/product/product_free_trip.jsp?pId=2625655754&newFlag=false&ret=%2Fsupplier%2Fproduct%2Fproduct_warehouse.jsp&canEdit=null");
		httpost.addHeader("Pragma", "no-cache");
		httpost.addHeader("Connection", "keep-alive");
		httpost.addHeader("Cache-Control", "no-cache");
		httpost.addHeader("Cookie", cookie);
		// for (Cookie c : cookies) {
		// httpost.addHeader(c.getName(), c.getValue());
		// }
		return httpost;
	}

	public void writImage(byte[] b, String spath) {
		try {
			byte[] data = b;
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			File imageFile = new File(spath);
			if (!imageFile.exists()) {
				imageFile.mkdirs();
				imageFile.delete();
			}
			imageFile.createNewFile();

			// 创建输出流
			FileOutputStream outStream = new FileOutputStream(imageFile);
			// 写入数据
			outStream.write(data);
			// 关闭输出流
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	public String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	private Map readJson(String str) {
		if (!str.equals("-")) {
			JsonGenerator generator = null;
			ObjectMapper mapper = null;
			mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			try {
				generator = mapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				Map json = mapper.readValue(str, Map.class);
				if (json != null) {
					return json;
				}
			} catch (IOException e) {
				updateUI(str);
			}
		}
		return null;
	}

	public class GetThread extends Thread {

		private final HttpClient httpClients;
		private final HttpContext context;
		private final HttpGet httpget;

		public GetThread(HttpClient httpClient, HttpGet httpget) {
			this.httpClients = httpClient;
			this.context = HttpClientContext.create();
			this.httpget = httpget;
			downover = false;
		}

		@Override
		public void run() {
			try {
				updateUI("开获取图片..." + httpget.getRequestLine());
				HttpResponse response = httpClients.execute(httpget, context);
				try {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream instream = entity.getContent();
						try {
							writImage(readInputStream(instream), tempimg);
							if (m.line.isWaterck()) {
								// 给图片打水印
								Random rd = new Random();
								int rimg = rd.nextInt(6);
								String waterprint = "";
								switch (rimg) {
									case 1:
										waterprint = m.line.getSpic1();
										break;
									case 2:
										waterprint = m.line.getSpic2();
										break;
									case 3:
										waterprint = m.line.getSpic3();
										break;
									case 4:
										waterprint = m.line.getSpic4();
										break;
									case 5:
										waterprint = m.line.getSpic5();
										break;
									case 6:
										waterprint = m.line.getSpic6();
										break;
								}
								ImageUtils.pressImage(tempimg, waterprint, 0, 0, 0.8f);
							}
						} catch (Exception e) {

						} finally {
							instream.close();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException ex) {
				updateUI("获取图片出错");
			} catch (IOException ex) {
				updateUI("获取图片出错");
			}
			downover = true;
		}
	}

	public void updateUI(final String msg) {
		new Thread(new Runnable() {
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						synchronized (this) {
							Main.log_txt.append("\r\n" + msg);
						}
					}
				});
			}
		}).start();
	}

	public String concatStr(String s, String c, String d) {
		if (s == null || s.trim().equals(""))
			return d;
		if (c == null || c.trim().equals(""))
			return s;
		int ci = s.indexOf(c);
		if (ci != -1) {
			String pre = s.substring(0, s.indexOf(c));
			String fix = s.substring(s.indexOf(c) + c.length());
			s = pre + d + fix;
		}
		s = s.replace("%20", "&nbsp;").replace("%21", "&mdash;").replace("%22", "&ldquo;").replace("%23", "&rdquo;").replace("%24", "&bull;");
		return s;
	}

	@SuppressWarnings("unchecked")
	public void listFiles(String fdir, @SuppressWarnings("rawtypes") List tlist) {
		// long a = System.currentTimeMillis();
		@SuppressWarnings("rawtypes")
		LinkedList list = new LinkedList();
		File dir = new File(fdir);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				list.add(file[i]);
			} else {
				addFileToList(tlist, file[i]);
			}
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = (File) list.removeFirst();
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);
					else {
						addFileToList(tlist, file[i]);
					}
				}
			} else {
				addFileToList(tlist, tmp);
			}
		}
		// System.out.println(System.currentTimeMillis() - a);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addFileToList(List tlist, File file) {
		if (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".gif")) {
			tlist.add(file.getAbsolutePath());
		}
	}

	/**
	 * 读取服务电话
	 */
	public void readServerPhone(String result, Label lab_show_phono_txt, Combo com_s_p) throws Exception {
		NodeList nodelist;
		Parser parser = Parser.createParser(result, "utf8");
		NodeFilter linkFilter = new NodeClassFilter(SelectTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { linkFilter });
		nodelist = parser.parse(lastFilter);
		Node[] nodes = nodelist.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (node instanceof SelectTag) {
				SelectTag lt = (SelectTag) node;
				String tag = lt.getAttribute("name");
				if (tag.startsWith("phoneinfo")) {
					NodeList nls = lt.getChildren();
					int c = 0;
					StringBuffer sb = new StringBuffer();
					List<String> tlist = new ArrayList<String>();
					for (int n = 0; n < nls.size(); n++) {
						Node nd = nls.elementAt(n);
						if (nd instanceof OptionTag) {
							String str = ((OptionTag) nd).getValue() + "\t" + ((OptionTag) nd).getChild(0).getText().trim().replaceAll("\\n", "-").replace(" ", "");
							updateUI(str);
							sb.append(str).append("\r\n");
							tlist.add(((OptionTag) nd).getValue());
							c++;
						}
					}
					String[] ops = new String[c];
					for (int v = 0; v < tlist.size(); v++) {
						String str = tlist.get(v);
						ops[v] = str;
					}
					lab_show_phono_txt.setText(sb.toString());
					com_s_p.setItems(ops);
				}
			}
		}
	}

	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	public String getValCode() {
		return valCode;
	}

	public void setValCode(String valCode) {
		this.valCode = valCode;
	}

	public boolean getLoginstatus() {
		return loginstatus;
	}

	public void setLoginstatus(boolean loginstatus) {
		this.loginstatus = loginstatus;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
