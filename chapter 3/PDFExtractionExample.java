package packt.pdfextractionexample;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFExtractionExample {

    public static void main(String[] args) {
        try {
            PDDocument document = PDDocument.load(new File("PDF File.pdf"));
                PDFTextStripper Tstripper = new PDFTextStripper();
                String documentText = Tstripper.getText(document);
                System.out.println(documentText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
