package com.icall.free.event;

/**
 * 
 * @author wanghao 2015-4-19 消息类型
 *
 */
public enum MessageType {

	COMPLETE_LOAD_CALLLOGS, // 完成加载通话记录

	OPEN_LEFT_DRAWER,


	HIDE_TAB_MSG, // 隐藏标签
	SHOW_TAB_MSG, // 显示标签

	CLOSE_ALL_ACTIVITY_MSG, // 关闭所有Activity
	CLOSE_LOGIN_REGISTER_ACTIVITY_MSG, // 关闭注册登录等相关Activity
	
	CLOSE_WALLETRECHARGE_ACTIVITY,
	CLOSE_INTERNAL_FLOW_ACTIVITY,
	CLOSE_VOICE_ACCOUNT_ACTIVITY,
	
	GET_USER_INFORMATION_SUCCESS, // 获取用户信息成功
	GET_USER_INFORMATION_FAILED, // 获取用户信息失败



	SCROOL_HIDE_DIAL_MESSAGE, // 滚动隐藏拨号盘

	DIAL_NUMBER, // 按键拨号

	MAIN_ACTIVITY_HANDLE,
	DIAL_PASTE, // 粘贴
//	LOGIN_RESULT_SUCCESS, // 判断登录成功
//	LOGIN_RESULT_FAILED, // 判断登录失败

	MODIFY_USER_INFOR_SUCCESS, // 修改个人信息成功
	MODIFY_USER_INFOR_FAILED, // 修改个人信息失败

	SHOW_TAB_ME_NEW, // tab me 显示有更新
	DIAL_INPUT_NULL, // 拨号盘输入内容为空
	DIAL_INPUT_TEXT, // 拨号盘有输入内容

	DIAL_KEYBOARD_STATE,
	
	UPDATE_VERSION_PROMPT,
	PUSH_MESSAGE_RECEIVER, // 推送消息到服务
	MAIN_RECH_FLOW, // 同页面跳转充流量

	GET_ACHIEVEMENT_SUCCESS,
	
	GET_WITHDRAW_ACCOUT_SUCCESS,
	
	GET_MY_REFEREE_SUCCESS,
	
	GET_AUDIO_ACCOUT_SUCCESS,
	
	GET_WITHDRAW_WALLET_SUCCESS,
	
	OPERATE_ACCOUNT_BALANCE_SUCCESS_RESULT, // 处理账户钱包成功结果
	SHOW_VOICE_MEAL_DUE, // 显示套餐到期提醒
	DISS_VOICE_MEAL_DUE,// 隐藏套餐到期提醒
	UPADTE_GLOBAL_VARS,		//改变从服务获取的全局参数
	CHANGE_DELETE_CALLLOGS_COUNTS,		//改变删除通话记录的条数
	LOCAL_CONTACTS_CHANGE;			//本地通讯录发生变化
}
