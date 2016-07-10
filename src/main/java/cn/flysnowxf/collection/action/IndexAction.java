/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import cn.flysnowxf.collection.constant.CacheConstants;
import cn.flysnowxf.collection.dto.BlockDto;
import cn.flysnowxf.collection.dto.BlockGroupDto;
import cn.flysnowxf.collection.dto.BlockRequest;
import cn.flysnowxf.collection.dto.DealPrice;
import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.KeyValueDto;
import cn.flysnowxf.collection.dto.NoteRequest;
import cn.flysnowxf.collection.dto.PmgGradeRequest;
import cn.flysnowxf.collection.dto.PmgLogRequest;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.dto.QueryOrder;
import cn.flysnowxf.collection.dto.QueryOrderType;
import cn.flysnowxf.collection.entity.Block;
import cn.flysnowxf.collection.entity.Note;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.entity.PmgGrade;
import cn.flysnowxf.collection.entity.PmgLog;
import cn.flysnowxf.collection.service.BlockService;
import cn.flysnowxf.collection.service.DataService;
import cn.flysnowxf.collection.service.NoteService;
import cn.flysnowxf.collection.service.PmgGradeService;
import cn.flysnowxf.collection.service.PmgLogService;
import cn.flysnowxf.collection.service.PmgService;

