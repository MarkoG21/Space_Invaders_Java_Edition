import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;



public class GUI extends JFrame implements KeyListener{
	
// Anfang Attribute
private 	boolean 		link 					= false;
private 	boolean 		recht 					= false;
private 	boolean 		schuss 					= false;
private 	JPanel 			contentPane;
private		int 			b;
private		int 			h;
private		int 			xa;
private		int 			ya;
private		JPanel[][] 		Feld;
private		JLabel[][] 		inhalte;
private 	char[][]		spielfeld;
private 	Spiellogik		gameBrain;
private 	JPanel			spielZeitAusgabePanel;
private 	JLabel			spielZeitAusgabeLabel;
private 	JPanel			gameOverAusgabePanel;
private		JLabel			gameOverAusgabeLabel;
private     ImageIcon		dead; 
private		ImageIcon		barrier;
private		ImageIcon		enemyshoot;
private		ImageIcon	    ally;
// Ende Attribute


//Main
public static void main(String[] args) {
	try {
		GUI GUI = new GUI("Space Invaders");
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
//Main Ende

// Konstruktor Keylintener
public GUI(String title) throws InterruptedException {
   
	// Frame-Initialisierung
	super(title);
	//Abfragen der Bildschirmauflösung
	Dimension screenSize 	= Toolkit.getDefaultToolkit().getScreenSize();
	
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { System.exit(0); }
    });
    int frameWidth 	= 1090;
    int frameHeight = 730; 
    setSize(frameWidth, frameHeight);
    int x = (screenSize.width - getSize().width) / 2;
    int y = (screenSize.height - getSize().height) / 2;
    setLocation(x, y);
    
    b  = 35;
    h  =  b;
    ya = -b;
    
    setResizable(false);
    setVisible(true);
    this.addKeyListener(this);   //hinzufügen des KeyListeners. Der GameFrame ist selbst der KeyListener
    
    //Setzen des Content Pane
    contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);
    
	//Spawnen der Felder
	
    Feld = new JPanel [18][31];
	this.setLayout(null);
	for(int op = 0; op < 18; op++){
		ya = ya+h;
		xa	= -b;
		for(int up = 0; up < 31; up++){
			xa += +b;
			Feld [op][up]  = new JPanel();
			Feld[op][up].setBounds(xa, ya, b, h);
			Feld[op][up].setBackground(Color.BLACK);
			Feld[op][up].repaint();
			Feld[op][up].setVisible(true);
			this.add(Feld[op][up]);
			this.repaint();
		}	
	}
	
	//Panel für Ausgabe der Spielzeit
	spielZeitAusgabePanel = new JPanel();
	spielZeitAusgabePanel.setBounds(0, (h*18), frameWidth, h);
	spielZeitAusgabePanel.setBackground(Color.gray);
	spielZeitAusgabePanel.repaint();
	spielZeitAusgabePanel.setVisible(true);
	spielZeitAusgabePanel.revalidate();
	this.add(spielZeitAusgabePanel);
	
	//Panel für Ausgabe des Game Overs mit Begründung
	gameOverAusgabePanel = new JPanel();
	gameOverAusgabePanel.setBounds(0, ((h*18)+h), frameWidth, h);
	gameOverAusgabePanel.setBackground(Color.gray);
	gameOverAusgabePanel.repaint();
	gameOverAusgabePanel.setVisible(true);
	gameOverAusgabePanel.revalidate();
	this.add(gameOverAusgabePanel);
	
	//Texturen setzen
	dead 	   = new ImageIcon(getClass().getResource("resources/dead.jpg"));
	barrier    = new ImageIcon(getClass().getResource("resources/barrier.jpg"));
	enemyshoot = new ImageIcon(getClass().getResource("resources/shoot.jpg"));
	ally 	   = new ImageIcon(getClass().getResource("resources/ally.jpg"));
	
	
	
	//Starten des Spiels
	starteSI();	
}//Ende Konstruktor
  
//Anfang Operationen

/*Methoden, die ständig lauschen, ob eine TAste gedrückt wird! */
/*****************************************************************/
public void keyTyped(KeyEvent ke) {
    
}                            
  
public void keyPressed(KeyEvent ke) {
    switch (ke.getKeyCode()) {
      case KeyEvent.VK_LEFT:
      link= true;
      break;
      case KeyEvent.VK_RIGHT: recht= true;
      break;
      case KeyEvent.VK_SPACE:
      schuss= true;
      break;
      default:;
    }
}
  
public void keyReleased(KeyEvent e) {
	    switch (e.getKeyCode()) {
	      case KeyEvent.VK_LEFT:
	      link= false;
	      break;
	      case KeyEvent.VK_RIGHT:
	      recht= false;
	      break;
	      case KeyEvent.VK_SPACE:
	      schuss= false;
	      break;
	      default:;
	    }
  
}
//Ende Tastenoperationen

