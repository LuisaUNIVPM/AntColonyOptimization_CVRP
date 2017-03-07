
public class Main {
	
	
	public static void main(String[] args) {
		
		InizializzazioneProblema.init();
		AlgoritmoFormiche formiche=new AlgoritmoFormiche(InizializzazioneProblema.Nodi, InizializzazioneProblema.MatriceIncidenza);
		formiche.test();
    }
}
