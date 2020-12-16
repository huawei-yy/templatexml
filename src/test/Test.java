package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import pers.yhw.templatexml.XmlBuildAndParse;

public class Test {
	public static void main(String[] args) throws DocumentException, InstantiationException, IllegalAccessException {

		build();
		parse();
		
	}

	private static void parse() throws InstantiationException, IllegalAccessException, DocumentException {
		String xmlTemplate = readTxtFile("BuildTest.xml");
		String target = readTxtFile("targetxml.xml");
		School school=XmlBuildAndParse.parstxml(School.class, xmlTemplate, target);
		System.out.println(school);

	}

	static void build() throws DocumentException {
		String xmlTemplate = readTxtFile("BuildTest.xml");
		School school = new School();
		Class class1 = new Class();
		class1.setName("一年级一班");
		class1.setNo("01001");
		Class class2 = new Class();
		class2.setName("一年级二班");
		class2.setNo("01002");
		Student stu1 = new Student();
		stu1.setAge("6");
		stu1.setName("哈哈");
		stu1.setSex("0");
		stu1.setClassno("01002");
		stu1.setScore(96.888888);
		stu1.setBirthday(new Date());
		Student stu2 = new Student();
		stu2.setAge("7");
		stu2.setName("嘻嘻");
		stu2.setSex("1");
		stu2.setClassno("01001");
		stu2.setScore(97.666666);
		stu2.setBirthday(new Date());
		List<Class> classs = school.getClasses();
		classs.add(class1);
		classs.add(class2);
		List<Student> stus = school.getStudents();
		stus.add(stu2);
		stus.add(stu1);

		System.out.println(XmlBuildAndParse.buildxml(school, xmlTemplate));
	}

	/**
	 * 读取本地请求报文
	 * 
	 * @return
	 */
	private static String readTxtFile(String name) {
		try {
			String encoding = "gbk";
			File file = new File(Test.class.getResource(name).toURI().getPath());
			if ((file.isFile()) && (file.exists())) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				StringBuffer buffer = new StringBuffer();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					buffer.append(lineTxt).append("\r\n");
				}
				read.close();
				return buffer.toString();
			}
			System.out.println("找不到指定的文件");
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return "";
	}
}
