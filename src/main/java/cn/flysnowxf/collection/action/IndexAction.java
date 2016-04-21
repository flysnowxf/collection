/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.KeyValueDto;
import cn.flysnowxf.collection.dto.NoteRequest;
import cn.flysnowxf.collection.dto.PmgGradeRequest;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.dto.QueryOrder;
import cn.flysnowxf.collection.dto.QueryOrderType;
import cn.flysnowxf.collection.entity.Note;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.entity.PmgGrade;
import cn.flysnowxf.collection.service.DataService;
import cn.flysnowxf.collection.service.NoteService;
import cn.flysnowxf.collection.service.PmgGradeService;
import cn.flysnowxf.collection.service.PmgLogService;
import cn.flysnowxf.collection.service.PmgService;

/**
 * 
 * <br>Create on 2016-4-14 上午12:35:39
 *
 * @author fengxuefeng
 */
@Controller
@Scope("prototype")
public class IndexAction extends BaseAction {
	@Autowired
	private PmgService pmgService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private PmgGradeService pmgGradeService;
	@Autowired
	private PmgLogService pmgLogService;
	@Autowired
	private DataService dataService;
	
	private static final int DISPLAY_NUM = 64;
	private List<String> DISPLAY_TITLE_LIST = new ArrayList<String>();
	
	@RequestMapping("/")
	public String index(Model model) {
		BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
		LinkedHashMap<String, List<Pmg>> pmgListMap = new LinkedHashMap<String, List<Pmg>>();
		
		NoteRequest noteRequest = new NoteRequest();
		noteRequest.setPageSize(Integer.MAX_VALUE);
		List<Note> noteList = noteService.queryList(noteRequest);
		
		List<Integer> noteIds2 = new ArrayList<Integer>();
		List<Integer> noteIds3 = new ArrayList<Integer>();
		List<Integer> noteIdsJn = new ArrayList<Integer>();
		List<Integer> noteIdsMaoJn = new ArrayList<Integer>();
		List<Integer> noteIdsHkJn = new ArrayList<Integer>();
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
			else if (note.getVersion().equals("澳门纪念钞")) {
				noteIdsMaoJn.add(note.getId());
			}
			else if (note.getVersion().equals("香港纪念钞")) {
				noteIdsHkJn.add(note.getId());
			}
		}
		
		PmgRequest pmgRequest = new PmgRequest();
		pmgRequest.setPageSize(Integer.MAX_VALUE);
		List<QueryOrder> queryOrderList = new ArrayList<QueryOrder>();
		queryOrderList.add(new QueryOrder("id", QueryOrderType.ASC));
		pmgRequest.setQueryOrderList(queryOrderList);
		
		pmgRequest.setNoteIds(noteIds3);
		List<Pmg> pmgList3 = pmgService.queryList(pmgRequest);
		packageData(pmgList3);
		pmgListMap.put("第三版", pmgList3);
		
		pmgRequest.setNoteIds(noteIds2);
		List<Pmg> pmgList2 = pmgService.queryList(pmgRequest);
		packageData(pmgList2);
		pmgListMap.put("第二版", pmgList2);
		
		pmgRequest.setNoteIds(noteIdsJn);
		List<Pmg> pmgListJn = pmgService.queryList(pmgRequest);
		packageData(pmgListJn);
		pmgListMap.put("纪念钞", pmgListJn);
		
		pmgRequest.setNoteIds(noteIdsMaoJn);
		List<Pmg> pmgListMaoJn = pmgService.queryList(pmgRequest);
		packageData(pmgListMaoJn, "澳门");
		pmgListMap.put("澳门纪念钞", pmgListMaoJn);
		
		pmgRequest.setNoteIds(noteIdsHkJn);
		List<Pmg> pmgListHkJn = pmgService.queryList(pmgRequest);
		packageData(pmgListHkJn, "香港");
		pmgListMap.put("香港纪念钞", pmgListHkJn);
		
		// 更新时间
		String updateDate = dataService.getByKeyword("updateDate").getValue();
		
		// return
		model.addAttribute("pmgListMap", pmgListMap);
		model.addAttribute("titleList", DISPLAY_TITLE_LIST);
		model.addAttribute("updateDate", updateDate);
    	
