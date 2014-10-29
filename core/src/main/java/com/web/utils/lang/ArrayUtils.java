package com.web.utils.lang;
/**  
 *   
 * ArrayUtils.java Create on 2012-2-28 11:32:51  
 * @author <a href="chenliguo@coo8.com">chenliguo</a> 
 */

public class ArrayUtils {
	
	public static Object[][] getArrayByColumnValue(Object[][] objectArray ,Object findToValue ,int columnIndex){
		Object[][] resultArray = null;
		for(int i = 0 ; i < objectArray.length; i++){
			if(findToValue.equals(objectArray[i][columnIndex])){
				resultArray = (Object[][])addElement(null, resultArray,objectArray[i],false);
			}
		}
		return resultArray;
	}
	
	public static Object[] removeElementByColumnValue(Object[] objectArray ,Object findToValue){
		if(objectArray == null || findToValue == null){
			return null;
		}
		for(int i = 0; i <objectArray.length; i++){
			if(findToValue.equals(objectArray[i])){
				objectArray = (Object[])removeElement(null, objectArray, i);
			}
		}
		return objectArray;
	}
	public static void main(String[] args){
		Object o = "1";
		System.out.println(String.format("%s is ",o));
	}
	//����ʹ��org.apache.commons.lang.ArrayUtils.add(array1, array2)
	public static <T> T[] add(T[] array1, T[] array2) {
		if(array1 == null && array2 == null){
			return null;
		}
		if (array2 == null || array2.length == 0)
			return array1;
		if (array1 == null || array1.length == 0)
			return array2;
		Class<?> elementClass = array1.getClass().getComponentType();
		T[] array = (T[]) java.lang.reflect.Array.newInstance(elementClass,
				array1.length + array2.length);
		System.arraycopy(array1, 0, array, 0, array1.length);
		System.arraycopy(array2, 0, array, array1.length, array2.length);
		return array;
	}

	public static <T> T[] addElement(T[] sourceArray, T value) {
		return (T[]) addElement((Class<T>) null, sourceArray, value, true);
	}
	//
	public static Object addElement(Class elementClass, Object sourceArray,
			Object element, boolean isSameContinue) {
		int arrayLength = sourceArray == null ? 0 : java.lang.reflect.Array
				.getLength(sourceArray);
		if (isSameContinue)
			for (int i = 0; i < arrayLength; i++) {
				Object x = java.lang.reflect.Array.get(sourceArray, i);
				if (x == element || (x != null && x.equals(element)))
					return sourceArray;
			}
		if (elementClass == null && element != null)
			elementClass = element.getClass();
		Object targetArray = java.lang.reflect.Array.newInstance(elementClass,
				arrayLength + 1);// new ?[n+1];
		if (arrayLength > 0)
			System.arraycopy(sourceArray, 0, targetArray, 0, arrayLength);
		java.lang.reflect.Array.set(targetArray, arrayLength, element);
		return targetArray;
	}

	public static Object[] getArrayByColumnIndex(Object[][] objectArray,int index){
		if(objectArray != null){
			Object[] resultArray = null;
			System.out.println("objectArray length is "+objectArray.length+" start add order id to array...");
			for(int i = 0 ; i < objectArray.length; i++){
				resultArray = addElement(resultArray,objectArray[i][index]);
			}
			return resultArray;
		}
		return null;
	}
	
	public static Object removeElement(Class elementClass, Object sourceArray,
			int rowNumber) {
		if (sourceArray == null)
			return null;
		int arrayLength = java.lang.reflect.Array.getLength(sourceArray);
		if (rowNumber >= 0 && rowNumber < arrayLength) {
			if (elementClass == null)
				elementClass = sourceArray.getClass().getComponentType();
			Object newArray = java.lang.reflect.Array.newInstance(elementClass,
					arrayLength - 1);
			System.arraycopy(sourceArray, 0, newArray, 0, rowNumber);
			System.arraycopy(sourceArray, rowNumber + 1, newArray, rowNumber, arrayLength
					- 1 - rowNumber);
			return newArray;
		}
		return sourceArray;
	}
}
