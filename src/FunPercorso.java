import java.util.Arrays;

public class FunPercorso {
	
static int[] costruiscipercorso(int numeroNodi, int capacitainiziale, double energiainiziale,Nodo[] insiemeNodi,double[][] feromoni, double alpha, double beta, double gamma){
		
		int numTabu=0;
		boolean[] tabulist=new boolean[numeroNodi];
		Nodo citta;
		int capacitaresidua=capacitainiziale;  ///costante
		double energiaresidua=energiainiziale;
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
			int candidati=0;
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
				if(candidati<numeroNodi/3){
					p[i]=  //distribuzione di probabilità (non normailzzata)
					Math.pow(feromoni[prossimacitta.nome][citta.nome]+0.00000000000001, alpha)*   //0.000001 serve per l'inizializzazione
					Math.pow(1/citta.distanze[i], beta)*
					Math.pow(citta.distanzaDeposito+prossimacitta.distanzaDeposito-citta.distanze[i], gamma);    //distanza triangolare		
					candidati++;
				}else{
					break;
				}
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
		//
		//bisogna fare l'euristica
		//
		int[] risultato=Arrays.copyOf(tour, tourlength);
		return risultato;
	}
	

	//funzione che mi calcola il costo del percorso della soluzione trovata come somma tra le distanze tra i nodi

	static double costopercorso(int[] sol, Nodo[] insiemeNodi, double[][]matrice){
		int inizio =0;
		int fine = 0;
		double somma = 0;
		while(fine < sol.length) {
			fine = FunSottotour.trovafinesottotour(sol, inizio);
			somma+=FunSottotour.costosottotour(sol, inizio, fine,insiemeNodi,matrice);
			inizio=fine+1;
		}
		return somma;
	}
}
