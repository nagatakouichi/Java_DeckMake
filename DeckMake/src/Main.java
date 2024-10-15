import card.data.Card;
import card.data.CardAndNum;
import deck.Deck;
import deck.DeckManager;
import deck.LoadDeck;
import deck.SaveDeck;
import ui.gui.CreateCardGUIManager;
import ui.gui.DeckDataGUIManager;
import ui.gui.TextGUIManager;
import ui.gui.YesNoGUIManager;

public class Main {
    public static void main(String[] args) {
        DeckManager deckManager = null;
        YesNoGUIManager yesNoGUIManager = new YesNoGUIManager();
        TextGUIManager textGUIManager = new TextGUIManager();
        DeckDataGUIManager deckDataGUIManager = new DeckDataGUIManager();
        CreateCardGUIManager createCardGUIManager = new CreateCardGUIManager();

        //デッキデータの読み込み
        boolean isLoadContinue = yesNoGUIManager.yesNoGUI("デッキを読み込みますか？");
        while (isLoadContinue) {
            String loadFileName = textGUIManager.getTextFromGUI("<html>読み込むファイル名を入力してください<br>(.datは不要です)<html>");
            deckManager = new DeckManager(LoadDeck.load(loadFileName));

            if (deckManager.getDeck() != null) {
                isLoadContinue = false;
            } else {
                isLoadContinue = yesNoGUIManager.yesNoGUI("もう一度デッキを読み込みますか？");
            }
        }

        if (deckManager == null || deckManager.getDeck() == null || deckManager.getCardNum() <= 0) {
            deckManager = new DeckManager(new Deck());
        }

        deckDataGUIManager.update(deckManager.getDeck());

        //カードの追加
        boolean isContinue = true;
        while (isContinue) {
            CardAndNum cardAndNum = createCardGUIManager.createCard();
            Card card = cardAndNum.getCard();
            int num = cardAndNum.getNum();
            for (int i = 0; i < num; i++) {
                deckManager.add(card);
            }

            deckDataGUIManager.update(deckManager.getDeck());

            isContinue = yesNoGUIManager.yesNoGUI("カードの追加を続けますか？");
        }

        //マナカーブの表示
        System.out.println("マナカーブを表示します");
        System.out.println(deckManager.getManaCurveText());

        //文明（色）バランスの表示
        System.out.println("文明バランスを表示します");
        System.out.println(deckManager.getColorBalanceText());

        //デッキの保存
        boolean isCompleteSave = false;
        while (!isCompleteSave) {
            String fileName = textGUIManager.getTextFromGUI(
                    "<html>デッキを保存するファイルの名前を入力してください<br>datファイルとtextファイルで保存されます<htm>");
            if (SaveDeck.save(deckManager, fileName)) {
                System.out.println("セーブに成功しました");
                isCompleteSave = true;
            } else {
                System.out.println("セーブに失敗しました。");
                if (!yesNoGUIManager.yesNoGUI("もう一度セーブを試みますか？")) {
                    isCompleteSave = true;
                }
            }
        }
        System.out.println("終了");
        deckDataGUIManager.close();
    }
}