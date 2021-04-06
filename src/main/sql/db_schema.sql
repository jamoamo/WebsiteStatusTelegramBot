-- Dumping database structure for telegram_bot
CREATE DATABASE IF NOT EXISTS `telegram_bot` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `telegram_bot`;

-- Dumping structure for table telegram_bot.telegram_update
CREATE TABLE IF NOT EXISTS `telegram_update` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `update_id` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
