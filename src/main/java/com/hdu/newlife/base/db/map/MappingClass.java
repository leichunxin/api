package com.hdu.newlife.base.db.map;

import java.io.Serializable;

/**
 * 数据映射类（类）
 */
public class MappingClass implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5374376991148428908L;
	
	private String name=null;
	private String type=null;
	
	public MappingClass()
	{
		this("","");
	}
	
	public MappingClass(String name,String type)
	{
		this.name=name;
		this.type=type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return "Class:"+name+"-"+type;
	}
}
