package com.weilun.api.poi.word;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIWordUtils2 {

    private static final String regular1 = "[一二三四五六七八九十]、";
    private static final String regular2 = ".*";
    private static final String regular3 = "((\\d\\.)|(（[一二三四五六七八九十]）)).*";
    private static final String regular4 = "（[一二三四五六七八九十]）.{1,4}";
    private static final String regular5 = "\\d\\.";
    private static final String regular6 = ".*\\d{4}年\\d{1,2}月\\d{1,2}日星期.";
    private static final String regular7 = "\\d{4}年\\d{1,2}月\\d{1,2}日";

    public static Set<WordPoJo> getWordContent(File wordFile, int periodNum) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(wordFile);

        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        //获取段落集合9
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
        // 需要返回的内容
        StringBuilder section = new StringBuilder();
        String date = null;

        Set<WordPoJo> wordPoJoSet = new LinkedHashSet<>();
        WordPoJo wordPoJo = null;
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            // 是否居中LEFT、CENTER、RIGHT
            String alignment = paragraph.getAlignment().toString();
            //当前段落文本
            String text = paragraph.getText().replaceAll("\\(\n \n\\)", "").strip();
            if (Strings.isNotEmpty(text)) {
                if (wordPoJo == null && section.length() == 0 && Pattern.matches(regular6, text)) {
                    Pattern compile = Pattern.compile(regular7);
                    Matcher matcher = compile.matcher(text);
                    if (matcher.find()) {
                        date = matcher.group();
                    }
                }
                if (determineLineBold(paragraph)) {
                    if (Pattern.matches(regular1 + regular2, text)) {
                        String trim = text.replaceAll(regular1, "").trim();
                        if (trim.equals("舆情热点") || trim.equals("舆情预警")|| trim.equals("舆情分析")) {
                            break;
                        }
                        if (trim.length() == 4) {
                            section.delete(0, section.length());
                            section.append(trim);
                        }
                    } else if ("CENTER".equals(alignment) && section.length() != 0) {
                        XWPFParagraph nextParagraph = paragraphs.get(i + 1);
                        String nextAlignment = nextParagraph.getAlignment().toString();
                        wordPoJo = new WordPoJo();
                        wordPoJo.setIssue(String.valueOf(periodNum));
                        wordPoJo.setSection(section.toString());
                        wordPoJo.setDate(date);
                        if ("CENTER".equals(nextAlignment)) {
                            wordPoJo.setTitle((text + nextParagraph.getText()).trim());
                            i++;
                        } else {
                            wordPoJo.setTitle(text.trim());
                        }
                    } else if (periodNum <= 37 && Pattern.matches(regular3, text) && section.length() != 0&&text.length()<=50) {
                        if (text.matches(regular4)) {
                            continue;
                        }
                        wordPoJo = new WordPoJo();
                        wordPoJo.setIssue(String.valueOf(periodNum));
                        wordPoJo.setSection(section.toString());
                        wordPoJo.setDate(date);
                        XWPFParagraph nextParagraph = paragraphs.get(i + 1);
                        String nextAlignment = nextParagraph.getAlignment().toString();
                        if ("CENTER".equals(nextAlignment)) {
                            wordPoJo.setTitle((text.replaceAll(regular5, "") + nextParagraph.getText()).trim());
                            i++;
                        } else {
                            wordPoJo.setTitle(text.replaceAll(regular5, "").trim());
                        }
                    } else if (wordPoJo != null && section.length() != 0 && wordPoJo.getTitle() != null) {
                        wordPoJo.setContent(text + "\n");
                        wordPoJoSet.add(wordPoJo);
                    }
                } else if (wordPoJo != null && section.length() != 0 && wordPoJo.getTitle() != null) {
                    wordPoJo.setContent(text + "\n");
                    wordPoJoSet.add(wordPoJo);
                }
            }
        }

        fileInputStream.close();
        return wordPoJoSet;
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
        File files = new File("F:\\TRS\\TRS项目\\教育报\\教育改革情报word\\tt");
        File[] listFiles = files.listFiles((dir, name) -> name.endsWith(".docx"));
        Pattern compile = Pattern.compile("\\d+");
        Set<WordPoJo> wordPoJoSet = new LinkedHashSet<>();
        for (File file : listFiles) {
            String fileName = file.getName();
            System.out.println(fileName);
            Matcher matcher = compile.matcher(fileName);
            int qi = 0;
            if (matcher.find()) {
                qi += Integer.parseInt(matcher.group(0));
            }
            try {
                if (qi >= 1 && qi <= 37) {
                    wordPoJoSet.addAll(getWordContent(file, qi));
                }
                if (qi >= 38 && qi <= 53) {
                    wordPoJoSet.addAll(getWordContent(file, qi));
                }
                if (qi >= 54 && qi <= 69) {
                    wordPoJoSet.addAll(getWordContent(file, qi));
                }
                if (qi >= 70 && qi <= 297) {
                    wordPoJoSet.addAll(getWordContent(file, qi));
                }
                if (qi >= 298) {
                    wordPoJoSet.addAll(getWordContent(file, qi));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wordPoJoSet.remove(null);
        for (WordPoJo wordPoJo : wordPoJoSet) {
            try {
                Path path = Paths.get("F:\\TRS\\TRS项目\\教育报\\教育改革情报word\\tt\\教育改革情报抽取", wordPoJo.getSection(), wordPoJo.getTitle().replaceAll("[:?]","") + ".txt");
                Files.createDirectories(path.getParent());
                System.out.println(wordPoJo.getIssue() + "======" + wordPoJo.getTitle());
                Files.deleteIfExists(path);

                Files.writeString(path, wordPoJo.getTitle() + "\n" + wordPoJo.getContent(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
