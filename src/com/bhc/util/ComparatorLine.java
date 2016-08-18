package com.bhc.util;

import java.util.Comparator;
import com.bhc.bean.DupRecords;

@SuppressWarnings("rawtypes")
public class ComparatorLine implements Comparator {
	public int compare(Object arg0, Object arg1) {
		DupRecords user0 = (DupRecords) arg0;
		DupRecords user1 = (DupRecords) arg1;
		int flag = user0.getCtime().compareTo(user1.getCtime());
		return flag;
	}
}