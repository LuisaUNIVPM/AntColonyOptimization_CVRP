//import java.util.Arrays;
import java.util.PriorityQueue;

public class AlgoritmoFormiche {

	private static final int capacitainiziale = 10;
	private static final double energiainiziale = 100;
	private static final int maxIter=1000;
	
	static int  m=Variabili.mVar;
	double alpha=Variabili.alphaVar,beta=Variabili.betaVar,gamma=Variabili.gammaVar;
	double ro=Variabili.roVar,theta=Variabili.thetaVar;
	int sigma=Variabili.sigmaVar;
	
	
	int numeroNodi;
	Nodo[] insiemeNodi;
	double[][] matrice;
	double[][] feromoni;
	Soluzione soluzioneottima;
	
	
	public AlgoritmoFormiche(Nodo[] Nodi, double[][] matriceIncidenza){
		matrice=matriceIncidenza;
		insiemeNodi=Nodi;
		numeroNodi=Nodi.length;
		feromoni=new double[numeroNodi][numeroNodi];
		FunFeromoni.feromoneinizializzazione(numeroNodi, feromoni, m);//inizializzazione feromoni
		soluzioneottima=new Soluzione(1000,null);//inizializzare soluzione
	}
	
	public Soluzione formiche(){
		int iterazioniottime=0;
		for( int iter=0;iter<maxIter;iter++){
			double Lavg=0;
			PriorityQueue<Soluzione> soluzionimigliori= new PriorityQueue<Soluzione>(sigma+1, (a,b) -> -(int)(a.costo - b.costo));
			
						
			for(int k=0;k<m;k++){
				int[] soluzione;
				soluzione=FunPercorso.costruiscipercorso(numeroNodi,capacitainiziale,energiainiziale,insiemeNodi,feromoni,alpha,beta,gamma);
						//complete routes construction
				
				/*for(int i=0;i<soluzione.length;i++){		//stampa
					System.out.print("\t"+soluzione[i]);
					if(i%5==4){
						System.out.println("");
					}
				}
				System.out.println("");*/
				FunSottotour.euristicasubtour(soluzione, insiemeNodi, matrice);;				//heuristic	
				double costo=FunPercorso.costopercorso(soluzione, insiemeNodi, matrice);
				
				//creazione vettori sigmasoluzioni per feromoni
				soluzionimigliori.add(new Soluzione(costo,soluzione));
				if (soluzionimigliori.size()>sigma){
					soluzionimigliori.remove();
				}
					
				Lavg=Lavg+costo;
				//System.out.println(costo);
				if (costo<soluzioneottima.costo){
					iterazioniottime=iter;
					soluzioneottima= new Soluzione(costo,soluzione);
				}
			}
			
			//System.out.println("");
			//System.out.println("la soluzione parziale alla "+iter+" iterazione vale: "+ valoreparziale);
			
			//pheromone evaporation
			FunFeromoni.feromoneevaporazione(Lavg, numeroNodi, feromoni, ro, theta);
			//pheromone udate
			FunFeromoni.feromonedeposizione(soluzioneottima, soluzionimigliori, feromoni, sigma);

			//controllo se la soluzion � stabile se si esco dal ciclo
		}
		System.out.println("");
		System.out.println("la soluzione ottima vale: "+ soluzioneottima.costo+ " ottenuta all'iterazione " +iterazioniottime+" ed � la seguente: ");
		for(int i=0;i<soluzioneottima.soluzione.length;i++){		//stampa
			System.out.print("\t"+soluzioneottima.soluzione[i]);
			if(i%5==4){
				System.out.println("");
			}
		}
		System.out.println("");
		
		return soluzioneottima;
	}
	
}


