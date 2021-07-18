CREATE TABLE Letters(
Id integer auto_increment primary key,
SenderId int,
RecipientId int,
FOREIGN KEY (SenderId) REFERENCES Persons(Id),
FOREIGN KEY (RecipientId) REFERENCES Persons(Id),
Topic VARCHAR(50),
Content VARCHAR(300),
Departure_date Date
);