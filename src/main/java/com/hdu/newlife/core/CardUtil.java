package com.hdu.newlife.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardUtil {

	/**
	 * 静态初始化块
	 */
	static {

	}

	/**
	 * 私有构造方法
	 */
	private CardUtil() {

	}

	/**
	 * 获取一副纸牌 链表实现
	 * 
	 * @return
	 */
	public static List<Card> initCardList() {
		List<Card> cardList = new ArrayList<Card>();

		/**
		 * 初始化一副扑克牌链表
		 */
		for (int i = 0; i < 20; i++) {
			cardList.add(new Card((i % 10) + 1));
		}
		for (int i = 0; i < 20; i++) {
			cardList.add(new Card((i % 10) + 1));
		}
		for (int i = 0; i < 20; i++) {
			cardList.add(new Card((i % 10) + 1));
		}
		for (int i = 0; i < 20; i++) {
			cardList.add(new Card((i % 10) + 1));
		}

		return cardList;
	}

	/**
	 * 获取打乱的扑克牌列表
	 * 
	 * @return
	 */
	public static List<Card> getShuffledPokerList() {
		List<Card> cardList = initCardList();
		Collections.shuffle(cardList);
		return cardList;
	}

	/**
	 * 打乱扑克牌列表
	 * 
	 * @param pokerList
	 * @return
	 */
	public static List<Card> shufflePokerList(List<Card> cardList) {
		Collections.shuffle(cardList);
		return cardList;
	}

}
