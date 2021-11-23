

public class Gegner {
  
  // Anfang Attribute
	private int 		leben 							= 1;
	private int 		gegnerPosY 						= 1;
	private int 		gegnerPosX 						= 10;
	private char[][] 	Spielfeld;
	private boolean 	entscheideUeberRechtsoderLinks  = false ; //Gibt in der Spiellogik an ob die Gegner nach rechts oder links gemoved werden soll
  // Ende Attribute

//Konstruktor der Gegner	
public Gegner(int yhold, int x, char[][] Feld){
		gegnerPosY = yhold;
		gegnerPosX = x;
		Spielfeld = Feld;
}	
//Ende Konstruktor


//Anfang Operationen
//Gibt in der Spiellogik an ob die Gegner nach rechts oder links gemoved werden soll
public void setzeEsAufFalse(){
	entscheideUeberRechtsoderLinks = false;
}

//Gibt in der Spiellogik an ob die Gegner nach rechts oder links gemoved werden soll
public void setzeEsAufTrue(){
	entscheideUeberRechtsoderLinks = true;
}
//Returnt Entscheidungsvariable für rechts oder links
public boolean gibWert(){
	return entscheideUeberRechtsoderLinks; 
}

//Bewege den Gegner nach rechts	  
public void bewegeR(){
	  gegnerPosX++;
}

//Bewege den Gegner nach links
public void bewegeL(){
	  gegnerPosX--;
}
 
//Bewege den Gegner eine Zeile nach unten
public void springZeile(){
	  gegnerPosY++;
}
	  
//Returnt Y-Position des Gegners
public int getGegnery(){
	  return gegnerPosY;
}

//Returnt X-Position des Gegners
public int getGegnerx(){
	  return gegnerPosX;
}
  
//Zeichne Gegner neu in das Spielfeld außer er hat 0 Leben  
public void drawDaShit(){
	  if(leben>0){
		  Spielfeld[gegnerPosY][gegnerPosX]='§';
	  }
	  else{
		  Spielfeld[gegnerPosY][gegnerPosX]='~';
	  }
}

//Ziehe dem Gegner 1 Leben ab 
public void stirb(){
	leben--; 
} 

//Return-Operation für Leben des Gegners
public boolean getLeben(){
	if(leben==0){
		return false;
	}
	
	else{
		return true;
	}
}
 
 
// Ende Operationen  
} // end of Gegner
