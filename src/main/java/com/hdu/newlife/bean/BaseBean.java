package com.hdu.newlife.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名: BaseBean 描述： 前台数据基础Bean，前台FormBean必须继承BaseBean。
 * 
 * @author newlife
 * 
 */
public abstract class BaseBean implements Serializable {

	private static final long serialVersionUID = -2462510018255864550L;

	private Boolean success = Boolean.TRUE;

	private long status;

	private Object result = new Object();

	private String msg = "";

	private List<ErrorBean> errorList = new ArrayList<ErrorBean>();

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Object getResult() {
		if (null == this.result) {
			return new Object();
		}
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		if (null == this.msg) {
			return "";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ErrorBean> getErrorList() {
		if (null == errorList) {
			return new ArrayList<ErrorBean>();
		}
		return errorList;
	}

	public void setErrorList(List<ErrorBean> errorList) {
		this.errorList = errorList;
	}

	public void setError(ErrorBean bean) {
		if (null == errorList) {
			this.errorList = new ArrayList<ErrorBean>();
		}
		this.errorList.add(bean);
	}

	protected abstract void validate();

}
