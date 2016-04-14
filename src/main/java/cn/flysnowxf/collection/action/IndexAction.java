/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.service.PmgService;

/**
 * 
 * <br>Create on 2016-4-14 上午12:35:39
 *
 * @author fengxuefeng
 */
@Controller
@RequestMapping("")
public class IndexAction {
	@Autowired
	private PmgService pmgService;
	
	private List<Pmg> pmgList;
	private List<Pmg> pmgList2;
	private List<Pmg> pmgList3;
	private List<Pmg> pmgListJn;
	
	@RequestMapping("")
    public String index(Model model) {
    	PmgRequest pmgRequest = new PmgRequest();
    	pmgRequest.setPageSize(Integer.MAX_VALUE);
    	pmgList = pmgService.queryList(pmgRequest);
    	
    	model.addAttribute("pmgList", pmgList);
    	model.addAttribute("pmgList2", pmgList2);
    	model.addAttribute("pmgList3", pmgList3);
    	model.addAttribute("pmgListJn", pmgListJn);
    	model.addAttribute("text", "123");
    	
		return "index";
    }
}
