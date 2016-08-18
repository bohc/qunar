package com.bhc.util;

public class ColumnsDesc {
	/** 列名 */
	private String cname;
	/** 类型 */
	private String ctype;
	/** 是否可为空 */
	private String isnull;
	/** 这个字段相对应的固定编号 */
	private String ccode;
	/** 显示的别名 */
	private String ctitle;
	/** 这列的宽度 */
	private String cwidth;
	/** 这列是否显示 */
	private String cisshow;
	/** 是否可编辑 */
	private String cisedit;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	public String getCwidth() {
		return cwidth;
	}

	public void setCwidth(String cwidth) {
		this.cwidth = cwidth;
	}

	public String getCisshow() {
		return cisshow;
	}

	public void setCisshow(String cisshow) {
		this.cisshow = cisshow;
	}

	public String getCisedit() {
		return cisedit;
	}

	public void setCisedit(String cisedit) {
		this.cisedit = cisedit;
	}

}
