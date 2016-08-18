package com.bhc.util;

import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class MyColumnSorter extends ViewerSorter {
	private static int propertyIndex;
	private static final int DESCENDING = 1;
	private static int direction = DESCENDING;
	private Map<String, String> lmap=null;

	public MyColumnSorter(Map<String, String> lmap) {
		this.lmap=lmap;
	}

	@SuppressWarnings("static-access")
	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			direction = -direction;
		} else {
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int rc = 0;
		String value=lmap.get(String.valueOf(propertyIndex)).toString();
		Object v1=ClassType.getFieldValue(e1,value);
		Object v2=ClassType.getFieldValue(e2,value);
		if(v1==null)v1="";
		if(v2==null)v2="";
		rc=v1.toString().compareTo(v2.toString());
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

}