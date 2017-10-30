/*
 * FileName: TimeUtil.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.app.utils;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈时间处理工具类〉
 *
 * @author zhangyihang 2017年10月17日 09:01:45
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    private static final String DAY_FORMAT = "yyyyMMdd";

    /**
     * 
     * 功能描述: <br>
     * 〈计算量日期之间天数〉
     *
     * @param startTime
     * @param endTime
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static DateTime[] prepareDays(DateTime startTime, DateTime endTime) {
        if (startTime.isAfter(endTime)) {
            return null;
        }
        Period p = new Period(endTime, endTime, PeriodType.days());
        // 1 表示包含开始与 结束时间
        int daysCount = p.getDays() + 1;
        DateTime[] days = new DateTime[daysCount];
        DateTime time = startTime;
        for (int i = 0; i < daysCount; i++) {
            days[i] = time;
            time = time.plusDays(1);
        }
        return days;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }
    /**
     * 
     * 功能描述: <br>
     * 〈获取时间〉
     *
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long queryTime(Date date) {
        return null == date ? 0 : date.getTime();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param time
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean checkIsBeforCurrentDay(long time) {
    	// 是否已过有效期，晚于第二天六点
    	Date limit = new Date(time);
		limit = DateUtils.truncate(limit, Calendar.DATE);
		limit = DateUtils.addDays(limit, 1);
		limit = DateUtils.addHours(limit, 6);
        return limit.before(new Date());
    }

    /**
     * 
     * 功能描述: <br>
     * 〈格式化时间〉
     *
     * @param dayTime
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long queryTimeOfDay(long dayTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAY_FORMAT);
            String date = simpleDateFormat.format(dayTime);
            return simpleDateFormat.parse(date).getTime();
        } catch (Exception e) {
            logger.error("queryTimeOfDay par:{} into yyyyMMdd error, mes:{}", dayTime, e);
        }
        return 0;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param ob
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formateDate(Object dayTime) {
        if(null == dayTime){
            return "0";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAY_FORMAT);
            return simpleDateFormat.format(Long.valueOf(String.valueOf(dayTime)));
        } catch (Exception e) {
            logger.error("formateDate par:{} into yyyyMMdd error, mes:{}", dayTime, e);
        }
        return "0";
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈获取昨天时间〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String queryTheDayBeforeLastDayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        return formateDate(calendar.getTime().getTime());
    }
}
