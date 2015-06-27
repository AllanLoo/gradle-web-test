
package com.isp.common.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import com.isp.common.config.Global;
import com.isp.common.utils.StringUtils;
import com.isp.common.web.bean.Page;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;



/**
 * Entity支持类
 * @author allan
 * @version 2015-06-22
 */

public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 记录状态（0：正常；1：删除；）
	 */
	public static final String REC_STATUS_NORMAL = "0";
	public static final String REC_STATUS_DELETE = "1";
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;
	
	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;
	
	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
	protected Map<String, String> sqlMap;
	
	/**
	 * 是否自动生成主键（默认：false）
	 * 设置为true后主键由java代码生成主键。
	 */
	protected boolean isGeneratePrimaryKey = false;

	public BaseEntity() {
		
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}
	
	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}


	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = new HashMap<String,String>();
		}
		return sqlMap;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
	
	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();
	
	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();

	/**
	 * 是否自动生成主键（默认：false）
	 * 设置为true后主键由java代码生成主键。
	 */
	public boolean getIsGeneratePrimaryKey() {
        return isGeneratePrimaryKey;
    }

	/**
	 * 是否自动生成主键（默认：false）
	 * 设置为true后主键由java代码生成主键。
	 */
	public void setisGeneratePrimaryKey(boolean isGeneratePrimaryKey) {
		this.isGeneratePrimaryKey = isGeneratePrimaryKey;
	}

	/**
	 * 全局变量对象
	 */
	public Global getGlobal() {
		return Global.getInstance();
	}
	
	/**
	 * 获取数据库名称
	 */
	public String getDbName(){
		return Global.getConfig("jdbc.type");
	}
	
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
