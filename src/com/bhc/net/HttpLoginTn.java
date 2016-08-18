package com.bhc.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Combo;
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
import org.htmlparser.util.SimpleNodeIterator;

import com.bhc.Main;
import com.bhc.bean.Day;
import com.bhc.bean.DupRecords;
import com.bhc.bean.Line;
import com.bhc.bean.Summary;
import com.bhc.bean.Team;
import com.bhc.util.ComparatorLine;
import com.bhc.util.ImageUtils;
import com.sun.xml.internal.messaging.saaj.util.Base64;

public class HttpLoginTn {
	private boolean downover = true;
	private String realpath = "";
	private String validatecode = "";
	private String tempimg = "";
	private CloseableHttpClient httpclient = null;
	private String valCode = "";
	private boolean loginstatus = false;
	public static String JSESSIONIDNB = "";
	public static Header[] ghs;
	private Line line;
	private Header firstheader;
	private Main m;
	private String pid = "", supperid = "";
	private boolean isup = false;
	private ParseXmlDo pxd;
	private ParseXmlDoNew pxdnew;
	private String dstrs;

	public HttpLoginTn(Main m) {
		super();
		realpath = System.getProperty("user.dir");// user.dir指定了当前的路径
		pxd = new ParseXmlDo();
		pxdnew = new ParseXmlDoNew();
		getHttpClient();
		this.m = m;
	}

