package com.icall.free.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileWRHelper {

	public static final String ROOT_FOLDER_NAME = "easyshare";
	public static final String PICTURE="/picture";		//图片
	public static final String APK_FILE="/apk";			//apk文件

	// 获取SD卡的保存文件路径
	// 部分手机自带SD卡，自带的SD卡文件夹命名为sdcard-ext或其它，用系统自带方法无法检测出
	// 返回格式为 "/mnt/sdcard/peiwo/" 或 "/mnt/sdcard-ext/peiwo/"
	public static String getSdCardRootPath() {
		String path = "";
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + ROOT_FOLDER_NAME);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = file.getAbsolutePath();
		}
		if (TextUtils.isEmpty(path)) {
			File mntFile = new File("/mnt");
			File[] mntFileList = mntFile.listFiles();
			if (mntFileList != null) {
				for (int i = 0; i < mntFileList.length; i++) {
					String mmtFilePath = mntFileList[i].getAbsolutePath();
					String sdPath = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					if (!mmtFilePath.equals(sdPath)
							&& mmtFilePath.contains(sdPath)) {
						File file = new File(mmtFilePath + "/"
								+ ROOT_FOLDER_NAME);
						if (!file.exists()) {
							file.mkdirs();
						}
						path = file.getAbsolutePath();
					}
				}
			}
		}
		return TextUtils.isEmpty(path) ? "" : path;
	}

	// 检测是否插入SD卡或是否有自带SD卡
	public static boolean checkSdCard() {
		if (getSdCardRootPath().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 获取保存文件路径
	// 有SD，或自带SD卡时返回SD卡的路径，无SD卡时返回手机存储路径
	public static String getSaveFilePath() {
		String path = getSdCardRootPath();
		if (path.length() == 0) {
			path = "/data/data/com.icall.free";
		}
		return path;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String fileName, String str) {
		try {
			FileWriter fw = new FileWriter(getSdCardRootPath() + "/" + fileName);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(str);
			bw.flush();
			fw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String fileName) {
		StringBuilder result = new StringBuilder();
		FileReader reader = null;
		BufferedReader buf = null;
		try {
			File file = new File(getSdCardRootPath(), fileName);
			if (!file.exists()) {
				return null;
			}
			reader = new FileReader(file);
			buf = new BufferedReader(reader);
			String line = null;
			while ((line = buf.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (buf != null) {
					buf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
}
