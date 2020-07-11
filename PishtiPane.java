import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;
// Custom Pane for game board
public class PishtiPane extends BorderPane {
    StackPane pile = new StackPane();
    StackPane remainingDeck = new StackPane();
    HBox playerHand = new HBox(5);
    HBox compHand = new HBox(5);
    VBox otherSide = new VBox(5);

    private Text deckSize = new Text(Integer.toString(Main.deck.numberOfCards()));

    public Text playerCards = new Text("Player: " + Main.player.getNumofCards());
    public Text compCards = new Text("Computer: " + Main.player.getNumofCards());

    public PishtiPane() {

        drawHand(Main.player);
        drawHand(Main.computer);

        deckSize.setFont(new Font(45));

        remainingDeck.getChildren().add(new ImageView(new Image("card/b2fv.png")));
        remainingDeck.getChildren().add(deckSize);

        for (int i = 0; i < 3; i++) {
            Node n = new ImageView(new Image("card/b2fv.png"));
            rotate(n);
            pile.getChildren().add(n);
        }

        Node n = new ImageView(new Image("card/" + Main.table.getTablePile().get(3) + ".png"));
        rotate(n);
        pile.getChildren().add(n);

        setCenter(pile);
        pile.setAlignment(Pos.CENTER);
        pile.setPadding(new Insets(10, 10, 10, 10));
        pile.setStyle("-fx-border-color: black; -fx-background-color: forestgreen");

        setBottom(playerHand);
        playerHand.setAlignment(Pos.CENTER);
        playerHand.setPadding(new Insets(10, 10, 10, 10));
        playerHand.setPrefHeight(120);

        setLeft(remainingDeck);
        remainingDeck.setAlignment(Pos.CENTER);
        remainingDeck.setPadding(new Insets(10, 10, 10, 10));
        remainingDeck.setPrefWidth(100);

        setTop(compHand);
        compHand.setAlignment(Pos.CENTER);
        compHand.setPadding(new Insets(10, 10, 10, 10));
        compHand.setPrefHeight(120);

        setRight(otherSide);
        otherSide.setAlignment(Pos.CENTER);
        otherSide.setPadding(new Insets(10, 10, 10, 10));
        otherSide.setPrefWidth(remainingDeck.getWidth());

        playerCards.setFont(new Font(20));
        compCards.setFont(new Font(20));
        Text label = new Text("Number of Cards");
        label.setFont(new Font(15));
        otherSide.getChildren().add(label);
        otherSide.getChildren().add(compCards);
        otherSide.getChildren().add(playerCards);

        setStyle("-fx-border-color: black; -fx-background-color: saddlebrown");
    }

    public void rotate(Node n) {
        Random rdm = new Random();
        n.setRotate(rdm.nextInt(45) - rdm.nextInt(45));
    }

    public void drawHand(Player p) {
        if (p.getPlayerDeck().equals(Main.computer.getPlayerDeck())) {
            compHand.getChildren().clear();
            for (int i = 0; i < Main.computer.getPlayerDeck().size(); i++) {
                compHand.getChildren().add(new ImageView(new Image("card/b2fv.png")));
            }
        } else {
            playerHand.getChildren().clear();
            for (Integer n : Main.player.getPlayerDeck()) {
                playerHand.getChildren().add(new ImageView(new Image("card/" + n + ".png")));
            }
        }

        deckSize.setText(Integer.toString(Main.deck.numberOfCards()));
    }

    public void updatePile() {
        pile.getChildren().clear();
        for (int n = 0; n < Main.table.getTablePile().size(); n++) {
            ImageView card = new ImageView(new Image("card/" + n + ".png"));
            pile.getChildren().add(card);
            rotate(card);
        }
    }
}