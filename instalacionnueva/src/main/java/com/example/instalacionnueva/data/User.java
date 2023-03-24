package com.example.instalacionnueva.data;

import java.io.Serializable;
import java.util.Comparator;

    public class User implements  Serializable {
        private String nameSurname;
        private String login;
        private String password;
        private boolean isAdmin;

        public User(String nameSurname, String login, String password, boolean isAdmin) {
            this.nameSurname = nameSurname;
            //this.nick = nick;
            this.login = login;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        @Override
        public String toString() {
            if (this.isAdmin == true) {
                return "Name: " + this.nameSurname + /*" Nick: " + "\"" + this
            .nick +*/ "\"" + " " + "Admin";
            } else {
                return "Name: " + this.nameSurname + /*" Nick: " + "\"" + this
            .nick +*/ "\"" + " Pilot";
            }
        }

        public String toFormat(String symbol) {
            if (this.isAdmin == true) {
                return this.nameSurname /*+ symbol + this.nick*/ + symbol + this.login + symbol + this.password + symbol + "y" + "\n";
            } else {
                return this.nameSurname /*+ symbol + this.nick*/ + symbol + this.login + symbol + this.password + symbol + "n" + "\n";
            }
        }

        public String getNameSurname() {
            return nameSurname;
        }

        public void setNameSurname(String nameSurname) {
            this.nameSurname = nameSurname;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public String compare(User user) {
            return getPassword();
        }
    }





