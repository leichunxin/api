package com.hdu.newlife.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DistanceUtil {

	public static String distancePost(String lat_s, String lng_s, String lat_e, String lng_e) throws IOException {
		String url_str = "http://api.map.baidu.com/direction/v1/routematrix?output=json&origins=" + lat_s + "," + lng_s + "&destinations=" + lat_e + "," + lng_e + "&ak=yjBglub26HG4A7D7ldzFa4a5";

		URL url = new URL(url_str);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		String sCurrentLine = "";
		String sTotalLine = "";
		InputStream urlStream = con.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream, "UTF-8"));
		while ((sCurrentLine = reader.readLine()) != null) {
			sTotalLine = sTotalLine + sCurrentLine;
		}

		JSONObject json = JSON.parseObject(sTotalLine);
		String message = json.getString("message");
		if ("ok".equals(message)) {
			JSONObject result = (JSONObject) json.get("result");
			String rs = result.getString("elements");
			rs = rs.substring(1, rs.length() - 1);
			JSONObject element = JSON.parseObject(rs);
			JSONObject distance = (JSONObject) element.get("distance");
			String value = distance.getString("value");
			if (null != value && !"".equals(value)) {
				value = (Double.valueOf(value) / 1000) + "";
			}
			return value;
		} else
			return "";
	}

}
