import exception.IllegalArgumentException;
import exception.IllegalPlayerException;
import exception.InvalidNumberException;
import exception.InvalidPositionException;
import model.AFLPlayer;
import model.AFLTeam;
import model.AFLTeamMember;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

import static constant.Constant.*;

public class AFLMatch {
    private AFLTeam homeTeam;
    private AFLTeam awayTeam;
    private Integer homeGoals;
    private Integer homeBehinds;
    private Integer awayGoals;
    private Integer awayBehinds;

    public AFLMatch() {
        homeGoals = 0;
        homeBehinds = 0;
        awayGoals = 0;
        awayBehinds = 0;
    }

    public AFLTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(AFLTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public AFLTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(AFLTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getHomeBehinds() {
        return homeBehinds;
    }

    public void setHomeBehinds(Integer homeBehinds) {
        this.homeBehinds = homeBehinds;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Integer getAwayBehinds() {
        return awayBehinds;
    }

    public void setAwayBehinds(Integer awayBehinds) {
        this.awayBehinds = awayBehinds;
    }

    public Integer getHomeTeamTotalScores() {
        return (homeGoals * AFL_GOAL_FACTOR) + homeBehinds;
    }

    public Integer getAwayTeamTotalScores() {
        return (awayGoals * AFL_GOAL_FACTOR) + awayBehinds;
    }

    public String getCurrentScore() {
        final String str = "The current score %s.%s (%s) to %s.%s (%s).";
        return String.format(str,
                homeGoals,
                homeBehinds,
                getHomeTeamTotalScores(),
                awayGoals,
                awayBehinds,
                getAwayTeamTotalScores());
    }

    public String result() {
        final String str = "%s %s.%s (%s) %s %s %s.%s (%s).";
        if (getHomeTeamTotalScores() > getAwayTeamTotalScores()) {
            return String.format(str,
                    homeTeam.getName(),
                    homeGoals,
                    homeBehinds,
                    getHomeTeamTotalScores(),
                    "defeated",
                    awayTeam.getName(),
                    awayGoals,
                    awayBehinds,
                    getAwayTeamTotalScores());
        } else if (getAwayTeamTotalScores() > getHomeTeamTotalScores()) {
            return String.format(str,
                    awayTeam.getName(),
                    awayGoals,
                    awayBehinds,
                    getAwayTeamTotalScores(),
                    "defeated",
                    homeTeam.getName(),
                    homeGoals,
                    homeBehinds,
                    getHomeTeamTotalScores());
        }
        return String.format(str,
                homeTeam.getName(),
                homeGoals,
                homeBehinds,
                getHomeTeamTotalScores(),
                "drew with",
                awayTeam.getName(),
                awayGoals,
                awayBehinds,
                getAwayTeamTotalScores());
    }

    @Override
    public String toString() {
        return "AFLMatch{" +
                "homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeGoals=" + homeGoals +
                ", homeBehinds=" + homeBehinds +
                ", awayGoals=" + awayGoals +
                ", awayBehinds=" + awayBehinds +
                '}';
    }

    /**
     * Load team details from text file.
     *
     * @param lineupFileName                    the txt file name.
     * @return                                  the loaded team {@link AFLTeam}
     * @throws IOException
     * @throws IllegalPlayerException
     * @throws InvalidPositionException
     * @throws InvalidNumberException
     */
    private AFLTeam loadLineup(final String lineupFileName) throws IOException, IllegalPlayerException, InvalidPositionException, InvalidNumberException {
        AFLTeam aflTeam = new AFLTeam();
        LinkedList<AFLTeamMember> lineup = new LinkedList<>();

        // Example: "10, Easton Wood, FB" is separated by ", "
        final String separator = ", ";
        InputStream is = AFLMatch.class.getClassLoader().getResourceAsStream(lineupFileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        int lineCount = 1;
        do {
            line = bufferedReader.readLine();
            if (line != null) {
                String[] split = line.split(separator);
                String fullName;
                String position;
                int number;

                AFLPlayer aflPlayer = new AFLPlayer();
                if (lineCount == 1) { // Team name
                    aflTeam.setName(line);
                    lineCount++;
                    continue;
                } else if (lineCount == 2) { // Coach
                    fullName = split[0];
                    position = split[1];
                } else { // Players
                    number = Integer.valueOf(split[0]);
                    fullName = split[1];
                    position = split[2];

                    aflPlayer.setNumber(number);
                }

                // 4, Marcus Bontempelli, FOL, c -> see this line in WesternBulldogs.txt file
                // which indicates this player is also the captain (c)
                if (split.length >= 4) { // Set captain
                    aflPlayer.setCaptain(true);
                }

                split = fullName.split(" "); // Split full name into first and last name
                aflPlayer.setFirstName(split[0]);
                aflPlayer.setLastName(split[1]);
                aflPlayer.setPosition(position);

                if (aflTeam.getCoach() == null) {
                    aflTeam.setCoach(aflPlayer);
                } else {
                    lineup.add(aflPlayer);
                }

                lineCount++;
            }
        } while (line != null);

        if (lineup.size() != AFL_VALID_NUMBER_OF_PLAYERS) {
            throw new IllegalPlayerException(String.format(AFL_ILLEGAL_PLAYERS, lineup.size()));
        }

        aflTeam.setLineup(lineup);

        if (homeTeam == null) {
            homeTeam = aflTeam;
        } else {
            awayTeam = aflTeam;
        }
        return aflTeam;
    }

    public static void main(String[] args) {
        AFLMatch aflMatch = new AFLMatch();
        try {
            int numberOfArguments = args.length;
            // Check the number of arguments
            if (numberOfArguments != 2) {
                throw new IllegalArgumentException(String.format(COMMAND_LINE_ILLEGAL_ARGUMENTS, numberOfArguments));
            }

            // Load line up
            String homeTeamFileName = args[0];
            String awayTeamFileName = args[1];
            aflMatch.loadLineup(homeTeamFileName);
            aflMatch.loadLineup(awayTeamFileName);

            // Start the match
            Scanner sc = new Scanner(System.in);
            String scoredTeam;
            String scoreType;
            do {
                System.out.println("Which team scored? ");
                scoredTeam = sc.nextLine();
                if ("f".equals(scoredTeam)) {
                    break;
                }
                System.out.println("Goal or behind? "  + sc.nextLine());
                scoreType = sc.nextLine();

                // Score handling
                Integer homeGoals = aflMatch.getHomeGoals();
                Integer homeBehinds = aflMatch.getHomeBehinds();
                Integer awayGoals = aflMatch.getAwayGoals();
                Integer awayBehinds = aflMatch.getAwayBehinds();
                if ("h".equals(scoredTeam) && "g".equals(scoreType)) {
                    aflMatch.setHomeGoals(++homeGoals);
                } else if ("h".equals(scoredTeam) && "b".equals(scoreType)) {
                    aflMatch.setHomeBehinds(++homeBehinds);
                } else if ("a".equals(scoredTeam) && "g".equals(scoreType)) {
                    aflMatch.setAwayGoals(++awayGoals);
                } else if ("a".equals(scoredTeam) && "b".equals(scoreType)) {
                    aflMatch.setAwayBehinds(++awayBehinds);
                }

                System.out.println(aflMatch.getCurrentScore());
            } while (!"f".equals(scoredTeam));
            System.out.println("FULL TIME");
            System.out.println(aflMatch.result());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
