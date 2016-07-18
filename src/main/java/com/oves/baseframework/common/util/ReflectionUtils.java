package com.oves.baseframework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author jin.qian
 */
public class ReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     *                       父类中的方法对象
     */
    public static Method getDeclaredMethod(Object object, String methodName,
                                           Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Throwable ex) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }

        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     *                       父类中方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象  
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        try {
            //抑制Java对方法进行检查,主要是针对私有方法而言
            if (null != method) {
                method.setAccessible(true);
                //调用object 的 method 所代表的方法，其方法的参数是 parameters  
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            LogUtils.error(logger, "invokeMethod %s process methodName:[%s] error[%s]. ", object, methodName, e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtils.error(logger, "invokeMethod %s process methodName:[%s] error[%s]. ", object, methodName, e.getMessage());
        } catch (InvocationTargetException e) {
            LogUtils.error(logger, "invokeMethod %s process methodName:[%s] error[%s]. ", object, methodName, e.getMessage());
        }

        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     *                  父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Throwable ex) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }

        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);

        try {
            //将 object 中 field 所代表的值 设置为 value  
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            LogUtils.error(logger, "setFieldValue %s process fieldName:[%s] error[%s]. ", object, fieldName, e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtils.error(logger, "setFieldValue %s process fieldName:[%s] error[%s]. ", object, fieldName, e.getMessage());
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     *                  父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获取 object 中 field 所代表的属性值  
            return field.get(object);
        } catch (Exception e) {
            LogUtils.error(logger, "getFieldValue %s process fieldName:[%s] error[%s]. ", object, fieldName, e.getMessage());
        }

        return null;
    }

    /**
     * 调用属性set方法
     *
     * @param fieldName
     * @param obj
     * @param value
     */
    public static void writeField(String fieldName, Object obj, Object value) {
        try {
            Class tClass = obj.getClass();
            Field field = tClass.getDeclaredField(fieldName);
            if (field != null) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tClass);
                //获得set方法
                Method method = pd.getWriteMethod();
                method.invoke(obj, value);
                logger.debug("field:" + field.getName() + "---getValue:" + value);
            }
        } catch (Exception ex) {
            logger.error("set field[%s] error", fieldName, ex);
        }
    }
}
