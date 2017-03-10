package de.sarbot.timerun.Screens;

import de.sarbot.timerun.TimeRun;

/**
 * Created by sarbot on 09.03.17.
 */
public class GameSave {
    int level;
    int[] scores;
    TimeRun game;

    public GameSave(TimeRun gam){
        game = gam;
        level = game.level;
        scores = game.levelSolves;

    }

}