import com.alibaba.fastjson.JSON;

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
	@Autowired
	private BlockService blockService;
	
	private static final int DISPLAY_NUM = 64;
	private List<String> DISPLAY_TITLE_LIST = new ArrayList<String>();
	
	@RequestMapping("/yiban")
	public String yiban(Model model) {
		LinkedHashMap<String, List<Pmg>> pmgListMap = new LinkedHashMap<String, List<Pmg>>();
		
		// 判断缓存
		Object pmgCache = memcachedClient.get(CacheConstants.YIBAN_PMGLISTMAP_KEY);
		Object titleCache = memcachedClient.get(CacheConstants.YIBAN_TITLE_KEY);
		if (pmgCache != null && titleCache != null) {
			pmgListMap = (LinkedHashMap<String, List<Pmg>>) pmgCache;
			DISPLAY_TITLE_LIST = (List<String>) titleCache;
		}
		else {
			BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
			
			NoteRequest noteRequest = new NoteRequest();
			noteRequest.setPageSize(Integer.MAX_VALUE);
			List<Note> noteList = noteService.queryList(noteRequest);
			
			List<Integer> noteIds1 = new ArrayList<Integer>();
			List<Integer> noteIds20 = new ArrayList<Integer>();
			List<Integer> noteIds100 = new ArrayList<Integer>();
			List<Integer> noteIds200 = new ArrayList<Integer>();
			List<Integer> noteIds1000 = new ArrayList<Integer>();
			List<Integer> noteIds10000 = new ArrayList<Integer>();
			for (Note note : noteList) {
				if (note.getVersion().equals("第一版")) {
					int value = Integer.valueOf(note.getValue().replace("元", ""));
					if (value < 20) {
						noteIds1.add(note.getId());
					}
					else if (value < 100) {
						noteIds20.add(note.getId());
					}
					else if (value < 200) {
						noteIds100.add(note.getId());
					}
					else if (value < 1000) {
						noteIds200.add(note.getId());
					}
					else if (value < 10000) {
						noteIds1000.add(note.getId());
					}
					else {
						noteIds10000.add(note.getId());
					}
				}
			}
			
			PmgRequest pmgRequest = new PmgRequest();
			pmgRequest.setPageSize(Integer.MAX_VALUE);
			List<QueryOrder> queryOrderList = new ArrayList<QueryOrder>();
			queryOrderList.add(new QueryOrder("id", QueryOrderType.ASC));
			pmgRequest.setQueryOrderList(queryOrderList);
			
			pmgRequest.setNoteIds(noteIds1);
			List<Pmg> pmgList1 = pmgService.queryList(pmgRequest);
			packageData(pmgList1, "1-10元");
			pmgListMap.put("1-10元", pmgList1);
			
			pmgRequest.setNoteIds(noteIds20);
			List<Pmg> pmgList20 = pmgService.queryList(pmgRequest);
			packageData(pmgList20, "20-50元");
			pmgListMap.put("20-50元", pmgList20);
			
			pmgRequest.setNoteIds(noteIds100);
			List<Pmg> pmgList100 = pmgService.queryList(pmgRequest);
			packageData(pmgList100, "100元");
			pmgListMap.put("100元", pmgList100);
			
			pmgRequest.setNoteIds(noteIds200);
			List<Pmg> pmgList200 = pmgService.queryList(pmgRequest);
			packageData(pmgList200, "200-500元");
			pmgListMap.put("200-500元", pmgList200);
			
			pmgRequest.setNoteIds(noteIds1000);
			List<Pmg> pmgList1000 = pmgService.queryList(pmgRequest);
			packageData(pmgList1000, "1千-5千元");
			pmgListMap.put("1千-5千元", pmgList1000);
			
			pmgRequest.setNoteIds(noteIds10000);
			List<Pmg> pmgList10000 = pmgService.queryList(pmgRequest);
			packageData(pmgList10000, "1万-5万元");
			pmgListMap.put("1万-5万元", pmgList10000);
			
			// 缓存
			memcachedClient.add(CacheConstants.YIBAN_PMGLISTMAP_KEY, pmgListMap, DateUtils.addHours(new Date(), 6));
			memcachedClient.add(CacheConstants.YIBAN_TITLE_KEY, DISPLAY_TITLE_LIST, DateUtils.addHours(new Date(), 6));
		}
		
		// 更新时间
		String updateDate = dataService.getByKeyword("updateDate").getValue();
		
		// return
		model.addAttribute("pmgListMap", pmgListMap);
		model.addAttribute("titleList", DISPLAY_TITLE_LIST);
		model.addAttribute("updateDate", updateDate);
		
		return "yiban";
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		LinkedHashMap<String, List<Pmg>> pmgListMap = new LinkedHashMap<String, List<Pmg>>();
		
		// 判断缓存
		Object pmgCache = memcachedClient.get(CacheConstants.INDEX_PMGLISTMAP_KEY);
		Object titleCache = memcachedClient.get(CacheConstants.INDEX_TITLE_KEY);
		if (pmgCache != null && titleCache != null) {
			pmgListMap = (LinkedHashMap<String, List<Pmg>>) pmgCache;
			DISPLAY_TITLE_LIST = (List<String>) titleCache;
		}
		else {
			BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
			
			NoteRequest noteRequest = new NoteRequest();
			noteRequest.setPageSize(Integer.MAX_VALUE);
			List<Note> noteList = noteService.queryList(noteRequest);
			
			List<Integer> noteIds2 = new ArrayList<Integer>();
			List<Integer> noteIds3 = new ArrayList<Integer>();
			List<Integer> noteIds4 = new ArrayList<Integer>();
			List<Integer> noteIds5 = new ArrayList<Integer>();
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
				else if (note.getVersion().equals("第四版")) {
					noteIds4.add(note.getId());
				}
				else if (note.getVersion().equals("第五版")) {
					noteIds5.add(note.getId());
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
			queryOrderList.add(new QueryOrder("create_date", QueryOrderType.ASC));
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
			
			pmgRequest.setNoteIds(noteIds4);
			List<Pmg> pmgList4 = pmgService.queryList(pmgRequest);
			packageData(pmgList4);
			pmgListMap.put("第四版", pmgList4);
			
			pmgRequest.setNoteIds(noteIds5);
			List<Pmg> pmgList5 = pmgService.queryList(pmgRequest);
			packageData(pmgList5);
			pmgListMap.put("第五版", pmgList5);
			
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
			
			// 缓存
			memcachedClient.add(CacheConstants.INDEX_PMGLISTMAP_KEY, pmgListMap, DateUtils.addHours(new Date(), 6));
			memcachedClient.add(CacheConstants.INDEX_TITLE_KEY, DISPLAY_TITLE_LIST, DateUtils.addHours(new Date(), 6));
		}
		
		// 更新时间
		String updateDate = dataService.getByKeyword("updateDate").getValue();
		
		// return
		model.addAttribute("pmgListMap", pmgListMap);
		model.addAttribute("titleList", DISPLAY_TITLE_LIST);
		model.addAttribute("updateDate", updateDate);
    	
		return "index";
    }
	
	private void packageKeyValue(Pmg pmg, Note note) {
		List<KeyValueDto> kvList = new ArrayList<KeyValueDto>();
		
		if (!note.getVersion().equals("第一版")) {
			// 高分难度
			String highTitle = "高分难度";
			kvList.add(new KeyValueDto(highTitle, String.valueOf(getRatioStar(pmg.getHighScoreRatio()))));
			if (!DISPLAY_TITLE_LIST.contains(highTitle)) {
				DISPLAY_TITLE_LIST.add(highTitle);
			}
			
			// 高分比例
			String highRatioTitle = "高分比例";
			kvList.add(new KeyValueDto(highRatioTitle, String.valueOf(pmg.getHighScoreRatio()) + "%"));
			if (!DISPLAY_TITLE_LIST.contains(highRatioTitle)) {
				DISPLAY_TITLE_LIST.add(highRatioTitle);
			}
			
			// 新增
			int day = getDayOfMonth();
			if (day > 20) {
				String monthTitle = "本月新增";
				kvList.add(new KeyValueDto(monthTitle, String.valueOf(pmg.getThisMonthAdd())));
				if (!DISPLAY_TITLE_LIST.contains(monthTitle)) {
					DISPLAY_TITLE_LIST.add(monthTitle);
				}
			}
			else {
				String lastMonthTitle = "上月新增";
				kvList.add(new KeyValueDto(lastMonthTitle, String.valueOf(pmg.getLastMonthAdd())));
				if (!DISPLAY_TITLE_LIST.contains(lastMonthTitle)) {
					DISPLAY_TITLE_LIST.add(lastMonthTitle);
				}
			}
		}
		
		pmg.setKeyValueList(kvList);
	}
	
	private int getDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}
	
	private String getMonth(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, amount);
		
		return monthSdf.format(calendar.getTime());
	}
	
	private void packageData(List<Pmg> pmgList) {
		packageData(pmgList, null);
	}
	
	private void packageData(List<Pmg> pmgList, String country) {
		for (Pmg pmg : pmgList) {
			Integer pmgId = pmg.getId();
			Note note = noteService.get(pmg.getNoteId());
			
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
				if (isDisplay(pmgGrade, note)) {
					// 使用简写E
					String grade = pmgGrade.getGrade().replaceAll(" EPQ", "E");
					String historyPrice = pmgGrade.getHistoryPrice();
					if (StringUtils.isNotBlank(historyPrice)) {
						historyPrice = historyPrice.replaceAll("\\s", "");
					}
					countList.add(new GradeCount(grade, pmgGrade.getCount(),
							pmgGrade.getPrice().intValue(), historyPrice));
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
			
			// 新增
			packageMonthAdd(pmg);
			
			// 冠号
			packageBlock(pmg);
			
			// 面值
			pmg.setValue(note.getValue());
			
			// 成交价
			packageDealPrice(pmg);
		}
		
		// 封装多个编号的问题
		packagePmgList(pmgList, country);
		
		for (Pmg pmg : pmgList) {
			Note note = noteService.get(pmg.getNoteId());
			// kv
			packageKeyValue(pmg, note);
		}
	}
	
	private void packageDealPrice(Pmg pmg) {
		if (StringUtils.isNotBlank(pmg.getDealPrice())) {
			pmg.setDealPriceList(JSON.parseArray(pmg.getDealPrice(), DealPrice.class));
		}
	}
	
	private void packageMonthAdd(Pmg pmg) {
		// 新增
		// 本月新增
		pmg.setThisMonthAdd(getMonthAdd(pmg, getMonth(0)));
		// 上月新增
		pmg.setLastMonthAdd(getMonthAdd(pmg, getMonth(-1)));
	}
	
	private Integer getMonthAdd(Pmg pmg, String month) {
		PmgLogRequest pmgLogRequest = new PmgLogRequest();
		pmgLogRequest.setMonth(month);
		pmgLogRequest.setPmgId(pmg.getId());
		pmgLogRequest.setPageSize(Integer.MAX_VALUE);
		List<PmgLog> logList = pmgLogService.queryList(pmgLogRequest);
		// 取头尾相减
		if (CollectionUtils.isNotEmpty(logList) && logList.size() > 1) {
			PmgLog start = logList.get(0);
			PmgLog end = logList.get(logList.size() - 1);
			return end.getTotal() - start.getTotal();
		}
		
		return 0;
	}
	
	private void packagePmgList(List<Pmg> pmgList, String country) {
		List<Pmg> result = new ArrayList<Pmg>();
		
		if (StringUtils.isNotBlank(country)) {
			LinkedHashMap<String, List<Pmg>> nameListMap = new LinkedHashMap<String, List<Pmg>>();
			for (Pmg pmg : pmgList) {
				// 名称+年份来区分
				String name = pmg.getName() + pmg.getYear();
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
				// 新增
				int thisMonthAdd = 0;
				int lastMonthAdd = 0;
				// 评分
				LinkedHashMap<String, Integer> gradeCountMap = new LinkedHashMap<String, Integer>();
				// 价格
				Map<String, Integer> gradePriceMap = new HashMap<String, Integer>();
				for (Pmg pmg : entry.getValue()) {
					List<GradeCount> gradeCountList = pmg.getGradeCountList();
					for (GradeCount gradeCount : gradeCountList) {
						String grade = gradeCount.getGrade();
						if (!gradeCountMap.containsKey(grade)) {
							gradeCountMap.put(grade, 0);
						}
						gradeCountMap.put(grade, gradeCountMap.get(grade) + gradeCount.getCount());
						
						if (gradeCount.getPrice() != null && gradeCount.getPrice() > 0) {
							gradePriceMap.put(grade, gradeCount.getPrice());
						}
					}
					
					// 总数累加
					total += pmg.getTotal();
					// 高分比例累加
					highScoreRatio += pmg.getHighScoreRatio();
					// 新增累加
					thisMonthAdd += pmg.getThisMonthAdd();
					lastMonthAdd += pmg.getLastMonthAdd();
				}
				
				List<GradeCount> countList = new ArrayList<GradeCount>();
				for (Map.Entry<String, Integer> countEntry : gradeCountMap.entrySet()) {
					countList.add(new GradeCount(countEntry.getKey(), 
							countEntry.getValue(), 
							gradePriceMap.get(countEntry.getKey())));
				}
				
				String catalogText = "";
				if (count > 1) {
					catalogText = "等";
				}
				newPmg.setCatalog(newPmg.getCatalog() + catalogText);
				newPmg.setTotal(total);
				newPmg.setHighScoreRatio(highScoreRatio / count);
				newPmg.setThisMonthAdd(thisMonthAdd);
				newPmg.setLastMonthAdd(lastMonthAdd);
				newPmg.setGradeCountList(countList);
				result.add(newPmg);
			}
		}
		
		if (CollectionUtils.isNotEmpty(result)) {
			pmgList.clear();
			pmgList.addAll(result);
		}
	}
	
	private boolean isDisplay(PmgGrade pmgGrade, Note note) {
		try {
			int gradeNum = getGradeNum(pmgGrade);
			if ((!note.getVersion().equals("第一版") && gradeNum >= DISPLAY_NUM)
					|| (note.getVersion().equals("第一版") && gradeNum >= 10 && gradeNum <= 68
							&& gradeNum != 60 && gradeNum != 61)) {
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
		else if (ratio > 30 && ratio <= 45) {
			star = 3;
		}
		else if (ratio > 45 && ratio <= 60) {
			star = 2;
		}
		
		return star;
	}
	
	private void packageBlock(Pmg pmg) {
		List<BlockDto> dtoList = new ArrayList<BlockDto>();
		
		BlockRequest blockRequest = new BlockRequest();
		blockRequest.setPmgId(pmg.getId());
		blockRequest.setPageSize(Integer.MAX_VALUE);
		List<Block> blockList = blockService.queryList(blockRequest);
		
		LinkedHashMap<String, List<BlockGroupDto>> blockMap = new LinkedHashMap<String, List<BlockGroupDto>>();
		LinkedHashMap<String, String> remarkNumMap = new LinkedHashMap<String, String>();
		for (Block block : blockList) {
			String split = getNbsp(3) + " ";
			
			String name = block.getName();
			if (!blockMap.containsKey(name)) {
				blockMap.put(name, new ArrayList<BlockGroupDto>());
			}
			
			String groupName = "冠字组";
			if (block.getIsGroup() == 0) {
				groupName = "冠字";
			}
			blockMap.get(name).add(new BlockGroupDto(groupName, block.getValue().replaceAll(",", split)));
			
			remarkNumMap.put(name, notNull(block.getRemarkNum()).replaceAll(",", split));
		}
		
		for (Map.Entry<String, List<BlockGroupDto>> entry : blockMap.entrySet()) {
			BlockDto blockDto = new BlockDto();
			blockDto.setName(entry.getKey());
			blockDto.setBlockGroupList(entry.getValue());
			blockDto.setRemarkNum(remarkNumMap.get(entry.getKey()));
			dtoList.add(blockDto);
		}
		
		pmg.setBlockList(dtoList);
	}
	
	private String getNbsp(int num) {
		String text = "";
		
		for (int i = 0; i < num; i++) {
			text += "&nbsp";
		}
		
		return text;
	}
}
