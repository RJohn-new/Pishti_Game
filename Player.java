import java.util.ArrayList;

// Class for player and the computer player
public class Player {
    private ArrayList<Integer> playerDeck;
    private int count = 0;
    private int score = 0;

    public Player(ArrayList<Integer> playerDeck) {
        setPlayerDeck(playerDeck);
    }

    public void setPlayerDeck(ArrayList<Integer> givenDeck) {
        playerDeck = givenDeck;
    }

    public void setNumOfCardsInPile(Integer numOfCards) {
        count = count + numOfCards;
    }

    public void setScore(Integer score) {
        this.score = this.score+score;
    }

    public int getScore() {
        return score;
    }

    public int getNumofCards() {
        return count;
    }

    public ArrayList<Integer> getPlayerDeck() {
        return playerDeck;
    }

    public Integer playCard(int selectedCard) {
            int cardNumber = this.getPlayerDeck().get(selectedCard);
            this.getPlayerDeck().remove(selectedCard);
            return cardNumber;
    }
}