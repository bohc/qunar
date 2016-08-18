package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Day extends OrderModel implements Serializable, PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String daytitle;
	private String daynum;
	private String sightimage;
	private String daydescription;
	private String daytraffic;
	private String stardesc;
	private String starname;
	private String innfeature;
	private String innmark;
	private String breakfirst;
	private String breakfirstdesc;
	private String lunch;
	private String lunchdesc;
	private String supper;
	private String supperdesc;
	private String other;
	private String otherdesc;
	private String shopping;
	private String selfexpense;
	private String activity;

	public String getDaytitle() {
		return daytitle;
	}

	public void setDaytitle(String daytitle) {
		// this.daytitle = daytitle;
		propertyChangeSupport.firePropertyChange("daytitle", this.daytitle, this.daytitle = daytitle);
	}

	public String getDaynum() {
		return daynum;
	}

	public void setDaynum(String daynum) {
		// this.daynum = daynum;
		propertyChangeSupport.firePropertyChange("daynum", this.daynum, this.daynum = daynum);
	}

	public String getSightimage() {
		return sightimage;
	}

	public void setSightimage(String sightimage) {
		// this.sightimage = sightimage;
		propertyChangeSupport.firePropertyChange("sightimage", this.sightimage, this.sightimage = sightimage);
	}

	public String getDaydescription() {
		return daydescription;
	}

	public void setDaydescription(String daydescription) {
		// this.daydescription = daydescription;
		propertyChangeSupport.firePropertyChange("daydescription", this.daydescription, this.daydescription = daydescription);
	}

	public String getDaytraffic() {
		return daytraffic;
	}

	public void setDaytraffic(String daytraffic) {
		// this.daytraffic = daytraffic;
		propertyChangeSupport.firePropertyChange("daytraffic", this.daytraffic, this.daytraffic = daytraffic);
	}

	public String getStardesc() {
		return stardesc;
	}

	public void setStardesc(String stardesc) {
		// this.stardesc = stardesc;
		propertyChangeSupport.firePropertyChange("stardesc", this.stardesc, this.stardesc = stardesc);
	}

	public String getStarname() {
		return starname;
	}

	public void setStarname(String starname) {
		// this.starname = starname;
		propertyChangeSupport.firePropertyChange("starname", this.starname, this.starname = starname);
	}

	public String getInnfeature() {
		return innfeature;
	}

	public void setInnfeature(String innfeature) {
		propertyChangeSupport.firePropertyChange("innfeature", this.innfeature, this.innfeature = innfeature);
	}

	public String getInnmark() {
		return innmark;
	}

	public void setInnmark(String innmark) {
		propertyChangeSupport.firePropertyChange("innmark", this.innmark, this.innmark = innmark);
	}

	public String getBreakfirst() {
		return breakfirst;
	}

	public void setBreakfirst(String breakfirst) {
		// this.breakfirst = breakfirst;
		propertyChangeSupport.firePropertyChange("breakfirst", this.breakfirst, this.breakfirst = breakfirst);
	}

	public String getBreakfirstdesc() {
		return breakfirstdesc;
	}

	public void setBreakfirstdesc(String breakfirstdesc) {
		// this.breakfirstdesc = breakfirstdesc;
		propertyChangeSupport.firePropertyChange("breakfirstdesc", this.breakfirstdesc, this.breakfirstdesc = breakfirstdesc);
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		// this.lunch = lunch;
		propertyChangeSupport.firePropertyChange("lunch", this.lunch, this.lunch = lunch);
	}

	public String getLunchdesc() {
		return lunchdesc;
	}

	public void setLunchdesc(String lunchdesc) {
		// this.lunchdesc = lunchdesc;
		propertyChangeSupport.firePropertyChange("lunchdesc", this.lunchdesc, this.lunchdesc = lunchdesc);
	}

	public String getSupper() {
		return supper;
	}

	public void setSupper(String supper) {
		// this.supper = supper;
		propertyChangeSupport.firePropertyChange("supper", this.supper, this.supper = supper);
	}

	public String getSupperdesc() {
		return supperdesc;
	}

	public void setSupperdesc(String supperdesc) {
		// this.supperdesc = supperdesc;
		propertyChangeSupport.firePropertyChange("supperdesc", this.supperdesc, this.supperdesc = supperdesc);
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		// this.other = other;
		propertyChangeSupport.firePropertyChange("other", this.other, this.other = other);
	}

	public String getOtherdesc() {
		return otherdesc;
	}

	public void setOtherdesc(String otherdesc) {
		// this.otherdesc = otherdesc;
		propertyChangeSupport.firePropertyChange("otherdesc", this.otherdesc, this.otherdesc = otherdesc);
	}

	public String getShopping() {
		return shopping;
	}

	public void setShopping(String shopping) {
		propertyChangeSupport.firePropertyChange("shopping", this.shopping, this.shopping = shopping);
	}

	public String getSelfexpense() {
		return selfexpense;
	}

	public void setSelfexpense(String selfexpense) {
		propertyChangeSupport.firePropertyChange("selfexpense", this.selfexpense, this.selfexpense = selfexpense);
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		propertyChangeSupport.firePropertyChange("activity", this.activity, this.activity = activity);
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
