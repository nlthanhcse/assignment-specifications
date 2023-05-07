package model;

import exception.InvalidPositionException;

import java.util.Arrays;
import java.util.List;

import static constant.Constant.*;

public class AFLTeamMember {
    private final List<String> VALID_POSITIONS = Arrays.asList(
            "FB",
            "HB",
            "C",
            "HF",
            "FF",
            "FOL",
            "IC",
            "COACH"
    );
    private String firstName;
    private String lastName;
    private String position;

    public AFLTeamMember() {
    }

    public AFLTeamMember(String firstName, String lastName, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) throws InvalidPositionException {
        if (!VALID_POSITIONS.contains(position)) {
            throw new InvalidPositionException(String.format(AFL_INVALID_POSITION, position));
        }
        this.position = position;
    }

    @Override
    public String toString() {
        final String str = "%s %s, %s";
        return String.format(str,
                firstName,
                lastName,
                position);
    }
}
