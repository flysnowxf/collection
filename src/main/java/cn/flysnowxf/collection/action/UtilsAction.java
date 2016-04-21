/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.flysnowxf.collection.service.LookupPmgService;

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
	
	@RequestMapping("/lookupPmg")
	public String lookupPmg() {
		lookupPmgService.lookupPmg();
		
		return "lookupPmg";
	}
}
