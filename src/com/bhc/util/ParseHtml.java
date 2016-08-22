package com.bhc.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtml {
	public static String parseImgCode(String htmlStr) {
		if (htmlStr == null)
			return "";
		Document document = Jsoup.parse(htmlStr);
		// 得到所有包含航线的 div 元素
		Elements businesses = document.select("img#vcodeImg");
		if(businesses.size()>0){
			Element el=businesses.first();
			String src=el.attr("src");
			System.out.println(src);
		}
		System.out.println(businesses.html());
		return "";
	}
}
