package com.hdu.newlife.guava;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * 集合API的使用
 * 
 * @author lenovo
 *
 */
public class CollectTest {

	public static void main(String[] args) {
		// 简化创建工作
		// Map<String, Map<String, String>> map = Maps.newHashMap();
		// List<List<Map<String, String>>> list = Lists.newArrayList();
		// Set<String> personSet= Sets.newHashSet();
		// Integer[] intArrays= ObjectArrays.newArray(Integer.class,10);

		Set<String> set = Sets.newHashSet("one", "two", "three");
		System.out.println(JSON.toJSONString(set));
		List<String> list1 = Lists.newArrayList("one", "two", "three");
		System.out.println(JSON.toJSONString(list1));
		Map<String, String> map1 = ImmutableMap.of("ON", "TRUE", "OFF", "FALSE");
		System.out.println(JSON.toJSONString(map1));

		/**
		 * 不变性 很大一部分是google集合提供了不变性，不变对比可变： 数据不可改变 线程安全 不需要同步逻辑 可以被自由的共享 容易设计和实现 内存和时间高效
		 */
		Set<Integer> data = new HashSet<Integer>();
		data.addAll(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80));
		System.out.println("data=" + JSON.toJSONString(data));
		Set<Integer> fixedData = Collections.unmodifiableSet(data); // fixedData - [50, 70, 80, 20, 40, 10, 60, 30]
		data.add(90); // fixedData - [50, 70, 80, 20, 40, 10, 90, 60, 30]
		System.out.println("data=" + JSON.toJSONString(data));
		System.out.println("fixedData=" + JSON.toJSONString(fixedData));
		// 如何创建不可变的集合：
		ImmutableSet<Integer> numbers = ImmutableSet.of(10, 20, 30, 40, 50);
		System.out.println("numbers=" + JSON.toJSONString(numbers));
		// 使用copyOf方法
		ImmutableSet<Integer> another = ImmutableSet.copyOf(numbers);
		System.out.println("another=" + JSON.toJSONString(another));
		// 使用Builder方法
		ImmutableSet<Integer> numbers2 = ImmutableSet.<Integer> builder().addAll(numbers).add(60).add(70).add(80).build();
		System.out.println("numbers2=" + JSON.toJSONString(numbers2));
		// 创建不可变的集合
		ImmutableList<Integer> personImmutableList = ImmutableList.of(1, 2);
		System.out.println("personImmutableSet=" + JSON.toJSONString(personImmutableList));
		ImmutableSet<Integer> personImmutableSet = ImmutableSet.copyOf(numbers2);
		System.out.println("personImmutableSet=" + JSON.toJSONString(personImmutableSet));
		ImmutableMap<String, String> personImmutableMap = ImmutableMap.<String, String> builder().put("hell", "1").putAll(map1).build();
		System.out.println("personImmutableMap=" + JSON.toJSONString(personImmutableMap));

		/**
		 * 新的集合类型 MultiMap， MultiSet， Table， BiMap， ClassToInstanceMap
		 */

		// 一种key可以重复的map，子类有ListMultimap和SetMultimap，对应的通过key分别得到list和set
		Multimap<String, Integer> customersByType = ArrayListMultimap.create();
		customersByType.put("abc", 1);
		customersByType.put("abc", 2);
		customersByType.put("abc", 3);
		customersByType.put("abc", 4);
		customersByType.put("abcd", 5);
		customersByType.put("abcde", 6);
		for (Integer person : customersByType.get("abc")) {
			System.out.println(person);
		}

		// 不是集合，可以增加重复的元素，并且可以统计出重复元素的个数，例子如下：
		Multiset<Integer> multiSet = HashMultiset.create();
		multiSet.add(10);
		multiSet.add(30);
		multiSet.add(30);
		multiSet.add(40);
		System.out.println(multiSet.count(30)); // 2
		System.out.println(multiSet.size()); // 4
		
		//相当于有两个key的map，不多解释
		Table<Integer,Integer,Integer> personTable=HashBasedTable.create();
		personTable.put(1,20,1);
		personTable.put(0,30,2);
		personTable.put(0,25,3);
		personTable.put(1,50,4);
		personTable.put(0,27,5);
		personTable.put(1,29,6);
		personTable.put(0,33,7);
		personTable.put(1,66,8);
		//1,得到行集合
		Map<Integer,Integer> rowMap= personTable.row(0);
		int maxAge= Collections.max(rowMap.keySet());
		System.out.println("maxAge=" + JSON.toJSONString(maxAge));
		
		//是一个一一映射，可以通过key得到value，也可以通过value得到key； 
		//双向map
		BiMap<Integer,String> biMap=HashBiMap.create();
		biMap.put(1,"hello");
		biMap.put(2,"helloa");
		biMap.put(3,"world");
		biMap.put(4,"worldb");
		biMap.put(5,"my");
		biMap.put(6,"myc");
		int value= biMap.inverse().get("my");
		System.out.println("my --"+value);
		
		//
//		ClassToInstanceMap<Integer> classToInstanceMap =MutableClassToInstanceMap.create();
//		Integer person= 1;
//		Integer p1= 2;
//		Integer p2= 3;
//		classToInstanceMap.putInstance(Integer.class,person);
//		classToInstanceMap.putInstance(Integer.class,p1);
//		classToInstanceMap.putInstance(Integer.class,p2);
//		Integer person1=classToInstanceMap.getInstance(Integer.class);
//		System.out.println("person1=" + person1);
		
	}

}
