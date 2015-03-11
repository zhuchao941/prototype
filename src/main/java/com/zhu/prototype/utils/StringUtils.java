package com.zhu.prototype.utils;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	private static String htmlReg = "<([^>]*)>";

	public static String buildDigest(String content) {
		if(content.contains("<p>")){
			content = content.substring(content.indexOf("<p>") + "<p>".length(),
					content.indexOf("</p>"));
		}else{
			content = content.substring(content.indexOf("<div>") + "<div>".length(),
					content.indexOf("</div>"));
		}
		// 去除所有标签，防止恰好截到标签
		return content.replaceAll(htmlReg, "") + "……";
	}
}