		return "index";
    }
	
	private void packageKeyValue(Pmg pmg) {
		List<KeyValueDto> kvList = new ArrayList<KeyValueDto>();
		
		// 高分难度
		String highTitle = "高分难度";
		kvList.add(new KeyValueDto(highTitle, String.valueOf(getRatioStar(pmg.getHighScoreRatio()))));
		if (!DISPLAY_TITLE_LIST.contains(highTitle)) {
			DISPLAY_TITLE_LIST.add(highTitle);
		}
		
		// 新增
		// 本月新增
//		String monthTitle = "本月新增";
//		int monthNew = 0;
//		Date now = new Date();
//		String nowMonth = monthSdf.format(now);
//		PmgLogRequest pmgLogRequest = new PmgLogRequest();
//		pmgLogRequest.setMonth(nowMonth);
//		pmgLogRequest.setPmgId(pmg.getId());
//		pmgLogRequest.setPageSize(Integer.MAX_VALUE);
//		List<PmgLog> logList = pmgLogService.queryList(pmgLogRequest);
//		// 取头尾相减
//		if (CollectionUtils.isNotEmpty(logList) && logList.size() > 1) {
//			PmgLog start = logList.get(0);
//			PmgLog end = logList.get(logList.size() - 1);
//			monthNew = end.getTotal() - start.getTotal();
//		}
//		kvList.add(new KeyValueDto(monthTitle, String.valueOf(monthNew)));
//		if (!DISPLAY_TITLE_LIST.contains(monthTitle)) {
//			DISPLAY_TITLE_LIST.add(monthTitle);
//		}
		
		pmg.setKeyValueList(kvList);
	}
	
	private void packageData(List<Pmg> pmgList) {
		packageData(pmgList, null);
	}
	
	private void packageData(List<Pmg> pmgList, String country) {
		for (Pmg pmg : pmgList) {
			Integer pmgId = pmg.getId();
			
			PmgGradeRequest request = new PmgGradeRequest();
			request.setPmgId(pmgId);
			request.setPageSize(Integer.MAX_VALUE);
			List<QueryOrder> queryOrderList = new ArrayList<QueryOrder>();
			queryOrderList.add(new QueryOrder("id", QueryOrderType.ASC));
			request.setQueryOrderList(queryOrderList);
			List<PmgGrade> gradeList = pmgGradeService.queryList(request);
			
			int highCount = 0;
			List<GradeCount> countList = new ArrayList<GradeCount>();
			for (PmgGrade pmgGrade : gradeList) {
				if (isDisplay(pmgGrade)) {
					// 使用简写E
					String grade = pmgGrade.getGrade().replaceAll(" EPQ", "E");
					countList.add(new GradeCount(grade, pmgGrade.getCount(),
							pmgGrade.getPrice().intValue()));
				}
				
				// 67以上为高分
				if (getGradeNum(pmgGrade) >= 67) {
					highCount += pmgGrade.getCount();
				}
			}
			pmg.setGradeCountList(countList);
			
			// 高分比例
			int ratio = (int)(highCount * 100.0f / pmg.getTotal());
			pmg.setHighScoreRatio(ratio);
			
			packageKeyValue(pmg);
		}
		
		packagePmgList(pmgList, country);
	}
	
	private void packagePmgList(List<Pmg> pmgList, String country) {
		List<Pmg> result = new ArrayList<Pmg>();
		
		if (StringUtils.isNotBlank(country)) {
			LinkedHashMap<String, List<Pmg>> nameListMap = new LinkedHashMap<String, List<Pmg>>();
			for (Pmg pmg : pmgList) {
				String name = pmg.getName();
				if (!nameListMap.containsKey(name)) {
					nameListMap.put(name, new ArrayList<Pmg>());
				}
				nameListMap.get(name).add(pmg);
			}
			
			for (Map.Entry<String, List<Pmg>> entry : nameListMap.entrySet()) {
				int count = entry.getValue().size();
				Pmg newPmg = new Pmg();
				Pmg oldPmg = entry.getValue().get(0);
				try {
					BeanUtils.copyProperties(newPmg, oldPmg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// 总数
				int total = 0;
				// 高分比例
				int highScoreRatio = 0;
				// 评分
				LinkedHashMap<String, Integer> gradeCountMap = new LinkedHashMap<String, Integer>();
				for (Pmg pmg : entry.getValue()) {
					List<GradeCount> gradeCountList = pmg.getGradeCountList();
					for (GradeCount gradeCount : gradeCountList) {
						String grade = gradeCount.getGrade();
						if (!gradeCountMap.containsKey(grade)) {
							gradeCountMap.put(grade, 0);
						}
						gradeCountMap.put(grade, gradeCountMap.get(grade) + gradeCount.getCount());
					}
					
					// 总数累加
					total += pmg.getTotal();
					// 高分比例累加
					highScoreRatio += pmg.getHighScoreRatio();
				}
				
				List<GradeCount> countList = new ArrayList<GradeCount>();
				for (Map.Entry<String, Integer> countEntry : gradeCountMap.entrySet()) {
					countList.add(new GradeCount(countEntry.getKey(), countEntry.getValue()));
				}
				
				String catalogText = "";
				if (count > 1) {
					catalogText = "等";
				}
				newPmg.setCatalog(newPmg.getCatalog() + catalogText);
				newPmg.setTotal(total);
				newPmg.setHighScoreRatio(highScoreRatio / count);
				newPmg.setGradeCountList(countList);
				result.add(newPmg);
			}
		}
		
		if (CollectionUtils.isNotEmpty(result)) {
			pmgList.clear();
			pmgList.addAll(result);
		}
	}
	
	private boolean isDisplay(PmgGrade pmgGrade) {
		try {
			if (getGradeNum(pmgGrade) >= DISPLAY_NUM) {
				// 使用简写E
				String grade = pmgGrade.getGrade().replaceAll(" EPQ", "E");
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
	
	private int getGradeNum(PmgGrade pmgGrade) {
		String grade = pmgGrade.getGrade();
		String num = grade;
		if (grade.contains(" ")) {
			num = grade.split(" ")[0];
		}
		
		return Integer.valueOf(num);
	}
	
	/**
	 * 根据高分比例进行排分
	 * @param ratio
	 * @return
	 */
	private int getRatioStar(int ratio) {
		int star = 1;
		
		if (ratio <= 10) {
			star = 5;
		}
		else if (ratio > 10 && ratio <= 30) {
			star = 4;
		}
		else if (ratio > 31 && ratio <= 45) {
			star = 3;
		}
		else if (ratio > 45 && ratio <= 60) {
			star = 2;
		}
		
		return star;
	}
}
