package com.icall.free.util;

import android.database.Cursor;

public class DBUtil {
	public static void closeCursor(Cursor cursor) {
		if (null != cursor) {
			try {
				cursor.close();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			cursor = null;
		}
	}
}
