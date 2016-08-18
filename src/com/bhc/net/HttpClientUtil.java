package com.bhc.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * ��װ�˲���HttpClient����HTTP����ķ���
 * 
 * @see �����������õ���HttpComponents-Client-4.2.1
 * @see 
 *      ==========================================================================
 *      =========================
 * @see ����HTTPSӦ��ʱ��ʱ���������������
 * @see 1�����Է�����û����Ч��SSL֤��,�ͻ�������ʱ�ͻ����쳣
 * @see javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
 * @see 2�����Է�������SSL֤��,���������ڸ��ֲ�֪����ԭ��,�����ǻ���һ������������쳣,��������������
 * @see javax.net.ssl.SSLException: hostname in certificate didn't match:
 *      <123.125.97.66> != <123.125.97.241>
 * @see javax.net.ssl.SSLHandshakeException:
 *      sun.security.validator.ValidatorException: PKIX path building failed:
 *      sun.security.provider.certpath.SunCertPathBuilderException: unable to
 *      find valid certification path to requested target
 * @see 
 *      ==========================================================================
 *      =========================
 * @see ����ʹ�õ���HttpComponents-Client-4.2.1����������,���Ծ�Ҫ������ʹ��һ����ͬ��TrustManager
 * @see ����SSLʹ�õ�ģʽ��X.509,���ڸ�ģʽ,Java��һ���ض���TrustManager,��ΪX509TrustManager
 * @see TrustManager��һ�����ڼ�������֤���Ƿ���Ч����,���������Լ�����һ��X509TrustManagerʵ��
 * @see ����X509TrustManagerʵ����
 *      ,��֤����Ч,��ôTrustManager������checkXXX()�����н��׳�CertificateException
 * @see ��Ȼ����Ҫ�������е�֤��,��ôX509TrustManager����ķ������в��׳��쳣������
 * @see Ȼ�󴴽�һ��SSLContext��ʹ��X509TrustManagerʵ������ʼ��֮
 * @see ����ͨ��SSLContext����SSLSocketFactory,���SSLSocketFactoryע���HttpClient�Ϳ�����
 * @see 
 *      ==========================================================================
 *      =========================
 * @version v1.7
 * @history v1.0-->�½�<code>sendGetRequest()</code>��
 *          <code>sendPostRequest()</code>����
 * @history v1.1-->����<code>sendPostSSLRequest()</code>����,���ڷ���HTTPS��POST����
 * @history v1.2-->����<code>sendPostRequest()</code>����,���ڷ���HTTPЭ�鱨����Ϊ�����ַ�����POST����
 * @history v1.3-->����<code>java.net.HttpURLConnection</code>ʵ�ֵ�
 *          <code>sendPostRequestByJava()</code>
 * @history v1.4-->����POST�������������ӳ�ʱ���ƺͶ�ȡ��ʱ����
 * @history v1.5-->���������,�������Զ���ȡHTTP��Ӧ�ı�����ķ�ʽ,�Ƴ�
 *          <code>sendPostRequestByJava()</code>
 * @history v1.6-->����GET��POST���󷽷�,ʹ֮��Ϊ����
 * @history v1.7-->����<code>sendPostRequest()</code>
 *          �����CONTENT_TYPEͷ��Ϣ,���Ż��������������ڲ�����ϸ��
 * @create Feb 1, 2012 3:02:27 PM
 * @update Jul 23, 2013 1:18:35 PM
 * @author ����<http://blog.csdn.net/jadyer>
 */
public class HttpClientUtil {
	private HttpClientUtil() {
	}

