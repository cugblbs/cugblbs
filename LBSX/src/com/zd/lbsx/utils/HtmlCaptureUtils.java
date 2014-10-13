package com.zd.lbsx.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zd.lbsx.bean.Info;

public class HtmlCaptureUtils {
	static ArrayList<Info> list = new ArrayList<Info>();

	public static ArrayList<Info> captureHtml(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (doc != null) {
			Elements links = doc.select("a[href]");
			// Elements media = doc.select("[src]");
			// Elements imports = doc.select("link[href]");

			// print("\nMedia: (%d)", media.size());
			// for (Element src : media) {
			// if (src.tagName().equals("img"))
			// print(" * %s: <%s> %sx%s (%s)", src.tagName(),
			// src.attr("abs:src"), src.attr("width"),
			// src.attr("height"), trim(src.attr("alt"), 20));
			// else
			// print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
			// }

			// print("\nImports: (%d)", imports.size());
			// for (Element link : imports) {
			// print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"),
			// link.attr("rel"));
			// }

			int count = 0;
			for (Element link : links) {
				if (link.text().length() >= 8
						&& !link.text().equals("English Version")) {
					System.out.println(link.attr("abs:href"));
					System.out.println(link.text());
					Info info = new Info(count, link.text(),
							link.attr("abs:href"));
					list.add(info);
					count++;
				}
			}
			print("\nLinks: (%d)", count++);
		} else {
			return null;
		}
		return list;
	}

	public static String CaptureContent(String url) {
	    String contentString="";
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (doc != null) {
			Elements links = doc.select("#artical_real");

			int count=0;
			for (Element link : links) {
				contentString+=link.text();
				count++;
			}
			print("\nLinks: (%d)", count++);
		}else {
			return null;
		}
		return contentString;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
}
