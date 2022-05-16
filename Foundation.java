public class Foundation extends Deck {
    Foundation(int newX,int newY){
        setX(newX);
        setY(newY);
        setHeight(95);
        setWidth(72);
    }

public void addDeckCard(Card card){
    int xoff = 20;
    if(getCards().size() % 2 == 0){
        xoff = -20;
    }
    if(getCards().size() == 0 ) xoff=0;
    card.setX(getX()+xoff);
    //int i = getCards().size();
    card.setY(getY()) ;// +(15*i));
    cards.add(card);
   
    card.setlocked(true);

    //int o = countfaceUp();
    
    }
    public boolean isFull(){
        if(cards.size()==13) return true;
        return false;
    }

    public boolean canTakeCard(Card card){
        if(card != null) {

            if (getTopCard() == null  && card.getRank() == 1 ) {
                return true;           
            }
            else if (getTopCard() == null && card.getRank() != 1){
                
                return false;
            }
            else if(getTopCard().getSuit() == card.getSuit() && getTopCard().getRank() +1 == card.getRank() && card.CardOverlap(getTopCard())){
                return true;
            }

        }       
        return false;
        
    }



}