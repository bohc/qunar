package com.bhc.test;

import com.bhc.net.QunarNDWS;

public class Qunar {
	public static void main(String[] args) throws Exception {
        
		//验证账号并获取cookie
			String cookie  =  QunarNDWS.testAccount2("vieye", "zln4310526");
			QunarNDWS.getProxy(cookie);
			//发帖
			//String info = BaiduTieBaNDWS.reply("这个贴狠牛B啊！！", "http://tieba.baidu.com/p/1193625840", cookie);
			//打印返回信心
			System.out.println(cookie);
		}
}
