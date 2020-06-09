package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.OrderGameMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SceneBuilder {



    public  static final HashMap<String,Image> imageHashMap= new HashMap<>();

    public SceneBuilder(){
        try {
            Image Apollo = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Apollo.png")));
            Image Artemis = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Artemis.png")));
            Image Athena = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Athena.png")));
            Image Atlas = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Atlas.png")));
            Image Demeter = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Demeter.png")));
            Image Hephaestus = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Hephaestus.png")));
            Image Minotaur = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Minotaur.png")));
            Image Pan = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Pan.png")));
            Image Prometheus = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Prometheus.png")));
            imageHashMap.put("Apollo", Apollo);
            imageHashMap.put("Artemis", Artemis);
            imageHashMap.put("Athena", Athena);
            imageHashMap.put("Atlas", Atlas);
            imageHashMap.put("Demeter", Demeter);
            imageHashMap.put("Hephaestus", Hephaestus);
            imageHashMap.put("Minotaur", Minotaur);
            imageHashMap.put("Pan", Pan);
            imageHashMap.put("Prometheus", Prometheus);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    public static Parent ChooseGameMode(){
        VBox layout1= new VBox(50);
        Label welcome= new Label("welcome to santorini\n 2 or 3 player mode?");
        HBox Buttons= new HBox(150);
        Button twoPlayer= new Button("2 player");
        Button treePlayer=new Button("3 player");
        twoPlayer.setOnAction(e->{
            ClientGuiApp.getClient().asyncWriteToSocketGUI("2");
            Scene scene=new Scene(ChooseName("enter your name"),800,710);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        treePlayer.setOnAction(e->{
            ClientGuiApp.getClient().asyncWriteToSocketGUI("3");
            Scene scene=new Scene(ChooseName("enter your name"),800,710);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        Buttons.getChildren().addAll(twoPlayer,treePlayer);
        Buttons.setAlignment(Pos.CENTER);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(welcome,Buttons);
        return layout1;

    }


    public static Parent ChooseName(String message){
        VBox layout=new VBox(40);
        Text textName= new Text(message);
        TextField name= new TextField();
        Button go= new Button("go");
        go.setOnAction(e-> ClientGuiApp.getClient().asyncWriteToSocketGUI(name.getText()));
        name.setMaxWidth(150);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName,name,go);
        return layout;
    }


    public static void PickGod(PickGodMessage message){
        BorderPane layout=new BorderPane();
        HBox firstLine= new HBox();
        HBox secondLine=new HBox();
        HBox thirdLine=new HBox();
        HBox fourLine= new HBox(30);
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
        desctripionZero.setWrappingWidth(130);
        Text desctripionOne =new Text(message.GetGod(1).getGodPower());
        desctripionOne.setWrappingWidth(130);
        Text desctripionTwo =new Text(message.GetGod(2).getGodPower());
        desctripionTwo.setWrappingWidth(130);
        Text desctripionThree =new Text(message.GetGod(3).getGodPower());
        desctripionThree.setWrappingWidth(130);
        Text desctripionFour =new Text(message.GetGod(4).getGodPower());
        desctripionFour.setWrappingWidth(130);
        Text descriptionFive =new Text(message.GetGod(5).getGodPower());
        descriptionFive.setWrappingWidth(130);
        Text desctripionSix =new Text(message.GetGod(6).getGodPower());
        desctripionSix.setWrappingWidth(130);
        Text desctripionSeven =new Text(message.GetGod(7).getGodPower());
        desctripionSeven.setWrappingWidth(130);
        Text desctripionEight =new Text(message.GetGod(8).getGodPower());
        desctripionEight.setWrappingWidth(130);
        firstLine.getChildren().addAll(zero,IWOne,desctripionZero,one,IWTwo,desctripionOne,two,IWThree,desctripionTwo);
        secondLine.getChildren().addAll(three,IWFour,desctripionThree,four,IWFive,desctripionFour,five,IWSix,descriptionFive);
        thirdLine.getChildren().addAll(six,IWSeven,desctripionSix,seven,IWEight,desctripionSeven,eight,IWNine,desctripionEight);
        VBox griglia=new VBox(30);
        Button chosenGods=new Button("enter");
        chosenGods.setOnAction(e->handleOptions(message.getNumPlayer(),zero,one,two,three,four,five,six,seven,eight));
        Text description= new Text("chose "+message.getNumPlayer()+" gods that will be in the match");
        description.setFont(Font.font(18));
        fourLine.getChildren().addAll(description,chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        griglia.getChildren().addAll(firstLine,secondLine,thirdLine,fourLine);
        layout.setCenter(griglia);
        Scene scene3= new Scene(layout,850,710);
        ClientGuiApp.getPrimaryStage().setScene(scene3);
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
            System.out.println(message);
            ClientGuiApp.getClient().asyncWriteToSocketGUI(message.toString());
            Scene scene=new Scene(handeScene("wait"),810,700);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        }else{
            AlertBox.displayError("select a correct number of God");
        }
    }

    public static void chooseGod(ChosenGodMessage message){
        BorderPane borderPane=new BorderPane();
        VBox layout=new VBox(20);
        layout.setAlignment(Pos.CENTER);
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton third= new RadioButton();
        HBox firstLine= new HBox(20);
        RadioButton first= new RadioButton();
        first.setToggleGroup(radioGroup);
        ImageView IWFirst = new ImageView(imageHashMap.get(message.getChosenGod(0).getGodName()));
        setGodImage(IWFirst);
        Text firstDescription=new Text(message.getChosenGod(0).getGodPower());
        firstLine.getChildren().addAll(first,IWFirst,firstDescription);
        HBox secondLine= new HBox(20);
        RadioButton second= new RadioButton();
        second.setToggleGroup(radioGroup);
        ImageView IWSecond= new ImageView(imageHashMap.get(message.getChosenGod(1).getGodName()));
        setGodImage(IWSecond);
        Text secondDescription=new Text(message.getChosenGod(1).getGodPower());
        secondLine.getChildren().addAll(second,IWSecond,secondDescription);
        layout.getChildren().addAll(firstLine,secondLine);
        if(message.getSize()==3){
            HBox thirdLine= new HBox(20);
            third.setToggleGroup(radioGroup);
            ImageView IWThird= new ImageView(imageHashMap.get(message.getChosenGod(2).getGodName()));
            setGodImage(IWThird);
            Text thirdDescription=new Text(message.getChosenGod(2).getGodPower());
            thirdLine.getChildren().addAll(third,IWThird,thirdDescription);
            layout.getChildren().add(thirdLine);
        }
        HBox fourLine= new HBox();
        Button sendMessage= new Button("enter");
        fourLine.getChildren().add(sendMessage);
        sendMessage.setOnAction(e->{
            if(first.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI("0");
            if(second.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI("1");
            if(third.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI("2");
            Scene scene=new Scene(BoardScene.createContent(),810,700);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        borderPane.setCenter(layout);
        Scene scene4= new Scene(borderPane,850,710);
        ClientGuiApp.getPrimaryStage().setScene(scene4);
    }

    public static void orderGame(OrderGameMessage message){
        VBox layout=new VBox();
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton first=new RadioButton();
        RadioButton second=new RadioButton();
        RadioButton third=new RadioButton();
        first.setToggleGroup(radioGroup);
        second.setToggleGroup(radioGroup);
        third.setToggleGroup(radioGroup);
        HBox firstLine=new HBox(10);
        Text firstName=new Text(message.getPlayerlist().get(0));
        firstLine.getChildren().addAll(first,firstName);
        HBox secondLine=new HBox(10);
        Text secondName=new Text(message.getPlayerlist().get(1));
        secondLine.getChildren().addAll(second,secondName);
        layout.getChildren().addAll(firstLine,secondLine);
        if (message.getPlayerlist().size()==3){
            HBox thirdLine=new HBox(10);
            Text thirdName=new Text(message.getPlayerlist().get(2));
            thirdLine.getChildren().addAll(third,thirdName);
            layout.getChildren().add(thirdLine);
        }
        HBox fourLine= new HBox();
        Button sendMessage= new Button("enter");
        fourLine.getChildren().add(sendMessage);
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        sendMessage.setOnAction(e->{
            if(first.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(0));
            if(second.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(1));
            if(third.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(2));
            Scene scene=new Scene(BoardScene.createContent(),810,700);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout,710,800);
        ClientGuiApp.getPrimaryStage().setScene(scene);

    }



    public static void setGodImage(ImageView IWGod){
        IWGod.setFitHeight(190);
        IWGod.setFitWidth(133.33);
    }

    public static Parent handeScene(String message){
        Label label= new Label(message);
        VBox layout=new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        return layout;

    }
}