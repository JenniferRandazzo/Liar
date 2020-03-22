import javax.swing.JOptionPane;


public class Perudos {
	private Giocatore[] giocatori;
	int giocatoreAttivo; // indice del giocatore in turno
	Scommessa scommessaAttiva;
	Scommessa scommessaPrecedente;
		
	String[] nomiGiocatori = { "Mario", "Giovanna", "Marco", "Giuseppe", "Francesca"}; 
	
	
	public Perudos() {
		
	}
	
	public int GetNumGiocatori() {
		return giocatori.length;
	}
	
	public int GetNumDadiInGioco() {
		int numDadiInGioco = 0;
		
		for(int i = 0; i < giocatori.length; i++) {
			numDadiInGioco += giocatori[i].GetNumDadi();
		}
		
		return numDadiInGioco;
	}
	
	public void StartGame() {
		// chiediamo all'utente con quanti opponenti vuole giocare		
		Object[] possibilities = {"1", "2", "3", "4", "5"};
		String s = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Con quanti avversari vuoi giocare?",
		                    "",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "1");

		int numAvversari = Integer.parseInt(s);
		
		// Creiamo 1 giocatore Umano, e numAvversari di giocatori AI
		giocatori = new Giocatore[numAvversari + 1];
		giocatori[0] = new GiocatoreUmano();
		giocatori[0].SetNome("Tu");
		for(int i = 1; i < giocatori.length; i++) {
			giocatori[i] = new GiocatoreAI();
			giocatori[i].SetNome(nomiGiocatori[i-1]);
		}
		
		giocatoreAttivo = 0; // il giocatore umano inizia il gioco
		
		StartGiro();
		
	}
	
	public void StartGiro() {
		for(int i = 0; i < giocatori.length; i++) {
			giocatori[i].InizioGiro();
		}
	
		scommessaAttiva = giocatori[giocatoreAttivo].Scommetti();
		System.out.println("prima scommessa fatta. giocatore attivo: " + giocatoreAttivo);
		PassaIlTurnoAlProssimo();
		
		Loop();
	}
	
	
	public void Loop() {
		System.out.println("Giro iniziato");
		boolean giroFinito = false;
		
		while(!giroFinito) {
			System.out.println("while loop, turno di giocatore: " + giocatoreAttivo);
			scommessaPrecedente = scommessaAttiva;
			scommessaAttiva = giocatori[giocatoreAttivo].DecidiMossa(scommessaAttiva);
			
			if(scommessaAttiva == null) {
				// il giocatoreAttivo ha dubitato, quindi usciamo dal loop
				giroFinito = true;
			}
			else {
				PassaIlTurnoAlProssimo();
			}
		}
		
		System.out.println("loop finito! Qualcuno ha dubitato");
		
		// Un giocatore ha dubitato. Quindi si verifica quale giocatore ha perso. Il giocatore perdente perde un dado / viene eliminato
		
		// contiamo tutte le facce e i dadi dei giocatori
		int numDadi = 0;
		
		String tuttiDadi = ""; // salviamo tutti i dadi di tutti i giocatori qui, per mostrarli
		
		for(int i = 0; i < giocatori.length; i ++)
		{
			// qui contiamo quanti dadi ci sono della faccia scommessa
			numDadi += giocatori[i].ContaDadiConFaccia(scommessaPrecedente.GetFaccia() );
			
			// qui salviamo le stringhe di tutti i dadi dei giocatori
			if( i == 0)
			{
				tuttiDadi += giocatori[i].nome + " hai: " + giocatori[i].ScopriDadi() + "\n";
			}
			else
			{
				tuttiDadi += giocatori[i].nome + " ha: " + giocatori[i].ScopriDadi() + "\n";
			}
		}
		
		// mostriamo tutti i dadi in gioco
		JOptionPane.showMessageDialog(null, tuttiDadi + "Ci sono " + numDadi + " " + scommessaPrecedente.GetFaccia());
		
		// verifichiamo chi ha perso la scommessa
		if( numDadi >= scommessaPrecedente.GetNumDadi() ) {
			// la scommessa del giocatore precedente e' verificata. Il giocatore precedente ha vinto, di conseguenza il giocatore attivo ha perso.
			RimuoviDadoGiocatore(giocatoreAttivo);
		}
		
		else {
			// il giocatore attivo, che ha dubitato, vince. Di conseguenza il giocatore precedente ha perso.
			RimuoviDadoGiocatore(GetIndiceGiocatorePrecedente());		
		}		
	}

	public void RimuoviDadoGiocatore(int indice) {

		if ( giocatori[indice].PerdiGiro() == false) {
			// il giocatore precedente ha perso tutti i dadi, quindi e' eliminato dal gioco
			EliminaGiocatore(indice);
		}
		else
		{
			// nessun giocatore eliminato, facciamo un'altro giro di gioco
			if(indice == 0)
			{
				// giocatore umano ha perso un dado
				JOptionPane.showMessageDialog(null, "Hai perso un dado!");
			}
			else
			{
				JOptionPane.showMessageDialog(null, giocatori[indice].GetNome() +" ha perso un dado!");
			}
			
			// il prossimo turno sara' del giocatore che ha perso un dado
			giocatoreAttivo = indice;
			scommessaAttiva = null; // reset
			StartGiro();
		}
		
	}
	
	public void EliminaGiocatore(int indiceGiocatore) {
		System.out.println("eliminiamo un giocatore...");
		// se il giocatore eliminato e' quello umano (indice 0), il gioco finisce, e l'umano ha perso
		if( indiceGiocatore == 0)
		{
			JOptionPane.showMessageDialog(null, "Hai perso il gioco :(");
		}
		
		// controlliamo se rimarranno almeno 2 giocatori dopo l'eliminazione, altrimenti il gioco finisce
		else if( giocatori.length < 3) {
			// eliminato l'ultimo giocatoreAI, rimane solo il giocatore umano, quindi hai vinto :)
			JOptionPane.showMessageDialog(null, "Hai vinto il gioco :)");
		}
		else
		{
			// Il gioco continua, eliminiamo semplicemente il giocatoreAI che ha perso.
			System.out.println("il gioco continua..");
			JOptionPane.showMessageDialog(null, giocatori[indiceGiocatore].GetNome() + " é stato eliminato");
			// creiamo un nuovo arrray, minore di un elemento rispetto all'array giocatori.
			// il nuovo array contiene gli stessi elementi di giocatori, meno il giocatore eliminato
			Giocatore[] giocatoriCorrenti = new Giocatore[giocatori.length - 1];
			
			for( int i = 0; i < indiceGiocatore; i++) {
				giocatoriCorrenti[i] = giocatori[i];
			}
			
			for( int i = indiceGiocatore+1; i < giocatori.length; i++) {
				giocatoriCorrenti[i-1] = giocatori[i];
			}
			
			giocatori = giocatoriCorrenti;
			
			// il turno passa al giocatore umano, che e' in indice 0
			giocatoreAttivo = 0;
			
			StartGiro();
		}
	}
	
	// restituise il giocatore precedente al giocatore attivo
	public int GetIndiceGiocatorePrecedente() {
		if(giocatoreAttivo > 0) {
			return giocatoreAttivo -1; 
		}
		
		else
		{
			return giocatori.length - 1;
		}
	}
	
	public void PassaIlTurnoAlProssimo() {
		if(giocatoreAttivo < giocatori.length - 1)
		{
			giocatoreAttivo++;
		}
		else
		{
			giocatoreAttivo = 0;
		}
		System.out.println("turno del prossimo giocatore, n. " + giocatoreAttivo);
		
	}
}
