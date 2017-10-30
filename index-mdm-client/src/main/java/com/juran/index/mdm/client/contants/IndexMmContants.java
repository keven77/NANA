package com.juran.index.mdm.client.contants;

public class IndexMmContants {
    /**
     * 作者:xy<br>
     * 功能描述:商品的操作
     */
    public static class CatentryAction {
        public static final String ADD = "add";//新增
        public static final String DEL = "del";//删除
        public static final String MODIFY = "modify";//修改（名称、定制、属性、品牌、价格）
        public static final String ONSHELF_UP = "up";//上架
        public static final String ONSHELF_DOWN = "down";//下架
        public static final String FREEZE = "freeze";//冻结
        public static final String FREEZE_RELEASE = "release";//释放

    }

    /**
     * 作者:xy<br>
     * 功能描述:品牌的操作
     */
    public static class BrandAction {
        public static final String ADD = "add";//新增
        public static final String MODIFY = "modify";//修改（名称、logo等修改）

        public static final String STATE_PASS = "A";//审核通过
        public static final String STATE_NO_PASS = "D";//审核不通过

        public static final String LOCK_OPEN = "open";//解锁
        public static final String LOCK_CLOSE = "close";//锁定

        public static final String DELETE = "delete";//删除
        public static final String DELETE_REVOKE = "delete_revoke";//撤销删除

    }
}
