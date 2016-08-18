package com.bhc.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ListLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if (element == null) {
			return "";
		}
		return element.toString();
	}

	public Image getImage(Object element) {
		return null;
	}
}