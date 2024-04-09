BEGIN;

-- Defining table `bottom` with `bottom_id` as the primary key
CREATE TABLE IF NOT EXISTS public.bottom
(
    bottom_id SERIAL PRIMARY KEY,
    flavour VARCHAR(255),
    price NUMERIC(10,2)
    );

-- Defining table `topping` with `topping_id` as the primary key
CREATE TABLE IF NOT EXISTS public.topping
(
    topping_id SERIAL PRIMARY KEY,
    flavour VARCHAR(255),
    price NUMERIC(10,2)
    );



-- Defining table `role` with `role_id` as the primary key.
CREATE TABLE IF NOT EXISTS public.role
(
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255)
    );


-- Defining table `users` with `user_id` as the primary key
CREATE TABLE IF NOT EXISTS public.users
(
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phonenumber VARCHAR(8),
    user_password VARCHAR(255),
    role_id INT,
	balance NUMERIC(10,2),
    FOREIGN KEY (role_id) REFERENCES public.role (role_id)
    );

-- Defining table `orders` with `order_id` as the primary key and a foreign key to `users`
CREATE TABLE IF NOT EXISTS public.orders
(
    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price_total NUMERIC(10,2) NOT NULL,
    pickup_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES public.users (user_id)
    );


-- Defining table `orderline` with `orderline_id` as the primary key and foreign keys to `bottom` and `topping`
CREATE TABLE IF NOT EXISTS public.orderline
(
    orderline_id SERIAL PRIMARY KEY,
    bottom_id INT NOT NULL,
    topping_id INT NOT NULL,
    quantity INT,
	order_id INT NOT NULL,
	FOREIGN KEY (order_id) REFERENCES public.orders (order_id),
    FOREIGN KEY (bottom_id) REFERENCES public.bottom (bottom_id),
    FOREIGN KEY (topping_id) REFERENCES public.topping (topping_id)
    );



COMMIT;


BEGIN;

INSERT INTO public.bottom (flavour, price) VALUES
('Chocolate', 5.00),
('Vanilla', 5.00),
('Nutmeg', 5.00),
('Pistachio', 6.00),
('Almond', 7.00);

INSERT INTO public.topping (flavour, price) VALUES
('Chocolate', 5.00),
('Blueberry', 5.00),
('Raspberry', 5.00),
('Crispy', 6.00),
('Strawberry', 6.00),
('Rum/Raisin', 7.00),
('Orange', 8.00),
('Lemon', 8.00),
('Blue cheese', 9.00);

INSERT INTO public.role (role_id, role_name) VALUES
(1, 'admin'),
(2, 'customer');


COMMIT;



