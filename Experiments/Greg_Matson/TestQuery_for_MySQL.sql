CREATE DATABASE UserInformation;
USE UserInformation;
CREATE TABLE UserLogin (
	id int NOT NULL AUTO_INCREMENT,
    Username		varchar(255) NOT NULL,
    UserPassword	varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE UserInfo (
	id int NOT NULL AUTO_INCREMENT,
    FirstName		varchar(255) NOT NULL,
    LastName		varchar(255) NOT NULL,
    Email			varchar(255) NOT NULL,
    Birthday		varchar(255) NOT NULL,
    loginID int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loginID) REFERENCES UserLogin(id)
);

INSERT INTO UserLogin (UserName, UserPassword)
VALUES ('gwmatson', 'password');

INSERT INTO UserLogin (UserName, UserPassword)
VALUES ('karl','wheezer'), ('deez', 'nuts'), ('Rick', 'Astley');

SELECT * FROM UserLogin;

SELECT * FROM UserLogin LIMIT 2;

SELECT UserName FROM UserLogin;

SELECT id AS 'ID', UserName AS 'User_Name'
FROM UserLogin;

SELECT * FROM UserLogin ORDER BY UserName;

INSERT INTO UserInfo (FirstName, LastName, Email, Birthday, loginID)
VALUES ('Greg', 'Matson', 'gwmatson@iastate.edu', '01/17/2001', 1),
	   ('Karl', 'Wheezer', 'Thewheeze@gmail.com', '06/09/2069', 2),
       ('Deez', 'Nuts', 'Sukon@gmail.com', '9/10/2021', 3),
       ('Rick', 'Astley', 'RickRoll@gmail.com', '02/06/1966', 4);
       
SELECT * FROM UserInfo;

SELECT FirstName from UserInfo;

SELECT * FROM UserLogin
JOIN UserInfo ON UserLogin.id = UserInfo.LoginID
