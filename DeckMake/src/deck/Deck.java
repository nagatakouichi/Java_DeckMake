package deck;

import card.data.Card;
import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {
    private ArrayList<Card> cardList;

    public Deck() {
        this.cardList = new ArrayList<>();
    }

    public ArrayList<Card> getCardList(){
        return this.cardList;
    }

    public void add(Card card) {
        cardList.add(card);
    }
}
