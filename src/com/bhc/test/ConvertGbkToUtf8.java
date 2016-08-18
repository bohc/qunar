package com.bhc.test;

import java.io.File;

import com.bhc.util.FileManger;

public class ConvertGbkToUtf8 {
	FileManger fm = new FileManger();

	public void listFile(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				listFile(files[i].getAbsolutePath());
			} else {
				if (files[i].getName().toLowerCase().endsWith(".java")) {
					if (files[i] != null && files[i].length() > 0) {
						if (files[i].length() == 0) {
							files[i].delete();
							continue;
						}
						System.out.println(files[i].getAbsolutePath());
						try {
							fm.convertFile(files[i].getAbsolutePath(), "gbk", "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ConvertGbkToUtf8 cgu = new ConvertGbkToUtf8();
		cgu.listFile("C:/Users/bohc/Desktop/citic21-core-0.9-src");
	}
}
