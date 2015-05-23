
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class AlphabetizedList {
	public static void main(String[] args) throws IOException {
		
		//create scanner for user inout on file to read
		Scanner scan = new Scanner (System.in);
	    System.out.println("Enter a file name: ");
	    String fileName = scan.nextLine();
	    
	    //create two arraylists
		ArrayList<String> list = new ArrayList<String>();		
		ArrayList<String> alphaList = new ArrayList<String>();
		
		try {
			//read from file
			 File myFile = new File(fileName);
	         Scanner scanFile = new Scanner (myFile);
	         
	         //add to arraylist list
	         while (scanFile.hasNext()){
					String line = scanFile.next();
					list.add(line.toLowerCase());
		         }	
			
			for (int i = 0; i <= list.size() - 1; i++){
				alphaList.add(list.get(i));
			}
			
			//alphabetize and print
			Collections.sort(alphaList);	
			System.out.println(alphaList);
			
			//close scanners
			scan.close();
			scanFile.close();
			
		//exception handling	
		} catch (FileNotFoundException e) {
			System.out.println("Error. Program aborted.");
			System.exit(0);
		}		
	}
}