package com.bhc.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FileManger {

	/**
	 * ȡ�ļ���׺
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		int lastIndexOfDot = fileName.lastIndexOf('.');
		if (lastIndexOfDot == -1)
			return "";
		int fileNameLength = fileName.length();
		return fileName.substring(lastIndexOfDot, fileNameLength);
	}

	public void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte buffer[] = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1)
					fs.write(buffer, 0, byteread);
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 * 
	 * @param dir
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deleteDirectory(String dir) {
		// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// System.out.println("ɾ��Ŀ¼ʧ��"+dir+"Ŀ¼�����ڣ�");
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
				// ɾ����Ŀ¼
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			// System.out.println("ɾ��Ŀ¼ʧ��");
			return false;
		}
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			// System.out.println("ɾ��Ŀ¼"+dir+"�ɹ���");
			return true;
		} else {
			// System.out.println("ɾ��Ŀ¼"+dir+"ʧ�ܣ�");
			return false;
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param fileName
	 * @return �����ļ�ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * д�ļ�
	 * 
	 * @return
	 */
	public static String writeFile(String filepath, String text, String encode) {
		FileOutputStream fw = null;
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file.delete();

			fw = new FileOutputStream(filepath);
			OutputStreamWriter ow = new OutputStreamWriter(fw, encode);
			BufferedWriter bw = new BufferedWriter(ow);
			bw.write(text);
			bw.flush();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException ex) {
				}
			}
		}
		return filepath;
	}
	
	/**
	 * ׷��д�ļ�
	 * 
	 * @return
	 */
	public static String reWriteFile(String filepath, String text) {
		FileWriter fw = null;
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
				file.delete();
			}else{
				file.createNewFile();
			}

			fw =new FileWriter( file,true) ;
			fw.write(text+"\r\n");
		} catch (Exception ex1) {
			ex1.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException ex) {
				}
			}
		}
		return filepath;
	}

	/**
	 * ��ȡ�ļ�
	 * 
	 * @return
	 */
	public static String readFile(String filepath, String encode) {
		InputStreamReader fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new InputStreamReader(new FileInputStream(filepath), encode);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\r\n");
			}
		} catch (Exception ex) {
			sb.append("-");
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ex1) {
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * ��ȡ�ļ�
	 * 
	 * @return
	 */
	public static String readFile(String filepath, String encode,String comparevalue) {
		InputStreamReader fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new InputStreamReader(new FileInputStream(filepath), encode);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			int f=0;
			if(comparevalue==null || comparevalue.equals("")){
				f=1;
			}
			while ((line = br.readLine()) != null) {
				if(f==0){
					if(line.indexOf(comparevalue)>-1){
						continue;
					}
				}
				sb.append(line);
			}
		} catch (Exception ex) {
			sb.append("-");
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ex1) {
				}
			}
		}
		return sb.toString();
	}

	public static String createFile(String realpath, String filepath,
			String text) throws Exception {
		Date pathDate = new Date();
		long pathTimestamp = pathDate.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String pathYyyymmdd = "";
		pathYyyymmdd = dateFormat.format(pathDate);

		String path = filepath + "/" + pathYyyymmdd;
		String fileurl = realpath + "/" + path;
		File f1 = new File(realpath + "/" + filepath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		File f2 = new File(realpath + "/" + filepath + "/" + pathYyyymmdd);
		if (!f2.exists()) {
			f2.mkdirs();
		}

		File file = new File(fileurl, "" + pathTimestamp);
		OutputStream os = new FileOutputStream(file);
		byte[] b = text.getBytes();
		InputStream in = new ByteArrayInputStream(b);
		BufferedInputStream bin = new BufferedInputStream(in);

		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = bin.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		in.close();
		return path + "/" + pathTimestamp;
	}

	public static String createimg(String realpath, String filepath, String text)
			throws Exception {
		Date pathDate = new Date();
		long pathTimestamp = pathDate.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String pathYyyymmdd = "";
		pathYyyymmdd = dateFormat.format(pathDate);

		String path = filepath + pathYyyymmdd + "/" + pathTimestamp;
		String fileurl = realpath + "/" + path;
		File f1 = new File(realpath + "/" + filepath);
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f2 = new File(realpath + "/" + filepath + "/" + pathYyyymmdd);
		if (!f2.exists()) {
			f2.mkdir();
		}

		File file = new File(fileurl, "" + pathTimestamp);
		OutputStream os = new FileOutputStream(file);
		File infile = new File(text);

		InputStream in = new FileInputStream(infile);
		BufferedInputStream bin = new BufferedInputStream(in);

		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = bin.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		in.close();
		return path;
	}

	public static String getFile(String realpath, String path)
			throws IOException {
		String filepath = "", s;
		File f1 = new File(realpath + "/" + path);
		FileReader fr = new FileReader(f1);
		BufferedReader br = new BufferedReader(fr);
		s = br.readLine();
		while (s != null) {
			filepath = filepath + s;
			s = br.readLine();
		}

		return new String(filepath);
	}

	/**
	 * ת������,��д�����ļ�
	 * 
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public void convertFile(String filePath, String oldEncode, String newEncode)
			throws Exception {
		// ��дFileReader��,��file������ָ�������ʽ����
		FileReaderByEncode file = new FileReaderByEncode(filePath, oldEncode);
		BufferedReader br = new BufferedReader(file);
		String temp = "";
		StringBuffer sb = new StringBuffer();
		while ((temp = br.readLine()) != null) {
			sb.append(temp + '\n');
		}
		br.close();
		temp = sb.toString();
		// filePath = filePath.replace(FILE_PATH, NEW_FOLDER);
		File tempFile = new File(filePath);
		// ���ָ��Ŀ¼���Ƿ����Ŀ¼,û�����½�
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		// ���ձ���д�����ļ�
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				filePath), newEncode);
		out.write(temp);
		out.flush();
		out.close();
	}
	
	public String formatXml(String str) throws UnsupportedEncodingException,
    IOException, DocumentException {
		//System.out.println(" str :  " + str);
		SAXReader reader=new SAXReader();
		//System.out.println(reader);
		//ע�ͣ�����һ�������ַ�������
		StringReader in=new StringReader(str);
		Document doc=reader.read(in);
		// System.out.println(doc.getRootElement());
		//ע�ͣ����������ʽ
		OutputFormat formater=OutputFormat.createPrettyPrint();
		//ע�ͣ�����xml���������
		formater.setEncoding("utf-8");
		//ע�ͣ��������(Ŀ��)
		StringWriter out=new StringWriter();
		//ע�ͣ����������
		XMLWriter writer=new XMLWriter(out,formater);
		//ע�ͣ������ʽ���Ĵ���Ŀ���У�ִ�к󡣸�ʽ����Ĵ�������out�С�
		writer.write(doc);
		
		String destXML=out.toString();
		writer.close();
		out.close();
		in.close();
		//ע�ͣ��������Ǹ�ʽ����Ľ��
		return destXML;       
	}
}
