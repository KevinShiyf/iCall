package com.icall.free.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getFormatDate() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
    }

    public static String getFormatDate(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static String getFormatDate(long time) {
        String date = "";

        Calendar currentC = Calendar.getInstance();
        int currYear = currentC.get(Calendar.YEAR);
        int currDay = currentC.get(Calendar.DAY_OF_YEAR);

        Calendar msgC = Calendar.getInstance();
        Date msgData = new Date(time);
        msgC.setTime(msgData);
        int msgYear = msgC.get(Calendar.YEAR);
        int msgDay = msgC.get(Calendar.DAY_OF_YEAR);

        if (msgYear != currYear) {
            SimpleDateFormat sDateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
            date = sDateFormatYMD.format(msgData);
//            SimpleDateFormat kDateFormatYMD = new SimpleDateFormat("kk");
//            int kk = Integer.parseInt(kDateFormatYMD.format(msgData));
//            if (kk > 0 && kk < 6) {
//                date = date.replace("日", "日 零晨 ");
//            } else if (kk >= 6 && kk <= 12) {
//                date = date.replace("日", "日 上午 ");
//            } else if (kk > 12 && kk <= 18) {
//                date = date.replace("日", "日 下午 ");
//            } else if (kk > 18 && kk <= 22) {
//                date = date.replace("日", "日 晚上 ");
//            } else {
//                date = date.replace("日", "日 深夜 ");
//            }
        } else {
            if (msgDay + 1 == currDay) {
                SimpleDateFormat sDFormat = new SimpleDateFormat("昨天 HH:mm");
                date = sDFormat.format(msgData);
                SimpleDateFormat kDateFormatYMD = new SimpleDateFormat("kk");
                int kk = Integer.parseInt(kDateFormatYMD.format(msgData));
                if (kk > 0 && kk < 6) {
                    date = date.replace("昨天", " 零晨 ");
                } else if (kk >= 6 && kk <= 12) {
                    date = date.replace("昨天", " 上午 ");
                } else if (kk > 12 && kk <= 18) {
                    date = date.replace("昨天", " 下午 ");
                } else if (kk > 18 && kk <= 22) {
                    date = date.replace("昨天", " 晚上 ");
                } else {
                    date = date.replace("昨天", " 深夜 ");
                }
            } else if (msgDay == currDay) {
                SimpleDateFormat sDFormat = new SimpleDateFormat("HH:mm");
                date = sDFormat.format(msgData);
//                SimpleDateFormat kDateFormatYMD = new SimpleDateFormat("kk");
//                int kk = Integer.parseInt(kDateFormatYMD.format(msgData));
//                if (kk > 0 && kk < 6) {
//                    date = date.replace("日", " 零晨 ");
//                } else if (kk >= 6 && kk <= 12) {
//                    date = date.replace("日", " 上午 ");
//                } else if (kk > 12 && kk <= 18) {
//                    date = date.replace("日", " 下午 ");
//                } else if (kk > 18 && kk <= 22) {
//                    date = date.replace("日", " 晚上 ");
//                } else {
//                    date = date.replace("日", " 深夜 ");
//                }
            } else {
                SimpleDateFormat sDateFormatMD = new SimpleDateFormat("MM月dd日");
                date = sDateFormatMD.format(msgData);
//                SimpleDateFormat kDateFormatYMD = new SimpleDateFormat("kk");
//                int kk = Integer.parseInt(kDateFormatYMD.format(msgData));
//                if (kk > 0 && kk < 6) {
//                    date = date.replace("日", "日 零晨 ");
//                } else if (kk >= 6 && kk <= 12) {
//                    date = date.replace("日", "日 上午 ");
//                } else if (kk > 12 && kk <= 18) {
//                    date = date.replace("日", "日 下午 ");
//                } else if (kk > 18 && kk <= 22) {
//                    date = date.replace("日", "日 晚上 ");
//                } else {
//                    date = date.replace("日", "日 深夜 ");
//                }
            }
        }
        return date;
    }

    public static String formatDruction(String druction) {
        StringBuffer sbf = new StringBuffer();
        if (druction != null && druction.length() > 0) {
            int h = Integer.parseInt(druction) / 3600;
            int d = 0;
            if (h > 0) {
                sbf.append(h + "小时");
                d = (Integer.parseInt(druction) - (h * 3600)) / 60;
            } else {
                d = Integer.parseInt(druction) / 60;
            }
            int s = Integer.parseInt(druction) % 60;

            if (d > 0) {
                sbf.append(d + "分");
            }
            if (s > 0) {
                sbf.append(s + "秒");
            }
            if (sbf.length() <= 0) {
                sbf.append("0秒");
            }
        } else {
            sbf.append("0秒");
        }
        return sbf.toString();
    }

}
