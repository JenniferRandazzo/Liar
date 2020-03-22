
public class Dado {
	private int faccia;
	
	
	public Dado() {
		Mescola(); // inizializza faccia con un valore random
			
	}
	
	public int Mescola() {
		
		faccia = (int) ( (Math.random() * 6 ) + 1);
		return faccia;
	}
	
	public int GetFaccia() {
		return faccia;
	}
	
	
}
