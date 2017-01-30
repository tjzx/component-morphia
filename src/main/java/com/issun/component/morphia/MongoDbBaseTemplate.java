package com.issun.component.morphia;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.issun.component.morphia.bean.BeanEntity;
import com.issun.component.morphia.pool.DatastorePool;
import com.mongodb.DB;
import com.mongodb.WriteResult;

/**
 * MongoDb CURD基础模板
 * @param <T>
 * 
 * @author ZHe
 */
public abstract class MongoDbBaseTemplate<T extends BeanEntity> {
	
	
    // ------------------------------------------------------- Static Variables
	
	/**
	 * 默认主键名称
	 */
	private static final String DEFAULT_FIELD_UNID = "_id";
	
    // ------------------------------------------------------- Instance Variables
	
	/**
	 * 存储对象的字节码
	 */
	private Class<T> beanClass;
	
	/**
	 * MongoDb的“连接”
	 */
	private Datastore datastore;
	
	
    // ------------------------------------------------------- Protected Methods
	
	
	protected Class<T> getBeanClass() {
		return beanClass;
	}

	protected void setBeanClass(Class<T> beanClass) {
		this.beanClass = beanClass;
	}
	
	protected Datastore getDatastore() {
		return datastore;
	}

	protected void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	
	/**
	 * 进行Mongo数据库的初始化(默认使用配置文件进行初始化)
	 * @param datastoreAlias
	 */
	protected void initDatastore(String datastoreAlias){
		DatastorePool mongoDBPool = DatastorePool.getInstance();
		this.datastore = mongoDBPool.getDatastore(datastoreAlias);
	}
	
	
	/**
	 * 通用保存
	 * @param beanObj
	 * @return true/false
	 */
	protected boolean commonSave(T beanObj){
		boolean result = true;
		
		Key<T> key = this.datastore.save(beanObj);
		if(null == key || null == key.getId()){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 通用的删除
	 * @param unid
	 * @return true/false
	 */
	protected boolean commonDelete(String unid) {
		if(StringUtils.isEmpty(unid)){
			return false;
		}
		
		Query<T> query = datastore.createQuery(beanClass);
		query.field(DEFAULT_FIELD_UNID).equal(unid);
		WriteResult writeResult = datastore.delete(query);
		
		if(writeResult.getN() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 通用获取
	 * @param unid
	 * @return T
	 */
	protected T commonGet(String unid) {
		if(StringUtils.isEmpty(unid)){
			return null;
		}
		Query<T> query = datastore.createQuery(beanClass);
		query.field(DEFAULT_FIELD_UNID).equal(unid);
		
		return query.get();
	}
	
	/**
	 * 通用更新
	 * @param beanObj
	 * @return true/false
	 */
	protected boolean commonUpdate(T beanObj) {
		
		UpdateOperations<T> updateOperations = this.buildUpdateOperation(beanObj);
		
		Query<T> query = datastore.createQuery(beanClass);
		query.field(DEFAULT_FIELD_UNID).equal(beanObj.getUnid());
		UpdateResults updateResults = this.datastore.update(query, updateOperations);
		
		return (updateResults.getUpdatedCount() > 0);
	}

	
    // ------------------------------------------------------- Private Methods
	
	/**
	 * 构造更新域(field/Val列表)
	 * @param beanObj
	 * @return updateOperations
	 */
	private UpdateOperations<T> buildUpdateOperation(T beanObj) {
		
		UpdateOperations<T> updateOperations = datastore.createUpdateOperations(beanClass);
		
		JSONObject jsonObject = JSONObject.fromObject(beanObj);
		buildUpdateParam(updateOperations,jsonObject);
		
		return updateOperations;
	}

	/**
	 * 将jsonObject最外层的keys(排除‘unid’)和vals赋值到updateOperations
	 * @param updateOperations
	 * @param jsonObject
	 */
	private void buildUpdateParam(UpdateOperations<T> updateOperations,
			JSONObject jsonObject) {
		
		if(!jsonObject.isEmpty()){
			Iterator itera = jsonObject.keys();
			while(itera.hasNext()){
				String fieldName = (String)itera.next();
				Object fieldValue = jsonObject.get(fieldName);
				
				if(BeanEntity.UNID_FIELD.equalsIgnoreCase(fieldName)){
					continue;
				}
				
				if(fieldValue instanceof JSONNull){
					continue;
				}
				
				if(fieldValue instanceof JSONObject){
					buildUpdateParam(updateOperations, (JSONObject)fieldValue);
				}else if(fieldValue instanceof JSONArray){
					JSONArray jsonArray = (JSONArray)fieldValue;
					Iterator arrItera = jsonArray.iterator();
					while(arrItera.hasNext()){
						JSONObject itemJsonObject = (JSONObject) arrItera.next();
						buildUpdateParam(updateOperations, itemJsonObject);
					}
				}else {
					updateOperations.set(fieldName, String.valueOf(fieldValue));
				}
			}
		}
		
	}
	
	/**
	 * 判断collection是否存在
	 * @param tableName
	 * @return Boolean
	 */
	protected boolean collectionExistCheck(String tableName){
		
		DB db = this.datastore.getDB();
		
		if(null == db){
			return false;
		}
		
		return db.collectionExists(tableName);
	}
	
}
