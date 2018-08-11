package com.icall.free.util;

import android.text.TextUtils;

import com.icall.free.ICallApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 归属地名字查找工具
 * 
 * @author Administrator
 * 
 */
public class LocalNameFinder {

	/*
	 * 移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、147、182、187、188
	 * 联通：130、131、132、155、156、185、186 电信：180、189、133、153、
	 */
	// private static String fileName = "local.dat";
	// private String TAG = "LocalNameFinder";

	public static LocalNameFinder self = null;

	public static LocalNameFinder getInstance() {
		if (self == null) {
			self = new LocalNameFinder();
		}
		return self;
	}

	private class AreaTable {
		short code;
		String name;
	}

	private class NationalTable {
		short code;
		String name;
	}

	private class NumHeadItem {
		short headName;
		int startPos;
		int length;
	}

	private class NumItem {
		short num;
		short index;
	}

	private ArrayList<NationalTable> ntiCodeList;
	private ArrayList<AreaTable> strCodeList;
	private ArrayList<NumHeadItem> numHeadItemList;
	private ArrayList<NumItem> numItemList;
	private String currentNumHead;
	private HashMap<String, ArrayList<NumItem>> map = new HashMap<String, ArrayList<NumItem>>(3);

	/**
	 * 根据电话号码查找归属地名称
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param isAreaCode
	 *            是否为区号
	 * @return 归属地名称
	 */
	public synchronized String findLocalName(String phoneNum, boolean isAreaCode) {
		if (phoneNum == null || phoneNum.length() < 3) {
			return "";
		}
		if (phoneNum.startsWith("10010")) {
			return "中国联通";
		} else if (phoneNum.startsWith("10000")) {
			return "中国电信";
		} else if (phoneNum.startsWith("10086")) {
			return "中国移动";
		}
		phoneNum = formatFreeCallNum(phoneNum);
		if (phoneNum == null) {
			return "";
		}
		initData();
		if (!initDataOk)
			return "";

		if (phoneNum.startsWith("+86")) {
			phoneNum = phoneNum.substring(3);
		} else if (phoneNum.startsWith("0086")) {
			phoneNum = phoneNum.substring(4);
		}

		if (phoneNum.startsWith("0")) { // 处理区号
			if (phoneNum.startsWith("00")) {
				String pString = interNational(phoneNum);
				return pString;
			} else if (phoneNum.startsWith("010") || phoneNum.startsWith("02")) {
				try {
					// 为手机号码
					short head = (short) Integer.parseInt(phoneNum.substring(1, 3));
					int idx = -1;
					int size = strCodeList.size();
					for (int i = 0; i < size; i++) {
						if (strCodeList.get(i).code == head) {
							idx = i;
							break;
						}
					}
					if (idx == -1) {
						return "";
					}
					// 二分查找
					short num = (short) Integer.parseInt(phoneNum.substring(1, 3));
					binarySearch(num, head);
					String locaName = strCodeList.get(idx).name;
					short locaCode = strCodeList.get(idx).code;
					if (isAreaCode) {
						return "" + locaCode;
					} else {
						return locaName;
					}

				} catch (Exception e) {
				}
			} else if (phoneNum.startsWith("03") || phoneNum.startsWith("07") || phoneNum.startsWith("05")
					|| phoneNum.startsWith("06") || phoneNum.startsWith("04") || phoneNum.startsWith("08")
					|| phoneNum.startsWith("09")) {
				try {
					short head = (short) Integer.parseInt(phoneNum.substring(1, 4));
					int idx = -1;
					int size = strCodeList.size();
					for (int i = 0; i < size; i++) {
						if (strCodeList.get(i).code == head) {
							idx = i;
							break;
						}
					}
					if (idx == -1) {
						return "";
					}
					// 二分查找
					short num = (short) Integer.parseInt(phoneNum.substring(1, 4));
					int pos = binarySearch(num, head);
					return strCodeList.get(idx).name;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 为手机号码
				if (phoneNum.length() < 7) {
					return "";
				}
				try {
					// 为手机号码
					short head = (short) Integer.parseInt(phoneNum.substring(1, 4));
					int idx = -1;
					int size = numHeadItemList.size();
					for (int i = 0; i < size; i++) {
						if (numHeadItemList.get(i).headName == head) {
							idx = i;
							break;
						}
					}
					if (idx == -1) {
						return "";
					}
					readPro(numHeadItemList.get(idx).startPos, numHeadItemList.get(idx).length, head);
					short num = (short) Integer.parseInt(phoneNum.substring(4, 8));
					int pos = binarySearch(num, head);
					String locaName = strCodeList.get(numItemList.get(pos).index).name;
					short locaCode = strCodeList.get(numItemList.get(pos).index).code;
					if (isAreaCode) {
						return "" + locaCode;
					} else {
						return locaName;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else { // 为手机号码
			if (phoneNum.length() < 7) {
				return "";
			}
			if (phoneNum.startsWith("17909") || phoneNum.startsWith("17951") || phoneNum.startsWith("17911")
					|| phoneNum.startsWith("12593")) {
				phoneNum = phoneNum.substring(5);
			}
			try {
				// 为手机号码
				short head = (short) Integer.parseInt(phoneNum.substring(0, 3));
				int idx = -1;
				int size = numHeadItemList.size();
				for (int i = 0; i < size; i++) {
					if (numHeadItemList.get(i).headName == head) {
						idx = i;
						break;
					}
				}
				if (idx == -1) {
					return "";
				}
				readPro(numHeadItemList.get(idx).startPos, numHeadItemList.get(idx).length, head);
				short num = (short) Integer.parseInt(phoneNum.substring(3, 7));
				int pos = binarySearch(num, head);
				if (pos >= 0 && pos < numItemList.size()) {
					int index = numItemList.get(pos).index;
					if (index >= 0 && index < strCodeList.size()) {
						String locaName = strCodeList.get(index).name;
						short locaCode = strCodeList.get(index).code;
						if (isAreaCode) {
							return "" + locaCode;
						} else {
							return locaName;
						}
					}
				}
				return "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private int binarySearch(int num, short head) {

		if (map.containsKey(String.valueOf(head))) {
			numItemList = map.get(String.valueOf(head));
		} else {
			return -1;
		}
		int left = 0;
		int right = numItemList.size() - 1;
		while (left <= right) {
			int middle = (left + right) / 2;
			short n = numItemList.get(middle).num;
			if (num == n)
				return middle;
			if (num > n) {
				left = middle + 1;
			} else {
				right = middle - 1;
			}
		}
		return right;
	}

	private short readShort(InputStream is) throws IOException {
		byte[] b = new byte[2];
		is.read(b);
		short value = (short) (((int) (b[0] & 0xff)) | (((int) (b[1] & 0xff)) << 8));
		return value;
	}

	private int readInt(InputStream is) throws IOException {
		byte[] b = new byte[4];
		is.read(b);
		int value = (int) (((int) (b[0] & 0xff)) | (((int) (b[1] & 0xff)) << 8) | (((int) (b[2] & 0xff)) << 16)
				| (((int) (b[3] & 0xff)) << 24));
		return value;
	}

	private String readStr(InputStream is) throws IOException {
		int len = is.read();
		byte[] b = new byte[len];
		is.read(b);
		String s = new String(b, "UTF-16");
		return s;
	}

	private boolean initDataOk = false;

	private void initData() {
		if (initDataOk)
			return;

		InputStream in = null;
		try {
			in = ICallApplication.getApplication().getAssets().open("PhoneNumberQuery.dat");
			// in = new FileInputStream(getLocFilePath());
			if (strCodeList == null) {
				strCodeList = new ArrayList<AreaTable>();
			}
			if (ntiCodeList == null) {
				ntiCodeList = new ArrayList<NationalTable>();
			}
			if (numHeadItemList == null) {
				numHeadItemList = new ArrayList<NumHeadItem>();
			}
			if (numItemList == null) {
				numItemList = new ArrayList<NumItem>();
			}
			strCodeList.clear();
			ntiCodeList.clear();
			numHeadItemList.clear();
			numItemList.clear();
			readIndexData(in);
			// 读取国内国外固定电话区号对应的地区名
			readAreaData(in);
			in.close();
			in = null;
			initDataOk = true;
		} catch (IOException e) {
			e.printStackTrace();
			initDataOk = false;
		} catch (NegativeArraySizeException e) {
			e.printStackTrace();
			initDataOk = false;
		}

	}

	private void readIndexData(InputStream is) throws IOException {
		// 号码段数量
		int num = readShort(is);
		for (int i = 0; i < num; i++) {
			NumHeadItem item = new NumHeadItem();
			item.headName = readShort(is);
			item.startPos = readInt(is);
			item.length = readInt(is);
			// Log.i("numHeadItemList","numHeadItemList headName = "
			// +item.headName+ " startPos = " +item.startPos+
			// " length = "+item.length);
			/*
			 * CustomLog.v(TAG, "numHeadItemList headName = " + item.headName +
			 * " startPos = " + item.startPos + " length = " + item.length);
			 */
			numHeadItemList.add(item);
		}
	}

	private void readAreaData(InputStream is) throws IOException {
		int count = readShort(is);
		for (int i = 0; i < count; i++) {
			AreaTable areaLine = new AreaTable();
			short code = readShort(is);
			String name = readStr(is);
			areaLine.code = code;
			areaLine.name = name;
			strCodeList.add(areaLine);
		}

		count = readShort(is);
		for (int i = 0; i < count; i++) {
			NationalTable nationalTable = new NationalTable();
			short code = readShort(is);
			String name = readStr(is);
			nationalTable.code = code;
			nationalTable.name = name;
			ntiCodeList.add(nationalTable);
		}

	}

	private void readPro(int start, int len, short headName) throws IOException {
		if (currentNumHead != null && currentNumHead.equals(headName) && numItemList.size() > 0) {
			return;
		}

		if (map.containsKey(String.valueOf(headName))) {
			return;
		}

		InputStream in = ICallApplication.getApplication().getAssets().open("PhoneNumberQuery.dat");
		if (in == null) {
			return;
		}
		byte[] data = new byte[len];
		try {
			ArrayList<NumItem> tem = new ArrayList<NumItem>(100);
			in.skip(start);
			in.read(data);
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			byte[] b = new byte[3];
			while (is.read(b, 0, b.length) != -1) {
				int v = ((int) b[0] & 0xff) | (((int) b[1] & 0xff) << 8) | (((int) b[2] & 0xff) << 16);
				short phoneNum = (short) (v >> 10);
				short code = (short) (v & 0x3FF);

				NumItem item = new NumItem();
				item.num = phoneNum;
				item.index = code;
				tem.add(item);
			}
			is.close();
			map.put(String.valueOf(headName), tem);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			data = null;
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * kevin
	 * 
	 * @param phoneNum
	 * @return
	 */
	private String interNational(String phoneNum) {
		String locaName = "";
		try {
			// 为国际号码
			short head = (short) Integer.parseInt(phoneNum.substring(0, 6));
			int idx = -1;
			for (int i = 0; i < ntiCodeList.size(); i++) {
				if (ntiCodeList.get(i).code == head) {
					idx = i;
					break;
				}
			}
			// CustomLog.i("idx:"+idx+"====head:"+head);
			if (idx == -1) {
				head = (short) Integer.parseInt(phoneNum.substring(0, 5));
				for (int i = 0; i < ntiCodeList.size(); i++) {
					if (ntiCodeList.get(i).code == head) {
						idx = i;
						break;
					}
				}
				if (idx == -1) {
					head = (short) Integer.parseInt(phoneNum.substring(0, 4));
					for (int i = 0; i < ntiCodeList.size(); i++) {
						if (ntiCodeList.get(i).code == head) {
							idx = i;
							break;
						}
					}
					if (idx == -1) {
						head = (short) Integer.parseInt(phoneNum.substring(0, 3));
						for (int i = 0; i < ntiCodeList.size(); i++) {
							if (ntiCodeList.get(i).code == head) {
								idx = i;
								break;
							}
						}
						if (idx == -1) {
							return "";
						}
					}
				}
			}

			locaName = ntiCodeList.get(idx).name;
			if (locaName.lastIndexOf("(") != -1) {
				locaName = locaName.substring(0, locaName.lastIndexOf("("));
			}
		} catch (Exception e) {
			try {
				short head = (short) Integer.parseInt(phoneNum.substring(0, 5));
				int idx = -1;
				for (int i = 0; i < ntiCodeList.size(); i++) {
					if (ntiCodeList.get(i).code == head) {
						idx = i;
						break;
					}
				}
				locaName = ntiCodeList.get(idx).name;
				if (locaName.lastIndexOf("(") != -1) {
					locaName = locaName.substring(0, locaName.lastIndexOf("("));
				}
			} catch (Exception e2) {
				try {
					short head = (short) Integer.parseInt(phoneNum.substring(0, 4));
					int idx = -1;
					for (int i = 0; i < ntiCodeList.size(); i++) {
						if (ntiCodeList.get(i).code == head) {
							idx = i;
							break;
						}
					}
					locaName = ntiCodeList.get(idx).name;
					if (locaName.lastIndexOf("(") != -1) {
						locaName = locaName.substring(0, locaName.lastIndexOf("("));
					}
				} catch (Exception e3) {
					try {
						short head = (short) Integer.parseInt(phoneNum.substring(0, 3));
						int idx = -1;
						for (int i = 0; i < ntiCodeList.size(); i++) {
							if (ntiCodeList.get(i).code == head) {
								idx = i;
								break;
							}
						}
						if (idx != -1)
							locaName = ntiCodeList.get(idx).name;
						if (locaName.lastIndexOf("(") != -1) {
							locaName = locaName.substring(0, locaName.lastIndexOf("("));
						}
					} catch (Exception e4) {
						e4.printStackTrace();
					}
				}
			}

		}
		return locaName;
	}

	/**
	 * 判断是否是国际电话
	 * 
	 * @param phoneNum
	 * @return
	 * @author: kevin
	 * @version: 2012-3-6 下午03:37:33
	 */
	public boolean isITT(String phoneNum) {
		if (phoneNum == null || phoneNum.equals("")) {
			return false;
		}
		if (phoneNum.startsWith("00") && !phoneNum.startsWith("0086")) {
			return true;
		}
		if (phoneNum.startsWith("+") && !phoneNum.startsWith("+86")) {
			return true;
		}
		return false;
	}

	private String formatFreeCallNum(String num) {
		// 去除"-","+"字符
		if (num == null)
			return "";
		String oldString = num.replace("-", "");
		oldString = oldString.replace("+", "").replace(" ", "");
		if (oldString.length() < 3) {
			return oldString;
		}
		if (oldString.length() > 4 && oldString.startsWith("0086")) {
			oldString = oldString.substring(4);
		}
		// 去除86前缀
		if (oldString.matches("^86.*") && oldString.length() != 7 && oldString.length() != 8) {
			oldString = oldString.substring(2);
		} else if (oldString.length() == 7 || oldString.length() == 8) { // 没有区号的固话
			return oldString;
		}
		// 去除12593 17951 17909 17911前缀
		if (oldString.matches("^12593.*|17951.*|17909.*|17911.*")) {
			oldString = oldString.substring(5);
		}

		// 以0起始，以数字串结束，并且8-12位
		if (oldString.matches("^(0){1}[1-9]*$") && oldString.matches("[0-9]{8,12}")) {
			return oldString;
		} else {
			if (oldString.startsWith("1")) {
				// 以13，14,15,18起始，并且11位数字
				if (oldString.matches("^13.*|14.*|15.*|18.*")) {
					if (oldString.matches("[0-9]{11}")) {
						return oldString;
					} else {
						return oldString;
					}
				} else {
					return oldString;
				}
			}
		}
		return oldString;
	}

	public String getOperatorOfNumbers(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber))
			return "";
		if (phoneNumber.startsWith("130") || phoneNumber.startsWith("131") || phoneNumber.startsWith("132")
				|| phoneNumber.startsWith("145") || phoneNumber.startsWith("155") || phoneNumber.startsWith("156")
				|| phoneNumber.startsWith("185") || phoneNumber.startsWith("186") || phoneNumber.startsWith("176")) {
			return "联通";
		} else if (phoneNumber.startsWith("133") || phoneNumber.startsWith("153") || phoneNumber.startsWith("180")
				|| phoneNumber.startsWith("181") || phoneNumber.startsWith("189") || phoneNumber.startsWith("177")) {
			return "电信";
		} else if (phoneNumber.startsWith("134") || phoneNumber.startsWith("135") || phoneNumber.startsWith("136")
				|| phoneNumber.startsWith("137") || phoneNumber.startsWith("138") || phoneNumber.startsWith("139")
				|| phoneNumber.startsWith("147") || phoneNumber.startsWith("150") || phoneNumber.startsWith("151")
				|| phoneNumber.startsWith("152") || phoneNumber.startsWith("157") || phoneNumber.startsWith("158")
				|| phoneNumber.startsWith("159") || phoneNumber.startsWith("182") || phoneNumber.startsWith("183")
				|| phoneNumber.startsWith("184") || phoneNumber.startsWith("187") || phoneNumber.startsWith("188")
				|| phoneNumber.startsWith("178")) {
			return "移动";
		} else if (phoneNumber.startsWith("170")) {
			if (phoneNumber.startsWith("1700")) {
				// xs dx
				return "电信";
			} else if (phoneNumber.startsWith("1705")) {
				// xs yd
				return "移动";
			} else if (phoneNumber.startsWith("1707") || phoneNumber.startsWith("1708")
					|| phoneNumber.startsWith("1709")) {
				// xs lt
				return "联通";
			}
		}
		return "";
	}
}