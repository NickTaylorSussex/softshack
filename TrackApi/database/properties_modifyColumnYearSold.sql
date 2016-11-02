/* Second script to execute */

UPDATE forge.properties
SET yearSold = YEAR(dateSold)

/* Add index based on yearSold column after execution manually. */
