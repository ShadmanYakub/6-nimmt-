import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    /**
     * The cards held on a player hand
     */
    private Card[] hand;
    /**
     * The number of card held by the player. This variable should be maintained
     * to match array hand.
     */
    private int handCount;
    /**
     * A dynamic array that holds the score pile.
     */
    private Card[] pile;
    /**
     * The name of the player
     */
    private String name;
    /**
     * A static variable that tells how many player has been initialized
     */
    private static int count = 1;

    /**
     * Other constructor that specify the name of the player.
     *
     * You need to initialize your data member properly.
     */
    public Player(String name) {
        // TODO
        this.name = name;
        handCount = 0;
        pile = new Card[0];// size of pile is initialized to zero

        hand = new Card[10]; // hand array size is initialized to 10;



    }

    /**
     * Default constructor that set the name of the player as "Computer #1",
     * "Computer #2", "Computer #3"...
     * The number grows when there are more computer players being created.
     *
     * You need to initialize your data member properly.
     */
    public Player() {
        // TODO
        this("Computer #" + count);
        count++;
        pile = new Card[0];  // size of pile is initialized to zero
        hand = new Card[10]; // hand array size is initialized to 10;

    }

    /**
     * Getter of name
     *
     * @return - the name of the player
     */
    public String getName() {
        // TODO
        return name;
    }

    /**
     * This method is called when a player is required to take the card from a stack
     * to his score pile. The variable pile should be treated as a dynamic array so
     * that the array will auto-resize to hold enough number of cards. The length of
     * pile should properly record the total number of cards taken by a player.
     *
     * Important: at the end of this method, you should also help removing all cards
     * from the parameter array "cards".
     *
     *
     *
     * @param cards - an array of cards taken from a stack
     * @param count - number of cards taken from the stack
     */
    public void moveToPile(Card[] cards, int count) {
        // TODO


        if(pile == null) { // if pile of a player has no cards then execute the following code
            Card[] anotherPile = new Card[count];
            for (int i = 0; i < count; i++) {
                anotherPile[i] = cards[i];
            }
            pile = anotherPile;
            for (int i = 0; i < cards.length; i++){
                cards[i] = null;
            }


        }
        else {
            Card[] anotherPile = new Card[count + pile.length];

            for (int i = 0; i < pile.length; i++) {// copy the existing cards in the pile to the anotherPile
                anotherPile[i] = pile[i];
            }
            for (int i = 0; i < count; i++) {// adds the card to the extra index.
                anotherPile[i + pile.length] = cards[i];
            }
            pile = anotherPile;
            for (int i = 0; i < cards.length; i++){
                cards[i] = null; // resets the cards argument to zero
            }
        }


    }


    /**
     * This method prompts a human player to play a card. It first print
     * all cards on his hand. Then the player will need to select a card
     * to play by entering the INDEX of the card.
     *
     * @return - the card to play
     */
    public Card playCard() {
        // TODO
        for(int i = 0; i < hand.length; i++) {
            System.out.println(i + ":" + hand[i]);
        }
        Scanner in = new Scanner(System.in);

        System.out.println("Kevin, please select a card to play: ");
        int index = in.nextInt();

        while(index < 0 || index >= hand.length){// if Kevin plays a card which is not the index it will keep on asking input unless correct input is given.
            System.out.println("Kevin, please select a card to play: ");
            index = in.nextInt();
        }
        Card card = hand[index];
        int storeNum = 0;                     // the following code stores the index of the card kevin plays
        for(int i = 0; i < hand.length; i++){
            if(getHandCard(index) == hand[i]) {
                storeNum = i;
            }


        }

        // the following code decreases the size of the hand array after kevin plays a card
        Card[] newArray = new Card[hand.length -1];
        for(int i = 0; i < storeNum; i++){
            newArray[i] = hand[i];

        }
        for(int i = storeNum; i < newArray.length; i++){
            newArray[i] = hand[i + 1];
        }
        hand = newArray;
        handCount--;

        return card;// returns the card kevin chose to play;
    }




    /**
     * This method let a computer player to play a card randomly. The computer
     * player will pick any available card to play in a random fashion.
     *
     * @return - card to play
     */
    public Card playCardRandomly() {
        // TODO
        int index = ThreadLocalRandom.current().nextInt(0, handCount);
        Card card = hand[index];
        int storeNum = 0;                     // the following code stores the index of the card computer plays
        for(int i = 0; i < hand.length; i++){
            if(getHandCard(index) == hand[i]) {
                storeNum = i;
            }


        }
        // the following code decreases the size of the hand array after kevin plays a card
        Card[] newArray = new Card[hand.length -1];
        for(int i = 0; i < storeNum; i++){
            newArray[i] = hand[i];

        }
        for(int i = storeNum; i < newArray.length; i++){
            newArray[i] = hand[i + 1];
        }
        handCount--;
        hand = newArray;
        return card;
    }



    /**
     * Deal a card to a player. This should add a card to the variable hand and
     * update the variable handCount. During this method, you do not need to resize
     * the array. You can assume that a player will be dealt with at most 10 cards.
     * That is, the method will only be called 10 times on a player.
     *
     * After each call of this method, the hand should be sorted properly according
     * to the number of the card.
     *
     * @param card - a card to be dealt
     */
    public void dealCard(Card card) {
        // TODO

        if(handCount == 0){ // the following code adds the card when handCount is zero or hand.length is zero
            Card[] array1 = new Card[1];
            array1[0] = card;
            handCount++;
            hand = array1;
        }


        else {
            Card[] array = new Card[++handCount];
            Card x;
            for(int i = 0; i < hand.length; i++) { // copies all the existing cards in hand to the newArray
                array[i] = hand[i];
            }

            array[handCount - 1] = card;// adds the card to the extra index

            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array.length; j++){
                    if(array[i].getNumber() < array[j].getNumber()) {
                        x = array[i];
                        array[i] = array[j];
                        array[j] = x;
                    }

                }
            }
            hand = array;
















        }







    }







    /**
     * Get the score of the player by counting the total number of Bull Head in the
     * score pile.
     *
     * @return - score, 0 or a positive integer
     */
    public int getScore() {
        // TODO
        int x = 0;
        if((pile.length>0) ){ // if there are cards in the pile then the following card is implemented
            for (int i = 0; i < pile.length; i++) {
                x += pile[i].getBullHead();
            }
        }

        return x;
    }

    /**
     * To print the score pile. This method has completed for you.
     *
     * You don't need to modify it and you should not modify it.
     */
    public void printPile() {
        for (Card c : pile) {
            c.print();
        }
        System.out.println();
    }

    /**
     * This is a getter of hand's card. This method has been completed for you
     *
     * You don't need to modify it and you should not modify it.
     *
     * @param index - the index of card to take
     * @return - the card from the hand or null
     */
    public Card getHandCard(int index) {
        if (index < 0 || index >= handCount)
            return null;
        return hand[index];
    }
}

