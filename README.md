# FSS Tech - Training demo projects

## Data Integrity
### Customers Table
```
CREATE TABLE Customers (
	CustomerID INT PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Email VARCHAR(100) UNIQUE NOT NULL
	)
```
### Orders Table
```
CREATE TABLE Orders(
	OrderID INT PRIMARY KEY,
	CustomerID INT,
	OrderDate DATE NOT NULL,
	Amount DECIMAL(10,2) CHECK (Amount > 0),
	FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
	);
```
### Working with Transactions
```
BEGIN TRANSACTION;
    INSERT INTO Customers(CustomerID, Name, Email) values(103, 'David', 'david@email.com');
    INSERT INTO Orders(OrderID, CustomerID, OrderDate, Amount) values(1003, 103, '2025-03-01', 454.0);
COMMIT;
```
### Working with Delete Cascade
```
ALTER TABLE Orders ADD CONSTRAINT Fk_Customer
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) on DELETE CASCADE;
```
```
DELETE FROM Customers where CustomerId=102;
```
### Creating view
```
CREATE VIEW CustomerOrdersView AS
SELECT 
	Orders.OrderID,
	Orders.CustomerID,
	Customers.Name,
	Customers.Email,
	Orders.OrderDate,
	Orders.Amount
FROM Orders 
JOIN Customers ON Orders.CustomerID = Customers.CustomerID;
```
### Query the view
```
SELECT * FROM CustomerOrdersView;

```
### Creating index for customer name
```
CREATE INDEX idx_customer_name ON Customers(Name);
```
### Query the customer by name after indexing
```
SELECT * FROM Customers WHERE Name = 'Peter';
```
### Create OrderHistory Table
```
CREATE TABLE OrderHistory (
	OrderID INT,
	CustomerID INT,
	DeletedAt DATETIME DEFAULT GETDATE()
	);
```
### Create Trigger to Log Deleted Orders
```
CREATE TRIGGER trg_AfterDelete ON Orders
AFTER DELETE
AS
BEGIN
	INSERT INTO OrderHistory(OrderID, CustomerID, DeletedAt)
	SELECT OrderID,CustomerID, GETDATE() FROM deleted;
END
```
### Deleting a Order to test Trigger
```
DELETE FROM Orders WHERE OrderID=1003;
```
## Hand-on Activity

### Customers Table
```
CREATE TABLE Customers (
	CustomerID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(100) NOT NULL,
	Email VARCHAR(100) UNIQUE NOT NULL,
	Phone VARCHAR(15) NOT NULL
	);
```

### Products Table
```
CREATE TABLE Products (
	ProductID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(100) NOT NULL,
	Price DECIMAL(10,2) NOT NULL CHECK (Price >0),
	Stock INT NOT NULL CHECK (Stock >= 0 )
	);
```
### Orders Table
```
CREATE TABLE Orders (
	OrderID INT PRIMARY KEY IDENTITY(1,1),
	CustomerID INT NOT NULL,
	OrderDate DATETIME DEFAULT GETDATE(),
	CONSTRAINT FK_Orders_Customers FOREIGN KEY (CustomerID) 
	REFERENCES Customers(CustomerID) ON DELETE CASCADE
	);
```
### OrderDetails Table
```
CREATE TABLE OrderDetails (
	OrderDetailID INT PRIMARY KEY IDENTITY(1,1),
	OrderID INT NOT NULL,
	ProductID INT NOT NULL,
	Quantity INT NOT NULL CHECK (Quantity > 0),
	CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderID) 
	REFERENCES Orders(OrderID) ON DELETE CASCADE,
	CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductID) 
	REFERENCES Products(ProductID) ON DELETE CASCADE,
	)
```
### Insert sample record to the tables
```
INSERT INTO Customers (Name, Email, Phone) 
VALUES('John Smith', 'john@email.com', '1212-232');

INSERT INTO Products (Name, Price, Stock) 
VALUES('Dell Laptop', 1500, 10),
('Dell Mouse', 25, 50);

INSERT INTO Orders(CustomerID) VALUES(1)

INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (1,1, 2);
```

### Prevent Product Stock from Going Negative
```
CREATE TRIGGER trg_ValidateStock
ON OrderDetails
AFTER INSERT
AS
BEGIN
	IF EXISTS (
		SELECT 1 FROM inserted i
		JOIN Products p ON i.ProductID = p.ProductID
		WHERE p.Stock - i.Quantity < 0
		)
	BEGIN
		PRINT 'Error: Not enough stock available!';
		ROLLBACK TRANSACTION;
	END
END;
```
### Test the Trigger

```
INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (1, 2, 100);
```
# Hands-on Activity in MySQL

### Customers Table
```
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Phone VARCHAR(15) NOT NULL
);
```
### Products Table
```
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Price DECIMAL(10,2) NOT NULL CHECK (Price > 0),
    Stock INT NOT NULL CHECK (Stock >= 0)
);
```
### Orders Table
```
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,    
    CONSTRAINT FK_Orders_Customers FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);
```
### OrderDetails Table
```
CREATE TABLE OrderDetails (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
```
### Prevent Product Stock from Going Negative
```
DELIMITER //
CREATE TRIGGER trg_ValidateStock
BEFORE INSERT ON OrderDetails
FOR EACH ROW
BEGIN
	DECLARE available_stock INT;
    SELECT Stock INTO available_stock FROM Products WHERE ProductID = NEW.ProductID;
    IF available_stock IS NULL OR available_stock < NEW.Quantity THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Not enough stock available!';
	END IF;
END;
//
DELIMITER ;
```
### Test the trigger

```
INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (1, 2, 100);
```