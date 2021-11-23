import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Spiellogik {
  
  // Anfang Attribute 
    private   char [][]       spielfeld           = new char [18][31];
  private   Spieler       s1;
  private   Deckung       deckung1;
  private   Deckung       deckung2;
  private   Deckung       deckung3;
  private   Deckung       deckung4;
  private   Gegner []       gegner;
  private   SpielerProjektiel   p1;
  private   GegnerProjektiel  gP1;
  private   GegnerProjektiel  gP2;
    private   int         bächleWarHier;
    private   int         bächleWarHier2;
    private   int                 warteschlange         = 0;
    private   int                 warteschlange2        = 0;
  private   GUI         t1;
  private   long        startZeit;
  private   int         gebrauchteZeitinS;
  private   long        endZeit;
  private   Clip        clip;
// Ende Attribute

//Konstruktor
public Spiellogik(GUI GUI) throws InterruptedException{
  
    //Initialisierung der einzelnen Objekte
    s1      = new Spieler     (spielfeld);
    deckung1  = new Deckung     (3,spielfeld);
    deckung2  = new Deckung     (10, spielfeld);
    deckung3  = new Deckung     (17, spielfeld);
    deckung4  = new Deckung     (24, spielfeld);
    gegner    = new Gegner      [60];
    p1      = new SpielerProjektiel (s1, spielfeld);
    gP1         = new GegnerProjektiel  (spielfeld);
    gP2     = new GegnerProjektiel  (spielfeld);
      t1      = GUI;
    //Ende Grundinitialisierung
    
    //Genauere Initialisierung der Gegner
    int rechts = 11;
    int links = 1;
    
    for(int marko = 0; marko<gegner.length; marko++){
        gegner[marko] = new Gegner(links , rechts, spielfeld);
        rechts++;
        if(rechts==21){
          links++;
          rechts = 11;
        } 
    }
    //Ende Initialisierung
    //Setzen der Startzeit
    startZeit = System.currentTimeMillis(); 
    
} //Ende Konstruktor
  // Anfang Methoden

//Anfang Operationen

public char[][] returneSpielfeld(){
  return spielfeld;
}


//Befüllung des Spielfeldes ohne Objekte (Platzhalter)
private void füllen(){
    
    
    for (int i1=0; i1<18; i1++){
       
        for(int k=0; k<31; k++){
         spielfeld[i1][k] = '.';
        }  
    }
}


//Spiel starten Operation
public void starteSpiel() throws InterruptedException {
    starteMusik();
    füllen();
    t1.setzeInhalte();
    gameLoop();
    
    
    
} 

//Musik Zeug
private void starteMusik(){
  
  //AudioInputStream audioInputStream;
  
}

//Stoppe Musikwiedergabe bei Spielende
private void musikStop(){
  clip.stop();
}


//Aktualisierung des Spielfeldes mit darauf folgender Ausgabe
private void aktua(){
  //Leere Befüllung
  füllen();
  
  //Aktualisierte Werte neu eintragen
  p1.Gewinnen();
  s1.drawDaSpieler();
  deckung1.drawDaShit();
  deckung2.drawDaShit();
  deckung3.drawDaShit();
  deckung4.drawDaShit();
  
  //Alle Gegner neu eintragen
  for(int zaehler = 0; zaehler<gegner.length; zaehler++){
    gegner[zaehler].drawDaShit();
  }
  
  //Spieler Projektiel, falls aktiviert neu eintragen
  if(p1.gibAktiviertStatus()==true){
    p1.checkPos(gegner, deckung1,deckung2,deckung3,deckung4);
    p1.drawDaShot();
    p1.bewegeP1();
  }
  
  //Gegner Projektiel, falls aktiviert neu eintragen --> Geschwindigkeit variabel anhand von Score
  //Gegnerprojektiel #1
  if(gP1.returnAktiviertStatus()==true){

    gP1.checkPos(deckung1, deckung2, deckung3, deckung4, s1);
    gP1.drawDaEnemyShot();
    if(p1.returnscore()>=15){
      warteschlange++;
      if(warteschlange==1){
        bächleWarHier=0;
      }
      if(bächleWarHier==2){
      gP1.bewegeP1();
      bächleWarHier= 0;
      
       }
      bächleWarHier++;  
    }

    else{
      if(bächleWarHier==6){
        gP1.bewegeP1();
        bächleWarHier= 0;
      }
      bächleWarHier++;
    }
  }
  
  //Gegnerprojektiel #2
  if(gP2.returnAktiviertStatus()==true){

    gP2.checkPos(deckung1, deckung2, deckung3, deckung4, s1);
    gP2.drawDaEnemyShot();
    if(p1.returnscore()>=15){
      warteschlange2++;
      if(warteschlange2==1){
        bächleWarHier2=0;
      }
      if(bächleWarHier2==2){
      gP2.bewegeP1();
      bächleWarHier2= 0;
      
       }
      bächleWarHier2++; 
    }

    else{
      if(bächleWarHier2==6){
        gP2.bewegeP1();
        bächleWarHier2= 0;
      }
      bächleWarHier2++;
    }
  }
  
  //Eintragen des Scores in der rechten unteren Ecke des Spielfeldes
  p1.drawScore();
  s1.drawDaLife();
  
} //Ende der Aktualisierungs-Operation

//Operation zur Beendigung des Spiels auf der Console 
private void gameOver() {
   
    if(spielVorbeiPrüfen()==0){
        t1.gameOverAusgeben("Game Over: Die Aliens haben die Erde in ihre Besitz genommen.");
     }
     if(spielVorbeiPrüfen()==1){
       t1.gameOverAusgeben("Game Over: Der Spieler hat die Welt mit "+s1.gibLife()+" verbleibenden Leben gerettet.");
     }
      
}

//Prüfungsoperation für das Beenden des Spiels
private int spielVorbeiPrüfen(){
  
    if(gegner[59].getGegnery()==12){
      return 0;
      
    }
    if(s1.istDerBoiTot()==true){
      return 0;
    }
    
    if(p1.Gewinnen()==true){
      return 1;
    }
    else{
      return 2;
    }
}
 
//Prüfungsmethode für noch lebende Gegner in zufälliger Spalte der Gegner i
private int lebtDerGegner(int i){

    int gegnerName = i-1;
    int gegnerFound = 0;
    int serumZaehler = gegnerName+50;
    
    while(serumZaehler>=gegnerName){
      if(gegner[serumZaehler].getLeben()==true){
       int gegnerGefunden = serumZaehler;
       gegnerFound = gegnerGefunden;
       break;
      }
     
        
      else{
        if(serumZaehler==gegnerName){
         gegnerFound = -1;
        }
      }
      
     serumZaehler = serumZaehler-10;
    }
    return gegnerFound;
}
 
//Operation für das starten des Spielerschusses
//GegnerProjektiel #1
private void schießZurueck(){
    int i = (int) (Math.random()*10.99);
    if(i==0){
      i++;
    }
      if (lebtDerGegner(i)!=-1){
        gP1.gibDenSchuetzen(gegner[lebtDerGegner(i)]);
      }
      else{
        schießZurueck();
      }
}

//GegnerProjektiel #2
private void schießZurueck2(){
    int i = (int) (Math.random()*10.99);
    if(i==0){
      i++;
    }
      if (lebtDerGegner(i)!=-1){
        gP2.gibDenSchuetzen(gegner[lebtDerGegner(i)]);
      }
      else{
        schießZurueck2();
      }
}

//Operation für die Zeitberechnung
private void berechneZeit(){
  int minuten;
    int stunden;
    gebrauchteZeitinS   = (int )(endZeit - startZeit)/1000;
    stunden         = gebrauchteZeitinS / 3600;
    minuten         = (gebrauchteZeitinS % 3600) / 60;
    int sekunden      = (gebrauchteZeitinS % 3600) % 60;

    t1.spielzeitAusgeben("Spielzeit: "+stunden  + " Stunden " + minuten + " Minuten " + sekunden +" Sekunden");
}
  
//Anfang Gameloop
private void gameLoop() throws InterruptedException{

    double  enemyBewegeZaehler                  = 0.0;
      int   zeitvariable                    = 0;
      int   gegnerRefenzDamitManDieEdgeDetectionSehenKann     = 9;
      int   gegnerRefenzDamitManDieEdgeDetectionSehenKannLinks  = 0;
      boolean darfIchNachLinks                  =false;
     

    
      while(true){
      Thread.sleep(70);
      
    //////////////////////////////////////////////////
      //Keylistener Abfrage Pfeiltaste rechts
      if(t1.returnBooleanRecht()==true){
        if(s1.gibXPos()<29){
          s1.nachRechts();
        }
      } 
    ///////////////////////////////////////////////////
      //Keylistener Abfrage Pfeiltaste links
      if(t1.returnBooleanLeft()==true){
        if(s1.gibXPos()>1){
          s1.nachLinks();
        }
      } 
    ///////////////////////////////////////////////////
      //Keylistener Abfrage Leertaste
      if(t1.returnBooleanLeer()==true){
        if(p1.gibAktiviertStatus()==false){
          p1.aktiviere();
        } 
      }
      
    //////////////////////////////////////////////////////
      //Gegnerbewegung mit Verschnellerung angand von Score-Wert
    gegner[gegnerRefenzDamitManDieEdgeDetectionSehenKann].setzeEsAufTrue();

           if(darfIchNachLinks==false){
      if(gegner[gegnerRefenzDamitManDieEdgeDetectionSehenKann].getGegnerx()==30){
        darfIchNachLinks=true;
           }
      else{
        if(enemyBewegeZaehler>=25){
          for(int bc = 0; bc<60 ; bc++){
          gegner[bc].bewegeR();
          enemyBewegeZaehler=0;
          } 
          }
      }
    }
          
           
           if(darfIchNachLinks==true){
      if(gegner[gegnerRefenzDamitManDieEdgeDetectionSehenKannLinks].getGegnerx()==0){
        for(int bc = 0; bc<60 ; bc++){
        gegner[bc].springZeile();
        }
        darfIchNachLinks=false;
           }
      else{
        if(enemyBewegeZaehler>=20){
          for(int bc = 0; bc<60 ; bc++){
          gegner[bc].bewegeL();
          enemyBewegeZaehler=0;
          } 
          }
      }
    }
       
           
            // Verschnellert die Gegner Bewegung nach bestimmter Zeit
      if(p1.returnscore()>=15){
        enemyBewegeZaehler= enemyBewegeZaehler+2.5;
      }
      else{
        enemyBewegeZaehler = enemyBewegeZaehler+1;
      }

      zeitvariable = zeitvariable + 50;
      
      //Gegnerschuss wird, falls gerade nicht aktiviert wieder neu gestartet
      //Gegnerprojektiel #1
      if(gP1.returnAktiviertStatus()==false){
      schießZurueck();
      gP1.setzeAktiviertStatusTrue();
      }
      
      //Gegnerprojektiel #2 wird zum Ausgleich der Schwierigkeit nur unter einem Score von 55 aufgerufen 
      if(p1.returnscore()<55){
        if(gP2.returnAktiviertStatus()==false){
          schießZurueck2();
          gP2.setzeAktiviertStatusTrue();
        }
      }
      //Prüfung auf Spielvorbei mit folgender Reaktion --> entweder weiter oder abbrechen 
      if(spielVorbeiPrüfen()==2){
        aktua();
        
        //GUI Aktualisierung
        t1.aktualisiereGUI();
      }
      else{
        gameOver();
       // musikStop();
        
        //GUI Aktualisierung
        t1.aktualisiereGUI();
        //Gebrauchte Zeit
        endZeit         = System.currentTimeMillis();
        berechneZeit();
        break;
        
      }
  }     
}//Ende Gameloop
  // Ende Methoden
  
 

  
  
  }// end of Spiellogik
