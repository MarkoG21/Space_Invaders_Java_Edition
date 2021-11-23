/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 13.10.2016
  * @author 
  */

public class Deckung{
  
// Anfang Attribute 
	private 	int 		leben 		= 15;
	private 	int 		deckungY	= 12;
	private 	int 		deckungX; 
	private 	char [][] 	Spielfeld;
//Ende Attribute
	
//Konstruktor
public Deckung(int x, char [][] Feld){
		Spielfeld = Feld;
		deckungX=x+1;
		drawDaShit();
}
//Ende Konstruktor

//Anfang Operationen

//Draw-Operation zum Eintragen in das Spielfeld
public void drawDaShit(){
		if(leben>0){
		Spielfeld[deckungY][deckungX] = '#';
		Spielfeld[deckungY][deckungX+1] = '#';
		Spielfeld[deckungY][deckungX+2] = '#';
		Spielfeld[deckungY+1][deckungX+2] = '#';
		Spielfeld[deckungY+1][deckungX-1] = '#';
		Spielfeld[deckungY][deckungX-1] = '#';	
		}
		else{
			Spielfeld[deckungY][deckungX] = '.';
			Spielfeld[deckungY][deckungX+1] = '.';
			Spielfeld[deckungY][deckungX+2] = '.';
			Spielfeld[deckungY+1][deckungX+2] = '.';
			Spielfeld[deckungY+1][deckungX-1] = '.';
			Spielfeld[deckungY][deckungX-1] = '.';
		}
}

//Operation zum verringern des Lebens durch Schussberührung
public void lebenZiehen(){
	leben--;	
}
	
// Ende Methoden	
	
} // end of Deckung	
	
  

