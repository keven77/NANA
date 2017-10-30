package com.juran.index.mdm.app.contants;

import com.google.common.collect.Lists;
import com.juran.index.mdm.client.contants.IndexMmContants;

import java.util.List;

public class IndexConstants {

    public static final String WIDE_TABLE_SCOPE_FULL = "full";

    public static final String WIDE_TABLE_SCOPE_DELTA = "delta";

    public static final String ACTION_NEW = "N";
    public static final String ACTION_MODIFY = "U";
    public static final String DELETE_DELETE = "D";

    /**
     * 作者:xy<br>
     * 功能描述:Catentry Action
     */
    public static class DeltaCatentryAction {
        public static List<String> MODIFY_LIST = Lists.newArrayList(
                IndexMmContants.CatentryAction.ADD,
                IndexMmContants.CatentryAction.MODIFY,
                IndexMmContants.CatentryAction.ONSHELF_UP,
                IndexMmContants.CatentryAction.FREEZE_RELEASE);

        public static List<String> DELETEL_LIST = Lists.newArrayList(
                IndexMmContants.CatentryAction.DEL,
                IndexMmContants.CatentryAction.ONSHELF_DOWN,
                IndexMmContants.CatentryAction.FREEZE);
    }

    /**
     * 作者:xy<br>
     * 功能描述:Brand Action
     */
    public static class DeltaBrandAction {
        public static final String ACTION_NEW = "N";
        public static final String ACTION_MODIFY = "U";
        public static final String DELETE_DELETE = "D";

        public static List<String> MODIFY_LIST = Lists.newArrayList(
                IndexMmContants.BrandAction.ADD,
                IndexMmContants.BrandAction.MODIFY,
                IndexMmContants.BrandAction.STATE_PASS,
                IndexMmContants.BrandAction.LOCK_OPEN,
                IndexMmContants.BrandAction.DELETE_REVOKE);

        public static List<String> DELETEL_LIST = Lists.newArrayList(
                IndexMmContants.BrandAction.STATE_NO_PASS,
                IndexMmContants.BrandAction.LOCK_CLOSE,
                IndexMmContants.BrandAction.DELETE);
    }

}
