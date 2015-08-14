package com.hdu.newlife.base.db;

import java.sql.*;
import java.util.*;
import java.lang.reflect.*;

import com.hdu.newlife.base.db.map.*;

public class Persistence 
{
    public static ArrayList<Object> loadResultToSet(ResultSet rs,String className) throws SQLException 
    {
    	Class<?> demo = null;
    	ArrayList<MappingData> mapList=new ArrayList<MappingData>();
    	//=========================================================================
    	try 
		{
			demo = Class.forName(className);
			Field[] field=demo.getDeclaredFields();
			for (int i=0;i<field.length;i++)
			{
				MappingData map=new MappingData();
				map.getMappingClass().setName(field[i].getName());
				map.getMappingClass().setType(field[i].getType().getName());
				mapList.add(map);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//=========================================================================
		ResultSetMetaData rsmd=rs.getMetaData();//获取结果集的结构
		for (int i=1;i<=rsmd.getColumnCount();i++)
		{
			String name=rsmd.getColumnName(i);
			String type=rsmd.getColumnTypeName(i);
			for (int j=0;j<mapList.size();j++)
			{
				if (mapList.get(j).getMappingClass().getName().equalsIgnoreCase(name))
				{
					mapList.get(j).getMappingTable().setName(name);
					mapList.get(j).getMappingTable().setType(type);
					break;
				}
			}
		}
		//=========================================================================
		ArrayList<Object> dataSet=new ArrayList<Object>();
		try
		{
			while(rs.next())
			{
				Class<?> temp = Class.forName(className);
				Object obj = temp.newInstance();
				for (int i=0;i<mapList.size();i++)
				{
					String name=mapList.get(i).getMappingClass().getName();
					String type=mapList.get(i).getMappingClass().getType();
					Object value=rs.getObject(mapList.get(i).getMappingTable().getName());
					//System.out.println(obj+"-"+name+"-"+value+"-"+Class.forName(type));
					if (value!=null && !value.toString().equals(""))
						Persistence.setter(obj,name,value,Class.forName(type));
				}
				dataSet.add(obj);
			}
		}
		catch(Exception e)
		{
			dataSet=null;
			e.printStackTrace();
		}
		return dataSet;
    }
	
	/**
	 * 执行实体Bean的get方法
     * @param obj 操作的对象
     * @param att 操作的属性
     */
    public static void getter(Object obj, String att) 
    {
        try 
        {
        	att=att.replaceFirst(att.substring(0,1),att.substring(0,1).toUpperCase());
            Method method=obj.getClass().getMethod("get"+att);
            System.out.println(method.invoke(obj));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
 
    /**
     * 执行实体Bean的set方法
     * @param obj 操作的对象
     * @param att 操作的属性
     * @param value 设置的值
     * @param type 参数的属性
     */
    public static void setter(Object obj, String att, Object value, Class<?> type) 
    {
        try 
        {
        	att=att.replaceFirst(att.substring(0,1),att.substring(0,1).toUpperCase());
            Method method=obj.getClass().getMethod("set"+att,type);
            String[] strList=type.toString().split("\\.");
            String strType=strList[strList.length-1];
            if(strType.equals("Long"))
            {
            	method.invoke(obj,Long.parseLong(value.toString()));
            }
            else if(strType.equals("Integer"))
            {
            	method.invoke(obj,Integer.parseInt(value.toString()));
            }
            else if(strType.equals("Float"))
            {
            	method.invoke(obj,Float.parseFloat(value.toString()));
            }
            else if(strType.equals("Double"))
            {
            	method.invoke(obj,Double.parseDouble(value.toString()));
            }
            else
            {
            	method.invoke(obj,value);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
