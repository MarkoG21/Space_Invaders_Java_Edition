public class GegnerProjektiel {
	//Anfang der Attribute
	private boolean 		aktiviertGegner = false;
	private int 			gegnerSchussX;
	private int 			gegnerSchussY;
	private char[][]		Spielfeld;
	private boolean			darfIchExistieren;
	private Gegner			schieﬂenderGegner;
	private Deckung			deckung1;
	private Deckung			deckung2;
	private Deckung			deckung3;
	private Deckung			deckung4;
	private Spieler			player;
	
	//Ende der Attribute 
	
//Konstruktor
public GegnerProjektiel(char [][] feld){
		  Spielfeld 	= feld;
		  drawDaEnemyShot();	  
}
//Ende Konstruktor

//Anfang der Methoden
	
public void gibDenSchuetzen(Gegner schuetze){
		schieﬂenderGegner = schuetze;
		gegnerSchussX = schieﬂenderGegner.getGegnerx();
		gegnerSchussY = schieﬂenderGegner.getGegnery()+1;
}
	
public void bewegeP1(){
		if(gegnerSchussY==17){
			aktiviertGegner = false;
		}
		if(gegnerSchussY<=17){
			gegnerSchussY++;
			
		}
		else{
			darfIchExistieren = false;
		}	
}
	
public boolean returnLebensberechtigung(){
		return darfIchExistieren;
}

public boolean returnAktiviertStatus(){
	return aktiviertGegner;
}

public void setzeAktiviertStatusTrue(){
	aktiviertGegner = true; 
}

public void positionYrunterzaehler(){
		gegnerSchussY--;
}
	
public void drawDaEnemyShot(){
		Spielfeld[gegnerSchussY][gegnerSchussX] = 'Y';
}

public void checkPos(Deckung num1, Deckung num2, Deckung num3, Deckung num4, Spieler s1){
	
	deckung1 	= num1;
	deckung2 	= num2;
	deckung3 	= num3;
	deckung4	= num4;
	player		= s1;
	
	if(gegnerSchussY<17){
		
	if(Spielfeld[gegnerSchussY+1][gegnerSchussX]!='.'){

		   if(Spielfeld[gegnerSchussY+1][gegnerSchussX]=='#'){
			   
			   if(gegnerSchussX<=6 && gegnerSchussX>=3){
			   aktiviertGegner=false;
			   deckung1.lebenZiehen();
			   }
			   
			   if(gegnerSchussX>=10 && gegnerSchussX<=13){
				aktiviertGegner=false;
				deckung2.lebenZiehen();   
			   }
			   
			   if(gegnerSchussX>=17 && gegnerSchussX<=20){
			    aktiviertGegner=false;
			    deckung3.lebenZiehen();
			   }
			   
			   if(gegnerSchussX>=24 && gegnerSchussX<=27){
				aktiviertGegner=false;
			    deckung4.lebenZiehen();   
			   }		   		   
		   }
		   
		   
	}
			if(Spielfeld[gegnerSchussY][gegnerSchussX]=='X'){
			   aktiviertGegner=false;
			   player.lebenZiehen();
		   }
	}
}


}
