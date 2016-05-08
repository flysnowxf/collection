/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.flysnowxf.collection.constant.CacheConstants;
import cn.flysnowxf.collection.dto.UtilsRequest;
import cn.flysnowxf.collection.entity.Block;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.service.BlockService;
import cn.flysnowxf.collection.service.LookupPmgService;
import cn.flysnowxf.collection.service.PmgService;

/**
 * 
 * <br>Create on 2016-4-21 下午9:46:48
 *
 * @author fengxuefeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/utils")
public class UtilsAction extends BaseAction {
	@Autowired
	private LookupPmgService lookupPmgService;
	@Autowired
	private PmgService pmgService;
	@Autowired
	private BlockService blockService;
	
	@RequestMapping("/lookupPmg")
	public String lookupPmg(String noteIds) {
		lookupPmgService.lookupPmg(noteIds);
		
		return "lookupPmg";
	}
	
	@RequestMapping("/queryBlock")
	public void queryBlock(UtilsRequest utilsRequest, HttpServletResponse response) {
		List<Block> blockList = blockService.get(
				utilsRequest.getNoteId(),
				utilsRequest.getBlockA(),
				utilsRequest.getBlockB(),
				utilsRequest.getBlockC());
		
		String result = "";
		if (CollectionUtils.isNotEmpty(blockList)) {
			for (Block block : blockList) {
				result += block.getName() + " ";
			}
		}
		else {
			List<Pmg> pmgList = pmgService.queryList(
					utilsRequest.getNoteId(),
					utilsRequest.getBlockA(),
					utilsRequest.getBlockB(),
					utilsRequest.getBlockC());
			for (Pmg pmg : pmgList) {
				result += pmg.getName() + " ";
			}
		}
		
		writerPrint(response, result);
	}
	
	@RequestMapping("/clearCache")
	public String clearCache() {
		memcachedClient.delete(CacheConstants.INDEX_PMGLISTMAP_KEY);
		memcachedClient.delete(CacheConstants.INDEX_TITLE_KEY);
		memcachedClient.delete(CacheConstants.YIBAN_PMGLISTMAP_KEY);
		memcachedClient.delete(CacheConstants.YIBAN_TITLE_KEY);
		
		return "clearCache";
	}
}
