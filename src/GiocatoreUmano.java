import javax.swing.*;
public class GiocatoreUmano extends Giocatore {
	
	
	public GiocatoreUmano() {
		System.out.println("giocatore umano creato");
		
		this.InizioGiro();
	}
	
	public Scommessa Scommetti() {
		
		System.out.println("l'utente decide la mossa");
		// Mostra all'utente i propri dadi
		JOptionPane.showMessageDialog(null, "I tuoi dadi sono: " + this.bicchiere.StringaValoreDadi());
		
		return CreaScommessa(null); // null perche' non abbiamo nessuna scommessa da valutare, visto che stiamo facendo la prima mossa

	}
	
	public Scommessa CreaScommessa(Scommessa scommessaPrecedente) {
		// Qui il giocatore umano decide la sua scommessa
		// possiamo solo permettere al giocatore umano di fare scommesse valide.
		
		// se c'e' stata una scommessa precedente, allora non si puo' scegliere una faccia minore di quella della scommessa precedente
		Object[] facce;
		
		int numFacceValide = 6;
		int facciaIniziale = 1;
		
		if(scommessaPrecedente != null) {
			facciaIniziale = scommessaPrecedente.GetFaccia();
		}
		
		// il numero di facce valide e' pari al numero di facce del dado (6), meno la faccia attuale della scommessaPrecedente, + 1 perche'
		// la faccia attuale e' compresa tra le scommesse valide
		numFacceValide = 6 - facciaIniziale + 1;
		
		facce = new Object[numFacceValide];
		
		for(int i = 0; i < numFacceValide; i++) {
			facce[i] = "" + ( i + facciaIniziale);
		}
		
		String fs = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Che faccia scegli?",
		                    "",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    facce,
		                    facce[0]);
	
		int facciaScelta = Integer.parseInt(fs);
		
		// Non si puo' scommettere che ci siano piu' dadi di quanti ce ne sono effettivamente in gioco
		// quindi giocatore potra' solo scegliere un massimo di dadi pari al numero di dadi in gioco.
		
		// Se l'utente rilancia ( c'era gia' una scommessa precedente),
		// puo' o aumentare il numero di dadi della scommessa precedente, e mantenere la stessa faccia
		// oppure aumentare la faccia di uno (se la faccia e' minore di 6)
		int maxDadi = Main.perudo.GetNumDadiInGioco();
		int dadoIniziale = 1;
		
		if( scommessaPrecedente != null){
			if(scommessaPrecedente.GetFaccia() == facciaScelta ){
				// l'utente ha scelto di scommettere sulla stessa faccia del giocatore precedente, allora puo' solo rilanciare con un numero piu' alto
				dadoIniziale = scommessaPrecedente.GetNumDadi() + 1;
			}
		}
		
		int numDadiDaScegliere = maxDadi - (dadoIniziale-1);
		
		Object[] numDadi = new Object[numDadiDaScegliere];
		System.out.println("dadi in gioco: " + numDadi);
		
		for(int i = 0; i < numDadiDaScegliere; i ++) {
			numDadi[i] = "" + (i + dadoIniziale );
		}

		String s = (String)JOptionPane.showInputDialog(
							null,
							"Quanti dadi?",
		                    "",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    numDadi,
		                    numDadi[0]);

		int volte = Integer.parseInt(s);
				
		Scommessa scommessa = new Scommessa();
		scommessa.SetFaccia(facciaScelta);
		scommessa.SetNumDadi(volte);
		return scommessa;
	}
	
	
	
	public Scommessa DecidiMossa(Scommessa scommessa) {
		System.out.println("giocatore umano decide mossa");
		System.out.println("Scommessa A.I: " + scommessa);
		//Custom button text
		Object[] options = {"Dubito",
		                    "Rilancio"};
		int n = JOptionPane.showOptionDialog(null,
			"Il giocatore precedente ha scommesso " +  scommessa.GetNumDadi() + " " + scommessa.GetFaccia() + "\nCosa vuoi fare?",
			"E' di nuovo il tuo turno",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]);
		
		
		System.out.println("decisione: " + n);
		
		if( n == 0 ) {
			//Dubita();
			System.out.println(" Hai dubitato ");
			scommessa = null;
		}
		
		else {
			scommessa = Rilancia(scommessa);
		}
		
		return scommessa;
		
	}
	
	public Scommessa Rilancia(Scommessa scommessa) {
		System.out.println("l'utente rilancia");
		// Mostra all'utente i propri dadi
		JOptionPane.showMessageDialog(null, "I tuoi dadi sono: " + this.bicchiere.StringaValoreDadi() + ".\nE' stato scommesso che ci sono " + scommessa.GetNumDadi() + " " + scommessa.GetFaccia() + ".\nCosa rilanci?");
		
		
		// Qui il giocatore umano decide la sua scommessa
		return CreaScommessa(scommessa);
	}

}
