public class SpielerProjektiel {
  
// Anfang Attribute
    private 	int 			SchussPosY;
	private 	int 			SchussPosX;
	public 		boolean 		aktiviert 		= false;
	private 	int 			score 			= 0;
	private 	Deckung 		deckung1;
	private 	Deckung 		deckung2;
	private 	Deckung 		deckung3;
	private 	Deckung 		deckung4;
	private 	boolean 		hatgewonnen 	= false;
	private		Spieler 		s1;
	private		char [][] 		Spielfeld;
// Ende Attribute	
  
//Konstruktor
public SpielerProjektiel(Spieler s,char [][] feld){
	  s1		= s;
	  Spielfeld	= feld;
}
//Ende Konstruktor

//Anfang Operationen	

//Operation zum Eintragen in das Spielfeld
public void drawDaShot(){
	Spielfeld[SchussPosY][SchussPosX] ='|';
}

//Return-Operation für X-Position
public int gimmethex(){
	return SchussPosX;
}

//Return-Operation für Y-Position
public int gimmethey(){
	return SchussPosY;
}

////////////////////////////////////////////////////////////
//Score Zeug
//Return-Operation für den Score
public int returnscore(){
	return score;
}

//Return-Operation für aktivierungs-Status
public boolean gibAktiviertStatus(){
	return aktiviert;
}

//Methode für Rückgabe der Zehner des Scores in char
private char getScoreZehner(){
	char a = '0';
	if(score>9){
		if(score<20 && score>9){ a = '1';}
		if(score<30 && score>19){ a = '2';}
		if(score<40 && score>29){ a = '3';}
		if(score<50 && score>39){ a = '4';}
		if(score<60 && score>49){ a = '5';}
		if(score==60){ a = '6';}
	}
	else{
		 a = '0';
	}
	return a;
}

//Methode für Rückgabe der Einer des Scores in char
private char getScoreEiner(){
	char bae = '0';
	if(score<10){
		 bae = (char) (score+'0');
	}
	else{
		if(score<20 && score>9){
			int c = (score - 10 + '0');
			 bae = (char) c;
			}
		if(score<30 && score>19){
			int c = (score - 20 + '0');
			 bae = (char) c;
		}
		if(score<40 && score>29){
			int c = (score - 30 + '0');
			bae = (char) c;
		}
		if(score<50 && score>39){
			int c = (score - 40 + '0');
			 bae = (char) c;
		}
		if(score<60 && score>49){
			int c = (score - 50 + '0');
			 bae = (char) c;
		}
		if(score==60){
			 bae = '0';
		}
	}
	return bae;	
}

//Operation zum Eintragen in das Spielfeld
public void drawScore(){
	Spielfeld[17][23] = 'S';
	Spielfeld[17][24] = 'C';
	Spielfeld[17][25] = 'O';
	Spielfeld[17][26] = 'R';
	Spielfeld[17][27] = 'E';
	Spielfeld[17][28] = ':';
	Spielfeld[17][29] = getScoreZehner();
	Spielfeld[17][30] = getScoreEiner();
}

//Ende Score-Zeug
/////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////


//Prüfungsoperation für das töten aller Gegner (Score==60)
public boolean Gewinnen(){
	  if(score==60){
		hatgewonnen = true;
	  }
	  return hatgewonnen;
}

//Aktivierungsoperation des Spieler-Schusses
public void aktiviere(){
	SchussPosX = s1.gibXPos();
	SchussPosY = s1.gibYPos()-2;
	aktiviert = true;	
}

//Operation zum Bewegen des Schusses auf der Y-Achse
public void bewegeP1(){
	if(SchussPosY>0){
		SchussPosY--;
	}
	else{
		aktiviert = false;
	}
}

//Kollisionsabfrage des Spieler-Schusses mit folgender Reaktion
public void checkPos(Gegner[]FD, Deckung num1, Deckung num2, Deckung num3, Deckung num4){
	
	deckung1 = num1;
	deckung2 = num2;
	deckung3 = num3;
	deckung4 = num4;
	
	if(SchussPosY>0){
	
	//Generelle Überprüfung auf Kollision
	if(Spielfeld[SchussPosY-1][SchussPosX]!='.'){
	   
		//Prüfung auf Kollision mit Gegnern
		if(Spielfeld[SchussPosY-1][SchussPosX]=='§'){
		   for(int marko=0; marko<60; marko++){
			   if(FD[marko].getGegnerx()==SchussPosX && FD[marko].getGegnery()==SchussPosY-1){
			   FD[marko].stirb();
			   score++;
			   aktiviert=false;
			   break;
			   }	 
		   }
		}
			//Prüfung auf Kollision mit Barrieren
			if(Spielfeld[SchussPosY-1][SchussPosX]=='#'){
			   
			   if(SchussPosX<=6 && SchussPosX>=3){
			   aktiviert=false;
			   deckung1.lebenZiehen();
			   }
			   
			   if(SchussPosX>=10 && SchussPosX<=13){
				aktiviert=false;
				deckung2.lebenZiehen();   
			   }
			   
			   if(SchussPosX>=17 && SchussPosX<=20){
			    aktiviert=false;
			    deckung3.lebenZiehen();
			   }
			   
			   if(SchussPosX>=24 && SchussPosX<=27){
				aktiviert=false;
			    deckung4.lebenZiehen();   
			   }		   		   
			}
	  }
	}
} //Ende Kollisionsprüfung

} // end of Projektiel
