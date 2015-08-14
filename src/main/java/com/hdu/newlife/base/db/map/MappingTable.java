package com.hdu.newlife.base.db.map;

import java.io.Serializable;

/**
 * 数据映射类（表）
 */
public class MappingTable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1864473153906695987L;
	
	private String name=null;
	private String type=null;
	
	public MappingTable()
	{
		this("","");
	}
	
	public MappingTable(String name,String type)
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
		return "Table:"+name+"-"+type;
	}
}
