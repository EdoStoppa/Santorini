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
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SceneBuilder {



    public  static final HashMap<String,Image> imageHashMap= new HashMap<>();

    public static void initImages(){
        setGodImagesMap();
        setConstructionImagesMap();
        setHighlightedImagesMap();
    }

    //-------- Methods used to create different scenes --------
    public static Parent ChooseGameMode(String message){
        VBox layout= new VBox(ClientGuiApp.height*0.071);
        //layout.setId("init");
        layout.setBackground(getBackground("initBackground"));
        Label welcome= new Label(message);
        HBox Buttons= new HBox(ClientGuiApp.width*0.1875);
        Button twoPlayer= new Button("2 player");
        Button treePlayer=new Button("3 player");
        twoPlayer.setOnAction(e->ClientGuiApp.getClient().writeToSocketGUI("2"));
        treePlayer.setOnAction(e->ClientGuiApp.getClient().writeToSocketGUI("3"));
        Buttons.getChildren().addAll(twoPlayer,treePlayer);
        Buttons.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcome,Buttons);
        return layout;

    }

    public static Parent ChooseName(String message){
        VBox layout=new VBox(ClientGuiApp.height*0.05714);
        layout.setBackground(getBackground("initBackground"));
        Text textName= new Text(message);
        TextField name= new TextField();
        Button go= new Button("go");
        go.setId("button");
        go.setOnAction(e-> ClientGuiApp.getClient().writeToSocketGUI(name.getText()));
        name.setMaxWidth(ClientGuiApp.width*0.1875);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName,name,go);
        return layout;
    }

    public static void PickGod(PickGodMessage message){
        BorderPane layout=new BorderPane();
        //layout.setId("init");
        layout.setBackground(getBackground("initBackground"));
        HBox firstLine= new HBox();
        HBox secondLine=new HBox();
        HBox thirdLine=new HBox();
        HBox fourLine= new HBox(ClientGuiApp.width*0.0375);
        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        thirdLine.setAlignment(Pos.CENTER);
        CheckBox zero=new CheckBox();
        CheckBox one=new CheckBox();
        CheckBox two= new CheckBox();
        CheckBox three= new CheckBox();
        CheckBox four= new CheckBox();
        CheckBox five= new CheckBox();
        CheckBox six=new CheckBox();
        CheckBox seven= new CheckBox();
        CheckBox eight= new CheckBox();
        ImageView IWOne= new ImageView(imageHashMap.get(message.GetGod(0).getGodName()));
        setGodImage(IWOne);
        ImageView IWTwo= new ImageView(imageHashMap.get(message.GetGod(1).getGodName()));
        setGodImage(IWTwo);
        ImageView IWThree= new ImageView(imageHashMap.get(message.GetGod(2).getGodName()));
        setGodImage(IWThree);
        ImageView IWFour= new ImageView(imageHashMap.get(message.GetGod(3).getGodName()));
        setGodImage(IWFour);
        ImageView IWFive = new ImageView(imageHashMap.get(message.GetGod(4).getGodName()));
        setGodImage(IWFive);
        ImageView IWSix = new ImageView(imageHashMap.get(message.GetGod(5).getGodName()));
        setGodImage(IWSix);
        ImageView IWSeven=new ImageView(imageHashMap.get(message.GetGod(6).getGodName()));
        setGodImage(IWSeven);
        ImageView IWEight= new ImageView(imageHashMap.get(message.GetGod(7).getGodName()));
        setGodImage(IWEight);
        ImageView IWNine= new ImageView(imageHashMap.get(message.GetGod(8).getGodName()));
        setGodImage(IWNine);
        Text desctripionZero =new Text(message.GetGod(0).getGodPower());
        desctripionZero.setFont(Font.font(ClientGuiApp.height*0.01714));
        desctripionZero.setWrappingWidth(ClientGuiApp.width*0.15);
        Text desctripionOne =new Text(message.GetGod(1).getGodPower());
        desctripionOne.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionOne.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionTwo =new Text(message.GetGod(2).getGodPower());
        desctripionTwo.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionTwo.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionThree =new Text(message.GetGod(3).getGodPower());
        desctripionThree.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionThree.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionFour =new Text(message.GetGod(4).getGodPower());
        desctripionFour.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionFour.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text descriptionFive =new Text(message.GetGod(5).getGodPower());
        descriptionFive.setWrappingWidth(ClientGuiApp.width*0.15);
        descriptionFive.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionSix =new Text(message.GetGod(6).getGodPower());
        desctripionSix.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionSix.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionSeven =new Text(message.GetGod(7).getGodPower());
        desctripionSeven.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionSeven.setFont(Font.font(ClientGuiApp.height*0.01714));
        Text desctripionEight =new Text(message.GetGod(8).getGodPower());
        desctripionEight.setWrappingWidth(ClientGuiApp.width*0.15);
        desctripionEight.setFont(Font.font(ClientGuiApp.height*0.01714));
        firstLine.getChildren().addAll(zero,IWOne,desctripionZero,one,IWTwo,desctripionOne,two,IWThree,desctripionTwo);
        secondLine.getChildren().addAll(three,IWFour,desctripionThree,four,IWFive,desctripionFour,five,IWSix,descriptionFive);
        thirdLine.getChildren().addAll(six,IWSeven,desctripionSix,seven,IWEight,desctripionSeven,eight,IWNine,desctripionEight);
        VBox griglia=new VBox(ClientGuiApp.width*0.02);
        Button chosenGods=new Button("enter");
        chosenGods.setId("button");
        chosenGods.setOnAction(e->handleOptions(message.getNumPlayer(),zero,one,two,three,four,five,six,seven,eight));
        Text description= new Text("chose "+message.getNumPlayer()+" gods that will be in the match");
        description.setFont(Font.font(ClientGuiApp.height*0.02571));
        fourLine.getChildren().addAll(description,chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        griglia.getChildren().addAll(firstLine,secondLine,thirdLine,fourLine);
        layout.setCenter(griglia);
        Scene scene= new Scene(layout,ClientGuiApp.width,ClientGuiApp.height);
        scene.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        ClientGuiApp.getPrimaryStage().setScene(scene);
    }

    private static void handleOptions(int numPlayer, CheckBox zero, CheckBox one, CheckBox two, CheckBox three, CheckBox four, CheckBox five, CheckBox six, CheckBox seven, CheckBox eight) {
        ArrayList<Integer>chosenGod = new ArrayList<>();
        if (zero.isSelected()){
            chosenGod.add(0);
        }
        if(one.isSelected()){
            chosenGod.add(1);
        }
        if(two.isSelected()){
            chosenGod.add(2);
        }
        if(three.isSelected()){
            chosenGod.add(3);
        }
        if(four.isSelected()){
            chosenGod.add(4);
        }
        if(five.isSelected()){
            chosenGod.add(5);
        }
        if(six.isSelected()){
            chosenGod.add(6);
        }
        if(seven.isSelected()){
            chosenGod.add(7);
        }
        if(eight.isSelected()){
            chosenGod.add(8);
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
            AlertBox.displayError("select a correct number of God");
        }
    }

    public static void chooseGod(ChosenGodMessage message){
        VBox layout=new VBox(ClientGuiApp.height*0.0142);
        //layout.setId("init");
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
        Button sendMessage= new Button("enter");
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
        Button sendMessage= new Button("enter");
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

    public static void waitScene(){
        Label label = new Label("Wait...");
        label.setFont(Font.font(ClientGuiApp.height*0.034));
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(getBackground("initBackground"));
        layout.getChildren().add(label);
        ClientGuiApp.getPrimaryStage().setScene(new Scene(layout, ClientGuiApp.width, ClientGuiApp.height));
    }

    public static void errorScene(){
        Label label = new Label("Wait...");
        label.setFont(Font.font(ClientGuiApp.height*0.034));
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(getBackground("initBackground"));
        layout.getChildren().add(label);
        ClientGuiApp.getPrimaryStage().setScene(new Scene(layout, ClientGuiApp.width, ClientGuiApp.height));
    }

    public static Parent endScene(String message){
        Label label= new Label(message);
        VBox layout=new VBox(10);
        if(message.equals("Wait")) {
            //layout.setId("init");
            layout.setBackground(getBackground("initBackground"));
        } else {
            //layout.setId("win");
            layout.setBackground(getBackground("endBackground"));
        }
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        return layout;

    }

    public static void loseScene(String message){
        Label label;
        if(message == null)
            label = new Label("Thanks for playing!\nIf you want to play another match, please restart the application");
        else
            label = new Label(message);
        VBox layout=new VBox(10);
        layout.setBackground(getBackground("initBackground"));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        ClientGuiApp.getPrimaryStage().setScene(new Scene(layout, ClientGuiApp.width, ClientGuiApp.height));
    }

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