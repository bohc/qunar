package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Team extends OrderModel implements Serializable, PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String pricedesc;
	private String availablecount;
	private String roomsendprice;
	private String roomnum;
	private String childprice;
	private String adultprice;
	private String qunarprice;
	private String marketprice;
	private String takeoffdate;

	public String getPricedesc() {
		return pricedesc;
	}

	public void setPricedesc(String pricedesc) {
		// this.pricedesc = pricedesc;
		propertyChangeSupport.firePropertyChange("pricedesc", this.pricedesc, this.pricedesc = pricedesc);
	}

	public String getAvailablecount() {
		return availablecount;
	}

	public void setAvailablecount(String availablecount) {
		// this.availablecount = availablecount;
		propertyChangeSupport.firePropertyChange("availablecount", this.availablecount, this.availablecount = availablecount);
	}

	public String getRoomsendprice() {
		return roomsendprice;
	}

	public void setRoomsendprice(String roomsendprice) {
		// this.roomsendprice = roomsendprice;
		propertyChangeSupport.firePropertyChange("roomsendprice", this.roomsendprice, this.roomsendprice = roomsendprice);
	}

	public String getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(String roomnum) {
		// this.roomnum = roomnum;
		propertyChangeSupport.firePropertyChange("roomnum", this.roomnum, this.roomnum = roomnum);
	}

	public String getChildprice() {
		return childprice;
	}

	public void setChildprice(String childprice) {
		// this.childprice = childprice;
		propertyChangeSupport.firePropertyChange("childprice", this.childprice, this.childprice = childprice);
	}

	public String getAdultprice() {
		return adultprice;
	}

	public void setAdultprice(String adultprice) {
		// this.adultprice = adultprice;
		propertyChangeSupport.firePropertyChange("adultprice", this.adultprice, this.adultprice = adultprice);
	}

	public String getQunarprice() {
		return qunarprice;
	}

	public void setQunarprice(String qunarprice) {
		propertyChangeSupport.firePropertyChange("qunarprice", this.qunarprice, this.qunarprice = qunarprice);
	}

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		// this.marketprice = marketprice;
		propertyChangeSupport.firePropertyChange("marketprice", this.marketprice, this.marketprice = marketprice);
	}

	public String getTakeoffdate() {
		return takeoffdate;
	}

	public void setTakeoffdate(String takeoffdate) {
		// this.takeoffdate = takeoffdate;
		propertyChangeSupport.firePropertyChange("takeoffdate", this.takeoffdate, this.takeoffdate = takeoffdate);
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
