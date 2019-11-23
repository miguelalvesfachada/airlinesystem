CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`),
  UNIQUE KEY `city_name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_loc_id` int(11) NOT NULL,
  `to_loc_id` int(11) NOT NULL,
  `dept_time` datetime NOT NULL,
  `arrival_time` datetime NOT NULL,
  `rem_capacity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `from_loc_id_UNIQUE` (`from_loc_id`),
  UNIQUE KEY `to_loc_id_UNIQUE` (`to_loc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
