package com.hdu.newlife.base;

import java.sql.*;
import java.util.*;

public class DataTable 
{
	private ArrayList<String> columnList=new ArrayList<String>();//保存查询结果返回的列名
	
	private ArrayList<ArrayList<Object>> dataList=new ArrayList<ArrayList<Object>>();//保存表格的数据内容
	
	public DataTable(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd=rs.getMetaData();//获取结果集的存储结构
		for (int i=1;i<=rsmd.getColumnCount();i++)
		{
			columnList.add(rsmd.getColumnName(i));
		}
		
		while(rs.next())
		{
			ArrayList<Object> tempList=new ArrayList<Object>();
			for (int i=1;i<=rsmd.getColumnCount();i++)
			{
				tempList.add(rs.getObject(i));
			}
			dataList.add(tempList);
		}
	}
	
	/**
	 * 获取返回结果首行首列的值
	 * @return 单元格的值
	 */
	public Object getFiret()
	{
		return dataList.get(0).get(0);
	}
	
	/**
	 * 返回某一个单元格的值
	 * @param row 行的索引
	 * @param col 列的索引
	 * @return 单元格的值
	 */
	public Object getValue(int row,int col)
	{
		return dataList.get(row).get(col);
	}
	
	/**
	 * 返回行的数量
	 * @return 行数
	 */
	public int getRowCount()
	{
		return dataList.size();
	}
	
	/**
	 * 返回列的数量
	 * @return 行数
	 */
	public int getColCount()
	{
		return columnList.size();
	}
	
	/**
	 * 返回列的名称
	 * @return 名称
	 */
	public String getColName(int index)
	{
		return columnList.get(index);
	}
}
