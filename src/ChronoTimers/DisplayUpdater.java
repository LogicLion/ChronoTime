package ChronoTimers;
import java.util.TimerTask;

import javax.swing.JTextPane;


public class DisplayUpdater extends TimerTask {

	ChronoTimer t;
	JTextPane c;
	
	public DisplayUpdater(ChronoTimer timer, JTextPane console){
		this.t = timer;
		this.c = console;
	}
	
	@Override
	public void run() {
		if(t._isOn){
			c.setText("");
			c.setText(t.updateDisplay());
		}
	}

}
