package com.example.instalacionnueva.data;

import java.io.Serializable;

public class Pilot extends User implements Serializable{
    private String nick;
    private int pointsFree;
    private int pointsRace;

    public Pilot(String nick,String nameSurname,String login,
                 String password,boolean isAdmin){
        super(nameSurname,login,
                password,isAdmin);
        this.nick = nick;
        this.pointsFree = 0;
        this.pointsRace = 0;
    }

    @Override
    public String toString() {
        return nick + " Points free: " + pointsFree + " Points race: " + pointsRace + "\n" ;
    }

    public String toStringByCompetitionType(CompetitionType type){
        if(type == CompetitionType.RACE){
            return nick + " : " + pointsRace + " pts";
        }else{
            return nick + " : " + pointsFree + " pts";
        }
    }

    public String toFormatPilot(String symbol) {
        if (super.isAdmin() == true){
            return super.getNameSurname() /*+ symbol + this
            .nick*/ + symbol + super.getLogin()+ symbol + super.getPassword() + symbol +
                    "y" +  this.nick + symbol + "\n" ;
        }
        else{
            return super.getNameSurname() /*+ symbol + this
            .nick*/ + symbol + super.getLogin()+ symbol + super.getPassword() + symbol +
                    "n" +  this.nick + symbol + "\n" ;
        }
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPointsFree() {
        return pointsFree;
    }

    public void setPointsFree(int pointsFree) {
        this.pointsFree = pointsFree;
    }

    public int getPointsRace() {
        return pointsRace;
    }

    public void setPointsRace(int pointsRace) {
        this.pointsRace = pointsRace;
    }




   /* public int compareTo(Pilot p) {
        if(this.pointsFree > p.pointsFree ){
            return 1;
        } else if (this.pointsFree == p.pointsFree ) {
            return 0;
        }else{
            return -1;
        }
    }*/
}
