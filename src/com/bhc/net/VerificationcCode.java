
package com.bhc.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 下载验证码的
 * @author Legend、
 *
 */
public class VerificationcCode {
   
	//发送post请求获取验证码
	public static void showVerificationcCode(String url,String fileUrl) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		InputStream in= entity.getContent();
		int temp = 0;
		File file = new File(fileUrl);
		FileOutputStream out = new FileOutputStream(file);
		while((temp=in.read())!=-1){
		 out.write(temp);
		}
		in.close();
		out.close();
	}

	//发送get请求获取验证码
	public static String showGetVerificationcCode(String url,Map<String ,String> header, String fileUrl) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		if(null!=header&&header.size()>0){
			get.setHeaders(SendRequest.assemblyHeader(header));
		}
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		InputStream in= entity.getContent();
		int temp = 0;
		File file = new File(fileUrl);
		FileOutputStream out = new FileOutputStream(file);
		while((temp=in.read())!=-1){
		 out.write(temp);
		}
		in.close();
		out.close();
		return SendRequest.assemblyCookie(client.getCookieStore().getCookies());
	}
	
	//发送get请求获取验证码
	public static Result showGetVerificationcCode(URI url,Map<String ,String> header, String fileUrl) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		if(null!=header&&header.size()>0){
			get.setHeaders(SendRequest.assemblyHeader(header));
		}
		HttpResponse response=null;
		try {
			response = client.execute(get);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		InputStream in= entity.getContent();
		int temp = 0;
		File file = new File(fileUrl);
		FileOutputStream out = new FileOutputStream(file);
		while((temp=in.read())!=-1){
			out.write(temp);
		}
		in.close();
		out.close();
		
		//封装返回的参数
        Result result = new Result();
        //设置返回状态代码
        result.setStatusCode(response.getStatusLine().getStatusCode());
        //设置返回的头部信息
        result.setHeaders(response.getAllHeaders());
        result.setCookies(client.getCookieStore().getCookies());
        //设置返回的cookie信心
		result.setCookie(SendRequest.assemblyCookie(client.getCookieStore().getCookies()));
		//设置返回到信息
		result.setHttpEntity(entity);
		return result ;
	}
}
