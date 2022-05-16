public class Tableau extends Deck{
    Tableau(int newX,int newY){
        setX(newX);
        setY(newY);
        setHeight(95);
        setWidth(72);
    }
    public void addDeckCard(Card card){
        card.setX(getX());
        int i = getCards().size() ;
        

        card.setY(getY()+(15*i));
        cards.add(card);
        countfaceUp();
    }
    public boolean canTakeCard(Card card){
        if(card == null ) return false;
        if(getTopCard().getIsRed() != card.getIsRed() && getTopCard().getRank() == card.getRank()+1 ){
           
            return true;
        }
        return false;
    }

    
    public int countfaceUp(){
        int sum = 0;
        for(Card card:getCards()){
            if(card.getFaceUp()){
                sum +=1;
            }

        }
        if(sum ==1 ){
            getTopCard().setlocked(true);
        }
        return sum;
    }
}