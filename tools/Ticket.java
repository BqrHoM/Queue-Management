
package tools;

import java.sql.Timestamp;

public final class Ticket {

    private int id;
    private int lab_number;
    private int number_of_defences;

    private String name;
    private boolean activity;

    private StudentActivity status;

    private QueueStatus queueStatus;
    private Timestamp createdAt;

    public Ticket(
            int id,
            int lab_number,
            int number_of_defences,
            String name,
            boolean activity,
            StudentActivity status,
            QueueStatus queueStatus,
            Timestamp createdAt) {
        this.id = id;
        this.lab_number = lab_number;
        this.number_of_defences = number_of_defences;
        this.name = name;
        this.activity = activity;
        this.status = status;

        this.queueStatus = queueStatus;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getLab_number() {
        return lab_number;
    }

    public int getNumber_of_defences() {
        return number_of_defences;
    }

    public String getName() {
        return name;
    }

    public boolean isActivity() {
        return activity;
    }

    public StudentActivity getStatus() {
        return status;
    }

    public QueueStatus getQueueStatus() {
        return queueStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
