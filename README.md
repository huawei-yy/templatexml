该项目是想解决系统之间的xml传输拼接报文不直观。
解决思路是建立一个XML模板，与javabean对象绑定，达到自动拼装，与自动解析。
当前为版初级版本。基本能满足日常应用。对于正确性校验，及复杂逻辑不足。
也是想着大牛多指导共同改善。
示例：
测试类：
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

public class BuildTest {
	public static void main(String[] args) throws DocumentException {
		String xmlTemplate =readTxtFile();
		Class class1 = new Class();
		class1.setName("一年级一班");
		class1.setNo("01001");
		Class class2 = new Class();
		class2.setName("一年级二班");
		class2.setNo("01002");
		Stu stu1 = new Stu();
		stu1.setAge("6");
		stu1.setName("哈哈");
		stu1.setSex("0");
		stu1.setClassno("01002");
		stu1.setScore(96.888888);
		stu1.setBirthday(new Date());
		Stu stu2 = new Stu();
		stu2.setAge("7");
		stu2.setName("嘻嘻");
		stu2.setSex("1");
		stu2.setClassno("01001");
		stu2.setScore(97.666666);
		stu2.setBirthday(new Date());
		List<Class> classs = new ArrayList<Class>();
		classs.add(class1);
		classs.add(class2);
		List<Stu> stus = new ArrayList<Stu>();
		stus.add(stu2);
		stus.add(stu1);
		Map data = new HashMap();
		data.put("classs", classs);
		data.put("stus", stus);
		System.out.println(XmlBuildAndParse.buildxml(data, xmlTemplate));
	}

	/**
	 * 读取本地请求报文
	 * 
	 * @return
	 */
	private static String readTxtFile() {
		try {
			String encoding = "gbk";
			File file = new File(BuildTest.class.getResource("BuildTest.xml").toURI().getPath());
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
模板xml
<?xml version="1.0" encoding="GBK"?>
<data>
	<!-- x-repeat为迭代$.classs对象,并添加对应节点。$.classs为根对象下的classs属性,$代表为根对象 -->
	<class x-repeat="class in $.classs">
		<!-- x-model为将该节点text赋值为class.no -->
		<id x-model="class.no"></id>
		<name x-model="class.name"></name>
		<!-- x-if表达式的值为真则输出该节点，假则不输出 。x-repeat与x-if哪个位置在前先执行哪个 -->
		<stu x-repeat="stu in $.stus" x-if="class.no==stu.classno">
			<name x-model="stu.name"></name>
			<age x-model="stu.age">20</age>
			<!-- transCode为转码，代表将0值输出为女 -->
			<sex x-model="stu.sex|transCode:{0=女,1=男}"></sex>
			<!-- dateFormat 为时间格式化 -->
			<birthday x-model="stu.birthday|dateFormat:yyyy-MM-dd"></birthday>
			<!-- numberFormat 为数字格式化 -->
			<score x-model="stu.score|numberFormat:###"></score>
		</stu>
	</class>
</data>
输出结果：
<?xml version="1.0" encoding="GBK"?>
<data>
	<class>
		<id>01001</id>
		<name>一年级一班</name>
		<stu>
			<name>嘻嘻</name>
			<age>7</age>
			<sex>男</sex>
			<birthday>2020-11-17</birthday>
			<score>98</score>
		</stu>
	</class>
	<class>
		<id>01002</id>
		<name>一年级二班</name>
		<stu>
			<name>哈哈</name>
			<age>6</age>
			<sex>女</sex>
			<birthday>2020-11-17</birthday>
			<score>97</score>
		</stu>
	</class>
</data>

