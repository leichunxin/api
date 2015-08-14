package com.hdu.newlife.base.db.map;

import java.io.Serializable;

/**
 * 数据映射类
 */
public class MappingData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5051257007902027488L;
	
	private MappingClass mappingClass=null;
	private MappingTable mappingTable=null;
	
	public MappingData()
	{
		this(new MappingClass(),new MappingTable());
	}
	
	public MappingData(MappingClass mappingClass,MappingTable mappingTable)
	{
		this.mappingClass=mappingClass;
		this.mappingTable=mappingTable;
	}

	public MappingClass getMappingClass() {
		return mappingClass;
	}

	public void setMappingClass(MappingClass mappingClass) {
		this.mappingClass = mappingClass;
	}

	public MappingTable getMappingTable() {
		return mappingTable;
	}

	public void setMappingTable(MappingTable mappingTable) {
		this.mappingTable = mappingTable;
	}
	
	@Override
	public String toString()
	{
		return mappingClass.toString()+"="+mappingTable.toString();
	}
}
