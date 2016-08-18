package com.bhc.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bhc.net.HttpLogin;

public class TestJunit {
	@Test
	public void testMultiLink() {
		HttpLogin hl = new HttpLogin(null);
		StringBuffer sb = hl.getHtmlContent("http://www.qly.cc/Api/api_list.aspx");
//		System.out.println(sb.toString());
		List<String> alist = new ArrayList<String>();
		hl.readLink(sb, alist);
		for (String s : alist) {
			System.out.println(s);
		}
	}
	
	public static void main(String[] args){
		String s="dddddddddddddddd火车汽车飞机";
		if(s.contains("火车|出租车")){
			System.out.println("ddddddddddddddddd");
		}
	}
}
