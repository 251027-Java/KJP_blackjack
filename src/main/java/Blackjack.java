import java.util.*;

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

            boolean playerTurn = true;
            boolean playerBust = false;
            int sum = 0;

            // display the player's and dealer's current hand

            for (int i = 0; i < 2; i++) {
                int temp = getRandomCard();
                dealerHand.add(temp);

                temp = getRandomCard();
                playerHand.add(temp);
            }

            do {
                int temp;

                display(true);
                IO.println("1 - HIT   2 - STAND");

                int userIn = promptHitStand();

                switch (userIn) {
                    // hit
                    case 1:
                        playerHand.add(getRandomCard());

                        sum = handTotal(playerHand);

                        if (sum == -1) {
                            IO.println("BUSTED");
                            playerTurn = false;
                            playerBust = true;
                        }
                        break;
                    // stand
                    case 2:
                        playerTurn = false;
                        sum = handTotal(playerHand);
                        break;
                    // int out of scope
                    default:
                        IO.println("User input is not one of the options. Try again.");
                }
            } while (playerTurn);

            display(false);

            int dealerTotal = handTotal(dealerHand);

            IO.println("Dealer's turn! Their current total is " + dealerTotal + ".");
            if (sum >= 0) {
                while (dealerTotal <= DEALER_MAX_LIMIT && dealerTotal != -1) {
                    int card = getRandomCard();
                    dealerHand.add(card);
                    if (dealerHand.getLast() > 10) {
                        String temp = resolveCardIndex(dealerHand.getLast());
                        IO.println("Dealer got a: " + temp + ".");
                    }
                    else {
                        IO.println("Dealer got a: " + dealerHand.getLast() + ".");
                    }
                    dealerTotal = handTotal(dealerHand);
                }
            }
            IO.println("Dealer finished taking cards.\n");

            updateAndDisplayGameResult(sum, dealerTotal);

            dealerHand = new ArrayList<>();
            playerHand = new ArrayList<>();
        } while (promptPlayAgain());

        System.out.println("Goodbye \uD83D\uDC4B");
        in.close();
    }

    String resolveCardIndex(int index) {
        return switch (index) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> "" + index;
        };
    }

    String cardsToString(List<Integer> cards, boolean hideFirst) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < cards.size(); i++) {
            if (i == 0 && hideFirst) {
                res.append("* ");
            } else {
                res.append(resolveCardIndex(cards.get(i))).append(' ');
            }
        }

        return res.toString().trim();
    }

    void display(boolean hideDealer) {
        String dealerStr = cardsToString(dealerHand, hideDealer);
        String playerStr = cardsToString(playerHand, false);

        String res = String.format("""
                
                %1$10s 🎩️: %3$s
                %2$10s 🙂: %4$s
                %5$d card(s) in the current deck
                
                """, "DEALER", "YOU", dealerStr, playerStr, cardsInDeck);

        System.out.println(res);
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

    int promptHitStand() {
        boolean inpNull = false;
        int userIn = 0;
        while (!inpNull) {
            try {
                userIn = in.nextInt();

            } catch (InputMismatchException e) {
                System.err.println("Wrong input! Try again.");
                continue;
            } finally {
                in.nextLine();
            }

            inpNull = true;
        }

        return userIn;
    }

    boolean promptPlayAgain() {
        if (userBalance <= 0) {
            System.out.println("You have no balance remaining.");
            return false;
        }

        // default answer is "no" to not play again
        // only continue to play if and only if the user enters 'y' or 'Y'
        System.out.printf("Balance $%d | Would you like to play again? y/[N] ", userBalance);
        String ans = in.nextLine().trim();

        return ans.equalsIgnoreCase("y");
    }

    /*
    0 - N/A
    1 - Ace
    2-10 - 2-10
    11-13 - Jack, Queen, King
     */

    int getRandomCard() {
        if (cardsInDeck == 0) resetDeck();

        int card = rand.nextInt(cardsInDeck) + 1;
        int currCard = 0;

        while (card > 0) {
            currCard++;
            card -= deckCardCounts[currCard];
        }

        deckCardCounts[currCard]--;
        cardsInDeck--;

        return currCard;
    }

    void resetDeck() {
        Arrays.fill(deckCardCounts, DECK_INIT_COUNTS);
        cardsInDeck = 52 * NUM_DECKS;

        for (int i : dealerHand) {
            cardsInDeck--;
            deckCardCounts[i]--;
        }

        for (int i : playerHand) {
            cardsInDeck--;
            deckCardCounts[i]--;
        }
    }

    // return -1 if busted hand
    int handTotal(ArrayList<Integer> hand) {
        int aces = 0;
        int nonAcesTotal = 0;
        for (int i : hand) {
            if (i == 1) aces++;
            else nonAcesTotal += Math.min(10, i);
        }

        if (nonAcesTotal == 0 && aces == 2) return 21;

        int total = nonAcesTotal;
        if (aces > 0) {
            total += aces;

            if (total <= 11) aces += 10; //one of the aces has a value of 11 instead of 1
        }

        if (total > 21) return -1;
        return total;
    }

    void updateAndDisplayGameResult(int playerTotal, int dealerTotal) {
        if (playerTotal < 0) IO.println("Your hand was busted!");
        else IO.println("Your total: " + playerTotal);

        if (dealerTotal < 0) IO.println("The dealer's hand was busted!");
        else IO.println("Dealer total: " + dealerTotal);

        if (playerTotal < dealerTotal) {
            IO.println("You lost!");
            userBalance -= userBet;
        } else if (playerTotal > dealerTotal) {
            IO.println("You won!");
            userBalance += userBet;
        } else {
            IO.println("You tied!");
        }

        IO.println();
    }
}
