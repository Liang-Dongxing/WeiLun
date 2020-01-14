package com.weilun.api.poi.word;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalAlignRun;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIWordUtils {


    private static String regular1 = "[一二三四五六七八九十]、";
    private static String regular2 = ".*";
    private static String regular3 = ".*第.*\\d+.*期.*";
    private static String regular4 = "\\d+";
    private static String tab = "\t";

    public static String getWordContent(File wordFile, String labelString, int periodNum) throws IOException {
        String label = String.format("（%s.+）", labelString);
        FileInputStream fileInputStream = new FileInputStream(wordFile);

        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        //获取段落集合
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
        StringBuilder period = new StringBuilder().append(periodNum).append(tab).append("第" + periodNum + "期").append(tab);
        // 需要返回的内容
        StringBuilder section = new StringBuilder();
        StringBuilder title = new StringBuilder();
        StringBuilder labelStringLine = new StringBuilder();
        StringBuilder resultStringLine = new StringBuilder();

        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            // 是否居中LEFT、CENTER、RIGHT
            String alignment = paragraph.getAlignment().toString();
            //当前段落文本
            String text = paragraph.getText().replaceAll("\\(\n \n\\)", "").strip();
            if (Strings.isNotEmpty(text)){
                if (determineLineBold(paragraph)) {
                    if (Pattern.matches(regular1 + regular2, text)) {
                        String trim = text.replaceAll(regular1, "").trim();
                        if (trim.length() == 4) {
                            section.delete(0, section.length());
                            section.append(trim).append(tab);
                        }
                    }
                    if (paragraph.getNumID() != null) {
                        String trim = text.trim();
                        if (trim.length() == 4) {
                            section.append(trim).append(tab);
                        }
                    }
                    if ("CENTER".equals(alignment) && title.length() == 0) {
                        XWPFParagraph nextParagraph = paragraphs.get(i + 1);
                        String nextAlignment = nextParagraph.getAlignment().toString();
                        if ("CENTER".equals(nextAlignment)) {
                            title.append(text).append(nextParagraph.getText()).append(tab);
                        } else {
                            title.append(text).append(tab);
                        }
                    }
                }
                if (Pattern.matches(label, text) && !determineLineBold(paragraph)) {
                    labelStringLine.append(period).append(section).append(title);
                    if (!text.contains("本期编辑")) {
                        if (text.contains("，")) {
                            String[] splits = text.replaceAll(String.format("[（%s|）]", labelString), "").split("，");
                            for (String split : splits) {
                                labelStringLine.append(split.trim()).append(tab);
                            }
                            resultStringLine.append(labelStringLine).append("\n");
                            title.delete(0, title.length());
                            labelStringLine.delete(0, labelStringLine.length());
                        } else if (text.contains(" ")) {
                            String[] splits = text.replaceAll(String.format("[（%s|）]", labelString), "").split(" ");
                            for (String split : splits) {
                                labelStringLine.append(split.trim()).append(tab);
                            }
                            resultStringLine.append(labelStringLine).append("\n");
                            title.delete(0, title.length());
                            labelStringLine.delete(0, labelStringLine.length());
                        }
                    }
                }
            }
        }

        fileInputStream.close();
        return resultStringLine.toString();
    }

    private static boolean determineLineBold(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            if (run.isBold()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        File files = new File("F:\\TRS\\TRS项目\\教育报\\教育改革情报word");
        Path path = Paths.get("F:\\TRS\\TRS项目\\教育报\\label.txt");
        File[] listFiles = files.listFiles((dir, name) -> name.endsWith(".docx"));
        Pattern compile = Pattern.compile("\\d+");
        for (File file : listFiles) {
            String fileName = file.getName();
            Matcher matcher = compile.matcher(fileName);
            int qi = 0;
            if (matcher.find()) {
                qi += Integer.parseInt(matcher.group(0));
            }
            try {
                if (qi >= 38 && qi <= 53) {
                    String wordContent = getWordContent(file, "标签：", qi);
                    Files.writeString(path, wordContent, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                if (qi >= 54 && qi <= 69) {
                    String wordContent = getWordContent(file, "关键词：", qi);
                    Files.writeString(path, wordContent, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                if (qi >= 70 && qi <= 297) {
                    String wordContent = getWordContent(file, "关键词：", qi);
                    Files.writeString(path, wordContent, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                if (qi >= 298) {
                    String wordContent = getWordContent(file, "", qi);
                    Files.writeString(path, wordContent, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
