/* Sixth script to execute */

SELECT max(inflatedPrice) FROM forge.DBNAME as maxprice;
SELECT min(inflatedPrice) FROM forge.DBNAME as minprice;

update forge.DBNAME p set inflatedNorm = round(1+((p.inflatedPrice - PUT_MIN_PRICE_HERE)*(100-1)/(PUT_MAX_PRICE_HERE - PUT_MIN_PRICE_HERE)));

/* Add index based on inflatedNorm column after execution manually. */
