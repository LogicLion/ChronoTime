import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;



public class BasicSwing extends JFrame {

		public BasicSwing(){
			initUI();
		}
		
		private void initUI(){
			
			JButton quitButton = new JButton("Quit");
			quitButton.setToolTipText("Buttonz");
			setTitle("Tooltip");
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
			quitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                System.exit(0);
	            }
	        });
			
	        createLayout(quitButton);
			setTitle("Basic title");
			setSize(900,600);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		 private void createLayout(JComponent... arg) {

		        Container pane = getContentPane();
		        GroupLayout gl = new GroupLayout(pane);
		        pane.setLayout(gl);

		        gl.setAutoCreateContainerGaps(true);

		        gl.setHorizontalGroup(gl.createSequentialGroup()
		                .addComponent(arg[0])
		                .addGap(200)
		        );

		        gl.setVerticalGroup(gl.createSequentialGroup()
		                .addComponent(arg[0])
		                .addGap(120)
		        );
		    }
		
		public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					BasicSwing s = new BasicSwing();
					s.setVisible(true);
					
				}
			});
			
		}
	
}
