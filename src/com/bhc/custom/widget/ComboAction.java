package com.bhc.custom.widget;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ComboAction extends ControlContribution {

	private String[] items;
	private Combo combo;

	public ComboAction(String id) {
		super(id);
	}

	@Override
	protected Control createControl(Composite composite) {
		combo = new Combo(composite, SWT.BORDER);
		FormData fdComboURL = new FormData();
		fdComboURL.right = new FormAttachment(40);
		combo.setLayoutData(fdComboURL);
		combo.setItems(items);
		return combo;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public Combo getCombo() {
		return combo;
	}

}