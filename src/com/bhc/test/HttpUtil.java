package com.bhc.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.eclipse.swt.browser.Browser;

/**
 * HTTP 请求工具类
 *
 * @author : liii
 * @version : 1.0.0
 * @date : 2015/7/21
 * @see : TODO
 */
@SuppressWarnings("unchecked")
public class HttpUtil {
	public static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;
	public static SSLConnectionSocketFactory sslf;
	public static CookieStore cookieStore = null;
	public static List<Header> hsl = new ArrayList<Header>();

	static {
		cookieStore = new BasicCookieStore();
		// BasicClientCookie bci=new BasicClientCookie("", value);

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		configBuilder.setCookieSpec(CookieSpecs.STANDARD_STRICT);
		configBuilder.setExpectContinueEnabled(true);
		configBuilder.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST));
		configBuilder.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC));
		// 在提交请求之前 测试连接是否可用
		// configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();

		sslf = createSSLConnSocketFactory();
		@SuppressWarnings("rawtypes")
		Registry registry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslf).build();
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager(registry);
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpPost = new HttpGet(apiUrl);
			HttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println("执行状态码 : " + statusCode);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = IOUtils.toString(instream, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(myStrategy).setConnectionManager(connMgr).setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, List<Header> headers, Map<String, Object> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		String recontent = "";
		// 封装返回的参数
		try {
			httpPost = new HttpPost(apiUrl);
			httpPost.setConfig(requestConfig);
			boolean iscookie = false;
			if (headers != null) {
				for (Header h : headers) {
					if (h.getName().trim().equals("Cookie")) {
						iscookie = true;
					}
					httpPost.addHeader(h);
				}
			}
			// 如果已经加了cookie,那么这儿不再设置
			if (!iscookie) {
				httpPost.setHeader("Cookie", assemblyCookie());
			}

			httpClient = HttpClients.custom().setKeepAliveStrategy(myStrategy).setConnectionManager(connMgr).setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig).build();

			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			// fetchCookie(response.getAllHeaders());

			for (Cookie k : cookieStore.getCookies()) {
				System.out.println("store\t" + k.toString());
			}

			HeaderElementIterator it = new BasicHeaderElementIterator(httpPost.headerIterator());
			while (it.hasNext()) {
				HeaderElement elem = it.nextElement();
				System.out.println(elem.getName() + " = " + elem.getValue());
				NameValuePair[] ps = elem.getParameters();
				for (int i = 0; i < ps.length; i++) {
					System.out.println(" " + ps[i]);
				}
			}

			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}

			HeaderIterator hit = response.headerIterator();
			hsl.clear();
			while (hit.hasNext()) {
				Header header = (Header) hit.next();
				hsl.add(header);
			}

			String ct = entity.getContentType().getValue();
			if (ct.equals("image/jpeg")) {
				String realpath = System.getProperty("user.dir");
				InputStream in = entity.getContent();
				int temp = 0;
				File file = new File(realpath + "/1.png");
				FileOutputStream out = new FileOutputStream(file);
				while ((temp = in.read()) != -1) {
					out.write(temp);
				}
				in.close();
				out.close();
				file.getAbsolutePath();
			} else {
				recontent = EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return recontent;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	public static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	// 这是组装cookie
	public static String assemblyCookie() {
		if (cookieStore.getCookies() == null || cookieStore.getCookies().size() == 0)
			return "";
		StringBuffer sbu = new StringBuffer();
		for (Cookie cookie : cookieStore.getCookies()) {
			sbu.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
		}
		if (sbu.length() > 0)
			sbu.setLength(sbu.length() - 1);
		return sbu.toString();
	}

	// 这是组装cookie
	public static void setCookie(String cookieStr) {
		if (cookieStr == null)
			return;
		String[] scs = cookieStr.split(";");
		if (scs.length > 0) {
			for (String str : scs) {
				int i = str.indexOf("=");
				if (i != -1) {
					String key = str.substring(0, i);
					String value = str.substring(i + 1);
					for (Cookie cookie : cookieStore.getCookies()) {
						if(cookie.getName().trim().equals(key.trim())){
//							cookie.
						}
					}
				}
				Browser.setCookie(str, "http://tb2cadmin.qunar.com");
			}
		}
	}

	private static DefaultHostnameVerifier createHostNameVerifier(String url) throws MalformedURLException, IOException { // 创建默认的httpClient实例.
		PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(url));
		DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
		return hostnameVerifier;
	}

	private static ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
		public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
			// Honor 'keep-alive' header
			HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();
				if (value != null && param.equalsIgnoreCase("timeout")) {
					try {
						return Long.parseLong(value) * 1000;
					} catch (NumberFormatException ignore) {
					}
				}
			}
			HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
			if ("www.qunar.com".equalsIgnoreCase(target.getHostName())) {
				// Keep alive for 5 seconds only
				return 60 * 1000;
			} else {
				// otherwise keep alive for 30 seconds
				return 60 * 1000;
			}
		}

	};

	public static List<Header> createHeader() {
		List<Header> hs = new ArrayList<Header>();
		hs.add(new BasicHeader("Accept", "application/json, text/javascript, */*; q=0.01"));
		hs.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		hs.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
		hs.add(new BasicHeader("Cache-Control", "no-cache"));
		hs.add(new BasicHeader("Connection", "keep-alive"));
		hs.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
		hs.add(new BasicHeader("Host", "user.qunar.com"));
		hs.add(new BasicHeader("Origin", "https://user.qunar.com"));
		hs.add(new BasicHeader("Pragma", "no-cache"));
		hs.add(new BasicHeader("Referer", "https://user.qunar.com/passport/login.jsp?ret=https%3A%2F%2Ftb2cadmin.qunar.com%2F"));
		hs.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"));
		hs.add(new BasicHeader("X-Requested-With", "XMLHttpRequest"));
		return hs;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String url = "";
		Map<String, Object> ps = new HashMap<String, Object>();
		String str = doPostSSL(url, null, ps);
		System.out.println(str);
	}
}