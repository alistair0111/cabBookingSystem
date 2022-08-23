package Repository;

import Model.Driver;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DriverRepository {
    @Getter
    private Map<String, Driver> driverMap;
    private static DriverRepository driverRepository;

    public static DriverRepository getInstance(){
        if(driverRepository==null)
            driverRepository = new DriverRepository();
        return driverRepository;
    }

    private DriverRepository(){
        driverMap = new HashMap<>();
    }

    public void addDriver(Driver driver){
        driverRepository.driverMap.put(driver.getName(), driver);
    }
}
