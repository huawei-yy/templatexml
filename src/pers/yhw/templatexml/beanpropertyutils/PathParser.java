package pers.yhw.templatexml.beanpropertyutils;

public class PathParser {
	private String path;
	private int index;
	private static final char POINT = '.';
	private static final char INDEXEDBEGIN = '[';
	private static final char INDEXEDEND = ']';

	PathParser(String path) {

		if (path == null)
			throw new IllegalArgumentException("path is null");
		this.path = path;

	}

	public boolean hasNext() {
		return index < path.length();
	}

	public String next() {
		StringBuffer subPath = new StringBuffer();
		for (int begin = index; index < path.length(); index++) {
			char c = path.charAt(index);
			if (c == POINT) {
				index++;
				break;
			} else if (c == INDEXEDBEGIN && c != begin) {
				break;
			} else if (c == INDEXEDEND) {
				subPath.append(Character.toString(c));
				index++;
				break;
			} else {
				subPath.append(Character.toString(c));
			}
		}

		return subPath.toString();
	}

	static public boolean isIndexed(String subPath) {
		return subPath.startsWith(Character.toString(INDEXEDBEGIN)) && subPath.endsWith(Character.toString(INDEXEDEND));
	}

	boolean hasLast() {
		int i = 0;
		PathParser pathParser = new PathParser(path.substring(index));
		while (pathParser.hasNext()) {
			pathParser.next();
			i++;
			if (i > 1) {
				return false;
			}
		}
		return true;
	}

	static public int parseIndexedPath(String subPath) {
		String indexStr = subPath.substring(1, subPath.length() - 1).trim();
		return Integer.valueOf(indexStr);
	}

}
