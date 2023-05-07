package model;

import constant.Constant;
import exception.InvalidNumberException;

public class AFLPlayer extends AFLTeamMember {
    private Integer number;
    private boolean captain;

    public AFLPlayer() {
    }

    public AFLPlayer(String firstName, String lastName, String position, Integer number, boolean captain) {
        super(firstName, lastName, position);
        this.number = number;
        this.captain = captain;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) throws InvalidNumberException {
        if (number < 0) {
            throw new InvalidNumberException(String.format(Constant.AFL_INVALID_NUMBER, number));
        }
        this.number = number;
    }

    public boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    @Override
    public String toString() {
        final String str = "[%s] %s %s, %s%s";
        return String.format(str,
                number,
                getFirstName(),
                getLastName(),
                getPosition(),
                captain ? "(c)" : "");
    }
}
