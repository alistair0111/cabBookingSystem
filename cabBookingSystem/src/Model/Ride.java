package Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ride {
    private Driver driver;
    private User user;
    private Location fromLocation;
    private Location toLocation;

    private int rideCost;

    //Constructor without rideCost
    public Ride(Driver driver, User user, Location fromLocation, Location toLocation){
        this.driver = driver;
        this.user = user;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

}
