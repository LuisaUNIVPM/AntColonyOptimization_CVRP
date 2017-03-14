import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class Interfaccia extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		InizializzazioneProblema.init();
		AlgoritmoFormiche formiche=new AlgoritmoFormiche(InizializzazioneProblema.Nodi, InizializzazioneProblema.MatriceIncidenza);
		formiche.formiche();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaccia frame = new Interfaccia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaccia() {
		 super("Lines Drawing Demo");
		 
	        setSize(900, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(null);
	        
	        JPanel panel = new JPanel();
	        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK, null, null));
	        panel.setBackground(Color.WHITE);
	        panel.setBounds(335, 11, 539, 539);
	        getContentPane().add(panel);
	}
	void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int i=0; i<InizializzazioneProblema.Nodi.length;i++){
        	Ellipse2D.Double nodo1=new Ellipse2D.Double(10+50*i,300,10,10);
        	g2d.fill(nodo1);
        	Rectangle nodo= new Rectangle(10 +100*i,200,10,10);
        	g2d.fill(nodo);
        }
        
    }
	
	 public void paint(Graphics g) {
	        super.paint(g);
	        drawLines(g);
	    }
	
}
