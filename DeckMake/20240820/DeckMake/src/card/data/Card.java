package card.data;

import card.type.CardType;
import card.type.EnumColor;

import java.io.Serializable;
import java.util.*;

public abstract class Card implements Serializable {
    private String name;
    private int cost;
    private ArrayList<EnumColor> colors;
    private CardType cardType;

    final String FORMAT ="コスト:%-2d %-15s";

    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public ArrayList<EnumColor> getColors() {
        return colors;
    }

    public Card(String name, int cost, ArrayList<EnumColor> colors, CardType cardType) {
        this.name = name;
        this.cost = cost;
        this.colors = colors;
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        String s = String.format(FORMAT, cost, name);
        String colorstr = "";
        for (EnumColor c : EnumColor.values()) {
            if (this.colors.contains(c)) {
                colorstr = colorstr + c.getJapanese();
            } else {
                colorstr = colorstr + "　".repeat(c.getJapanese().length());
            }
            colorstr = colorstr + "　";
        }
        return colorstr + s;
    }
}
