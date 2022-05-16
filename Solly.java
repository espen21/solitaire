import javax.swing.*;



import java.io.File;


import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Daniel Espefält
//danne_190@hotmail.com
//java version "13.0.2" 2020-01-14
// kompilera javac .\Solly.java
//köra java Solly

class Ritpanel extends JPanel implements MouseListener ,MouseMotionListener  {
    Stack <Card> CardList = new Stack<>();

    private boolean isMousePressed;
    private int currentMouseX;
    private int currentMouseY;
    private Point mousePt;
    private Card currentCard;
    private boolean gameOver = false;
    private boolean gamerunning = false;

    public ArrayList <Foundation> Foundationlist ;
    public Wastepile wastepile;
    public Takepile takepile;
    public ArrayList <Tableau> Tableaulist;
    Deck oldDeck;
    
    public Ritpanel () {
        isMousePressed = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(800,700));       
    }

    public int getMouseX(){
        return currentMouseX;
    }
    public int getMouseY(){
        return currentMouseY;
    }
    public void setisMousePressed(boolean mousePressed){
        isMousePressed = mousePressed;
    }
    public boolean getisMousePressed(){
        return isMousePressed;
    }
    public void addCard (Card Card){
        CardList.add(Card);

        Graphics g = getGraphics();
        Card.drawCard(g);
    }


    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        if(gamerunning ){
            for(Tableau deck: Tableaulist){
                deck.draw(g);
            }
            for(Foundation deck: Foundationlist){
                deck.draw(g);
            }
            if(wastepile != null) wastepile.draw(g);
            if (takepile != null) takepile.draw(g);
            if(currentCard != null)currentCard.drawCard(g);
        }
    }
    
    public void setCurrentCard(Card o){
        currentCard = o;
    }

    public void mouseClicked (MouseEvent e) {
       if(gamerunning){
        mousePt = e.getPoint();
        if(takepile.getCards().empty()){
            
            for(Card card : wastepile.getCards()){

                card.flipCard();

                takepile.addDeckCard(card);
                
                setCurrentCard(takepile.getTopCard());
            }
            wastepile.getCards().removeAllElements();
        }
        else {
            if(!takepile.getTopCard().getFaceUp() && takepile.getTopCard().clickCardcollsion(mousePt.x, mousePt.y)){
                takepile.getTopCard().setlocked(false);
                takepile.getTopCard().flipCard();
               setCurrentCard(takepile.getTopCard());

            }
            else if(takepile.getTopCard().clickCardcollsion(mousePt.x, mousePt.y)){
                takepile.getTopCard().flipCard();
                wastepile.addDeckCard(takepile.getCards().pop());
                wastepile.getTopCard().flipCard();
               setCurrentCard(takepile.getTopCard());

            }
        }
       
        repaint();
    }
    }
    
    
    public void mouseMoved (MouseEvent e){}

    public void mouseEntered (MouseEvent e) { }
    public void mouseExited (MouseEvent e) { }
    
    public void mousePressed (MouseEvent e) {
        mousePt = e.getPoint();
        if(gamerunning){
 
        for(Tableau deck: Tableaulist){
            if(deck.isTopCardPressed(mousePt.x,mousePt.y)){
                setisMousePressed(true);
                setCurrentCard(deck.getTopCard());
                
       

                deck.getCards().remove(currentCard);
                oldDeck = deck;
                currentCard.setoldX(currentCard.getX());
                currentCard.setoldY(currentCard.getY());
          

                break;
            }

        }

   
        
        if(takepile.isTopCardPressed(mousePt.x,mousePt.y)){
            if(currentCard != null){
                setisMousePressed(true);
                setCurrentCard(takepile.getTopCard());
            
        
                takepile.getCards().remove(currentCard);
                oldDeck = takepile;
                currentCard.setoldX(currentCard.getX());
                currentCard.setoldY(currentCard.getY());
            

              
                }
      
            }
            repaint();
        }
      
    }
    

    public void mouseReleased (MouseEvent e) { 
        mousePt = e.getPoint();
        if(gamerunning){
            boolean checkCard = false;
            
            for(Tableau deck : Tableaulist){
      
            if(deck.isTopCardPressed(mousePt.x, mousePt.y)){
                if(deck.equals(oldDeck)) continue;
                if (deck.canTakeCard(currentCard) && currentCard.CardOverlap(deck.getTopCard())){
                    
                    oldDeck.getCards().remove(currentCard);
                    deck.getCards().push(currentCard);
                    oldDeck.flipTopCard();
                    checkCard = true;
                    currentCard = null;
                    break;
                }
            
            }
        }
            for(Foundation foundation : Foundationlist){
            if(currentCard == null)continue;
            if(foundation.isTopCardPressed(mousePt.x, mousePt.y)){
                if ((foundation.canTakeCard(currentCard) )){
                    
                    oldDeck.getCards().remove(currentCard);
                    oldDeck.flipTopCard();
                    foundation.addDeckCard(currentCard);
                    checkCard = true;
                    break;

                }
        
                }
            }
            if(oldDeck != null){
                if(oldDeck.equals(takepile) && !checkCard && currentCard != null && isMousePressed ){
                    wastepile.addDeckCard(currentCard);
                    checkCard = false;
                    currentCard = null;

                }

                else if(!checkCard && currentCard != null && isMousePressed ){
                    currentCard.setX(currentCard.getoldX());
                    currentCard.setY(currentCard.getoldY());
                
                    oldDeck.getCards().push(currentCard);
                    checkCard = false;
                    currentCard = null;

                }
            }
            setisMousePressed(false);
            checkWin();

            repaint();
        }
        
    }
    public void checkWin(){
        for(Foundation fond : Foundationlist){
  
            if(!fond.isFull()){
                gameOver = false;
                break;
            }
            gameOver = true;
            
        }
        setisMousePressed(false);
        if(gameOver )JOptionPane.showMessageDialog(null, "Du har vunnit!", "InfoBox: " + "Du har vunnit!", JOptionPane.INFORMATION_MESSAGE);
        
    }

    public void instaWin(){
        resetlists();
        initGame(true);
            if(takepile != null ){
            takepile.getCards().removeAllElements();
            for(Tableau tab : Tableaulist){
                tab.getCards().removeAllElements();
            }
            int i = 0;
            for(Card card : CardList){
                card.setFaceUp(true);
            
                if(card.getRank() == 1 && i <4){

                    Foundationlist.get(i).addDeckCard(card);
                    
                    i+=1;
                }
            }
            for(Card card: CardList){
            
                for(Foundation fond : Foundationlist){
                    if(fond.canTakeCard(card)){
                        fond.addDeckCard(card);
                    }
                }
            }
            for (int o = CardList.size(); o-- > 0; ) {
                for(Foundation fond : Foundationlist){
                    if(fond.canTakeCard(CardList.get(o))){
                        fond.addDeckCard(CardList.get(o));
                    }
                
                }
            }
            for(Card card: CardList){
                
                for(Foundation fond : Foundationlist){
                    if(fond.canTakeCard(card)){
                        fond.addDeckCard(card);
                    }
                }

            }
            for(Card card: CardList){
                if(card.getRank() == 13){
                    if(card.getSuit() == 'c') Foundationlist.get(0).addDeckCard(card);
                    if(card.getSuit() == 'd') Foundationlist.get(1).addDeckCard(card);
                    if(card.getSuit() == 'h') Foundationlist.get(2).addDeckCard(card);
                    if(card.getSuit() == 's') Foundationlist.get(3).addDeckCard(card);
                
                }
            }
        }
        repaint();

        checkWin();

    }
    public void mouseDragged(MouseEvent e){
        boolean pressed = getisMousePressed();
        if(pressed){
            
            currentCard.moveCard(e.getX(), e.getY(), mousePt.x, mousePt.y);
            currentCard.setX(e.getX());
            currentCard.setY(e.getY());


            
            mousePt = e.getPoint();
        
            repaint();
        }
    }           
                
   public void resetlists(){
       CardList.removeAllElements();
    
       currentCard = null;
       if(wastepile != null) wastepile.getCards().removeAllElements();
       if( takepile  != null) takepile.getCards().removeAllElements();
       if(Tableaulist != null ){
            for(Tableau tab : Tableaulist){
                if( tab.getTopCard() !=  null) {
                tab.getCards().remove(tab.getTopCard());
                tab.getCards().removeAllElements();
                }    
            }
        }
    
        if(Foundationlist != null ){
            for(Foundation fond : Foundationlist) {
                if(fond.getTopCard() != null){
                    fond.getCards().remove(fond.getTopCard());
                    fond.getCards().removeAllElements();
                } 

             }
     }
     repaint();
     
   }


    public void initGame (boolean fixed ){
        File dir = new File("cards");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                char suit = child.getName().charAt(0);
                char rank = child.getName().charAt(1);
                String cardPath = child.getName();
                if(suit == 'c' || suit == 'd' || suit == 's' || suit == 'h'){
                    int realrank = Character.getNumericValue(rank);
              
                    if(rank == 't'){
                        realrank = 10;
                    }
                    else if(rank == 'j'){
                        realrank = 11;
                    }
                    else if(rank == 'q'){
                        realrank = 12;

                    }
                    else if(rank == 'k'){
                      realrank = 13;
                    }
                    Card Card = new Card (50,100,realrank,suit,"cards/"+cardPath);
                   
                    addCard(Card);
                }

            }  
            if(!fixed){
                Collections.shuffle(CardList);
                
            }
            
          } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
            System.out.println("something did not work with the cards dir");

        }
        Foundationlist =   new ArrayList<Foundation>();
        Tableaulist =  new ArrayList<Tableau>();
         takepile = new Takepile(800, 50);
         wastepile = new Wastepile(900, 50);
        int x_offset_Foundtion = 0;
        for(int i = 0; i<=3;i++){
            Foundationlist.add(new Foundation(50+x_offset_Foundtion, 50));
            x_offset_Foundtion += 120;
        }

        int x_offset_tableu = 0;
        for (int i = 0 ; i <= 6 ; i++){
            Tableaulist.add(new Tableau(60+x_offset_tableu ,300));
            x_offset_tableu += 100;
        }
        int index=0;
        for(Card card : CardList){
            if(index ==1 ){
                Tableaulist.get(0).addDeckCard(card);
                Tableaulist.get(0).getTopCard().setFaceUp(true);
            }
            
            else if(index <=2){
               
                    Tableaulist.get(1).addDeckCard(card);

                
 
            }
            else if(index <=5){
                
                    Tableaulist.get(2).addDeckCard(card);

               

            }
            else if(index <=9){
                
                    Tableaulist.get(3).addDeckCard(card);


 
            }
            else if(index <=14){
                    Tableaulist.get(4).addDeckCard(card);


            }
            else if(index <=20){
                Tableaulist.get(5).addDeckCard(card);


            }
            
            else if(index <=27){
                
                    Tableaulist.get(6).addDeckCard(card);

                

            }
            
            
            if(index >28)
            {
                takepile.addDeckCard(card);
                card.setIsWasteTake(true);
            }
            index +=1;

        }
        Graphics g = getGraphics(); 
        for(Foundation fond : Foundationlist){
            fond.draw(g);
        }
        Tableaulist.get(1).getTopCard().setFaceUp(true);

        Tableaulist.get(2).getTopCard().setFaceUp(true);
        Tableaulist.get(3).getTopCard().setFaceUp(true);
        Tableaulist.get(4).getTopCard().setFaceUp(true);
        Tableaulist.get(5).getTopCard().setFaceUp(true);
        Tableaulist.get(6).getTopCard().setFaceUp(true);
        
        gamerunning = true;
        repaint();

       
    }
}


class Solly extends JFrame {
    JButton newBtn, fixBtn, exitBtn, instaWinBtn;

    public Solly (){
 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    newBtn = new JButton("New");
    fixBtn = new JButton("Fixed");
    exitBtn = new JButton("Exit");
    instaWinBtn = new JButton("Insta win");

	Ritpanel ritpanel = new Ritpanel();
    ritpanel.add(newBtn);
    ritpanel.add(fixBtn);
    ritpanel.add(exitBtn);
    ritpanel.add(instaWinBtn);

    add(ritpanel);
    newBtn.setVisible(true);
    fixBtn.setVisible(true);
    exitBtn.setVisible(true);
    instaWinBtn.setVisible(true);
	setVisible(true);
    
    newBtn.addActionListener(e-> {
        ritpanel.resetlists();
        ritpanel.initGame(false);
    });
    fixBtn.addActionListener(e-> {
        ritpanel.resetlists();

        ritpanel.initGame(true);
    });
    instaWinBtn.addActionListener(e-> {
        ritpanel.instaWin();
    });
    exitBtn.addActionListener(e-> {
        System.exit(0);
    });

    }
      public static void main (String[] arg) {

    JFrame solly = new Solly();
    solly.setSize(1200,800);
    }
}
