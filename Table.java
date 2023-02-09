import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Table {
    /**
     * Total number of player. Use this variable whenever possible
     */
    private static final int NUM_OF_PLAYERS = 4;
    /**
     * Total number of cards used in this game. Use this variable whenever possible
     */
    private static final int TOTAL_NUMBER_OF_CARD = 104;
    /**
     * The four stacks of cards on the table.
     */
    private Card[][] stacks = new Card[4][6];
    /**
     * This number of cards of each stack on the table. For example, if the variable
     * stacks stores
     * -------------------------
     * | 0 | 10 13 14 -- -- -- |
     * | 1 | 12 45 -- -- -- -- |
     * | 2 | 51 55 67 77 88 90 |
     * | 3 | 42 -- -- -- -- -- |
     * -------------------------
     * <p>
     * stacksCount should be {3, 2, 6, 1}.
     * <p>
     * You are responsible to maintain the data consistency.
     */
    private int[] stacksCount = new int[4];
    /**
     * The array of players
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * Default constructor
     * <p>
     * In the constructor, you should perform the following tasks:
     * <p>
     * 1. Initialize cards for play. You should construct enough number of cards
     * to play. These cards should be unique (i.e., no two cards share the same
     * number). The value of card must be between 1 to 104. The number of bullHead
     * printed on each card can be referred to the rule.
     * <p>
     * 2. Initialize four player. The first player should be a human player, call
     * "Kevin". The other player should be a computer player. These computer player
     * should have the name "Computer #1", "Computer #2", "Computer #3".
     * <p>
     * 3. Deal randomly 10 cards to each player. A card can only be dealt to one
     * player. That is, no two players can have the same card.
     * <p>
     * 4. Deal a card on each stack. The card dealt on the stack should not be dealt
     * to any player. Card dealt on each stack should also be unique (no two stack
     * have the same card).
     */
    public Table() {
        // TODO
        for (int i = 0; i < stacks.length; i++) {// initializing stackCount to 0
            stacksCount[i] = 0;
        }
        for (int i = 0; i < stacks.length; i++) {// initializing stackCount to 1
            stacksCount[i]++;
        }
        Card[] x = new Card[TOTAL_NUMBER_OF_CARD]; // will store the cards from 1 to 104
        for (int i = 0; i < TOTAL_NUMBER_OF_CARD; i++) {// make an array of cards from 1 to 104
            x[i] = new Card(i + 1);
        }
        players[0] = new Player("Kevin"); //Creates Kevin
        for(int i = 1; i < 4; i++) {// creates the rest 3 computer players
            players[i] = new Player();
        }
        for (int i = 0; i < NUM_OF_PLAYERS; i++) { //3
            for (int j = 0; j < 10; j++) {// deals 10 cards to each player

                int a = ThreadLocalRandom.current().nextInt(0, (x.length));// random generation of number between 0 and 103
                players[i].dealCard(x[a]);// deals the card from array x of index between 0 and 103

                Card[] newArr = new Card[x.length - 1];


                for (int k = 0; k < a; k++) { // copies every card till the card chosen randomly and stored in b and also copies the cards from the next value card of b till the end value cards
                    newArr[k] = x[k];
                }
                for (int k = a; k < newArr.length; k++) {
                    newArr[k] = x[k + 1];
                }
                x = newArr;


            }


        }

        // the following code is to deals 4 cards to the stack
        for (int i = 0; i < NUM_OF_PLAYERS; i++) { //4

            int b = ThreadLocalRandom.current().nextInt(1, x.length);
            stacks[i][0] = x[b];

            Card[] newArr = new Card[x.length - 1];

            for (int k = 0; k < b; k++) {// copies every card till the card chosen randomly and stored in b and also copies the cards from the next value card of b till the end value cards
                newArr[k] = x[k];
            }
            for (int k = b; k < newArr.length; k++) {
                newArr[k] = x[k + 1];
            }
            x = newArr;

        }


    }

    /**
     * This method is to find the correct stack that a card should be added to
     * according to the rule. It should return the stack among which top-card of
     * that stack is the largest of those smaller than the card to be placed. (If
     * the rule sounds complicate to you, please refer to the game video.)
     * <p>
     * In case the card to be place is smaller than the top cards of all stacks,
     * return -1.
     *
     * @param card - the card to be placed
     * @return the index of stack (0,1,2,3) that the card should be place or -1 if
     * the card is smaller than all top cards
     */
    public int findStackToAdd(Card card) {
        // TODO

        int minimumDiffernece = 1000; /// Set this to such a high value because I use this only to compare and then later set it to differences
        int GreaterCard = 0; // keeps record of the number of cards that are greater than the card played
        int addStack = 0;// intializing addStack which will return us the index of the stack to add to

        for(int i = 0; i < NUM_OF_PLAYERS; i++){ // excludes the card that is greater than the card being played
            if(card.getNumber() < stacks[i][stacksCount[i] -1].getNumber()){
                GreaterCard++;
            }
            else{
                // the following code will choose the card with the smallest difference
                int difference = card.getNumber() - stacks[i][stacksCount[i] -1].getNumber();
                if(difference < minimumDiffernece){
                    minimumDiffernece = difference;
                    addStack = i;
                }
            }
        }
        if(GreaterCard == NUM_OF_PLAYERS){ // if the number of greater cards is equal to the number of players that is the card played is smaller than all other cards then return -1
            return -1;
        }
        else {
            return addStack; // return the index
        }








    }

    /**
     * To print the stacks on the table. Please refer to the demo program for the
     * format. Within each stack, the card should be printed in ascending order,
     * left to right. However, there is no requirement on the order of stack to
     * print.
     */
    public void print() {
        // TODO
        for (int i = 0; i < stacks.length; i++) {
            System.out.print("Stack " + i + ": ");
            for (int j = 0; j < stacksCount[i]; j++) {// prints the stacks 2D array
                System.out.print(stacks[i][j] + " ");
            }
            System.out.println();
        }
    }

    // method to deal 10 cards to 4 players and 4 cards to each stack

    /**
     * This method is the main logic of the game. You should create a loop for 10
     * times (running 10 rounds). In each round all players will need to play a
     * card. These cards will be placed to the stacks from small to large according
     * to the rule of the game.
     * <p>
     * In case a player plays a card smaller than all top cards, he will be
     * selecting one of the stack of cards and take them to his/her own score pile.
     * If the player is a human player, he will be promoted for selection. If the
     * player is a computer player, the computer player will select the "cheapest"
     * stack, i.e. the stack that has fewest bull heads. If there are more than
     * one stack having fewest bull heads, selecting any one of them.
     */
    public void runApp() {

        for (int turn = 0; turn < 10; turn++) {
            // print Table

            System.out.println("----------Table----------");
            print();
            System.out.println("-------------------------");
            // TODO

            Card x;// Used for ascending order arrangement of the statement place the card
            Player a;// Used for ascending order arrangement of the statement place the card
            Card[] c = new Card[NUM_OF_PLAYERS]; // to store the card played by the players
            Card[] copyArray;
            c[0] = players[0].playCard();
            System.out.println("Kevin: " + c[0]);// code to print out what card Kevin played
            for (int i = 1; i < NUM_OF_PLAYERS; i++) {// code to print out which card the computer player played
                c[i] = players[i].playCardRandomly();
                System.out.println(players[i].getName() + ": " + c[i]);
            }

            int[] sortPlayer = {0, 1, 2, 3};
            for (int i = 0; i < players.length; i++) { // code to arrange Place the card x(a) for players in ascending order
                for (int j = 0; j < players.length; j++) {
                    if (c[i].getNumber() < c[j].getNumber()) {
                        x = c[i];
                        c[i] = c[j];
                        c[j] = x;
                        int temp = sortPlayer[i];
                        sortPlayer[i] = sortPlayer[j];
                        sortPlayer[j] = temp;

                    }
                }


            }

            for (int i = 0; i < NUM_OF_PLAYERS; i++) {// Printing place the card statement
                System.out.println("Place the card " + c[i] + " for " + players[sortPlayer[i]].getName());
            }


            // The following code is the logic of the game for all the players

            for (int i = 0; i < NUM_OF_PLAYERS; i++) {// this part of code finds the stack to add and adds the card played by the players
                int z = findStackToAdd(c[i]);

//                System.out.println("Hello");
//                System.out.println(c[i].getNumber() +"  " + z);


                Card[] card; // card array to copy the cards in a stack for Kevin
                Card[] cardForComp; // card array to copy the cards in a stack for computers
                if (z >= 0) {// if the card Kevin plays is less than all the other cards in the pile
                    stacks[z][stacksCount[z]] = c[i];
                    stacksCount[z]++;
                } else { // if findStackToAdd return -1 then ask Kevin to choose a stack and move all the cards in the stack to pile and place the card played by Kevin in the zeroth index
                    if (sortPlayer[i] == 0) {
//
                        Scanner in = new Scanner(System.in);
                        System.out.println("Pick a stack to collect the stack of cards");
                        int index = in.nextInt();

                        while (index < 0 || index > 3) {// if user inputs stack no less than 0 or greater than 3 then program will keep on asking for input
                            System.out.println(" Pick a stack to collect the cards");
                            index = in.nextInt();
                        }


                        card = new Card[stacksCount[index]];// the card array stores all the cards of the stack chosen by Kevin
                        for (int j = 0; j < card.length; j++) {
                            card[j] = stacks[index][j];

                        }
                        stacks[index][0] = c[i]; // replaces the first card in the stack chosen with the card.
                        stacksCount[index] = 1;

                        players[0].moveToPile(card, stacksCount[index]); // moves all the cards in the chosen stack of Kevin to his pile.


                    } else {
                        // The following code is for computer to choose the index with the lowest number of Bull Head

                        int[] sumOfBullHeads = new int[NUM_OF_PLAYERS];
                        int index;
                        for (int y = 0; y < stacks.length; y++) {
                            for (int j = 0; j < stacksCount[y]; j++) {
                                sumOfBullHeads[y] += stacks[y][j].getBullHead();
                            }
                        }
                        if (sumOfBullHeads[0] <= sumOfBullHeads[1] && sumOfBullHeads[0] <= sumOfBullHeads[2] && sumOfBullHeads[0] <= sumOfBullHeads[3]) {
                            index = 0;
                        } else if (sumOfBullHeads[1] <= sumOfBullHeads[0] && sumOfBullHeads[1] <= sumOfBullHeads[2] && sumOfBullHeads[1] <= sumOfBullHeads[3]) {
                            index = 1;

                        } else if (sumOfBullHeads[2] <= sumOfBullHeads[0] && sumOfBullHeads[2] <= sumOfBullHeads[1] && sumOfBullHeads[3] <= sumOfBullHeads[2]) {
                            index = 2;

                        } else {
                            index = 3;
                        }


                            cardForComp = new Card[stacksCount[index]];

                            for (int j = 0; j < cardForComp.length; j++) {// used in movetopile for computer players
                                cardForComp[j] = stacks[index][j];

                            }
                            stacks[index][0] = c[i];
                            stacksCount[index] = 1;


                            players[i].moveToPile(cardForComp, stacksCount[index]);


                        }
                    }


                    for (int p = 0; p < NUM_OF_PLAYERS; p++) {
                        if (stacksCount[p] == 6) {// if 6 cards in a stack, place the 6th card as first card and move the rest to pile
                            copyArray = new Card[5];
                            for (int h = 0; h < 5; h++) {
                                for (int m = 0; m < 6; m++) {
                                    copyArray[h] = stacks[p][m];
                                }
                            }
                            stacks[p][0] = stacks[p][5];
                            stacksCount[p] = 1;
                            players[i].moveToPile(copyArray, 5);


                        }
                    }


                }


            }
            for (Player p : players) {
                System.out.println(p.getName() + " has a score of " + p.getScore());
                p.printPile();
            }
        }




        /**
         * Programme main. You should not change this.
         *
         * @param args - no use.
         */
        public static void main (String[] args){
            new Table().runApp();
        }

    }

