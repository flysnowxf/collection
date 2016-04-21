/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dao;

import cn.flysnowxf.collection.entity.Data;

/**
 * 
 * <br>Create on 2016-4-12 下午9:21:52
 *
 * @author fengxuefeng
 */
public interface DataDao extends GenericDao<Data> {
	/**
	 * 根据key获取
	 * @return
	 */
	public Data getByKeyword(String keyword);
	
	/**
	 * 通过key修改
	 * @param key
	 * @param value
	 */
	public void updateByKeyword(Data data);
}
