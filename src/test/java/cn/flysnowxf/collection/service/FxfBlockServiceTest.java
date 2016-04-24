/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.flysnowxf.collection.entity.Block;
import cn.flysnowxf.collection.test.BaseTest;

/**
 * 
 * <br>Create on 2016-4-23 下午6:38:02
 *
 * @author fengxuefeng
 */
public class FxfBlockServiceTest extends BaseTest {
	@Autowired
	private BlockService blockService;
	
	@Test
	public void get() {
		Integer noteId = 1;
		String blockNum = "123";
		List<Block> listBlock = blockService.get(noteId, blockNum);
		for (Block block : listBlock) {
			System.out.println(block.getName());
		}
	}
}
