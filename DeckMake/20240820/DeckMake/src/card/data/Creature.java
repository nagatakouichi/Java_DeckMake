package card.data;

import card.type.CardType;
import card.type.EnumColor;
import java.util.ArrayList;

public class Creature extends Card {
    private int power;

    public int getPower() {
        return power;
    }

    public Creature(String name, int cost, ArrayList<EnumColor> colors, int power) {
        super(name, cost, colors, CardType.Creature);
        this.power = power;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s + " パワー:" + this.power;
    }
}
