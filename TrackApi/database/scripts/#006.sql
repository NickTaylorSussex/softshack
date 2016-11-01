
SELECT max(inflatedPrice) FROM nicksdb.properties as maxprice;

SELECT min(inflatedPrice) FROM nicksdb.properties as minprice ;

/* then */

update nicksdb.properties p set inflatedNorm = round(1+((p.inflatedPrice - PUT_MIN_PRICE_HERE)*(100-1)/(PUT_MAX_PRICE_HERE - PUT_MIN_PRICE_HERE)));

/* then add an index to inflatedNorm column */