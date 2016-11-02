/* Eighth script to execute */

insert forge.processed_properties (postcode,avgYearPostcodeNorm,latitude,longitude,yearSold)
select postcode, round(avg(inflatedNorm)), latitude, longitude, yearSold FROM forge.properties group by postcode, yearSold
