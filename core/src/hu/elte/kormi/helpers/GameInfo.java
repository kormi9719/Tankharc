package hu.elte.kormi.helpers;

import hu.elte.kormi.database.DatabaseHandler;

public class GameInfo {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 1200;
    public static final int COMMON_GAMBLING_RARITY = 84;
    public static final int RARE_GAMBLING_RARITY = 15;
    public static final int ULTRA_RARE_GAMBLING_RARITY = 1;
    public static final int SEASON = DatabaseHandler.getSeason();
    public static final int PPM = 100;

}
