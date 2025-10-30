import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    static final int CARD_COUNT = 14;
    static final int DEALER_MAX_LIMIT = 16;
    static final int NUM_DECKS = 2;

    // two decks so 8 of each card
    static final int DECK_INIT_COUNTS = 4 * NUM_DECKS;

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

            int playerTotal = 0;
            int dealerTotal = handTotal(dealerHand);

            if (playerTotal >= 0) {
                while (dealerTotal <= DEALER_MAX_LIMIT) {
                    int card = getRandomCard();
                    dealerHand.add(card);
                    dealerTotal = handTotal(dealerHand);
                }
            }

            updateAndDisplayGameResult(playerTotal, dealerTotal);

            dealerHand = new ArrayList<>();
            playerHand = new ArrayList<>();
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
        if (cardsInDeck == 0) resetDeck();

        int card = rand.nextInt(cardsInDeck) + 1;
        int currCard = 0;

        while (card > 0){
            currCard++;
            card -= deckCardCounts[currCard];
        }
        deckCardCounts[currCard] -= 1;

        return card;
    }

    void resetDeck(){
        Arrays.fill(deckCardCounts, DECK_INIT_COUNTS);
        cardsInDeck = 52 * NUM_DECKS;

        for (int i : dealerHand){
            cardsInDeck--;
            deckCardCounts[i]--;
        }

        for (int i : playerHand){
            cardsInDeck--;
            deckCardCounts[i]--;
        }
    }

    // return -1 if busted hand
    int handTotal(ArrayList<Integer> hand){
        int aces = 0;
        int nonAcesTotal = 0;
        for (int i : hand){
            if (i == 1) aces++;
            else nonAcesTotal += Math.min(10, i);
        }

        if (nonAcesTotal == 0 && aces == 2) return 21;

        int total = nonAcesTotal;
        if (aces > 0){
            total += aces;

            if (total <= 11) aces += 10; //one of the aces has a value of 11 instead of 1
        }

        if (total > 21) return -1;
        return total;
    }

    void updateAndDisplayGameResult(int playerTotal, int dealerTotal){
        if (playerTotal < 0) IO.println("Your hand was busted!");
        else IO.println("Your total: " + playerTotal);

        if (dealerTotal < 0) IO.println("The dealer's hand was busted!");
        else IO.println("Dealer total: " + dealerTotal);

        if (playerTotal < dealerTotal) {
            IO.println("You lost!");
            userBalance -= userBet;
        }
        else if (playerTotal > dealerTotal) {
            IO.println("You won!");
            userBalance += userBet;
        }
        else {
            IO.println("You tied!");
        }

        IO.println();
    }
}
