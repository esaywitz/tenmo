TRUNCATE transfers, accounts, users CASCADE;


INSERT INTO users (user_id, username, password_hash)
VALUES (1,'user1', 'test1Password'),
       (2,'user2', 'test2Password'),
       (3,'user3', 'test3Password');

INSERT INTO accounts (account_id, user_id, balance)
VALUES (101,1, 1000),
       (102,2, 950),
       (103,3, 1050);

INSERT INTO transfers (transfer_id, amount, account_to, account_from, transfer_type_id, transfer_status_id)
VALUES (1001,50, 101, 102, 2, 2),
       (1002,100,101, 103, 2, 2),
       (1003,150,103, 101, 2, 2);

