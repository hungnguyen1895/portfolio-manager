CREATE TABLE `conygre`.`user` (
                                  `id` INT NOT NULL,
                                  `firstname` VARCHAR(45) NULL,
                                  `lastname` VARCHAR(45) NULL,
                                  `username` VARCHAR(45) NULL,
                                  `password` VARCHAR(45) NULL,
                                  PRIMARY KEY (`id`));

CREATE TABLE `conygre`.`cash_account` (
                                          `id` INT NOT NULL,
                                          `userId` INT NULL,
                                          `account_name` VARCHAR(45) NULL,
                                          `value` DOUBLE NULL,
                                          PRIMARY KEY (`id`),
                                          INDEX `FK_cashaccount_user_id_idx` (`userId` ASC) VISIBLE,
                                          CONSTRAINT `FK_cashaccount_user_id`
                                              FOREIGN KEY (`userId`)
                                                  REFERENCES `conygre`.`user` (`id`)
                                                  ON DELETE CASCADE
                                                  ON UPDATE CASCADE);


CREATE TABLE `conygre`.`investment` (
                                        `id` INT NOT NULL,
                                        `userId` INT NULL,
                                        `stock_name` VARCHAR(45) NULL,
                                        `quantity` DOUBLE NULL,
                                        `stock_symbol` VARCHAR(45) NULL,
                                        PRIMARY KEY (`id`),
                                        INDEX `FK_investment_user_id_idx` (`userId` ASC) VISIBLE,
                                        CONSTRAINT `FK_investment_user_id`
                                            FOREIGN KEY (`userId`)
                                                REFERENCES `conygre`.`user` (`id`)
                                                ON DELETE NO ACTION
                                                ON UPDATE NO ACTION);

