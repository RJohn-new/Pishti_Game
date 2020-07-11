import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

// Application class for the game window and logic
public class PishtiGame extends Application {

    private boolean playerWon;
    private boolean firstPileWin;

    @Override
    public void start(Stage primaryStage) {
        // Call to Main to initialize other objects and variables
        Main.main(null);
        // Creating simpler aliases for objects and ArrayLists
        // Local Variables
        PishtiPane pane = new PishtiPane();
        Player player = Main.player;
        Deck deck = Main.deck;
        Player computer = Main.computer;
        Table table = Main.table;
        firstPileWin = true;

        //Localized ArrayLists
        ArrayList<Integer> playerDeck = player.getPlayerDeck();
        ArrayList<Integer> compDeck = computer.getPlayerDeck();
        ArrayList<Integer> tableDeck = table.getTablePile();

        // Setting up sound
        File file1 = new File("src/sound/shuffle.mp3");
        String source1 = file1.toURI().toString();
        Media media1 = new Media(source1);
        MediaPlayer sound1 = new MediaPlayer(media1);
        sound1.play();

        // Iteration of cards in hand refreshes when mouse moves
        pane.setOnMouseMoved(event -> {
            for (int i = 0; i < playerDeck.size(); i++) {
                Node n = pane.playerHand.getChildren().get(i);
                n.setOnMouseClicked(e -> {
                    // Play card that is selected
                    if (pane.otherSide.getChildren().size() > 3) {
                        pane.otherSide.getChildren().remove(3, 6);
                    }
                    if (pane.playerHand.getChildren().contains(n)) {
                        Main.playCard(pane.playerHand.getChildren().indexOf(n), player, playerDeck, table);
                        pane.pile.getChildren().add(pane.pile.getChildren().size(), n);
                        pane.playerHand.getChildren().remove(n);
                        pane.rotate(n);
                        PathTransition pt = new PathTransition(Duration.millis(1000),
                                new Line(50, 200, 40, 40), n);
                        pt.setCycleCount(1);
                        pt.play(); // Start animation

                        File file2 = new File("src/sound/playcard.mp3");
                        String source2 = file2.toURI().toString();
                        Media media2 = new Media(source2);
                        MediaPlayer sound2 = new MediaPlayer(media2);
                        sound2.setVolume(0.25);
                        sound2.play();

                        if (table.win()) {
                            if (firstPileWin) {
                                for (int j = 0; j < 3; j++) {
                                    ImageView flip = new ImageView(new Image("card/" + tableDeck.get(j) + ".png"));
                                    pane.otherSide.getChildren().add(flip);
                                }
                                firstPileWin = false;
                            }
                            player.setScore(table.calcTablePoints());
                            player.setNumOfCardsInPile(table.getTablePile().size());
                            pane.playerCards.setText("Player: " + Integer.toString(player.getNumofCards()));
                            table.getTablePile().clear();
                            table.tableScore = 0;
                            pane.updatePile();
                            playerWon = true;
                            Node x = new ImageView(new Image("card/b2fv.png"));
                            pane.pile.getChildren().add(x);
                            PathTransition won = new PathTransition(Duration.millis(1000),
                                    new Line(40, 40, 1000, 600), x);
                            won.setCycleCount(1);
                            won.play();

                            File file = new File("src/sound/winhand.mp3");
                            String source = file.toURI().toString();
                            Media media = new Media(source);
                            MediaPlayer sound = new MediaPlayer(media);
                            sound.play();
                        }
                        // Computer takes turn after your click
                        compPlay(pane);

                    }
                    if (Main.player.getPlayerDeck().isEmpty() && deck.returnDeck().size() != 0) {
                        Main.giveCards(playerDeck, deck, player);
                        pane.drawHand(player);
                    }
                    if (computer.getPlayerDeck().isEmpty() && deck.returnDeck().size() != 0) {
                        Main.giveCards(compDeck, deck, computer);
                        pane.drawHand(computer);
                    }
                    if (deck.returnDeck().isEmpty() && compDeck.isEmpty()) {
                        if (playerWon) {
                            player.setScore(table.calcTablePoints());
                            player.setNumOfCardsInPile(table.getTablePile().size());
                            table.getTablePile().clear();
                            pane.updatePile();
                            if (player.getNumofCards() > computer.getNumofCards()) {
                                player.setScore(3);
                            } else if (player.getNumofCards() < computer.getNumofCards()) {
                                computer.setScore(3);
                            }
                            Node x = new ImageView(new Image("card/b2fv.png"));
                            pane.pile.getChildren().add(x);
                            PathTransition pt = new PathTransition(Duration.millis(1000),
                                    new Line(40, 40, 1000, 600), x);
                            pt.setCycleCount(1);
                            pt.play();
                            pane.playerCards.setText("Player: " + Integer.toString(player.getNumofCards()));
                        } else if (!playerWon) {
                            computer.setScore(table.calcTablePoints());
                            computer.setNumOfCardsInPile(table.getTablePile().size());
                            table.getTablePile().clear();
                            pane.updatePile();
                            if (player.getNumofCards() > computer.getNumofCards()) {
                                player.setScore(3);
                            } else if (player.getNumofCards() < computer.getNumofCards()) {
                                computer.setScore(3);
                            }
                            Node x = new ImageView(new Image("card/b2fv.png"));
                            pane.pile.getChildren().add(x);
                            PathTransition pt = new PathTransition(Duration.millis(1000),
                                    new Line(40, 40, 1000, -520), x);
                            pt.setCycleCount(1);
                            pt.play();
                            pane.compCards.setText("Computer: " + Integer.toString(Main.computer.getNumofCards()));
                        }
                        String playerScore = Integer.toString(player.getScore());
                        String compScore = Integer.toString(computer.getScore());

                        // Closes and relaunches the stage to replay the game
                        pane.requestFocus();
                        pane.pile.setOnMouseClicked(game -> {
                            primaryStage.close();
                            start(new Stage());
                        });
                        // End situations: Win, Lose, or Draw
                        if (player.getScore() > computer.getScore()) {
                            Text win = new Text("You Win!\nComputer Score:" + compScore + "\nYour Score:" + playerScore);
                            win.setFont(new Font(47));
                            pane.pile.getChildren().add(win);

                            File file3 = new File("src/sound/wingame.mp3");
                            String source3 = file3.toURI().toString();
                            Media media3 = new Media(source3);
                            MediaPlayer sound3 = new MediaPlayer(media3);
                            sound3.play();
                        } else if (player.getScore() < computer.getScore()) {
                            Text win = new Text("You Lose!\nComputer Score:" + compScore + "\nYour Score:" + playerScore);
                            win.setFont(new Font(47));
                            pane.pile.getChildren().add(win);

                            File file4 = new File("src/sound/losegame.mp3");
                            String source4 = file4.toURI().toString();
                            Media media4 = new Media(source4);
                            MediaPlayer sound4 = new MediaPlayer(media4);
                            sound4.play();
                        } else {
                            Text win = new Text("Draw!\nComputer Score:" + compScore + "\nYour Score:" + playerScore);
                            win.setFont(new Font(47));
                            pane.pile.getChildren().add(win);

                            File file5 = new File("src/sound/drawgame.mp3");
                            String source5 = file5.toURI().toString();
                            Media media5 = new Media(source5);
                            MediaPlayer sound5 = new MediaPlayer(media5);
                            sound5.play();
                        }
                        Text Replay = new Text("\n\n\n\nClick to play again");
                        Replay.setFont(new Font(47));
                        pane.pile.getChildren().add(Replay);
                    }
                });
            }
        });
        Scene scene = new Scene(pane, 700, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pishti Game");
        primaryStage.show();
    }

    public void compPlay(PishtiPane pane) {
        for (int i = 0; i < Main.computer.getPlayerDeck().size(); i++) {
            Node k = pane.compHand.getChildren().get(i);
            if (!Main.table.getTablePile().isEmpty()) {
                for (int j = 0; j < Main.computer.getPlayerDeck().size(); j++) {
                    if (Main.computer.getPlayerDeck().get(j) % 13 == 11 ||
                            Main.computer.getPlayerDeck().get(j) % 13 == Main.table.getTablePile().get(Main.table.getTablePile().size() - 1) % 13) {
                        k = pane.compHand.getChildren().get(j);
                        break;
                    }
                }
            }
            if (pane.compHand.getChildren().contains(k)) {
                ImageView newCard = new ImageView(new Image("card/" + Main.computer.getPlayerDeck().get(
                        pane.compHand.getChildren().indexOf(k)) + ".png"));
                Main.playCard(pane.compHand.getChildren().indexOf(k), Main.computer, Main.computer.getPlayerDeck(), Main.table);
                pane.pile.getChildren().add(pane.pile.getChildren().size(), newCard);
                pane.compHand.getChildren().remove(k);
                pane.rotate(newCard);
                PathTransition pt = new PathTransition(Duration.millis(1500),
                        new Line(50, -120, 40, 40), newCard);
                pt.setCycleCount(1);
                pt.play();

                if (Main.table.win()) {
                    firstPileWin = false;
                    Main.computer.setScore(Main.table.calcTablePoints());
                    Main.computer.setNumOfCardsInPile(Main.table.getTablePile().size());
                    pane.compCards.setText("Computer: " + Integer.toString(Main.computer.getNumofCards()));
                    Main.table.getTablePile().clear();
                    Main.table.tableScore = 0;
                    pane.updatePile();
                    playerWon = false;
                    Node x = new ImageView(new Image("card/b2fv.png"));
                    pane.pile.getChildren().add(x);
                    PathTransition won = new PathTransition(Duration.millis(1000),
                            new Line(40, 40, 1000, -520), x);
                    won.setCycleCount(1);
                    won.play();

                    File file = new File("src/sound/losehand.mp3");
                    String source = file.toURI().toString();
                    Media media = new Media(source);
                    MediaPlayer sound = new MediaPlayer(media);
                    sound.play();
                }
                break;
            }
        }
        return;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
