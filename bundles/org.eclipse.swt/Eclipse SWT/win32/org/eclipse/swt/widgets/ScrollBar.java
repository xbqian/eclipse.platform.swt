package org.eclipse.swt.widgets;

/*
 * Copyright (c) 2000, 2002 IBM Corp.  All rights reserved.
 * This file is made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */

import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;

/**
 * Instances of this class are selectable user interface
 * objects that represent a range of positive, numeric values. 
 * <p>
 * At any given moment, a given scroll bar will have a 
 * single <em>selection</em> that is considered to be its
 * value, which is constrained to be within the range of
 * values the scroll bar represents (that is, between its
 * <em>minimum</em> and <em>maximum</em> values).
 * </p><p>
 * Typically, scroll bars will be made up of five areas:
 * <ol>
 * <li>an arrow button for decrementing the value</li>
 * <li>a page decrement area for decrementing the value by a larger amount</li>
 * <li>a <em>thumb</em> for modifying the value by mouse dragging</li>
 * <li>a page increment area for incrementing the value by a larger amount</li>
 * <li>an arrow button for incrementing the value</li>
 * </ol>
 * Based on their style, scroll bars are either <code>HORIZONTAL</code>
 * (which have a left facing button for decrementing the value and a
 * right facing button for incrementing it) or <code>VERTICAL</code>
 * (which have an upward facing button for decrementing the value
 * and a downward facing buttons for incrementing it).
 * </p><p>
 * On some platforms, the size of the scroll bar's thumb can be
 * varied relative to the magnitude of the range of values it
 * represents (that is, relative to the difference between its
 * maximum and minimum values). Typically, this is used to
 * indicate some proportional value such as the ratio of the
 * visible area of a document to the total amount of space that
 * it would take to display it. SWT supports setting the thumb
 * size even if the underlying platform does not, but in this
 * case the appearance of the scroll bar will not change.
 * </p><p>
 * Scroll bars are created by specifying either <code>H_SCROLL</code>,
 * <code>V_SCROLL</code> or both when creating a <code>Scrollable</code>.
 * They are accessed from the <code>Scrollable</code> using
 * <code>getHorizontalBar</code> and <code>getVerticalBar</code>.
 * </p><p>
 * Note: Scroll bars are not Controls.  On some platforms, scroll bars
 * that appear as part of some standard controls such as a text or list
 * have no operating system resources and are not children of the control.
 * For this reason, scroll bars are treated specially.  To create a control
 * that looks like a scroll bar but has operating system resources, use
 * <code>Slider</code>. 
 * </p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>HORIZONTAL, VERTICAL</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles HORIZONTAL and VERTICAL may be specified.
 * </p><p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 *
 * @see Slider
 * @see Scrollable
 * @see Scrollable#getHorizontalBar
 * @see Scrollable#getVerticalBar
 */

