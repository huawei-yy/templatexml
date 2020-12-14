package test.genericTypes;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

public class GenericTypesTest {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {

		Field list = genericTypeVo.class.getField("a");
		// 属性对应的Class如果是List或其子类
		if (List.class.isAssignableFrom(list.getType())) {
			// 获得 Type
			Type genericType = list.getGenericType();
			// ParameterizedType 如果超类实现了参数化类型接口
			if (genericType instanceof ParameterizedType) {
				// 获得泛型类型
				Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
				// WildcardType 如果使用了通配符
				// 在泛型中可以通过通配符来声明一个泛型类型，即使用?。同时可以指明?的上下边界。带上边界的通配符： List<? extends Number>
				// list；带下边界的通配符:List<? super Integer> list。
				// getUpperBounds：获得上边界。
				// 对于List<? extends Number> list上边界为Number，而List<? super Integer>
				// list上边界则为:Object。
				if (type instanceof WildcardType) {
					WildcardType wildcardType = (WildcardType) type;
					Type[] upperBounds = wildcardType.getUpperBounds();
					if (upperBounds.length == 1) {
						Type actualTypeArgument = upperBounds[0];
						System.out.println("获得泛型上边界类型:" + actualTypeArgument);
					}
				} else if(type instanceof ParameterizedType){
					
					System.out.println("获得泛型类型泛型:" +((ParameterizedType) type).getActualTypeArguments()[1]);
					System.out.println("获得泛型类型:" +(Class)((ParameterizedType) type).getRawType());
					
				}
			}
		}

	}
}