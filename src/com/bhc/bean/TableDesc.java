package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@SuppressWarnings("serial")
public class TableDesc extends OrderModel implements Serializable,PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private String cname;
	private String ctype;
	private String isnull;
	private String ckey;
	private String extra;
	private String privileges;
	private String ccomment;
	private String dvalue;
	private Boolean isselect;
	private String htmlwidget="text";
	private String prevalue="";
	private String prevsource="db";//预选值来源

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		propertyChangeSupport.firePropertyChange("cname", this.cname, this.cname=cname);
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		propertyChangeSupport.firePropertyChange("ctype", this.ctype, this.ctype=ctype);
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		propertyChangeSupport.firePropertyChange("isnull", this.isnull, this.isnull=isnull);
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		propertyChangeSupport.firePropertyChange("ckey", this.ckey, this.ckey=ckey);
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		propertyChangeSupport.firePropertyChange("extra", this.extra, this.extra=extra);
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		propertyChangeSupport.firePropertyChange("privileges", this.privileges, this.privileges=privileges);
	}

	public String getCcomment() {
		return ccomment;
	}

	public void setCcomment(String ccomment) {
		propertyChangeSupport.firePropertyChange("ccomment", this.ccomment, this.ccomment=ccomment);
	}

	public Boolean getIsselect() {
		return isselect;
	}

	public void setIsselect(Boolean isselect) {
		propertyChangeSupport.firePropertyChange("isselect", this.isselect, this.isselect=isselect);
	}

	public String getHtmlwidget() {
		return htmlwidget;
	}

	public void setHtmlwidget(String htmlwidget) {
		propertyChangeSupport.firePropertyChange("htmlwidget", this.htmlwidget, this.htmlwidget=htmlwidget);
	}

	public String getPrevalue() {
		return prevalue;
	}

	public void setPrevalue(String prevalue) {
		propertyChangeSupport.firePropertyChange("prevalue", this.prevalue, this.prevalue=prevalue);
	}
	
	public String getDvalue() {
		return dvalue;
	}

	public void setDvalue(String dvalue) {
		propertyChangeSupport.firePropertyChange("dvalue", this.dvalue, this.dvalue=dvalue);
	}

	public String getPrevsource() {
		return prevsource;
	}

	public void setPrevsource(String prevsource) {
		propertyChangeSupport.firePropertyChange("prevsource", this.prevsource, this.prevsource=prevsource);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public String toString(){
		return "cname:"+cname
		+";ctype:"+ctype
		+";isnull:"+isnull
		+";ckey:"+ckey
		+";extra:"+extra
		+";privile:"+privileges
		+";ccomment:"+ccomment
		+";dvalue:"+dvalue
		+";isselect:"+isselect
		+";htmlwidget:"+htmlwidget
		+";prevalue:"+prevalue;
	}
}
