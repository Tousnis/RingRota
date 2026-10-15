INSERT INTO staff_entity (name, last_name, email, phone_number, password, role, total_hours)VALUES ('Tousni', 'Sillah', 'Tousni@ringrota.com', '07111111111', '$2a$10$.e/oyk2ib9GN/m1g9lwQNOuI1W84oQaLdp/UHlcHMfut3MB3ZRLEi', 'ADMIN', 0);

INSERT INTO staff_entity (name, last_name, email, phone_number, password, role, total_hours)VALUES ('Alahaji', 'Jabbi', 'Alahaji@ringrota.com', '07222222222', '$2a$10$V0LU6kcs.A2/HXb3Hhi1/OhMShVSA1UxbBTSAn.7OGfI7iNbR3y7q', 'STAFF', 0);

INSERT INTO staff_entity (name, last_name, email, phone_number, password, role, total_hours)VALUES ('Pepe', 'Brow', 'Pepe@ringrota.com', '07333333333', '$2a$10$V0LU6kcs.A2/HXb3Hhi1/OhMShVSA1UxbBTSAn.7OGfI7iNbR3y7q', 'STAFF', 0);

INSERT INTO staff_entity (name, last_name, email, phone_number, password, role, total_hours)VALUES ('Josemi', 'Wili', 'Josemi@ringrota.com', '07444444444', '$2a$10$GYx1KFKsG4ekbFKSt2nBOenmJuebjE/0uXLoijtBkeBEk.itjwv6u', 'STAFF', 0);

INSERT INTO staff_entity (name, last_name, email, phone_number, password, role, total_hours)VALUES ('Davo', 'Diva', 'Davo@ringrota.com', '07555555555', '$2a$10$GYx1KFKsG4ekbFKSt2nBOenmJuebjE/0uXLoijtBkeBEk.itjwv6u', 'STAFF', 0);

-- Clock records
INSERT INTO clock_entity (clock_date, clock_in, clock_out, status, staff_id) VALUES ('2026-10-05', '09:00:00', '17:00:00', 'APPROVED', 1);
INSERT INTO clock_entity (clock_date, clock_in, clock_out, status, staff_id) VALUES ('2026-10-05', '09:05:00', '17:02:00', 'PENDING', 2);
INSERT INTO clock_entity (clock_date, clock_in, clock_out, status, staff_id) VALUES ('2026-10-06', '08:58:00', NULL, 'PENDING', 1);