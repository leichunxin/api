package com.hdu.newlife.guava;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

public class BaseTest {

	public static void main(String[] args) {
		int[] numbers = { 1, 2, 3, 4, 5 };
		String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));
		System.out.println("numbersAsString=" + JSON.toJSONString(numbersAsString));
		String numbersAsStringDirectly = Ints.join(";", numbers);
		System.out.println("numbersAsStringDirectly=" + JSON.toJSONString(numbersAsStringDirectly));
		
		List<?> split = Splitter.on( ";" ).splitToList(numbersAsString);  
		System.out.println("split=" + JSON.toJSONString(split));
		String testString =  "foo , what,,,more," ;  
		//忽略空字符
		List<String> split1 = Splitter.on( "," ).omitEmptyStrings().trimResults().splitToList(testString); 
		System.out.println("split1=" + JSON.toJSONString(split1));
	}

}
