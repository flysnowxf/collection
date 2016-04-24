/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.PmgDao;
import cn.flysnowxf.collection.dto.BlockRequest;
import cn.flysnowxf.collection.dto.PageVo;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.entity.Block;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.utils.NumberUtils;
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
	@Autowired
	private BlockService blockService;
	
	/**
	 * 冠号拆为a/b/c3个数字
	 * @param noteId
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public List<Pmg> queryList(Integer noteId, 
			String a, String b, String c) {
		return queryList(noteId, NumberUtils.getAlaboNumber(a, b, c));
	}
	
	/**
	 * 根据品种和冠号获取pmg分类
	 * @param noteId
	 * @param blockNum
	 * @return
	 */
	public List<Pmg> queryList(Integer noteId, String blockNum) {
		List<Pmg> result = new ArrayList<Pmg>();
		
		if (noteId != null && StringUtils.isNotBlank(blockNum)) {
			List<Block> blockList = blockService.get(noteId, blockNum);
			if (CollectionUtils.isNotEmpty(blockList)) {
				for (Block b : blockList) {
					Pmg pmg = get(b.getPmgId());
					result.add(pmg);
					
					// 特殊判断红五元
					if (pmg.getCatalog().equals("869a")) {
						// 添加黄五元
						PmgRequest pmgRequest = new PmgRequest();
						pmgRequest.setCatalog("872");
						List<Pmg> list = queryList(pmgRequest);
						result.add(list.get(0));
					}
				}
			}
			// 查询其他分类
			else {
				// 查询不到的pmg
				BlockRequest blockRequest = new BlockRequest();
				blockRequest.setPageSize(Integer.MAX_VALUE);
				blockRequest.setNoteId(noteId);
				blockList = blockService.queryList(blockRequest);
				List<Integer> noQueryIdList = new ArrayList<Integer>();
				for (Block b : blockList) {
					// 特殊判断碳黑和拾光十元
					Pmg pmg = get(b.getPmgId());
					if (!pmg.getCatalog().equals("876a1")
							&& !pmg.getCatalog().equals("879b")) {
						noQueryIdList.add(b.getPmgId());
					}
				}
				
				// 所有的pmg
				PmgRequest pmgRequest = new PmgRequest();
				pmgRequest.setNoteId(noteId);
				pmgRequest.setPageSize(Integer.MAX_VALUE);
				List<Pmg> pmgList = queryList(pmgRequest);
				
				Map<Integer, Pmg> pmgMap = new HashMap<Integer, Pmg>();
				int blockCount = blockNum.length();
				List<Integer> allIdList = new ArrayList<Integer>();
				for (Pmg pmg : pmgList) {
					// 对比冠号几罗
					if (pmg.getBlockCount() == 0 || 
							pmg.getBlockCount() == blockCount) {
						pmgMap.put(pmg.getId(), pmg);
						allIdList.add(pmg.getId());
					}
				}
				
				// 剩下的pmg
				allIdList.removeAll(noQueryIdList);
				for (Integer id : allIdList) {
					result.add(pmgMap.get(id));
				}
			}
		}
		
		return result;
	}
	
	public Pmg get(Integer id) {
		return pmgDao.get(id);
	}
	
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
