package com.bhc.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.cookie.Cookie;

/**
 * 封住请求返回的参数
 * 
 * @author Legend、
 *
 */

public class Result {

	private String cookie;
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private int statusCode;
	private HashMap<String, Header> headerAll;
	private HttpEntity httpEntity;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HashMap<String, Header> getHeaders() {
		return headerAll;
	}

	public void setHeaders(Header[] headers) {
		headerAll = new HashMap<String, Header>();
		for (Header header : headers) {
			headerAll.put(header.getName(), header);
		}
	}

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

}
