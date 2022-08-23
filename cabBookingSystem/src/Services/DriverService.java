package Services;

import Model.*;
import Repository.DriverRepository;
import Services.Exceptions.DriverAlreadyExists;
import Services.Exceptions.DriverNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverService {
    private DriverRepository driverRepository = DriverRepository.getInstance();

    private DriverService(){}

    public static DriverService getInstance(){
        return new DriverService();
    }

    public DriverRepository getDriverRepository(){
        return DriverRepository.getInstance();
    }

    public void addDriver(String driverName, int age, Gender gender, Location driverLocation, Vehicle vehicle, boolean Available) throws DriverAlreadyExists {
        if(driverRepository.getDriverMap().containsKey(driverName)){
            throw new DriverAlreadyExists();
        }
        Driver newDriver = new Driver(driverName, age, gender, driverLocation, vehicle, Available);
        driverRepository.addDriver(newDriver);
    }

    public void updateDriverLocation(String driverName, Location location) throws DriverNotFoundException{
        if(driverRepository.getDriverMap().containsKey(driverName)){
            Driver driverLocationUpdate = driverRepository.getDriverMap().get(driverName);
            driverLocationUpdate.setLocation(location);
            driverRepository.getDriverMap().put(driverName, driverLocationUpdate);
        }
        else{
            throw new DriverNotFoundException("Driver already exists in DB");
        }
    }

    public void changeDriverStatus(String driverName, boolean available) throws DriverNotFoundException{
        if(driverRepository.getDriverMap().containsKey(driverName)) {
            Driver driverStatusUpdate = driverRepository.getDriverMap().get(driverName);
            driverStatusUpdate.setAvailable(available);
            driverRepository.getDriverMap().put(driverName, driverStatusUpdate);
        }else{
            throw new DriverNotFoundException("Driver already exists in DB");
        }
    }

    public void updateDriverEarning(String driverName, int fare) {
        Driver updatedDriverDetails = driverRepository.getDriverMap().get(driverName);
        int newEarning = updatedDriverDetails.getDriverEarning()+fare;
        updatedDriverDetails.setDriverEarning(newEarning);
        driverRepository.getDriverMap().put(driverName, updatedDriverDetails);
    }

    public Map<String, Integer> findTotalDriverEarnings() throws DriverNotFoundException{
        Map<String, Integer> driverEarningMap
                = driverRepository.getDriverMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, driver -> driver.getValue().getDriverEarning()));
        if(driverEarningMap.isEmpty()){
            throw new DriverNotFoundException("No drivers exists in DB");
        }
        return driverEarningMap;
    }
}
