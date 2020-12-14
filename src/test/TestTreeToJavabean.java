package test;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.alibaba.fastjson.util.TypeUtils;

public class TestTreeToJavabean {

	public static void main(String[] args) {
		TreeMap map = new TreeMap<String,String>();
		map.put(1, "hehe");
		map.put(2, "haha");
		List list = TypeUtils.castToJavaBean(map, List.class);
		System.out.println(list);
	}
}
