
package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class QueueManager {


    public static void addTicket(String name, int lab_number) {

        String sql = """
            INSERT INTO tickets (student_name, lab_number)
            VALUES (?, ?)
            """;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, lab_number);

            pstmt.executeUpdate();

            System.out.println("Ticket added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static Ticket callNext() {

        String selectSql = """
            SELECT * FROM tickets
            WHERE queue_status = 'WAITING'
            ORDER BY id ASC
            LIMIT 1
            FOR UPDATE
            """;

        String updateSql = """
            UPDATE tickets
            SET queue_status = 'CALLED'
            WHERE id = ?
            """;

        try (Connection conn = Database.connect()) {

            conn.setAutoCommit(false);

            try (PreparedStatement pstmt =
                     conn.prepareStatement(selectSql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (!rs.next()) {
                    conn.commit();
                    return null;
                }

                Ticket ticket = new Ticket(
                    rs.getInt("id"),
                    rs.getInt("lab_number"),
                    rs.getInt("number_of_defences"),
                    rs.getString("student_name"),
                    rs.getBoolean("activity"),
                    StudentActivity.valueOf(
                        rs.getString("status")
                    ),
                    QueueStatus.CALLED,
                    rs.getTimestamp("created_at")
                );

                try (PreparedStatement update =
                         conn.prepareStatement(updateSql)) {

                    update.setInt(1, ticket.getId());
                    update.executeUpdate();
                }

                conn.commit();

                return ticket;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void completeTicket(int ticketId) {

        String sql = """
            UPDATE tickets
            SET queue_status = 'COMPLETED'
            WHERE id = ?
            AND queue_status = 'CALLED'
            """;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ticketId);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Ticket completed successfully!");
            } else {
                System.out.println(
                    "Ticket not found or is not currently called."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static List<Ticket> getWaitingTickets() {

        List<Ticket> tickets = new ArrayList<>();

        String sql = """
            SELECT * FROM tickets
            WHERE queue_status = 'WAITING'
            ORDER BY id ASC
            """;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Ticket ticket = new Ticket(
                    rs.getInt("id"),
                    rs.getInt("lab_number"),
                    rs.getInt("number_of_defences"),
                    rs.getString("student_name"),
                    rs.getBoolean("activity"),
                    StudentActivity.valueOf(
                        rs.getString("status")
                    ),
                    QueueStatus.valueOf(
                        rs.getString("queue_status")
                    ),
                    rs.getTimestamp("created_at")
                );

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
