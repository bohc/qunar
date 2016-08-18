package com.bhc.bean;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * ���г־���Ļ���
 * 
 * @author Administrator sort ���ĸ��ֶ����� ascOrDesc �������ǽ��� curPage ��ǰҳ pageRows
 *         ÿҳ��ʾ������ pageRows �Ƿ��ҳ,0Ϊ���ֲ�,1Ϊ��ҳ,Ĭ��Ϊ����ҳ; maxCount �ж�������
 */
public class OrderModel {
	private String sort;// ���ĸ��ֶ�����
	private String ascOrDesc;// �������ǽ���
	private int curPage = 0;// ��ǰҳ
	private int pageRows = 10;// ÿҳ��ʾ������
	private String ispage;// �Ƿ��ҳ,��Ϊ���ֲ�,��ֵΪ��ҳ
	private int maxcount;// �ж�������

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public String getIspage() {
		return ispage;
	}

	public void setIspage(String ispage) {
		this.ispage = ispage;
	}

	public int getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(int maxcount) {
		this.maxcount = maxcount;
	}

	/**
	 * Gets a string from the resource bundle. We don't want to crash because of
	 * a missing String. Returns the key if not found.
	 */
	public static String getResourceString(String key) {
		return key;
	}

	/**
	 * Gets a string from the resource bundle and binds it with the given
	 * arguments. If the key is not found, return the key.
	 */
	public static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}
}
