package com.hdu.newlife.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 【FrameWork】I/O流工具类
 * 
 * <br>createDate：2013-11-14
 * <br>updateDate：2013-12-05
 * @version 1.0
 * @author newlife
 *
 */
public class IOUtils {

	/** 缓冲大小： */
	private static final int BUF_SIZE = 1024 * 4;
	
	/**
	 * 输出字节流信息(会关闭流)
	 * @param os				输出流
	 * @param data				数据
	 */
	public static void out(OutputStream os, byte[] data) throws IOException {
		ByteArrayInputStream bais = null;
		BufferedOutputStream bos = null;
		
		try {
			bais = new ByteArrayInputStream(data);
			bos = new BufferedOutputStream(os, BUF_SIZE);
			
			byte[] b = new byte[BUF_SIZE];
			int len;
			while ((len = bais.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			
		} finally {
			close(bais, bos);
		}
	}
	
	/**
	 * 从输入流中读取到信息，并返回一个byte[]数组(会关闭流)
	 * @param is				输入流
	 * @return
	 */
	public static byte[] getData(InputStream is) throws IOException {
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		
		try {
			bis = new BufferedInputStream(is, BUF_SIZE);
			baos = new ByteArrayOutputStream(BUF_SIZE);
			
			byte[] b = new byte[BUF_SIZE];
			int len;
			while ((len = bis.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			
			return baos.toByteArray();
		} finally {
			close(bis, baos);
		}
	}
	
	/** 关闭输入输出流： */
	public static void close(InputStream is, OutputStream os) {
		try {
			if (os != null) {
				os.close();
				os = null;
			}
		} catch (IOException e) {
		    throw new RuntimeException("输出流关闭时出现异常！", e);
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
			    throw new RuntimeException("输入流关闭时出现异常！", e);
			}
		}
	}
}
