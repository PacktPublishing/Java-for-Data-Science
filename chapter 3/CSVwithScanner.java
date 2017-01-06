import static java.lang.System.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CSVwithScanner {
	public static void main(String[] args){


		try {
			File demoFile = new File("C:\\Users\\jreese\\workspace\\Packt Data Science\\Demographics.txt");
			Scanner getData = new Scanner(demoFile);
			while(getData.hasNext()){
				out.println(getData.nextLine());
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}



	}
}
