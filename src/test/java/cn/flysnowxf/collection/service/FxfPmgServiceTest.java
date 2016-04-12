/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.test.BaseTest;

/**
 * 
 * <br>Create on 2016-4-12 下午10:20:44
 *
 * @author fengxuefeng
 */
public class FxfPmgServiceTest extends BaseTest {
	@Autowired
	private PmgService pmgService;
	
	@Test
	public void queryList() {
		PmgRequest pmgRequest = new PmgRequest();
		pmgRequest.setPageSize(Integer.MAX_VALUE);
		List<Pmg> list = pmgService.queryList(pmgRequest);
		for (Pmg pmg : list) {
			System.out.println(pmg.getName() + ":" + pmg.getCatalog());
		}
	}
}
