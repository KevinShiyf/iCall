package com.icall.free.entity;

import android.util.Log;

import com.icall.free.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class ContactsEntity implements Comparable<ContactsEntity>, Serializable {
	private static final long serialVersionUID = 854808022154008732L;
	private String displayName;
	private String photoId;
	private String constactId;
	private String mark;
	private boolean isGroup;
	private String sortKey;
	private final String TAG = this.getClass().getName();
	private String firstLetter;
	private String pinyin;	
	
	private String numberPinyin;		//名字拼音对应的数字
	private String numberShortPinyin;			//名字缩写拼音对应的数字

	private ArrayList<PhoneNumEntity> phoneNums;
	
	private int searchType;
	private String keyInput = "";
	private int matchIndex;
	
	public String getFirstLetter() {
		if (firstLetter == null) 
			return "";
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public void setNumberPinyin(String numberPinyin){
		this.numberPinyin=numberPinyin;
	}
	
	public String getNumberPinyin(){
		if (numberPinyin == null) 
			return "";
		return this.numberPinyin;
	}
	
	public void setNumberShortPinyin(String numberShortPinyin){
		this.numberShortPinyin=numberShortPinyin;
	}
	
	public String getNumberShortPinyin(){
		if (numberShortPinyin == null) 
			return "";
		return this.numberShortPinyin;
	}

	public ContactsEntity(){
		phoneNums = new ArrayList<PhoneNumEntity>(0);
	}
	
	public void addPhoneNum(PhoneNumEntity phoneNum) {
		this.phoneNums.add(phoneNum);
	}

	public ArrayList<PhoneNumEntity> getPhoneNums() {
		return phoneNums;
	}

	// 名字第一个字母
	public String getFirstLetterFromSortKey() {
		if(StringUtil.isNull(firstLetter)){
			return "#";
		}
		String key = firstLetter.substring(0, 1).toUpperCase(Locale.US);
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
	}
	
	//名字拼音
	public String getSpellingName(){
		return StringUtil.getSpelling(sortKey);
	}
	
	//名字中文
	public String getChineseName(){
		return StringUtil.getChinese(sortKey);
	}
	
	
	public String getConstactId() {
		return constactId;
	}
	
	public void setConstactId(String constactId) {
		this.constactId = constactId;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	
	@Override
	public int compareTo(ContactsEntity another) {
		int result = 0;
		try {
			result = numberPinyin.compareTo(another.numberPinyin);
		} catch (Exception e) {
			Log.e(TAG,"error, sort ContactsModel . ");
			e.printStackTrace();
		}
		return result;
	}

	public String getDisplayName() {
		if (displayName == null)
			return "";
		return displayName;
	}

	public void setName(String name) {
		this.displayName = name;
	}

	public String getPhoneNum() {
		if (phoneNums != null && phoneNums.size() > 0) {
			return phoneNums.get(0).phoneNum;
		}
		return "";
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	
	public void setKeyInput(String input) {
		keyInput = input;
	}

	public String getKeyInput() {
		if (keyInput == null) {
			return "";
		}
		return keyInput;
	}
	
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPinyin() {
		if (pinyin == null) {
			return "";
		}
		return pinyin;
	}
	
	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	
	public int getMatchIndex() {
		return matchIndex;
	}

	public void setMatchIndex(int mMatchIndex) {
		this.matchIndex = mMatchIndex;
	}
}
