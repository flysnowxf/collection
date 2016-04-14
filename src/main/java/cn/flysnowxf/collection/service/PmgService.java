/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.PmgDao;
import cn.flysnowxf.collection.dto.PageVo;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.utils.QueryUtils;

/**
 * 
 * <br>Create on 2016-4-12 下午10:16:49
 *
 * @author fengxuefeng
 */
@Service
public class PmgService {
	@Autowired
	private PmgDao pmgDao;
	
	public PageVo<Pmg> query(PmgRequest pmgRequest) {
		List<Pmg> userList = queryList(pmgRequest);
		
		Map<String, Object> param = QueryUtils.getQueryMap(pmgRequest);
		packageListParam(param, pmgRequest);
		int count = queryCount(param);

		PageVo<Pmg> pv = new PageVo<Pmg>();
		pv.setData(userList);
		pv.setPageNo(pmgRequest.getPageNo());
		pv.setPageSize(pmgRequest.getPageSize());
		pv.setRecordSize(count);

		return pv;
	}
	
	public int queryCount(Map<String, Object> param) {
		return pmgDao.queryCount(param);
	}
	
	public List<Pmg> queryList(PmgRequest pmgRequest) {
		Map<String, Object> param = QueryUtils.getQueryMap(pmgRequest);
		packageListParam(param, pmgRequest);
		List<Pmg> list = pmgDao.query(param);

		return list;
	}
	
	public void add(Pmg pmg) {
		pmgDao.add(pmg);
	}
	
	public void update(Pmg pmg) {
		pmgDao.update(pmg);
	}
	
	private void packageListParam(Map<String, Object> param, PmgRequest pmgRequest) {
		param.put("noteIds", pmgRequest.getNoteIds());
	}
}
