import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {
    public static final int ROWS = 4;
    public static final int[] SEATS_COUNT = {14, 12, 12, 14};
    public static final int[][] SEATS = new int[ROWS][];
    public static final Ticket[] TICKETS_SOLD = new Ticket[52]; // Array to store sold
    public static int ticket_index = 0; // Index to track sold tickets

    public static void main(String[] args) {
        System.out.println("\nWelcome to the Plane Management application");
        initializeSeats();
        menuOption();

    }
    private static void initializeSeats() {
        for (int i = 0; i < ROWS; i++) { //initialize the seats array with the number of seats per row
            SEATS[i] = new int[SEATS_COUNT[i]];
            for (int j = 0; j < SEATS_COUNT[i]; j++) {
                SEATS[i][j] = 0; //initialize all seats are available
            }
        }
    }
    private static void menuOption() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("*****************************************************");
            System.out.println("*                  MENU OPTIONS                     *");
            System.out.println("*****************************************************");
            System.out.println("      1) Buy a seat ");
            System.out.println("      2) Cancel a seat ");
            System.out.println("      3) Find first available seat ");
            System.out.println("      4) Show seating plan ");
            System.out.println("      5) Print tickets information and total sales ");
            System.out.println("      6) Search ticket ");
            System.out.println("      0) Quit ");
            System.out.println("*****************************************************");
            System.out.print("Please select an option: ");

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Exit from the Plane Management programme.");
                        return;
                    default:
                        System.out.println("Invalid option! Please select a valid menu option.");

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
                scanner.next();
            }
        }
    }

    private static void buy_seat() {
        Scanner scanner = new Scanner(System.in);
        char row = validRowLetter();
        int seat = validSeatNumber();
        int rowNumber = row - 'A';
        if (SEATS[rowNumber][seat - 1] == 1) {
            System.out.println("Seat " + row + seat + " is already sold.");
            return;
        }
        // Gather information about the person
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter surname: ");
        String surname = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();

        SEATS[rowNumber][seat - 1] = 1;
        System.out.println("Seat " + row + seat + " reserved successfully.");

        // Create a new Person object
        Person person = new Person(name, surname, email);

        // Calculate ticket price
        double price;
        if (seat >= 1 && seat <= 5) {
            price = 200;
        } else if (seat >= 6 && seat <= 9) {
            price = 150;
        } else {
            price = 180;
        }

        // Create a new Ticket object
        Ticket ticket = new Ticket(row, seat, price, person);

        // Add the ticket to the array of tickets sold
        TICKETS_SOLD[ticket_index++] = ticket;

        // Save ticket information to a file
        ticket.save();
    }

    private static void cancel_seat() {
        char row = validRowLetter();
        int seat = validSeatNumber();
        int rowNumber = row - 'A';
        if (SEATS[rowNumber][seat - 1] == 0) {
            System.out.println("Seat " + row + seat + " is already available.");
            return;
        }
        // Remove the canceled ticket from the array of tickets sold
        for (int i = 0; i < ticket_index; i++) {
            if (TICKETS_SOLD[i].getRow() == row && TICKETS_SOLD[i].getSeat() == seat) {
                TICKETS_SOLD[i] = TICKETS_SOLD[ticket_index - 1];
                TICKETS_SOLD[ticket_index - 1] = null;
                ticket_index--;
                System.out.println("Ticket canceled successfully.");
                break;
            }
        }
        SEATS[rowNumber][seat - 1] = 0;
        System.out.println("Seat " + row + seat + " cancelled successfully.");
    }

    private static void find_first_available() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_COUNT[i]; j++) {
                if (SEATS[i][j] == 0) {
                    System.out.println("First available seat: " + (char) ('A' + i) + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    private static void show_seating_plan() {
        System.out.println("\nSeating Plan:");
        // Print seating plan
        for (int i = 0; i < ROWS; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < SEATS_COUNT[i]; j++) {
                if (SEATS[i][j] == 0) {
                    System.out.print(" O ");
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println();
        }
    }

    private static void print_tickets_info() {
        double totalPrice = 0;
        for (int i = 0; i < ticket_index; i++) {
            TICKETS_SOLD[i].printInfo();
            totalPrice += TICKETS_SOLD[i].getPrice();
            System.out.println();
        }
        System.out.println("Total Price of Tickets Sold: £" + totalPrice);
    }

    private static void search_ticket() {
        char row = validRowLetter();
        int seat = validSeatNumber();

        boolean found = false;
        for (int i = 0; i < ticket_index; i++) {
            if (TICKETS_SOLD[i].getRow() == row && TICKETS_SOLD[i].getSeat() == seat) {
                System.out.println();//Get a space
                TICKETS_SOLD[i].printInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("This seat is available.");
        }
    }

    //Create methods to get valid RowLetter and valid SeatNumber inputs from user
    private static char validRowLetter() {
        char row;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a row letter (A-D): ");
            row = scanner.next().toUpperCase().charAt(0);
            if (row >= 'A' && row <= 'D') {
                break;
            } else {
                System.out.println("Invalid row letter. Please enter a valid row letter (A-D).");
            }
        }
        return row;
    }

    private static int validSeatNumber() {
        Scanner scanner = new Scanner(System.in);
        int seat;
        while (true) {
            System.out.print("Enter a seat number (1-14): ");
            try {
                seat = scanner.nextInt();
                // Validate the seat number
                if (seat >= 1 && seat <= 14) {
                    // If the seat number is within the valid range, return it
                    return seat;
                } else {
                    System.out.println("Invalid seat number. Please enter a valid seat number (1-14).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid seat number (1-14).");
                scanner.next();
            }
        }
    }
}