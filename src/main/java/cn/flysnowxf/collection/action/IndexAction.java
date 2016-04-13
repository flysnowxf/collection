/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * <br>Create on 2016-4-14 上午12:35:39
 *
 * @author fengxuefeng
 */
@Controller
@RequestMapping("")
public class IndexAction {
	
	@RequestMapping("")
    public String index() {
		return "index";
    }
}
