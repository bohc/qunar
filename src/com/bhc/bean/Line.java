package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Line extends OrderModel implements Serializable, PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String username, password, tnuname, tnpwd;
	private Summary summary = new Summary();
	private List<Day> days = new ArrayList<Day>();
	private List<Team> teams = new ArrayList<Team>();
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	private List<Vehicle> vehicleSet = new ArrayList<Vehicle>();
	private String freetrippickupairinfo;
	private String agelimit;
	private String soldout;
	private String editime;

	private boolean btcprice;// 是否用界面设置价格

	private String includefee;
	private String excludefee;
	private String kidspricedesc;
	private String kidsprice;
	private String adultprice;
	private String marketprice;
	private String sigledistance;
	private String storenum;
	private String minnumtime;
	private String maxnumtime;
	private boolean daysimgcheck;

	private String viewimgdir;
	private String hotelimgdir;
	private String foodimgdir;
	private String txtdiscription;
	private String prate;

	private boolean singlelist;
	private boolean multilist;
	private String lupath;
	private String mupath;

	private boolean btc1, btc2, adck, payck, btc2ac, btc2acc, limitcountchk;
	private boolean lbc, ldc, lpc, loc, lic, c1up, c1nomain, c1noimg;
	private boolean c2up, c2down, c2del, c2dn;

	private int sendwaitbegin = 1, sendwaitend = 3;
	private String disbegindate, disenddate, discount;
	private boolean reduceprice, earlybookcheck, favorabledisplaycheck;
	private String earlystartdate, earlyenddate, earlybookday, earlydiscount;
	private boolean favorabledisplaytype1, favorabledisplaytype2, favorabledisplaytype3;
	private String favorabledisplaystart, favorabledisplayend, favorabledes;

	private boolean dkeycheck;
	private String dkeytxt;
	private String dkeystyle, priceratedate, priceratedate3;

	private String spic1, spic2, spic3, spic4, spic5, spic6;
	private boolean changespecial, changespecial2, changespecial3;
	private Date specialsatrttime, specialendtime, specialsatrttime2, specialendtime2, specialsatrttime3, specialendtime3;
	private boolean httpmodel, httpsmodel;

	// 以当前日期为准，进行指定天数后的调价
	private boolean changespecial4;
	private int specialdate;
	private int specealprice;

	private boolean downdatecheck1, downdatecheck2;
	private Date down_date_begin, down_date_end;
	private Date pricedate;
	private List<String> pricedates = new ArrayList<String>();
	private int limitcount;
	private boolean waterck;

	private boolean mainpicselect, flashshowuse;
	private String mainpictxt;
	private String phonenum;

	private String cselect;// 是新网站还是旧网站
	private String tnrand;

	private boolean group_method0, group_method1;// 参团方式
	private boolean promise_ironclad_group;// 铁定成团
	private boolean promise_no_self_pay;// 无自费
	private boolean promise_no_shopping;// 无购物
	private boolean promise_truthful_description;// 如实描述

	private boolean promise_guarantee_go;// 出行保障
	private boolean promise_booking_current_day;// 当日可定
	private boolean promise_truthful_description_free;// 如实描述
	private boolean promise_refund_anytime_not_consume;// 未验证消费 随时退

	private boolean have_shopping0, have_shopping1;// 现在已经不用，保留只是为了加载以前的xml数据不出错；

	private String assembly;// 集合地信息
	private String gathertime;// 集合时间
	private String gatherspot;// 集合地点

	private boolean img_publish_style1, img_publish_style2, img_publish_style3;// 行程图片排列方式

	private List<String> dayspic = new ArrayList<String>();

	private int failinterrupt = 30;
	
	private String cookiestr;

	public boolean isWaterck() {
		return waterck;
	}

	public void setWaterck(boolean waterck) {
		propertyChangeSupport.firePropertyChange("waterck", this.waterck, this.waterck = waterck);
	}

	public boolean isChangespecial2() {
		return changespecial2;
	}

	public void setChangespecial2(boolean changespecial2) {
		propertyChangeSupport.firePropertyChange("changespecial2", this.changespecial2, this.changespecial2 = changespecial2);
	}

	public Date getSpecialsatrttime2() {
		return specialsatrttime2;
	}

	public void setSpecialsatrttime2(Date specialsatrttime2) {
		propertyChangeSupport.firePropertyChange("specialsatrttime2", this.specialsatrttime2, this.specialsatrttime2 = specialsatrttime2);
	}

	public Date getSpecialendtime2() {
		return specialendtime2;
	}

	public void setSpecialendtime2(Date specialendtime2) {
		propertyChangeSupport.firePropertyChange("specialendtime2", this.specialendtime2, this.specialendtime2 = specialendtime2);
	}

	public boolean isLimitcountchk() {
		return limitcountchk;
	}

	public void setLimitcountchk(boolean limitcountchk) {
		propertyChangeSupport.firePropertyChange("limitcountchk", this.limitcountchk, this.limitcountchk = limitcountchk);
	}

	public int getLimitcount() {
		return limitcount;
	}

	public void setLimitcount(int limitcount) {
		propertyChangeSupport.firePropertyChange("limitcount", this.limitcount, this.limitcount = limitcount);
	}

	public List<String> getPricedates() {
		return pricedates;
	}

	public void setPricedates(List<String> pricedates) {
		this.pricedates = pricedates;
		propertyChangeSupport.firePropertyChange("pricedates", this.pricedates, this.pricedates = pricedates);
	}

	public Date getPricedate() {
		return pricedate;
	}

	public void setPricedate(Date pricedate) {
		propertyChangeSupport.firePropertyChange("pricedate", this.pricedate, this.pricedate = pricedate);
	}

	public boolean isDowndatecheck1() {
		return downdatecheck1;
	}

	public void setDowndatecheck1(boolean downdatecheck1) {
		// this.downdatecheck1 = downdatecheck1;
		propertyChangeSupport.firePropertyChange("downdatecheck1", this.downdatecheck1, this.downdatecheck1 = downdatecheck1);
	}

	public boolean isDowndatecheck2() {
		return downdatecheck2;
	}

	public void setDowndatecheck2(boolean downdatecheck2) {
		// this.downdatecheck2 = downdatecheck2;
		propertyChangeSupport.firePropertyChange("downdatecheck2", this.downdatecheck2, this.downdatecheck2 = downdatecheck2);
	}

	public Date getDown_date_begin() {
		return down_date_begin;
	}

	public void setDown_date_begin(Date down_date_begin) {
		// this.down_date_begin = down_date_begin;
		propertyChangeSupport.firePropertyChange("down_date_begin", this.down_date_begin, this.down_date_begin = down_date_begin);
	}

	public Date getDown_date_end() {
		return down_date_end;
	}

	public void setDown_date_end(Date down_date_end) {
		// this.down_date_end = down_date_end;
		propertyChangeSupport.firePropertyChange("down_date_end", this.down_date_end, this.down_date_end = down_date_end);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		// this.username = username;
		propertyChangeSupport.firePropertyChange("username", this.username, this.username = username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// this.password = password;
		propertyChangeSupport.firePropertyChange("password", this.password, this.password = password);
	}

	public boolean isHttpmodel() {
		return httpmodel;
	}

	public void setHttpmodel(boolean httpmodel) {
		// this.httpmodel = httpmodel;
		propertyChangeSupport.firePropertyChange("httpmodel", this.httpmodel, this.httpmodel = httpmodel);
	}

	public boolean isHttpsmodel() {
		return httpsmodel;
	}

	public void setHttpsmodel(boolean httpsmodel) {
		// this.httpsmodel = httpsmodel;
		propertyChangeSupport.firePropertyChange("httpsmodel", this.httpsmodel, this.httpsmodel = httpsmodel);
	}

	public boolean isChangespecial4() {
		return changespecial4;
	}

	public void setChangespecial4(boolean changespecial4) {
		propertyChangeSupport.firePropertyChange("changespecial4", this.changespecial4, this.changespecial4 = changespecial4);
	}

	public int getSpecialdate() {
		return specialdate;
	}

	public void setSpecialdate(int specialdate) {
		propertyChangeSupport.firePropertyChange("specialdate", this.specialdate, this.specialdate = specialdate);
	}

	public int getSpecealprice() {
		return specealprice;
	}

	public void setSpecealprice(int specealprice) {
		propertyChangeSupport.firePropertyChange("specealprice", this.specealprice, this.specealprice = specealprice);
	}

	public String getPriceratedate() {
		return priceratedate;
	}

	public void setPriceratedate(String priceratedate) {
		// this.priceratedate = priceratedate;
		propertyChangeSupport.firePropertyChange("priceratedate", this.priceratedate, this.priceratedate = priceratedate);
	}

	public boolean isChangespecial() {
		return changespecial;
	}

	public void setChangespecial(boolean changespecial) {
		// this.changespecial = changespecial;
		propertyChangeSupport.firePropertyChange("changespecial", this.changespecial, this.changespecial = changespecial);
	}

	public Date getSpecialsatrttime() {
		return specialsatrttime;
	}

	public void setSpecialsatrttime(Date specialsatrttime) {
		// this.specialsatrttime = specialsatrttime;
		propertyChangeSupport.firePropertyChange("specialsatrttime", this.specialsatrttime, this.specialsatrttime = specialsatrttime);
	}

	public Date getSpecialendtime() {
		return specialendtime;
	}

	public void setSpecialendtime(Date specialendtime) {
		// this.specialendtime = specialendtime;
		propertyChangeSupport.firePropertyChange("specialendtime", this.specialendtime, this.specialendtime = specialendtime);
	}

	public String getSpic1() {
		return spic1;
	}

	public void setSpic1(String spic1) {
		// this.spic1 = spic1;
		propertyChangeSupport.firePropertyChange("spic1", this.spic1, this.spic1 = spic1);
	}

	public String getSpic2() {
		return spic2;
	}

	public void setSpic2(String spic2) {
		// this.spic2 = spic2;
		propertyChangeSupport.firePropertyChange("spic2", this.spic2, this.spic2 = spic2);
	}

	public String getSpic3() {
		return spic3;
	}

	public void setSpic3(String spic3) {
		// this.spic3 = spic3;
		propertyChangeSupport.firePropertyChange("spic3", this.spic3, this.spic3 = spic3);
	}

	public String getSpic4() {
		return spic4;
	}

	public void setSpic4(String spic4) {
		// this.spic4 = spic4;
		propertyChangeSupport.firePropertyChange("spic4", this.spic4, this.spic4 = spic4);
	}

	public String getSpic5() {
		return spic5;
	}

	public void setSpic5(String spic5) {
		// this.spic5 = spic5;
		propertyChangeSupport.firePropertyChange("spic5", this.spic5, this.spic5 = spic5);
	}

	public String getSpic6() {
		return spic6;
	}

	public void setSpic6(String spic6) {
		// this.spic6 = spic6;
		propertyChangeSupport.firePropertyChange("spic6", this.spic6, this.spic6 = spic6);
	}

	public boolean isBtc2acc() {
		return btc2acc;
	}

	public void setBtc2acc(boolean btc2acc) {
		// this.btc2acc = btc2acc;
		propertyChangeSupport.firePropertyChange("btc2acc", this.btc2acc, this.btc2acc = btc2acc);
	}

	public String getDkeystyle() {
		return dkeystyle;
	}

	public void setDkeystyle(String dkeystyle) {
		// this.dkeystyle = dkeystyle;
		propertyChangeSupport.firePropertyChange("dkeystyle", this.dkeystyle, this.dkeystyle = dkeystyle);
	}

	public boolean isDkeycheck() {
		return dkeycheck;
	}

	public void setDkeycheck(boolean dkeycheck) {
		propertyChangeSupport.firePropertyChange("dkeycheck", this.dkeycheck, this.dkeycheck = dkeycheck);
	}

	public String getDkeytxt() {
		return dkeytxt;
	}

	public void setDkeytxt(String dkeytxt) {
		// this.dkeytxt = dkeytxt;
		propertyChangeSupport.firePropertyChange("dkeytxt", this.dkeytxt, this.dkeytxt = dkeytxt);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
		propertyChangeSupport.firePropertyChange("propertyChangeSupport", this.propertyChangeSupport, this.propertyChangeSupport = propertyChangeSupport);
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		propertyChangeSupport.firePropertyChange("summary", this.summary, this.summary = summary);
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		propertyChangeSupport.firePropertyChange("days", this.days, this.days = days);
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		propertyChangeSupport.firePropertyChange("teams", this.teams, this.teams = teams);
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		propertyChangeSupport.firePropertyChange("vehicles", this.vehicles, this.vehicles = vehicles);
	}

	public List<Vehicle> getVehicleSet() {
		return vehicleSet;
	}

	public void setVehicleSet(List<Vehicle> vehicleSet) {
		propertyChangeSupport.firePropertyChange("vehicleSet", this.vehicleSet, this.vehicleSet = vehicleSet);
	}

	public String getFreetrippickupairinfo() {
		return freetrippickupairinfo;
	}

	public void setFreetrippickupairinfo(String freetrippickupairinfo) {
		// this.freetrippickupairinfo = freetrippickupairinfo;
		propertyChangeSupport.firePropertyChange("freetrippickupairinfo", this.freetrippickupairinfo, this.freetrippickupairinfo = freetrippickupairinfo);
	}

	public String getAgelimit() {
		return agelimit;
	}

	public void setAgelimit(String agelimit) {
		// this.agelimit = agelimit;
		propertyChangeSupport.firePropertyChange("agelimit", this.agelimit, this.agelimit = agelimit);
	}

	public String getSoldout() {
		return soldout;
	}

	public void setSoldout(String soldout) {
		// this.soldout = soldout;
		propertyChangeSupport.firePropertyChange("soldout", this.soldout, this.soldout = soldout);
	}

	public String getEditime() {
		return editime;
	}

	public void setEditime(String editime) {
		// this.editime = editime;
		propertyChangeSupport.firePropertyChange("editime", this.editime, this.editime = editime);
	}

	public boolean isBtcprice() {
		return btcprice;
	}

	public void setBtcprice(boolean btcprice) {
		// this.btcprice = btcprice;
		propertyChangeSupport.firePropertyChange("btcprice", this.btcprice, this.btcprice = btcprice);
	}

	public String getIncludefee() {
		return includefee;
	}

	public void setIncludefee(String includefee) {
		// this.includefee = includefee;
		propertyChangeSupport.firePropertyChange("includefee", this.includefee, this.includefee = includefee);
	}

	public String getExcludefee() {
		return excludefee;
	}

	public void setExcludefee(String excludefee) {
		// this.excludefee = excludefee;
		propertyChangeSupport.firePropertyChange("excludefee", this.excludefee, this.excludefee = excludefee);
	}

	public String getKidspricedesc() {
		return kidspricedesc;
	}

	public void setKidspricedesc(String kidspricedesc) {
		// this.kidspricedesc = kidspricedesc;
		propertyChangeSupport.firePropertyChange("kidspricedesc", this.kidspricedesc, this.kidspricedesc = kidspricedesc);
	}

	public String getKidsprice() {
		return kidsprice;
	}

	public void setKidsprice(String kidsprice) {
		// this.kidsprice = kidsprice;
		propertyChangeSupport.firePropertyChange("kidsprice", this.kidsprice, this.kidsprice = kidsprice);
	}

	public String getAdultprice() {
		return adultprice;
	}

	public void setAdultprice(String adultprice) {
		// this.adultprice = adultprice;
		propertyChangeSupport.firePropertyChange("adultprice", this.adultprice, this.adultprice = adultprice);
	}

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		// this.marketprice = marketprice;
		propertyChangeSupport.firePropertyChange("marketprice", this.marketprice, this.marketprice = marketprice);
	}

	public String getSigledistance() {
		return sigledistance;
	}

	public void setSigledistance(String sigledistance) {
		// this.sigledistance = sigledistance;
		propertyChangeSupport.firePropertyChange("sigledistance", this.sigledistance, this.sigledistance = sigledistance);
	}

	public String getStorenum() {
		return storenum;
	}

	public void setStorenum(String storenum) {
		// this.storenum = storenum;
		propertyChangeSupport.firePropertyChange("storenum", this.storenum, this.storenum = storenum);
	}

	public String getMinnumtime() {
		return minnumtime;
	}

	public void setMinnumtime(String minnumtime) {
		// this.minnumtime = minnumtime;
		propertyChangeSupport.firePropertyChange("minnumtime", this.minnumtime, this.minnumtime = minnumtime);
	}

	public String getMaxnumtime() {
		return maxnumtime;
	}

	public void setMaxnumtime(String maxnumtime) {
		// this.maxnumtime = maxnumtime;
		propertyChangeSupport.firePropertyChange("maxnumtime", this.maxnumtime, this.maxnumtime = maxnumtime);
	}

	public boolean isDaysimgcheck() {
		return daysimgcheck;
	}

	public void setDaysimgcheck(boolean daysimgcheck) {
		// this.daysimgcheck = daysimgcheck;
		propertyChangeSupport.firePropertyChange("daysimgcheck", this.daysimgcheck, this.daysimgcheck = daysimgcheck);
	}

	public String getViewimgdir() {
		return viewimgdir;
	}

	public void setViewimgdir(String viewimgdir) {
		// this.viewimgdir = viewimgdir;
		propertyChangeSupport.firePropertyChange("viewimgdir", this.viewimgdir, this.viewimgdir = viewimgdir);
	}

	public String getHotelimgdir() {
		return hotelimgdir;
	}

	public void setHotelimgdir(String hotelimgdir) {
		// this.hotelimgdir = hotelimgdir;
		propertyChangeSupport.firePropertyChange("hotelimgdir", this.hotelimgdir, this.hotelimgdir = hotelimgdir);
	}

	public String getFoodimgdir() {
		return foodimgdir;
	}

	public void setFoodimgdir(String foodimgdir) {
		// this.foodimgdir = foodimgdir;
		propertyChangeSupport.firePropertyChange("foodimgdir", this.foodimgdir, this.foodimgdir = foodimgdir);
	}

	public String getTxtdiscription() {
		return txtdiscription;
	}

	public void setTxtdiscription(String txtdiscription) {
		// this.txtdiscription = txtdiscription;
		propertyChangeSupport.firePropertyChange("txtdiscription", this.txtdiscription, this.txtdiscription = txtdiscription);
	}

	public String getPrate() {
		return prate;
	}

	public void setPrate(String prate) {
		// this.prate = prate;
		propertyChangeSupport.firePropertyChange("prate", this.prate, this.prate = prate);
	}

	public boolean isSinglelist() {
		return singlelist;
	}

	public void setSinglelist(boolean singlelist) {
		// this.singlelist = singlelist;
		propertyChangeSupport.firePropertyChange("singlelist", this.singlelist, this.singlelist = singlelist);
	}

	public boolean isMultilist() {
		return multilist;
	}

	public void setMultilist(boolean multilist) {
		// this.multilist = multilist;
		propertyChangeSupport.firePropertyChange("multilist", this.multilist, this.multilist = multilist);
	}

	public String getLupath() {
		return lupath;
	}

	public void setLupath(String lupath) {
		// this.lupath = lupath;
		propertyChangeSupport.firePropertyChange("lupath", this.lupath, this.lupath = lupath);
	}

	public String getMupath() {
		return mupath;
	}

	public void setMupath(String mupath) {
		// this.mupath = mupath;
		propertyChangeSupport.firePropertyChange("mupath", this.mupath, this.mupath = mupath);
	}

	public boolean isBtc1() {
		return btc1;
	}

	public void setBtc1(boolean btc1) {
		// this.btc1 = btc1;
		propertyChangeSupport.firePropertyChange("btc1", this.btc1, this.btc1 = btc1);
	}

	public boolean isBtc2() {
		return btc2;
	}

	public void setBtc2(boolean btc2) {
		// this.btc2 = btc2;
		propertyChangeSupport.firePropertyChange("btc2", this.btc2, this.btc2 = btc2);
	}

	public boolean isAdck() {
		return adck;
	}

	public void setAdck(boolean adck) {
		// this.adck = adck;
		propertyChangeSupport.firePropertyChange("adck", this.adck, this.adck = adck);
	}

	public boolean isPayck() {
		return payck;
	}

	public void setPayck(boolean payck) {
		// this.payck = payck;
		propertyChangeSupport.firePropertyChange("payck", this.payck, this.payck = payck);
	}

	public boolean isBtc2ac() {
		return btc2ac;
	}

	public void setBtc2ac(boolean btc2ac) {
		// this.btc2ac = btc2ac;
		propertyChangeSupport.firePropertyChange("btc2ac", this.btc2ac, this.btc2ac = btc2ac);
	}

	public boolean isLbc() {
		return lbc;
	}

	public void setLbc(boolean lbc) {
		// this.lbc = lbc;
		propertyChangeSupport.firePropertyChange("lbc", this.lbc, this.lbc = lbc);
	}

	public boolean isLdc() {
		return ldc;
	}

	public void setLdc(boolean ldc) {
		// this.ldc = ldc;
		propertyChangeSupport.firePropertyChange("ldc", this.ldc, this.ldc = ldc);
	}

	public boolean isLpc() {
		return lpc;
	}

	public void setLpc(boolean lpc) {
		// this.lpc = lpc;
		propertyChangeSupport.firePropertyChange("lpc", this.lpc, this.lpc = lpc);
	}

	public boolean isLoc() {
		return loc;
	}

	public void setLoc(boolean loc) {
		// this.loc = loc;
		propertyChangeSupport.firePropertyChange("loc", this.loc, this.loc = loc);
	}

	public boolean isLic() {
		return lic;
	}

	public void setLic(boolean lic) {
		propertyChangeSupport.firePropertyChange("lic", this.lic, this.lic = lic);
	}

	public boolean isC1up() {
		return c1up;
	}

	public void setC1up(boolean c1up) {
		// this.c1up = c1up;
		propertyChangeSupport.firePropertyChange("c1up", this.c1up, this.c1up = c1up);
	}

	public boolean isC1nomain() {
		return c1nomain;
	}

	public void setC1nomain(boolean c1nomain) {
		// this.c1nomain = c1nomain;
		propertyChangeSupport.firePropertyChange("c1nomain", this.c1nomain, this.c1nomain = c1nomain);
	}

	public boolean isC1noimg() {
		return c1noimg;
	}

	public void setC1noimg(boolean c1noimg) {
		// this.c1noimg = c1noimg;
		propertyChangeSupport.firePropertyChange("c1noimg", this.c1noimg, this.c1noimg = c1noimg);
	}

	public boolean isC2up() {
		return c2up;
	}

	public void setC2up(boolean c2up) {
		// this.c2up = c2up;
		propertyChangeSupport.firePropertyChange("c2up", this.c2up, this.c2up = c2up);
	}

	public boolean isC2down() {
		return c2down;
	}

	public void setC2down(boolean c2down) {
		// this.c2down = c2down;
		propertyChangeSupport.firePropertyChange("c2down", this.c2down, this.c2down = c2down);
	}

	public boolean isC2del() {
		return c2del;
	}

	public void setC2del(boolean c2del) {
		// this.c2del = c2del;
		propertyChangeSupport.firePropertyChange("c2del", this.c2del, this.c2del = c2del);
	}

	public boolean isC2dn() {
		return c2dn;
	}

	public void setC2dn(boolean c2dn) {
		// this.c2dn = c2dn;
		propertyChangeSupport.firePropertyChange("c2dn", this.c2dn, this.c2dn = c2dn);
	}

	public int getSendwaitbegin() {
		return sendwaitbegin;
	}

	public void setSendwaitbegin(int sendwaitbegin) {
		propertyChangeSupport.firePropertyChange("sendwaitbegin", this.sendwaitbegin, this.sendwaitbegin = sendwaitbegin);
	}

	public int getSendwaitend() {
		return sendwaitend;
	}

	public void setSendwaitend(int sendwaitend) {
		propertyChangeSupport.firePropertyChange("sendwaitend", this.sendwaitend, this.sendwaitend = sendwaitend);
	}

	public String getDisbegindate() {
		return disbegindate;
	}

	public void setDisbegindate(String disbegindate) {
		// this.disbegindate = disbegindate;
		propertyChangeSupport.firePropertyChange("disbegindate", this.disbegindate, this.disbegindate = disbegindate);
	}

	public String getDisenddate() {
		return disenddate;
	}

	public void setDisenddate(String disenddate) {
		// this.disenddate = disenddate;
		propertyChangeSupport.firePropertyChange("disenddate", this.disenddate, this.disenddate = disenddate);
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		// this.discount = discount;
		propertyChangeSupport.firePropertyChange("discount", this.discount, this.discount = discount);
	}

	public boolean isReduceprice() {
		return reduceprice;
	}

	public void setReduceprice(boolean reduceprice) {
		// this.reduceprice = reduceprice;
		propertyChangeSupport.firePropertyChange("reduceprice", this.reduceprice, this.reduceprice = reduceprice);
	}

	public boolean isEarlybookcheck() {
		return earlybookcheck;
	}

	public void setEarlybookcheck(boolean earlybookcheck) {
		// this.earlybookcheck = earlybookcheck;
		propertyChangeSupport.firePropertyChange("earlybookcheck", this.earlybookcheck, this.earlybookcheck = earlybookcheck);
	}

	public boolean isFavorabledisplaycheck() {
		return favorabledisplaycheck;
	}

	public void setFavorabledisplaycheck(boolean favorabledisplaycheck) {
		// this.favorabledisplaycheck = favorabledisplaycheck;
		propertyChangeSupport.firePropertyChange("favorabledisplaycheck", this.favorabledisplaycheck, this.favorabledisplaycheck = favorabledisplaycheck);
	}

	public String getEarlystartdate() {
		return earlystartdate;
	}

	public void setEarlystartdate(String earlystartdate) {
		// this.earlystartdate = earlystartdate;
		propertyChangeSupport.firePropertyChange("earlystartdate", this.earlystartdate, this.earlystartdate = earlystartdate);
	}

	public String getEarlyenddate() {
		return earlyenddate;
	}

	public void setEarlyenddate(String earlyenddate) {
		// this.earlyenddate = earlyenddate;
		propertyChangeSupport.firePropertyChange("earlyenddate", this.earlyenddate, this.earlyenddate = earlyenddate);
	}

	public String getEarlybookday() {
		return earlybookday;
	}

	public void setEarlybookday(String earlybookday) {
		// this.earlybookday = earlybookday;
		propertyChangeSupport.firePropertyChange("earlybookday", this.earlybookday, this.earlybookday = earlybookday);
	}

	public String getEarlydiscount() {
		return earlydiscount;
	}

	public void setEarlydiscount(String earlydiscount) {
		// this.earlydiscount = earlydiscount;
		propertyChangeSupport.firePropertyChange("earlydiscount", this.earlydiscount, this.earlydiscount = earlydiscount);
	}

	public boolean isFavorabledisplaytype1() {
		return favorabledisplaytype1;
	}

	public void setFavorabledisplaytype1(boolean favorabledisplaytype1) {
		// this.favorabledisplaytype1 = favorabledisplaytype1;
		propertyChangeSupport.firePropertyChange("favorabledisplaytype1", this.favorabledisplaytype1, this.favorabledisplaytype1 = favorabledisplaytype1);
	}

	public boolean isFavorabledisplaytype2() {
		return favorabledisplaytype2;
	}

	public void setFavorabledisplaytype2(boolean favorabledisplaytype2) {
		// this.favorabledisplaytype2 = favorabledisplaytype2;
		propertyChangeSupport.firePropertyChange("favorabledisplaytype2", this.favorabledisplaytype2, this.favorabledisplaytype2 = favorabledisplaytype2);
	}

	public boolean isFavorabledisplaytype3() {
		return favorabledisplaytype3;
	}

	public void setFavorabledisplaytype3(boolean favorabledisplaytype3) {
		// this.favorabledisplaytype3 = favorabledisplaytype3;
		propertyChangeSupport.firePropertyChange("favorabledisplaytype3", this.favorabledisplaytype3, this.favorabledisplaytype3 = favorabledisplaytype3);
	}

	public String getFavorabledisplaystart() {
		return favorabledisplaystart;
	}

	public void setFavorabledisplaystart(String favorabledisplaystart) {
		// this.favorabledisplaystart = favorabledisplaystart;
		propertyChangeSupport.firePropertyChange("favorabledisplaystart", this.favorabledisplaystart, this.favorabledisplaystart = favorabledisplaystart);
	}

	public String getFavorabledisplayend() {
		return favorabledisplayend;
	}

	public void setFavorabledisplayend(String favorabledisplayend) {
		// this.favorabledisplayend = favorabledisplayend;
		propertyChangeSupport.firePropertyChange("favorabledisplayend", this.favorabledisplayend, this.favorabledisplayend = favorabledisplayend);
	}

	public String getFavorabledes() {
		return favorabledes;
	}

	public void setFavorabledes(String favorabledes) {
		// this.favorabledes = favorabledes;
		propertyChangeSupport.firePropertyChange("favorabledes", this.favorabledes, this.favorabledes = favorabledes);
	}

	public String getPriceratedate3() {
		return priceratedate3;
	}

	public void setPriceratedate3(String priceratedate3) {
		propertyChangeSupport.firePropertyChange("priceratedate3", this.priceratedate3, this.priceratedate3 = priceratedate3);
	}

	public boolean isChangespecial3() {
		return changespecial3;
	}

	public void setChangespecial3(boolean changespecial3) {
		propertyChangeSupport.firePropertyChange("changespecial3", this.changespecial3, this.changespecial3 = changespecial3);
	}

	public Date getSpecialsatrttime3() {
		return specialsatrttime3;
	}

	public void setSpecialsatrttime3(Date specialsatrttime3) {
		propertyChangeSupport.firePropertyChange("specialsatrttime3", this.specialsatrttime3, this.specialsatrttime3 = specialsatrttime3);
	}

	public Date getSpecialendtime3() {
		return specialendtime3;
	}

	public void setSpecialendtime3(Date specialendtime3) {
		propertyChangeSupport.firePropertyChange("specialendtime3", this.specialendtime3, this.specialendtime3 = specialendtime3);
	}

	public boolean isMainpicselect() {
		return mainpicselect;
	}

	public void setMainpicselect(boolean mainpicselect) {
		propertyChangeSupport.firePropertyChange("mainpicselect", this.mainpicselect, this.mainpicselect = mainpicselect);
	}

	public String getMainpictxt() {
		return mainpictxt;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		propertyChangeSupport.firePropertyChange("phonenum", this.phonenum, this.phonenum = phonenum);
	}

	public void setMainpictxt(String mainpictxt) {
		propertyChangeSupport.firePropertyChange("mainpictxt", this.mainpictxt, this.mainpictxt = mainpictxt);
	}

	public List<String> getDayspic() {
		return dayspic;
	}

	public void setDayspic(List<String> dayspic) {
		propertyChangeSupport.firePropertyChange("dayspic", this.dayspic, this.dayspic = dayspic);
	}

	public boolean getFlashshowuse() {
		return flashshowuse;
	}

	public void setFlashshowuse(boolean flashshowuse) {
		propertyChangeSupport.firePropertyChange("flashshowuse", this.flashshowuse, this.flashshowuse = flashshowuse);
	}

	public String getCselect() {
		return cselect;
	}

	public void setCselect(String cselect) {
		propertyChangeSupport.firePropertyChange("cselect", this.cselect, this.cselect = cselect);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getTnuname() {
		return tnuname;
	}

	public void setTnuname(String tnuname) {
		propertyChangeSupport.firePropertyChange("tnuname", this.tnuname, this.tnuname = tnuname);
	}

	public String getTnpwd() {
		return tnpwd;
	}

	public void setTnpwd(String tnpwd) {
		propertyChangeSupport.firePropertyChange("tnpwd", this.tnpwd, this.tnpwd = tnpwd);
	}

	public String getTnrand() {
		return tnrand;
	}

	public void setTnrand(String tnrand) {
		propertyChangeSupport.firePropertyChange("tnrand", this.tnrand, this.tnrand = tnrand);
	}

	public boolean isGroup_method0() {
		return group_method0;
	}

	public void setGroup_method0(boolean group_method0) {
		propertyChangeSupport.firePropertyChange("group_method0", this.group_method0, this.group_method0 = group_method0);
	}

	public boolean isGroup_method1() {
		return group_method1;
	}

	public void setGroup_method1(boolean group_method1) {
		propertyChangeSupport.firePropertyChange("group_method1", this.group_method1, this.group_method1 = group_method1);
	}

	public boolean isPromise_ironclad_group() {
		return promise_ironclad_group;
	}

	public void setPromise_ironclad_group(boolean promise_ironclad_group) {
		propertyChangeSupport.firePropertyChange("promise_ironclad_group", this.promise_ironclad_group, this.promise_ironclad_group = promise_ironclad_group);
	}

	public boolean isPromise_no_self_pay() {
		return promise_no_self_pay;
	}

	public void setPromise_no_self_pay(boolean promise_no_self_pay) {
		propertyChangeSupport.firePropertyChange("promise_no_self_pay", this.promise_no_self_pay, this.promise_no_self_pay = promise_no_self_pay);
	}

	public boolean isPromise_no_shopping() {
		return promise_no_shopping;
	}

	public void setPromise_no_shopping(boolean promise_no_shopping) {
		propertyChangeSupport.firePropertyChange("promise_no_shopping", this.promise_no_shopping, this.promise_no_shopping = promise_no_shopping);
	}

	public boolean isPromise_truthful_description() {
		return promise_truthful_description;
	}

	public void setPromise_truthful_description(boolean promise_truthful_description) {
		propertyChangeSupport.firePropertyChange("promise_truthful_description", this.promise_truthful_description, this.promise_truthful_description = promise_truthful_description);
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		propertyChangeSupport.firePropertyChange("assembly", this.assembly, this.assembly = assembly);
	}

	
	
	public String getGathertime() {
		return gathertime;
	}

	public void setGathertime(String gathertime) {
		propertyChangeSupport.firePropertyChange("gathertime", this.gathertime, this.gathertime = gathertime);
	}

	public String getGatherspot() {
		return gatherspot;
	}

	public void setGatherspot(String gatherspot) {
		propertyChangeSupport.firePropertyChange("gatherspot", this.gatherspot, this.gatherspot = gatherspot);
	}

	public boolean isImg_publish_style1() {
		return img_publish_style1;
	}

	public void setImg_publish_style1(boolean img_publish_style1) {
		propertyChangeSupport.firePropertyChange("img_publish_style1", this.img_publish_style1, this.img_publish_style1 = img_publish_style1);
	}

	public boolean isImg_publish_style2() {
		return img_publish_style2;
	}

	public void setImg_publish_style2(boolean img_publish_style2) {
		propertyChangeSupport.firePropertyChange("img_publish_style2", this.img_publish_style2, this.img_publish_style2 = img_publish_style2);
	}

	public boolean isImg_publish_style3() {
		return img_publish_style3;
	}

	public void setImg_publish_style3(boolean img_publish_style3) {
		propertyChangeSupport.firePropertyChange("img_publish_style3", this.img_publish_style3, this.img_publish_style3 = img_publish_style3);
	}

	public boolean isHave_shopping0() {
		return have_shopping0;
	}

	public void setHave_shopping0(boolean have_shopping0) {
		propertyChangeSupport.firePropertyChange("have_shopping0", this.have_shopping0, this.have_shopping0 = have_shopping0);
	}

	public boolean isHave_shopping1() {
		return have_shopping1;
	}

	public void setHave_shopping1(boolean have_shopping1) {
		propertyChangeSupport.firePropertyChange("have_shopping1", this.have_shopping1, this.have_shopping1 = have_shopping1);
	}

	public boolean isPromise_guarantee_go() {
		return promise_guarantee_go;
	}

	public void setPromise_guarantee_go(boolean promise_guarantee_go) {
		propertyChangeSupport.firePropertyChange("promise_guarantee_go", this.promise_guarantee_go, this.promise_guarantee_go = promise_guarantee_go);
	}

	public boolean isPromise_booking_current_day() {
		return promise_booking_current_day;
	}

	public void setPromise_booking_current_day(boolean promise_booking_current_day) {
		propertyChangeSupport.firePropertyChange("promise_booking_current_day", this.promise_booking_current_day, this.promise_booking_current_day = promise_booking_current_day);
	}

	public boolean isPromise_truthful_description_free() {
		return promise_truthful_description_free;
	}

	public void setPromise_truthful_description_free(boolean promise_truthful_description_free) {
		propertyChangeSupport.firePropertyChange("promise_truthful_description_free", this.promise_truthful_description_free, this.promise_truthful_description_free = promise_truthful_description_free);
	}

	public boolean isPromise_refund_anytime_not_consume() {
		return promise_refund_anytime_not_consume;
	}

	public void setPromise_refund_anytime_not_consume(boolean promise_refund_anytime_not_consume) {
		propertyChangeSupport.firePropertyChange("promise_refund_anytime_not_consume", this.promise_refund_anytime_not_consume, this.promise_refund_anytime_not_consume = promise_refund_anytime_not_consume);
	}

	public int getFailinterrupt() {
		return failinterrupt;
	}

	public void setFailinterrupt(int failinterrupt) {
		propertyChangeSupport.firePropertyChange("failinterrupt", this.failinterrupt, this.failinterrupt = failinterrupt);
	}

	public String getCookiestr() {
		return cookiestr;
	}

	public void setCookiestr(String cookiestr) {
		propertyChangeSupport.firePropertyChange("cookiestr", this.cookiestr, this.cookiestr = cookiestr);
	}

	
}
