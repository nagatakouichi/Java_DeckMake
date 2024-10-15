package deck;

import card.data.Card;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

public class SaveDeck {

    public static boolean save(DeckManager deckManager, String saveFileName){
        String textSaveFile = saveFileName + ".txt";
        try (FileWriter fw = new FileWriter(textSaveFile)) {
            for (Card card : deckManager.getCardList()) {
                fw.write(card.toString());
                fw.write("\r\n");
            }
            fw.write("\r\n");
            fw.write("マナカーブ\r\n");
            fw.write(deckManager.getManaCurveText());

            fw.write("\r\n");
            fw.write("文明バランス\r\n");
            fw.write(deckManager.getColorBalanceText());
        } catch (Exception e) {
            System.out.println("ファイル書き込み中に例外発生:" + e.getMessage());
            return false;
        }

        String objectSaveFile = saveFileName + ".dat";
        try (
                FileOutputStream fos = new FileOutputStream(objectSaveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(deckManager.getDeck());
            oos.flush();
        } catch (Exception e) {
            System.out.println("セーブ中に例外発生:" + e.getMessage());
            return false;
        }
        return true;
    }
}
