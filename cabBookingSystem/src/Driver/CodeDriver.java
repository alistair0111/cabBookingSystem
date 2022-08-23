package Driver;


import Model.Driver;
import Model.Gender;
import Model.Location;
import Model.Vehicle;
import Services.CabBookingService;
import Services.DriverService;
import Services.Exceptions.DriverAlreadyExists;
import Services.Exceptions.DriverNotAvailableException;
import Services.Exceptions.DriverNotFoundException;
import Services.RideService;
import Services.UserService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

//Driver Class
public class CodeDriver {

    public static void main(String[] args) {
        //Service Objects
        CabBookingService cabBookingService = CabBookingService.getInstance();
        DriverService driverService = DriverService.getInstance();
        RideService rideService = RideService.getInstance();
        UserService userService = UserService.getInstance();

        //Adding users
        try{
            userService.addNewUser("Abhishek", 23, Gender.MALE, new Location(0,0));
            userService.addNewUser("Rahul", 29, Gender.MALE, new Location(10,0));
            userService.addNewUser("Nandini", 22, Gender.FEMALE, new Location(15,6));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        //Adding drivers
        try{
            Location driver1 = new Location(10, 1);
            Vehicle vehicle1 = new Vehicle("Swift", "KA-01-12345");
            driverService.addDriver("Driver1", 22, Gender.MALE, driver1, vehicle1, true);

            Location driver2 = new Location(11, 10);
            Vehicle vehicle2 = new Vehicle("Swift", "KA-01-12345");
            driverService.addDriver("Driver2", 29, Gender.MALE, driver2, vehicle2, true);

            Location driver3 = new Location(5, 3);
            Vehicle vehicle3 = new Vehicle("Swift", "KA-01-12345");
            driverService.addDriver("Driver3", 24, Gender.MALE, driver3, vehicle3, true);
        } catch (DriverAlreadyExists e) {
            System.out.println(e.getMessage());
        }


//        System.out.println(driverService.getDriverRepository().getDriverMap());
//        System.out.println(userService.getUserRepository().getUserMap());

        //finding a ride
        List<Driver> driverList;
        try {
            System.out.println("Requesting a cab for Abhishek");
            cabBookingService.findRides("Abhishek", new Location(0,0), new Location(20,1));
        } catch (DriverNotAvailableException e) {
            System.out.println(e.getMessage());
        }
        try{
            System.out.println("Requesting a cab for Rahul");
            driverList = cabBookingService.findRides("Rahul", new Location(10,0), new Location(15,3));
            System.out.println("Available drivers are");
            for(Driver driver: driverList){
                System.out.println(driver.getName());
            }
        } catch (DriverNotAvailableException e) {
            System.out.println(e.getMessage());
        }


        //choosing a ride
        System.out.println("Choosing a cab with driver Driver1");
        cabBookingService.chooseRide("Rahul", "Driver1", new Location(10,0), new Location(15,3));

        //calculate bill
        System.out.println("Calculating Bill for Rahul...");
        System.out.println("Ride total "+cabBookingService.calculateBill("Rahul")+"$");


        //change driver status
        try {
            System.out.println("Driver1 status change requested");
            driverService.changeDriverStatus("Driver1", false);
            System.out.println("Driver1 status changed to unavailable");
        }catch (DriverNotFoundException e){
            System.out.println(e.getMessage());
        }


        //find a ride
        try{
            System.out.println("Requesting a cab for Nandini");
            driverList = cabBookingService.findRides("Nandini", new Location(15,6), new Location(20,4));
            System.out.println("Available drivers are");
            for(Driver driver: driverList){
                System.out.println(driver.getName());
            }
        } catch (DriverNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        try{
            System.out.println("Listing all cabdriver earnings");
            Map<String, Integer> driverEarnings = driverService.findTotalDriverEarnings();
            for(Map.Entry<String, Integer> driver : driverEarnings.entrySet()){
                System.out.println(driver.getKey()+" earned "+driver.getValue()+"$");
            }
        }catch (DriverNotFoundException e){
            System.out.println(e.getMessage());
        }
    }


}
