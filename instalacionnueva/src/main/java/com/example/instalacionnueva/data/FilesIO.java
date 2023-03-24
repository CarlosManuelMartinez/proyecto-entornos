package com.example.instalacionnueva.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilesIO {
    //Method to create a List of users from a fileName.
    public static List<User> UserListFromTxt(String fileName,
                                                 String symbol){
        List<User> userList = new ArrayList<>() {
        };
        BufferedReader inputFile = null;
        try{
            inputFile = new BufferedReader(new FileReader(fileName));
            String line = inputFile.readLine();
            while (line != null){
                userList.add(LineParseUser(line,symbol));
                line = inputFile.readLine();
            }
            return userList;
        }
        catch (IOException fileError){
            System.out.println("UserListFromTxt");
            System.err.println("Error reading UserListFromTxt");
            return userList;
        }

    }


    public static List<Pilot> PilotsListFromTxt(String fileName,
                                                String symbol){
        List<Pilot> pilots = new ArrayList<>();
        BufferedReader inputFile = null;
        try{
            inputFile = new BufferedReader(new FileReader(new File(fileName)));
            String line = inputFile.readLine();
            while (line != null){
                pilots.add(LineParsePilot(line,symbol));
                line = inputFile.readLine();
            }
            return pilots;
        }
        catch (IOException fileError){
            System.out.println("PilotsListFromTxt");
            System.err.println("Error reading fileName");
            return pilots;
        }

    }

    //Method to create a Sorted File of users from a List.
    public static void UserTxtFromList(List<User> list,String fileName){
        List<User> userList = new ArrayList<>();
        BufferedWriter outputFile = null;
        try{
            outputFile = new BufferedWriter(new FileWriter(new File(fileName)));

            for (User user : list) {
                outputFile.write(user.toFormat("_"));
            }
            outputFile.close();
        }
        catch (IOException fileError){
            System.out.println("UserTxtFromList");
            System.err.println("Error fileName");
        }

    }

    //Method to create a File of Competitions from a List.
    public static void CompetitionTxtFromList(List<Competition> list,
                                              String fileName){
        List<User> userList = new ArrayList<>();
        BufferedWriter outputFile = null;
        try{
            outputFile = new BufferedWriter(new FileWriter(new File(fileName)));

            for (Competition competition : list) {
                outputFile.write(competition.toString());
            }
            outputFile.close();

        }
        catch (IOException fileError){
            System.out.println("CompetitionTxtFromLis");
            System.err.println("Error fileName CompetitionTxtFromList");
        }

    }
    public static void PilotTxtFromList(List<Pilot> list,String fileName){
        List<Pilot> userList = new ArrayList<>();
        BufferedWriter outputFile = null;
        try{
            outputFile = new BufferedWriter(new FileWriter(fileName));

            for (Pilot pilot : list) {
                outputFile.write(pilot.toFormat("_"));
            }
            outputFile.close();

        }
        catch (IOException fileError){
            System.out.println("PilotTxtFromList");
            System.err.println("Error fileName PilotTxtFromList");
        }

    }

    //Method to create a User from a String
    private static User LineParseUser(String line, String symbol){
        String[] splitLine = line.split(symbol);
        boolean isAdmin = false;
        if (splitLine[3].equals("Y") || splitLine[3].equals("y")){
            isAdmin = true;
        }
        else if(splitLine[3].equals("U") || splitLine[3].equals("u")){
            isAdmin = false;
        }
        return  new User(splitLine[0],splitLine[1],splitLine[2]
                ,isAdmin);
    }

    private static Pilot LineParsePilot(String line, String symbol){

        String[] splitLine = line.split(symbol);
        boolean isAdmin = false;
        if (splitLine[4].equals("Y") || splitLine[4].equals("y")){
            isAdmin = true;
        }
        else if(splitLine[4].equals("U") || splitLine[4].equals("u")){
            isAdmin = false;
        }
        return  new Pilot(splitLine[0],splitLine[1],splitLine[2],
                splitLine[3] ,isAdmin);
    }

    private static Competition LineParseCompetition(String line, String symbol){
        CompetitionType type = null;
        String[] splitLine = line.split(symbol);
        String typeString = splitLine[3];
        LocalDate date = LocalDate.parse(splitLine[5]);
        if(typeString.equals("RACE")){
            type = CompetitionType.RACE;
        }
        else{
            type = CompetitionType.FREESTYLE;
        }
        return new Competition(splitLine[1],splitLine[2],type,Integer.parseInt(splitLine[4]), date );
    }
}

