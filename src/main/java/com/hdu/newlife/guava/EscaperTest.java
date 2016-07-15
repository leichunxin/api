package com.hdu.newlife.guava;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import com.google.common.xml.XmlEscapers;

public class EscaperTest {

	public static void main(String[] args) {
		// 将html转义如下源码所示
		Escaper htmlEscaper = HtmlEscapers.htmlEscaper();
		System.out.println(htmlEscaper.escape("<a>你好</a>"));

		// escaping HTML
		System.out.println(HtmlEscapers.htmlEscaper().escape("echo foo > file &"));
		// [result] echo foo > file &

		// escaping XML attributes and content
		System.out.println(XmlEscapers.xmlAttributeEscaper().escape("foo \"bar\""));
		// [result] echo "bar"

		System.out.println(XmlEscapers.xmlContentEscaper().escape("foo \"bar\""));
		// [result] foo "bar"
	}

}
