/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flysnowxf.collection.dao.NoteDao;
import cn.flysnowxf.collection.dto.NoteRequest;
import cn.flysnowxf.collection.entity.Note;
import cn.flysnowxf.collection.utils.QueryUtils;

/**
 * 
 * <br>Create on 2016-4-12 下午10:16:58
 *
 * @author fengxuefeng
 */
@Service
public class NoteService {
	@Autowired
	private NoteDao noteDao;
	
	public int queryCount(Map<String, Object> param) {
		return noteDao.queryCount(param);
	}
	
	public List<Note> queryList(NoteRequest noteRequest) {
		Map<String, Object> param = QueryUtils.getQueryMap(noteRequest);
		List<Note> list = noteDao.query(param);

		return list;
	}
	
	public Note get(Integer id) {
		return noteDao.get(id);
	}
}
