package vrbaidu.top.util;

import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by Administrator on 2017/2/21.
 */
public class DateUtils {

    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    // 默认日期时间格式
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 默认时间格式
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
    // 日期格式化
    private static DateFormat dateFormat = null;
    // 时间格式化
    private static DateFormat dateTimeFormat = null;
    private static DateFormat timeFormat = null;
    private static Calendar gregorianCalendar = null;

    /**
     * 解决多线程程序的并发问题
     */
    private static  final ThreadLocal<DateFormat> dtf = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        }
    };
    private static  final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        }
    };

    static {
        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        gregorianCalendar = new GregorianCalendar();
    }

    /*-----------------------------------yyyy-MM-dd HH:mm:ss格式-------------------------------------------*/
    /**
     *  Date类型转换为String类型(并发下线程安全)
     *  yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String dateChangeStr(final Date date) {
        return dtf.get().format(date);
    }

    /**
     *  String类型转换为Date类型(并发下线程安全)
     *  yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date strChangeDate(final String date) throws ParseException {
        return dtf.get().parse(date);
    }
    /**
     *  String类型转换为Date类型(并发下线程安全)
     *  yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String dateTimeFmt(final String date) throws ParseException {
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //DateTime
        return LocalDateTime.parse(date, dtf).format(dtf);
    }

    /**
     * 日期格式化(并发下线程不安全)
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * 时间格式化(并发下线程不安全)
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

     /*-----------------------------------yyyy-MM-dd格式-------------------------------------------*/
    /**
     * 日期格式化(并发下线程不安全)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 日期格式化(并发下线程安全)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getConcurDateFormat(Date date) {
        return df.get().format(date);
    }

    /**
     * 日期格式化(并发下线程不安全)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化(并发下线程安全)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date getConcurDateFormat(String date) throws ParseException {
        return df.get().parse(date);
    }

    /*-----------------------------------HH:mm:ss格式-------------------------------------------*/
    /**
     * 时间格式化(并发下线程不安全)
     * HH:mm:ss
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 时间格式化(并发下线程不安全)
     * HH:mm:ss
     * @param date
     * @return HH:mm:ss
     */
    public static Date getTimeFormat(String date) throws ParseException {
        return timeFormat.parse(date);
    }

    /*----------------------------------------其他---------------------------------------------------*/
    /**
     * 日期格式化(并发下线程不安全)
     * 自定义pattern
     * @param date
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化(并发下线程不安全)
     * 自定义pattern
     * @param date
     * @param 格式类型
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {
        if (StringUtils.isNotBlank(formatStr)) {
            return new SimpleDateFormat(formatStr).format(date);
        }
        return null;
    }


    /**
     * 获取当前日期(并发下线程不安全)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date getNowDate() {
        return DateUtils.getDateFormat(dateFormat.format(new Date()));
    }

    /**
     * 获取当前日期(java1.8)
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getNowLocDate() {
        return LocalDate.now().toString();
    }

    /**
     * 获取当前时间(java1.8)
     * hour, minutes, seconds, nano seconds
     * @return
     */
    public static String getNowLocTime() {
        return LocalTime.now().toString();
    }

    /**
     * 获取当前日期(并发下线程不安全)
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date getNowDateTime() {
        return DateUtils.getDateFormat(dateTimeFormat.format(new Date()));
    }

    /*--------------------------------------------日期类-------------------------------------------------*/
    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前日期星期日日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定时间的星期一日期
     *
     * @param 指定日期
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param 指定日期
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDay() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间段的每一天
     *
     * @param 开始日期
     * @param 结算日期
     * @return 日期列表
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtils.getDateFormat(DateUtils.getDateFormat(startDate));
        endDate = DateUtils.getDateFormat(DateUtils.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        gregorianCalendar.setTime(startDate);
        dates.add(gregorianCalendar.getTime());
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(gregorianCalendar.getTime());
        }
        return dates;
    }

    /**
     * 两时间相减 返回
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getSubTwoTime(String startTime, String endTime){
        try{
            Date d1 = dateTimeFormat.parse(endTime);
            Date d2 = dateTimeFormat.parse(startTime);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            if (days < 0){
                days = new BigDecimal(days).abs().intValue();
            }
            return ""+days;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两时间相减 返回
     * 天+小时+分
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getSubTwoTimes(String startTime, String endTime){
        try{
            Date d1 = dateTimeFormat.parse(startTime);
            Date d2 = dateTimeFormat.parse(endTime);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            long s = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000);
            if(hours <0){
                hours = new BigDecimal(hours).abs().intValue();
            }
            if(minutes <0){
                minutes = new BigDecimal(minutes).abs().intValue();
            }
//            if (s <0 ){
//                s= new BigDecimal(s).abs().intValue();
//            }
            return ""+days+"-"+hours+"h-"+minutes+"m-";
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取提前多少个月
     *
     * @param monty
     * @return
     */
    public static Date getFirstMonth(int monty) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -monty);
        return c.getTime();
    }

    /**
     * 获取1周后的日期
     * @return
     */
    
    public static LocalDate getOneWeekDate(){
        return  LocalDate.now().plus(1, ChronoUnit.WEEKS);
    }

    /**
     * 判断是否是生日
     * @param today
     * @return
     */
    public  static boolean isDate(LocalDate today) {
        MonthDay currrntDay = MonthDay.from(LocalDate.now());
        MonthDay birthday = MonthDay.of(currrntDay.getMonth(), currrntDay.getDayOfMonth());
        MonthDay birthdayMonthDay = MonthDay.from(today);
        if(birthdayMonthDay.equals(birthday)){
            return true;
        }
        return false;
    }

    /**
     * 功能：添加指定秒杀的时间
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        long s = date.getTime();
        s = s + second * 1000;
        return new Date(s);
    }

    /**
     * 功能：返回毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 功能：指定日期加上指定天数
     * @param date 日期
     * @param minute 分钟
     * @return 返回相加后的日期
     */
    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);
        return c.getTime();
    }

    public static void main(String[] args) throws ParseException {
       // System.out.println(DateUtils.dateTimeFmt("2012-12-21 23:22:45"));
        System.out.println(DateUtils.getOneWeekDate());
        System.out.println(getSubTwoTimes("2016-03-02 23:22:29", "2017-03-01 22:22:45"));
    }
}
