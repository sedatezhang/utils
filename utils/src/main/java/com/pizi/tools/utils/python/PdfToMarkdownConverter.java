package com.pizi.tools.utils.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PdfToMarkdownConverter {

    private static final String PYTHON_SCRIPT_PATH = "D:\\Code\\pizi\\utils\\src\\fools\\utils\\pdf_to_markdown.py";

    /**
     * 将指定路径的 PDF 文件转换为 Markdown 格式
     *
     * @param pdfFilePath PDF 文件路径
     * @param outputMdPath 输出的 Markdown 文件路径
     */
    public static void convertPdfToMarkdown(String pdfFilePath, String outputMdPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", PYTHON_SCRIPT_PATH, pdfFilePath, outputMdPath);
            Process process = pb.start();

            // 读取输出流
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[STDOUT] " + line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println("[STDERR] " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String pdfPath = "D:\\Code\\pizi\\utils\\src\\main\\resources\\tools\\test.pdf";
        String mdOutputPath = "D:\\Code\\pizi\\utils\\src\\main\\resources\\tools\\test_output.md";

        convertPdfToMarkdown(pdfPath, mdOutputPath);
    }
}

//PdfToMarkdownConverter.convertPdfToMarkdown(
//    "path/to/your/test.pdf",
//            "path/to/output.md"
//);
