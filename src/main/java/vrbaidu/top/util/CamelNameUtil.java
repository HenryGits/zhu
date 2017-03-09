package vrbaidu.top.util;

/**
 * @desc 骆驼命名工具类
 * @author Someone
 *
 * @date 2016-08-17 16:26:14
 *
 */
public class CamelNameUtil {

	public static String camel2underscore(String camelName) {
		camelName = capitalize(camelName);
		String regex = "([A-Z][a-z]+)";
		String replacement = "$1_";
		String underscoreName = camelName.replaceAll(regex, replacement);
		underscoreName = underscoreName.toLowerCase().substring(0, underscoreName.length() - 1);
		return underscoreName;
	}

	public static String underscore2camel(String underscoreName) {
		String[] sections = underscoreName.split("_");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sections.length; ++i) {
			String s = sections[i];
			if (i == 0) {
				sb.append(s);
			} else {
				sb.append(capitalize(s));
			}
		}

		return sb.toString();
	}

	public static String capitalize(String str) {
		int strLen;
		return str != null && (strLen = str.length()) != 0 ? (new StringBuilder(
				strLen)).append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString() : str;
	}
}
