package ui.gui;

import card.data.*;

import java.io.Serializable;

public class CreateCardGUIManager implements Serializable {
    private CardAndNum cardData;
    public CardAndNum createCard() {
        //GUI作成
        CreateCardWindow window = new CreateCardWindow(this);

        //待機 GUI
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("カード情報の入力待機中のエラー:" + e.getMessage());
            }
        }

        return this.cardData;
    }

    synchronized void setCardData(Card card, int num) {
        this.cardData = new CardAndNum(card, num);
        this.notifyAll();
    }
}
