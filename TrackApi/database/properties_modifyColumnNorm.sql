/* Sixth script to execute */

update forge.DBNAME p set norm = round(1+((p.price - PUT_MIN_PRICE_HERE)*(100-1)/(PUT_MAX_PRICE_HERE - PUT_MIN_PRICE_HERE)));

/* Add index based on norm column after execution manually. */
