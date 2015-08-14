package com.hdu.newlife.enums;

/**
 * 类名: Status 描述: 状态枚举
 * 
 * @author newlife
 * 
 */
public enum Status {

	NORMAL(1, "正常"), DELETE(0, "已删除");

	private int key;

	private String value;

	Status(int key, String value) {

		this.key = key;

		this.value = value;
	}

	/**
	 * @return 返回变量key的值
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @return 返回变量value的值
	 */
	public String getValue() {
		return value;
	}

	public static Status getStatusByKey(int key) {
		for (Status status : Status.values()) {
			if (status.getKey() == key) {
				return status;
			}
		}
		return null;
	}

	public static Status getStatusByValue(String value) {
		for (Status status : Status.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}

}
