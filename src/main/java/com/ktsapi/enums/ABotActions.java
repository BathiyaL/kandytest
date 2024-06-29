package com.ktsapi.enums;

public enum ABotActions {
	Type,
	Check,
	UnCheck,
	FindElement,
	EnterText,
	NativeClick,
	Clear,
	Click,
	ToCombobox,
	ToFrameElement,
	Submit,
	SendKeys,
	GetTagName,
	GetText,
	GetAttribute,
	IsSelected,
	IsEnabled,
	IsDisplayed,
	GetLocation,
	GetRect,
	GetSize,
	GoTo,
	GetNewWindow,
	GetNewTab,
	SelectOption,
	SelectValue,
	SelectIndex,
	DeselectOption,
	DeselectAll,
	GetAllSelectedOptions,
	GetAllSelectedOptionsText,
	GetFirstSelectedOption,
	GetFirstSelectedOptionText,
	IsMultipleSelectElement,
	OpenBrowser,
	CloseBrowser,
	CloseBrowserTab,
	WaitUntil,
	GetTitle,
	HandleBrowserWindow,
	DragAndDropTo,
	GetElementCount,
	
	ManageTimeOuts_setImplicitlyWaitTime,
	ManageTimeOuts_setScriptTimeout,
	ManageTimeOuts_setPageLoadTimeout,
	
	Alert_dismiss,
	Alert_accept,
	Alert_getText,
	Alert_sendKeys,
	
	
	// custom element types actions
	ToComboBox_selectByVisibleText,
	ToComboBox_selectByIndex,
	ToComboBox_isMultiple,
	ToComboBox_selectByValue,
	ToComboBox_deselectAll,
	ToComboBox_deselectByValue,
	ToComboBox_deselectByIndex,
	ToComboBox_deselectByVisibleText,
	ToComboBox_getFirstSelectedOption,
	ToComboBox_getAllSelectedOptions,
	ToComboBox_getOptions,
	
	ToFrameElement_switchToFrame,
	ToFrameElement_parentFrame,
	ToFrameElement_defaultContent,
	
	BrowserNavigate_back,
	BrowserNavigate_forward,
	BrowserNavigate_to,
	BrowserNavigate_refresh,
	
	SwitchTo_frame,
	SwitchTo_parentFrame,
	SwitchTo_window,
	SwitchTo_defaultContent,
	
	SwitchToWindowOrTab,
	//Common Action
	Pause,
	Print,
	BaseUrl,
	
	// Rest API actions
	GET,
	POSt

}
