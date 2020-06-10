package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.Client;
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
            Scene scene=new Scene(ChooseName("enter your name"),ClientGuiApp.width,ClientGuiApp.height);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        treePlayer.setOnAction(e->{
            ClientGuiApp.getClient().asyncWriteToSocketGUI("3");
            Scene scene=new Scene(ChooseName("enter your name"),ClientGuiApp.width, ClientGuiApp.height);
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
        chosenGods.setOnAction(e->handleOptions(message.getNumPlayer(),zero,one,two,three,four,five,six,seven,eight));
        Text description= new Text("chose "+message.getNumPlayer()+" gods that will be in the match");
        description.setFont(Font.font(ClientGuiApp.height*0.02571));
        fourLine.getChildren().addAll(description,chosenGods);
        fourLine.setAlignment(Pos.CENTER);
        griglia.getChildren().addAll(firstLine,secondLine,thirdLine,fourLine);
        layout.setCenter(griglia);
        Scene scene3= new Scene(layout,ClientGuiApp.width,ClientGuiApp.height);
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
            Scene scene=new Scene(handeScene("wait"),ClientGuiApp.width,ClientGuiApp.height);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        }else{
            AlertBox.displayError("select a correct number of God");
        }
    }

    public static void chooseGod(ChosenGodMessage message){
        BorderPane borderPane=new BorderPane();
        VBox layout=new VBox(ClientGuiApp.height*0.0142);
        layout.setAlignment(Pos.CENTER);
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton third= new RadioButton();
        HBox firstLine= new HBox(ClientGuiApp.width*0.025);
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
                ClientGuiApp.getClient().asyncWriteToSocketGUI("0");
            if(second.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI("1");
            if(third.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI("2");
            Scene scene=new Scene(BoardScene.createContent(),ClientGuiApp.width,ClientGuiApp.height);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        borderPane.setCenter(layout);
        Scene scene4= new Scene(borderPane,ClientGuiApp.width,ClientGuiApp.height);
        ClientGuiApp.getPrimaryStage().setScene(scene4);
    }

    public static void orderGame(OrderGameMessage message){
        VBox layout=new VBox(ClientGuiApp.width*0.02857);
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
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(0));
            if(second.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(1));
            if(third.isSelected())
                ClientGuiApp.getClient().asyncWriteToSocketGUI(message.getPlayerlist().get(2));
            Scene scene=new Scene(BoardScene.createContent(),ClientGuiApp.width,ClientGuiApp.height);
            ClientGuiApp.getPrimaryStage().setScene(scene);
        });
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout,ClientGuiApp.width,ClientGuiApp.height);
        ClientGuiApp.getPrimaryStage().setScene(scene);

    }



    public static void setGodImage(ImageView IWGod){
        IWGod.setFitHeight(ClientGuiApp.height*0.27);
        IWGod.setFitWidth(ClientGuiApp.width*0.15);
    }

    public static Parent handeScene(String message){
        Label label= new Label(message);
        VBox layout=new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        return layout;

    }
}