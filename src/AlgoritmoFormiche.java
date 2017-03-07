import java.util.Arrays;

public class AlgoritmoFormiche {

	private static final int capacitainiziale = 10;
	private static final int energiainiziale = 100;
	private static final int maxIter=1000, m=10;
	
	double[][] feromoni;
	int numeroNodi;
	Nodo[] insiemeNodi;
	int[] soluzione;
	double[][] matrice;
	int[] soluzioneottima;  
	double valoreottimo=10000;//da inizializzare ad un numero piu grande dell'ottimo
	
	public AlgoritmoFormiche(Nodo[] Nodi, double[][] matriceIncidenza){
		matrice=matriceIncidenza;
		insiemeNodi=Nodi;
		numeroNodi=Nodi.length;
		feromoni=new double[numeroNodi][numeroNodi];//inizializzare feromoni
		//inizializzare soluzione
	}
	
	/*public void test(){
		int[] pippo=costruisciSoluzione();
		for(int i=0;i<pippo.length;i++){
			System.out.print("\t"+pippo[i]);
			if(i%8==7){
				System.out.println("");
			}
		}
		System.out.println("");
		double pluto=costopercorso(pippo);
		System.out.println(pluto);
	}*/
	
	public void formiche(){
		
		for( int iter=0;iter<1;iter++){
		
			for(int k=0;k<m;k++){
				
				soluzione=costruisciSoluzione();		//complete routes construction
				
				for(int i=0;i<soluzione.length;i++){
					System.out.print("\t"+soluzione[i]);
					if(i%8==4){
						System.out.println("");
					}
				}
				System.out.println("");
				double pluto=costopercorso(soluzione);
				System.out.println(pluto);
				
				if(pluto<valoreottimo){
					soluzioneottima=soluzione;
					valoreottimo=pluto;
				}
				
				//heuristic
				
				//controllo se Best solution>=L*
				
			}
			
			//pheromone udate
			
			
			//controllo se la soluzion è stabile se si esco dal ciclo
		}
		
		System.out.println("la soluzione ottima vale: "+ valoreottimo);
	}
	
	int[] costruisciSoluzione(){
		
		int numTabu=0;
		boolean[] tabulist=new boolean[numeroNodi];
		Nodo citta;
		int capacitaresidua=capacitainiziale;  ///costante
		double energiaresidua=energiainiziale;
		double alpha=1,beta=1,gamma=1;
		int[] tour=new int[2*numeroNodi]; // 2* perchè al massimo posso passare al deposito tante volte quanto il numero di nodi
		int tourlength=0;
		
		//**mi sceglie la prima città in maniera random e mi fa spostare dal deposito
		{
		double[] q= new double[numeroNodi]; //vettore probabilità
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
				p[i]=  //distribuzione di probabilità (non normailzzata)
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
					double[] q= new double[numeroNodi]; //vettore probabilità
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
		
		//bisogna fare l'euristica
		
		int[] risultato=Arrays.copyOf(tour, tourlength);

		return risultato;
	}
	
	
	double costopercorso(int[] sol){
		double somma=0;
		somma=insiemeNodi[sol[0]].distanzaDeposito+insiemeNodi[sol[sol.length-1]].distanzaDeposito;
		for (int j=1;j<sol.length;j++){			
			
			if (sol[j]==-1){  							//se sono arrivato nel deposito
				somma=somma+insiemeNodi[j-1].distanzaDeposito;
			}else 
				if(sol[j-1]==-1){						//se vengo dal deposito
					somma=somma+insiemeNodi[j].distanzaDeposito;				
				}
			else{
				somma=somma+matrice[sol[j]][sol[j-1]];  //distanza tra due nodi qualsiasi
			}
		}
		return somma; 
	}
	
}
