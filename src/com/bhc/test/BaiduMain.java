
package com.bhc.test;

/**
 *  ������
 * @author Legend��
 *
 */
public class BaiduMain {

	
public static void main(String[] args) throws Exception {
        
	//��֤�˺Ų���ȡcookie
		String cookie  =  BaiduTieBaNDWS.testAccount("410425754@qq.com", "bhc197811");
		//����
		String info = BaiduTieBaNDWS.reply("�������ţB������", "http://tieba.baidu.com/p/1193625840", cookie);
		//��ӡ��������
		System.out.println(info);
	}
}

