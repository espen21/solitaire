public class Takepile extends Deck {
    Takepile(int newX,int newY){
        setX(newX);
        setY(newY);
        setHeight(95);
        setWidth(72);
    }
    
    public void addDeckCard(Card card){
        card.setX(getX());
        card.setY(getY());
        cards.add(card);
        
        }
    public boolean canTakeCard(Card card){
            if(card == null ) return false;

            if (getCards().size() == 0  )return true;
            if(getTopCard().getSuit() == card.getSuit() && getTopCard().getRank() +1 == card.getRank()){
                return true;
            }
            return false;
            
        }
}