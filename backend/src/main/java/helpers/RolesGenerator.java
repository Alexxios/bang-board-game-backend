package helpers;

import cards.Role;

import java.util.*;

public class RolesGenerator {
    public static List<Role> generateRoles(int playersCount){
        ArrayList<Role> result = new ArrayList<Role>();
        result.add(Role.Sheriff);
        result.add(Role.Assistant);
//        result.add(Role.Bandit); // TODO fix this
//        result.add(Role.Bandit);
        Collections.shuffle(result);
        return result;
    }
}
