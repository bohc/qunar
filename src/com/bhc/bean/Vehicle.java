package com.bhc.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Vehicle extends OrderModel implements Serializable, PropertyChangeListener {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private String arrtime;
	private String deptime;
	private String arrcity;
	private String depcity;
	private String arrairport;
	private String depairport;
	private String flightno;
	private String flightype;

	public String getArrtime() {
		return arrtime;
	}

	public void setArrtime(String arrtime) {
		propertyChangeSupport.firePropertyChange("arrtime", this.arrtime, this.arrtime = arrtime);
	}

	public String getDeptime() {
		return deptime;
	}

	public void setDeptime(String deptime) {
		propertyChangeSupport.firePropertyChange("deptime", this.deptime, this.deptime = deptime);
	}

	public String getArrcity() {
		return arrcity;
	}

	public void setArrcity(String arrcity) {
		propertyChangeSupport.firePropertyChange("arrcity", this.arrcity, this.arrcity = arrcity);
	}

	public String getDepcity() {
		return depcity;
	}

	public void setDepcity(String depcity) {
		propertyChangeSupport.firePropertyChange("depcity", this.depcity, this.depcity = depcity);
	}

	public String getArrairport() {
		return arrairport;
	}

	public void setArrairport(String arrairport) {
		propertyChangeSupport.firePropertyChange("arrairport", this.arrairport, this.arrairport = arrairport);
	}

	public String getDepairport() {
		return depairport;
	}

	public void setDepairport(String depairport) {
		propertyChangeSupport.firePropertyChange("depairport", this.depairport, this.depairport = depairport);
	}

	public String getFlightno() {
		return flightno;
	}

	public void setFlightno(String flightno) {
		propertyChangeSupport.firePropertyChange("flightno", this.flightno, this.flightno = flightno);
	}

	public String getFlightype() {
		return flightype;
	}

	public void setFlightype(String flightype) {
		propertyChangeSupport.firePropertyChange("flightype", this.flightype, this.flightype = flightype);
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
