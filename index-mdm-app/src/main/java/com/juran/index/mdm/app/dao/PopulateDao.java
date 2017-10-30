package com.juran.index.mdm.app.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 作者:xy<br>
 * 功能描述:PopulateCatalog DB交互
 */
@Component
@Mapper
public interface PopulateDao {

    @Update("${sql}")
    int executorOperateSql(@Param("sql") String sql);

    @Select("${sql}")
    List<Map<String,Object>> executorSelectSql(@Param("sql") String sql);

    @Select("${sql}")
    List<String> executorSelectSqlToList(@Param("sql") String sql);

    @Insert("${sql}")
    int executorInsertSql(@Param("sql") String sql);

    @Insert("<script>INSERT INTO `solrtemp1`.`TI_CATGROUP_PATH_0` (`CATGROUP_KEY`, `CATGROUPS`) VALUES <foreach collection=\"map.keys\" item=\"key\" index=\"index\" open=\"(\" close=\")\" separator=\"),(\"> #{key}, #{map[${key}]}</foreach> ;</script>")
    int insertCatgroupPath(@Param("map") Map<String,String> map);

    @Insert("INSERT INTO `solrtemp1`.`TI_APGROUP_0` (`CATENTRY_KEY`, `CATGROUPS`) SELECT tmp.CATENTRY_KEY,GROUP_CONCAT(tmp.CATGROUPS SEPARATOR ';') FROM(SELECT TI_CATENTRY_0.CATENTRY_KEY AS CATENTRY_KEY, TI_CATGROUP_PATH_0.CATGROUPS AS CATGROUPS FROM solrtemp1.TI_CATENTRY_0 TI_CATENTRY_0 JOIN mdmdb2.CATGPENREL CATGPENREL ON TI_CATENTRY_0.CATENTRY_KEY=CATGPENREL.CATENTRY_KEY JOIN mdmdb2.CATGRPOPTREL CATGRPOPTREL ON CATGPENREL.CATGROUP_KEY =CATGRPOPTREL.REL_CATGROUP_KEY JOIN solrtemp1.TI_CATGROUP_PATH_0 TI_CATGROUP_PATH_0 ON CATGRPOPTREL.CATGROUP_KEY=TI_CATGROUP_PATH_0.CATGROUP_KEY WHERE TI_CATENTRY_0.CATENTTYPE_ID='spu' AND CATGPENREL.CATALOG_KEY=2 UNION ALL SELECT TI_CATENTRY_0.CATENTRY_KEY AS CATENTRY_KEY, TI_CATGROUP_PATH_0.CATGROUPS AS CATGROUPS FROM solrtemp1.TI_CATENTRY_0 TI_CATENTRY_0 JOIN mdmdb2.CATGPENREL CATGPENREL ON TI_CATENTRY_0.CATENTRY_KEY=CATGPENREL.CATENTRY_KEY JOIN solrtemp1.TI_CATGROUP_PATH_0 TI_CATGROUP_PATH_0 ON CATGPENREL.CATGROUP_KEY=TI_CATGROUP_PATH_0.CATGROUP_KEY WHERE TI_CATENTRY_0.CATENTTYPE_ID='spu'  AND CATGPENREL.CATALOG_KEY=4) tmp GROUP BY tmp.CATENTRY_KEY;")
    int insertCatgroupCatentry();

    @Insert({"INSERT INTO solrtemp1.TI_INDEX ( index_type,core_name, date) VALUES (#{map.indexType},#{map.coreName}, NOW());"})
    int insertIndex(@Param("map") Map map);

    @Select("SELECT * FROM solrtemp1.TI_INDEX WHERE core_name=#{coreName}")
    List<Map<String,String>> queryIndexByCoreName(@Param("coreName") String coreName);

    @Select("SELECT c.CATENTRY_KEY AS catentryKey FROM mdmdb2.catentry c WHERE c.CATENTRY_ID=#{catentryId} LIMIT 1")
    Map<String,Object> queryCatentryKeyById(@Param("catentryId") String catentryId );

