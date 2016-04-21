/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.PmgData;
import cn.flysnowxf.collection.dto.PmgGradeRequest;
import cn.flysnowxf.collection.dto.PmgLogRequest;
import cn.flysnowxf.collection.dto.PmgRequest;
import cn.flysnowxf.collection.entity.Note;
import cn.flysnowxf.collection.entity.Pmg;
import cn.flysnowxf.collection.entity.PmgGrade;
import cn.flysnowxf.collection.entity.PmgLog;
import cn.flysnowxf.collection.utils.PmgUtils;

import com.google.gson.Gson;

/**
 * 
 * <br>Create on 2016-4-12 下午9:45:13
 *
 * @author fengxuefeng
 */
@Service
public class LookupPmgService {
	@Autowired
	private PmgGradeService pmgGradeService;
	@Autowired
	private PmgService pmgService;
	@Autowired
	private PmgLogService pmgLogService;
	@Autowired
	private DataService dataService;
	@Autowired
	private NoteService noteService;
	
	private static final Logger logger = Logger.getLogger(LookupPmgService.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void lookupPmg() {
		logger.info("LookupPmg Start...");
		
		// 获取pmg数据
		PmgRequest pmgRequest = new PmgRequest();
		pmgRequest.setPageSize(Integer.MAX_VALUE);
		List<Pmg> list = pmgService.queryList(pmgRequest);
		
		// 设置最新的session id
		PmgUtils.setSession(getSessionId(), getAuth());
		
		String date = sdf.format(DateUtils.addHours(new Date(), -6));
		boolean isException = false;
		for (Pmg pmg : list) {
			try {
				String country = "中国";
				Note note = noteService.get(pmg.getNoteId());
				if (note != null) {
					country = note.getCountry();
				}
				String catalog = pmg.getCatalog();
				PmgData data = PmgUtils.lookup(country, catalog);
				
				// 总数
				int total = data.getTotal();
				pmg.setTotal(total);
				pmgService.update(pmg);
				
				// 打印
				System.out.println(pmg.getName() + pmg.getCatalog());
				
				// 写入grade
				Integer pmgId = pmg.getId();
				for (GradeCount gradeCount : data.getGradeCountList()) {
					String grade = gradeCount.getGrade();
					Integer count = gradeCount.getCount();
					
					// 写入grade
					PmgGradeRequest pmgGradeRequest = new PmgGradeRequest();
					pmgGradeRequest.setPmgId(pmgId);
					pmgGradeRequest.setGrade(grade);
					List<PmgGrade> gradeList = pmgGradeService.queryList(pmgGradeRequest);
					// 插入
					if (CollectionUtils.isEmpty(gradeList)) {
						PmgGrade pmgGrade = new PmgGrade();
						pmgGrade.setPmgId(pmg.getId());
						pmgGrade.setGrade(grade);
						pmgGrade.setCount(count);
						
						pmgGradeService.add(pmgGrade);
					}
					// 修改
					else {
						PmgGrade pmgGrade = gradeList.get(0);
						pmgGrade.setCount(count);
						
						pmgGradeService.update(pmgGrade);
					}
				}
				
				// 写入log
				String logCount = new Gson().toJson(data.getGradeCountList());
				PmgLogRequest pmgLogRequest = new PmgLogRequest();
				pmgLogRequest.setPmgId(pmgId);
				pmgLogRequest.setDate(date);
				List<PmgLog> logList = pmgLogService.queryList(pmgLogRequest);
				// 插入
				if (CollectionUtils.isEmpty(logList)) {
					PmgLog pmgLog = new PmgLog();
					pmgLog.setPmgId(pmgId);
					pmgLog.setDate(date);
					pmgLog.setCount(logCount);
					pmgLog.setTotal(total);
					
					pmgLogService.add(pmgLog);
				}
				// 修改
				else {
					PmgLog pmgLog = logList.get(0);
					pmgLog.setCount(logCount);
					pmgLog.setTotal(total);
					
					pmgLogService.update(pmgLog);
				}
				
				// 暂停一下，不要连续抓数据
//				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				
				isException = true;
				break;
			}
		}
		
		// 更新时间
		if (!isException) {
			dataService.updateByKeyword("updateDate", date);
		}
		
		logger.info("LookupPmg End!");
	}
	
	private String getSessionId() {
		return dataService.getByKeyword("sessionId").getValue();
	}
	
	private String getAuth() {
		return dataService.getByKeyword("auth").getValue();
	}
}
