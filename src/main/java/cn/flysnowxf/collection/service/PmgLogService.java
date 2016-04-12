/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.PmgLogDao;
import cn.flysnowxf.collection.dto.PmgLogRequest;
import cn.flysnowxf.collection.entity.PmgLog;
import cn.flysnowxf.collection.utils.QueryUtils;

/**
 * 
 * <br>Create on 2016-4-12 下午10:16:58
 *
 * @author fengxuefeng
 */
@Service
public class PmgLogService {
	@Autowired
	private PmgLogDao pmgLogDao;
	
	public int queryCount(Map<String, Object> param) {
		return pmgLogDao.queryCount(param);
	}
	
	public List<PmgLog> queryList(PmgLogRequest pmgLogRequest) {
		Map<String, Object> param = QueryUtils.getQueryMap(pmgLogRequest);
		List<PmgLog> list = pmgLogDao.query(param);

		return list;
	}
	
	public void add(PmgLog pmgLog) {
		pmgLogDao.add(pmgLog);
	}
	
	public void update(PmgLog pmgLog) {
		pmgLogDao.update(pmgLog);
	}
}