	/**
	 * ����HTTP_GET����
	 * 
	 * @see 1)�÷������Զ��ر�����,�ͷ���Դ
	 * @see 2)���������������ӺͶ�ȡ��ʱʱ��,��λΪ����,��ʱ���������쳣ʱ�������Զ�����"ͨ��ʧ��"�ַ���
	 * @see 3)�������������ʱ,�����Կ�ֱ�Ӵ�������,HttpClient���Զ����뷢��Server,Ӧ��ʱӦ����ʵ��Ч����������ǰ�Ƿ�ת��
	 * @see 4)�÷������Զ���ȡ����Ӧ��Ϣͷ��[Content-Type:text/html;
	 *      charset=GBK]��charsetֵ��Ϊ��Ӧ���ĵĽ����ַ���
	 * @see ����Ӧ��Ϣͷ����Content-Type����,���ʹ��HttpClient�ڲ�Ĭ�ϵ�ISO-8859-1��Ϊ��Ӧ���ĵĽ����ַ���
	 * @param requestURL
	 *            �����ַ(������)
	 * @return Զ��������Ӧ����
	 */
	public static String sendGetRequest(String reqURL) {
		String respContent = "ͨ��ʧ��"; // ��Ӧ����
		HttpClient httpClient = new DefaultHttpClient(); // ����Ĭ�ϵ�httpClientʵ��
		// ���ô��������
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// new HttpHost("10.0.0.4", 8080));
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // ���ӳ�ʱ10s
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000); // ��ȡ��ʱ20s
		HttpGet httpGet = new HttpGet(reqURL); // ����org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = httpClient.execute(httpGet); // ִ��GET����
			HttpEntity entity = response.getEntity(); // ��ȡ��Ӧʵ��
			if (null != entity) {
				// respCharset=EntityUtils.getContentCharSet(entity)Ҳ���Ի�ȡ��Ӧ����,����4.1.3��ʼ������ʹ�����ַ�ʽ
				Charset respCharset = ContentType.getOrDefault(entity).getCharset();
				respContent = EntityUtils.toString(entity, respCharset);
				// Consume response content
				EntityUtils.consume(entity);
			}
			System.out.println("-------------------------------------------------------------------------------------------");
			StringBuilder respHeaderDatas = new StringBuilder();
			for (Header header : response.getAllHeaders()) {
				respHeaderDatas.append(header.toString()).append("\r\n");
			}
			String respStatusLine = response.getStatusLine().toString(); // HTTPӦ��״̬����Ϣ
			String respHeaderMsg = respHeaderDatas.toString().trim(); // HTTPӦ����ͷ��Ϣ
			String respBodyMsg = respContent; // HTTPӦ��������Ϣ
			System.out.println("HTTPӦ����������=[" + respStatusLine + "\r\n" + respHeaderMsg + "\r\n\r\n" + respBodyMsg + "]");
			System.out.println("-------------------------------------------------------------------------------------------");
		} catch (ConnectTimeoutException cte) {
			// Should catch ConnectTimeoutException, and don`t catch
			// org.apache.http.conn.HttpHostConnectException
			System.out.println("����ͨ��[" + reqURL + "]ʱ���ӳ�ʱ,��ջ�켣����" + cte.getMessage());
		} catch (SocketTimeoutException ste) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ��ȡ��ʱ,��ջ�켣����" + ste.getMessage());
		} catch (ClientProtocolException cpe) {
			// ���쳣ͨ����Э�������:���繹��HttpGet����ʱ����Э�鲻��(��'http'д��'htp')or��Ӧ���ݲ�����HTTPЭ��Ҫ���
			System.out.println("����ͨ��[" + reqURL + "]ʱЭ���쳣,��ջ�켣����" + cpe.getMessage());
		} catch (ParseException pe) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ�����쳣,��ջ�켣����" + pe.getMessage());
		} catch (IOException ioe) {
			// ���쳣ͨ��������ԭ�������,��HTTP������δ������
			System.out.println("����ͨ��[" + reqURL + "]ʱ�����쳣,��ջ�켣����" + ioe.getMessage());
		} catch (Exception e) {
			System.out.println("����ͨ��[" + reqURL + "]ʱż���쳣,��ջ�켣����" + e.getMessage());
		} finally {
			// �ر�����,�ͷ���Դ
			httpClient.getConnectionManager().shutdown();
		}
		return respContent;
	}

	/**
	 * ����HTTP_POST����
	 * 
	 * @see 1)�÷��������Զ����κθ�ʽ�����ݵ�HTTP��������
	 * @see 2)�÷������Զ��ر�����,�ͷ���Դ
	 * @see 3)���������������ӺͶ�ȡ��ʱʱ��,��λΪ����,��ʱ���������쳣ʱ�������Զ�����"ͨ��ʧ��"�ַ���
	 * @see 4)������������ĵ������ַ�ʱ,��ֱ�Ӵ��뱾����,��ָ��������ַ���encodeCharset����,�����ڲ����Զ�����ת��
	 * @see 5)�÷����ڽ�����Ӧ����ʱ�����õı���,ȡ����Ӧ��Ϣͷ�е�[Content-Type:text/html;
	 *      charset=GBK]��charsetֵ
	 * @see ����Ӧ��Ϣͷ��δָ��Content-Type����,���ʹ��HttpClient�ڲ�Ĭ�ϵ�ISO-8859-1
	 * @param reqURL
	 *            �����ַ
	 * @param reqData
	 *            �������,���ж��������Ӧƴ��Ϊparam11=value11&22=value22&33=value33����ʽ
	 * @param encodeCharset
	 *            �����ַ���,������������ʱ��֮,�˲���Ϊ������(����Ϊ""��null)
	 * @return Զ��������Ӧ����
	 */
	public static String sendPostRequest(String reqURL, String reqData, String encodeCharset) {
		String reseContent = "ͨ��ʧ��";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		HttpPost httpPost = new HttpPost(reqURL);
		// ��������ʹ�õ���new
		// StringEntity(....),����Ĭ�Ϸ���ȥ��������ͷ��CONTENT_TYPEֵΪtext/plain;
		// charset=ISO-8859-1
		// ����п��ܻᵼ�·���˽��ղ���POST��ȥ�Ĳ���,����������Tomcat6.0.36�е�Servlet,���������ֹ�ָ��CONTENT_TYPEͷ��Ϣ
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + encodeCharset);
		try {
			httpPost.setEntity(new StringEntity(reqData == null ? "" : reqData, encodeCharset));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ���ӳ�ʱ,��ջ�켣����" + cte.getMessage());
		} catch (SocketTimeoutException ste) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ��ȡ��ʱ,��ջ�켣����" + ste.getMessage());
		} catch (Exception e) {
			System.out.println("����ͨ��[" + reqURL + "]ʱż���쳣,��ջ�켣����" + e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return reseContent;
	}

	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset) {
		return sendPostSSLRequest(reqURL, params, encodeCharset);
	}
	
	/**
	 * ����HTTP_POST_SSL����
	 * 
	 * @see 1)�÷������Զ��ر�����,�ͷ���Դ
	 * @see 2)�÷�����ɴ�����ͨ��HTTP_POST����
	 * @see 3)������HTTP_POST_SSL����ʱ,Ĭ��������ǶԷ�443�˿�,����reqURL������ָ����SSL�˿�
	 * @see 4)���������������ӺͶ�ȡ��ʱʱ��,��λΪ����,��ʱ���������쳣ʱ�������Զ�����"ͨ��ʧ��"�ַ���
	 * @see 5)������������ĵ������ַ�ʱ,��ֱ�Ӵ��뱾����,��ָ��������ַ���encodeCharset����,�����ڲ����Զ�����ת��
	 * @see 6)�����ڲ����Զ�ע��443��ΪSSL�˿�,��ʵ��ʹ����reqURLָ����SSL�˿ڷ�443,�����г��Ը��ķ����ڲ�ע���SSL�˿�
	 * @see 7)�÷����ڽ�����Ӧ����ʱ�����õı���,ȡ����Ӧ��Ϣͷ�е�[Content-Type:text/html;
	 *      charset=GBK]��charsetֵ
	 * @see ����Ӧ��Ϣͷ��δָ��Content-Type����,���ʹ��HttpClient�ڲ�Ĭ�ϵ�ISO-8859-1
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param encodeCharset
	 *            �����ַ���,������������ʱ��֮,����Ϊnullʱ,��ȡHttpClient�ڲ�Ĭ�ϵ�ISO-8859-1�����������
	 * @return Զ��������Ӧ����
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset,HttpPost httpPosts) {
		String responseContent = "ͨ��ʧ��";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		// ����TrustManager()
		// ���ڽ��javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// ����HostnameVerifier
		// ���ڽ��javax.net.ssl.SSLException: hostname in certificate didn't match:
		// <123.125.97.66> != <123.125.97.241>
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}

			@Override
			public void verify(String host, X509Certificate cert) throws SSLException {
			}

			@Override
			public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
			}

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			// TLS1.0��SSL3.0������û��̫��Ĳ��,�ɴ������ΪTLS��SSL�ļ̳��ߣ�������ʹ�õ�����ͬ��SSLContext
			SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
			// ʹ��TrustManager����ʼ����������,TrustManagerֻ�Ǳ�SSL��Socket��ʹ��
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			// ����SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);
			// ͨ��SchemeRegistry��SSLSocketFactoryע�ᵽHttpClient��
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			// ����HttpPost
			HttpPost httpPost=httpPosts;
			if(httpPosts==null){
				httpPost = new HttpPost(reqURL);
			}
			// ��������ʹ�õ���new
			// UrlEncodedFormEntity(....),�������ﲻ��Ҫ�ֹ�ָ��CONTENT_TYPEΪapplication/x-www-form-urlencoded
			// ��Ϊ�ڲ鿴��HttpClient��Դ�����,UrlEncodedFormEntity�����õ�Ĭ��CONTENT_TYPE����application/x-www-form-urlencoded
			// httpPost.setHeader(HTTP.CONTENT_TYPE,
			// "application/x-www-form-urlencoded; charset=" + encodeCharset);
			// ����POST����ı�����
			if (null != params) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ���ӳ�ʱ,��ջ�켣����" + cte.getMessage());
		} catch (SocketTimeoutException ste) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ��ȡ��ʱ,��ջ�켣����" + ste.getMessage());
		} catch (Exception e) {
			System.out.println("����ͨ��[" + reqURL + "]ʱż���쳣,��ջ�켣����" + e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * ����HTTP_POST_SSL����
	 * 
	 * @see 1)�÷������Զ��ر�����,�ͷ���Դ
	 * @see 2)�÷�����ɴ�����ͨ��HTTP_POST����
	 * @see 3)������HTTP_POST_SSL����ʱ,Ĭ��������ǶԷ�443�˿�,����reqURL������ָ����SSL�˿�
	 * @see 4)���������������ӺͶ�ȡ��ʱʱ��,��λΪ����,��ʱ���������쳣ʱ�������Զ�����"ͨ��ʧ��"�ַ���
	 * @see 5)������������ĵ������ַ�ʱ,��ֱ�Ӵ��뱾����,��ָ��������ַ���encodeCharset����,�����ڲ����Զ�����ת��
	 * @see 6)�����ڲ����Զ�ע��443��ΪSSL�˿�,��ʵ��ʹ����reqURLָ����SSL�˿ڷ�443,�����г��Ը��ķ����ڲ�ע���SSL�˿�
	 * @see 7)�÷����ڽ�����Ӧ����ʱ�����õı���,ȡ����Ӧ��Ϣͷ�е�[Content-Type:text/html;
	 *      charset=GBK]��charsetֵ
	 * @see ����Ӧ��Ϣͷ��δָ��Content-Type����,���ʹ��HttpClient�ڲ�Ĭ�ϵ�ISO-8859-1
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param encodeCharset
	 *            �����ַ���,������������ʱ��֮,����Ϊnullʱ,��ȡHttpClient�ڲ�Ĭ�ϵ�ISO-8859-1�����������
	 * @return Զ��������Ӧ����
	 */
	public static String postFile(HttpPost httpPost,String encodeCharset) {
		String reqURL="";
		try {
			reqURL = httpPost.getURI().toURL().getHost();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String responseContent = "ͨ��ʧ��";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		// ����TrustManager()
		// ���ڽ��javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// ����HostnameVerifier
		// ���ڽ��javax.net.ssl.SSLException: hostname in certificate didn't match:
		// <123.125.97.66> != <123.125.97.241>
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}
			@Override
			public void verify(String host, X509Certificate cert) throws SSLException {
			}
			@Override
			public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
			}
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			// TLS1.0��SSL3.0������û��̫��Ĳ��,�ɴ������ΪTLS��SSL�ļ̳��ߣ�������ʹ�õ�����ͬ��SSLContext
			SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
			// ʹ��TrustManager����ʼ����������,TrustManagerֻ�Ǳ�SSL��Socket��ʹ��
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			// ����SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);
			// ͨ��SchemeRegistry��SSLSocketFactoryע�ᵽHttpClient��
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			// ����HttpPost
			//HttpPost httpPost = new HttpPost(reqURL);
			// ��������ʹ�õ���new
			// UrlEncodedFormEntity(....),�������ﲻ��Ҫ�ֹ�ָ��CONTENT_TYPEΪapplication/x-www-form-urlencoded
			// ��Ϊ�ڲ鿴��HttpClient��Դ�����,UrlEncodedFormEntity�����õ�Ĭ��CONTENT_TYPE����application/x-www-form-urlencoded
			// httpPost.setHeader(HTTP.CONTENT_TYPE,
			// "application/x-www-form-urlencoded; charset=" + encodeCharset);
			// ����POST����ı�����
//			if (null != params) {
//				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//				}
//				httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
//			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ���ӳ�ʱ,��ջ�켣����" + cte.getMessage());
		} catch (SocketTimeoutException ste) {
			System.out.println("����ͨ��[" + reqURL + "]ʱ��ȡ��ʱ,��ջ�켣����" + ste.getMessage());
		} catch (Exception e) {
			System.out.println("����ͨ��[" + reqURL + "]ʱż���쳣,��ջ�켣����" + e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
}
