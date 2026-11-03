
package tools;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Keeps track of the ticket currently being served
        Ticket currentTicket = null;

        while (true) {

            System.out.println("\n===== QUEUE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add a ticket");
            System.out.println("2. Call next");
            System.out.println("3. Complete current ticket");
            System.out.println("4. Show waiting queue");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                // ADD A TICKET
                case 1:

                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter lab number: ");

                    try {
                        int lab = Integer.parseInt(scanner.nextLine());

                        QueueManager.addTicket(name, lab);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid lab number.");
                    }

                    break;

                // CALL NEXT
                case 2:

                    currentTicket = QueueManager.callNext();

                    if (currentTicket == null) {
                        System.out.println("The queue is empty!");
                    } else {

                        System.out.println("\nNow calling:");
                        System.out.println(
                                "Ticket ID: " + currentTicket.getId());
                        System.out.println(
                                "Student: " + currentTicket.getName());
                        System.out.println(
                                "Lab number: " +
                                        currentTicket.getLab_number());
                    }

                    break;

                // COMPLETE CURRENT TICKET
                case 3:

                    if (currentTicket == null) {

                        System.out.println(
                                "There is no current ticket.");

                    } else {

                        QueueManager.completeTicket(
                                currentTicket.getId());

                        currentTicket = null;
                    }

                    break;

                // SHOW WAITING QUEUE
                case 4:

                    List<Ticket> tickets = QueueManager.getWaitingTickets();

                    if (tickets.isEmpty()) {

                        System.out.println("No waiting tickets.");

                    } else {

                        System.out.println("\n===== WAITING QUEUE =====");

                        for (Ticket ticket : tickets) {

                            System.out.println(
                                    "ID: " + ticket.getId()
                                            + " | Name: " + ticket.getName()
                                            + " | Lab: " + ticket.getLab_number());
                        }
                    }

                    break;

                // EXIT
                case 5:

                    System.out.println("Goodbye!");

                    scanner.close();
                    return;

                default:

                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
