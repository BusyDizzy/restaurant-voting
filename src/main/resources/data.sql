DELETE
FROM user_role;
DELETE
FROM vote;
DELETE
FROM dish;
DELETE
FROM restaurant;
DELETE
FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);



INSERT INTO restaurant(name, address)
VALUES ('Pizza Palace', '123 Main St'), -- 1
       ('Burger Joint', '456 Oak St'),  -- 2
       ('Sushi Spot', '789 Maple Ave'); -- 3

INSERT INTO menu(date_added, restaurant_id)
VALUES ('2023-03-06', 1), -- 1
       ('2023-03-06', 2), -- 2
       ('2023-03-06', 3), -- 3
       ('2023-04-09', 1), -- 4
       ('2023-04-09', 2), -- 5
       ('2023-04-09', 3); -- 6


INSERT INTO dish(name, price, menu_id)
VALUES ('Pepperoni Pizza', 10.99, 1),          -- 1
       ('Coca Cola', 2.99, 1),                 -- 2
       ('Cheeseburger', 8.99, 1),              -- 3
       ('Bacon Cheeseburger', 9.99, 2),        -- 4
       ('California Roll', 11.99, 2),          -- 5
       ('Spicy Tuna Roll', 12.99, 3),          -- 6

       ('Margarita Pizza', 9.99, 4),           -- 7
       ('Pepsi Cola', 2.98, 4),                -- 8
       ('Green Salad', 4.99, 5),               -- 9
       ('Beyond Meat Cheeseburger', 15.99, 5), -- 10
       ('Crispy Fried Roll', 14.99, 5),        -- 11
       ('Spicy Tuna Roll', 12.99, 6); -- 12

INSERT INTO vote(user_id, restaurant_id, vote_date)
VALUES (1, 1, '2023-03-06'), -- 1
       (2, 2, '2023-03-06'), -- 2
       (3, 1, '2023-03-06'), -- 3
       (1, 2, '2023-04-09'), -- 4
       (2, 1, '2023-04-09'), -- 4
       (3, 2, '2023-04-09'); -- 6
