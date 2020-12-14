package test.genericTypes;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

public class GenericTypesTest {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {

		Field list = genericTypeVo.class.getField("a");
		// ���Զ�Ӧ��Class�����List��������
		if (List.class.isAssignableFrom(list.getType())) {
			// ��� Type
			Type genericType = list.getGenericType();
			// ParameterizedType �������ʵ���˲��������ͽӿ�
			if (genericType instanceof ParameterizedType) {
				// ��÷�������
				Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
				// WildcardType ���ʹ����ͨ���
				// �ڷ����п���ͨ��ͨ���������һ���������ͣ���ʹ��?��ͬʱ����ָ��?�����±߽硣���ϱ߽��ͨ����� List<? extends Number>
				// list�����±߽��ͨ���:List<? super Integer> list��
				// getUpperBounds������ϱ߽硣
				// ����List<? extends Number> list�ϱ߽�ΪNumber����List<? super Integer>
				// list�ϱ߽���Ϊ:Object��
				if (type instanceof WildcardType) {
					WildcardType wildcardType = (WildcardType) type;
					Type[] upperBounds = wildcardType.getUpperBounds();
					if (upperBounds.length == 1) {
						Type actualTypeArgument = upperBounds[0];
						System.out.println("��÷����ϱ߽�����:" + actualTypeArgument);
					}
				} else if(type instanceof ParameterizedType){
					
					System.out.println("��÷������ͷ���:" +((ParameterizedType) type).getActualTypeArguments()[1]);
					System.out.println("��÷�������:" +(Class)((ParameterizedType) type).getRawType());
					
				}
			}
		}

	}
}