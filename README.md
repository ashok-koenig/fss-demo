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
