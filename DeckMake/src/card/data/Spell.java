package card.data;

import card.type.CardType;
import card.type.EnumColor;
import java.util.ArrayList;

public class Spell extends Card {
    public Spell(String name, int cost, ArrayList<EnumColor> colors) {
        super(name, cost, colors, CardType.Spell);
    }
}
