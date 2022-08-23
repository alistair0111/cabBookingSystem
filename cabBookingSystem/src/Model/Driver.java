package Model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Driver extends User {


    private Vehicle vehicle;
    private boolean available;

    private int driverEarning = 0;

    public Driver(String name, int age, Gender gender, Location driverLocation, Vehicle vehicle, boolean available){
        super(name, age, gender, driverLocation);
        this.vehicle = vehicle;
        this.available = available;
    }
}
