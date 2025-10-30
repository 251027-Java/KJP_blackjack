import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Random;


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

        boolean playerTurn = true;
        boolean playerBust = false;
        userBet = promptBet();

        for (int i = 0; i < 2; i++) {
            int temp = getRandomCard();
            deckCardCounts[temp]--;
            dealerHand.add(temp);

            temp =  getRandomCard();
            deckCardCounts[temp]--;
            playerHand.add(temp);
        }

        int userIn = in.nextInt();
        do {
            int temp;

            display();

            switch(userIn) {
                // hit
                case 1:
                    playerHand.add(temp = getRandomCard());

                    int sum = 0;
                    for (int i = 0; i < playerHand.size(); i++) sum += playerHand.get(i);

                    if (sum > 21) {
                        playerTurn = false;
                        playerBust = true;
                    }
                    break;
                // stand
                case 2:
                    playerTurn = false;
                    break;
                // int out of scope
                default:
                    IO.println("User input is not one of the options. Try again.");
            }
        } while (playerTurn);



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

            // display the player's and dealer's current hand
            display();


        } while (promptPlayAgain());

        in.close();
    }

    void display() {
    }

    int promptBet() {
        return 0;
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
