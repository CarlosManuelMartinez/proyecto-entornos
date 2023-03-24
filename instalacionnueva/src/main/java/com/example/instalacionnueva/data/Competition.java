package com.example.instalacionnueva.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competition implements Serializable {
    private String name;
    private String city;
    private CompetitionType competitionType;
    private int maxParticipants;
    private List<Pilot> participants;
    private boolean finished;
    private LocalDate dateCreation;

    public Competition(String name, String city, CompetitionType competitionType, int maxParticipants) {
        this.name = name;
        this.city = city;
        this.competitionType = competitionType;
        this.maxParticipants = maxParticipants;
        this.participants = new ArrayList<>();
        this.finished = false;
        this.dateCreation = LocalDate.now();
    }

    public Competition(String name, String city, CompetitionType competitionType, int maxParticipants, LocalDate dateCreation) {
        this.name = name;
        this.city = city;
        this.competitionType = competitionType;
        this.maxParticipants = maxParticipants;
        this.participants = new ArrayList<>();
        this.finished = false;
        this.dateCreation = dateCreation;
    }



    public boolean addParticipant(Pilot userLogged) {
        boolean registered = false;
        if (this.participants.size() < this.maxParticipants) {
            this.participants.add(userLogged);
            registered = true;
        }
        else {
            Tools.alert("");
        }
        return registered;
    }

    @Override
    public String toString() {
        return "Competition_" + this.name + "_" + this.city + "_" + this.competitionType + "_"
                + this.maxParticipants + "_" + this.dateCreation + "\n";
    }

    public String toFormat() {
        return "Competition_" + this.name + "_" + this.city + "_" + this.competitionType + "_"
                + this.maxParticipants + "_" + this.dateCreation ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<Pilot> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Pilot> participants) {
        this.participants = participants;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


}