public class ScrollBar extends Widget {	
	Scrollable parent;
	int increment, pageIncrement;

/**
 * Constructs a new instance of this class given its parent
 * and a style value describing its behavior and appearance.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
 * </ul>
 *
 * @see SWT#HORIZONTAL
 * @see SWT#VERTICAL
 * @see Widget#checkSubclass
 * @see Widget#getStyle
 */
ScrollBar (Scrollable parent, int style) {
	super (parent, checkStyle (style));
	this.parent = parent;
	createWidget ();
}

/**
 * Adds the listener to the collection of listeners who will
 * be notified when the receiver's value changes, by sending
 * it one of the messages defined in the <code>SelectionListener</code>
 * interface.
 * <p>
 * When <code>widgetSelected</code> is called, the event object detail field contains one of the following values:
 * <code>0</code> - for the end of a drag.
 * <code>SWT.DRAG</code>.
 * <code>SWT.HOME</code>.
 * <code>SWT.END</code>.
 * <code>SWT.ARROW_DOWN</code>.
 * <code>SWT.ARROW_UP</code>.
 * <code>SWT.PAGE_DOWN</code>.
 * <code>SWT.PAGE_UP</code>.
 * <code>widgetDefaultSelected</code> is not called.
 * </p>
 *
 * @param listener the listener which should be notified
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #removeSelectionListener
 * @see SelectionEvent
 */
public void addSelectionListener (SelectionListener listener) {
	checkWidget();
	if (listener == null) error (SWT.ERROR_NULL_ARGUMENT);
	TypedListener typedListener = new TypedListener(listener);
	addListener (SWT.Selection,typedListener);
	addListener (SWT.DefaultSelection,typedListener);
}

static int checkStyle (int style) {
	return checkBits (style, SWT.HORIZONTAL, SWT.VERTICAL, 0, 0, 0, 0);
}

void createWidget () {
	increment = 1;
	pageIncrement = 10;
	/*
	* Do not set the intial values of the maximum
	* or the thumb.  These values normally default
	* to 100 and 10 but may have been set already
	* by the widget that owns the scroll bar.  For
	* example, a scroll bar that is created for a
	* list widget, setting these defaults would
	* override the initial values provided by the
	* list widget.
	*/
}

public void dispose () {
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	super.dispose ();
	if (OS.IsWinCE) {
		SCROLLINFO info = new SCROLLINFO ();
		info.cbSize = SCROLLINFO.sizeof;
		info.fMask = OS.SIF_RANGE | OS.SIF_PAGE;
		info.nPage = 101;
		info.nMax = 100;
		info.nMin = 0;
		OS.SetScrollInfo (hwnd, type, info, true);
	} else {
		OS.ShowScrollBar (hwnd, type, false);
	}
}

/*
* Not currently used.
*/
Rectangle getBounds () {
	RECT rect = new RECT ();
	OS.GetClientRect (parent.handle, rect);
	int x = 0, y = 0, width, height;
	if ((style & SWT.HORIZONTAL) != 0) {
		y = rect.bottom - rect.top;
		width = rect.right - rect.left;
		height = OS.GetSystemMetrics (OS.SM_CYHSCROLL);
	} else {
		x = rect.right - rect.left;
		width = OS.GetSystemMetrics (OS.SM_CXVSCROLL);
		height = rect.bottom - rect.top;
	}
	return new Rectangle (x, y, width, height);
}

public Display getDisplay () {
	Scrollable parent = this.parent;
	if (parent == null) error (SWT.ERROR_WIDGET_DISPOSED);
	return parent.getDisplay ();
}

/**
 * Returns <code>true</code> if the receiver is enabled, and
 * <code>false</code> otherwise. A disabled control is typically
 * not selectable from the user interface and draws with an
 * inactive or "grayed" look.
 *
 * @return the receiver's enabled state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @see #isEnabled
 */
public boolean getEnabled () {
	checkWidget();
	return (state & DISABLED) == 0;
}

/**
 * Returns the amount that the receiver's value will be
 * modified by when the up/down (or right/left) arrows
 * are pressed.
 *
 * @return the increment
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getIncrement () {
	checkWidget();
	return increment;
}

/**
 * Returns the maximum value which the receiver will allow.
 *
 * @return the maximum
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getMaximum () {
	checkWidget();
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	info.fMask = OS.SIF_RANGE;
	int hwnd = hwndScrollBar ();
	int type = scrollBarType ();
	OS.GetScrollInfo (hwnd, type, info);
	return info.nMax;
}

/**
 * Returns the minimum value which the receiver will allow.
 *
 * @return the minimum
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getMinimum () {
	checkWidget();
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	info.fMask = OS.SIF_RANGE;
	int hwnd = hwndScrollBar ();
	int type = scrollBarType ();
	OS.GetScrollInfo (hwnd, type, info);
	return info.nMin;
}

/**
 * Returns the amount that the receiver's value will be
 * modified by when the page increment/decrement areas
 * are selected.
 *
 * @return the page increment
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getPageIncrement () {
	checkWidget();
	return pageIncrement;
}

/**
 * Returns the receiver's parent, which must be scrollable.
 *
 * @return the receiver's parent
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Scrollable getParent () {
	checkWidget();
	return parent;
}

/**
 * Returns the single <em>selection</em> that is the receiver's value.
 *
 * @return the selection
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getSelection () {
	checkWidget();
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	info.fMask = OS.SIF_POS;
	int hwnd = hwndScrollBar ();
	int type = scrollBarType ();
	OS.GetScrollInfo (hwnd, type, info);
	return info.nPos;
}

/**
 * Returns a point describing the receiver's size. The
 * x coordinate of the result is the width of the receiver.
 * The y coordinate of the result is the height of the
 * receiver.
 *
 * @return the receiver's size
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Point getSize () {
	checkWidget();
	RECT rect = new RECT ();
	OS.GetClientRect (parent.handle, rect);
	int width, height;
	if ((style & SWT.HORIZONTAL) != 0) {
		width = rect.right - rect.left;
		height = OS.GetSystemMetrics (OS.SM_CYHSCROLL);
	} else {
		width = OS.GetSystemMetrics (OS.SM_CXVSCROLL);
		height = rect.bottom - rect.top;
	}
	return new Point (width, height);
}

/**
 * Answers the size of the receiver's thumb relative to the
 * difference between its maximum and minimum values.
 *
 * @return the thumb value
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see ScrollBar
 */
public int getThumb () {
	checkWidget();
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	info.fMask = OS.SIF_PAGE;
	int hwnd = hwndScrollBar ();
	int type = scrollBarType ();
	OS.GetScrollInfo (hwnd, type, info);
	if (info.nPage != 0) --info.nPage;
	return info.nPage;
}

/**
 * Returns <code>true</code> if the receiver is visible, and
 * <code>false</code> otherwise.
 * <p>
 * If one of the receiver's ancestors is not visible or some
 * other condition makes the receiver not visible, this method
 * may still indicate that it is considered visible even though
 * it may not actually be showing.
 * </p>
 *
 * @return the receiver's visibility state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public boolean getVisible () {
	checkWidget();
	return (state & HIDDEN) == 0;
}

int hwndScrollBar () {
	return parent.handle;
}

/**
 * Returns <code>true</code> if the receiver is enabled and all
 * of the receiver's ancestors are enabled, and <code>false</code>
 * otherwise. A disabled control is typically not selectable from the
 * user interface and draws with an inactive or "grayed" look.
 *
 * @return the receiver's enabled state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @see #getEnabled
 */
public boolean isEnabled () {
	checkWidget();
	return getEnabled () && parent.isEnabled ();
}

/**
 * Returns <code>true</code> if the receiver is visible and all
 * of the receiver's ancestors are visible and <code>false</code>
 * otherwise.
 *
 * @return the receiver's visibility state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see #getVisible
 */
public boolean isVisible () {
	checkWidget();
	return getVisible () && parent.isVisible ();
}

void releaseChild () {
	super.releaseChild ();
	if (parent.horizontalBar == this) parent.horizontalBar = null;
	if (parent.verticalBar == this) parent.verticalBar = null;
}

void releaseWidget () {
	super.releaseWidget ();
	parent = null;
}

/**
 * Removes the listener from the collection of listeners who will
 * be notified when the receiver's value changes.
 *
 * @param listener the listener which should no longer be notified
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #addSelectionListener
 */
public void removeSelectionListener (SelectionListener listener) {
	checkWidget();
	if (listener == null) error (SWT.ERROR_NULL_ARGUMENT);
	if (eventTable == null) return;
	eventTable.unhook (SWT.Selection, listener);
	eventTable.unhook (SWT.DefaultSelection,listener);	
}

int scrollBarType () {
	if ((style & SWT.VERTICAL) != 0) return OS.SB_VERT;
	/*
	* This line is intentionally commented.  There should
	* only ever be HORIZONTAL and VERTICAL scroll bars.
	* The commented code reminds us that this is the case
	* and that the default style is HORIZONTAL.
	*/	
//	if ((style & SWT.HORIZONTAL) != 0) return OS.SB_HORZ;
	return OS.SB_HORZ;
}

/**
 * Enables the receiver if the argument is <code>true</code>,
 * and disables it otherwise. A disabled control is typically
 * not selectable from the user interface and draws with an
 * inactive or "grayed" look.
 *
 * @param enabled the new enabled state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setEnabled (boolean enabled) {
	checkWidget();
	/*
	* This line is intentionally commented.  Currently
	* always show scrollbar as being enabled and visible.
	*/
//	if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
	if (!OS.IsWinCE) {
		int hwnd = hwndScrollBar (), type = scrollBarType ();
		int flags = enabled ? OS.ESB_ENABLE_BOTH : OS.ESB_DISABLE_BOTH;
		OS.EnableScrollBar (hwnd, type, flags);
		state &= ~DISABLED;
		if (!enabled) state |= DISABLED;
	}
}

