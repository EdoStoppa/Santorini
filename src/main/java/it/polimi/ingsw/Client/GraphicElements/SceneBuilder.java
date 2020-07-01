package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.OrderGameMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import it.polimi.ingsw.Model.God;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * this class create all the scenes
 */
public class SceneBuilder {


    public static final HashMap<String, Image> imageHashMap = new HashMap<>();

    /**
     * this method initializes the images
     */
    public static void initImages() {
        setGodImagesMap();
        setConstructionImagesMap();
        setHighlightedImagesMap();
    }

    //-------- Methods used to create different scenes --------

    /**
     * this method create a scene where the player choose the number of player in the game
     *
     * @param message string to display
     * @return the root of the constructed scene
     */
    public static Parent ChooseGameMode(String message) {
        VBox layout = new VBox(ClientGuiApp.height * 0.071);
        layout.setBackground(getBackground("initBackground"));
        Label welcome = new Label(message);
        welcome.setFont(Font.font(ClientGuiApp.height * 0.02571));
        HBox Buttons = new HBox(ClientGuiApp.width * 0.1875);
        Button twoPlayer = new Button("2 player");
        twoPlayer.setFont(Font.font(ClientGuiApp.height*0.0285));
        Button treePlayer = new Button("3 player");
        treePlayer.setFont(Font.font(ClientGuiApp.height*0.0285));
        twoPlayer.setOnAction(e -> ClientGuiApp.getClient().writeToSocketGUI("2"));
        treePlayer.setOnAction(e -> ClientGuiApp.getClient().writeToSocketGUI("3"));
        Buttons.getChildren().addAll(twoPlayer, treePlayer);
        Buttons.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcome, Buttons);
        return layout;

    }

    /**
     * this method create a scene where the player choose his nickname
     *
     * @param message string to display
     * @return the root of the constructed scene
     */
    public static Parent ChooseName(String message) {
        VBox layout = new VBox(ClientGuiApp.height * 0.05714);
        layout.setBackground(getBackground("initBackground"));
        Text textName = new Text(message);
        textName.setFont(Font.font(ClientGuiApp.height * 0.02571));
        TextField name = new TextField();
        Button go = new Button("Go");
        go.setFont(Font.font(ClientGuiApp.height*0.0285));
        go.setId("button");
        go.setOnAction(e -> ClientGuiApp.getClient().writeToSocketGUI(name.getText()));
        name.setMaxWidth(ClientGuiApp.width * 0.1875);
        name.setFont(Font.font(ClientGuiApp.height*0.04));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName, name, go);
        return layout;
    }

    /**
     * this method create a scene where the godLikePlayer choose the god for the game
     *
     * @param message contains all the information for the god
     */
    public static void PickGod(PickGodMessage message) {
        BorderPane layout = new BorderPane();
        layout.setBackground(getBackground("initBackground"));
        HBox firstLine = new HBox();
        HBox secondLine = new HBox();
        HBox thirdLine = new HBox();
        HBox fourLine = new HBox(ClientGuiApp.width * 0.0375);

        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        thirdLine.setAlignment(Pos.CENTER);

        ArrayList<CheckBox> godCheckBox = new ArrayList<>();
        ArrayList<ImageView> godIW = new ArrayList<>();
        ArrayList<Text> powerGod = new ArrayList<>();

        for (int i = 0; i < God.getAllGod().size(); i++) {
            godCheckBox.add(new CheckBox());

            ImageView iw = new ImageView(imageHashMap.get(message.GetGod(i).getGodName()));
            setGodImage(iw);
            godIW.add(iw);

            Text descriptionGod = new Text(message.GetGod(i).getGodPower());
            descriptionGod.setFont(Font.font(ClientGuiApp.height * 0.01714));
            descriptionGod.setWrappingWidth(ClientGuiApp.width * 0.15);
            powerGod.add(descriptionGod);
        }

        firstLine.getChildren().addAll(godCheckBox.get(0), godIW.get(0), powerGod.get(0), godCheckBox.get(1), godIW.get(1), powerGod.get(1), godCheckBox.get(2), godIW.get(2), powerGod.get(2));
        secondLine.getChildren().addAll(godCheckBox.get(3), godIW.get(3), powerGod.get(3), godCheckBox.get(4), godIW.get(4), powerGod.get(4), godCheckBox.get(5), godIW.get(5), powerGod.get(5));
        thirdLine.getChildren().addAll(godCheckBox.get(6), godIW.get(6), powerGod.get(6), godCheckBox.get(7), godIW.get(7), powerGod.get(7), godCheckBox.get(8), godIW.get(8), powerGod.get(8));

        VBox grid = new VBox(ClientGuiApp.width * 0.02);
        Button chosenGods = new Button("Go");
        chosenGods.setFont(Font.font(ClientGuiApp.height*0.0285));
        chosenGods.setId("button");
        chosenGods.setOnAction(e -> handleOptions(message.getNumPlayer(), godCheckBox));

        Text description = new Text("Choose " + message.getNumPlayer() + " gods that will be in the match");
        description.setFont(Font.font(ClientGuiApp.height * 0.02571));

        fourLine.getChildren().addAll(description, chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        grid.getChildren().addAll(firstLine, secondLine, thirdLine);
        layout.setCenter(grid);
        layout.setBottom(fourLine);
        layout.setPadding(new Insets(0, 0, 10, 0));

        Scene scene = new Scene(layout, ClientGuiApp.width, ClientGuiApp.height);
        scene.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        ClientGuiApp.getPrimaryStage().setScene(scene);
    }

    /**
     *  this method check if the godLikePlayer choose a correct number of god
     * @param numPlayer number of player in the game
     * @param checkBoxList arraylist for all the CheckBox
     */
    private static void handleOptions(int numPlayer, ArrayList<CheckBox> checkBoxList) {
        ArrayList<Integer>chosenGod = new ArrayList<>();
        for(CheckBox c : checkBoxList){
            if(c.isSelected())
                chosenGod.add(checkBoxList.indexOf(c));

        }

        if (chosenGod.size()==numPlayer){
            StringBuilder message= new StringBuilder();
            for(int i=0;i<numPlayer;i++){
                message.append(chosenGod.get(i));
                if (i!=numPlayer-1){
                    message.append(",");
                }
            }
            ClientGuiApp.getClient().writeToSocketGUI(message.toString());
            waitScene();
        }else{
            AlertBox.displayError("Select the correct number of Gods");
        }
    }

    /**
     * this method create a scene where the player choose his god for the game
     * @param message contains all the information for the chosen god
     */
    public static void chooseGod(ChosenGodMessage message){
        VBox layout=new VBox(ClientGuiApp.height*0.0142);
        layout.setAlignment(Pos.CENTER);
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton third= new RadioButton();
        HBox firstLine= new HBox(ClientGuiApp.width*0.025);
        firstLine.setAlignment(Pos.CENTER);
        RadioButton first= new RadioButton();
        first.setToggleGroup(radioGroup);
        first.setSelected(true);
        ImageView IWFirst = new ImageView(imageHashMap.get(message.getChosenGod(0).getGodName()));
        setGodImage(IWFirst);
        Text firstDescription=new Text(message.getChosenGod(0).getGodPower());
        firstDescription.setFont(Font.font(ClientGuiApp.height*0.0228));
        firstDescription.setWrappingWidth(ClientGuiApp.width*0.7);
        firstLine.getChildren().addAll(first,IWFirst,firstDescription);
        HBox secondLine= new HBox(ClientGuiApp.width*0.025);
        secondLine.setAlignment(Pos.CENTER);
        RadioButton second= new RadioButton();
        second.setToggleGroup(radioGroup);
        ImageView IWSecond= new ImageView(imageHashMap.get(message.getChosenGod(1).getGodName()));
        setGodImage(IWSecond);
        Text secondDescription=new Text(message.getChosenGod(1).getGodPower());
        secondDescription.setFont(Font.font(ClientGuiApp.height*0.0228));
        secondDescription.setWrappingWidth(ClientGuiApp.width*0.7);
        secondLine.getChildren().addAll(second,IWSecond,secondDescription);
        layout.getChildren().addAll(firstLine,secondLine);
        if(message.getSize()==3){
            HBox thirdLine= new HBox(ClientGuiApp.width*0.025);
            thirdLine.setAlignment(Pos.CENTER);
            third.setToggleGroup(radioGroup);
            ImageView IWThird= new ImageView(imageHashMap.get(message.getChosenGod(2).getGodName()));
            setGodImage(IWThird);
            Text thirdDescription=new Text(message.getChosenGod(2).getGodPower());
            thirdDescription.setFont(Font.font(ClientGuiApp.height*0.0228));
            thirdDescription.setWrappingWidth(ClientGuiApp.width*0.7);
            thirdLine.getChildren().addAll(third,IWThird,thirdDescription);
            layout.getChildren().add(thirdLine);
        }
        HBox fourLine= new HBox(ClientGuiApp.width*0.0375);
        Button sendMessage= new Button("Go");
        sendMessage.setFont(Font.font(ClientGuiApp.height*0.0285));
        Text description= new Text("Choose the god you'll use for this match");
        description.setFont(Font.font(ClientGuiApp.height*0.02571));
        fourLine.getChildren().addAll(description,sendMessage);
        sendMessage.setOnAction(e->{
            if(first.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI("0");
            if(second.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI("1");
            if(third.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI("2");

            waitScene();
        });
        fourLine.setAlignment(Pos.CENTER);
        BorderPane borderPane=new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(fourLine);
        borderPane.setPadding(new Insets(0,0,10,0));
        borderPane.setBackground(getBackground("initBackground"));
        Scene scene4= new Scene(borderPane,ClientGuiApp.width,ClientGuiApp.height);
        scene4.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        ClientGuiApp.getPrimaryStage().setScene(scene4);
    }
    /**
     * this method create a scene where the godLikePlayer choose who start the game
     * @param message contains all the player's name
     */
    public static void orderGame(OrderGameMessage message){
        VBox layout=new VBox(ClientGuiApp.width*0.02857);
        layout.setBackground(getBackground("initBackground"));
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton first=new RadioButton();
        RadioButton second=new RadioButton();
        RadioButton third=new RadioButton();
        first.setToggleGroup(radioGroup);
        second.setToggleGroup(radioGroup);
        third.setToggleGroup(radioGroup);
        first.setSelected(true);
        HBox firstLine=new HBox(ClientGuiApp.width*0.0125);
        firstLine.setAlignment(Pos.CENTER);
        Text firstName=new Text(message.getPlayerlist().get(0));
        firstName.setFont(Font.font(ClientGuiApp.height*0.0228));
        firstLine.getChildren().addAll(first,firstName);
        HBox secondLine=new HBox(ClientGuiApp.width*0.0125);
        secondLine.setAlignment(Pos.CENTER);
        Text secondName=new Text(message.getPlayerlist().get(1));
        secondName.setFont(Font.font(ClientGuiApp.height*0.0228));
        secondLine.getChildren().addAll(second,secondName);
        layout.getChildren().addAll(firstLine,secondLine);
        if (message.getPlayerlist().size()==3){
            HBox thirdLine=new HBox(ClientGuiApp.width*0.0125);
            thirdLine.setAlignment(Pos.CENTER);
            Text thirdName=new Text(message.getPlayerlist().get(2));
            thirdName.setFont(Font.font(ClientGuiApp.height*0.0228));
            thirdLine.getChildren().addAll(third,thirdName);
            layout.getChildren().add(thirdLine);
        }
        HBox fourLine= new HBox(ClientGuiApp.width*0.025);
        Button sendMessage= new Button("Go");
        sendMessage.setFont(Font.font(ClientGuiApp.height*0.0285));
        Text description= new Text("Choose the player which'll move first");
        description.setFont(Font.font(ClientGuiApp.height*0.02571));
        fourLine.getChildren().addAll(description,sendMessage);
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        sendMessage.setOnAction(e->{
            if(first.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI(message.getPlayerlist().get(0));
            if(second.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI(message.getPlayerlist().get(1));
            if(third.isSelected())
                ClientGuiApp.getClient().writeToSocketGUI(message.getPlayerlist().get(2));

            waitScene();
        });
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout,ClientGuiApp.width,ClientGuiApp.height);
        scene.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        ClientGuiApp.getPrimaryStage().setScene(scene);

    }

    /**
     * this method creates a scene to be used when the client waits for information from server
     */
    public static void waitScene(){
        Label label = new Label("Wait...");
        label.setFont(Font.font(ClientGuiApp.height*0.034));
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(getBackground("initBackground"));
        layout.getChildren().add(label);
        ClientGuiApp.getPrimaryStage().setScene(new Scene(layout, ClientGuiApp.width, ClientGuiApp.height));
    }


    /**
     * this method creates a scene to used when the game ended
     * @param message string to display
     * @return root of the constructed scene
     */
    public static Parent endScene(String message){
        Label label= new Label(message);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(ClientGuiApp.height*0.05));
        VBox layout=new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        BorderPane pane=new BorderPane();
        pane.setCenter(layout);
        Label endMessage= new Label("To play again, please restart the App!");
        label.setAlignment(Pos.CENTER);
        endMessage.setAlignment(Pos.BOTTOM_CENTER);
        endMessage.setFont(Font.font(ClientGuiApp.height*0.028));
        endMessage.setPadding(new Insets(0,0 ,10,ClientGuiApp.width*0.15));
        endMessage.setId("bold");
        pane.setBottom(endMessage);
        if(message.equals("Wait")) {
            pane.setBackground(getBackground("initBackground"));
        } else {
            label.setId("bold");
            label.setPadding(new Insets(ClientGuiApp.height*0.089,0,0,0));
            pane.setBackground(getBackground("endBackground"));
        }

        return pane;

    }

    /**
     * this method creates a scene to used when the player loose
     * @param message string to display
     */
    public static void loseScene(String message){
        Label label;
        if(message == null)
            label = new Label("Thanks for playing!\nIf you want to play another match, please restart the application");
        else
            label = new Label(message);
        label.setFont(Font.font(ClientGuiApp.height*0.034));
        VBox layout=new VBox(10);
        layout.setBackground(getBackground("initBackground"));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        ClientGuiApp.getPrimaryStage().setScene(new Scene(layout, ClientGuiApp.width, ClientGuiApp.height));
    }

    /**
     * this method creates a scene to used when the game ended
     * @param win a boolean to check if the player is the winner
     * @param winner string to display for the looser
     */
    public  static void endGameTransition(boolean win, String winner){
        Platform.runLater(()->{
            if (win){
                Scene SceneWin = new Scene(SceneBuilder.endScene("You!"),ClientGuiApp.width,ClientGuiApp.height);
                SceneWin.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
                ClientGuiApp.getPrimaryStage().setScene(SceneWin);
            }else{
                Scene SceneLose = new Scene(SceneBuilder.endScene(winner),ClientGuiApp.width,ClientGuiApp.height);
                SceneLose.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
                ClientGuiApp.getPrimaryStage().setScene(SceneLose);
            }
        });

    }

    //-------- Methods used to initialize and manipulate all the images --------


    /**
     * method to initialize the imageHashMap
     */
    public static void setGodImagesMap(){
        for(God god : God.getAllGod()){
            String path = "God/" + god.getGodName() + ".png";
            try{
                Image godImage = new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(path)));
                imageHashMap.put(god.getGodName(), godImage);
            } catch (Exception e){
                System.out.println("Image not found for " + god.getGodName());
            }
        }
    }

    /**
     * method to initialize lightTileHashMap and darkTileHashMap
     */
    private static void setConstructionImagesMap() {
        for (int i=0;i<=7;i++){
            String pathLight="Tile/light/" +i+ "light.png";
            String pathDark="Tile/dark/" +i+ "dark.png";
            try {
                Image lightTile = new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(pathLight)));
                Image darkTile= new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(pathDark)));
                BoardScene.lightTileHashMap.put(i,lightTile);
                BoardScene.darkTileHashMap.put(i,darkTile);
            }catch (Exception e){
                System.out.println("Image not found for tile" +i);
            }

        }
    }


    /**
     * method to initialize HighlightedLightTileHashMap and HighlightedDarkTileHashMap
     */
    private static void setHighlightedImagesMap() {
        for (int i=0;i<=3;i++){
            String pathLight="Tile/highlighted/"+i+"showLight.png";
            String pathDark="Tile/highlighted/"+i+"showDark.png";
            try {
                Image showLightTile = new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(pathLight)));
                Image showDarkTile= new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(pathDark)));
                BoardScene.HighlightedLightTileHashMap.put(i,showLightTile);
                BoardScene.HighlightedDarkTileHashMap.put(i,showDarkTile);
            }catch (Exception e){
                System.out.println("Image not found for  Show tile" +i);
            }

        }
    }

    /**
     *  method for setting the image size
     * @param IWGod image to be sized
     */
    public static void setGodImage(ImageView IWGod){
        IWGod.setFitHeight(ClientGuiApp.height*0.27);
        IWGod.setFitWidth(ClientGuiApp.width*0.15);
    }

    public static Background getBackground(String nameBackground){
        String path = "Background/" + nameBackground + ".png";
        Image img = new Image(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResourceAsStream(path)));
        BackgroundSize bkgSize = new BackgroundSize(100.0, 100.0, true, true, true, true);
        BackgroundImage bkgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bkgSize);
        return new Background(bkgImg);
    }
}