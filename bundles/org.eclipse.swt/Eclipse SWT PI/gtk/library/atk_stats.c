/*******************************************************************************
 * Copyright (c) 2000, 2019 IBM Corporation and others. All rights reserved.
 * The contents of this file are made available under the terms
 * of the GNU Lesser General Public License (LGPL) Version 2.1 that
 * accompanies this distribution (lgpl-v21.txt).  The LGPL is also
 * available at http://www.gnu.org/licenses/lgpl.html.  If the version
 * of the LGPL at http://www.gnu.org is different to the version of
 * the LGPL accompanying this distribution and there is any conflict
 * between the two license versions, the terms of the LGPL accompanying
 * this distribution shall govern.
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/* Note: This file was auto-generated by org.eclipse.swt.tools.internal.JNIGenerator */
/* DO NOT EDIT - your changes will be lost. */

#include "swt.h"
#include "atk_stats.h"

#ifdef NATIVE_STATS

char * ATK_nativeFunctionNames[] = {
	"ATK_1TYPE_1ACTION",
	"ATK_1TYPE_1COMPONENT",
	"ATK_1TYPE_1EDITABLE_1TEXT",
	"ATK_1TYPE_1HYPERTEXT",
	"ATK_1TYPE_1OBJECT_1FACTORY",
	"ATK_1TYPE_1SELECTION",
	"ATK_1TYPE_1TABLE",
	"ATK_1TYPE_1TEXT",
	"ATK_1TYPE_1VALUE",
	"AtkAttribute_1sizeof",
	"AtkTextRange_1sizeof",
	"AtkTextRectangle_1sizeof",
	"_1ATK_1ACTION_1GET_1IFACE",
	"_1ATK_1COMPONENT_1GET_1IFACE",
	"_1ATK_1EDITABLE_1TEXT_1GET_1IFACE",
	"_1ATK_1HYPERTEXT_1GET_1IFACE",
	"_1ATK_1OBJECT_1FACTORY_1CLASS",
	"_1ATK_1SELECTION_1GET_1IFACE",
	"_1ATK_1TABLE_1GET_1IFACE",
	"_1ATK_1TEXT_1GET_1IFACE",
	"_1ATK_1VALUE_1GET_1IFACE",
	"_1atk_1object_1add_1relationship",
	"_1atk_1object_1notify_1state_1change",
	"_1atk_1object_1remove_1relationship",
	"_1atk_1state_1set_1add_1state",
	"_1atk_1text_1attribute_1get_1name",
	"_1atk_1text_1attribute_1get_1value",
	"_1call__JJ",
	"_1call__JJJ",
	"_1call__JJJJ",
	"_1call__JJJJJJ",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkActionIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkAttribute_2I",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkComponentIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkEditableTextIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkHypertextIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkObjectClass_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkSelectionIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkTableIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkTextIface_2",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkTextRange_2I",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkTextRectangle_2I",
	"memmove__JLorg_eclipse_swt_internal_accessibility_gtk_AtkValueIface_2",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkActionIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkAttribute_2JI",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkComponentIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkEditableTextIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkHypertextIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkObjectClass_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkSelectionIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkTableIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkTextIface_2J",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkTextRange_2JI",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkTextRectangle_2JI",
	"memmove__Lorg_eclipse_swt_internal_accessibility_gtk_AtkValueIface_2J",
};
#define NATIVE_FUNCTION_COUNT sizeof(ATK_nativeFunctionNames) / sizeof(char*)
int ATK_nativeFunctionCount = NATIVE_FUNCTION_COUNT;
int ATK_nativeFunctionCallCount[NATIVE_FUNCTION_COUNT];

#define STATS_NATIVE(func) Java_org_eclipse_swt_tools_internal_NativeStats_##func

JNIEXPORT jint JNICALL STATS_NATIVE(ATK_1GetFunctionCount)
	(JNIEnv *env, jclass that)
{
	return ATK_nativeFunctionCount;
}

JNIEXPORT jstring JNICALL STATS_NATIVE(ATK_1GetFunctionName)
	(JNIEnv *env, jclass that, jint index)
{
	return (*env)->NewStringUTF(env, ATK_nativeFunctionNames[index]);
}

JNIEXPORT jint JNICALL STATS_NATIVE(ATK_1GetFunctionCallCount)
	(JNIEnv *env, jclass that, jint index)
{
	return ATK_nativeFunctionCallCount[index];
}

#endif
