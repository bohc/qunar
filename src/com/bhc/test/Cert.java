package com.bhc.test;

public class Cert {
	public static void main(String[] args) throws Exception {
		//验证账号并获取cookie
			String cookie  =  CertNDWS.testAccount("admin", "123456");
			//发帖
			CertNDWS.getEmpLicDeal("http://localhost/PJCertManage/empstartprocess!listm.do", cookie);
			//打印返回信心
			System.out.println(cookie);
		}
}
