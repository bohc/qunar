package com.bhc.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
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
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.bhc.net.Result;
import com.bhc.net.SendRequest;

/**
 * HTTP ���󹤾���
 *
 * @author : liii
 * @version : 1.0.0
 * @date : 2015/7/21
 * @see : TODO
 */
@SuppressWarnings("unchecked")
public class HttpUtil {
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;
	private static SSLConnectionSocketFactory sslf;

	static {

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// �������ӳ�ʱ
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// ���ö�ȡ��ʱ
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// ���ô����ӳػ�ȡ����ʵ���ĳ�ʱ
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// ���ύ����֮ǰ ���������Ƿ����
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();

		sslf = createSSLConnSocketFactory();
		@SuppressWarnings("rawtypes")
		Registry registry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslf).build();
		// �������ӳ�
		connMgr = new PoolingHttpClientConnectionManager(registry);
		// �������ӳش�С
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
	}

	/**
	 * ���� GET ����HTTP����������������
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * ���� GET ����HTTP����K-V��ʽ
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

			System.out.println("ִ��״̬�� : " + statusCode);

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
	 * ���� POST ����HTTP����������������
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * ���� POST ����HTTP����K-V��ʽ
	 * 
	 * @param apiUrl
	 *            API�ӿ�URL
	 * @param params
	 *            ����map
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
	 * ���� POST ����HTTP����JSON��ʽ
	 * 
	 * @param apiUrl
	 * @param json
	 *            json����
	 * @return
	 */
	public static String doPost(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// ���������������
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

	/**
	 * ���� SSL POST ����HTTPS����K-V��ʽ
	 * 
	 * @param apiUrl
	 *            API�ӿ�URL
	 * @param params
	 *            ����map
	 * @return
	 */
	public static Result doPostSSL(String apiUrl, Map<String, Header> headers, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(myStrategy).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		// ��װ���صĲ���
		Result result = new Result();
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
				System.out.println(pair.getName() + ":" + pair.getValue());
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, Header> entry : headers.entrySet()) {
					if (entry.getKey().equals("Set-Cookie")) {
						System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
						httpPost.addHeader("Cookie", entry.getValue().getValue());
					}
				}
			}
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			Header[] hd = response.getAllHeaders();
			if (hd != null) {
				for (Header h : hd) {
					System.out.println(h.getName() + ":" + h.getValue());
				}
			}
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
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
				// httpStr = EntityUtils.toString(entity, "utf-8");
			}

			// ���÷���״̬����
			result.setStatusCode(response.getStatusLine().getStatusCode());
			// ���÷��ص�ͷ����Ϣ
			result.setHeaders(response.getAllHeaders());
			// result.setCookies(client.getCookieStore().getCookies());
			// ���÷��ص�cookie����
			// result.setCookie(SendRequest.assemblyCookie(client.getCookieStore().getCookies()));
			// ���÷��ص���Ϣ
			result.setHttpEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if (response != null) {
//				try {
//					EntityUtils.consume(response.getEntity());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return result;
	}

	/**
	 * ���� SSL POST ����HTTPS����JSON��ʽ
	 * 
	 * @param apiUrl
	 *            API�ӿ�URL
	 * @param json
	 *            JSON����
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// ���������������
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
	 * ����SSL��ȫ����
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
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

	static ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
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

	/**
	 * ���Է���
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String url = "";
		Map<String, Object> ps = new HashMap<String, Object>();
		Result str = doPostSSL(url, null, ps);
		System.out.println(str);
	}
}