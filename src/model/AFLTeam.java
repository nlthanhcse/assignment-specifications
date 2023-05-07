package model;

import java.util.LinkedList;

public class AFLTeam {
    private String name;
    private AFLTeamMember coach;
    private LinkedList<AFLTeamMember> lineup; // Use LinkedList to remain the player orders from txt file

    public AFLTeam() {
    }

    public AFLTeam(String name, AFLTeamMember coach, LinkedList<AFLTeamMember> lineup) {
        this.name = name;
        this.coach = coach;
        this.lineup = lineup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AFLTeamMember getCoach() {
        return coach;
    }

    public void setCoach(AFLTeamMember coach) {
        this.coach = coach;
    }

    public LinkedList<AFLTeamMember> getLineup() {
        return lineup;
    }

    public void setLineup(LinkedList<AFLTeamMember> lineup) {
        this.lineup = lineup;
    }

    @Override
    public String toString() {
        return "AFLTeam{" +
                "name='" + name + '\'' +
                ", coach=" + coach +
                ", lineup=" + lineup +
                '}';
    }
}
