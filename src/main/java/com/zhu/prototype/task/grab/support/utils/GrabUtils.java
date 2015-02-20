package com.zhu.prototype.task.grab.support.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Zhu
 * @date 2015-2-17下午8:02:37
 * @description
 * 
 */
public class GrabUtils {

	// private static String htmlReg = "</?\\w.*>";

	private static Logger logger = Logger.getLogger(GrabUtils.class);
	@SuppressWarnings("unused")
	private static String htmlReg = "<([^>]*)>";

	/**
	 * 根据url得到html，并用selector取出元素，最后取出该元素的href属性
	 * 
	 * @param url
	 * @param selector
	 * @return
	 * @throws IOException
	 */
	public static String grab(String url, String selector) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select(selector);
		StringBuffer html = new StringBuffer();
		for (Element element : elements) {
			html.append(getHrefOfLink(element)).append("\n");
		}
		return html.toString();
	}

	private static String getHrefOfLink(Element link) {
		return link.attr("href");
	}

	public static Document getDocument(String url) {
		try {
			return Jsoup.connect(url).timeout(10000).get();
		} catch (IOException e) {
			logger.error(url + "连接不上..............");
			return getDocument(url);
		}
	}
}
