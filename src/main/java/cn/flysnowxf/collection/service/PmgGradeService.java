/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.PmgGradeDao;
import cn.flysnowxf.collection.dto.PmgGradeRequest;
import cn.flysnowxf.collection.entity.PmgGrade;
import cn.flysnowxf.collection.utils.QueryUtils;

/**
 * 
 * <br>Create on 2016-4-12 下午9:23:40
 *
 * @author fengxuefeng
 */
@Service
public class PmgGradeService {
	@Autowired
	private PmgGradeDao pmgGradeDao;
	
	public int queryCount(Map<String, Object> param) {
		return pmgGradeDao.queryCount(param);
	}
	
	public List<PmgGrade> queryList(PmgGradeRequest pmgGradeRequest) {
		Map<String, Object> param = QueryUtils.getQueryMap(pmgGradeRequest);
		List<PmgGrade> list = pmgGradeDao.query(param);

		return list;
	}
	
	public void add(PmgGrade pmgGrade) {
		pmgGradeDao.add(pmgGrade);
	}
	
	public void update(PmgGrade pmgGrade) {
		pmgGradeDao.update(pmgGrade);
	}
}
