/*
 * Copyright (C) 2017 larryTheCoder
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.larryTheCoder.player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author larryTheCoder
 */
public class PlayerData implements Cloneable {

    public int homes;
    public int resetleft;
    public String playerName;
    public HashMap<String, Boolean> challengeList = new HashMap<>();
    public HashMap<String, Integer> challengeListTimes = new HashMap<>();
    public ArrayList<String> banList = new ArrayList<>();
    // Team Data
    public String teamLeader;
    public String teamIslandLocation;
    public boolean inTeam;
    public ArrayList<String> members = new ArrayList<>();
    public String name;
    public String leader;

    public PlayerData(String playerName, int homes, int resetleft) {
        this.playerName = playerName;
        this.homes = homes;
        this.resetleft = resetleft;
    }

    public PlayerData(String playerName, int homes, ArrayList<String> members, HashMap<String, Boolean> list, HashMap<String, Integer> times, boolean inTeam, String teamleader, String teamIslandloc, int resetleft, ArrayList<String> banList) {
        this.homes = homes;
        this.members = members;
        this.inTeam = inTeam;
        this.teamLeader = teamleader;
        this.teamIslandLocation = teamIslandloc;
        this.resetleft = resetleft;
        this.playerName = playerName;
        this.banList = banList;
    }

    /**
     * Checks if a challenge exists in the player's challenge list
     *
     * @param challenge
     * @return true if challenge is listed in the player's challenge list,
     * otherwise false
     */
    public boolean challengeExists(final String challenge) {
        return challengeList.containsKey(challenge.toLowerCase());
    }

    /**
     * Checks if a challenge is recorded as completed in the player's challenge
     * list or not
     *
     * @param challenge
     * @return true if the challenge is listed as complete, false if not
     */
    public boolean checkChallenge(final String challenge) {
        if (challengeList.containsKey(challenge.toLowerCase())) {
            // plugin.getLogger().info("DEBUG: " + challenge + ":" +
            // challengeList.get(challenge.toLowerCase()).booleanValue() );
            return challengeList.get(challenge.toLowerCase());
        }
        return false;
    }

    /**
     * Checks how many times a challenge has been done
     *
     * @param challenge
     * @return number of times
     */
    public int checkChallengeTimes(final String challenge) {
        if (challengeListTimes.containsKey(challenge.toLowerCase())) {
            // plugin.getLogger().info("DEBUG: check " + challenge + ":" +
            // challengeListTimes.get(challenge.toLowerCase()).intValue() );
            return challengeListTimes.get(challenge.toLowerCase());
        }
        return 0;
    }

    public HashMap<String, Boolean> getChallengeStatus() {
        return challengeList;
    }

    /**
     * Records the challenge as being complete in the player's list. If the
     * challenge is not listed in the player's challenge list already, then it
     * will be added.
     *
     * @param challenge
     */
    public void completeChallenge(final String challenge) {
        // plugin.getLogger().info("DEBUG: Complete challenge");
        challengeList.put(challenge.toLowerCase(), true);
        // Count how many times the challenge has been done
        int times = 0;
        if (challengeListTimes.containsKey(challenge.toLowerCase())) {
            times = challengeListTimes.get(challenge.toLowerCase());
        }
        times++;
        challengeListTimes.put(challenge.toLowerCase(), times);
        // plugin.getLogger().info("DEBUG: complete " + challenge + ":" +
        // challengeListTimes.get(challenge.toLowerCase()).intValue() );
    }

}
