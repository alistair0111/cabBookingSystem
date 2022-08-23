package Services;

import Model.Gender;
import Model.Location;
import Model.User;
import Repository.UserRepository;
import Services.Exceptions.UserExistsException;
import Services.Exceptions.UserNotFoundException;

public class UserService {


    public static UserService userService = null;
    private UserRepository userRepository = UserRepository.getInstance();

    private UserService(){}

    public static UserService getInstance(){
        userService = new UserService();
        return userService;
    }

    public UserRepository getUserRepository(){
        return UserRepository.getInstance();
    }

    public void addNewUser(String userName, int age, Gender gender, Location userLocation) throws UserExistsException {
        User newUser = new User(userName, age, gender, userLocation);
        if(userRepository.getUserMap().containsKey(userName))
            throw new UserExistsException();
        else
            userRepository.addUser(newUser);
    }

    public void updateUser(String userName, User updatedUser) throws UserNotFoundException {
        if(userRepository.getUserMap().containsKey(userName))
            userRepository.getUserMap().put(userName, updatedUser);
        else
            throw new UserNotFoundException();
    }

    public void updateUserLocation(String userName, Location location) throws UserNotFoundException{
        if(userRepository.getUserMap().containsKey(userName)){
            User userToUpdate = userRepository.getUserMap().get(userName);
            userToUpdate.setLocation(location);
            userRepository.getUserMap().put(userName, userToUpdate);
        }else{
            throw new UserNotFoundException();
        }

    }
}
