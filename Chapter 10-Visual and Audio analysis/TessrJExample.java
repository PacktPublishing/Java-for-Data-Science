package tessrj.example;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TessrJExample {

    public static void main(String[] args) {
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");
        try {
            String result;
            result = instance.doOCR(new File("OCRExample.png"));
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
