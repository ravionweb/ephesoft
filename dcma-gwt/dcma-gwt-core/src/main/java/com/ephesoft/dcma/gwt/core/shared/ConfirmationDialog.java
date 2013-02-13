/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.gwt.core.shared;

import com.ephesoft.dcma.gwt.core.client.i18n.LocaleCommonConstants;
import com.ephesoft.dcma.gwt.core.client.i18n.LocaleDictionary;
import com.ephesoft.dcma.gwt.core.shared.listener.ThirdButtonListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmationDialog extends DialogBox {

	private static final String KEYDOWN = "keydown";

	interface Binder extends UiBinder<Widget, ConfirmationDialog> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	public interface DialogListener {

		void onOkClick();

		void onCancelClick();
	}

	@UiField
	public Button okButton;

	@UiField
	public Button cancelButton;

	@UiField
	public Button thirdButton;

	@UiField
	protected HTML centerWidget;

	@UiField
	protected HTMLPanel panel;

	private DialogListener listener;

	private boolean performCancelOnEscape;

	public boolean isPerformCancelOnEscape() {
		return performCancelOnEscape;
	}

	public void setPerformCancelOnEscape(boolean performCancelOnEscape) {
		this.performCancelOnEscape = performCancelOnEscape;
	}

	public ConfirmationDialog(boolean thirdButtonVisibility) {
		super();
		setWidget(BINDER.createAndBindUi(this));
		okButton.setText(LocaleDictionary.get().getConstantValue(LocaleCommonConstants.TITLE_CONFIRMATION_OK));
		cancelButton.setText(LocaleDictionary.get().getConstantValue(LocaleCommonConstants.TITLE_CONFIRMATION_CANCEL));
		thirdButton.setText(LocaleDictionary.get().getConstantValue(LocaleCommonConstants.TITLE_CONFIRMATION_DISCARD));
		thirdButton.setVisible(thirdButtonVisibility);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setWidth("100%");
		setDefaultListener();
	}

	public final void setDefaultListener() {
		addDialogListener(new DialogListener() {

			@Override
			public void onOkClick() {
				hideConfirmationDialog();
			}

			@Override
			public void onCancelClick() {
				hideConfirmationDialog();
			}
		});
	}

	public ConfirmationDialog() {
		this(false);
	}

	private void hideConfirmationDialog() {
		this.hide();
	}

	public void setMessage(String message) {
		centerWidget.setHTML(message);
	}

	public void setDialogTitle(String text) {
		setText(text);
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent preview) {
		super.onPreviewNativeEvent(preview);

		// Use the popup's key preview hooks to close the dialog when either
		// enter or escape is pressed.
		NativeEvent evt = preview.getNativeEvent();
		if (evt.getType().equals(KEYDOWN) && evt.getKeyCode() == KeyCodes.KEY_ESCAPE && performCancelOnEscape && listener != null
				&& cancelButton.isVisible()) {
			listener.onCancelClick();
			hide();
		}

	}
	

	@UiHandler("okButton")
	protected void onOk(ClickEvent event) {
		listener.onOkClick();
		hide();
	}

	@UiHandler("cancelButton")
	protected void onCancel(ClickEvent event) {
		listener.onCancelClick();
		hide();
	}

	@UiHandler("thirdButton")
	protected void onDiscard(ClickEvent event) {
		if (listener instanceof ThirdButtonListener) {
			((ThirdButtonListener) listener).onThirdButtonClick();
			hide();
		}
	}

	public final void addDialogListener(DialogListener dialogListener) {
		this.listener = dialogListener;
	}

	public HTMLPanel getPanel() {
		return panel;
	}

}