/**
 * Sets the amount that the receiver's value will be
 * modified by when the up/down (or right/left) arrows
 * are pressed to the argument, which must be at least 
 * one.
 *
 * @param value the new increment (must be greater than zero)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setIncrement (int value) {
	checkWidget();
	if (value < 1) return;
	increment = value;
}

/**
 * Sets the maximum value which the receiver will allow
 * to be the argument which must be greater than or
 * equal to zero.
 *
 * @param value the new maximum (must be zero or greater)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setMaximum (int value) {
	checkWidget();
	if (value < 0) return;
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	info.fMask = OS.SIF_RANGE | OS.SIF_DISABLENOSCROLL;
	OS.GetScrollInfo (hwnd, type, info);
	if (value - info.nMin - info.nPage < 1) return;
	info.nMax = value;
	OS.SetScrollInfo (hwnd, type, info, (state & DISABLED) == 0);
	
	/*
	* Bug in Windows.  For some reason, when the widget
	* is a standard scroll bar, and SetScrollInfo () is
	* called with SIF_RANGE or SIF_PAGE, the widget is
	* incorrectly made visible so that the next time the
	* widget is resized (or another scroll bar operation
	* is performed), the scroll bar draws.  The fix is
	* to hide the scroll bar (again) when already hidden.
	*/
	if ((state & HIDDEN) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.ShowScrollBar (hwnd, type, false);
		}
	}
		
	/*
	* Feature in Windows.  Using SIF_DISABLENOSCROLL,
	* SetScrollInfo () can change enabled and disabled
	* state of the scroll bar causing a scroll bar that
	* was disabled by the application to become enabled.
	* The fix is to disable the scroll bar (again) when
	* the application has disabled the scroll bar.
	*/
	if ((state & DISABLED) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.EnableScrollBar (hwnd, type, OS.ESB_DISABLE_BOTH);
		}
	}
}

