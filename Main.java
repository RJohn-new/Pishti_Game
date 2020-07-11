import java.util.ArrayList;

// Initialization for original game objects and some methods for simple game functions (play card, deal hand)
public class Main {
    public static Player player;
    public static Player computer;
    public static Table table;
    public static Deck deck;
    public static void main(String[] args) {
        // Initalize the deck and shuffle
        deck = new Deck();
        deck.shuffle();

        //Deal the deck to the player
        ArrayList<Integer> playerDeck = new ArrayList<>();
        deck.deal(playerDeck);
        player = new Player(playerDeck);

        //Deal the deck to the computer
        ArrayList<Integer> compDeck = new ArrayList<>();
        deck.deal(compDeck);
        computer = new Player(compDeck);

        //Deal the deck to table
        ArrayList<Integer> tableDeck = new ArrayList<>();
        table =new Table( DeckDeal(tableDeck));
    }

    public static void playCard(int i, Player player, ArrayList<Integer> playerDeck, Table table) {
        Integer cardPlayed = player.playCard(i);
        table.addCardToTable(cardPlayed);
    }

    public static void giveCards (ArrayList playerDeck,Deck deck, Player player){
            player.setPlayerDeck(deck.deal(playerDeck));
    }

    public static ArrayList<Integer> DeckDeal(ArrayList<Integer> tableDeck){
        if((deck.returnDeck().get(3))%13  == 11){
            deck.shuffle();
            DeckDeal(tableDeck);
        }else
            deck.deal(tableDeck);
        return tableDeck;
    }
}