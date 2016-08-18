package com.bhc.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

/**
 * @since 3.2
 * 
 */
public class MyCellModifier implements ICellModifier {
	private Map<String, ColumnsDesc> lmap = null;
//	private TableViewer tv;
	// 主要用来标识哪一列是什么类型，如下拉，文本等
	private Map<String, String[]> fmap = null;
	private Set<Integer> ceditable=null;

	public MyCellModifier(Map<String, ColumnsDesc> mcds,Map<String, String[]> fmap,Set<Integer> ceditable) {
		super();
		this.lmap = mcds;
		this.fmap = fmap;
		this.ceditable=ceditable;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
	 * java.lang.String)
	 */
	public boolean canModify(Object tabledesc, String column) {
		if(ceditable==null)
			return true;
		if(ceditable.contains(Integer.parseInt(column)))
			return true;
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
	 * java.lang.String)
	 */
	public Object getValue(Object o, String column) {
		ColumnsDesc cd = (ColumnsDesc)lmap.get(column);
		Method m[] = o.getClass().getDeclaredMethods();
		Object ov =null;
		for (int i = 0; i < m.length; i++) {
			// --------------------获得方法的名字
			String mname=m[i].getName();
			if(!mname.startsWith("get")){
				continue;
			}
			String cname=mname.substring(3);
			
			String cv = cd.getCname();
			if(!cname.toLowerCase().equals(cv)){
				continue;
			}
			
			try {
				ov=m[i].invoke(o,null);
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			}
			break;
		}
		if (StringUtils.defaultString(cd.getCtype()).equals("select")) {
			return getNameIndex(fmap.get(column), ov.toString());
		}
		if (ov == null) {
			return "";
		}
		return ov.toString();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
	 * java.lang.String, java.lang.Object)
	 */
	public void modify(Object tableitem, String column, Object value) {
		if (value != null) {
			String v = value.toString();
			ColumnsDesc cd = (ColumnsDesc)lmap.get(column);
			if (StringUtils.defaultString(cd.getCtype()).equals("select")) {
				Integer sv = Integer.parseInt(value.toString());
				v = fmap.get(column)[sv.intValue()];
			}
			Method m[] = ((TableItem) tableitem).getData().getClass().getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				// --------------------获得方法的名字
				String mname=m[i].getName();
				if(!mname.startsWith("set") && !mname.startsWith("is")){
					continue;
				}
				String cname="";
				if(mname.startsWith("set")){
					cname=mname.substring(3);
				}else{
					cname=mname.substring(2);
				}
				String cv = cd.getCname();
				if(!cname.toLowerCase().equals(cv)){
					continue;
				}
				try {
					m[i].invoke(((TableItem) tableitem).getData(), v);
				} catch (Exception e1) {
					System.err.println(e1.getMessage());
				}
				break;
			}
		}
//		tv.refresh(false);
	}

	private int getNameIndex(String[] varray, String name) {
		for (int i = 0; i < varray.length; i++) {
			if (varray[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}
}