package com.hdu.newlife.core;

import java.io.Serializable;

/**
 * 扑克牌类
 * 
 * @author newlife
 * 
 */
public class Poker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6881991115576433737L;

	/**
	 * 黑桃
	 */
	public static final String SPADE = "spade";

	/**
	 * 红心
	 */
	public static final String HEART = "heart";

	/**
	 * 梅花
	 */
	public static final String CLUB = "club";

	/**
	 * 方块
	 */
	public static final String DIAMOND = "diamond";

	/**
	 * 大王
	 */
	public static final String KING = "king";

	/**
	 * 小王
	 */
	public static final String JOKER = "joker";

	/**
	 * 纸牌花色
	 */
	private String colour;

	/**
	 * 纸牌数字
	 */
	private int number;

	/**
	 * 大小王标志
	 */
	private String joker = "";

	/**
	 * 默认构造方法
	 */
	public Poker() {

	}

	/**
	 * 构造普通牌
	 * 
	 * @param number
	 * @param colour
	 */
	public Poker(int number, String colour) {
		this.number = number;
		this.colour = colour;
	}

	/**
	 * 构造大小王
	 * 
	 * @param joker
	 */
	public Poker(String joker) {
		this.joker = joker;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getJoker() {
		return joker;
	}

	public void setJoker(String joker) {
		this.joker = joker;
	}

}
