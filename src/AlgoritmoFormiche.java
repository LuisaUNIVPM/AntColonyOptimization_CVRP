import java.util.Arrays;
import java.util.PriorityQueue;

public class AlgoritmoFormiche {

	private static final int capacitainiziale = 10;
	private static final double energiainiziale = 100;
	private static final int maxIter=1000, m=20;
	

	final double alpha=2,beta=5,gamma=9;
	final double ro=0.80,theta=80;
	final int sigma=3;
	
	
	int numeroNodi;
	Nodo[] insiemeNodi;
	double[][] matrice;
	double[][] feromoni;
	Soluzione soluzioneottima;
	//da inizializzare ad un numero piu grande dell'ottimo
	
	
	public AlgoritmoFormiche(Nodo[] Nodi, double[][] matriceIncidenza){
		matrice=matriceIncidenza;
		insiemeNodi=Nodi;
		numeroNodi=Nodi.length;
		feromoni=new double[numeroNodi][numeroNodi];
		FunFeromoni.feromoneinizializzazione(numeroNodi, feromoni, m);//inizializzazione feromoni
		soluzioneottima=new Soluzione(1000,null);//inizializzare soluzione
	}
	
	public void formiche(){
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
	}
	
/*
	//funzione che mi calcola il costo del percorso della soluzione trovata come somma tra le distanze tra i nodi
		
	double costopercorso(int[] sol){
		int inizio =0;
		int fine = 0;
		double somma = 0;
		while(fine < sol.length) {
			fine = trovafinesottotour(sol, inizio);
			somma+=costosottotour(sol, inizio, fine);
			inizio=fine+1;
		}
		return somma;
	}
	*/
	
	/*
	//funzione divisione in sottotour

	// se 0<= inizio < fine <= tour.length
	// e per ogni i, inizio <= i < fine -> tour[i]!= -1
	// calcola il costo del sottotour tra tour[inizio] e tour[fine-1] 
	double costosottotour(int[] tour, int inizio,int fine ){
		double somma;
		somma=insiemeNodi[tour[inizio]].distanzaDeposito+insiemeNodi[tour[fine-1]].distanzaDeposito;
		for(int j=inizio+1;j<fine;j++){
			somma=somma+matrice[tour[j]][tour[j-1]]; 
		}
		return somma;
	}
	
	// se inizio < tour.length
	// restituisce il primo fine >= inizio per cui tour[fine] == -1 oppure fine == tour.length
	int trovafinesottotour(int[] tour, int inizio){
		for(int j=inizio;j<tour.length;j++){
			if(tour[j]==-1){
				return j;
			}
		}
		return tour.length;
	}
	
	
	void euristicasubtour(int[] sol){
		int inizio=0, fine=0;
		while(fine<sol.length){
			fine=trovafinesottotour(sol, inizio);
			for(int l=2;l<fine-inizio;l++){  ///lunghezza minima=2
				double guadagno=(insiemeNodi[sol[inizio]].distanzaDeposito+matrice[sol[inizio+l-1]][sol[inizio+l]])
								-(insiemeNodi[sol[inizio+l-1]].distanzaDeposito+matrice[sol[inizio]][sol[inizio+l]]);
				if(guadagno>0){
					reverse(sol,inizio,inizio+l);
					l=1;
					continue;
				}
				guadagno=(insiemeNodi[sol[fine-1]].distanzaDeposito+matrice[sol[fine-l]][sol[fine-l-1]])
						-(insiemeNodi[sol[fine-l]].distanzaDeposito+matrice[sol[fine-1]][sol[fine-l-1]]);
				if(guadagno>0){
					reverse(sol,fine-l,fine);
					l=1;
					continue;
				}
				for(int i=inizio+1;i+l<fine;i++){
					guadagno=(matrice[sol[i-1]][sol[i]]+matrice[sol[i+l-1]][sol[i+l]])
							-(matrice[sol[i-1]][sol[i+l-1]]+matrice[sol[i]][sol[i+l]]);
					if(guadagno>0){
						reverse(sol,i,i+l);
						l=1;
						break;
					}
				}
			}
			inizio=fine+1;
		}
	}
	
	void reverse(int[] array, int inizio, int fine){
		for(int k=0;k<(fine-inizio)/2;k++){
			int box;
			box=array[inizio+k];
			array[inizio+k]=array[fine-k-1];
			array[fine-k-1]=box;
		}
		//System.out.println(costopercorso(array));
	}
	*/
	/*
	//feromoni
	void feromoneinizializzazione(){
		for(int i=0;i<numeroNodi;i++){
			for(int j=0;j<numeroNodi;j++){
				feromoni[i][j]=m/200;

				//System.out.print(feromoni[i][j] +"\t");
				//System.out.println("");
			}	
		}
	}
	
	void feromoneevaporazione(double Lavg){
		for(int i=0;i<numeroNodi;i++){
			for(int j=0;j<numeroNodi;j++){
				feromoni[i][j]=(ro+theta/Lavg)*feromoni[i][j];
				//System.out.print(feromoni[i][j] +"\t");
			}	
		}
	}
	
	void feromonedeposizione(Soluzione soluzioneelitist, PriorityQueue<Soluzione> soluzionimigliori){
		for(int k=1;k<soluzioneelitist.soluzione.length;k++){
			if (soluzioneelitist.soluzione[k]==-1 || soluzioneelitist.soluzione[k-1]==-1){
				continue;
			}
			feromoni[soluzioneelitist.soluzione[k-1]][soluzioneelitist.soluzione[k]]+=sigma/soluzioneelitist.costo;
			feromoni[soluzioneelitist.soluzione[k]][soluzioneelitist.soluzione[k-1]]+=sigma/soluzioneelitist.costo;
		}
		for(int lambda=sigma-1;lambda>=0;lambda--){
			Soluzione s=soluzionimigliori.remove();
			for(int k=1;k<s.soluzione.length;k++){
				if (s.soluzione[k]==-1 || s.soluzione[k-1]==-1){
					continue;
				}
				feromoni[s.soluzione[k-1]][s.soluzione[k]]+=(sigma-lambda)/s.costo;
				feromoni[s.soluzione[k]][s.soluzione[k-1]]+=(sigma-lambda)/s.costo;
			}
		}
	
	}
	*/
		
}


