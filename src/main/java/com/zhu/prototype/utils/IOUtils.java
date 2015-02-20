package com.zhu.prototype.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

public class IOUtils {

	private static Logger logger = Logger.getLogger(IOUtils.class);

	/**
	 * 从request中读取数据、不能用request.paramter，要通过stream来读取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String readDataFromPost(HttpServletRequest request)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String body = null;
		BufferedReader br = request.getReader();
		if (body == null) {
			body = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				body += line;
			}
		}
		return body;
	}

	/**
	 * 下载指定图片到指定目录 若urlStr为null，那么则不显示图片
	 * 
	 * @param urlStr
	 * @param path
	 * @return 文件名
	 */
	public static String downloadResource(String urlStr, String path) {
		if (StringUtils.isBlank(urlStr)) { // 处理无图的情况
			return null;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String fileName = getFileName(urlStr);
			logger.info("正在从" + urlStr + "下载图片到" + path + fileName);
			URL url = new URL(urlStr);
			bos = new BufferedOutputStream(
					new FileOutputStream(path + fileName));
			bis = new BufferedInputStream(url.openStream());
			// 为什么这么这里用 byte b = new byte[1024]会出问题？ TODO
			int i = -1;
			long start = System.currentTimeMillis();
			while ((i = bis.read()) != -1) {
				bos.write(i);
			}
			long end = System.currentTimeMillis();
			logger.info("消耗时间：" + (end - start) + "ms\n-----------------");
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static String getFileName(String url) {
		if (url == null || url.indexOf("/") == -1) {
			return null;
		}
		url = url.replace("*", "");
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}
}
