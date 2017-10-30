/*
 * FileName: ParamCheckUtil.java
 * Author:   liubb
 * Date:     2016年5月16日 上午11:28:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.app.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈判断是否为空〉
 *
 * @author liubb
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ParamCheckUtil {
    
    /**
     * 
     * 功能描述: <br>
     * 〈检查list是否为空〉
     *
     * @param list
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> boolean checkListNull(List<T> list) {
        if (null == list || list.isEmpty() || 0 == list.size()) {
            return true;
        }
        return false;
    }

    
    /**
     * 
     * 功能描述: <br>
     * 〈检查字符串是否为空〉
     *
     * @param str
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String checkString(String str){
        return StringUtils.isBlank(str) ? "" : str;
    }
    
}
