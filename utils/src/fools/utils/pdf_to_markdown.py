# // ... existing code ...
# pdf_to_markdown.py
import sys
import PyPDF2

class PDFToMarkdownConverter:
    @staticmethod
    def extract_text(pdf_path):
        with open(pdf_path, 'rb') as file:
            reader = PyPDF2.PdfReader(file)
            text = ""
            for page in reader.pages:
                extracted = page.extract_text()
                if extracted:
                    text += extracted + "\n\n"
            return text

    @staticmethod
    def format_as_markdown(text):
        lines = text.split('\n')
        markdown_lines = []

        for line in lines:
            stripped = line.strip()
            if not stripped:
                continue
            elif stripped.startswith('#'):
                markdown_lines.append(f"## {stripped}")
            elif stripped.startswith('**') or stripped.startswith('__'):
                markdown_lines.append(f"### {stripped[2:-2]}")
            else:
                markdown_lines.append(stripped)

        return '\n\n'.join(markdown_lines)

    @staticmethod
    def convert(pdf_path, output_path):
        raw_text = PDFToMarkdownConverter.extract_text(pdf_path)
        markdown_text = PDFToMarkdownConverter.format_as_markdown(raw_text)
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(markdown_text)
        print(f"Markdown 文件已保存至：{output_path}")

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Usage: python pdf_to_markdown.py <pdf_path> <output_md_path>")
        sys.exit(1)

    PDFToMarkdownConverter.convert(sys.argv[1], sys.argv[2])
