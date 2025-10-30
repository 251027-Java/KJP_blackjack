import java.util.Arrays;

public class Blackjack {
    static final int CARD_COUNT = 13;
    static final int DECK_INIT_COUNTS = 8;
    static final int DEALER_MAX_LIMIT = 17;


    public static void main(String[] args) {
        int[] deckCardCounts = new int[CARD_COUNT];
        Arrays.fill(deckCardCounts, DECK_INIT_COUNTS);

        int userBalance = 1000;

        /*
        prompt user bet

        game starts
        - dealer shows one card (has two)
        - user has two cards
            user has hit or stand options

            hit - they get a new card
            stand - dealer shows card

            dealer's turn - hit until 17
            when deck runs out of cards

        ace value - assume 11, if total > 21, value is 1
        blackjack has to be a one ace and another 10-ace

        TODO account for deck runs out of cards
         */
    }
}
