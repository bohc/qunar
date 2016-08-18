package com.bhc.test;

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

import com.bhc.net.Result;
import com.bhc.net.SendRequest;

/**
 * �ٶ����ɵķ����������
 * 
 * @author Legend��
 *
 */
public class CertNDWS {

	public static String getEmpLicDeal(String postsUrl, String cookie) throws ClientProtocolException, IOException {

		// ����һЩ���п��޵�ͷ����Ϣ���е�ʱ����Ҫ�������е�ʱ��ȷ��Ҫ�����Խ�������ü��ϣ�
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", postsUrl);
		headers.put("Host", "localhost");
		headers.put("Cookie", cookie);

		// ���Ǵ������л�ȡһЩ����ʱ��ر��Ĳ���
		Result r = null;
		try {
			r=SendRequest.sendGet(postsUrl, headers, null, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
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
		// ׼����һ�У�����
		//Result res = SendRequest.sendPost("http://tieba.baidu.com/f/commit/post/add", headers, parameters, "utf-8");

		// ����֮��ٶȻ᷵��һ��json��˵���Ƿ�����ɹ�
		//return EntityUtils.toString(res.getHttpEntity(), "utf-8");
		return "";
	}

	// ȥ�Ķ���½
	public static String testAccount(String name, String password) throws ClientProtocolException, IOException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("vusername", name);
		parameters.put("vpassword", password);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", "http://localhost/PJCertManage");
		headers.put("Host", "localhost");
		Result r = SendRequest.sendPost("http://localhost/PJCertManage/login!loginm.do", headers, parameters, "utf-8");
		System.out.println(r.getStatusCode());
		String str = r.getCookie();
		return str;
	}

}
