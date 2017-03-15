import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interfaccia extends JFrame {
	
	private JPanel contentPane;

	static Soluzione Pippo=new Soluzione(1000,null);
	private JTextField txtm;
	private JTextField txtalfa;
	private JTextField txtbeta;
	private JTextField txtgamma;
	private JTextField txtro;
	private JTextField txttheta;
	private JTextField Niterazioni;
	private JTextField ValoreMigliore;
	private JTextField txtsigma;
	private JTextField txtTempo;
	
	boolean premuto;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		InizializzazioneProblema.init();
		//AlgoritmoFormiche formiche=new AlgoritmoFormiche(InizializzazioneProblema.Nodi, InizializzazioneProblema.MatriceIncidenza);
		//Pippo=formiche.formiche();
	
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
		 super("ACO - Ant Colony Optimization");
		 
	        setSize(900, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(null);
	        
	        JPanel panel = new JPanel();
	        panel.setBounds(335, 11, 539, 539);
	        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK, null, null));
	        panel.setBackground(Color.WHITE);
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
	        
	        JLabel lblNewLabel_1 = new JLabel("IMPOSTAZIONE PARAMETRI");
	        lblNewLabel_1.setForeground(Color.BLACK);
	        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
	        lblNewLabel_1.setBounds(10, 11, 315, 36);
	        getContentPane().add(lblNewLabel_1);
	        
	        
	        
	        JLabel lblMnumeroFormiche = new JLabel("m");
	        lblMnumeroFormiche.setForeground(Color.BLACK);
	        lblMnumeroFormiche.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblMnumeroFormiche.setHorizontalAlignment(SwingConstants.LEFT);
	        lblMnumeroFormiche.setBounds(20, 58, 155, 20);
	        getContentPane().add(lblMnumeroFormiche);
	        JLabel lblnumeroFormiche = new JLabel("(Numero formiche)");
	        lblnumeroFormiche.setForeground(Color.BLACK);
	        lblnumeroFormiche.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        lblnumeroFormiche.setBounds(20, 76, 251, 14);
	        getContentPane().add(lblnumeroFormiche);
	        txtm = new JTextField();
	        txtm.setBounds(185, 58, 86, 20);
	        getContentPane().add(txtm);
	        txtm.setColumns(10);
	        
	        
	        JLabel lblalfa = new JLabel("alfa");
	        lblalfa.setForeground(Color.BLACK);
	        lblalfa.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblalfa.setHorizontalAlignment(SwingConstants.LEFT);
	        lblalfa.setBounds(20, 138, 155, 20);
	        getContentPane().add(lblalfa);
	        JLabel label1 = new JLabel("(Influenza \"Pheromone concentration\")");
	        label1.setForeground(Color.BLACK);
	        label1.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        label1.setBounds(20, 156, 251, 14);
	        getContentPane().add(label1);
	        txtalfa = new JTextField();
	        txtalfa.setBounds(185, 138, 86, 20);
	        getContentPane().add(txtalfa);
	        txtalfa.setColumns(10);
	        
	        JLabel lblbeta = new JLabel("beta");
	        lblbeta.setForeground(Color.BLACK);
	        lblbeta.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblbeta.setHorizontalAlignment(SwingConstants.LEFT);
	        lblbeta.setBounds(20, 178, 155, 20);
	        getContentPane().add(lblbeta);
	        JLabel label2 = new JLabel("(Influenza \"Attractivness\")");
	        label2.setForeground(Color.BLACK);
	        label2.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        label2.setBounds(20, 196, 251, 14);
	        getContentPane().add(label2);
	        txtbeta = new JTextField();
	        txtbeta.setBounds(185, 178, 86, 20);
	        getContentPane().add(txtbeta);
	        txtbeta.setColumns(10);
	        
	        JLabel lblgamma = new JLabel("gamma");
	        lblgamma.setForeground(Color.BLACK);
	        lblgamma.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblgamma.setHorizontalAlignment(SwingConstants.LEFT);
	        lblgamma.setBounds(20, 218, 155, 20);
	        getContentPane().add(lblgamma);
	        JLabel label3 = new JLabel("(Influenza \"Saving\")");
	        label3.setForeground(Color.BLACK);
	        label3.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        label3.setBounds(20, 236, 251, 14);
	        getContentPane().add(label3);
	        txtgamma = new JTextField();
	        txtgamma.setBounds(185, 218, 86, 20);
	        getContentPane().add(txtgamma);
	        txtgamma.setColumns(10);
	        
	        JLabel lblro = new JLabel("ro");
	        lblro.setForeground(Color.BLACK);
	        lblro.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblro.setHorizontalAlignment(SwingConstants.LEFT);
	        lblro.setBounds(20, 258, 155, 20);
	        getContentPane().add(lblro);
	        JLabel label4 = new JLabel("(Persistenza del feromone)");
	        label4.setForeground(Color.BLACK);
	        label4.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        label4.setBounds(20, 276, 251, 14);
	        getContentPane().add(label4);
	        txtro = new JTextField();
	        txtro.setBounds(185, 258, 86, 20);
	        getContentPane().add(txtro);
	        txtro.setColumns(10);
	        
	        JLabel lbltheta= new JLabel("theta");
	        lbltheta.setForeground(Color.BLACK);
	        lbltheta.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lbltheta.setHorizontalAlignment(SwingConstants.LEFT);
	        lbltheta.setBounds(20, 298, 155, 20);
	        getContentPane().add(lbltheta);
	        JLabel label5 = new JLabel("(Costante di evaporazione)");
	        label5.setForeground(Color.BLACK);
	        label5.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        label5.setBounds(20, 316, 251, 14);
	        getContentPane().add(label5);
	        txttheta = new JTextField();
	        txttheta.setBounds(185, 298, 86, 20);
	        getContentPane().add(txttheta);
	        txttheta.setColumns(10);
     
	        JLabel lblRisultati = new JLabel("RISULTATI");
	        lblRisultati.setHorizontalAlignment(SwingConstants.CENTER);
	        lblRisultati.setForeground(Color.BLACK);
	        lblRisultati.setFont(new Font("Times New Roman", Font.BOLD, 18));
	        lblRisultati.setBounds(10, 384, 315, 36);
	        getContentPane().add(lblRisultati);
	        
	        JLabel lblValore = new JLabel("Valore migliore");
	        lblValore.setHorizontalAlignment(SwingConstants.LEFT);
	        lblValore.setForeground(Color.BLACK);
	        lblValore.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblValore.setBounds(20, 431, 155, 20);
	        getContentPane().add(lblValore);
	        
	        JLabel lblNumeroIterazioni = new JLabel("Numero iterazioni");
	        lblNumeroIterazioni.setHorizontalAlignment(SwingConstants.LEFT);
	        lblNumeroIterazioni.setForeground(Color.BLACK);
	        lblNumeroIterazioni.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblNumeroIterazioni.setBounds(20, 471, 155, 20);
	        getContentPane().add(lblNumeroIterazioni);
	        
	        Niterazioni = new JTextField();
	        Niterazioni.setColumns(10);
	        Niterazioni.setBounds(185, 471, 86, 20);
	        Niterazioni.setEditable(false);
	        getContentPane().add(Niterazioni);
	        
	        ValoreMigliore = new JTextField();
	        ValoreMigliore.setColumns(10);
	        ValoreMigliore.setBounds(185, 431, 86, 20);
	        ValoreMigliore.setEditable(false);
	        getContentPane().add(ValoreMigliore);
	        
	        JLabel lblsigma = new JLabel("sigma");
	        lblsigma.setHorizontalAlignment(SwingConstants.LEFT);
	        lblsigma.setForeground(Color.BLACK);
	        lblsigma.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblsigma.setBounds(20, 98, 155, 20);
	        getContentPane().add(lblsigma);
	        
	        JLabel lblnumeroFormicheElitist = new JLabel("(numero formiche elitist)");
	        lblnumeroFormicheElitist.setForeground(Color.BLACK);
	        lblnumeroFormicheElitist.setFont(new Font("Times New Roman", Font.PLAIN, 11));
	        lblnumeroFormicheElitist.setBounds(20, 116, 251, 14);
	        getContentPane().add(lblnumeroFormicheElitist);
	        
	        txtsigma = new JTextField();
	        txtsigma.setColumns(10);
	        txtsigma.setBounds(185, 98, 86, 20);
	        getContentPane().add(txtsigma);
	        
	        JLabel lblTempoDiEsecuzione = new JLabel("Tempo di esecuzione");
	        lblTempoDiEsecuzione.setHorizontalAlignment(SwingConstants.LEFT);
	        lblTempoDiEsecuzione.setForeground(Color.BLACK);
	        lblTempoDiEsecuzione.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        lblTempoDiEsecuzione.setBounds(20, 511, 155, 20);
	        getContentPane().add(lblTempoDiEsecuzione);
	        
	        txtTempo = new JTextField();
	        txtTempo.setColumns(10);
	        txtTempo.setBounds(185, 511, 86, 20);
	        txtTempo.setEditable(false);
	        getContentPane().add(txtTempo);
	        
	        
	        
	        JButton Bottone = new JButton("START");
	        Bottone.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent arg0) {
	        		premuto=true;

	        		AlgoritmoFormiche formiche=new AlgoritmoFormiche(InizializzazioneProblema.Nodi, InizializzazioneProblema.MatriceIncidenza);
	        		Pippo=formiche.formiche();
	        		
	        		//removeall() rimuove tutti gli oggetti
	        		
	        		//lettura da texfielder alfa beta gamma....
	        		
	        		
	        		//esecuzione algoritmo
	        		
	        		
	        		//stampa percorsi
	        		repaint();
	        		
	        		
					//stampa risultati
	        		
	        		DecimalFormat DF=new DecimalFormat("#.####",new DecimalFormatSymbols());
	        		DF.setRoundingMode(RoundingMode.CEILING);
	        		ValoreMigliore.setText(DF.format(Pippo.costo));
	        	}
	        });
	        Bottone.setForeground(Color.BLACK);
	        Bottone.setBackground(SystemColor.inactiveCaption);
	        Bottone.setFont(new Font("Times New Roman", Font.BOLD, 12));
	        Bottone.setBounds(113, 341, 89, 23);
	        getContentPane().add(Bottone);
	        
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
    
        if (premuto){
		     
        
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
        g2d.drawLine(344+20+255+InizializzazioneProblema.PosX[Pippo.soluzione[Pippo.soluzione.length-1]]*10, 42+255+20-InizializzazioneProblema.PosY[Pippo.soluzione[Pippo.soluzione.length-1]]*10,344+20+250, 42+20+250);
    }}
	 
	 public void paint(Graphics g) {
			super.paint(g);
			     
	        drawLines(g);
			
	    }
	
}
