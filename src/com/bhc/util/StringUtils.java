package com.bhc.util;

public class StringUtils {
	public static String convert(String str) {
		StringBuffer sb = new StringBuffer(1024);
		sb.setLength(0);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				int j = (c >>> 8);
				String tmp = Integer.toHexString(j);
				if (tmp.length() == 1) {
					sb.append("0");
				}
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1) {
					sb.append("0");
				}
				sb.append(tmp);
			} else {
				sb.append(c);
			}
		}
		return (new String(sb));
	}
	
	public static String parseCharacter(String str){
		if(str==null)return "";
		return str.replace("%20","&nbsp;").replace("%21","&mdash;").replace("%22","&ldquo;").replace("%22","&rdquo;").replace("%22","&bull;");
	}
}
