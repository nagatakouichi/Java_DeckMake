package card.type;

import java.util.ArrayList;

public enum CardType {
    Creature("クリーチャー"),
    Spell("呪文");

    private String japanese;
    private CardType(String japanese) {
        this.japanese = japanese;
    }

    public String getJapanese() {
        return japanese;
    }

    @Override
    public String toString() {
        return getJapanese();
    }
}
