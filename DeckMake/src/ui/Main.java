package ui;

import card.data.Card;
import card.manager.CardManager;
import deck.Deck;
import deck.LoadDeck;
import deck.SaveDeck;
import ui.gui.TextGUIManager;
import ui.gui.YesNoGUIManager;

public class Main {
    public static void main(String[] args) {
        Deck deck = null;
        YesNoGUIManager yesNoGUIManager = new YesNoGUIManager();
        TextGUIManager textGUIManager = new TextGUIManager();

        //デッキデータの読み込み
        boolean isLoadContinue = yesNoGUIManager.yesNoGUI("デッキを読み込みますか？");
        while (isLoadContinue) {
            String loadFileName = textGUIManager.getTextFromGUI("<html>読み込むファイル名を入力してください<br>(.datは不要です)<html>");
            loadFileName = loadFileName + ".dat";
            deck = LoadDeck.load(loadFileName);

            if (deck != null) {
                isLoadContinue = false;
            } else {
                isLoadContinue = yesNoGUIManager.yesNoGUI("もう一度デッキを読み込みますか？");
            }
        }


        if (deck == null || deck.getCardNum() <= 0) {
            System.out.println("新規のデッキを作成します");
            deck = new Deck();
        } else {
            for (Card c : deck.getCardList()) {
                System.out.println(c.toString());
            }
            System.out.println("現在のデッキの枚数は" + deck.getCardNum() + "です。");
        }


        //カードの追加
        System.out.println("カードの追加を行います");
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("デッキに入れるカードの情報を入力してください");
            Card c = CardManager.createCard();
            System.out.println(c.getName() + "は何枚入れますか？");
            int num = new java.util.Scanner(System.in).nextInt();
            for (int i = 0; i < num; i++) {
                deck.add(c);
            }

            System.out.println("現在のデッキの枚数は" + deck.getCardNum() + "です。");
            isContinue = yesNoGUIManager.yesNoGUI("カードの追加を続けますか？");
        }

        //マナカーブの表示
        System.out.println("マナカーブを表示します");
        System.out.println(deck.getManaCurveText());

        //文明（色）バランスの表示
        System.out.println("文明バランスを表示します");
        System.out.println(deck.getColorBalanceText());

        //デッキの保存
        boolean isCompleteSave = false;
        while (!isCompleteSave) {
            String fileName = textGUIManager.getTextFromGUI(
                    "<html>デッキを保存するファイルの名前を入力してください<br>datファイルとtextファイルで保存されます<htm>");
            if (SaveDeck.save(deck, fileName)) {
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
    }
}