    @Select("SELECT c.CATENT_KEY_PARENT AS spuKey,c.CATENT_KEY_CHILD AS skuKey,'sku' AS type FROM mdmdb2.catentrel c WHERE c.CATENT_KEY_CHILD=#{catentryKey} UNION SELECT c.CATENT_KEY_PARENT AS spuKey,c.CATENT_KEY_CHILD AS skuKey ,'spu' AS type FROM mdmdb2.catentrel c WHERE c.CATENT_KEY_PARENT=#{catentryKey}")
    List<Map<String,Object>> queryCatentryKeyByKey(@Param("catentryKey") String catentryKey );

    @Select("SELECT c.CATENT_KEY_PARENT AS spuKey FROM mdmdb2.catentrel c WHERE c.CATENT_KEY_CHILD=#{catentryKey}")
    Map<String,Object> querySpuKeyBySkuKey(@Param("catentryKey") String catentryKey );

    @Delete("DELETE FROM solrtemp1.TI_INDEX WHERE core_name=#{coreName}")
    int deleteIndexByCoreName(@Param("coreName") String coreName);

    @Insert("<script><foreach collection=\"listMap\" item=\"map\" index=\"index\" >INSERT IGNORE INTO solrtemp1.TI_DELTA_CATENTRY( CATENTRY_KEY, ACTION, date) VALUES (#{map.catentryKey},#{map.action},NOW()) ON duplicate KEY UPDATE ACTION=#{map.action},date=NOW();</foreach></script>")
    int batchNotifyCatentry(@Param("listMap") List<Map<String,String>> listMap);

    @Select("<script>SELECT TI_DELTA_CATENTRY.CATENTRY_KEY AS catentryKey FROM solrtemp1.TI_DELTA_CATENTRY TI_DELTA_CATENTRY <if test=\"action != null\"> WHERE TI_DELTA_CATENTRY.ACTION=#{action}</if> </script>")
    List<Map<String,String>> queryDeltaCatentryByAction(@Param("action") String action );

    @Delete("<script>DELETE FROM solrtemp1.TI_DELTA_CATENTRY WHERE CATENTRY_KEY in <foreach collection=\"listMapKey\" item=\"map\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> #{map.catentryKey} </foreach> ;</script>")
    int deleteDeltaCatentryByKey(@Param("listMapKey") List<Map<String,String>> listMapKey );

    @Insert("<script><foreach collection=\"listMap\" item=\"map\" index=\"index\" >INSERT IGNORE INTO solrtemp1.TI_DELTA_BRAND( brand_id, action, date) VALUES (#{map.brandId},#{map.action},NOW()) ON duplicate KEY UPDATE ACTION=#{map.action},date=NOW();</foreach></script>")
    int batchNotifyBrand(@Param("listMap") List<Map<String,String>> listMap);

    @Select("<script>SELECT TI_DELTA_BRAND.brand_id AS brandId FROM solrtemp1.TI_DELTA_BRAND TI_DELTA_BRAND <if test=\"action != null\"> WHERE TI_DELTA_BRAND.ACTION=#{action}</if> </script>")
    List<Map<String,String>> queryDeltaBrandByAction(@Param("action") String action );

    @Delete("<script>DELETE FROM solrtemp1.TI_DELTA_BRAND WHERE brand_id in <foreach collection=\"listMapKey\" item=\"map\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> #{map.brandId} </foreach> ;</script>")
    int deleteDeltaBrandByBrandId(@Param("listMapKey") List<Map<String,String>> listMapKey );

    @Insert("INSERT INTO solrtemp1.TI_DELTA_CATENTRY( CATENTRY_KEY, ACTION, date) SELECT i.CATENTRY_KEY,#{action} AS ACTION,NOW() AS date  FROM mdmdb2.iteminfo i JOIN mdmdb2.brand b ON b.BRAND_KEY=i.BRAND_KEY WHERE b.BRAND_ID=#{brandId}")
    int batchNotifyCatentryByBrandId(@Param("action") String action,@Param("brandId") String brandId);

}
