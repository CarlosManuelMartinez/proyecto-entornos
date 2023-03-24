package com.example.instalacionnueva.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionTest {
    Competition competition = new Competition("NameTest","CityTest",
            CompetitionType.FREESTYLE,1);

    @Test
    void addParticipant() {
        Pilot participant = new Pilot("Drib","Juan Martinez","Juan",
                "1234 ",false);

        boolean result = this.competition.addParticipant(participant);
        assertTrue(result);

        List<Pilot> listPilots = competition.getParticipants();
        int numParticipants = listPilots.size();
        assertEquals(1,numParticipants);

    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.now();
        String format = "Competition_NameTest_CityTest_FREESTYLE_1_"+ date+
                "\n";
        String compString = this.competition.toString();
        assertEquals(format,compString);
    }



    @Test
    void getName() {
        String name = this.competition.getName();
        assertEquals("NameTest",name);
    }

    @Test
    void setName() {
        this.competition.setName("AlternativeName");
        String nameResult = competition.getName();
        assertEquals("AlternativeName",nameResult);
    }

    @Test
    void getCity() {
        String name = this.competition.getCity();
        assertEquals("CityTest",name);
    }

    @Test
    void setCity() {
        this.competition.setCity("AlternativeNameCity");
        String nameResult = competition.getCity();
        assertEquals("AlternativeNameCity",nameResult);
    }

    @Test
    void getCompetitionType() {
        CompetitionType type = this.competition.getCompetitionType();
        assertEquals(CompetitionType.FREESTYLE,type);
    }

    @Test
    void setCompetitionType() {
        this.competition.setCompetitionType(CompetitionType.RACE);
        CompetitionType type = competition.getCompetitionType();
        assertEquals(CompetitionType.RACE,type);
    }

    @Test
    void getMaxParticipants() {
        int maxParticipants = competition.getMaxParticipants();
        assertEquals(1,maxParticipants);
    }

    @Test
    void setMaxParticipants() {
        competition.setMaxParticipants(2);
        int maxReturn = competition.getMaxParticipants();
        assertEquals(2,maxReturn);
    }

    @Test
    void getParticipants() {
        List<Pilot> list = competition.getParticipants();
        assertNotNull(this.competition.getParticipants());
    }

    @Test
    void setParticipants() {
        List<Pilot> listTest = new ArrayList<>();
        this.competition.setParticipants(listTest);
        assertEquals(listTest,this.competition.getParticipants());
    }

    @Test
    void isFinished() {
        boolean testBool = false;
        assertEquals(testBool,this.competition.isFinished());
    }

    @Test
    void setFinished() {
        this.competition.setFinished(true);
        boolean testBool = this.competition.isFinished();
        assertEquals(true,testBool);
    }


}