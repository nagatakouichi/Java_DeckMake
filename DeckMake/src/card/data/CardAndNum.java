package card.data;

import java.io.Serializable;

public class CardAndNum implements Serializable {
    private Card card;
    private int num;

    public CardAndNum(Card card, int num) {
        this.card = card;
        this.num = num;
    }

    public Card getCard() {
        return card;
    }

    public int getNum() {
        return num;
    }
}
