package pippin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel implements Observer {
	private int previousColor = -1;
	private Code code = new Code();
	private Processor cpu = new Processor(); 
	private JScrollPane scroller;
	JTextField[] codeText = new JTextField[Code.CODE_MAX];
	JTextField[] codeHex = new JTextField[Code.CODE_MAX];
	
	public CodeViewPanel(Machine machine){
		code = machine.getCode();
		cpu = machine.getCpu();
		machine.addObserver(this);
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CodeViewPanel codeViewPanel = new CodeViewPanel(new Machine());
                JFrame frame = new JFrame("Code View Panel");
                frame.add(codeViewPanel.createCodeDisplay());
                frame.setSize(300,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
	
	public JPanel createCodeDisplay(){
		JPanel returnPanel = new JPanel(); 
		JPanel panel = new JPanel(); 
		JPanel numPanel = new JPanel(); 
		JPanel sourcePanel = new JPanel(); 
		JPanel hexPanel = new JPanel(); 
		returnPanel.setPreferredSize(new Dimension(300,150));;
        returnPanel.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        numPanel.setLayout(new GridLayout(0,1));
        sourcePanel.setLayout(new GridLayout(0,1));
        hexPanel.setLayout(new GridLayout(0,1));
        for(int i = 0; i <= Code.CODE_MAX-1; ++i){
        	numPanel.add(new JLabel(i+": ", JLabel.RIGHT)); 
        	codeText[i]= new JTextField(10);
        	codeHex[i]= new JTextField(10);
        	sourcePanel.add(codeText[i]);
        	hexPanel.add(codeHex[i]);
        }	
        Border border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Code Memory View",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        returnPanel.setBorder(border);
        panel.add(numPanel, BorderLayout.LINE_START);
        panel.add(sourcePanel, BorderLayout.CENTER);
        panel.add(hexPanel, BorderLayout.LINE_END);
        scroller = new JScrollPane(panel);
        returnPanel.add(scroller);
        return returnPanel;
	}
	
	public void update(Observable arg0, Object arg1) {
		if(arg1 != null && arg1.equals("Load Code")) {
            for(int i = 0; i < Code.CODE_MAX; i++) {
                codeText[i].setText(code.getCodeText(i));
                codeHex[i].setText(code.getCodeHex(i));
            }     
            previousColor = cpu.getProgramCounter();
            codeText[previousColor].setBackground(Color.CYAN);
            codeHex[previousColor].setBackground(Color.CYAN);
        }
		if(arg1 != null && arg1.equals("Clear")) {
			for(int i = 0; i < Code.CODE_MAX; i++) {
				codeText[i].setText("");
				codeHex[i].setText("");
			}
			if(previousColor >= 0){
				codeText[previousColor].setBackground(Color.WHITE);
				codeHex[previousColor].setBackground(Color.WHITE);
			}
			previousColor = -1;
		}
		if(previousColor >= 0){
			codeText[previousColor].setBackground(Color.WHITE);
			codeHex[previousColor].setBackground(Color.WHITE);
			previousColor = cpu.getProgramCounter();
			codeText[previousColor].setBackground(Color.CYAN);
			codeHex[previousColor].setBackground(Color.CYAN);
		}
        if(scroller != null && code != null && cpu!= null) {
            JScrollBar bar= scroller.getVerticalScrollBar();
            if(cpu.getProgramCounter() < Code.CODE_MAX && codeText[cpu.getProgramCounter()] != null) {
                Rectangle bounds = codeText[cpu.getProgramCounter()].getBounds();
                bar.setValue(Math.max(0, bounds.y - 15*bounds.height));
            }
        }
	}
}