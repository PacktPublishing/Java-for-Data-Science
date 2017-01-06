import static java.lang.System.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidatingData {

	public static void main(String[] args) {

		String testData = "François Moreau";
		//String testData = "12334";
		String type = "int";
		String dateFormat = "MM/dd/yyyy";

		//validateText(testData, type);
		//out.println(validateDate(testData,dateFormat));

		//out.println(validateEmail(testData));
		//out.println(validateEmailStandard(testData));

		//validateZip(testData);
		testData = "Bobby Smith, Jr.";
		validateName(testData);
		testData = "Bobby Smith the 4th";
		validateName(testData);
		testData = "Albrecht Müller";
		validateName(testData);
		testData = "François Moreau";
		validateName(testData);

	}

	public static void validateText(String toValidate, String format){
		switch (format) {
		case "int": out.println(validateInt(toValidate)); 
		case "float": out.println(validateFloat(toValidate)); 
		}
	}

	public static String validateInt(String text){
		String result = text + " is not a valid integer";
		try{
			int validInt = Integer.parseInt(text);
			result = validInt + " is a valid integer";
			out.println(result);
			return result;
		}catch(NumberFormatException e){
			out.println(result);
			return result;
		}
	}

	public static String validateFloat(String text){
		String result = "Data '" + text + "' is not a valid floating point number";
		try{
			float validFloat = Float.parseFloat(text);
			result = validFloat + " is a valid floating point number";
			return result;
		}catch(NumberFormatException e){
			return result;
		}
	}

	public static String validateDate(String theDate, String dateFormat){

		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Date test = format.parse(theDate);
			if(format.format(test).equals(theDate)){
				return theDate.toString() + " is a valid date";
			}else{
				return theDate.toString() + " is not a valid date";
			}
		} catch (ParseException e) {
			return theDate.toString() + " is not a valid date";
		}

	}

	public static String validateEmail(String email) {
		//String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		String emailRegex = "^[a-zA-Z0-9.!$'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			return email + " is a valid email address";
		}else{
			return email + " is not a valid email address";
		}
	}

	//make note about limitations of this standard library
	//see comments http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
	public static String validateEmailStandard(String email){
		try{
			InternetAddress testEmail = new InternetAddress(email);
			testEmail.validate();
			return email + " is a valid email address";
		}catch(AddressException e){
			return email + " is not a valid email address";
		}
	}

	public static void validateZip(String zip){
		//Note can be useful to customize postal code formats across the world
		String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(zipRegex);
		Matcher matcher = pattern.matcher(zip);
		if(matcher.matches()){
			out.println(zip + " is a valid zip code");
		}else{
			out.println(zip + " is not a valid zip code");
		}

	}

	public static void validateName(String name){
		//discuss different name formats, 
		String nameRegex = "^[\\p{L}\\s.',-]+$";
		Pattern pattern = Pattern.compile(nameRegex);
		Matcher matcher = pattern.matcher(name);
		if(matcher.matches()){
			out.println(name + " is a valid name");
		}else{
			out.println(name + " is not a valid name");
		}

	}

}