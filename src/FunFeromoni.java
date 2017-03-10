import java.util.PriorityQueue;

public class FunFeromoni {
	//feromoni
		static void feromoneinizializzazione(int numeroNodi, double[][]feromoni, int m){
			for(int i=0;i<numeroNodi;i++){
				for(int j=0;j<numeroNodi;j++){
					feromoni[i][j]=m/200;

					//System.out.print(feromoni[i][j] +"\t");
					//System.out.println("");
				}	
			}
		}
		
		static void feromoneevaporazione(double Lavg,int numeroNodi, double[][] feromoni, double ro, double theta){
			for(int i=0;i<numeroNodi;i++){
				for(int j=0;j<numeroNodi;j++){
					feromoni[i][j]=(ro+theta/Lavg)*feromoni[i][j];
					//System.out.print(feromoni[i][j] +"\t");
				}	
			}
		}
		
		static void feromonedeposizione(Soluzione soluzioneelitist, PriorityQueue<Soluzione> soluzionimigliori,double[][] feromoni, int sigma){
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
}
