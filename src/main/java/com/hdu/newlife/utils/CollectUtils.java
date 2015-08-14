package com.hdu.newlife.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 【FrameWork】集合方面的工具类
 * 
 * <br>createDate：2012-11-12
 * <br>updateDate：2012-11-12
 * @version 1.0
 * @author newlife
 *
 */
public class CollectUtils {

    /**
     * Collection转化为List
     * 注意：一些特殊情况使用此方法会出错
     * @param clt				Collection
     * @return
     */
    public static <T> List<T> collectToList(Collection<T> clt) {
        List<T> list = new ArrayList<T>();

        Iterator<T> iter = clt.iterator();
        while (iter.hasNext()) {
            list.add(iter.next());
        }

        return list;
    }

    /**
     * Array转化为List
     * 注意：一些特殊情况使用此方法会出错
     * @param args				Array
     * @return
     */
    public static <T> List<T> arrayToList(T[] args) {
        List<T> list = new ArrayList<T>();

        for (T t : args) {
            list.add(t);
        }

        return list;
    }

    /**
     * Set转化为List
     * @param set				Set
     * @return
     */
    public static <T> List<T> setToList(Set<T> set) {
        List<T> list = new ArrayList<T>();

        for (T t : set) {
            list.add(t);
        }

        return list;
    }
}
