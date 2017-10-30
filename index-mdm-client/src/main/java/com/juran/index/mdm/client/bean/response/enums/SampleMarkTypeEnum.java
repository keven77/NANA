package com.juran.index.mdm.client.bean.response.enums;


public enum SampleMarkTypeEnum {
    STYLE(1, "风格"),
    SPACE(2, "户型"),
    AREA(3, "面积"),
    FEATURE(4, "特色");

    public int type;
    public String name;

    SampleMarkTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String findName(int type) {
        String result = null;
        for (SampleMarkTypeEnum catalogIdEnum : SampleMarkTypeEnum.values()) {
            if (catalogIdEnum.type == type) {
                result = catalogIdEnum.name;
            }
        }
        return result;
    }

}
