/* Eighth script to execute */

insert forge.processed_DBNAME (postcode,avgYearPostcodeNorm,latitude,longitude,yearSold)
select postcode, round(avg(inflatedNorm)), latitude, longitude, yearSold FROM forge.DBNAME group by postcode, yearSold
