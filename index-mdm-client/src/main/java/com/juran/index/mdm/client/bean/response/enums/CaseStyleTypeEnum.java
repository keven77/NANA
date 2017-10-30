package com.juran.index.mdm.client.bean.response.enums;

/*
 * FileName: CaseIndexResource.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述 <案列相关的枚举>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public enum CaseStyleTypeEnum {
    Japan("Japan", "日式"),
    Korea("Korea", "韩式"),
    Mashup("Mashup", "混搭"),
    chinese("chinese", "中式"),
    neoclassical("neoclassical", "新古典"),
    asan("ASAN", "东南亚"),
    US("US", "美式"),
    country("country", "田园"),
    modern("modern", "现代"),
    mediterranean("mediterranean", "地中海"),
    european("european","欧式"),

    one("one", "一室"),
    two("two", "两室"),
    three("three", "三室"),
    four("four", "四室"),
    five("five", "五室"),
    loft("loft", "LOFT"),
    multiple("multiple", "复式"),
    villa("villa", "别墅"),
    other("other", "其他");

    public String type;
    public String name;

    CaseStyleTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String findName(String type) {
        String result = null;
        for (CaseStyleTypeEnum catalogIdEnum : CaseStyleTypeEnum.values()) {
            if (catalogIdEnum.type .equals(type) ) {
                result = catalogIdEnum.name;
            }
        }
        return result;
    }

}
