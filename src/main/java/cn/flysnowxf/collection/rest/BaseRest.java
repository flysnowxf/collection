package cn.flysnowxf.collection.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.flysnowxf.collection.exception.ParamNullException;
import cn.flysnowxf.collection.resp.BaseResp;

/**
 * 
 * <br>
 * Create on 2016年3月17日 下午2:14:35
 *
 * @author fengxuefeng
 */
public class BaseRest {
	@Autowired
	protected HttpServletRequest request;

	private static final String LOG_SEPARATOR = "<|>";
	protected static final Logger logger = Logger.getLogger("stat");
	protected static final Logger selfLog = Logger.getLogger(BaseRest.class);
	protected static final SimpleDateFormat basicSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected String getHeader(String name) {
		// 兼容从参数中获取
		String value = request.getHeader(name);
		// 不判断userId
		if (StringUtils.isBlank(value) && !name.equals("userId")) {
			value = getParam(name);
		}

		return notNull(value);
	}

	protected String notNull(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}

		return "";
	}

	protected String getParam(String param) {
		try {
			return request.getParameter(param);
		} catch (Exception e) {
			try {
				selfLog.info("at=" + basicSdf.format(new Date()) + "<|>am=getParam" + "<|>param=" + param
						+ "<|>exception=" + e.getClass().getName() + "<|>message=" + e.getCause().getMessage());

				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return "";
	}
	
	protected void log(Map<String, Object> logs, Logger logger) {
		// 统一参数
		logs.put("at", basicSdf.format(new Date()));

		StringBuilder s = new StringBuilder();
		for (String key : logs.keySet()) {
			String v = logs.get(key) == null ? "" : String.valueOf(logs.get(key));
			s.append(key).append("=").append(v).append(LOG_SEPARATOR);
		}

		logger.info(s.toString());
	}

	protected void validateParam(Object... param) throws ParamNullException {
		if (param != null && param.length > 0) {
			for (Object p : param) {
				if (p == null) {
					throw new ParamNullException();
				} else if (p instanceof String) {
					// 判断字符串空的情况
					if ("".equals((String) p) || "null".equals((String) p)) {
						throw new ParamNullException();
					}
				}
			}
		}
	}
	
	protected void accessException(BaseResp resp, Exception e) {
		String code = null;

		if (e instanceof Exception) {
			code = "100";
			e.printStackTrace();
		}

		if (resp != null) {
			resp.setStatus(code);
		}
	}
}
