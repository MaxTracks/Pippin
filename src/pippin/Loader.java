package pippin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Loader {

	public static void load(Memory mem, Code code, File file) throws FileNotFoundException{
		Scanner input = new Scanner(file);
		Boolean modeCheck = true; //true for code mode, false for data mode
		while(input.hasNextInt(16)){
			//reads a hexadecimal number into inp
			int inp = input.nextInt(16);
			
			//If inp is -1, simply switch to "data" mode
			if(inp == -1){modeCheck = false;}
			
			//Else if in "code" read the next line as int arg and use code.setCode(inp, arg);
			else if (modeCheck){
				try{
					int arg = input.nextInt(16);
					//int arg = Integer.parseInt(input.nextLine());
					code.setCode(inp, arg);
				}
				catch (ArrayIndexOutOfBoundsException e) {
		            JOptionPane.showMessageDialog(null, 
		                    e.getMessage(), 
		                    "Failure loading data", JOptionPane.WARNING_MESSAGE);
		        }
			}
			
			//Else read the next line as int val and use mem.setData(inp, val);
			else{
				try{
					int val = input.nextInt(16);
					//int val = Integer.parseInt(input.nextLine());
					mem.setData(inp, val);
				
				}
				catch (ArrayIndexOutOfBoundsException e) {
		            JOptionPane.showMessageDialog(null, 
		                    e.getMessage(), 
		                    "Failure loading data", JOptionPane.WARNING_MESSAGE);
		        }
			}
		}
		input.close();
	}
}
