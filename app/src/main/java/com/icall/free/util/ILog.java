package com.icall.free.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ILog {

	public static boolean SHOW_LOG = true;// 不需要显示日志时，将该值设为false
	public static boolean SHOW_LOG_SDCARD = true;// 在显示日志的条件下，true--日志输出到本地，false--日志不写到本地
	
	public final static String LOGTAG = "icall";

	public static final String PHONE_LOG_NAME = "phone_log";
	
	public static void v(String logMe) {
		v(LOGTAG, logMe);
	}

	public static void d(String logMe) {
		d(LOGTAG, logMe);
	}

	public static void i(String logMe) {
		i(LOGTAG, logMe);
	}

	public static void e(String logMe) {
		e(LOGTAG, logMe);
	}

	public static void v(String tag, String logMe) {
		if (logMe == null)
			return;
		if (SHOW_LOG) {
			Log.v(tag, logMe);
			if (needWriteFile()) {
				writeTestFile(tag, logMe);
			}
		}
	}
	
	public static void d(String tag, String logMe) {
		if (logMe == null)
			return;
		if (SHOW_LOG) {
			Log.d(tag, logMe);
			if (needWriteFile()) {
				writeTestFile(tag, logMe);
			}
		}
	}
	
	public static void i(String tag, String logMe) {
		if (logMe == null)
			return;
		if (SHOW_LOG) {
			Log.i(tag, logMe);
			if (needWriteFile()) {
				writeTestFile(tag, logMe);
			}
		}
	}

	public static void e(String tag, String logMe) {
		if (logMe == null)
			return;
		if (SHOW_LOG) {
			Log.e(tag, logMe);
			if (needWriteFile()) {
				writeTestFile(tag, logMe);
			}
		}
	}

	private static boolean needWriteFile() {
		return SHOW_LOG_SDCARD;
	}

	/**
	 * 写日志信息到文件，AboutYX文件夹目录下，调试用，日志信息会自动换行
	 * @param fileName 文件名
	 * @param data 写入的数据
	 */
	private static SimpleDateFormat sDateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	public synchronized static void writeTestFile(String fileName, String data) {
		String path = FileWRHelper.getSdCardRootPath();
		try {
			File filePath1 = new File(path);
			if (!filePath1.exists()) {
				if (!filePath1.mkdirs())
					return;
			}
			File filePath2 = new File(filePath1, "LogFolder");
			if (!filePath2.exists()) {
				if (!filePath2.mkdirs())
					return;
			}
			File file = new File(filePath2, fileName); // httptest.txt
			if (file.exists()) {
				if (file.length() > 1 * 1024 * 1024) {//Max   5M
					file.delete();
					file.createNewFile();
				}
			} else {
				file.createNewFile();
			}
			StringBuffer buffer = new StringBuffer();
			String dateString = sDateFormatYMD.format(new Date(System.currentTimeMillis()));
			buffer.append(dateString).append("   ").append(data).append("\r\n");

			RandomAccessFile raf = new RandomAccessFile(file, "rw");// "rw" 读写权限
			raf.seek(file.length());
			raf.write(buffer.toString().getBytes());
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
