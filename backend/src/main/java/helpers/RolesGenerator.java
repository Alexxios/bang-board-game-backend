package helpers;

import cards.Role;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolesGenerator {

    public List<Role> generateRoles(int playersCount){
        ArrayList<Role> result = new ArrayList<Role>();
        switch (playersCount){
            case 2:
                result.add(Role.Sheriff);
                result.add(Role.Assistant);
                break;
            case 3:
                result.add(Role.Sheriff);
                result.add(Role.Assistant);
                result.add(Role.Bandit);
                break;
            case 4:
                result.add(Role.Sheriff);
                result.add(Role.Renegat);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
                break;
            case 5:
                result.add(Role.Sheriff);
                result.add(Role.Renegat);
                result.add(Role.Assistant);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
                break;
            case 6:
                result.add(Role.Sheriff);
                result.add(Role.Renegat);
                result.add(Role.Assistant);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
                break;
            case 7:
                result.add(Role.Sheriff);
                result.add(Role.Renegat);
                result.add(Role.Assistant);
                result.add(Role.Assistant);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
                result.add(Role.Bandit);
        }
        
        Collections.shuffle(result);
        return result;
    }
}
