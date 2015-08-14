package com.hdu.newlife.base;

import java.sql.*;
import java.util.*;

public interface IBean 
{
	public List<Object> getResultSet(ResultSet rs) throws SQLException;
}
