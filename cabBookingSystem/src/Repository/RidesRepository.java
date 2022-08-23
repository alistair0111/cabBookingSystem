package Repository;

import Model.Ride;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RidesRepository {

    @Getter
    private HashMap<String, List<Ride>> allDriverRides;
    @Getter
    private HashMap<String, List<Ride>> allUserRides;
    public static RidesRepository ridesRepository;

    private RidesRepository(){
        allDriverRides = new HashMap<>();
        allUserRides = new HashMap<>();
    }

    public static RidesRepository getInstance(){
        if(ridesRepository==null)
            ridesRepository = new RidesRepository();
        return ridesRepository;
    }

    public void addRide(String driverUsername, String riderName, Ride newRide){
        if(ridesRepository.allDriverRides.containsKey(driverUsername)){
            List<Ride> driverRides = ridesRepository.allDriverRides.get(driverUsername);
            driverRides.add(newRide);
            ridesRepository.allDriverRides.put(driverUsername, driverRides);
        }else{
            List<Ride> newRideList = new ArrayList<>();
            newRideList.add(newRide);
            ridesRepository.allDriverRides.put(driverUsername, newRideList);
        }

        if(ridesRepository.allUserRides.containsKey(riderName)){
            List<Ride> userRides = allUserRides.get(riderName);
            userRides.add(newRide);
            ridesRepository.allUserRides.put(riderName, userRides);
        }else{
            List<Ride> newRideList = new ArrayList<>();
            newRideList.add(newRide);
            ridesRepository.allUserRides.put(riderName, newRideList);
        }
    }

    public void updateRideFare(String userName, int fare){
        List<Ride> getUserRides = ridesRepository.allUserRides.get(userName);
        getUserRides.get(getUserRides.size()-1).setRideCost(fare);
        String driverName = getUserRides.get(getUserRides.size()-1).getDriver().getName();
        List<Ride> getDriverRides = ridesRepository.allDriverRides.get(driverName);
        getDriverRides.get(getDriverRides.size()-1).setRideCost(fare);
    }
}
