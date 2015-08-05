
package com.isp.common.service;

import java.util.List;
import java.util.Map;

import com.isp.common.persistence.CrudDao;
import com.isp.common.persistence.DataEntity;
import com.isp.common.utils.StringUtils;
import com.isp.common.web.bean.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Service基类
 * @author allan
 * @version 2015-06-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> {
	
	/**
	 * 持久层对象
	 */
	@Resource
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	public List<T> findList(Map<String,Object> params) {
         return dao.findList(params);
	}

	/**
	 * 查询所有数据
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity) {
		return dao.findAllList(entity);
	}

	public List<T> findAllList(Map<String,Object> params) {
		return dao.findAllList(params);
	}

	public List<T> findAllList() {
		return dao.findAllList();
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setDatas(dao.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
