import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardArt {
    static void main() {
        new CardArt().run();
    }

    private static final String[] cards = {
            """
            ╔═══════╗
            ║   ?   ║
            ║       ║
            ║  ???  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   A   ║
            ║       ║
            ║  Ace  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   2   ║
            ║       ║
            ║  Two  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   3   ║
            ║       ║
            ║ Three ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   4   ║
            ║       ║
            ║ Four  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   5   ║
            ║       ║
            ║ Five  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   6   ║
            ║       ║
            ║  Six  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   7   ║
            ║       ║
            ║ Seven ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   8   ║
            ║       ║
            ║ Eight ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   9   ║
            ║       ║
            ║ Nine  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║  10   ║
            ║       ║
            ║  Ten  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   J   ║
            ║       ║
            ║ Jack  ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   Q   ║
            ║       ║
            ║ Queen ║
            ╚═══════╝
            """,
            """
            ╔═══════╗
            ║   K   ║
            ║       ║
            ║ King  ║
            ╚═══════╝
            """,
    };

    String resolveCardIndex(int index) {
        if (index >= 1 && index < cards.length) {
            return cards[index];
        }
        return cards[0];
    }

    String cardsToString(List<Integer> cards) {
        int lineCount = 5;

        StringBuilder[] lines = new StringBuilder[lineCount];
        for (int i = 0; i < lineCount; i++) lines[i] = new StringBuilder();

        for (int index : cards) {
            String card = resolveCardIndex(index);
            String[] cardLines = card.split("\n");

            for (int i = 0; i < cardLines.length; i++) {
                lines[i].append(cardLines[i]).append("   ");
            }
        }

        return Arrays.stream(lines).map(e -> e.toString().trim()).collect(Collectors.joining("\n"));
    }

    void run() {
       var s = cardsToString(Arrays.asList(1, 2));
        System.out.println(s);
    }
}
