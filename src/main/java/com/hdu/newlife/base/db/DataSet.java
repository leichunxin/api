package com.hdu.newlife.base.db;

import java.util.*;
import java.sql.*;

public class DataSet 
{
	private ArrayList<String> columnList=new ArrayList<String>();//保存表的列名
	
	private ArrayList<ArrayList<Object>> dataList=new ArrayList<ArrayList<Object>>();//表的内容（每一行数据）
	
	public DataSet(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd=rs.getMetaData();//获取结果集的结构
		for (int i=1;i<=rsmd.getColumnCount();i++)
		{
			columnList.add(rsmd.getColumnName(i));//获取表头的名称
		}
		
		while(rs.next())
		{
			ArrayList<Object> TempList=new ArrayList<Object>();
			for (int i=1;i<=rsmd.getColumnCount();i++)
			{
				TempList.add(rs.getObject(i));
			}
			dataList.add(TempList);
		}
	}
	
	/**
	 * 获取表格中首行首列的值
	 */
	public Object GetFirst()
	{
		ArrayList<Object> TempList=(ArrayList<Object>)dataList.get(0);
		return TempList.get(0);
	}
	
	/**
	 * 获取表格中某一单元格的值
	 */
	public Object GetValue(int row,int column)
	{
		ArrayList<Object> TempList=(ArrayList<Object>)dataList.get(row);
		return TempList.get(column);
	}

	/**
	 * 获取表格中数据的行数
	 */
	public int GetRowCount()
	{
		return dataList.size();
	}
	
	/**
	 * 获取表格中列的数量
	 */
	public int GetColumnCount()
	{
		return columnList.size();
	}
	
	/**
	 * 获取表格中列的名称
	 */
	public Object GetColumnName(int column)
	{
		return columnList.get(column);
	}
	
}
