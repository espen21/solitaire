import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

public class Card{
    private int x;
    private int y;
    private int oldx;
    private int oldy;

    private int width;
    private int height;
    private boolean faceup;
    private int rank;
    private boolean isRed;
    private String path;
    private boolean locked;
    private boolean inWasteTake ;
    private Rectangle rec;
    private char suit;
    ImageIcon CardImg;
    ImageIcon downImg = new ImageIcon("Cards/b1fv.gif");
    public Card(int startX,int startY,int initRank,char suit1,String path0){
        setHeight(downImg.getIconHeight());
        setWidth(downImg.getIconWidth());
        setX(startX);
        setY(startY);
        suit = suit1;
        oldx = x;
        oldy = y;
        setRank(initRank);
        path=path0;
        setCardImg();
        setRec();
        setFaceUp(false);
        setIsRed();

    }
    public char getSuit(){
        return suit;
    }
    public void  setCardImg(){
        CardImg = new ImageIcon(path);
    }
    public void setPath(String path0){
        path = path0;
    } 
    public ImageIcon getdownImg(){
        return downImg;
    }
    public ImageIcon getCardImg(){
        return CardImg;
    }
    public void setIsRed(){
        if(suit == 'c' || suit == 's'){
            isRed = false;
        }
        else{
            isRed = true;
        } 
    }
    public boolean getIsRed(){
        return isRed;
    }
    public boolean getlocked(){
        return locked;
    }
    public void setlocked(boolean lock){
        locked = lock;
    }
    public boolean getIsWasteTake(){
        return inWasteTake;
    }
    public void setIsWasteTake(boolean lock){
        inWasteTake = lock;
    }
    public void setX(int newX){
        x = newX;
    }
    
    public void setY(int newY){
        y = newY;
    }
    public void setoldX(int newX){
        oldx = newX;
    }
    public void setRec(){
        rec = new Rectangle(x, y, height, width);
    }
    public void setoldY(int newY){
        oldy = newY;
    }
    public int getoldX(){
        return oldx;
    }
    public int getoldY(){
        return oldy;
    }
  
    public void setWidth(int newWidth){
        width = newWidth;
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
    public void flipCard(){
        setFaceUp(!faceup);
    }

    public void  setRank(int newRank){
        rank = newRank;
    }
    public int getRank(){
        return rank;

    }
    
    public void setFaceUp(boolean newFaceUp){
        faceup = newFaceUp;
    }
    public boolean getFaceUp(){
        return faceup;
    }
    public void printCard(){
        System.out.printf("%d %s \n" ,getRank(),getSuit());

    }
    public boolean clickCardcollsion(int clickX,int clickY){
        
        if(clickX > getX() && clickX <(getX()+getWidth())&& clickY > getY() && clickY < (getY()+getHeight())){
            //setFaceUp(true);
            return true;
        }

        return false;
    }
    
    public  boolean CardOverlap(Card otherCard) { 
        if(otherCard == null ) return false;
        return rec.intersects(otherCard.rec);
    } 
    
    /*
    public boolean CardOverlap(Point pt){
        return rec.contains(pt);
    }
    */
    /*
    public void moveCardOverlap(Card otherCard){

        if(CardOverlap(otherCard) && getRank() !=1 && (otherCard.getSuit() != getSuit()|| otherCard.getRank() > getRank())){
            int newY = otherCard.getY();
            setY(newY+30);
            setX(otherCard.getX());
            oldx = otherCard.getX();
            oldy = newY;
        }
    }
    */
    public void drawCard(Graphics g){
        
        if(getFaceUp() && getCardImg() != null){
            g.drawImage(getCardImg().getImage(), getX(), getY(), null);

        }
        else{
            g.drawImage(getdownImg().getImage(), getX(), getY(), null);

        }

    } 
    public void moveCard(int mouseX,int mouseY,int mousePtX, int mousePtY ){
        if(getFaceUp() && !locked){
            int dx = mouseX - mousePtX;
            int dy = mouseY - mousePtY;
            int CardCurrentX = getX();
            int CardCurrentY = getY();
            
            setX(dx+CardCurrentX);
            setY(dy+CardCurrentY);
        }
        
    }
}