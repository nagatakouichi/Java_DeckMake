package deck;

import card.data.Card;
import card.type.ColorNumType;
import card.type.EnumColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public int getCardNum() {
        return cardList.size();
    }

    public String getManaCurveText(){
        int[] manaList = new int [11];
        for (Card card : this.cardList) {
            int cost = card.getCost();
            if (cost >= 10) {
                manaList[10] = manaList[10] + 1;
            } else {
                manaList[cost] = manaList[cost] + 1;
            }
        }

        String format = "%3d%1s | %3d :";
        StringBuilder manaCurve = new StringBuilder();
        manaCurve.append("cost | num :\n");
        for (int i = 0; i < manaList.length; i++) {
            if (i >= 10) {
                manaCurve.append(String.format(format, i, "+", manaList[i]));
            } else {
                manaCurve.append(String.format(format, i, " ", manaList[i]));
            }
            manaCurve.append("■".repeat(Math.max(0, manaList[i])));
            manaCurve.append("\n");
        }

        return manaCurve.toString();
    }

    public Map<EnumColor, Map<ColorNumType, Integer>> getColorBalance() {
        Map<EnumColor, Map<ColorNumType, Integer>> colorBalance = new HashMap<>();
        for (EnumColor ec : EnumColor.values()) {
            colorBalance.put(ec, new HashMap<>());
            for (ColorNumType cnt : ColorNumType.values()) {
                colorBalance.get(ec).put(cnt, 0);
            }
        }
        for (Card card : this.cardList) {
            ColorNumType cnType = ColorNumType.SINGLE;
            if (card.getColors().size() > 1) {
                cnType = ColorNumType.RAINBOW;
            }
            for (EnumColor color : card.getColors()) {
                int n = colorBalance.get(color).get(cnType);
                colorBalance.get(color).put(cnType, n + 1);
            }
        }
        return colorBalance;
    }

    public String getColorBalanceText() {
        Map<EnumColor, Map<ColorNumType, Integer>> colorBalance = this.getColorBalance();
        StringBuilder text = new StringBuilder("文明 : 単色|多色|合計\n");
        String textFormat = " : %3d|%3d|%3d  ";
        for (EnumColor ec : EnumColor.values()) {
            text.append(ec.getJapanese());
            if (ec.getJapanese().length() == 1) {
                text.append("　");
            }
            int singleNum = colorBalance.get(ec).get(ColorNumType.SINGLE);
            int rainbowNum = colorBalance.get(ec).get(ColorNumType.RAINBOW);
            text.append(String.format(textFormat, singleNum, rainbowNum, singleNum + rainbowNum));

            for (int i = 0; i < singleNum; i++) {
                text.append("■");
            }
            for (int i = 0; i < rainbowNum; i++) {
                text.append("□");
            }
            text.append("\n");
        }

        return text.toString();
    }
}
