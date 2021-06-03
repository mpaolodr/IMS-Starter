use ims_project;

CREATE TABLE IF NOT EXISTS customer (
	customer_id int NOT NULL AUTO_INCREMENT,
    firstname varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    email varchar(255) UNIQUE,
    address varchar(255) NOT NULL,
	PRIMARY KEY(customer_id)
);

CREATE TABLE IF NOT EXISTS item(
	item_id int NOT NULL AUTO_INCREMENT,
    item_name varchar(100) NOT NULL,
    item_price varchar(100) NOT NULL,
    PRIMARY KEY(item_id)
);

ALTER TABLE item
MODIFY COLUMN item_price dec(10,2) NOT NULL;

CREATE TABLE IF NOT EXISTS orders(
	order_id int NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    PRIMARY KEY(order_id)
);


CREATE TABLE IF NOT EXISTS orders_item(
	id int NOT NULL AUTO_INCREMENT,
	order_id int NOT NULL,
    item_id int NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(order_id) REFERENCES orders(order_id),
    FOREIGN KEY(item_id) REFERENCES item(item_id)
);

ALTER TABLE orders_item DROP FOREIGN KEY orders_item_ibfk_1;
ALTER TABLE orders_item ADD FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE;
ALTER TABLE orders_item DROP FOREIGN KEY orders_item_ibfk_2;
ALTER TABLE orders_item ADD FOREIGN KEY(item_id) REFERENCES item(item_id) ON DELETE CASCADE;


