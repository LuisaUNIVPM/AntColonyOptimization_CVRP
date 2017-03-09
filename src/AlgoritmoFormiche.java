import java.util.Arrays;
import java.util.PriorityQueue;

public class AlgoritmoFormiche {

	private static final int capacitainiziale = 10;
	private static final int energiainiziale = 100;
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
		feromoneinizializzazione();//inizializzazione feromoni
		soluzioneottima=new Soluzione(1000,null);//inizializzare soluzione
	}
	
	public void formiche(){
		for( int iter=0;iter<5;iter++){
			int[] soluzioneparziale = null;
			double valoreparziale=1000;		//da inizializzare ad un numero piu grande dell'ottimo
			double Lavg=0;
			PriorityQueue<Soluzione> soluzionimigliori= new PriorityQueue<Soluzione>(sigma+1, (a,b) -> -(int)(a.costo - b.costo));
			
						
			for(int k=0;k<m;k++){
				int[] soluzione;
				soluzione=costruisciSoluzione();		//complete routes construction
				
				/*for(int i=0;i<soluzione.length;i++){		//stampa
					System.out.print("\t"+soluzione[i]);
					if(i%5==4){
						System.out.println("");
					}
				}
				System.out.println("");*/
				euristicasubtour(soluzione);				//heuristic	
				double costo=costopercorso(soluzione);
				
				//creazione vettori sigmasoluzioni per feromoni
				soluzionimigliori.add(new Soluzione(costo,soluzione));
				if (soluzionimigliori.size()>sigma){
					soluzionimigliori.remove();
				}
					
				Lavg=Lavg+costo;
				//System.out.println(costo);
				if(costo<valoreparziale){
					soluzioneparziale=soluzione;
					valoreparziale=costo;
				}	
			}
			if (valoreparziale<soluzioneottima.costo){
				soluzioneottima= new Soluzione(valoreparziale,soluzioneparziale);
			}
			
			System.out.println("");
			System.out.println("la soluzione parziale alla "+iter+" iterazione vale: "+ valoreparziale);
			
			//pheromone evaporation
			feromoneevaporazione(Lavg);
			feromonedeposizione(soluzioneottima, soluzionimigliori);
			//pheromone udate
			//controllo se la soluzion � stabile se si esco dal ciclo
		}
		System.out.println("");
		System.out.println("la soluzione ottima vale: "+ soluzioneottima.costo+ " ed � la seguente: ");
		for(int i=0;i<soluzioneottima.soluzione.length;i++){		//stampa
			System.out.print("\t"+soluzioneottima.soluzione[i]);
			if(i%5==4){
				System.out.println("");
			}
		}
		System.out.println("");
	}
	
	
	int[] costruisciSoluzione(){
		
		int numTabu=0;
		boolean[] tabulist=new boolean[numeroNodi];
		Nodo citta;
		int capacitaresidua=capacitainiziale;  ///costante
		double energiaresidua=energiainiziale;
		int[] tour=new int[2*numeroNodi]; // 2* perch� al massimo posso passare al deposito tante volte quanto il numero di nodi
		int tourlength=0;
		
		//**mi sceglie la prima citt� in maniera random e mi fa spostare dal deposito
		{
		double[] q= new double[numeroNodi]; //vettore probabilit�
		for(int i=0;i<numeroNodi;i++){
			if (tabulist[i]){
				q[i]=0;
			} else {
				q[i]=1;
			}
			if(i!=0){
				q[i]=q[i]+q[i-1];
			}
		}
		double random=Math.random()*q[numeroNodi-1];
		citta=null;
		for(int i=0;i<numeroNodi;i++){
			if(q[i]>random){
				citta=insiemeNodi[i];
				break;
			}
		}
		numTabu++;
		tabulist[citta.nome]=true;
		tour[tourlength++]=citta.nome;
		capacitaresidua=capacitaresidua-citta.domanda;
		energiaresidua=energiaresidua-citta.distanzaDeposito;
		}
		//	**	
		
		while(numTabu<numeroNodi){
			double[] p= new double[numeroNodi];
			for(int i=1;i<numeroNodi;i++){
				Nodo prossimacitta=citta.vicini[i];
				if (tabulist[prossimacitta.nome]){
					p[i]=0;
				} else
				if(capacitaresidua<prossimacitta.domanda){
					p[i]=0;
				} else
				if(energiaresidua<(citta.distanze[i]+ prossimacitta.distanzaDeposito)){
					p[i]=0;
				} else 
				p[i]=  //distribuzione di probabilit� (non normailzzata)
					Math.pow(feromoni[prossimacitta.nome][citta.nome]+0.00000001, alpha)*   //0.000001 serve per l'inizializzazione
					Math.pow(1/citta.distanze[i], beta)*
					Math.pow(citta.distanzaDeposito+prossimacitta.distanzaDeposito-citta.distanze[i], gamma);    //distanza triangolare		
			}
			for(int i=1;i<numeroNodi;i++){
				p[i]=p[i]+p[i-1];  //sovrascrivo somme parziali 
			}
			if(p[numeroNodi-1]==0){
				//torna a casa	
				capacitaresidua=capacitainiziale;
				energiaresidua=energiainiziale;
				tour[tourlength++]=-1;
				//ricomincia tour
					double[] q= new double[numeroNodi]; //vettore probabilit�
					for(int i=0;i<numeroNodi;i++){
						if (tabulist[i]){
							q[i]=0;
						} else {
							q[i]=1;
						}
						if(i!=0){
							q[i]=q[i]+q[i-1];
						}
					}
					double random=Math.random()*q[numeroNodi-1];
					for(int i=0;i<numeroNodi;i++){
						if(q[i]>random){
							citta=insiemeNodi[i];
							break;
						}
					}
					capacitaresidua=capacitaresidua-citta.domanda;
					energiaresidua=energiaresidua-citta.distanzaDeposito;
			} else{
				double random=Math.random()*p[numeroNodi-1];
				for(int i=0;i<numeroNodi;i++){
					if(p[i]>random){
						Nodo prossimacitta=citta.vicini[i];
						capacitaresidua=capacitaresidua-prossimacitta.domanda;
						energiaresidua=energiaresidua-citta.distanze[i];
						citta=prossimacitta;
						break;
					}
				}
			}
			tabulist[citta.nome]=true;
			tour[tourlength++]=citta.nome;
			numTabu++;
		}
		//
		//bisogna fare l'euristica
		//
		int[] risultato=Arrays.copyOf(tour, tourlength);
		return risultato;
	}
	
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
	
	/*//implementazione euristica scambio due nodi
	int[] euristica2nodi(int sol[]){
		int[] risultato = null,arraybox;
		int fine=0,inizio=0,box;
		while(fine<sol.length){
			fine=trovafinesottotour(sol, inizio);
			double costo=costosottotour(sol, inizio, fine);
			for(int j=inizio; j<fine;j++){
				for(int k=j+1;k<fine;k++){
					arraybox=sol;
					box=arraybox[inizio+k];
					arraybox[inizio+k]=arraybox[inizio+j];
					arraybox[inizio+j]=box;
					if (costosottotour(arraybox,inizio,fine)<costo){
						System.out.println(costosottotour(arraybox,inizio,fine));
						risultato=arraybox;
					}else{
						
						risultato=sol;
					}
				}
			}
			inizio=fine+1;
		}
		return risultato;
	}*/
	
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
			feromoni[soluzioneelitist.soluzione[k-1]][soluzioneelitist.soluzione[k]]+=sigma/soluzioneelitist.costo;
		}
		for(int lambda=sigma-1;lambda>=0;lambda--){
			Soluzione s=soluzionimigliori.remove();
			for(int k=1;k<s.soluzione.length;k++){
				feromoni[s.soluzione[k-1]][s.soluzione[k]]+=(sigma-lambda)/s.costo;
			}
		}
	
	}
	
		
}


