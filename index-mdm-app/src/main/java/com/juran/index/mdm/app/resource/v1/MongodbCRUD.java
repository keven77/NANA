package com.juran.index.mdm.app.resource.v1;

import com.mongodb.*;

import java.util.Set;

public class MongodbCRUD {
    private static Mongo m = null;
    private static DB db = null;

    //数据集合名称
    private static final String COLLECTION_NAME = "d3Case";

    /**
     * 数据读取
     */
    private static void readColData(){
        DBCollection dbCol = db.getCollection(COLLECTION_NAME);
        DBCursor ret = dbCol.find();
        System.out.printf(String.valueOf(ret.count()));

        System.out.println("从mongonDB数据集中读取数据：");
        while(ret.hasNext()){
            BasicDBObject bdbObj = (BasicDBObject) ret.next();
            if(bdbObj != null){
                System.out.println("assetId:"+bdbObj.getString("assetId"));
                System.out.println("restroom:"+bdbObj.getString("restroom"));
                System.out.println("roomArea:"+bdbObj.getString("roomArea"));
                System.out.println("communityName:"+bdbObj.getString("assetId"));
                System.out.println("roomType:"+bdbObj.getString("restroom"));
                System.out.println("bedroom:"+bdbObj.getString("roomArea"));
                System.out.println("projectStyle:"+bdbObj.getString("assetId"));
                System.out.println("createDate:"+bdbObj.getString("restroom"));
                System.out.println("originalAvatar:"+bdbObj.getString("roomArea"));
                System.out.println("thumbnailMainPath:"+bdbObj.getString("roomArea"));
            }
        }
    }

    /**
     * 获取mongodb数据库连接
     */
    private static void startMongoDBConn(){
        try{
            // 连接到 mongodb 服务
            m = new Mongo("52.80.22.207", 27017);
            //根据mongodb数据库的名称获取mongodb对象 ,
            db = m.getDB( "design" );
            Set<String> collectionNames = db.getCollectionNames();
            // 打印出test中的集合
            for (String name : collectionNames) {
                System.out.println("collectionName==="+name);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startMongoDBConn();
        readColData();
    }
}