/**
 * Sets the minimum value which the receiver will allow
 * to be the argument which must be greater than or
 * equal to zero.
 *
 * @param value the new minimum (must be zero or greater)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setMinimum (int value) {
	checkWidget();
	if (value < 0) return;
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	info.fMask = OS.SIF_RANGE | OS.SIF_DISABLENOSCROLL;
	OS.GetScrollInfo (hwnd, type, info);
	if (info.nMax - value - info.nPage < 1) return;
	info.nMin = value;
	OS.SetScrollInfo (hwnd, type, info, true);
	
	/*
	* Bug in Windows.  For some reason, when the widget
	* is a standard scroll bar, and SetScrollInfo () is
	* called with SIF_RANGE or SIF_PAGE, the widget is
	* incorrectly made visible so that the next time the
	* widget is resized (or another scroll bar operation
	* is performed), the scroll bar draws.  The fix is
	* to hide the scroll bar (again) when already hidden.
	*/
	if ((state & HIDDEN) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.ShowScrollBar (hwnd, type, false);
		}
	}
		
	/*
	* Feature in Windows.  Using SIF_DISABLENOSCROLL,
	* SetScrollInfo () can change enabled and disabled
	* state of the scroll bar causing a scroll bar that
	* was disabled by the application to become enabled.
	* The fix is to disable the scroll bar (again) when
	* the application has disabled the scroll bar.
	*/
	if ((state & DISABLED) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.EnableScrollBar (hwnd, type, OS.ESB_DISABLE_BOTH);
		}
	}
}

/**
 * Sets the amount that the receiver's value will be
 * modified by when the page increment/decrement areas
 * are selected to the argument, which must be at least
 * one.
 *
 * @return the page increment (must be greater than zero)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setPageIncrement (int value) {
	checkWidget();
	if (value < 1) return;
	pageIncrement = value;
}

/**
 * Sets the single <em>selection</em> that is the receiver's
 * value to the argument which must be greater than or equal
 * to zero.
 *
 * @param value the new selection (must be zero or greater)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setSelection (int selection) {
	checkWidget();
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	info.fMask = OS.SIF_POS;
	info.nPos = selection;
	OS.SetScrollInfo (hwnd, type, info, true);
}

/**
 * Sets the size of the receiver's thumb relative to the
 * difference between its maximum and minimum values to the
 * argument which must be at least one.
 *
 * @param value the new thumb value (must be at least one)
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see ScrollBar
 */
