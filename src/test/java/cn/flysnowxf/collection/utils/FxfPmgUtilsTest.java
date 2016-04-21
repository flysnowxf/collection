/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.PmgData;
import cn.flysnowxf.collection.service.DataService;
import cn.flysnowxf.collection.test.BaseTest;

/**
 * 
 * <br>Create on 2016-4-9 下午2:20:25
 *
 * @author fengxuefeng
 */
public class FxfPmgUtilsTest extends BaseTest {
	@Autowired
	private DataService dataService;
	
	@Test
	public void lookup() throws Exception {
		String sessionId = dataService.getByKeyword("sessionId").getValue();
		String auth = dataService.getByKeyword("auth").getValue();
		PmgUtils.setSession(sessionId, auth);
		
		String country = "中国";
		String catalog = "877a";
		PmgData data = PmgUtils.lookup(country, catalog);
		// 打印
		for (GradeCount gradeCount : data.getGradeCountList()) {
			System.out.println(gradeCount.getGrade() + "\t" + gradeCount.getCount());
		}
		
		catalog = "877b";
		data = PmgUtils.lookup(country, catalog);
		// 打印
		for (GradeCount gradeCount : data.getGradeCountList()) {
			System.out.println(gradeCount.getGrade() + "\t" + gradeCount.getCount());
		}
	}
}