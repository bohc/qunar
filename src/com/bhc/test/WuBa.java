package com.bhc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.util.EntityUtils;

import com.bhc.net.SendRequest;

public class WuBa {

	public String test(String name, String pass) throws Exception {
		// ��ȡJS�ļ�
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("f:/wuba.js"))));

		// ����js���������ǹؼ� ��
		ScriptEngineManager scriptManager = new ScriptEngineManager();
		ScriptEngine js = scriptManager.getEngineByExtension("js");
		// ִ��JS
		js.eval(buf);
		long date = new Date().getTime();
		String time = String.valueOf(date).substring(5, 11);
		Invocable inv2 = (Invocable) js;

		// p1�Ļ�ȡ ִ��js�еķ���
		String p1 = (String) inv2.invokeFunction("getm32str", pass, time);

		// p2�Ļ�ȡ
		String m32 = (String) inv2.invokeFunction("hex_md5", pass);
		m32 = m32.substring(8, 24);

		String result = "";
		for (int i = m32.length() - 1; i >= 0; i--) {
			result += m32.charAt(i);
		}

		String p2 = (String) inv2.invokeFunction("getm16str", result, time);

		// ��װ����
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(
				"path",
				"http://xa.58.com/?utm_source=pinpaizhuanqu&utm_medium=wf&utm_campaign=bp-title");
		params.put("p1", p1);
		params.put("p2", p2);
		params.put("timesign", String.valueOf(date));
		params.put("username", name);
		params.put("mobile", "�ֻ���");
		params.put("password", "password");
		params.put("remember", "on");
		// �������󲢻�ȡcookie
		String cookie = SendRequest.sendGet("http://passport.58.com/dologin",null, params, "utf-8").getCookie();
		return cookie;
	}

	public static void main(String[] args) throws Exception {
		String cookie = new WuBa().test("majia200", "majia123");
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Cookie", cookie);

		// ��½�ҵ����� ��֤�Ƿ��½�ɹ���
		System.out.println(EntityUtils.toString(SendRequest.sendGet("http://my.58.com/", header, null, "utf-8").getHttpEntity(), "utf-8"));

	}
}
