package com.icall.free.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Data;
import android.text.TextUtils;

import com.icall.free.entity.ContactsEntity;
import com.icall.free.entity.PhoneNumEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ContactsUtil {

	public static ArrayList<ContactsEntity> mContacts = new ArrayList<ContactsEntity>(0);
	public static ArrayList<ContactsEntity> mSearchContacts = new ArrayList<ContactsEntity>();

	private static Cursor getAllContacts(Context context) {
		return getSingleContacts(context, null);
	}

	private static Cursor getSingleContacts(Context context, String contactsId) {
		String ver = "";
		Uri uri = Uri.parse("content://com.android.contacts/data/phones");
		String selection = "";
		String[] selectionArgs = null;
		String[] projection = null;
		if (!TextUtils.isEmpty(contactsId)) {
			selection = "contact_id" + " = ?";
			selectionArgs = new String[] { contactsId };
		}
		if (android.os.Build.VERSION.SDK_INT >= 8) {
			ver = " sort_key COLLATE LOCALIZED asc";
			projection = new String[] { "_id", "display_name", "data1", "sort_key", "contact_id", "photo_id" };
		} else {
			ver = " display_name COLLATE LOCALIZED asc";
			projection = new String[] { "_id", "display_name", "data1", "contact_id", "photo_id" };
		}
		Cursor cur = null;
		try {
			ContentResolver contentresolver = context.getContentResolver();
			cur = contentresolver.query(uri, projection, selection, selectionArgs, ver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cur;
	}

	public static ContactsEntity getSingleContactsEntity(Context context, String contactsId) {
		Cursor cursor = getSingleContacts(context, contactsId);
		ContactsEntity model = new ContactsEntity();
		while (cursor != null && cursor.moveToNext()) {
			String contactId = cursor.getString(cursor.getColumnIndex("contact_id"));
			String displayName = cursor.getString(cursor.getColumnIndex("display_name"));

			PhoneNumEntity PhoneNumEntity = new PhoneNumEntity();
			PhoneNumEntity.location = "";
			PhoneNumEntity.name = displayName;
			PhoneNumEntity.phoneNum = cursor.getString(cursor.getColumnIndexOrThrow("data1"));

			model.addPhoneNum(PhoneNumEntity);

//			model.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
			model.setGroup(false);

			model.setName(displayName);

			model.setPhotoId(cursor.getString(cursor.getColumnIndex("photo_id")));
			model.setSortKey(cursor.getString(cursor.getColumnIndex("sort_key")));
			model.setConstactId(contactId);
			model.setFirstLetter(model.getFirstLetterFromSortKey());
		}
		DBUtil.closeCursor(cursor);
		return model;
	}

	/**
	 * 整合联系人
	 */
	private static ArrayList<ContactsEntity> getContacts(Context context) {
		ArrayList<ContactsEntity> contacts = new ArrayList<ContactsEntity>();
		Cursor cursor = getAllContacts(context);

		HashMap<String, ContactsEntity> map = new HashMap<String, ContactsEntity>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String contactId = cursor.getString(cursor.getColumnIndex("contact_id"));
				String displayName = cursor.getString(cursor.getColumnIndex("display_name"));
				String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("data1"));
				int dbId = cursor.getInt(cursor.getColumnIndex("_id"));
				String photo_id = cursor.getString(cursor.getColumnIndex("photo_id"));
				String sort_key = cursor.getString(cursor.getColumnIndex("sort_key"));
				if (!TextUtils.isEmpty(phoneNumber)) {
					phoneNumber = phoneNumber.replace(" ", "").replace("-", "");
				}

				ContactsEntity model = getModel(contactId, map);
//				model.set_id(dbId);
				model.setGroup(false);
				model.setName(displayName);
				model.setPhotoId(photo_id);
				model.setSortKey(sort_key);
				model.setConstactId(contactId);
				PhoneNumEntity PhoneNumEntity = new PhoneNumEntity();
				PhoneNumEntity.location = "";
				PhoneNumEntity.name = displayName;
				PhoneNumEntity.phoneNum = phoneNumber;
				boolean isSame = false;
				if (model.getPhoneNums().size() > 0) {
					for (int i = 0; i < model.getPhoneNums().size(); i++) {
						String lastPhoneNum = model.getPhoneNums().get(i).phoneNum;
						if (!TextUtils.isEmpty(lastPhoneNum) && lastPhoneNum.equals(phoneNumber)) {
							isSame = true;
							break;
						}
					}
				}
				if (isSame) {
					continue;
				} else {
					model.addPhoneNum(PhoneNumEntity);
				}
				if (!TextUtils.isEmpty(displayName)) {
					String[] str = ConverToPingying.getInstance().converToPingYingAndNumber(displayName);
					model.setFirstLetter(str[0]);
					model.setNumberShortPinyin(str[1]);
					model.setPinyin(str[2]);
					model.setNumberPinyin(str[3]);
				} else {
					model.setFirstLetter("#");
				}
			}
		}

		contacts.addAll(map.values());
		DBUtil.closeCursor(cursor);
		return contacts;
	}

	private static ContactsEntity getModel(String contactId, HashMap<String, ContactsEntity> map) {
		ContactsEntity model = map.get(contactId);
		if (null == model) {
			model = new ContactsEntity();
			map.put(contactId, model);
		}
		return model;
	}

	/**
	 * userful
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<ContactsEntity> getSortContacts(Context context) {
		ArrayList<ContactsEntity> contacts = getContacts(context);
		try {
			Collections.sort(contacts);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		TreeMap<String, ArrayList<ContactsEntity>> map = new TreeMap<String, ArrayList<ContactsEntity>>();
		// 分组
		for (int i = 0; i < contacts.size(); i++) {
			ContactsEntity model = contacts.get(i);
			String firstLetter = model.getFirstLetterFromSortKey();
			// String firstLetter = model.getPhoneBookLable();
			ArrayList<ContactsEntity> list = getList(firstLetter, map);
			list.add(model);
		}
		ArrayList<ContactsEntity> sortContacts = getSortContacts(map);
		return sortContacts;
	}

	private static ArrayList<ContactsEntity> getSortContacts(TreeMap<String, ArrayList<ContactsEntity>> map) {
		ArrayList<ContactsEntity> contacts = new ArrayList<ContactsEntity>();
		while (!map.isEmpty()) {
			Entry<String, ArrayList<ContactsEntity>> entry = map.pollFirstEntry();
//			ContactsEntity model = new ContactsEntity();
//			model.setGroup(true);
//			model.setName(entry.getKey());
//			contacts.add(model);
			contacts.addAll(entry.getValue());
		}
		return contacts;
	}

	private static ArrayList<ContactsEntity> getList(String key, TreeMap<String, ArrayList<ContactsEntity>> map) {
		key = key.toUpperCase(Locale.US);
		ArrayList<ContactsEntity> contacts = map.get(key);
		if (null == contacts) {
			contacts = new ArrayList<ContactsEntity>();
			map.put(key, contacts);
		}
		return contacts;
	}

	public static int dialSearchFriend(String input) {
		// Log.e(TAG,"input str:" + input);
		mSearchContacts.clear();
		int totalCount = mContacts.size();
		for (int i = 0; i < totalCount; i++) {
			ContactsEntity item = null;
			try {
				item = mContacts.get(i);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				continue;
			}
			if (item == null) {
				continue;
			}
			if (mSearchContacts.contains(item)) {
				continue;
			}
			item.setKeyInput(input);
			if (item.getNumberShortPinyin().contains(input)) {
				// 首字母包含
				item.setSearchType(1);
				item.setMatchIndex(item.getNumberShortPinyin().indexOf(input));
				mSearchContacts.add(item);
			} else if (item.getNumberPinyin().contains(input)) {
				// 拼音匹配，需要去韵母匹配
				String strLeftLetter = item.getPinyin();
				String strPinyinNumber = item.getNumberPinyin();
				int nMatchIndex = strPinyinNumber.indexOf(input);
				item.setMatchIndex(nMatchIndex);

				String inputLetter = strLeftLetter.substring(nMatchIndex, nMatchIndex + input.length());
				while (-1 != strLeftLetter.toLowerCase().indexOf(inputLetter.toLowerCase())) {
					String strTmp = strLeftLetter.substring(nMatchIndex);
					strTmp = strTmp.replace(strTmp.substring(1), strTmp.substring(1).toLowerCase());
					if (strTmp.contains(setFirstLetterUpper(inputLetter.toLowerCase()))) {
						item.setSearchType(2);
						mSearchContacts.add(item);
						break;
					}
					strLeftLetter = strLeftLetter.substring(nMatchIndex + 1);
					strPinyinNumber = strPinyinNumber.substring(nMatchIndex + 1);
					nMatchIndex = strPinyinNumber.indexOf(input);
					if (-1 != nMatchIndex) {
						inputLetter = strLeftLetter.substring(nMatchIndex, nMatchIndex + input.length());
					}
					item.setMatchIndex(nMatchIndex + 1);
				}
			} else {
				for (PhoneNumEntity pItem : item.getPhoneNums()) {
					if (!TextUtils.isEmpty(pItem.phoneNum) && pItem.phoneNum.contains(input)) {
						item.setSearchType(3);
						mSearchContacts.add(item);
						break;
					}
				}
			}
		}
		Collections.sort(mSearchContacts, new Comparator<ContactsEntity>() {
			@Override
			public int compare(ContactsEntity item1, ContactsEntity item2) {
				return item1.getSearchType() - item2.getSearchType();
			}
		});
		return totalCount;
	}

	/**
	 * 联系人搜索
	 * 
	 * @param input：用户输入的字符
	 * @return
	 * @author: kevin
	 * @data:2013-9-17 上午9:58:11
	 */
	public static ArrayList<ContactsEntity> searchAll(String input) {
		ArrayList<ContactsEntity> result = new ArrayList<ContactsEntity>();
		try {
			input = input.replace(" ", "");
			if (input.length() == 0) {
				return result;
			}
			// 匹配联系人
			for (ContactsEntity item : mContacts) {
				item.setKeyInput(input);
				if (item.getDisplayName().contains(input)) { // 中文名字
					item.setSearchType(4);
					result.add(item);
				} else if (item.getFirstLetter().toLowerCase().contains(input.toLowerCase())) {
					item.setSearchType(1);
					item.setMatchIndex(item.getFirstLetter().toLowerCase().indexOf(input.toLowerCase()));
					result.add(item);
				} else if (item.getPinyin().toLowerCase().contains(input.toLowerCase())) {
					// 过滤掉韵母查询结果,输入首字母非拼音首字母即认为是韵母
					String strFirstLetter = input.substring(0, 1);
					String strLeftLetter = item.getPinyin();
					int nMatchIndex = 0;
					while (-1 != strLeftLetter.toLowerCase().indexOf(input.toLowerCase())) {
						String strTmp = strLeftLetter;
						nMatchIndex = strTmp.toLowerCase().indexOf(input.toLowerCase());
						strTmp = strTmp.substring(nMatchIndex);
						strTmp = strTmp.replace(strTmp.substring(1), strTmp.substring(1).toLowerCase());
						// strLeftLetter = setFirstLetterUpper(strLeftLetter);
						if (item.getFirstLetter().toLowerCase().contains(strFirstLetter.toLowerCase())
								&& strTmp.contains(setFirstLetterUpper(input.toLowerCase()))) {
							item.setMatchIndex(item.getMatchIndex() + nMatchIndex);// item.getContactPY().toLowerCase().indexOf(input.toLowerCase());
							item.setSearchType(2);
							result.add(item);
							break;
						}
						strLeftLetter = strLeftLetter.substring(nMatchIndex + 1);
					}
				} else {
					ContactsEntity cItem = (ContactsEntity) item;
					for (PhoneNumEntity pItem : cItem.getPhoneNums()) {
						if (pItem.phoneNum.contains(input)) {
							item.setSearchType(3);
							result.add(item);
							break;
						}
					}
				}
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String setFirstLetterUpper(String input) {
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;
	}

	public static boolean judgeExistByName(String name) {
		boolean ret = false;
		if (mContacts != null) {
			for (ContactsEntity model : mContacts) {
				if (model != null && model.getDisplayName() != null) {
					if (model.getDisplayName().equals(name)) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	// 往数据库中新增联系人
	public static void AddContact(Context context, String name, String number) {
		ContentValues values = new ContentValues();
		// 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
		Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);

		// 往data表插入姓名数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// 内容类型
		values.put(StructuredName.GIVEN_NAME, name);

		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
		// 往data表插入电话数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, number);
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
	}

	public static String getNameByPhone(Context mContext, String phoneNumber) {
		String name = "";
		if (TextUtils.isEmpty(phoneNumber)) {
			return name;
		}
		try {
			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
			Cursor cursor = mContext.getContentResolver().query(uri, new String[] { PhoneLookup.DISPLAY_NAME }, null,
					null, null);
			if (cursor != null && cursor.moveToFirst()) {
				name = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			}
			if (cursor != null)
				cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
}
