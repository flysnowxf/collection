/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.whalin.MemCached.MemCachedClient;

/**
 * 
 * <br>Create on 2016-4-16 上午1:24:44
 *
 * @author fengxuefeng
 */
public class BaseAction {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected MemCachedClient memcachedClient;
	
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected static final SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");
	protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#");
	
	protected void writerPrint(HttpServletResponse response, String value) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String notNull(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		
		return text;
	}
}
