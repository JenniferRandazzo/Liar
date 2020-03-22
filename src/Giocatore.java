
public class Giocatore {
	
	protected Bicchiere bicchiere;
	protected String nome;
	
	public Giocatore() {
		bicchiere = new Bicchiere();

		System.out.println("giocatore ha inizializzato il bicchiere");
	}
	
	public String GetNome()
	{
		return nome;
	}
	
	public void SetNome(String nome) {
		this.nome = nome;
	}
	
	public void  InizioGiro() {
		bicchiere.MescolaDadi();
	}
	
	public Scommessa DecidiMossa(Scommessa scommessa) {
		return scommessa;
	}
	
	public Scommessa Scommetti() {
		return null;
	}
	
	
	public Scommessa Rilancia(Scommessa scommessa) {
		return scommessa;
	}
	
	
	public int ContaDadiConFaccia(int faccia) {
		return bicchiere.GetNumDadiConFaccia(faccia);
	}
	
	public String ScopriDadi() {
		return bicchiere.StringaValoreDadi();
	}
	
	public int GetNumDadi() {
		return bicchiere.GetNumDadi();
	}
	
	
	// Rimuove un dado dal bicchiere del giocatore.
	// Restituisce True, se il giocatore perde solo il giro, ed e' ancora il gioco
	// False, se il giocatore ha perso la partita, e non ha piu' dadi in gioco
	public boolean PerdiGiro() {
		// rimuovi un dado dal bicchiere
		return this.bicchiere.TogliDado();
	}
}
