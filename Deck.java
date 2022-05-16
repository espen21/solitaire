import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import java.util.Stack;

public abstract class Deck{
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle rec ;
    
    Stack <Card> cards = new Stack<>();
    public abstract void addDeckCard(Card card);
    public abstract boolean  canTakeCard(Card card);

    
    public void setX(int newX){
        x = newX;
    }
    
    public void setY(int newY){
        y = newY;
    }
    

    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setRec(){
        rec= new Rectangle(x,y,width,height);
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public Stack <Card> getCards(){
        return cards;
    }

    public Card getTopCard(){
        if(cards.empty())return null;
        return cards.peek();
    }
    public boolean isTopCardPressed(int clickX , int clickY){
        if(getTopCard() == null ){return rec.contains(clickX,clickY);}
        return getTopCard().clickCardcollsion(clickX, clickY);

    }
    public void flipTopCard(){
       
        if(getTopCard() == null) return;
        else if (getTopCard().getFaceUp() == false){
                getTopCard().setFaceUp(true);
        }
    }
    public boolean isRecPressed(Point pt){
        return rec.contains(pt);
    }
    
    public void draw(Graphics g ){
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        setRec();
        if(!cards.empty() ){
       
            for(Card card: cards){
                card.drawCard(g);
            }

        }
        
    }
}
