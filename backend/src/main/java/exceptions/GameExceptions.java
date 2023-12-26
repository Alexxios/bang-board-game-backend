package exceptions;

public class GameExceptions {
    public static class FullGame extends Exception{}
    public static class PlayerAlreadyInGame extends Exception{}
    public static class CanNotJoinGame extends Exception{}
}
