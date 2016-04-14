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
public class IndexAction {
	@Autowired
	private PmgService pmgService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private PmgGradeService pmgGradeService;
	
	private List<Pmg> pmgList2;
	private List<Pmg> pmgList3;
	private List<Pmg> pmgListJn;
	
	@RequestMapping("/")
    public String index(Model model) {
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
		
		pmgRequest.setNoteIds(noteIds2);
    	pmgList2 = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgList2);
    	
    	pmgRequest.setNoteIds(noteIds3);
    	pmgList3 = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgList3);
    	
    	pmgRequest.setNoteIds(noteIdsJn);
    	pmgListJn = pmgService.queryList(pmgRequest);
    	packageGradeCount(pmgListJn);
		
    	model.addAttribute("pmgList2", pmgList2);
    	model.addAttribute("pmgList3", pmgList3);
    	model.addAttribute("pmgListJn", pmgListJn);
    	
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
				countList.add(new GradeCount(pmgGrade.getGrade(), pmgGrade.getCount()));
			}
			
			pmg.setGradeCountList(countList);
		}
	}
}
