/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.utils;

import org.apache.commons.httpclient.Header;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.PmgData;
import cn.flysnowxf.net.HttpRequest;
import cn.flysnowxf.net.HttpResponse;
import cn.flysnowxf.net.HttpUtils;

/**
 * 
 * <br>Create on 2016-4-9 下午2:20:25
 *
 * @author fengxuefeng
 */
public class FxfPmgUtils {
	private static final String COOKIE = "language=en-US; __cfduid=d9e9b9f2564cb13e5fdeba93d43ac50591458659780; ASP.NET_SessionId=43c3vfozh3zt5sufku4mbwpx; __utma=221905049.1696584910.1458659783.1460282917.1460287836.5; __utmc=221905049; __utmz=221905049.1458659786.1.1.utmcsr=m.pmgnotes.cn|utmccn=(referral)|utmcmd=referral|utmcct=/paper-money-grading/introduction-to-pmg/; _ga=GA1.2.1696584910.1458659783; has-registry=false; .ASPXAUTH=3E1949180999BDE73433DF60E0B2551C5024922BF862C0C4B3FAFCB9F417A0F188ADF68647A7330D16F00543AABEF41EDA03C11DE9F54CE5533B0398FF3DD4A4747C9A2AA3C400C307C23B01A1198064D73ADECC77A404D3E566E957854A9710436B68A50B077E0A240DC7DD7543440AB124E6E9";
	
	public static void main(String[] args) {
		String country = "中国";
		String catalog = "877a";
		PmgData data = PmgUtils.lookup(country, catalog);
		// 打印
		for (GradeCount gradeCount : data.getGradeCountList()) {
			System.out.println(gradeCount.getGrade() + "\t" + gradeCount.getCount());
		}
		
		catalog = "877b";
		data = PmgUtils.lookup(country, catalog);
		// 打印
		for (GradeCount gradeCount : data.getGradeCountList()) {
			System.out.println(gradeCount.getGrade() + "\t" + gradeCount.getCount());
		}
	}
	
	public void lookup() {
		String url = "https://www.pmgnotes.com/poplookup/Note.aspx?Country=CHN&Name=China&NoteNumber=CHN873";
		HttpRequest httpRequest = new HttpRequest(url);
		httpRequest.setConnTimeout(20000);
		httpRequest.setSoTimeout(20000);
		Header[] header = new Header[] {
				new Header("Cookie", COOKIE)
		};
		httpRequest.setHeader(header);
		HttpResponse httpResponse = HttpUtils.get(httpRequest);
		System.out.println(httpResponse.getResponse());
		for (Header respHeader : httpResponse.getHeader()) {
			System.out.println(respHeader.getName() + ":" + respHeader.getValue());
		}
	}
}