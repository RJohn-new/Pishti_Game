import java.util.ArrayList;

// Class for the pile of cards in the center of the game
public class Table {
    int tableScore = 0;
    private ArrayList<Integer> tablePile;
    public Table(ArrayList<Integer> givenCards) {
        tablePile = givenCards;
    }

    public ArrayList<Integer> getTablePile() {
        return tablePile;
    }

    public Integer calcTablePoints() {

        if (tablePile.size()==2){
            if(tablePile.get(tablePile.size()-1)%13 == 11 && tablePile.get(tablePile.size()-2)%13 == 11){

                tableScore = tableScore+20;
            } else if (tablePile.get(tablePile.size()-1)%13  == tablePile.get(tablePile.size()-2)%13 ){

                tableScore = tableScore+10;
            }
        }

        int cardCount = 0;
        while (cardCount < tablePile.size()){
            if(tablePile.get(cardCount) == 36){
                tableScore = tableScore+3;
            }else if(tablePile.get(cardCount) == 41){
                tableScore = tableScore+2;
            }else if((tablePile.get(cardCount))%13 == 1|| (tablePile.get(cardCount))%13 == 10||
                    (tablePile.get(cardCount))%13 == 11||(tablePile.get(cardCount))%13 == 12||(tablePile.get(cardCount))%13 ==0){
                tableScore++;
            }
            cardCount++;
        }

        return tableScore;
    }

    public boolean win() {
        if(tablePile.size() > 1) {

            if (tablePile.get(tablePile.size() - 1) % 13 == tablePile.get(tablePile.size() - 2) % 13 || tablePile.get(tablePile.size() - 1) % 13 == 11) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public void addCardToTable(Integer newCard) {
        tablePile.add(newCard);
    }
}
