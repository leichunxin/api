package com.hdu.newlife.servlet;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "checkCodeServlet", urlPatterns = { "/checkCodeServlet" })
public class CheckCodeServlet extends BaseServlet {

	private static final long serialVersionUID = -2365502626509790187L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置不缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的相应图片
		response.setContentType("image/jpeg");
		IdentifyingCode idCode = new IdentifyingCode();
		BufferedImage image = new BufferedImage(idCode.getWidth(), idCode.getHeight(), BufferedImage.TYPE_INT_BGR);
		Graphics2D g = image.createGraphics();
		// 定义字体样式
		Font myFont = new Font("黑体", Font.BOLD, 16);
		// 设置字体
		g.setFont(myFont);

		g.setColor(idCode.getRandomColor(200, 250));
		// 绘制背景
		g.fillRect(0, 0, idCode.getWidth(), idCode.getHeight());

		g.setColor(idCode.getRandomColor(180, 200));
		idCode.drawRandomLines(g, 160);
		idCode.drawRandomString(4, g);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

}