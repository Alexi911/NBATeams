package com.example.nba.presentation.model;


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

