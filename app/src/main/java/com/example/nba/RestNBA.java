package com.example.nba;


import com.example.nba.Bulls.BullsPlayers;
import com.example.nba.Cavaliers.CavaliersPlayers;
import com.example.nba.Lakers.LakersPlayers;
import com.example.nba.Warriors.WarriorsPlayers;

import java.util.List;

public class RestNBA {

    private List<WarriorsPlayers> warriors_players;
    private List<CavaliersPlayers> cavaliers_players;
    private List<LakersPlayers> lakers_players;
    private List<BullsPlayers> bulls_players;

    public List<WarriorsPlayers> getWarriors_players() {
        return warriors_players;
    }

    public List<CavaliersPlayers> getCavaliers_players() {
        return cavaliers_players;
    }

    public List<LakersPlayers> getLakers_players() {
        return lakers_players;
    }

    public List<BullsPlayers> getBulls_players() {
        return bulls_players;
    }
}

