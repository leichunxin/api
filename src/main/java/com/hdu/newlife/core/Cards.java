package com.hdu.newlife.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author newlife
 * 
 */
public class Cards {

	/**
	 * 
	 */
	private static Poker[] pokerArray = new Poker[108];

	/**
	 * 
	 */
	private static List<Poker> pokerList = new ArrayList<Poker>();

	/**
	 * 静态初始化块
	 */
	static {
		/**
		 * 初始化数组
		 */
		for (int i = 0; i < 13; i++) {
			pokerArray[i] = new Poker(i + 1, Poker.SPADE);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 13] = new Poker(i + 1, Poker.SPADE);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 26] = new Poker(i + 1, Poker.HEART);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 39] = new Poker(i + 1, Poker.HEART);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 52] = new Poker(i + 1, Poker.CLUB);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 65] = new Poker(i + 1, Poker.CLUB);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 78] = new Poker(i + 1, Poker.DIAMOND);
		}
		for (int i = 0; i < 13; i++) {
			pokerArray[i + 91] = new Poker(i + 1, Poker.DIAMOND);
		}
		pokerArray[104] = new Poker(Poker.JOKER);
		pokerArray[105] = new Poker(Poker.JOKER);
		pokerArray[106] = new Poker(Poker.KING);
		pokerArray[107] = new Poker(Poker.KING);

		/**
		 * 初始化链表
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
	}

	/**
	 * 私有构造方法
	 */
	private Cards() {

	}

	/**
	 * 获取一副纸牌 数组实现
	 * 
	 * @return
	 */
	public static Poker[] getPokerArray() {
		return pokerArray;
	}

	/**
	 * 获取一副纸牌 链表实现
	 * 
	 * @return
	 */
	public static List<Poker> getPokerList() {
		return pokerList;
	}

}
