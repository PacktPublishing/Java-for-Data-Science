
public class Dogs {

	private String name;
	private int age;
	
	public Dogs(){
		name = "Fido";
		age = 0;
	}
	
	public Dogs(String n){
		name = n;
		age = 0;
	}
	
	public Dogs(int a){
		name = "Fido";
		age = a;
	}
	
	public Dogs(String n, int a){
		name = n;
		age = a;
	}
	
	public String getName(){
		return name;
	}
	
	public int getAge(){
		return age;
	}
}
