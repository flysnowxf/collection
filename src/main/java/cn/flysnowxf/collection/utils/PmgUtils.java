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
	private static final String COOKIE = "language=en-US; __cfduid=d9e9b9f2564cb13e5fdeba93d43ac50591458659780; ASP.NET_SessionId=43c3vfozh3zt5sufku4mbwpx; __utma=221905049.1696584910.1458659783.1460282917.1460287836.5; __utmc=221905049; __utmz=221905049.1458659786.1.1.utmcsr=m.pmgnotes.cn|utmccn=(referral)|utmcmd=referral|utmcct=/paper-money-grading/introduction-to-pmg/; _ga=GA1.2.1696584910.1458659783; has-registry=false; .ASPXAUTH=3E1949180999BDE73433DF60E0B2551C5024922BF862C0C4B3FAFCB9F417A0F188ADF68647A7330D16F00543AABEF41EDA03C11DE9F54CE5533B0398FF3DD4A4747C9A2AA3C400C307C23B01A1198064D73ADECC77A404D3E566E957854A9710436B68A50B077E0A240DC7DD7543440AB124E6E9";
	private static final String URL = "https://www.pmgnotes.com/poplookup/Note.aspx?Country={0}&Name={1}&NoteNumber={0}{2}";
	private static final Map<String, String> COUNTRY_CODE_MAP = new HashMap<String, String>();
	private static final Integer TIMEOUT = 20000;
	
	static {
		COUNTRY_CODE_MAP.put("中国", "CHN,China");
	}
	
	public static PmgData lookup(String country, String catalog) {
		PmgData data = new PmgData();
		
		String code = COUNTRY_CODE_MAP.get(country);
		String countryCode = code.split(",")[0];
		String countryName = code.split(",")[1];
		
		String url = MessageFormat.format(URL, countryCode, countryName, catalog);
		HttpRequest httpRequest = new HttpRequest(url);
		httpRequest.setConnTimeout(TIMEOUT);
		httpRequest.setSoTimeout(TIMEOUT);
		Header[] header = new Header[] {
				new Header("Cookie", COOKIE)
		};
		httpRequest.setHeader(header);
		HttpResponse httpResponse = HttpUtils.get(httpRequest);
		
		logger.info("at=" + sdf.format(new Date()) +
				"<|>am=lookup" +
				"<|>url=" + url);
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
}