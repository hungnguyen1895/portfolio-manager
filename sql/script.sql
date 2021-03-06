use conygre;

drop table IF EXISTS cash_account;

drop table IF EXISTS investment;

drop table IF EXISTS user;

CREATE TABLE `conygre`.`user` (
                                  `id` INT NOT NULL,
                                  `firstname` VARCHAR(45) NULL,
                                  `lastname` VARCHAR(45) NULL,
                                  `username` VARCHAR(45) NULL,
                                  `password` VARCHAR(45) NULL,
                                  PRIMARY KEY (`id`));

CREATE TABLE `conygre`.`cash_account` (
                                          `id` INT NOT NULL,
                                          `user_id` INT NULL,
                                          `account_name` VARCHAR(45) NULL,
                                          `value` DOUBLE NULL,
                                          PRIMARY KEY (`id`),
                                          INDEX `FK_cashaccount_user_id_idx` (`user_id` ASC) VISIBLE,
                                          CONSTRAINT `FK_cashaccount_user_id`
                                              FOREIGN KEY (`user_id`)
                                                  REFERENCES `conygre`.`user` (`id`)
                                                  ON DELETE CASCADE
                                                  ON UPDATE CASCADE);


CREATE TABLE `conygre`.`investment` (
                                        `id` INT NOT NULL,
                                        `user_id` INT NULL,
                                        `stock_name` VARCHAR(45) NULL,
                                        `quantity` DOUBLE NULL,
                                        `stock_symbol` VARCHAR(45) NULL,
                                        PRIMARY KEY (`id`),
                                        INDEX `FK_investment_user_id_idx` (`user_id` ASC) VISIBLE,
                                        CONSTRAINT `FK_investment_user_id`
                                            FOREIGN KEY (`user_id`)
                                                REFERENCES `conygre`.`user` (`id`)
                                                ON DELETE NO ACTION
                                                ON UPDATE NO ACTION);





INSERT INTO user (id, firstname, lastname, username, password) VALUES
(1, 'hung', 'nguyen', 'hungnguyen', '123456'),
(2,'jessica', 'phelan', 'jessphelan', '123456'),
(3, 'jovanny', 'vera', 'jovannyvera', '123456');

INSERT INTO cash_account (id, user_id, account_name, value) VALUES
(1, 1, 'Citi', 3127.12),
(2, 1, 'Well Fargo', 1223.12),
(3, 1, 'Fidelity Cash', 6734.90),
(4, 2, 'Citi', 1000),
(5, 2, 'Well Fargo', 2000),
(6, 2, 'Fidelity Cash', 3000),
(7, 3, 'Citi', 5000),
(8, 3, 'Well Fargo', 5000),
(9, 3, 'Fidelity Cash', 5000);

INSERT INTO investment (id, user_id, stock_name, quantity, stock_symbol) VALUES
(1, 1, 'TESLA', 31.25, 'TSLA'),
(2, 1, 'Apple', 20, 'AAPL'),
(3, 1, 'Citi', 200, 'C'),
(4, 2, 'Twitter', 31.25, 'TWTR'),
(5, 2, 'Disney', 20, 'DIS'),
(6, 2, 'Citi', 200, 'C'),
(7, 3, 'Facebook', 10, 'FB'),
(8, 3, 'Microsoft', 20, 'MSFT'),
(9, 3, 'Alibaba', 15, 'BABA');