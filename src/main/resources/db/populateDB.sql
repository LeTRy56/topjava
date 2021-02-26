DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-01-30 10:00:00', 'Юзер завтрак', 100),
       (100000, '2020-01-30 18:00:00', 'Юзер ужин', 200),
       (100001, '2021-01-31 14:00:00', 'Админ обед', 300),
       (100001, '2021-01-31 21:00:00', 'Админ ужин', 400);