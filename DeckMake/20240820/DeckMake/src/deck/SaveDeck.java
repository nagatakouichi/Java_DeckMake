package deck;

import card.data.Card;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

public class SaveDeck {

    public static boolean save(Deck deck, String saveFileName){
        String textSaveFile = saveFileName + ".txt";
        try (FileWriter fw = new FileWriter(textSaveFile)) {
            for (Card card : deck.getCardList()) {
                fw.write(card.toString());
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("マナカーブ\r\n");
            fw.write(deck.getManaCurveText());

            fw.write("\r\n");
            fw.write("色バランス\r\n");
            fw.write(deck.getColorBalanceText());
        } catch (Exception e) {
            System.out.println("ファイル書き込み中に例外発生:" + e.getMessage());
            return false;
        }

        String objectSaveFile = saveFileName + ".dat";
        try (
                FileOutputStream fos = new FileOutputStream(objectSaveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(deck);
            oos.flush();
        } catch (Exception e) {
            System.out.println("セーブ中に例外発生:" + e.getMessage());
            return false;
        }
        return true;
    }
}