	public static void main(String[] args) {
		try {
			// HttpLogin hl = new HttpLogin(new Main(Display.getDefault()));
			// hl.downLoadImage("http://yn.gayosite.com/ad_pic/yn_lj6.jpg");
			// while (!hl.loginstatus) {
			// hl.valCode = "";
			// hl.executeLogin();
			// }
			// List<String> tlist=new ArrayList<String>();
			// hl.listFiles("f:/gj/",tlist);
			// for(String str:tlist){
			// System.out.println(str);
			// }
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

		SSLSocketFactory sslsf = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File(System.getProperty("user.dir") + "/my.keystore"));
			try {
				trustStore.load(instream, "nopassword".toCharArray());
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();

			sslsf = new SSLSocketFactory(sslcontext, SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setKeepAliveStrategy(keepAliveStrat).setConnectionManager(cm).build();

	}

	public void executeLogin() {
		getVimage();
		try {
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			try {
				valCode = br.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(valCode);
		loginForm();
		loginRedirect();
		postLine();
	}

	public void getVimage() {
		updateUI("开始获取验证码");
		String url = new String();
		url = "http://www.tuniu.cn/restful/validate-num-image/query?" + Math.random();
		HttpGet httpget = createHttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			ghs = response.getHeaders("Set-Cookie");
			// "Set-Cookie"
			firstheader = response.getFirstHeader("Set-Cookie");
			if (firstheader != null) {
				String headerCookie = firstheader.getValue();
				JSESSIONIDNB = headerCookie.substring(headerCookie.indexOf("JSESSIONIDNB=") + ("JSESSIONIDNB=".length()), headerCookie.indexOf(";"));
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					validatecode = realpath + "/tnv.jpg";
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
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void loginForm() {
		updateUI("开始初始化登录数据");

		String uname = m.line.getTnuname();
		String pwd = m.line.getTnpwd();
		String tnrand = m.line.getTnrand();

		String par = "{\"r\":" + Math.random() + ",\"loginName\":\"" + uname + "\",\"password\":\"" + pwd + "\",\"security\":\"" + tnrand + "\"}";
		byte[] para = Base64.encode(par.getBytes());
		par = new String(para);
		String loginurl = "http://www.tuniu.cn/restful/login/logon?" + par;

		try {

			// MultipartEntity reqEntity = new MultipartEntity();
			// httpost.setEntity(reqEntity);
			// ContentBody cb=new ByteArrayBody(para,"Filedata");
			// reqEntity.addPart("Filedata", cb);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("loginName", uname));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("security", tnrand));
			String param = URLEncodedUtils.format(nvps, "UTF-8");
			URI uri = URIUtils.createURI("http", "www.tuniu.cn", 80, "/restful/login/logon", param, null);
			HttpPost httpost = createHttpPost(uri);
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "companyName=;JSESSIONIDNB=" + JSESSIONIDNB + ";JSESSIONIDBB=" + JSESSIONIDNB);
			httpost.addHeader("Referer", "http://www.tuniu.cn/nbooking/login.html");
			// httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			updateUI("执行登录");
			HttpResponse response = httpclient.execute(httpost);

			Browser.clearSessions();

			Header[] hs = response.getHeaders("Set-Cookie");
			Header[] ths = new Header[hs.length + ghs.length];
			int i = 0;
			for (; i < ghs.length; i++) {
				ths[i] = ghs[i];
				String[] temcookie = ghs[i].getValue().split(";");
				for (int f = 0; f < temcookie.length; f++) {
					Browser.setCookie(temcookie[f], "http://seller.tuniu.cn");
				}
				System.out.println("已发送" + ths[i]);
			}
			for (int j = i; j < ths.length; j++) {
				ths[j] = hs[j - i];
				String[] temcookie = hs[j - i].getValue().split(";");
				for (int f = 0; f < temcookie.length; f++) {
					Browser.setCookie(temcookie[f], "http://seller.tuniu.cn");
				}
				System.out.println(ths[j]);
			}
			ghs = ths.clone();
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
					String content = new String(com.sun.org.apache.xml.internal.security.utils.Base64.decode(sb.toString()), "utf-8");
					if (m.line.isHttpmodel()) {
						if (!content.equals("")) {
							String tstr = content.substring(content.indexOf("msg"));
							tstr = tstr.substring(0, tstr.indexOf(":"));
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
		if (m.line.isHttpsmodel()) {
			loginRedirect();
		}
	}

	public void loginRedirect() {
		updateUI("开始初始化登录数据");
		String loginurl = "http://user.qunar.com/webApi/proxy.jsp";
		try {
			HttpPost httpost = createHttpPost(loginurl);
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

			updateUI("执行登录");
			HttpResponse response = httpclient.execute(httpost);

			Browser.clearSessions();

			Header[] hs = response.getHeaders("Set-Cookie");
			Header[] ths = new Header[hs.length + ghs.length];
			int i = 0;
			for (; i < ghs.length; i++) {
				ths[i] = ghs[i];
				String[] temcookie = ghs[i].getValue().split(";");
				for (int f = 0; f < temcookie.length; f++) {
					Browser.setCookie(temcookie[f], "http://tb2cadmin.qunar.com");
				}
				System.out.println("已发送" + ths[i]);
			}
			for (int j = i; j < ths.length; j++) {
				ths[j] = hs[j - i];
				String[] temcookie = hs[j - i].getValue().split(";");
				for (int f = 0; f < temcookie.length; f++) {
					Browser.setCookie(temcookie[f], "http://tb2cadmin.qunar.com");
				}
				System.out.println(ths[j]);
			}

			ghs = ths.clone();
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
					// if (content.equals("")) {
					loginstatus = true;
					updateUI("登录完成");
					// } else {
					// updateUI("登录失败" + content);
					// }
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
		if (!loginstatus) {
			updateUI("请先登录");
		}
		String url = "http://tb2cadmin.qunar.com/supplier/flashUpload.action";
		String filePath = "F://cwb/aec379310a5.jpg";
		if (ifile != null) {
			filePath = ifile;
		}

		HttpPost httpPost = new HttpPost(url);

		MultipartEntity reqEntity = new MultipartEntity();
		httpPost.setEntity(reqEntity);

		/** file param name */
		FileBody bin = new FileBody(new File(filePath));
		reqEntity.addPart("Filedata", bin);
		try {
			/** String param name */
			StringBody userId = new StringBody("1");
			reqEntity.addPart("userId", userId);

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

	public void getSourceLine(String url) {
		if (m.line.getCselect().equals("新网站")) {
			pxdnew.getHurl(url);
			line = pxdnew.getLine();
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
						downLine();
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
			if (m.line.isC1up()) {
				upLine();
			} else if (oup) {
				upLine();
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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

			String pfunction = summary.getPfunction().trim();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 取得目的地
			StringBuffer arrive = new StringBuffer("");
			String[] ta = summary.getArrive().split(",");
			if (ta != null && ta.length > 0) {
				int i = 0;
				String pstr = "";
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
					} catch (Exception e) {
						updateUI(e.getMessage());
					}
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
			String mainpic = "";
			// 图库
			String images = "[";

			String img = summary.getImage();
			String[] imgs = img.split(",");
			Random r = new Random();
			int rm = r.nextInt(imgs.length);
			if (imgs != null) {
				if (m.line.isC1nomain()) {
					for (int i = 0; i < imgs.length; i++) {
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
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"1\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com" + iurl + "\"}";
								} else {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"0\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com" + iurl + "\"}";
								}
							} else {
								images += ",";
								if (rm == i) {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"1\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com" + iurl + "\"}";
								} else {
									images += "{\"imgUrl\":\"" + iurl + "\",\"ret\":" + "1,\"errmsg\":\"\",\"isHttp\":\"0\",\"type\":\"0\",\"desc\":\"\",\"id\":\"" + iurl + "\",\"imageUrl\":\"http://img1.qunarzz.com" + iurl + "\"}";
								}
							}
							if (rm == i) {
								mainpic = iurl;
							}
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
							mainpic = iurl;
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
				nvps.add(new BasicNameValuePair("mainpic", mainpic));
				nvps.add(new BasicNameValuePair("images", images));
			} else {
				nvps.add(new BasicNameValuePair("method", "updateProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "false"));
				nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
				if (m.line.isC1nomain()) {
					nvps.add(new BasicNameValuePair("mainpic", mainpic));
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

			// 类别
			String type = "";
			String ttype = summary.getArrivetype().trim();
			if (ttype.equals("国内游") || ttype.equals("周边旅游")) {
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
			// 产品副标题
			nvps.add(new BasicNameValuePair("recommendation", concatStr(m.line.getSummary().getRecommendation().trim(), "{title}", summary.getRecommendation())));

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
			nvps.add(new BasicNameValuePair("feature", featurecon));

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
				// 出发地*
				nvps.add(new BasicNameValuePair("departure", concatStr(m.line.getSummary().getDeparture(), "{title}", summary.getDeparture().trim())));
				// 目的地*
				nvps.add(new BasicNameValuePair("freeArrive", "云南-昆明"));
				// 利润率
				nvps.add(new BasicNameValuePair("profit", "0"));
			}

			// for (NameValuePair bp : nvps) {
			// System.out.println(bp.getName() + ":" + bp.getValue());
			// }

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
							if (rm == i) {
								mainpic = iurl;
							}
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
				nvps.add(new BasicNameValuePair("mainpic", mainpic));
				nvps.add(new BasicNameValuePair("images", images));
			} else {
				nvps.add(new BasicNameValuePair("method", "updateProductBaseInfo"));
				nvps.add(new BasicNameValuePair("newFlag", "false"));
				nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
				if (m.line.isC1noimg()) {
					nvps.add(new BasicNameValuePair("mainpic", mainpic));
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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);
			String pfunction = summary.getPfunction().trim();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// 上传行程数据
			updateUI("开始上传行程数据");
			nvps.clear();
			// if(pfunction.trim().equals("group")){
			nvps.add(new BasicNameValuePair("method", "updatDailySchedule2"));
			nvps.add(new BasicNameValuePair("pId", pid));
			nvps.add(new BasicNameValuePair("ret", "/supplier/product/product_warehouse.jsp"));
			nvps.add(new BasicNameValuePair("next", "false"));
			nvps.add(new BasicNameValuePair("newFlag", "false"));
			nvps.add(new BasicNameValuePair("radio", "radio"));
			List<Day> list = line.getDays();
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Day d = list.get(j);

					String dst = d.getDaydescription();
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
							StringBuffer sbs = new StringBuffer();
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
							if (m.line.getCselect().equals("新网站")) {
								nvps.add(new BasicNameValuePair("images_" + (j + 1), ""));
							} else {
								nvps.add(new BasicNameValuePair("images_" + (j + 1), dayimgs));
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

					nvps.add(new BasicNameValuePair("day_" + (j + 1), d.getDaynum()));
					nvps.add(new BasicNameValuePair("day_title_" + (j + 1), d.getDaytitle()));
					nvps.add(new BasicNameValuePair("day_hotel_star_" + (j + 1), d.getStarname()));
					nvps.add(new BasicNameValuePair("day_hotel_desc_" + (j + 1), d.getStardesc()));

					if (m.line.getCselect().equals("新网站")) {
						if (dayimgs != null && dayimgs.length() > 10) {
							nvps.add(new BasicNameValuePair("images_" + (j + 1), ""));
							String[] imgs = dayimgs.split(",");
							if (imgs.length > 0) {
								StringBuffer sb = new StringBuffer();
								int c = 0;
								for (c = 0; c < imgs.length; c++) {
									if (c % 2 == 0) {
										sb.append("<div style=\"margin:0px 0px 3px 0px;\">");
									}
									sb.append("<img style=\"width:49%;clear:both\" src=\"http://img1.qunarzz.com" + imgs[c] + "\">");
									if (c % 2 == 1) {
										sb.append("</div>\r\n");
									}
								}
								if (c % 2 != 0) {
									sb.append("</div>\r\n");
								}
								String tstr = m.line.getTxtdiscription();
								String txtdesc = "";
								if (tstr.indexOf("{imgposition}") != -1) {
									txtdesc = concatStr(daydescription.toString(), "{imgposition}", sb.toString());

								} else {
									txtdesc = daydescription.toString() + sb.toString();
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

					try {
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
					} catch (Exception e) {
						updateUI("日程交通工具上传出错");
					}
				}
			}
			// }

			for (NameValuePair bp : nvps) {
				if (bp.getName().startsWith("description_")) {
					System.out.println(bp.getName() + ":" + bp.getValue());
				}
			}

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
		String loginurl = "http://tb2cadmin.qunar.com/supplier/productTeamOperation.do";
		try {
			HttpPost httpost = null;
			HttpResponse response = null;
			String pfunction = summary.getPfunction().trim();
			List<NameValuePair> nvps = null;

			// 上传行程数据
			updateUI("开始上传价格数据");
			// if(pfunction.trim().equals("group")){

			boolean bs = false;
			if (m.line.getPricedates().size() > 0) {
				bs = true;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			for (int i = 0; i < 6; i++) {// 先把以前的价格下架
				String dstr = sdf.format(calendar.getTime());
				searchExistLineTeams(dstr);
				calendar.add(calendar.MONTH, 1);
			}

			List<Team> list = line.getTeams();
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Team t = list.get(j);
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
					httpost = createHttpPost(loginurl);
					for (Header hd : ghs) {
						httpost.addHeader(hd);
					}
					httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
					if (m.line.isChangespecial()) {
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
						if (ldate.getTime() >= sdate.getTime() && ldate.getTime() < edate.getTime()) {
							fprice += Integer.parseInt(m.line.getPriceratedate());
						}
					}

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
								fprice += Integer.parseInt(m.line.getPriceratedate3());
							}
						} catch (Exception e) {

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
						nvps.add(new BasicNameValuePair("adult_price", String.valueOf(Integer.parseInt(t.getAdultprice()) + fprice)));
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
					httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

					for (NameValuePair bp : nvps) {
						System.out.println(bp.getName() + ":" + bp.getValue());
					}

					response = httpclient.execute(httpost);
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
				}
			}
			// }

			updateUI("价格数据上传完成");
			// return rc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			String sfu = summary.getFeeinclude();
			sfu = sfu == null ? "" : sfu;
			while (true) {
				if (sfu.indexOf("-") == -1) {
					break;
				}
				sfu = sfu.substring(0, sfu.indexOf("-")) + "<br/>" + sfu.substring(sfu.indexOf("-") + 1);
			}
			StringBuffer sbs = new StringBuffer();
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

			nvps.add(new BasicNameValuePair("fee_include", concatStr(m.line.getSummary().getFeeinclude(), "{title}", sfu)));

			sfu = summary.getFeeexclude();
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
			nvps.add(new BasicNameValuePair("attention", concatStr(m.line.getSummary().getAttention(), "{title}", sfu)));

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
		for (Header hd : ghs) {
			httpget.addHeader(hd);
		}
		httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

		CloseableHttpResponse response = null;
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
					if (dmap.get("ret").toString().trim().equals("1")) {
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
			try {
				if (response != null)
					response.close();
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getPhontoForServer(Label lab_show_phono_txt, Combo com_s_p) {
		// http://tb2cadmin.qunar.com/supplier/product.do?method=validateUpdate&type=up&ids=698364580
		// http://tb2cadmin.qunar.com/supplier/product/product.jsp?p_function=freetrip&free_type=normal
		if (!loginstatus) {
			updateUI("请先登录");
			return;
		}
		updateUI("得到服务电话");
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
		for (Header hd : ghs) {
			httpget.addHeader(hd);
		}
		httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

		CloseableHttpResponse response = null;
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
			try {
				if (response != null)
					response.close();
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
			for (Header hd : ghs) {
				httpost.addHeader(hd);
			}
			httpost.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

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
		for (Header hd : ghs) {
			httpget.addHeader(hd);
		}
		httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

		CloseableHttpResponse response = null;
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
				if (response != null)
					response.close();
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
			uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "mergeCityAndSightForRouteOrigin").setParameter("lang", "zh").setParameter("sa", "true")
					.setParameter("code", aname).setParameter("callback", "jQuery172012178607676759229_" + String.valueOf(new Date().getTime())).setParameter("_", String.valueOf(new Date().getTime())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet httpget = createHttpGet(uri);
		for (Header hd : ghs) {
			httpget.addHeader(hd);
		}
		httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

		CloseableHttpResponse response = null;
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
				if (response != null)
					response.close();
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
		for (Header hd : ghs) {
			httpget.addHeader(hd);
		}
		httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);

		CloseableHttpResponse response = null;
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
				if (response != null)
					response.close();
				if (instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void searchExistLine(String ptitle, String type, int cur) {
		ptitle = ptitle.trim();
		StringBuffer sb = new StringBuffer();
		int c = 0;// 计算取出来多少
		try {
			URI uri = null;
			try {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "searchProduct").setParameter("type", type).setParameter("departure", "").setParameter("arrive", "")
						.setParameter("title", ptitle).setParameter("pid", "").setParameter("pageNo", String.valueOf(cur)).setParameter("perPageNo", "40").setParameter("orderBy", "").setParameter("t", String.valueOf(new Date().getTime())).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpGet httpget = createHttpGet(uri);
			for (Header hd : ghs) {
				httpget.addHeader(hd);
			}
			httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);
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
					System.out.println(sb.toString());
					for (LinkedHashMap<String, Object> lms : lt) {
						String remotenno = lms.get("teamNo").toString().trim();
						System.out.println(remotenno + "\t" + ptitle);
						if (remotenno.equals(ptitle)) {
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
			searchExistLine(ptitle, type, ++cur);
		}
	}

	// 删除用到，得到所有的重复数据
	public void getExistLines(String ptitle, String type, List<DupRecords> plist) {
		ptitle = ptitle.trim();
		StringBuffer sb = new StringBuffer();
		try {
			URI uri = null;
			try {
				uri = new URIBuilder().setScheme("http").setHost("tb2cadmin.qunar.com").setPath("/supplier/product.do").setParameter("method", "searchProduct").setParameter("type", type).setParameter("departure", "").setParameter("arrive", "")
						.setParameter("title", ptitle).setParameter("pid", "").setParameter("pageNo", "1").setParameter("perPageNo", "40").setParameter("orderBy", "").setParameter("t", String.valueOf(new Date().getTime())).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpGet httpget = createHttpGet(uri);
			for (Header hd : ghs) {
				httpget.addHeader(hd);
			}
			httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);
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
		try {
			HttpGet httpget = createHttpGet(furl);
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
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 分别读纯文本和链接.
	 * 
	 * @param result
	 *            网页的内容
	 * @throws Exception
	 */
	public void readLink(StringBuffer result, List<String> list) {
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
					list.add("http://s-58391.gotocdn.com/kuxun_api/" + lt.getLink());
				} else if (m.line.getCselect().equals("新网站")) {
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
			for (Header hd : ghs) {
				httpget.addHeader(hd);
			}
			httpget.addHeader("Cookie", "JSESSIONIDNB=" + JSESSIONIDNB);
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
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.0 Safari/537.36");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpget.addHeader("X-DevTools-Emulate-Network-Conditions-Client-Id", "DBFB3F80-1386-4E7A-BF2E-27057AE7EF3C");
		httpget.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.addHeader("Accept-Encoding", "gzip,deflate");
		httpget.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpget.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpget.addHeader("Connection", "keep-alive");
		httpget.addHeader("Cache-Control", "max-age=0");
		return httpget;
	}

	public HttpGet createHttpGet(String url) {
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.0 Safari/537.36");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpget.addHeader("X-DevTools-Emulate-Network-Conditions-Client-Id", "DBFB3F80-1386-4E7A-BF2E-27057AE7EF3C");
		httpget.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.addHeader("Accept-Encoding", "gzip,deflate");
		httpget.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpget.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpget.addHeader("Connection", "keep-alive");
		httpget.addHeader("Cache-Control", "max-age=0");
		return httpget;
	}

	public HttpPost createHttpPost(String url) {
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.0 Safari/537.36");
		httpost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpost.addHeader("X-DevTools-Emulate-Network-Conditions-Client-Id", "DBFB3F80-1386-4E7A-BF2E-27057AE7EF3C");
		httpost.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpost.addHeader("Accept-Encoding", "gzip,deflate");
		httpost.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpost.addHeader("Connection", "keep-alive");
		httpost.addHeader("Cache-Control", "max-age=0");
		return httpost;
	}

	public HttpPost createHttpPost(URI uri) {
		HttpPost httpost = new HttpPost(uri);
		httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.0 Safari/537.36");
		httpost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpost.addHeader("X-DevTools-Emulate-Network-Conditions-Client-Id", "DBFB3F80-1386-4E7A-BF2E-27057AE7EF3C");
		httpost.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpost.addHeader("Accept-Encoding", "gzip,deflate");
		httpost.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpost.addHeader("Connection", "keep-alive");
		httpost.addHeader("Cache-Control", "max-age=0");
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

		private final CloseableHttpClient httpClients;
		private final HttpContext context;
		private final HttpGet httpget;

		public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
			this.httpClients = httpClient;
			this.context = HttpClientContext.create();
			this.httpget = httpget;
			downover = false;
		}

		@Override
		public void run() {
			try {
				updateUI("开获取图片..." + httpget.getRequestLine());
				CloseableHttpResponse response = httpClients.execute(httpget, context);
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
				} finally {
					response.close();
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
		Runnable runnable = new Runnable() {
			public void run() {
				m.display.asyncExec(new Runnable() {
					public void run() {
						Main.log_txt.append("\r\n" + msg);
					}
				});
			}
		};
		new Thread(runnable).start();
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
		List<String> list = new ArrayList();
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
					SimpleNodeIterator sni = lt.elements();
					// System.out.println(lt.toHtml());
					NodeList nls = lt.getChildren();
					int c = 0;
					StringBuffer sb = new StringBuffer();
					List<String> tlist = new ArrayList<String>();
					for (int n = 0; n < nls.size(); n++) {
						Node nd = nls.elementAt(n);
						if (nd instanceof OptionTag) {
							updateUI(((OptionTag) nd).getValue() + "\t" + ((OptionTag) nd).getChildrenHTML().trim().replace(" ", ""));
							sb.append(((OptionTag) nd).getValue() + "\t" + ((OptionTag) nd).getChildrenHTML().trim().replace(" ", "")).append("\r\n");
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
					// list.add(lt.getLink() + ";" + lt.getLinkText());
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
