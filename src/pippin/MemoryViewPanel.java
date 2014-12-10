package pippin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MemoryViewPanel implements Observer{
	private Memory memory;
	private JScrollPane scroller;
	private JTextField[] dataDecimal = new JTextField[Memory.DATA_SIZE];
	private JTextField[] dataHex = new JTextField[Memory.DATA_SIZE];
	private int lower;
	private int upper;
	
	public MemoryViewPanel(Machine machine, int lower, int upper){
		memory = machine.getMemory();
		this.upper = upper;
		this.lower = lower;
		machine.addObserver(this);
	}
	
	 public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                MemoryViewPanel memoryViewPanel = new MemoryViewPanel(new Machine(),50,120);
	                JFrame frame = new JFrame("Code View Panel");
	                frame.add(memoryViewPanel.createMemoryDisplay());
	                frame.setSize(300,600);
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setVisible(true);
	            }
	        });
	 }
	 
	 public JPanel createMemoryDisplay(){
			JPanel returnPanel = new JPanel(); 
			JPanel panel = new JPanel(); 
			JPanel numPanel = new JPanel(); 
			JPanel decimalPanel = new JPanel(); 
			JPanel hexPanel = new JPanel(); 
			returnPanel.setPreferredSize(new Dimension(300,150));;
	        returnPanel.setLayout(new BorderLayout());
	        panel.setLayout(new BorderLayout());
	        numPanel.setLayout(new GridLayout(0,1));
	        decimalPanel.setLayout(new GridLayout(0,1));
	        hexPanel.setLayout(new GridLayout(0,1));
	        for(int i = lower; i < upper; ++i){
	        	numPanel.add(new JLabel(i+": ", JLabel.RIGHT)); 
	        	dataDecimal[i]= new JTextField(10);
	        	dataHex[i]= new JTextField(10);
	        	decimalPanel.add(dataDecimal[i]);
	        	hexPanel.add(dataHex[i]);
	        }	
	        returnPanel.add(new JLabel("Data Memory View [" + lower + "-" + upper + "]", JLabel.CENTER), 
	                BorderLayout.PAGE_START);
	        panel.add(numPanel, BorderLayout.LINE_START);
	        panel.add(decimalPanel, BorderLayout.CENTER);
	        panel.add(hexPanel, BorderLayout.LINE_END);
	        scroller = new JScrollPane(panel);
	        returnPanel.add(scroller);
	        return returnPanel;
		}
		
		public void update(Observable arg0, Object arg1) {}
}


