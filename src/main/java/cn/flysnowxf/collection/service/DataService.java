/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.DataDao;
import cn.flysnowxf.collection.entity.Data;


/**
 * 
 * <br>Create on 2016-4-21 下午9:23:47
 *
 * @author fengxuefeng
 */
@Service
public class DataService {
	@Autowired
	private DataDao dataDao;
	
	/**
	 * 根据key获取
	 * @return
	 */
	public Data getByKeyword(String keyword) {
		return dataDao.getByKeyword(keyword);
	}
	
	/**
	 * 通过key修改
	 * @param key
	 * @param value
	 */
	public void updateByKeyword(String keyword, String value) {
		Data data = new Data();
		data.setKeyword(keyword);
		data.setValue(value);
		
		dataDao.updateByKeyword(data);
	}
}
