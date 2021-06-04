Coverage: 85%

# Project Title

Inventory Management System - IMS is an application where users can interact on Customers, Items, and Orders Data via the command line.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

---

### Prerequisites

1. Java

- needed to run the executable file
- Steps:
  - Download **[Java](https://www.oracle.com/java/technologies/javase-downloads.html 'Java SE Download Link')** and install

2. MySQL

- needed to create your database that'll be connected to this application
- Steps:
  - Download and **[MySQL](https://dev.mysql.com/downloads/installer/ 'MySQL Download Link')** and install

---

### Installing

1. Clone this repository into your local machine
2. You should have a database containing these entities:

```
Customer
    customer_id
    firstname
    surname
    address
    email

Item
    item_id
    item_name
    item_price

Orders
    order_id
    customer_id

Orders_item
    id
    orders_id
    item_id

```

3. Create a `db.properties` file in `src -> main -> resources` and add the following:

```
db.url
db.user
db.password

```

```
example

db.url = jdbc:mysql://localhost:3306/jdbc_example?db_name&serverTimezone=UTC

db.user = testUser
db.user = testPass

```

4. You can then run the application by running it in your code editor of choice

---

## Running the tests

Explain how to run the automated tests for this system. Break down into which tests and what they do

1. If you're using Eclipse, right click on the main project and click on `Run As`
2. Select `JUnit Tests` and it'll run all the tests for this application

### Unit Tests

Unit tests are testing each individual classes for my main entities (Customer, Item, Order). Each entity has 3 classes associated with them:

1. EntityController
2. EntityDAO
3. Entity Domain

These unit tests are testing the behavior of each of these classes to ensure that they're working as expected on their own

---

## Built With

- [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

I used [GitHub](https://github.com/) for versioning.

## Authors

- **Marlon Del Rosario** - _Initial work_ - [GitHub Account](https://github.com/mpaolodr)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details

_For help in [Choosing a license](https://choosealicense.com/)_

## Acknowledgments

- Hat tip to anyone whose code was used
- Inspiration
- etc
