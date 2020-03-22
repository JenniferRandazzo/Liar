
import javax.swing.*;

public class GiocatoreAI extends Giocatore {
	
	private boolean bluffatore = false; // true se l'AI bluffa, ovvero scommette dadi che non ha nel proprio bicchiere
	private float dubbioso = 0.0f; // Probabilita' di quanto sia dubbiosa l'A.I. (valore da 0-non dubbioso, a 1- dubbioso)
	private float azzardoso = 0.0f; // Percentuale di quanto sia azzardosa l'A.I. 0= cauta, 1= azzardosa 
	
	public GiocatoreAI() {
		// crea una personalita' unica per l'A.I.
		
		double bluff =  Math.random() * 2;
		if(bluff > 1){
			bluffatore = true;
		}
		
		dubbioso = (float) Math.random();
		azzardoso = (float) Math.random();
		
		System.out.println("giocatore artificiale creato. bluff= " + bluffatore + ", dubbioso: " + dubbioso + ", azzardoso: " + azzardoso);
		
		this.InizioGiro();
	}
	
	public Scommessa Scommetti() {
	
		int facciaFrequente = bicchiere.GetFacciaFrequente();
		
		int numDadi = bicchiere.GetNumDadiConFaccia( facciaFrequente ) + ((Main.perudo.GetNumDadiInGioco() - bicchiere.GetNumDadi()) / 3);
		
		// controlliamo quanto sia azzardosa la nostra A.I. e modifichiamo la sua scommessa
		if( azzardoso < 0.3) {
			// A.I. molto cauta, meglio fare una scommessa meno azzardosa
			numDadi -= 1;
			if(numDadi < 1)
			{
				numDadi = 1;
			}
		}
		else if(bluffatore == true) {
			numDadi += 1;
		}
		
		if(azzardoso > 0.7) {
			numDadi += 1;
		}
		
		Scommessa scommessa = new Scommessa();
		System.out.println("AI scommette nuova scommessa, numDadi: " + numDadi + "facciaFrequente: " + bicchiere.GetFacciaFrequente() );
		JOptionPane.showMessageDialog(null, this.nome + " scommette che ci sono " + numDadi  + " " + facciaFrequente);
		
		scommessa.SetNumDadi(numDadi);
		scommessa.SetFaccia(facciaFrequente);
		
		return scommessa;
	}
	
	
	
	public Scommessa DecidiMossa(Scommessa scommessa) {
		System.out.println("Giocatore A.I. decide la sua mossa. scommessa.GetNumDadi: "+ scommessa.GetNumDadi() + "; NumDadiInGioco: " + Main.perudo.GetNumDadiInGioco());
		
		// controlliamo se AI ha dadi uguali alla scommessa.faccia
		int dadiUguali = this.bicchiere.GetNumDadiConFaccia( scommessa.GetFaccia() );
		
		// controlliamo la scommessa, e decidiamo se dubitare o meno
		int maxDadiAccettabili = ((Main.perudo.GetNumDadiInGioco() - bicchiere.GetNumDadi()) / 3) + dadiUguali;
		
		// modifichiamo in base a quanto sia dubbiosa la nostra A.I.
		if(Main.perudo.GetNumDadiInGioco() > 5) {
			maxDadiAccettabili -= (int) (dubbioso * 2.3d);
		}
		
		if(scommessa.GetNumDadi() > maxDadiAccettabili) {
			// dubita
			JOptionPane.showMessageDialog(null, this.nome + " ha dubitato che ci sono " + scommessa.GetNumDadi()  + " " + scommessa.GetFaccia());
			scommessa = null;
		}
		else {
			scommessa = Rilancia(scommessa);
		}
		
		
		return scommessa;
	}
	
	public Scommessa Rilancia(Scommessa scommessa) {
		int numDadi = scommessa.GetNumDadi();
		int faccia = scommessa.GetFaccia();
		
		// controlliamo se AI ha dadi uguali alla scommessa.faccia
		int dadiUguali = this.bicchiere.GetNumDadiConFaccia( scommessa.GetFaccia() );
		
		int maxDadiAccettabili = ((Main.perudo.GetNumDadiInGioco() - bicchiere.GetNumDadi()) / 3) + dadiUguali;
		
		// se la nostra A.I. e' azzardosa, allora preferira' rilanciare la faccia, il piu' vicino possibile alla faccia piu' frequente
		// che ha lui stesso
		int facciaFrequente = this.bicchiere.GetFacciaFrequente();
		
		if( azzardoso > 0.4 && facciaFrequente > scommessa.GetFaccia()) {
			// rilanciamo la faccia
			scommessa.SetFaccia(this.bicchiere.GetFacciaFrequente());
			
			int dadi = bicchiere.GetNumDadiConFaccia( facciaFrequente ) + ((Main.perudo.GetNumDadiInGioco() - bicchiere.GetNumDadi()) / 3);
			
			scommessa.SetNumDadi(dadi);
			
			// controlliamo quanto sia azzardosa la nostra A.I. e modifichiamo la sua scommessa
			if(bluffatore == true) {
				numDadi += 1;
			}
		}
		
		else if( scommessa.GetNumDadi() < maxDadiAccettabili ) {
			
			scommessa.SetNumDadi(numDadi + 1);
		}
		else if(scommessa.GetFaccia() < 6) {
			
			scommessa.SetFaccia(faccia + 1);
		}
		else {
			scommessa.SetNumDadi(numDadi + 1);
		}
		
		JOptionPane.showMessageDialog(null, this.nome + " ha scommesso che ci sono " + scommessa.GetNumDadi()  + " " + scommessa.GetFaccia());
		
		return scommessa;
	}

}
