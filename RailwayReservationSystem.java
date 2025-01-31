

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// User class to manage user details
class User {
    private String username;
    private String password;
    private String email;

    public class User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean validateLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }
}

// Train class to manage train details
class Train {
    private String trainName;
    private int availableSeats;
    private double fare;
    private String route;

    public Train(String trainName, int availableSeats, double fare, String route) {
        this.trainName = trainName;
        this.availableSeats = availableSeats;
        this.fare = fare;
        this.route = route;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public double getFare() {
        return fare;
    }

    public String getRoute() {
        return route;
    }
}

// Main railway reservation system
public class RailwayReservationSystem {
    private static List<User> users = new ArrayList<>();
    private static List<Train> trains = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();

        while (true) {
            System.out.println("\nWelcome to the Railway Reservation System!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    forgotPassword();
                    break;
                case 4:
                    System.out.println("Exiting the system. Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeData() {
        // Sample train data with routes
        trains.add(new Train("Express 1", 100, 50.0, "Route A"));
        trains.add(new Train("Express 2", 0, 60.0, "Route A")); // No seats available
        trains.add(new Train("Express 3", 50, 70.0, "Route B"));
        trains.add(new Train("Express 4", 120, 40.0, "Route A"));
        trains.add(new Train("Express 5", 30, 90.0, "Route C"));

        // Sample user data
        users.add(new User("user1", "password123", "example@.com"));
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean found = false;
        for (User user : users) {
            if (user.validateLogin(username, password)) {
                System.out.println("Login successful. Welcome " + username);
                found = true;
                bookTicket();
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void register() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password, email));
        System.out.println("Registration successful. You can now log in.");
    }

    private static void forgotPassword() {
        System.out.print("Enter your registered email: ");
        String email = scanner.nextLine();

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.print("Enter a new password: ");
                String newPassword = scanner.nextLine();
                user.resetPassword(newPassword);
                System.out.println("Your password has been reset successfully. You can now log in.");
                return;
            }
        }

        System.out.println("No account found with this email.");
    }

    private static void bookTicket() {
        System.out.println("\nAvailable Trains:");
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            System.out.println((i + 1) + ". " + train.getTrainName() +
                    " - Seats: " + train.getAvailableSeats() +
                    " - Fare: $" + train.getFare() +
                    " - Route: " + train.getRoute());
        }

        System.out.print("Select a train to book (1-" + trains.size() + "): ");
        int trainChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (trainChoice < 1 || trainChoice > trains.size()) {
            System.out.println("Invalid train selection.");
            return;
        }

        Train selectedTrain = trains.get(trainChoice - 1);

        if (selectedTrain.getAvailableSeats() > 0) {
            selectedTrain.bookSeat();
            System.out.println("Booking successful! Remaining seats: " + selectedTrain.getAvailableSeats());
        } else {
            System.out.println("Seats are not available on this train.");

            // Find alternative trains on the same route
            boolean alternativeFound = false;
            System.out.println("Available trains on the same route:");

            for (Train train : trains) {
                if (train.getRoute().equals(selectedTrain.getRoute()) && train.getAvailableSeats() > 0) {
                    System.out.println(train.getTrainName() + " - Seats: " + train.getAvailableSeats() +
                            " - Fare: $" + train.getFare());
                    alternativeFound = true;
                }
            }

            if (!alternativeFound) {
                System.out.println("No other trains available on the same route.");
            }
        }
    }
}