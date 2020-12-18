# tempplatexml
## 主要功能：
- 通过配置报文模板，转化为目标报文。
- 通过配置报文模板，将目标报文转换为对象。

## 优点：
- 使报文模板化，使其阅读直观，且可便于集中管理
- 无需写适配对象，使其代码更加清爽。

## 用法：
- 首先配置xml模板,如：

```
	<?xml version="1.0" encoding="GBK"?>
	<data>
		<!-- x-repeat为迭代$.classs对象,并添加对应节点。$.classs为根对象下的classs属性,$代表为根对象 -->
		<class x-repeat="class in $.classes">
			<!-- x-model为将该节点text赋值为class.no -->
			<id x-model="class.no"></id>
			<name x-model="class.name"></name>
			<!-- x-if表达式的值为真则输出该节点，假则不输出 。x-repeat与x-if哪个位置在前先执行哪个 -->
			<stu x-repeat="stu in $.students" x-if="class.no==stu.classno">
				<name x-model="stu.name"></name>
				<age x-model="stu.age">20</age>
				<!-- transCode为转码，代表将0值输出为女 -->
				<sex x-model="stu.sex|transCode:{0=女,1=男}"></sex>
				<!-- dateFormat 为时间格式化 -->
				<birthday x-model="stu.birthday|dateFormat:yyyy-MM-dd"></birthday>
				<!-- numberFormat 为数字格式化 -->
				<score x-model="stu.score|numberFormat:##.#"></score>
			</stu>
		</class>
	</data>
```

- 使用方法:

```
XmlBuildAndParse.buildxml(school, xmlTemplate);
```
school为Javabean数据对象，模板中$.classes是取shool下的classes对象，。$代表为根school对象。school代码如下

```
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
```
xmlTemplate为上述配置的xml模板

- 输出报文目标报文为：

```
<?xml version="1.0" encoding="GBK"?>
<data>
	<class1>01001</class1>
	<class>
		<id>01001</id>
		<name>一年级一班</name>
		<stu>
			<name>嘻嘻</name>
			<age>7</age>
			<sex>男</sex>
			<birthday>2020-12-18</birthday>
			<score>97.7</score>
		</stu>
	</class>
	<class>
		<id>01002</id>
		<name>一年级二班</name>
		<stu>
			<name>哈哈</name>
			<age>6</age>
			<sex>女</sex>
			<birthday>2020-12-18</birthday>
			<score>96.9</score>
		</stu>
	</class>
</data>
```