public void setThumb (int value) {
	checkWidget();

	/* Position the thumb */
	if (value < 1) return;
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	info.fMask = OS.SIF_PAGE | OS.SIF_RANGE | OS.SIF_DISABLENOSCROLL;
	OS.GetScrollInfo (hwnd, type, info);
	if (info.nMax - info.nMin - value < 0) return;
	info.nPage = value;
	if (info.nPage != 0) info.nPage++;
	OS.SetScrollInfo (hwnd, type, info, true);
	
	/*
	* Bug in Windows.  For some reason, when the widget
	* is a standard scroll bar, and SetScrollInfo () is
	* called with SIF_RANGE or SIF_PAGE, the widget is
	* incorrectly made visible so that the next time the
	* widget is resized (or another scroll bar operation
	* is performed), the scroll bar draws.  The fix is
	* to hide the scroll bar (again) when already hidden.
	*/
	if ((state & HIDDEN) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.ShowScrollBar (hwnd, type, false);
		}
	}
		
	/*
	* Feature in Windows.  Using SIF_DISABLENOSCROLL,
	* SetScrollInfo () can change enabled and disabled
	* state of the scroll bar causing a scroll bar that
	* was disabled by the application to become enabled.
	* The fix is to disable the scroll bar (again) when
	* the application has disabled the scroll bar.
	*/
	if ((state & DISABLED) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.EnableScrollBar (hwnd, type, OS.ESB_DISABLE_BOTH);
		}
	}
}

/**
 * Sets the receiver's selection, minimum value, maximum
 * value, thumb, increment and page increment all at once.
 * <p>
 * Note: This is equivalent to setting the values individually
 * using the appropriate methods, but may be implemented in a 
 * more efficient fashion on some platforms.
 * </p>
 *
 * @param selection the new selection value
 * @param minimum the new minimum value
 * @param maximum the new maximum value
 * @param thumb the new thumb value
 * @param increment the new increment value
 * @param pageIncrement the new pageIncrement value
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setValues (int selection, int minimum, int maximum, int thumb, int increment, int pageIncrement) {
	checkWidget();
	if (selection < 0) return;
	if (minimum < 0) return;
	if (maximum < 0) return;
	if (thumb < 1) return;
	if (maximum - minimum - thumb < 0) return;
	if (increment < 1) return;
	if (pageIncrement < 1) return;
	this.increment = increment;	
	this.pageIncrement = pageIncrement;
	SCROLLINFO info = new SCROLLINFO ();
	info.cbSize = SCROLLINFO.sizeof;
	info.fMask = OS.SIF_POS | OS.SIF_PAGE | OS.SIF_RANGE | OS.SIF_DISABLENOSCROLL;
	info.nPos = selection;
	info.nMin = minimum;
	info.nMax = maximum;
	info.nPage = thumb;
	if (info.nPage != 0) info.nPage++;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	OS.SetScrollInfo (hwnd, type, info, true);
		
	/*
	* Bug in Windows.  For some reason, when the widget
	* is a standard scroll bar, and SetScrollInfo () is
	* called with SIF_RANGE or SIF_PAGE, the widget is
	* incorrectly made visible so that the next time the
	* widget is resized (or another scroll bar operation
	* is performed), the scroll bar draws.  The fix is
	* to hide the scroll bar (again) when already hidden.
	*/
	if ((state & HIDDEN) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.ShowScrollBar (hwnd, type, false);
		}
	}
		
	/*
	* Feature in Windows.  Using SIF_DISABLENOSCROLL,
	* SetScrollInfo () can change enabled and disabled
	* state of the scroll bar causing a scroll bar that
	* was disabled by the application to become enabled.
	* The fix is to disable the scroll bar (again) when
	* the application has disabled the scroll bar.
	*/
	if ((state & DISABLED) != 0) {
		/*
		* This line is intentionally commented.  Currently
		* always show scrollbar as being enabled and visible.
		*/
//		if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		if (!OS.IsWinCE) {
			OS.EnableScrollBar (hwnd, type, OS.ESB_DISABLE_BOTH);
		}
	}
}

