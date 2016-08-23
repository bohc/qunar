package com.bhc.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtml {
	public static String parseImgCode(String htmlStr) {
		if (htmlStr == null)
			return "";
		Document document = Jsoup.parse(htmlStr);
		// �õ����а������ߵ� div Ԫ��
		Elements businesses = document.select("img#vcodeImg");
		if (businesses.size() > 0) {
			Element el = businesses.first();
			String src = el.attr("src");
			System.out.println(src);
			return src;
		}
		return "";
	}

		public static String parseJsCode(String htmlStr) {
			if (htmlStr == null)
				return "";
			int s=htmlStr.indexOf("sessionId");
			if(s!=-1){
				htmlStr=htmlStr.substring(s+"sessionId".length()+1);
				int d=htmlStr.indexOf("\")");
				if(d!=-1){
					return htmlStr.substring(0, d);
				}
			}
			return "";
		}
	
	/**
	 * ȥ��url�е�·�������������������
	 * 
	 * @param strURL
	 *            url��ַ
	 * @return url�����������
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	/**
	 * ������url�����еļ�ֵ�� �� "index.jsp?Action=del&id=123"��������Action:del,id:123����map��
	 * 
	 * @param URL
	 *            url��ַ
	 * @return url�����������
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// ÿ����ֵΪһ�� www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// ��������ֵ
			if (arrSplitEqual.length > 1) {
				// ��ȷ����
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// ֻ�в���û��ֵ��������
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
