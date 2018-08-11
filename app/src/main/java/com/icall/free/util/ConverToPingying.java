package com.icall.free.util;

import com.icall.free.ICallApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author: xiaozhenhua
 * @data: 2018-01-25
 */

public class ConverToPingying {

    public static ConverToPingying py;
    public static ConverToPingying getInstance(){
        if(py == null){
            py = new ConverToPingying();
        }
        return py;
    }

    private Properties properties;
    private boolean isLoadResource = false;

    public void clearProperties(){
        if(properties != null){
            properties.clear();
            isLoadResource = false;
            System.gc();
        }
    }

    /**
     * 输入汉字，转失汉字结果集
     * returnStr[0]首字母
     * returnStr[1]首字母数字
     * returnStr[2]全拼
     * returnStr[3]全拼数字
     * @param name
     * @return
     * @author: kevin
     * @data:2011-1-31 下午5:30:43
     */
    public String[] converToPingYingAndNumber(String name){
        if (!isLoadResource) {
            loadResource();
        }
        String[] returnStr = new String[4];
        StringBuilder pingYingHead = new StringBuilder(); // 首字母
        StringBuilder pingYingHeadNumber = new StringBuilder(); // 首字母数字
        StringBuilder pingYing = new StringBuilder(); // 全拼
        StringBuilder pingYingNumber = new StringBuilder(); // 全拼数字
        name = replaceString(name);
        char[] charName = name.toCharArray();
        if (charName != null) {
            for (char c : charName) {
                String str = nameConverToPingying(c);
                try {
                    char[] array = str.toCharArray();
                    if (array.length > 0) {
                        char headChar = conversionHeadUppercase(array[0]);
                        if (str.length() > 1) {
                            pingYing.append(headChar + str.substring(1, str.length()));
                        } else {
                            pingYing.append(headChar);
                        }
                        pingYingHead.append(headChar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        pingYingHeadNumber.append(converEnToNumber(pingYingHead.toString()));
        pingYingNumber.append(converEnToNumber(pingYing.toString()));

        returnStr[0] = pingYingHead.toString();
        returnStr[1] = pingYingHeadNumber.toString();
        returnStr[2] = pingYing.toString();
        returnStr[3] = pingYingNumber.toString();

        //CustomLog.i("NAME:"+name);
        //CustomLog.i("PY_HEAD:"+pingYingHead.toString());
        //CustomLog.i("PY_HEAD_NUMBER:"+pingYingHeadNumber.toString());
        //CustomLog.i("PY:"+pingYing.toString());
        //CustomLog.i("PY_NUMBER:"+pingYingNumber.toString());
        pingYingHead.delete(0, pingYingHead.length());
        pingYingHeadNumber.delete(0, pingYingHeadNumber.length());
        pingYing.delete(0, pingYing.length());
        pingYingNumber.delete(0, pingYingNumber.length());
        return returnStr;
    }

    /**
     * 转换过程
     * @param name
     * @return
     * @author: kevin
     * @data:2011-2-1 下午4:16:22
     */
    private String nameConverToPingying(char name){
        String str4 = "";
        String str1 = null;
        try {
            if (isChinese(String.valueOf(name))) {
                int i = name;
                str1 = Integer.toHexString(i).toUpperCase();
                str4 = properties.getProperty(str1);
            } else {
                str4 = String.valueOf(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            str1 = null;
        }
        return str4;
    }

    public boolean isChinese(String str) {
        return Pattern.compile("[\\u4e00-\\u9fa5]").matcher(str).find();
    }

    private synchronized void loadResource(){
        if (properties == null) {
            properties = new Properties();
        }
        try {
            InputStream inputStream = ICallApplication.getApplication().getAssets().open("unicode_to_hanyu_pinyin.propertes");
            properties.load(inputStream);
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            isLoadResource = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isLoadResource = false;
        } catch (IOException e) {
            e.printStackTrace();
            isLoadResource = false;
        }
    }

    private char conversionHeadUppercase(char c) {
        switch (c) {
            case 'a':
                return 'A';
            case 'b':
                return 'B';
            case 'c':
                return 'C';
            case 'd':
                return 'D';
            case 'e':
                return 'E';
            case 'f':
                return 'F';
            case 'g':
                return 'G';
            case 'h':
                return 'H';
            case 'i':
                return 'I';
            case 'j':
                return 'J';
            case 'k':
                return 'K';
            case 'l':
                return 'L';
            case 'm':
                return 'M';
            case 'n':
                return 'N';
            case 'o':
                return 'O';
            case 'p':
                return 'P';
            case 'q':
                return 'Q';
            case 'r':
                return 'R';
            case 's':
                return 'S';
            case 't':
                return 'T';
            case 'u':
                return 'U';
            case 'v':
                return 'V';
            case 'w':
                return 'W';
            case 'x':
                return 'X';
            case 'y':
                return 'Y';
            case 'z':
                return 'Z';
            default:
                return c;
        }
    }

    /**
     *
     * 将输入的拼音转成数字
     *
     * @param str
     * @return
     * @author: kevin
     * @version: 2012-4-18 下午04:24:18
     */
    public String converEnToNumber(String str) {
        char[] chars = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (char c : chars) {
            sbf.append(getOneNumFromAlpha(c));
        }
        return sbf.toString();
    }
    /**
     *
     * 将字母转换成数字
     *
     * @param firstAlpha
     * @return
     * @author: kevin
     * @version: 2012-4-16 下午04:38:19
     */
    public char getOneNumFromAlpha(char firstAlpha) {
        switch (firstAlpha) {
            case 'a':
            case 'b':
            case 'c':
            case 'A':
            case 'B':
            case 'C':
                return '2';
            case 'd':
            case 'e':
            case 'f':
            case 'D':
            case 'E':
            case 'F':
                return '3';
            case 'g':
            case 'h':
            case 'i':
            case 'G':
            case 'H':
            case 'I':
                return '4';
            case 'j':
            case 'k':
            case 'l':
            case 'J':
            case 'K':
            case 'L':
                return '5';
            case 'm':
            case 'n':
            case 'o':
            case 'M':
            case 'N':
            case 'O':
                return '6';
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
                return '7';
            case 't':
            case 'u':
            case 'v':
            case 'T':
            case 'U':
            case 'V':
                return '8';
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return '9';
            default:
                return firstAlpha;
        }
    }


    /**
     * 首字母是否有英文
     * @param str
     * @return
     */
    public boolean isEng(String str) {
        if (str != null && str.length() > 0) {
            char ch = str.charAt(0);
            return  ch >= 0x0000 && ch <= 0x00ff;
        } else {
            return false;
        }
    }

    /**
     * 获得汉语拼音首字母
     *
     * @param str
     * @return
     * @author: kevin
     * @data:2012-8-7 上午9:39:12
     */
    public String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }
    public String replaceString(String chines) {
        if(chines != null){
            return chines.replace("《", "").replace("》", "").replace("！", "")
                    .replace("￥", "").replace("【", "").replace("】", "")
                    .replace("（", "").replace("）", "").replace("－", "")
                    .replace("；", "").replace("：", "").replace("”", "")
                    .replace("“", "").replace("。", "").replace("，", "")
                    .replace("、", "").replace("？", "").replace(" ", "")
                    .replace("-", "").replace("*", "").replace("…", "")
                    .replace(",", "");
        } else {
            return chines;
        }
    }
}
