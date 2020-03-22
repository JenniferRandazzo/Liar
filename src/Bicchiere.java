
public class Bicchiere {
	private static int NUM_FACCE = 6;
	private static int NUM_DADI_INIZIALI = 5;
	private Dado dadi[];
	
	private int contatoreFacce[];
	private int facciaFrequente;
	

	public Bicchiere() {
		// inizializza tutti i dadi
		dadi = new Dado[NUM_DADI_INIZIALI];
		for(int i = 0; i< dadi.length; i++) {
			dadi[i] = new Dado();
		}
	}
	
	public int GetNumDadi() {
		return dadi.length;
	}
	
	public int[] GetValoreDadi() {
		int[] valoriDadi = new int[dadi.length];
		
		for(int i = 0; i < dadi.length; i++) {
			valoriDadi[i] = dadi[i].GetFaccia();
		}
		
		return valoriDadi;
		
	}
	
	public String StringaValoreDadi() {
		String s = "";
		
		for(int i = 0; i<dadi.length; i++) {
			s += dadi[i].GetFaccia() + ", ";
		}
		
		return s;
	}
	
	
	// restituisce true se è possibile togliere un dado, false se non ci sono più dadi nel bicchiere
	public boolean TogliDado() {
		if(dadi.length < 2) {
			return false;
		}
		
		 Dado nuoviDadi[] = new Dado[dadi.length-1];
		 for(int i = 0; i<nuoviDadi.length; i++) {
			 nuoviDadi[i] = new Dado();
		}
		 
		 dadi = nuoviDadi;
		 return true;
	}
	
	public void MescolaDadi() {
		for(int i = 0; i < dadi.length; i++) {
			dadi[i].Mescola();
		}
	}
	
	public void ContaFacce() {
		
		// mantiene la conta di quante facce dello stesso tipo ci sono nei dadi del bicchiere. ContatoreFacce[i] è uguale al numero di dadi nel bicchiere
		// la cui faccia è i+1 ( perchè l'array parte da 0)
		contatoreFacce = new int[NUM_FACCE];
		
		// conta quante facce dello stesso tipo ci sono nei dadi del bicchiere.
		for(int i = 0; i < dadi.length; i++) {
			contatoreFacce[dadi[i].GetFaccia() -1]++;
		}
		
		// trova il numero che si ripete più volte all'interno del bicchiere
		int numDadi = 0;
		facciaFrequente = 0;
		for(int i = 0; i < NUM_FACCE; i++) {
			if(contatoreFacce[i] >= numDadi) {
				numDadi = contatoreFacce[i];
				facciaFrequente = i + 1;
			}
		}
		System.out.println("ContaFacce, faccia frequente: " + facciaFrequente);
	}
	
	public int GetFacciaFrequente() {
		
		// aggiorniamo il numero di facciaFreuente
		ContaFacce();
		
		return facciaFrequente;
	}
	
	public int GetNumDadiConFaccia(int faccia) {
		int numDadi = 0;
		
		for(int i = 0; i < dadi.length; i++) {
			if(dadi[i].GetFaccia() == faccia ) {
				numDadi++;
			}
		}
		
		return numDadi;
	}
	

}
