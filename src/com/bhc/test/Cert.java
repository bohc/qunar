package com.bhc.test;

public class Cert {
	public static void main(String[] args) throws Exception {
		//��֤�˺Ų���ȡcookie
			String cookie  =  CertNDWS.testAccount("admin", "123456");
			//����
			CertNDWS.getEmpLicDeal("http://localhost/PJCertManage/empstartprocess!listm.do", cookie);
			//��ӡ��������
			System.out.println(cookie);
		}
}
