CREATE TABLE `buy_record` (
  `id` int NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `mallId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `shoe_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shoeId` int DEFAULT NULL,
  `detail` varchar(450) DEFAULT NULL,
  `urls` varchar(4500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `malls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  `url` varchar(450) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `size` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


insert into yanghd.shoe_detail (`shoeId`, detail, urls) values
 ("2", "轻盈透气", "https://img14.360buyimg.com/n0/jfs/t1/162718/4/12405/100680/605048c0E822e0259/3da893d6ad17fada.jpg,https://img14.360buyimg.com/n0/jfs/t1/162626/15/13460/204270/605048c0E916779c5/4be7e61515357748.jpg,https://img14.360buyimg.com/n0/jfs/t1/156056/15/16309/144941/605048c0E4f8e25b0/07ac2cd8134688ac.jpg")


 insert into yanghd.malls (`name`, brand, price, `description`,`url`, `color`, `size`) values
  ("板鞋", "NIKE", "100", "轻盈透气", "https://imgsa.baidu.com/forum/w%3D580/sign=94016b5326f5e0feee1889096c6134e5/4a4dc458ccbf6c81e41448a2b73eb13532fa40ac.jpg", "红", "36")