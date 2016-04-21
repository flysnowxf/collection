/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.utils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.PmgData;
import cn.flysnowxf.net.HttpRequest;
import cn.flysnowxf.net.HttpResponse;
import cn.flysnowxf.net.HttpUtils;

/**
 * 
 * <br>Create on 2016-4-10 下午11:24:22
 *
 * @author fengxuefeng
 */
public class PmgUtils {
	private static final Logger logger = Logger.getLogger(PmgUtils.class);
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String SESSION_ID = "";
	private static String AUTH = "";
	private static final String COOKIE = "language=en-US; __cfduid=d9e9b9f2564cb13e5fdeba93d43ac50591458659780; ASP.NET_SessionId={0}; __utma=221905049.1696584910.1458659783.1460282917.1460287836.5; __utmc=221905049; __utmz=221905049.1458659786.1.1.utmcsr=m.pmgnotes.cn|utmccn=(referral)|utmcmd=referral|utmcct=/paper-money-grading/introduction-to-pmg/; _ga=GA1.2.1696584910.1458659783; has-registry=false; .ASPXAUTH={1}";
	private static final String URL = "https://www.pmgnotes.com/poplookup/Note.aspx?Country={0}&Name={1}&NoteNumber={0}{2}";
	private static final Map<String, String> COUNTRY_CODE_MAP = new HashMap<String, String>();
	private static final Integer TIMEOUT = 20000;
	
	static {
		COUNTRY_CODE_MAP.put("中国", "CHN,China");
		COUNTRY_CODE_MAP.put("澳门", "MAO,Macao");
		COUNTRY_CODE_MAP.put("香港", "HKG,Hong%20Kong");
	}
	
	public static void setSession(String sessionId, String auth) {
		SESSION_ID = sessionId;
		AUTH = auth;
	}
	
	public static PmgData lookup(String country, String catalog) throws Exception {
		if (StringUtils.isBlank(SESSION_ID) || StringUtils.isBlank(AUTH)) {
			throw new Exception();
		}
		
		PmgData data = new PmgData();
		
		String code = COUNTRY_CODE_MAP.get(country);
		String countryCode = code.split(",")[0];
		String countryName = code.split(",")[1];
		
		String url = MessageFormat.format(URL, countryCode, countryName, catalog);
		HttpResponse httpResponse = callUrl(url);
			
		for (Header respHeader : httpResponse.getHeader()) {
			logger.info(respHeader.getName() + ":" + respHeader.getValue());
		}
		
		// 解析数据
		Document doc = Jsoup.parse(httpResponse.getResponse());
		String total = doc.select("#ctl00_MainContent_lblTotalGraded").text();
		Elements tables = doc.select(".tdBorder");
		List<GradeCount> gradeCountList = new ArrayList<GradeCount>();
		if (tables.size() == 2) {
			Element gradeTable = tables.get(0);
			for (Element td : gradeTable.select("td")) {
				String text = td.text();
				gradeCountList.add(new GradeCount(text, 0));
			}
			
			Element countTable = tables.get(1);
			int index = 0;
			for (Element td : countTable.select("td")) {
				String text = td.text();
				int count = 0;
				if (StringUtils.isNotBlank(text)) {
					try {
						count = Integer.valueOf(text);
					} catch(Exception e) {
					}
				}
				
				if (count > 0) {
					GradeCount gradeCount = gradeCountList.get(index);
					gradeCount.setCount(count);
				}
				
				index++;
			}
		}
		
		data.setCatalog(catalog);
		data.setTotal(Integer.valueOf(total));
		data.setGradeCountList(gradeCountList);
		
		return data;
	}
	
	private static HttpResponse callUrl(String url) {
		HttpRequest httpRequest = new HttpRequest(url);
		httpRequest.setConnTimeout(TIMEOUT);
		httpRequest.setSoTimeout(TIMEOUT);
		Header[] header = new Header[] {
				new Header("Cookie", MessageFormat.format(COOKIE, SESSION_ID, AUTH))
		};
		httpRequest.setHeader(header);
		HttpResponse httpResponse = HttpUtils.get(httpRequest);
		boolean result = false;
		if (httpResponse.getStatus() == HttpStatus.SC_OK) {
			result = true;
		}
		
		logger.info("at=" + sdf.format(new Date()) +
				"<|>am=lookup" +
				"<|>url=" + url +
				"<|>result=" + result);
		
		if (!result) {
			return callUrl(url);
		}
		
		return httpResponse;
	}
}
