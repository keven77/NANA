package com.juran.index.mdm.client.bean.response.enums;


public enum SampleImgTypeEnum {
    ROAM(1, "漫游图"),
    DRAWING(2, "渲染图"),
    NORMAL(3, "普通图");

    public int type;
    public String name;

    SampleImgTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String findName(int type) {
        String result = null;
        for (SampleImgTypeEnum catalogIdEnum : SampleImgTypeEnum.values()) {
            if (catalogIdEnum.type == type) {
                result = catalogIdEnum.name;
            }
        }
        return result;
    }

}
