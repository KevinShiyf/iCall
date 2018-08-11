package com.icall.free.util;

import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isNull(String text) {
        if (text == null || text.length() == 0 || "null".equals(text) || "NULL".equals(text)) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String text) {
        return !isNull(text);
    }

    public static String getSpelling(String str) {
        if (null == str || "".equals(str)) {
            return "#";
        } else {
            str = str.trim();
            str = str.replaceAll(" ", "");
            str = str.replaceAll("[\u4e00-\u9fa5]+", "");
        }
        return str;
    }

    public static String getChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(0));
        }
        return sb.toString();
    }

    public static String getFirstLetter(String str) {
        String key = str.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }

    public static boolean isChinese(String str) {
        if (isNull(str)) {
            return false;
        }
        String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        StringBuilder result = new StringBuilder();
        while (m.find()) {
            result.append(m.group(0));
        }
        return result.length() == str.length();
    }

    /**
     * @since 2015-07-30
     * @author wanghao
     * @param str
     * @return 判断字符窜是否以01-9开头
     */
    public static boolean isStartWith01_9(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("01") || str.startsWith("02") || str.startsWith("03") || str.startsWith("04")
                    || str.startsWith("05") || str.startsWith("06") || str.startsWith("07") || str.startsWith("08")
                    || str.startsWith("09")) {
                return true;
            }
        }
        return false;
        // String regEx = "^(0[1-9])";
        // Pattern p = Pattern.compile(regEx);
        // Matcher m = p.matcher(str);
        // return m.matches();
    }

    /**
     * @since 2015-07-30
     * @author wanghao
     * @param str
     * @return 判断字符窜是否以01-2开头
     */
    public static boolean isStartWith01_2(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("01") || str.startsWith("02")) {
                return true;
            }
        }
        return false;
        // String regEx = "^(0[1-2])";
        // Pattern p = Pattern.compile(regEx);
        // Matcher m = p.matcher(str);
        // return m.matches();
    }

    /**
     * @since 2015-07-30
     * @author wanghao
     * @param str
     * @return 判断字符窜是否以03-9开头
     */
    public static boolean isStartWith03_9(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("03") || str.startsWith("04") || str.startsWith("05") || str.startsWith("06")
                    || str.startsWith("07") || str.startsWith("08") || str.startsWith("09")) {
                return true;
            }
        }
        return false;
        // String regEx = "^(0[3-9])";
        // Pattern p = Pattern.compile(regEx);
        // Matcher m = p.matcher(str);
        // return m.matches();
    }

    // 判断单个字符是否为汉字 add by wanghao 2015-6-11
    public static boolean singleChinese(String str) {
        boolean ret = false;
        if (isNull(str)) {
            ret = false;
        }
        String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    // add by wanghao 2015-5-11 判断字符窜是否为数字字符窜
    public static boolean isAllNumberString(String src) {
        if (src == null || src.length() == 0) {
            return false;
        } else {
            char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            char[] chars = src.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char tmp = chars[i];
                boolean flags = false;
                for (int j = 0; j < numbers.length; j++) {
                    if (tmp == numbers[j]) {
                        flags = true;
                        break;
                    } else {
                        flags = false;
                        continue;
                    }
                }

                if (flags) {
                    continue;
                } else {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * @since 2015-11-01
     * @author wanghao
     * @return 返回字符中所包含的数字
     */
    public static ArrayList<String> getNumberFromString(String content) {
        if (TextUtils.isEmpty(content))
            return null;
        ArrayList<String> mlist = new ArrayList<String>();
        String number = "";
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) >= 48 && content.charAt(i) <= 57) {
                number += content.charAt(i);
                if (i == content.length() - 1) {
                    if (number.length() > 0) {
                        mlist.add(number);
                        number = "";
                    }
                }
            } else {
                if (number.length() > 3) {
                    mlist.add(number);
                }
                number = "";
            }
        }
        return mlist;
    }

    public static String getOperatorFlag(String phoneNum) {
        if (isNull(phoneNum)) {
            return "";
        }
        String myOperator = LocalNameFinder.getInstance().getOperatorOfNumbers(phoneNum);
        String flag = "";
        if (myOperator.contains("电信")) {
            flag = "DX";
        } else if (myOperator.contains("联通")) {
            flag = "LT";
        } else if (myOperator.contains("移动")) {
            flag = "YD";
        }
        return flag;
    }

    public static boolean isPhoneNum(String phoneNum) {

        String p1 = "(13[0-9])" + "|" + "(14[5|7])" + "|" + "(15[^4,\\D])" + "|" + "(18[0-9])" + "|" + "(17[0|6|7|8])";

        Pattern p = Pattern.compile("^(" + p1 + ")" + "\\d{8}$");

        Matcher m = p.matcher(phoneNum);
        return m.matches();

    }

    public static boolean isPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^[1][3-8][0-9]{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * TODO 保留两位小数不进行四舍五入
     *
     * @author Evan 2015-8-27
     * @param finalMoney
     * @return
     */
    public static double getProitwo(double finalMoney) {
        if ("".equals(finalMoney))
            return 0;

        BigDecimal big = new BigDecimal(finalMoney);
        BigDecimal news = big.setScale(2, RoundingMode.HALF_EVEN); // 保留2位小数，可以不带前导零
        return news.doubleValue();
    }

    /**
     * 价格如果小数点超出3位取2位，一位有值小数点显示一位，小数点后面如果是0显示整形
     *
     * @param finalMoney
     * @return
     */
    public static String getStrPrice(double finalMoney) {
        String new_price = "0";
        double price = getProitwo(finalMoney);
        if (price % 1 == 0) {
            new_price = String.valueOf((int) price);
        } else {
            // DecimalFormat g = new DecimalFormat("0.00");
            // new_price = g.format(price);
            new_price = String.valueOf(price);
        }
        return new_price;
    }

    /**
     * 手机号码按3-4-4格式化显示
     *
     * @author Evan 2015-8-28
     * @param num
     * @return
     */
    public static void formatPhoneNum(EditText edt, CharSequence num, int start, int before, int count) {
        if (num == null || edt == null || num.length() == 0) {
            return;
        }
        String contents = mGetFiltTel(num.toString());
        if (num.length() > 13) {
            contents = mGetFiltTel(num.toString());
            String newContents = contents.substring(0, contents.length() > 13 ? 13 : contents.length());
            edt.setText(newContents);
            if (contents.length() < 14) {
                edt.setSelection(contents.length() - 1);
            } else {
                if (contents.charAt(12) == ' ') {
                    edt.setSelection(12);
                } else {
                    edt.setSelection(13);
                }
            }
            return;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < contents.length(); i++) {
            if (i != 3 && i != 8 && contents.charAt(i) == ' ')
                continue;
            else {
                sb.append(contents.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, " ");
                }
            }
        }
        if (!sb.toString().equals(contents)) {
            edt.setText(sb.toString());
            edt.setSelection(edt.getText().length());
        }
    }

    public static void formatPhoneNum(EditText edt, CharSequence num, int before) {
        if (num == null || edt == null || num.length() == 0) {
            return;
        }
        String contents = num.toString();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(contents.charAt(i));
            }
        }
        if (!sb.toString().equals(contents)) {
            edt.setText(sb.toString());
            edt.setSelection(sb.length());
        }
    }

    /**
     * 手机号码按3－4－4格式化显示
     *
     * @author Evan 2015-8-28
     * @param num
     * @return
     */
    public static void formatTelPhoneNum(EditText edt, CharSequence num) {
        String contents = num.toString();
        int length = contents.length();
        if (num.length() > 13) {
            contents = mGetFiltTel(num.toString());
            edt.setText(contents.substring(0, contents.length() > 13 ? 13 : contents.length()));
            if (contents.length() < 14) {
                edt.setSelection(contents.length() - 1);
            } else {
                if (contents.charAt(12) == ' ') {
                    edt.setSelection(12);
                } else {
                    edt.setSelection(13);
                }
            }
            return;
        }
        if (length == 4) {
            if (contents.substring(3).equals(new String(" "))) { // -
                contents = contents.substring(0, 3);
                edt.setText(contents);
                edt.setSelection(contents.length());
            } else { // +
                contents = contents.substring(0, 3) + " " + contents.substring(3);
                edt.setText(contents);
                edt.setSelection(contents.length());
            }
        } else if (length == 9) {
            if (contents.substring(8).equals(new String(" "))) { // -
                contents = contents.substring(0, 8);
                edt.setText(contents);
                edt.setSelection(contents.length());
            } else {// +
                contents = contents.substring(0, 8) + " " + contents.substring(8);
                edt.setText(contents);
                edt.setSelection(contents.length());
            }
        }
    }

    public static String mFiltTel(String tel) {
        tel = tel.replace("-", "");
        tel = tel.replace("+86", "");
        tel = tel.replace(" ", "");
        if (tel.length() > 10 && tel.startsWith("1"))
            tel = tel.substring(0, 11);
        return tel;
    }

    public static String mFiltTels(String tel) {
        tel = tel.replace("-", "");
        tel = tel.replace("+86", "");
        tel = tel.replace(" ", "");
        return tel;
    }

    private static String mGetFiltTel(CharSequence tel) {
        tel = tel.toString().replace("-", "");
        tel = tel.toString().replace("+86", "");
        tel = tel.toString().replace("+", "");
        return tel.toString();
    }

    public static void formatPhoneShow(EditText edt, String num) {
        String contents = num.toString();
        if (contents.length() > 8) {
            contents = contents.substring(0, 3) + " " + contents.substring(3, 7) + " " + contents.substring(7);
            edt.setText(contents);
            edt.setSelection(contents.length());
        }
    }
}
