package helpers;

import characters.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharactersGenerator {
    public static List<Character> generateCharacters(int playersCount){
        ArrayList<Character> characters = new ArrayList<>(List.of(Character.values()));
        Collections.shuffle(characters);
        return characters.stream().limit(playersCount).toList();
    }
}
