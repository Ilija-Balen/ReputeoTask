package com.example.reputeo.taskReputeo.utils;

import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.InvalidData;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkDownUtil {
    private static final Parser parser = Parser.builder().build();
    private static final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public static String markdownToHtml(String markdown) {
        if (markdown ==null){
            throw new InvalidData(ExceptionMessages.INVALID_DATA.toString());
        }
        return renderer.render(parser.parse(markdown));
    }

    public static String htmlToMarkdown(String html) {
        if (html ==null){
            throw new InvalidData(ExceptionMessages.INVALID_DATA.toString());
        }
        FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder().build();
        return converter.convert(html);
    }
}
