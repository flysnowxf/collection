/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.NoteRequest;
import cn.flysnowxf.collection.dto.PmgGradeRequest;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.dto.QueryOrder;
import cn.flysnowxf.collection.dto.QueryOrderType;
import cn.flysnowxf.collection.entity.Note;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.entity.PmgGrade;
import cn.flysnowxf.collection.service.NoteService;
import cn.flysnowxf.collection.service.PmgGradeService;
import cn.flysnowxf.collection.service.PmgService;

/**
 * 
 * <br>Create on 2016-4-14 上午12:35:39
 *
 * @author fengxuefeng
 */
@Controller
public class IndexAction extends BaseAction {
	@Autowired
	private PmgService pmgService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private PmgGradeService pmgGradeService;
	
	private static final int DISPLAY_NUM = 64;
	private static final List<String> DISPLAY_TITLE_LIST = new ArrayList<String>();
	
	@RequestMapping("/")
    public String index(Model model) {
		LinkedHashMap<String, List<Pmg>> pmgListMap = new LinkedHashMap<String, List<Pmg>>();
		
		NoteRequest noteRequest = new NoteRequest();
		noteRequest.setPageSize(Integer.MAX_VALUE);
		List<Note> noteList = noteService.queryList(noteRequest);
		
		List<Integer> noteIds2 = new ArrayList<Integer>();
		List<Integer> noteIds3 = new ArrayList<Integer>();
		List<Integer> noteIdsJn = new ArrayList<Integer>();
		for (Note note : noteList) {
			if (note.getVersion().equals("第二版")) {
				noteIds2.add(note.getId());
			}
			else if (note.getVersion().equals("第三版")) {
				noteIds3.add(note.getId());
			}
			else if (note.getVersion().equals("纪念钞")) {
				noteIdsJn.add(note.getId());
			}
		}
		
		PmgRequest pmgRequest = new PmgRequest();
		pmgRequest.setPageSize(Integer.MAX_VALUE);
		List<QueryOrder> queryOrderList = new ArrayList<QueryOrder>();
		queryOrderList.add(new QueryOrder("id", QueryOrderType.ASC));
		pmgRequest.setQueryOrderList(queryOrderList);
		
		pmgRequest.setNoteIds(noteIds3);
    	List<Pmg> pmgList3 = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgList3);
    	pmgListMap.put("第三版", pmgList3);
		
		pmgRequest.setNoteIds(noteIds2);
		List<Pmg> pmgList2 = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgList2);
    	pmgListMap.put("第二版", pmgList2);
    	
    	pmgRequest.setNoteIds(noteIdsJn);
    	List<Pmg> pmgListJn = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgListJn);
    	pmgListMap.put("纪念钞", pmgListJn);
    	
    	// 更新时间为昨天
    	String updateDate = addDays(-1);

    	// return
    	model.addAttribute("pmgListMap", pmgListMap);
    	model.addAttribute("titleList", DISPLAY_TITLE_LIST);
    	model.addAttribute("updateDate", updateDate);
    	
		return "index";
    }
	
	private void packageGradeCount(List<Pmg> pmgList) {
		for (Pmg pmg : pmgList) {
			Integer pmgId = pmg.getId();
			
			PmgGradeRequest request = new PmgGradeRequest();
			request.setPmgId(pmgId);
			request.setPageSize(Integer.MAX_VALUE);
			List<QueryOrder> queryOrderList = new ArrayList<QueryOrder>();
			queryOrderList.add(new QueryOrder("id", QueryOrderType.ASC));
			request.setQueryOrderList(queryOrderList);
			List<PmgGrade> gradeList = pmgGradeService.queryList(request);
			
			List<GradeCount> countList = new ArrayList<GradeCount>();
			for (PmgGrade pmgGrade : gradeList) {
				if (isDisplay(pmgGrade)) {
					countList.add(new GradeCount(pmgGrade.getGrade(), pmgGrade.getCount(),
							pmgGrade.getPrice().intValue()));
				}
			}
			
			pmg.setGradeCountList(countList);
		}
	}
	
	private boolean isDisplay(PmgGrade pmgGrade) {
		try {
			String grade = pmgGrade.getGrade();
			String num = grade;
			if (grade.contains(" ")) {
				num = grade.split(" ")[0];
			}
			
			if (Integer.valueOf(num) >= DISPLAY_NUM) {
				if (!DISPLAY_TITLE_LIST.contains(grade)) {
					DISPLAY_TITLE_LIST.add(grade);
				}
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String addDays(int amount) {
		return sdf.format(DateUtils.addDays(new Date(), amount));
	}
}
