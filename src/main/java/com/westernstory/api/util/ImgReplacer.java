package com.westernstory.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fedor on 15/3/1.
 */
public class ImgReplacer {

    private static final Pattern IMG_PATTERN = Pattern.compile(
//            "<img(\\s+.*?)(?:src\\s*=\\s*(?:'|\")(.*?)(?:'|\"))(.*?)>/",
            "<img(\\s+.*?)(?:src\\s*=\\s*(?:'|\")(.*?)(?:'|\"))(.*?)>",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern CLASS_PATTERN = Pattern.compile(
            "class\\s*=\\s*(?:'|\")(.*?)(?:'|\")", Pattern.DOTALL
                    | Pattern.CASE_INSENSITIVE);

    /**
     * 图片加前缀
     * @param str str
     * @param prefix prefix
     * @return String
     */
    public static String addPrefix(String str, String prefix) {
        StringBuilder sb = new StringBuilder();
        Matcher m = IMG_PATTERN.matcher(str);
        int start = 0, end = 0;
        while (m.find()) { // find next match
            end = m.start();
            sb.append(str.substring(start, end));

            sb.append("<img");
			boolean classExists = false;
            for (int i = 1; i < m.groupCount() + 1; i++) {
                if (i == 2) { // image src
                    if (m.group(2).indexOf("http:") != 0) {
                        sb.append(" src=\"" + prefix + m.group(2) + "\"");
                    } else {
                        sb.append(" src=\"" + m.group(2) + "\"");
                    }
                    continue;
                }

				Matcher mc = CLASS_PATTERN.matcher(m.group(i));
				if (mc.find()) {
					classExists = true;
					sb.append(m.group(i).substring(0, mc.start()));
					sb.append(" class=\"" + mc.group(1) + " mycls\"");
					sb.append(m.group(i).substring(mc.end()));
				} else {
					sb.append(m.group(i));
				}
            }

			if (!classExists) {
				sb.append(" class=\"mycls\"");
			}

            sb.append("/>");

            start = m.end();
        }
        sb.append(str.substring(start, str.length()));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String str1 = "<img src=\"/editor/2015/03/31/20150331112727_569.jpg\" alt=\"\" width=\"600\" height=\"391\" title=\"\" align=\"\"/><img src=\"/editor/2015/03/31/20150331112727_569.jpg\" alt=\"\" width=\"600\" height=\"391\" title=\"\" align=\"\"/>";
        String str2 = "<img src='http://editor/2015/03/31/20150331112727_569.jpg'>";
        System.out.println(ImgReplacer.addPrefix(str1, "http://www.baidu.com/"));
        System.out.println(ImgReplacer.addPrefix(str2, "http://www.baidu.com/"));
    }
}
