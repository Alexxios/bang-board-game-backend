package game_controllers;

class GameNumberGenerator{
    private static int gameNumber = 0;

    public static int getGameNumber(){
        ++gameNumber;
        return gameNumber;
    }
}

public class GameCreator {
    public static int createGame(){
        int gameNumber = GameNumberGenerator.getGameNumber();
        // add to database
        return gameNumber;
    }
}
