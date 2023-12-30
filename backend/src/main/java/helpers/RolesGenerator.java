package helpers;

import cards.Role;

import java.util.ArrayList;
import java.util.List;

public class RolesGenerator {
    public static List<Role> generateRoles(int playersCount){
        ArrayList<Role> result = new ArrayList<Role>();
        for (int i = 0; i < playersCount; ++i){
            result.add(Role.Sheriff);
        }
        return result;
    }
}
