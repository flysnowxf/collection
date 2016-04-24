/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.BlockDao;
import cn.flysnowxf.collection.dto.BlockRequest;
import cn.flysnowxf.collection.entity.Block;
import cn.flysnowxf.collection.utils.NumberUtils;
import cn.flysnowxf.collection.utils.QueryUtils;

/**
 * 
 * <br>Create on 2016-4-23 下午6:22:13
 *
 * @author fengxuefeng
 */
@Service
public class BlockService {
	@Autowired
	private BlockDao blockDao;
	
	public List<Block> get(Integer noteId, String a, String b, String c) {
		return get(noteId, NumberUtils.getAlaboNumber(a, b, c));
	}
	
	/**
	 * 根据品种和冠号获取冠号分类
	 * @param noteId
	 * @param number
	 * @return
	 */
	public List<Block> get(Integer noteId, String blockNum) {
		List<Block> result = new ArrayList<Block>();
		
		if (noteId != null && StringUtils.isNotBlank(blockNum)) {
			BlockRequest blockRequest = new BlockRequest();
			blockRequest.setNoteId(noteId);
			List<Block> list = queryList(blockRequest);
			
			if (CollectionUtils.isNotEmpty(list)) {
				// 遍历所有
				for (Block block : list) {
					String value = block.getValue();
					String[] values = value.split(",");
					// 是否排列组合
					if (block.getIsGroup() == 1) {
						value = NumberUtils.getGroup(Arrays.asList(values));
					}
					
					// 匹配
					for (String v : value.split(",")) {
						if (blockNum.equals(v)) {
							result.add(block);
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public List<Block> queryList(BlockRequest blockRequest) {
		Map<String, Object> param = QueryUtils.getQueryMap(blockRequest);
		List<Block> list = blockDao.query(param);

		return list;
	}
	
	public Block get(Integer id) {
		return blockDao.get(id);
	}
}
