import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Optional;

public class DataImputation {

	public static void main(String[] args) {

		tempExample();

	}

	public static void tempExample(){
//		double[] tempList = new double[365];
//		for(int x = 0; x < tempList.length; x++){
//			tempList[x] = Math.random()*100;
//		}
//		tempList[5] = 0;
//		double sum = 0;
//		for(double d : tempList){
//			out.println(d);
//			sum += d;
//		}
//		out.println(sum/365);
		String useName = "";
		String[] nameList = {"Amy","Bob","Sally","Sue","Don","Rick",null,"Betsy"};
		Optional<String> tempName;
		for(String name : nameList){
			tempName = Optional.ofNullable(name);
			useName = tempName.orElse("DEFAULT");
			out.println("Name to use = " + useName);
		}
	}
}
