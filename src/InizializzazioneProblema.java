import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class InizializzazioneProblema  {

	final public static int MaxNodo=23;  //modificare in base alla dimensione dello spazio di stato
	final static String pathExcel = "C:/Users/Luisa/Desktop/Università/Laurea magistrale/Ricerca operativa 2 (Pezzella)/AntColonyData.xls";

	public static int[] PosX= new int[MaxNodo]; 
	public static int[] PosY= new int[MaxNodo]; 
	public static int[] Domanda = new int[MaxNodo]; 
	
	public static double[][] MatriceIncidenza= new double[MaxNodo][MaxNodo];
	
	public static Nodo[] Nodi=new Nodo[MaxNodo];
	
	static void init(){
		
		final Workbook workbook;  //final=posso assegnare una e una sola volta workbook
		final File excel = new File(pathExcel);
		Sheet sheet = null;
		try {
			workbook = Workbook.getWorkbook(excel);
			sheet = workbook.getSheet(0);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			for(int i=0; i<MaxNodo; i++) {
		
				Cell b = sheet.getCell(1,i+1);
				Cell c = sheet.getCell(2,i+1);
				Cell d = sheet.getCell(3,i+1);
				
				PosX[i]=(int)((NumberCell)b).getValue();
				PosY[i]=(int)((NumberCell)c).getValue();
		        Domanda[i]=(int)((NumberCell)d).getValue();
		        
		        System.out.println("il nodo " +i+" ha domanda " + Domanda[i]);
			} 
		
			for(int i=0;i<MaxNodo;i++){   		//costruzione matrice di adiacenza
				for(int j=0;j<MaxNodo;j++){
					MatriceIncidenza[i][j]=Math.sqrt(((PosX[i]-PosX[j])*(PosX[i]-PosX[j]))+((PosY[i]-PosY[j])*(PosY[i]-PosY[j])));
					System.out.print(MatriceIncidenza[i][j] + "\t ");
				}
				System.out.println("");
			}
			
			for(int i=0;i<MaxNodo;i++){
				Nodo N= new Nodo();
				N.nome=i;
				N.domanda=Domanda[i];
				N.distanzaDeposito=Math.sqrt(((PosX[i])*(PosX[i]))+((PosY[i])*(PosY[i])));
				Nodi[i]=N;
			}
			
			for(int i=0;i<MaxNodo;i++){
				Nodo[] box=Arrays.copyOf(Nodi, MaxNodo);
				final int k=i;
				Arrays.sort(box, new Comparator<Nodo>() {
					@Override
					public int compare(Nodo o1, Nodo o2) {
						return (int)Math.signum(MatriceIncidenza[o1.nome][k]-MatriceIncidenza[o2.nome][k]);
					}
				});
				Nodi[i].vicini=box;
				Nodi[i].distanze=new double[MaxNodo];
				for(int j=0;j<MaxNodo;j++){
					Nodi[i].distanze[j]=MatriceIncidenza[i][Nodi[i].vicini[j].nome];
				}
			}
			
			/*for(int i=0;i<MaxNodo;i++){
				System.out.print("nodo" +i );
				for(int j=0;j<MaxNodo;j++){
				System.out.print(" " +Nodi[i].distanze[j]);
				}
				System.out.println("");
			}*/
	}
}