import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information
    public void printInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Person Information:");
        person.personInfo();
    }

    // Method to save ticket information to a file
    public void save() {
        String fileName = row +""+ seat + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("Ticket Information:\n");
            fileWriter.write("Row: " + row + "\n");
            fileWriter.write("Seat: " + seat + "\n");
            fileWriter.write("Price: £" + price + "\n");
            fileWriter.write("Person Information:\n");
            fileWriter.write("Name: " + person.getName() + "\n");
            fileWriter.write("Surname: " + person.getSurname() + "\n");
            fileWriter.write("Email: " + person.getEmail() + "\n");
            System.out.println("Ticket saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving the ticket.");
        }
    }
}

