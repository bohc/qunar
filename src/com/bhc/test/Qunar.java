package com.bhc.test;

import com.bhc.net.QunarNDWS;

public class Qunar {
	public static void main(String[] args) throws Exception {
        
		//��֤�˺Ų���ȡcookie
			String cookie  =  QunarNDWS.testAccount2("vieye", "zln4310526");
			QunarNDWS.getProxy(cookie);
			//����
			//String info = BaiduTieBaNDWS.reply("�������ţB������", "http://tieba.baidu.com/p/1193625840", cookie);
			//��ӡ��������
			System.out.println(cookie);
		}
}
