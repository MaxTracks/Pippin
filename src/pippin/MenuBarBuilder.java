package pippin;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Part VIII
 * @author Tim
 *
 */
public class MenuBarBuilder implements Observer {
    private JMenuItem assemble = new JMenuItem("Assemble Source...");
    private JMenuItem load = new JMenuItem("Load Program...");
    private JMenuItem exit = new JMenuItem("Exit");
    private JMenuItem go = new JMenuItem("Go");
    private Machine machine;

    public MenuBarBuilder(Machine machine) {
        this.machine = machine;
        machine.addObserver(this);
    }
    
    public JMenu createFileMenu(){
    	JMenu returnMenu = new JMenu("File");
    	returnMenu.setMnemonic(KeyEvent.VK_F);
    	
    	assemble.setMnemonic(KeyEvent.VK_A);
    	assemble.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        assemble.addActionListener(e -> machine.assembleFile());
        returnMenu.add(assemble);
        
        load.setMnemonic(KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(e -> machine.loadFile());
        returnMenu.add(load);
        
        returnMenu.addSeparator();
        
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.addActionListener(e -> machine.exit());
        returnMenu.add(exit);
        
        return returnMenu;
    }
    
    public JMenu createExecuteMenu(){
    	JMenu executeMenu = new JMenu("Execute");
    	executeMenu.setMnemonic(KeyEvent.VK_X);
    	
    	go.setMnemonic(KeyEvent.VK_G);
    	go.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));
    	go.addActionListener(e -> machine.execute());
        executeMenu.add(go);
        
        return executeMenu;
    }

	@Override
	public void update(Observable o, Object arg) {
		assemble.setEnabled(machine.getState().getAssembleFileActive());
        load.setEnabled(machine.getState().getLoadFileActive());
        go.setEnabled(machine.getState().getStepActive());
	}
}