package com.hdu.newlife.guava;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File readFile = new File(System.getProperty("user.dir") + "/src/main/resources/log.txt");
			StringBuilder content = new StringBuilder();
			if (readFile.exists()) {
				List<String> lines = Files.readLines(readFile, Charsets.UTF_8);
				for (String string : lines) {
					System.out.println(string);
					content.append(string + "\n");
				}
			}
			File writeFile = new File(System.getProperty("user.dir") + "/src/main/resources/log" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt");
			Files.write(content.toString(), writeFile, Charsets.UTF_8);

			final File sourceFile = new File(System.getProperty("user.dir") + "/src/main/resources/log.txt");
			final File targetFile = new File(System.getProperty("user.dir") + "/src/main/resources/conf/log.txt");
			Files.copy(sourceFile, targetFile);
			System.out.println(Files.equal(sourceFile, targetFile));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
