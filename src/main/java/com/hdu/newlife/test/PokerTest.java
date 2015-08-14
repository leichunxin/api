package com.hdu.newlife.test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hdu.newlife.core.Poker;
import com.hdu.newlife.core.PokerUtil;

public class PokerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Poker> list = PokerUtil.initCardList();
		System.out.println(JSON.toJSONString(list.size()));
	}

}
