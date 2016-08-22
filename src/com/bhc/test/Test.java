package com.bhc.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static void main(String args[]) throws JSONException {
		t();
	}

	public static void main1(String args[]) throws JSONException {
		String jsonContent = "{'hello':world,'abc':'xyz'}";
		JSONObject jsonObject = new JSONObject(jsonContent);
		String str1 = jsonObject.getString("hello");
		String str2 = jsonObject.getString("abc");
		System.out.println(str1);
		System.out.println(str2);

		System.out.println("------------------");
		jsonContent = "[{'hello':333,'abc':'false','xyz':{'a':1,'b':'ab'}},{'hello':555,'abc':'true','xyz':{'a':2,'b':'ba'}}]";
		JSONArray jsonArray = new JSONArray(jsonContent);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonobject2 = jsonArray.getJSONObject(i);
			int value1 = jsonobject2.getInt("hello");
			boolean value2 = jsonobject2.getBoolean("abc");
			// String value3=jsonobject2.getString("xyz");
			JSONObject jsonobject3 = jsonobject2.getJSONObject("xyz");
			int value4 = jsonobject3.getInt("a");
			String value5 = jsonobject3.getString("b");
			System.out.println(value1);
			System.out.println(value2);
			System.out.println(value4);
			System.out.println(value5);

		}

	}

	public static void t() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Process p = new ProcessBuilder("C:/Users/bohc/Downloads/AnyDesk.exe", "D:\\music\\2014-1\\月光下的凤尾竹.mp3").start();
					p.getInputStream();
					Thread.sleep(60 * 60 * 1000);
					p.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		// Runtime rn = Runtime.getRuntime();
		// Process p = null;
		// try {
		// p = rn.exec("D:\\Program Files\\TTPlayer\\TTPlayer.exe");
		// } catch (Exception e) {
		// System.out.println("Error exec!");
		// }
	}
	
	@org.junit.Test
	public void testSdf() throws ParseException{
		SimpleDateFormat sdfs = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss 'GMT'", Locale.US);
		System.out.println(sdfs.parse("Thu, 31-Dec-37 23:55:55 GMT"));
	}
}