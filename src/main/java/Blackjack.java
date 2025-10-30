import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    static final int CARD_COUNT = 14;
    static final int DEALER_MAX_LIMIT = 16;
    static final int NUM_DECKS = 2;

    // two decks so 8 of each card
    static final int DECK_INIT_COUNTS = 8;

    int[] deckCardCounts;
    ArrayList<Integer> dealerHand;
    ArrayList<Integer> playerHand;
    int userBalance;
    int userBet;
    Scanner in;
    int cardsInDeck;
    Random rand;

    public static void main(String[] args) {
        new Blackjack().start();
    }

    public Blackjack() {
        deckCardCounts = new int[CARD_COUNT];
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
        userBalance = 1000;
        in = new Scanner(System.in);
        Arrays.fill(deckCardCounts, DECK_INIT_COUNTS);
        cardsInDeck = NUM_DECKS * 52;
        rand = new Random();
    }

    public void start() {
        /*
        prompt user bet

        game starts
        - dealer shows one card (has two)
        - user initially has two cards
            user has hit or stand options

            hit - they get a new card
            stand - dealer shows card
            ---
            dealer's turn - hit until 17
            when deck runs out of cards

        ace value - assume 11, if total > 21, value is 1
        blackjack has to be a one ace and another 10-ace

        - add back the cards to the deck that are currently not in
         either the dealers hand or player's hand

         random selection of cards - based on real life
         pick random card based on total of actual cards in cards
         */

        do {
            userBet = promptBet();
            userBalance -= userBet;

            // display the player's and dealer's current hand
            display();


        } while (promptPlayAgain());

        in.close();
    }

    void display() {
    }

    int promptBet() {
        int bet = -1;
        boolean done = false;

        while (!done) {
            try {
                System.out.printf("Balance $%d | Enter a bet: ", userBalance);
                bet = Integer.parseInt(in.nextLine());

                if (isValidBet(bet)) {
                    done = true;
                } else {
                    promptInvalidBet();
                }
            } catch (Exception e) {
                promptInvalidBet();
            }
        }

        return bet;
    }

    void promptInvalidBet() {
        System.out.println("🚫 Invalid bet. Try again.");
    }

    boolean isValidBet(int bet) {
        return bet >= 1 && bet <= userBalance;
    }

    boolean promptHitStand() {
        // return true if hit, false if stand
        // get user response
        // validate response
        // etc
        return false;
    }

    boolean promptPlayAgain() {
        return false;
    }

    /*
    0 - N/A
    1 - Ace
    2-10 - 2-10
    11-13 - Jack, Queen, King
     */
    int getRandomCard(){
        int card = rand.nextInt(cardsInDeck);
        return card;
    }
}
