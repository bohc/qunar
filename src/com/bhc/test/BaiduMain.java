
package com.bhc.test;

/**
 *  主方法
 * @author Legend、
 *
 */
public class BaiduMain {

	
public static void main(String[] args) throws Exception {
        
	//验证账号并获取cookie
		String cookie  =  BaiduTieBaNDWS.testAccount("410425754@qq.com", "bhc197811");
		//发帖
		String info = BaiduTieBaNDWS.reply("这个贴狠牛B啊！！", "http://tieba.baidu.com/p/1193625840", cookie);
		//打印返回信心
		System.out.println(info);
	}
}

