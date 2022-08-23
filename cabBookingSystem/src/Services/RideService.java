package Services;

import Model.Driver;
import Model.Location;
import Model.Ride;
import Model.User;
import Repository.RidesRepository;
import Services.Exceptions.DriverNotFoundException;
import Services.Exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public class RideService {

    private RidesRepository ridesRepository = RidesRepository.getInstance();
    private static DriverService driverService = DriverService.getInstance();
    private static UserService userService = UserService.getInstance();

    private RideService(){}

    public static RideService getInstance(){
        return new RideService();
    }

    public RidesRepository getRidesRepository(){
        return RidesRepository.getInstance();
    }

    public void addRide(Driver driver, User user, Location source, Location destination) throws DriverNotFoundException, UserNotFoundException {
        if(!driverService.getDriverRepository().getDriverMap().containsKey(driver.getName()))
            throw new DriverNotFoundException("Driver ride history not present");
        if(!userService.getUserRepository().getUserMap().containsKey(user.getName())) {
            throw new UserNotFoundException("User ride history not present");
        }
        Ride newRide = new Ride(driver, user, source, destination);
        ridesRepository.addRide(driver.getName(), user.getName(), newRide);

    }

    public void updateDriverEarning(String driverName, int fare){
        driverService.updateDriverEarning(driverName, fare);
    }

    public void updateRideFare(String userName, int fare){
        ridesRepository.updateRideFare(userName, fare);
    }

    public Ride getCurrentRide(String userName) {
        List<Ride> userRides = ridesRepository.getAllUserRides().get(userName);
        return userRides.get(userRides.size()-1);
    }
}
