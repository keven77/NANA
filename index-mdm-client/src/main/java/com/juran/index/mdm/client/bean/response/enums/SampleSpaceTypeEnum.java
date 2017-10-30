package com.juran.index.mdm.client.bean.response.enums;


public enum SampleSpaceTypeEnum {
//    1全屋2客厅3卧室4厨房5阳台6书房7卫生间8其他9次卧10餐厅11门厅
    ALL(1, "全屋",1),
    PARLOUR(2, "客厅",3),
    BEDROOM(3, "卧室",4),
    KITCHEN(4, "厨房",5),
    BALCONY(5, "阳台",6),
    STUDY(6, "书房",7),
    BATHROOM(7, "卫生间",8),
    OTHER(8, "其他",9),
    lying(9, "次卧",10),
    RESTAURANT(10, "餐厅",11),
    HALL(11, "门厅",12),

    KIDSROOM(12, "儿童房",13),
    OFFICE(13, "办公室",14),
    OUTDOORS(14, "户外",15),
    PUBLICEXTERIOR(15, "商用/公用室外区域",16),
    PUBLICINTERIOR(16, "商用/公用室内区域",17),
    RESIDENTIALEXTERIOR(17, "住宅室外区域",18),
    ENTRANCEHALLWAY(18, "入口和过厅",19),
    PRODUCTSHOWCASE(19, "产品展示柜",20),
    FLOORPLAN(20, "平面图",21),
    STUDIO(21, "工作室",22),
    BASEMENT(22, "地下室",23),

    HOMECINEMA(23, "家庭影院",24),
    DEN(24, "小房间",25),
    SKETCH(25, "草图",26),
    MASTERBEDROOM(26, "主卧",27),
    ELDERLYROOM(27, "老人房",28),
    LOUNGE(28, "休闲厅",29),
    AUDITORIUM(29, "影视厅",30),
    NANNYROOM(30, "保姆间",31),
    LAUNDRYROOM(31, "洗衣间",32),
    STORAGEROOM(32, "储藏间",33),
    CLOAKROOM(33, "衣帽间",34),

    STAIRWELL(34, "楼梯间",35),
    AISLE(35, "过道",36),
    CORRIDOR(36, "走廊",37),
    PORCHBALCONY(37, "玄关和阳台",38),
    LIVINGDININGROOM(38, "客厅及餐厅",2);

    public int type;
    public String name;
    public double sequence;

    SampleSpaceTypeEnum(int type, String name,double sequence) {
        this.type = type;
        this.name = name;
        this.sequence = sequence;
    }

    public static String findName(int type) {
        String result = null;
        for (SampleSpaceTypeEnum catalogIdEnum : SampleSpaceTypeEnum.values()) {
            if (catalogIdEnum.type == type) {
                result = catalogIdEnum.name;
            }
        }
        return result;
    }

}
