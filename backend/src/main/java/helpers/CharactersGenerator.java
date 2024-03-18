package helpers;

import characters.Character;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CharactersGenerator {
    public List<Character> generateCharacters(int playersCount){
        ArrayList<Character> characters = new ArrayList<>(List.of(Character.values()));
        Collections.shuffle(characters);
        return characters.stream().limit(playersCount).toList();
    }
}
