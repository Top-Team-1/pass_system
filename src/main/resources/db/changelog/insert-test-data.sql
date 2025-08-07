--liquibase formatted sql
--changeset All:data

INSERT INTO territories (name, address, added_at, updated_at, type)
VALUES
    ('Офис на Тверской', 'ул. Тверская, 10', NOW(), NOW(), 'OFFICE'),
    ('Бизнес-центр "Северный"', 'пр. Ленинградский, 15', NOW(), NOW(), 'ENTERPRISE'),
    ('Жилой комплекс "Восток"', 'ул. Восточная, 25', NOW(), NOW(), 'HOUSE'),
    ('Головной офис', 'ул. Центральная, 1', NOW(), NOW(), 'OFFICE'),
    ('Складской комплекс', 'ул. Промышленная, 5', NOW(), NOW(), 'ENTERPRISE'),
    ('ЖК "Западный"', 'ул. Западная, 12', NOW(), NOW(), 'HOUSE'),
    ('Филиал на Южной', 'ул. Южная, 8', NOW(), NOW(), 'OFFICE');


INSERT INTO users (first_name, last_name, date_of_birth, phone, added_at, updated_at, role, password)
VALUES
-- Админы
 ('Админ', 'Админов','1992-01-02', 'admin', NOW(), NOW(), 'ADMIN', '$2a$12$0nRQjsQlurTKKRAWX2kid.6C9deaDQMsb6Hfe1PhL/sAmaYrXR5H.'),
('Алексей', 'Петров', '1985-05-15', '+79161234567', NOW(), NOW(), 'ADMIN', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Ольга', 'Иванова', '1990-08-22', '+79167654321', NOW(), NOW(), 'ADMIN', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),

-- Охрана
('Дмитрий', 'Сидоров', '1988-03-10', '+79161112233', NOW(), NOW(), 'SECURITY', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Иван', 'Козлов', '1992-07-18', '+79162223344', NOW(), NOW(), 'SECURITY', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Сергей', 'Волков', '1987-11-05', '+79163334455', NOW(), NOW(), 'SECURITY', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Анна', 'Морозова', '1991-09-30', '+79164445566', NOW(), NOW(), 'SECURITY', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Михаил', 'Лебедев', '1989-12-15', '+79165556677', NOW(), NOW(), 'SECURITY', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),

-- Обычные пользователи
('Андрей', 'Смирнов', '1993-04-20', '+79166667788', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Наталья', 'Кузнецова', '1994-06-25', '+79167778899', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Артем', 'Попов', '1995-01-12', '+79168889900', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Елена', 'Васильева', '1986-02-28', '+79169990011', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Кирилл', 'Павлов', '1990-10-08', '+79160001122', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Светлана', 'Семенова', '1984-07-14', '+79161112200', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Александр', 'Голубев', '1996-08-03', '+79162220011', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Татьяна', 'Виноградова', '1983-09-17', '+79163331122', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Юлия', 'Ковалева', '1997-05-19', '+79164442233', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Николай', 'Новиков', '1982-12-24', '+79165553344', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Ирина', 'Алексеева', '1998-11-11', '+79166664455', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Анастасия', 'Лебедева', '1981-10-05', '+79167775566', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Мария', 'Соколова', '1999-03-08', '+79168886677', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Владимир', 'Козлов', '1980-04-30', '+79169997788', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Екатерина', 'Егорова', '1992-07-22', '+79160008899', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Павел', 'Федоров', '1987-06-13', '+79161119900', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Людмила', 'Медведева', '1994-01-27', '+79162220033', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Геннадий', 'Борисов', '1989-02-09', '+79163330044', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Виктория', 'Андреева', '1991-05-16', '+79164440055', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Роман', 'Макаров', '1985-08-07', '+79165550066', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Алина', 'Захарова', '1996-09-14', '+79166660077', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly'),
('Станислав', 'Степанов', '1983-10-23', '+79167770088', NOW(), NOW(), 'USER', '$2a$12$Ykp16DJ4iVDYay4fyp179uI3CxIXFd8CVFu1GVKIXTcXRi89W53ly');



INSERT INTO passes (user_id, territory_id, first_name, last_name, type, status, start_date, end_date, added_at, update_at)
VALUES

(1, 1, 'Алексей', 'Петров', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(1, 2, 'Алексей', 'Петров', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(1, 3, 'Алексей', 'Петров', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(2, 4, 'Ольга', 'Иванова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(2, 5, 'Ольга', 'Иванова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(3, 1, 'Дмитрий', 'Сидоров', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(4, 2, 'Иван', 'Козлов', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(5, 3, 'Сергей', 'Волков', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(6, 4, 'Анна', 'Морозова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(7, 5, 'Михаил', 'Лебедев', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(8, 1, 'Андрей', 'Смирнов', 'TEMPORARY', 'ACTIVE', NOW(), NOW() + INTERVAL '3 months', NOW(), NOW()),
(9, 2, 'Наталья', 'Кузнецова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '6 months', NOW(), NOW()),
(10, 3, 'Артем', 'Попов', 'TEMPORARY', 'ACTIVE', NOW(), NOW() + INTERVAL '2 months', NOW(), NOW()),
(11, 4, 'Елена', 'Васильева', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(12, 5, 'Кирилл', 'Павлов', 'TEMPORARY', 'ACTIVE', NOW(), NOW() + INTERVAL '1 month', NOW(), NOW()),
(13, 6, 'Светлана', 'Семенова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(14, 7, 'Александр', 'Голубев', 'TEMPORARY', 'ACTIVE', NOW(), NOW() + INTERVAL '4 months', NOW(), NOW()),
(15, 1, 'Татьяна', 'Виноградова', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(16, 2, 'Юлия', 'Ковалева', 'TEMPORARY', 'ACTIVE', NOW(), NOW() + INTERVAL '3 months', NOW(), NOW()),
(17, 3, 'Николай', 'Новиков', 'PERMANENT', 'ACTIVE', NOW(), NOW() + INTERVAL '1 year', NOW(), NOW()),
(18, 4, 'Ирина', 'Алексеева', 'TEMPORARY', 'EXPIRED', NOW() - INTERVAL '4 months', NOW() - INTERVAL '1 month', NOW(), NOW());