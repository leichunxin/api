package com.hdu.newlife.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerUtil {

	/**
	 * 静态初始化块
	 */
	static {

	}

	/**
	 * 私有构造方法
	 */
	private PokerUtil() {

	}

	/**
	 * 获取一副纸牌 链表实现
	 * 
	 * @return
	 */
	public static List<Poker> initPokerList() {
		List<Poker> pokerList = new ArrayList<Poker>();

		/**
		 * 初始化一副扑克牌链表
		 */
		for (int i = 0; i < 26; i++) {
			pokerList.add(new Poker((i % 13) + 1, Poker.SPADE));
		}
		for (int i = 0; i < 26; i++) {
			pokerList.add(new Poker((i % 13) + 1, Poker.HEART));
		}
		for (int i = 0; i < 26; i++) {
			pokerList.add(new Poker((i % 13) + 1, Poker.CLUB));
		}
		for (int i = 0; i < 26; i++) {
			pokerList.add(new Poker((i % 13) + 1, Poker.DIAMOND));
		}
		pokerList.add(new Poker(Poker.JOKER));
		pokerList.add(new Poker(Poker.JOKER));
		pokerList.add(new Poker(Poker.KING));
		pokerList.add(new Poker(Poker.KING));

		return pokerList;
	}

	/**
	 * 获取一副纸牌 ，用于小砍刀
	 * 
	 * @return
	 */
	public static List<Poker> initCardList() {
		List<Poker> pokerList = new ArrayList<Poker>();

		/**
		 * 初始化一副扑克牌链表
		 */
		for (int i = 0; i < 20; i++) {
			pokerList.add(new Poker((i % 10) + 1, Poker.SPADE));
		}
		for (int i = 0; i < 20; i++) {
			pokerList.add(new Poker((i % 10) + 1, Poker.HEART));
		}
		for (int i = 0; i < 20; i++) {
			pokerList.add(new Poker((i % 10) + 1, Poker.CLUB));
		}
		for (int i = 0; i < 20; i++) {
			pokerList.add(new Poker((i % 10) + 1, Poker.DIAMOND));
		}

		return pokerList;
	}

	/**
	 * 获取打乱的扑克牌列表
	 * 
	 * @return
	 */
	public static List<Poker> getShuffledPokerList() {
		List<Poker> pokerList = initPokerList();
		Collections.shuffle(pokerList);
		return pokerList;
	}

	/**
	 * 打乱扑克牌列表
	 * 
	 * @param pokerList
	 * @return
	 */
	public static List<Poker> shufflePokerList(List<Poker> pokerList) {
		Collections.shuffle(pokerList);
		return pokerList;
	}

}
