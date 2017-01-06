package packt.poiexamples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static java.lang.System.out;

// From: http://howtodoinjava.com/apache-commons/readingwriting-excel-files-in-java-poi-tutorial/
public class ReadExcelExample {

    public static void main(String[] args) {
        //Create Workbook instance holding reference to .xlsx file
        try (FileInputStream file = new FileInputStream(
                new File("Sample.xlsx"))) {
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
            for(Row row : sheet) {
                for (Cell cell : row) {
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
