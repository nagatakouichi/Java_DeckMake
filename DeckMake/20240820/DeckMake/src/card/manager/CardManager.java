package card.manager;

import card.data.*;
import card.type.CardType;
import card.type.EnumColor;
import java.util.ArrayList;

public class CardManager {
    public static Card createCard() {
        boolean isCorrect = false;
        String name = null;
        int cost = -1;
        ArrayList<EnumColor> colors = null;
        CardType cardType = CardType.Creature;
        while (!isCorrect) {
            System.out.println("カードタイプを入力してください。「クリーチャー」->[0],「呪文」->[1]");
            int cardTypeNum = new java.util.Scanner(System.in).nextInt();
            switch (cardTypeNum) {
                case 0 -> cardType = CardType.Creature;
                case 1 -> cardType = CardType.Spell;
                default -> {
                    System.out.println("エラー：カードタイプが正しく指定されていません。");
                    continue;
                }
            }

            System.out.println("カード名を入力してください");
            name = new java.util.Scanner(System.in).nextLine();
            if (name.isEmpty()) {
                System.out.println("エラー：カード名が入力されていません。");
                continue;
            }

            System.out.println("コストを入力してください");
            cost = new java.util.Scanner(System.in).nextInt();
            if (cost < 0) {
                System.out.println("エラー：コストが0以下です");
                continue;
            }

            System.out.println("文明を入力してください。「ゼロ」->[0],「火」->[1],「自然」->[2],「水」->[3],「闇」->[4],「光」->[5]");
            System.out.println("複数文明の場合は「2,4」のように「,(カンマ)」で区切ってください。");
            String inputColors = new java.util.Scanner(System.in).nextLine();
            inputColors = inputColors.trim();
            var strColors = inputColors.split(",");
            colors = new ArrayList<>();
            try {
                for (String s : strColors) {
                    switch (Integer.parseInt(s)) {
                        case 0 -> colors.add(EnumColor.ZERO);
                        case 1 -> colors.add(EnumColor.FIRE);
                        case 2 -> colors.add(EnumColor.NATURE);
                        case 3 -> colors.add(EnumColor.WATER);
                        case 4 -> colors.add(EnumColor.DARK);
                        case 5 -> colors.add(EnumColor.LIGHT);
                        default -> throw new NumberFormatException("0~5以外の数値が入力されました。");
                    }
                }
                isCorrect = true;
            } catch (NumberFormatException ex) {
                System.out.println("エラー：文明が正しく入力されていません");
            }
        }

        Card c = null;
        switch (cardType) {
            case Creature -> {
                System.out.println("パワーを入力してください");
                int power = new java.util.Scanner(System.in).nextInt();
                c = new Creature(name, cost, colors, power);
            }
            case Spell -> c = new Spell(name, cost, colors);
        }
        return c;
    }
}
