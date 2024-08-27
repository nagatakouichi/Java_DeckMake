package deck;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadDeck {
    public static Deck load(String fileName){
        Deck deck = null;
        try (
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);){
            Object obj = ois.readObject();
            if (obj instanceof Deck loadDeck) {
                deck = loadDeck;
            }
        } catch (Exception e) {
            System.out.println("デッキ読み込み中にエラー発生:" + e.getMessage());
            return null;
        }
        return deck;
    }
}
