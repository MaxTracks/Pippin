package pippin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {

	public static void load(Memory mem, Code code, File file) throws FileNotFoundException{
		Scanner input = new Scanner(file);
		Boolean modeCheck = true;
		while(input.hasNextInt(16)){
			//reads a hexadecimal number into inp
			int inp = input.nextInt(16);
			
			//If inp is -1, simply switch to "data" mode
			if(inp == -1){modeCheck = false;}
			
			//Else if in "code" read the next line as int arg and use code.setCode(inp, arg);
			else if(inp == 1){
				try{int arg = Integer.parseInt(input.nextLine());}
				catch (ArrayIndexOutOfBoundsException e) {
		            JOptionPane.showMessageDialog(null, 
		                    e.getMessage(), 
		                    "Failure loading data", JOptionPane.WARNING_MESSAGE);
		        }
				code.setCode(inp, arg);
			}
			
			//Else read the next line as int val and use mem.setData(inp, val);
			else{
				try{int val = Integer.parseInt(input.nextLine());}
				catch (ArrayIndexOutOfBoundsException e) {
		            JOptionPane.showMessageDialog(null, 
		                    e.getMessage(), 
		                    "Failure loading data", JOptionPane.WARNING_MESSAGE);
		        }
				mem.setData(inp, val);
			}
		}
		
		
		
		input.close();
	}
}
