package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Summary extends OrderModel implements Serializable, PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String title;
	private String recommendation;// 产品副标题
	private String teamno;
	private String placeid;
	private String resourceid;
	private String pfunction;
	private String extfunction;
	private String subfunction;
	private String pcomposition, linesubject;
	private String day;
	private String advanceday;
	private String advancedaytype;
	private String advancehour;// 跟团游用
	private String advanceminute;// 跟团游用
	private String advancedesc;
	private String departure;
	private String arrive;
	private String arrivecity;
	private String arrivetype;
	private String distancetype;
	private String freetriptotraffic;
	private String freetripbacktraffic;
	private String image;
	private List<String> feature = new ArrayList<String>();
	private String feeinclude;
	private String feeexclude;
	private String attention;
	private String prebook;
	private String tip;
	private String features;
	private boolean payway1, payway2, payway3;
	private String txtfeaturepath, confeature;
	private boolean featurechecked;

	public String getAdvancehour() {
		return advancehour;
	}

	public void setAdvancehour(String advancehour) {
		propertyChangeSupport.firePropertyChange("advancehour", this.advancehour, this.advancehour = advancehour);
	}

	public String getAdvanceminute() {
		return advanceminute;
	}

	public void setAdvanceminute(String advanceminute) {
		propertyChangeSupport.firePropertyChange("advanceminute", this.advanceminute, this.advanceminute = advanceminute);
	}

	public String getAdvancedesc() {
		return advancedesc;
	}

	public void setAdvancedesc(String advancedesc) {
		propertyChangeSupport.firePropertyChange("advancedesc", this.advancedesc, this.advancedesc = advancedesc);
	}

	public String getLinesubject() {
		return linesubject;
	}

	public void setLinesubject(String linesubject) {
		propertyChangeSupport.firePropertyChange("linesubject", this.linesubject, this.linesubject = linesubject);
	}

	public boolean isPayway3() {
		return payway3;
	}

	public void setPayway3(boolean payway3) {
		// this.payway3 = payway3;
		propertyChangeSupport.firePropertyChange("payway3", this.payway3, this.payway3 = payway3);
	}

	public String getPrebook() {
		return prebook;
	}

	public void setPrebook(String prebook) {
		propertyChangeSupport.firePropertyChange("prebook", this.prebook, this.prebook = prebook);
	}

	public boolean isFeaturechecked() {
		return featurechecked;
	}

	public void setFeaturechecked(boolean featurechecked) {
		// this.featurechecked = featurechecked;
		propertyChangeSupport.firePropertyChange("featurechecked", this.featurechecked, this.featurechecked = featurechecked);
	}

	public String getConfeature() {
		return confeature;
	}

	public void setConfeature(String confeature) {
		propertyChangeSupport.firePropertyChange("confeature", this.confeature, this.confeature = confeature);
	}

	public String getTxtfeaturepath() {
		return txtfeaturepath;
	}

	public void setTxtfeaturepath(String txtfeaturepath) {
		propertyChangeSupport.firePropertyChange("txtfeaturepath", this.txtfeaturepath, this.txtfeaturepath = txtfeaturepath);
	}

	public boolean isPayway1() {
		return payway1;
	}

	public void setPayway1(boolean payway1) {
		// this.payway1 = payway1;
		propertyChangeSupport.firePropertyChange("payway1", this.payway1, this.payway1 = payway1);
	}

	public boolean isPayway2() {
		return payway2;
	}

	public void setPayway2(boolean payway2) {
		// this.payway2 = payway2;
		propertyChangeSupport.firePropertyChange("payway2", this.payway2, this.payway2 = payway2);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		// this.title = title;
		propertyChangeSupport.firePropertyChange("title", this.title, this.title = title);
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		// this.recommendation = recommendation;
		propertyChangeSupport.firePropertyChange("recommendation", this.recommendation, this.recommendation = recommendation);
	}

	public String getTeamno() {
		return teamno;
	}

	public void setTeamno(String teamno) {
		// this.teamno = teamno;
		propertyChangeSupport.firePropertyChange("teamno", this.teamno, this.teamno = teamno);
	}

	public String getPlaceid() {
		return placeid;
	}

	public void setPlaceid(String placeid) {
		propertyChangeSupport.firePropertyChange("placeid", this.placeid, this.placeid = placeid);
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		// this.resourceid = resourceid;
		propertyChangeSupport.firePropertyChange("resourceid", this.resourceid, this.resourceid = resourceid);
	}

	public String getPfunction() {
		return pfunction;
	}

	public void setPfunction(String pfunction) {
		propertyChangeSupport.firePropertyChange("pfunction", this.pfunction, this.pfunction = pfunction);
	}

	public String getExtfunction() {
		return extfunction;
	}

	public void setExtfunction(String extfunction) {
		propertyChangeSupport.firePropertyChange("extfunction", this.extfunction, this.extfunction = extfunction);
	}

	public String getSubfunction() {
		return subfunction;
	}

	public void setSubfunction(String subfunction) {
		propertyChangeSupport.firePropertyChange("subfunction", this.subfunction, this.subfunction = subfunction);
	}

	public String getPcomposition() {
		return pcomposition;
	}

	public void setPcomposition(String pcomposition) {
		// this.pcomposition = pcomposition;
		propertyChangeSupport.firePropertyChange("pcomposition", this.pcomposition, this.pcomposition = pcomposition);
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		// this.day = day;
		propertyChangeSupport.firePropertyChange("day", this.day, this.day = day);
	}

	public String getAdvanceday() {
		return advanceday;
	}

	public void setAdvanceday(String advanceday) {
		// this.advanceday = advanceday;
		propertyChangeSupport.firePropertyChange("advanceday", this.advanceday, this.advanceday = advanceday);
	}

	public String getAdvancedaytype() {
		return advancedaytype;
	}

	public void setAdvancedaytype(String advancedaytype) {
		// this.advancedaytype = advancedaytype;
		propertyChangeSupport.firePropertyChange("advancedaytype", this.advancedaytype, this.advancedaytype = advancedaytype);
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		// this.departure = departure;
		propertyChangeSupport.firePropertyChange("departure", this.departure, this.departure = departure);
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		// this.arrive = arrive;
		propertyChangeSupport.firePropertyChange("arrive", this.arrive, this.arrive = arrive);
	}

	public String getArrivecity() {
		return arrivecity;
	}

	public void setArrivecity(String arrivecity) {
		propertyChangeSupport.firePropertyChange("arrivecity", this.arrivecity, this.arrivecity = arrivecity);
	}

	public String getArrivetype() {
		return arrivetype;
	}

	public void setArrivetype(String arrivetype) {
		// this.arrivetype = arrivetype;
		propertyChangeSupport.firePropertyChange("arrivetype", this.arrivetype, this.arrivetype = arrivetype);
	}

	public String getDistancetype() {
		return distancetype;
	}

	public void setDistancetype(String distancetype) {
		// this.distancetype = distancetype;
		propertyChangeSupport.firePropertyChange("distancetype", this.distancetype, this.distancetype = distancetype);
	}

	public String getFreetriptotraffic() {
		return freetriptotraffic;
	}

	public void setFreetriptotraffic(String freetriptotraffic) {
		// this.freetriptotraffic = freetriptotraffic;
		propertyChangeSupport.firePropertyChange("freetriptotraffic", this.freetriptotraffic, this.freetriptotraffic = freetriptotraffic);
	}

	public String getFreetripbacktraffic() {
		return freetripbacktraffic;
	}

	public void setFreetripbacktraffic(String freetripbacktraffic) {
		// this.freetripbacktraffic = freetripbacktraffic;
		propertyChangeSupport.firePropertyChange("freetripbacktraffic", this.freetripbacktraffic, this.freetripbacktraffic = freetripbacktraffic);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		// this.image = image;
		propertyChangeSupport.firePropertyChange("image", this.image, this.image = image);
	}

	public List<String> getFeature() {
		return feature;
	}

	public void setFeature(List<String> feature) {
		// this.feature = feature;
		propertyChangeSupport.firePropertyChange("feature", this.feature, this.feature = feature);
	}

	public String getFeeinclude() {
		return feeinclude;
	}

	public void setFeeinclude(String feeinclude) {
		// this.feeinclude = feeinclude;
		propertyChangeSupport.firePropertyChange("feeinclude", this.feeinclude, this.feeinclude = feeinclude);
	}

	public String getFeeexclude() {
		return feeexclude;
	}

	public void setFeeexclude(String feeexclude) {
		// this.feeexclude = feeexclude;
		propertyChangeSupport.firePropertyChange("feeexclude", this.feeexclude, this.feeexclude = feeexclude);
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		// this.attention = attention;
		propertyChangeSupport.firePropertyChange("attention", this.attention, this.attention = attention);
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		// this.tip = tip;
		propertyChangeSupport.firePropertyChange("tip", this.tip, this.tip = tip);
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		// this.features = features;
		propertyChangeSupport.firePropertyChange("features", this.features, this.features = features);
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
}
