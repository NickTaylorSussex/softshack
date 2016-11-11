/* Second script to execute */

UPDATE forge.DBNAME
SET yearSold = YEAR(dateSold)

/* Add index based on yearSold column after execution manually. */
