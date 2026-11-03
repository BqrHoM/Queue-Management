
CREATE DATABASE IF NOT EXISTS queue_manager;

USE queue_manager;

CREATE TABLE IF NOT EXISTS tickets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    activity BOOLEAN NOT NULL DEFAULT FALSE,

    status ENUM(
        'THEORY',
        'LIVE_PROGRAMMING',
        'CODE_DEFENSE'
    ) NOT NULL DEFAULT 'CODE_DEFENSE',

    lab_number INT NOT NULL DEFAULT 0,
    number_of_defences INT NOT NULL DEFAULT 0,

    queue_status ENUM(
        'WAITING',
        'CALLED',
        'COMPLETED'
    ) NOT NULL DEFAULT 'WAITING',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
