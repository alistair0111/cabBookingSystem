package Repository;

import Model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    @Getter
    private Map<String, User> userMap;
    private static UserRepository userRepository = null;

    public static UserRepository getInstance(){
        if(userRepository==null)
            userRepository = new UserRepository();
        return userRepository;
    }

    private UserRepository(){
        userMap = new HashMap<>();
    }

    public void addUser(User user){
        userRepository.userMap.put(user.getName(), user);
    }
}
