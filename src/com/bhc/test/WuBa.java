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
		// 读取JS文件
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("f:/wuba.js"))));

		// 调用js。。这里是关键 啊
		ScriptEngineManager scriptManager = new ScriptEngineManager();
		ScriptEngine js = scriptManager.getEngineByExtension("js");
		// 执行JS
		js.eval(buf);
		long date = new Date().getTime();
		String time = String.valueOf(date).substring(5, 11);
		Invocable inv2 = (Invocable) js;

		// p1的获取 执行js中的方法
		String p1 = (String) inv2.invokeFunction("getm32str", pass, time);

		// p2的获取
		String m32 = (String) inv2.invokeFunction("hex_md5", pass);
		m32 = m32.substring(8, 24);

		String result = "";
		for (int i = m32.length() - 1; i >= 0; i--) {
			result += m32.charAt(i);
		}

		String p2 = (String) inv2.invokeFunction("getm16str", result, time);

		// 组装参数
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(
				"path",
				"http://xa.58.com/?utm_source=pinpaizhuanqu&utm_medium=wf&utm_campaign=bp-title");
		params.put("p1", p1);
		params.put("p2", p2);
		params.put("timesign", String.valueOf(date));
		params.put("username", name);
		params.put("mobile", "手机号");
		params.put("password", "password");
		params.put("remember", "on");
		// 发送请求并获取cookie
		String cookie = SendRequest.sendGet("http://passport.58.com/dologin",null, params, "utf-8").getCookie();
		return cookie;
	}

	public static void main(String[] args) throws Exception {
		String cookie = new WuBa().test("majia200", "majia123");
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Cookie", cookie);

		// 登陆我的中心 验证是否登陆成功！
		System.out.println(EntityUtils.toString(SendRequest.sendGet("http://my.58.com/", header, null, "utf-8").getHttpEntity(), "utf-8"));

	}
}
