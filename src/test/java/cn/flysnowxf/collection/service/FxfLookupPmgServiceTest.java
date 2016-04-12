/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.flysnowxf.collection.test.BaseTest;

/**
 * 
 * <br>Create on 2016-4-12 下午11:00:58
 *
 * @author fengxuefeng
 */
public class FxfLookupPmgServiceTest extends BaseTest {
	@Autowired
	private LookupPmgService lookupPmgService;
	
	@Test
	public void lookupPmg() {
		lookupPmgService.lookupPmg();
	}
}
