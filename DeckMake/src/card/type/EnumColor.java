package card.type;

public enum EnumColor {
    ZERO("ゼロ"),
    FIRE("火"),
    NATURE("自然"),
    WATER("水"),
    DARK("闇"),
    LIGHT("光"),;

    private String japanese;
    private EnumColor(String japanese) {
        this.japanese = japanese;
    }

    public String getJapanese() {
        return japanese;
    }
}
