DROP TABLE Product IF EXISTS;
DROP TABLE Vendor IF EXISTS;
CREATE TABLE Vendor (
    vendor_id int primary key,
    vendor_name varchar(255) not null
);
CREATE TABLE Product (
    product_id int primary key,
    product_name varchar(255) not null,
    product_price double,
    sold_by int references Vendor(vendor_id)
);
INSERT INTO Vendor (vendor_id, vendor_name)
VALUES
(1, 'marty'),
(2, 'sam');
INSERT INTO Product (product_id, product_name, product_price, sold_by)
VALUES
(1, 'bagel', 2.25, 1),
(2, 'muffin', 3.00, 2),
(3, 'pancake', 2.50, 1);