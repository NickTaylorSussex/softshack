/* Seventh script to execute */

CREATE TABLE `processed_properties` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `postcode` char(8) NOT NULL,
  `avgYearPostcodeNorm` int(11) NOT NULL,
  `latitude` decimal(8,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  `yearSold` int(4) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
