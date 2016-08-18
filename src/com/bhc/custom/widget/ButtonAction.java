package com.bhc.custom.widget;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ButtonAction extends ControlContribution {
	private String name;
	private Button button;
	private Image image;

	public ButtonAction(String id) {
		super(id = id == null ? "1" : id);
	}

	protected Control createControl(Composite parent) {
		button = new Button(parent, SWT.NONE);
		button.setText(name);
		button.setImage(image);
		return button;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Button getButton() {
		return button;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
