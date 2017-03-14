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
import javax.swing.JLabel;

public class Interfaccia extends JFrame {
	
	private JPanel contentPane;

	static Soluzione Pippo=new Soluzione(1000,null);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		InizializzazioneProblema.init();
		AlgoritmoFormiche formiche=new AlgoritmoFormiche(InizializzazioneProblema.Nodi, InizializzazioneProblema.MatriceIncidenza);
		Pippo=formiche.formiche();
		
		
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
	        panel.setLayout(null);
	        
	        for(int i=0;i<InizializzazioneProblema.Nodi.length;i++){
	        JLabel lblNewLabel = new JLabel(""+InizializzazioneProblema.Nodi[i].nome);
	        lblNewLabel.setBounds(20+250+InizializzazioneProblema.PosX[i]*10+10, 250+20-InizializzazioneProblema.PosY[i]*10+5, 46, 14);
	        panel.add(lblNewLabel);
	        }
	        JLabel lblNewLabel = new JLabel("D");
	        lblNewLabel.setBounds(20+245+10,20+245+7, 46, 14);
	        panel.add(lblNewLabel);
	        
	}
	void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        for(int i=0; i<InizializzazioneProblema.Nodi.length;i++){
        	Ellipse2D.Double nodo=new Ellipse2D.Double(344+20+250+InizializzazioneProblema.PosX[i]*10,42+250+20-InizializzazioneProblema.PosY[i]*10,10,10);
        	g2d.fill(nodo);
        }
        
        g2d.setColor(Color.RED);
        Rectangle nodo1= new Rectangle(344+20+245,42+20+245,10,10);
        g2d.fill(nodo1);

        g2d.setColor(Color.BLACK); 
		g2d.drawLine(344+20+250, 42+20+250, 344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[0]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[0]]*10);
        for(int j=1;j<Pippo.soluzione.length;j++){
        		if(Pippo.soluzione[j]==-1){
        			g2d.drawLine(344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[j-1]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[j-1]]*10,344+20+250, 42+20+250);
        		}else
        			if(Pippo.soluzione[j-1]==-1){
        				g2d.drawLine(344+20+250, 42+20+250, 344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[j]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[j]]*10);
            		}else{
        		g2d.drawLine(344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[j-1]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[j-1]]*10, 344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[j]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[j]]*10);
            		}
        }
        
    }
	
	 public void paint(Graphics g) {
	        super.paint(g);
	        drawLines(g);
	    }
}
