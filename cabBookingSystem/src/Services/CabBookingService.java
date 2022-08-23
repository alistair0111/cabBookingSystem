package Services;

import Model.Driver;
import Model.Location;
import Model.Ride;
import Model.User;
import Services.Exceptions.DriverNotAvailableException;
import Services.Exceptions.DriverNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CabBookingService {

    private static final int MAX_DISTANCE = 5;
    private static DriverService driverService = DriverService.getInstance();
    private static UserService userService = UserService.getInstance();
    private static RideService rideService = RideService.getInstance();
    private CabBookingService(){}

    public static CabBookingService getInstance(){
        return new CabBookingService();
    }

    public List<Driver> findRides(String userName, Location source, Location destination) throws DriverNotAvailableException {
        List<Driver> allDriversAvailable = new ArrayList<>();
//                driverService.getDriverRepository().getDriverMap().values().stream()
//                .filter(Driver->{
//                    if(Driver.isAvailable() && (Math.abs(Driver.getLocation().getLongitude()- source.getLongitude())
//                       + Math.abs(Driver.getLocation().getLatitude()- source.getLatitude()))
//                        <= MAX_DISTANCE){
//                        return true;
//                    }
//                    return false;
//                }).toList();
        for(Map.Entry<String, Driver> driverEntries: driverService.getDriverRepository().getDriverMap().entrySet()){
            Location driverLocation = driverEntries.getValue().getLocation();
            boolean availability =driverEntries.getValue().isAvailable();
            int calculatedDistance = Math.abs(driverLocation.getLongitude()-source.getLongitude()) + Math.abs(driverLocation.getLatitude() - source.getLatitude());
            if(availability && calculatedDistance<=MAX_DISTANCE ){
                allDriversAvailable.add(driverEntries.getValue());
            }
        }
        if(allDriversAvailable.size()==0){
            throw new DriverNotAvailableException("No driver found in your area");
        }
        return allDriversAvailable;
    }


    public void chooseRide(String userName, String driverName, Location source, Location destination){
        Driver driverBooked = driverService.getDriverRepository().getDriverMap().get(driverName);
        User userRider = userService.getUserRepository().getUserMap().get(userName);
        try{
            driverService.changeDriverStatus(driverName, false);
            driverService.updateDriverLocation(driverName, destination);
            userService.updateUserLocation(userName, destination);
            rideService.addRide(driverBooked, userRider, source, destination);
        }catch (Exception e){
            e.getMessage();
        }
    }

    public int calculateBill(String userName){

        Ride currentUserRide = rideService.getCurrentRide(userName);
        int rideCost = Math.abs(currentUserRide.getFromLocation().getLongitude()-currentUserRide.getToLocation().getLongitude())+
                Math.abs(currentUserRide.getFromLocation().getLatitude()-currentUserRide.getToLocation().getLatitude());
        try{
            driverService.updateDriverLocation(currentUserRide.getDriver().getName(), currentUserRide.getToLocation());
            userService.updateUserLocation(userName, currentUserRide.getToLocation());
            driverService.changeDriverStatus(currentUserRide.getDriver().getName(), true);
        }catch(Exception e){
            e.getMessage();
        }finally {
            rideService.updateDriverEarning(currentUserRide.getDriver().getName(), rideCost);
            rideService.updateRideFare(userName, rideCost);
        }

        return rideCost;
    }
}