/**
 * Marks the receiver as visible if the argument is <code>true</code>,
 * and marks it invisible otherwise. 
 * <p>
 * If one of the receiver's ancestors is not visible or some
 * other condition makes the receiver not visible, marking
 * it visible may not actually cause it to be displayed.
 * </p>
 *
 * @param visible the new visibility state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setVisible (boolean visible) {
	checkWidget();
	if (OS.IsWinCE) {
		SCROLLINFO info = new SCROLLINFO ();
		info.cbSize = SCROLLINFO.sizeof;
		int hwnd = hwndScrollBar (), type = scrollBarType ();
		info.fMask = OS.SIF_RANGE | OS.SIF_PAGE;
		if (visible) info.fMask |= OS.SIF_DISABLENOSCROLL;
		OS.GetScrollInfo (hwnd, type, info);
		if (info.nPage == info.nMax - info.nMin + 1) {
			/*
			* Bug in Windows.  When the only changed flag to
			* SetScrollInfo () is OS.SIF_DISABLENOSCROLL, 
			* Windows does not update the scroll bar state.
			* The fix is to increase and then decrease the
			* maximum, causing Windows to honour the flag.
			*/  
			int max = info.nMax;
			info.nMax++;
			OS.SetScrollInfo (hwnd, type, info, false);
			info.nMax = max;
			OS.SetScrollInfo (hwnd, type, info, true);
		} else {
        	/*
        	* This line is intentionally commented.  Currently
        	* always show scrollbar as being enabled and visible.
        	*/
//			if (OS.IsWinCE) error (SWT.ERROR_NOT_IMPLEMENTED);
		}
		return;
	}
	
	/*
	* Set the state bits before calling ShowScrollBar ()
	* because hiding and showing the scroll bar can cause
	* WM_SIZE messages when the client area is resized.
	* Setting the state before the call means that code
	* that runs during WM_SIZE that queries the visibility
	* of the scroll bar will get the correct value.
	*/
	state &= ~HIDDEN;
	if (!visible) state |= HIDDEN;
	int hwnd = hwndScrollBar (), type = scrollBarType ();
	if (OS.ShowScrollBar (hwnd, type, visible)) {
		/*
		* Bug in Windows.  For some reason, when the widget
		* is a standard scroll bar, and SetScrollInfo () is
		* called with SIF_RANGE or SIF_PAGE while the widget
		* is not visible, the widget is incorrectly disabled
		* even though the values for SIF_RANGE and SIF_PAGE,
		* when set for a visible scroll bar would not disable
		* the scroll bar.  The fix is to enable the scroll bar
		* when not disabled by the application and the current
		* scroll bar ranges would cause the scroll bar to be
		* enabled had they been set when the scroll bar was
		* visible.
		*/
		if ((state & DISABLED) == 0) {
			SCROLLINFO info = new SCROLLINFO ();
			info.cbSize = SCROLLINFO.sizeof;
			info.fMask = OS.SIF_RANGE | OS.SIF_PAGE;
			OS.GetScrollInfo (hwnd, type, info);
			if (info.nMax - info.nMin - info.nPage >= 0) {
				OS.EnableScrollBar (hwnd, type, OS.ESB_ENABLE_BOTH);
			}
		}
		sendEvent (visible ? SWT.Show : SWT.Hide);
		// widget could be disposed at this point
	}
}

LRESULT wmScrollChild (int wParam, int lParam) {

	/* Do nothing when scrolling is ending */
	int code = wParam & 0xFFFF;
	if (code == OS.SB_ENDSCROLL) return null;

	/*
	* Send the event because WM_HSCROLL and
	* WM_VSCROLL are sent from a modal message
	* loop in Windows that is active when the
	* user is scrolling.
	*/
	Event event = new Event ();
	switch (code) {
		/*
		* This line is intentionally commented.  Do not set the detail
		* field to DRAG to indicate that the dragging has ended when the
		* scroll bar is finally positioned in SB_THUMBPOSITION.
		*/
//		case OS.SB_THUMBPOSITION:	break;
		case OS.SB_THUMBTRACK:		event.detail = SWT.DRAG;  break;
		case OS.SB_TOP: 			event.detail = SWT.HOME;  break;
		case OS.SB_BOTTOM:			event.detail = SWT.END;  break;
		case OS.SB_LINEDOWN:		event.detail = SWT.ARROW_DOWN;  break;
		case OS.SB_LINEUP: 		event.detail = SWT.ARROW_UP;  break;
		case OS.SB_PAGEDOWN:		event.detail = SWT.PAGE_DOWN;  break;
		case OS.SB_PAGEUP:			event.detail = SWT.PAGE_UP;  break;
	}
	sendEvent (SWT.Selection, event);
	// the widget could be destroyed at this point
	return null;
}

}
