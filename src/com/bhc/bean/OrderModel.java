package com.bhc.bean;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * 所有持久类的基类
 * 
 * @author Administrator sort 用哪个字段排序 ascOrDesc 是升序还是降序 curPage 当前页 pageRows
 *         每页显示的行数 pageRows 是否分页,0为不分布,1为分页,默认为不分页; maxCount 有多少数据
 */
public class OrderModel {
	private String sort;// 用哪个字段排序
	private String ascOrDesc;// 是升序还是降序
	private int curPage = 0;// 当前页
	private int pageRows = 10;// 每页显示的行数
	private String ispage;// 是否分页,空为不分布,有值为分页
	private int maxcount;// 有多少数据

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
