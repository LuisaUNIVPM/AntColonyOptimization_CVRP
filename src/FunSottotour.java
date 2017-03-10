
public class FunSottotour {
	//funzione divisione in sottotour

		// se 0<= inizio < fine <= tour.length
		// e per ogni i, inizio <= i < fine -> tour[i]!= -1
		// calcola il costo del sottotour tra tour[inizio] e tour[fine-1] 
		static double costosottotour(int[] tour, int inizio,int fine, Nodo[] insiemeNodi,double[][] matrice){
			double somma;
			somma=insiemeNodi[tour[inizio]].distanzaDeposito+insiemeNodi[tour[fine-1]].distanzaDeposito;
			for(int j=inizio+1;j<fine;j++){
				somma=somma+matrice[tour[j]][tour[j-1]]; 
			}
			return somma;
		}
		
		// se inizio < tour.length
		// restituisce il primo fine >= inizio per cui tour[fine] == -1 oppure fine == tour.length
		static int trovafinesottotour(int[] tour, int inizio){
			for(int j=inizio;j<tour.length;j++){
				if(tour[j]==-1){
					return j;
				}
			}
			return tour.length;
		}
		
		
		static void euristicasubtour(int[] sol, Nodo[] insiemeNodi, double[][]matrice){
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
		
		static void reverse(int[] array, int inizio, int fine){
			for(int k=0;k<(fine-inizio)/2;k++){
				int box;
				box=array[inizio+k];
				array[inizio+k]=array[fine-k-1];
				array[fine-k-1]=box;
			}
			//System.out.println(costopercorso(array));
		}
}