//Return - Operationen für Tasten-booleans  
public boolean returnBooleanRecht(){
	  return recht;
}
  
public boolean returnBooleanLeft(){
	  return link;
}
  
public boolean returnBooleanLeer(){
	  return schuss;
}
//Ende Return - Operationen für Tasten-booleans 

//Operation zum starten des Spiels in der Spiellogik
private void starteSI() throws InterruptedException{
	gameBrain = new Spiellogik(this);
	gameBrain.starteSpiel();
}

//GUI Befüllung und Aktualisierung
//Setze Inhaltsboxen auf die GUI und befülle sie zum ersten mal
public void setzeInhalte(){
	
	inhalte = new JLabel [18][31]; 
		for(int op = 0; op < 18; op++){
			for(int up = 0; up < 31; up++){
				inhalte	[op][up] = new JLabel();
				inhalte	[op][up].setFont(new Font("Arial",1,20));
				inhalte	[op][up].setForeground(Color.ORANGE);
				inhalte	[op][up].setVisible(true);
				Feld	[op][up].add(inhalte[op][up]);
				inhalte	[op][up].revalidate();
				
			}	
		}
}

//Aktualisiert JLabels auf der GUI --> wird im GameLoop aufgerufen
public void aktualisiereGUI(){
	
	//Felder wieder von Farben usw. säubern und auf ihren Standart zurücksetzen
	for(int op = 0; op < 18; op++){
		for(int up = 0; up < 31; up++){
			Feld[op][up].setBackground(Color.BLACK);
			inhalte[op][up].setText("");
			inhalte[op][up].setIcon(null);
		}	
	}
	//Ende der Säuberung
	
	
	spielfeld = gameBrain.returneSpielfeld();
	for(int op = 0; op < 18; op++){
			for(int up = 0; up < 31; up++){
				//Je nach Zeichen im Spielfeld Array andere Ausgabe auf der GUI
				if(spielfeld[op][up]=='.' || spielfeld[op][up]=='X' || spielfeld[op][up]=='|' || spielfeld[op][up]=='#' || spielfeld[op][up]=='~' || spielfeld[op][up]=='§' || spielfeld[op][up]=='Y' || spielfeld[op][up]=='/'){
					if(spielfeld[op][up]=='.'){
					}
					if(spielfeld[op][up]=='X'){
						Feld[op][up].setBackground(Color.LIGHT_GRAY);
					}
					if(spielfeld[op][up]=='|'){
						inhalte[op][up].setIcon(ally);
					}
					if(spielfeld[op][up]=='#'){
						inhalte[op][up].setIcon(barrier);
					}
					if(spielfeld[op][up]=='§'){
						inhalte	[op][up].setForeground(Color.RED);
						inhalte	[op][up].setText("|°_°|");
					}
					if(spielfeld[op][up]=='Y'){
						inhalte[op][up].setIcon(enemyshoot);
					}
					if(spielfeld[op][up]=='/'){
						inhalte	[op][up].setText("");
					}
					if(spielfeld[op][up]=='~'){
						inhalte[op][up].setIcon(dead);
					}
				}
				else{
					inhalte[op][up].setText(""+spielfeld[op][up]);
				}
				inhalte	[op][up].repaint();
			}
	}
	contentPane.repaint();
}
//Ende GUI Befüllung und Aktualisierung

//Ausgeben der Spielzeit auf der GUI
public void spielzeitAusgeben(String ausgabe){
	spielZeitAusgabeLabel = new JLabel();
	spielZeitAusgabeLabel.setText(ausgabe);
	spielZeitAusgabeLabel.setFont(new Font("Arial",1,20));
	spielZeitAusgabeLabel.setForeground(Color.ORANGE);
	spielZeitAusgabeLabel.setVisible(true);
	spielZeitAusgabePanel.add(spielZeitAusgabeLabel);
	spielZeitAusgabeLabel.revalidate();
}

//Ausgeben des Game Overs auf der GUI
public void gameOverAusgeben(String ausgabe){
	gameOverAusgabeLabel = new JLabel();
	gameOverAusgabeLabel.setText(ausgabe);
	gameOverAusgabeLabel.setFont(new Font("Arial",1,20));
	gameOverAusgabeLabel.setForeground(Color.ORANGE);
	gameOverAusgabeLabel.setVisible(true);
	gameOverAusgabePanel.add(gameOverAusgabeLabel);
	gameOverAusgabeLabel.revalidate();
}
 // Ende Operationen
}//Ende Keylistener
