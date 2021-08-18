//////////////////////////////////////////////
// Code by: Spencer Czarnezki               //
// Simulates a capsule hotel where the user //
// can add, remove, and view patrons.       //
//////////////////////////////////////////////
import java.util.Scanner;

public class CapsuleHotel {
    public static void main(String[] args) {
        int caps = startUp();
        displayMenu(caps);
    }


    public static int startUp() { // a method with initial text and for the user to set up how many capsules the hotel will have
        System.out.println("\nWelcome to the Super Capsule Hotel!");
        System.out.println("=".repeat(35));
        int caps;
        String unconverted;

        do { // do while loop to get the # of capsules
            System.out.print("How many capsules are available?: ");
            unconverted = scannerHelper();
            caps = properInput(unconverted);
        } while (unconverted.isBlank() || caps < 1);

        System.out.printf("%nThere are %d unoccupied capsules in the hotel ready to be booked.%n", caps);
        return caps;
    }


    public static void displayMenu(int caps) { // method that displays the menu, takes user input, and takes action based on that input
        boolean ending = false;
        String[] capsules = new String[caps];

        do {
            System.out.println("\nGuest Menu");
            System.out.println("=".repeat(10));
            System.out.println("1. Check In a Guest");
            System.out.println("2. Check Out a Capsule");
            System.out.println("3. View Nearby Guests");
            System.out.println("4. Exit");

            String unconverted;
            int hold;

            do { // do while loop to get the menu option chosen
                System.out.print("Choose an option [1-4]: ");
                unconverted = scannerHelper();
                hold = properInput(unconverted);
            } while (unconverted.isBlank() || (hold < 1) || (hold > 4));

            switch (hold) {
                case 1: // Check In
                    capsules = displayCheckIn(capsules, caps);
                    break;
                case 2: // Check Out
                    capsules = displayCheckOut(capsules, caps);
                    break;
                case 3: // View Guests
                    displayGuests(capsules, caps);
                    break;
                case 4: // Exit
                    ending = displayExit();
                    break;
                default:
            }
        } while (!ending);
    }


    public static String[] displayCheckIn(String[] capsules, int caps) { // method to let user add guests names to capsules
        System.out.println("\nGuest Check In");
        System.out.println("=".repeat(14));
        boolean check = false;
        String name;

        do { // do while loop to get guest name from user
            System.out.print("Guest Name (type R to return to the menu): ");
            name = scannerHelper();

            if (name.equals("R")) {
                return capsules;
            }
        } while (name.isBlank());

        do { // do while loop to get the capsule number, ensure that capsule assigned is not already filled, and put it into the array
            String room = roomFinder(caps);

            if (room.equals("R")) {
                return capsules;
            }
            int hold = Integer.parseInt(room);

            if (capsules[hold - 1] == null) {
                System.out.printf("%nSuccess%n%s is booked in capsule %s%n%n", name, room);
                capsules[hold - 1] = name;
                check = true;
            } else if (capsules[hold - 1] != null) {
                System.out.printf("%nError%nCapsule %s is occupied.%n", room);
            }
        } while (!check);
        return capsules;
    }


    public static String[] displayCheckOut(String[] capsules, int caps) { // method to let user remove guests from capsules
        System.out.println("\nGuest Check Out");
        System.out.println("=".repeat(15));
        boolean check = false;

        do { // do while loop to get the capsule number, ensure that if it is an unassigned capsule nothing changes, and remove it from the array
            String room = roomFinder(caps);
            if (room.equals("R")) {
                return capsules;
            }
            int hold = Integer.parseInt(room);

            if (capsules[hold - 1] != null) {
                String name = capsules[hold - 1];
                System.out.printf("%nSuccess%n%s checked out from capsule %s%n%n", name, room);
                capsules[hold - 1] = null;
                check = true;
            } else if (capsules[hold - 1] == null) {
                System.out.printf("%nError%nCapsule %s is unoccupied.%n", room);
            }

        } while (!check);
        return capsules;
    }


    public static void displayGuests(String[] capsules, int caps) { // method to display the 5 closest capsules with their occupants on each side of selected capsule
        System.out.println("\nView Nearby Guests");
        System.out.println("=".repeat(18));

        String room = roomFinder(caps);
        if (room.equals("R")) {
            return;
        }

        int hold = Integer.parseInt(room);
        System.out.println("\nCapsule: Guest");


        if ((hold < 6) && (hold > (caps - 5))) {
            viewHelper(capsules, hold, (6 - hold), (caps - hold));
        } else if (hold < 6) {
            viewHelper(capsules, hold, (6 - hold), 5);
        } else if (hold > (caps - 5)) {
            viewHelper(capsules, hold, 0, (caps - hold));
        } else {
            viewHelper(capsules, hold, 0, 5);
        }
    }


    public static boolean displayExit() { // displays exit text and prompts user if they really want to end the program
        System.out.println("\nExit");
        System.out.println("====");
        System.out.println("Are you sure you want to exit?");
        System.out.println("All data will be lost.");

        String unconverted;
        do { // checks to make sure if the user is going to quit
            System.out.print("Exit [y/n]: ");
            unconverted = scannerHelper();
        } while (unconverted.isBlank() || !((unconverted.equalsIgnoreCase("n")) || (unconverted.equalsIgnoreCase("y"))));

        return !unconverted.equalsIgnoreCase("n");
    }


    public static int properInput(String unconverted) { // method to check if the input is a number (int) and not anything else
        if(unconverted.matches("\\d+")) {
            return Integer.parseInt(unconverted);
        }
        return -1;
    }


    public static String roomFinder(int caps) { // method for getting the proper capsule room number
        String room;
        int hold;
        do {
            System.out.printf("Capsule #[1-%d] (type R to return to menu): ", caps);
            room = scannerHelper();
            if (room.equals("R")) {
                return "R";
            }
            hold = properInput(room);
            if(hold >= caps) {
                System.out.println("Error: Number not in bounds");
            }

        } while (room.isBlank() || (hold < 1) || (hold > (caps)));
        return room;
    }


    public static void viewHelper(String[] capsules, int hold, int compare1, int compare2) { // method to properly display the nearby guests
        for (int i = ((hold - 6) + compare1); i < (hold + compare2); i++) {
            String replace;
            if (capsules[i] == null) {
                replace = "[unoccupied]";
            } else {
                replace = capsules[i];
            }
            System.out.printf("%d: %s%n", (i + 1), replace);
        }
    }


    public static String scannerHelper() { // holds the scanner
        Scanner console = new Scanner(System.in);
        return console.next();
    }
}