public class Spieler {
  
  // Anfang Attribute - Der "Y" wert wird im Array von Oben nach unten gezählt.
	private 	int 		xmitte 		= 4;
	private 	int 		ymitte 		= 16;
	private 	int 		leben 		= 3;
	private 	char[][] 	Spielfeld;
	private 	boolean 	tot 		= false;
// Ende Attribute


 //Anfang Konstruktor 
public Spieler(char [][] Feld){
		Spielfeld = Feld;
		drawDaSpieler();
}
//Ende Konstruktor     
	 
// Anfang Operationen
//Operation zum Eintragen des Spielrs in das Spielfeld
public void drawDaSpieler(){
			 Spielfeld[ymitte][xmitte] = 'X';
			  
			  Spielfeld[ymitte][xmitte-1] = 'X';
			  
			  Spielfeld[ymitte-1][xmitte] = 'X';
			  
			  Spielfeld[ymitte][xmitte+1] = 'X';		
}

//Operation zum verringern des Lebens
public void lebenZiehen(){
	  leben--;
}
  
//Operation zum Bewegen nach links
public void nachLinks(){
	  xmitte = xmitte-1;
}

//Operation zum Bewegen nach rechts  
public void nachRechts(){
	  xmitte = xmitte+1;
}

//Return Operation für X-Position 
public int gibXPos(){
	  return xmitte;
}
 
//Return Operation für Y-Position 
public int gibYPos(){
	  return ymitte;
}

//Prüfungsmethode für das Leben des Spielers
public boolean istDerBoiTot(){
	  if(leben==0){
		  tot = true;
	  }
	  return tot;
}

//Operation zum Eintragen des Spieler-Lebens in das Array
public void drawDaLife(){
	Spielfeld[17][0] = 'L';
	Spielfeld[17][1] = 'I';
	Spielfeld[17][2] = 'F';
	Spielfeld[17][3] = 'E';
	Spielfeld[17][4] = ':';
	Spielfeld[17][5] = (char) (leben+'0');
}

//Operation zum returnrn des Spielerlebens
public int gibLife(){
	return leben;
}

//Ende Operationen

}//Ende Spieler
  