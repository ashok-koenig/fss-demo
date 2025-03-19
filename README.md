# FSS Tech - Training demo projects

## Data Integrity
### Customers Table

CREATE TABLE Customers (
	CustomerID INT PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Email VARCHAR(100) UNIQUE NOT NULL
	)

### Orders Table

CREATE TABLE Orders(
	OrderID INT PRIMARY KEY,
	CustomerID INT,
	OrderDate DATE NOT NULL,
	Amount DECIMAL(10,2) CHECK (Amount > 0),
	FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
	);

### Working with Transactions

BEGIN TRANSACTION;
    INSERT INTO Customers(CustomerID, Name, Email) values(103, 'David', 'david@email.com');
    INSERT INTO Orders(OrderID, CustomerID, OrderDate, Amount) values(1003, 103, '2025-03-01', 454.0);
COMMIT;

### Working with Delete Cascade

ALTER TABLE Orders ADD CONSTRAINT Fk_Customer
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) on DELETE CASCADE;

DELETE FROM Customers where CustomerId=102;