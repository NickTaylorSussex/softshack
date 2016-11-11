/* Eighth script to execute */

INSERT INTO processed_DBNAME (postcode,avgYearPostcodeNorm,latitude,longitude,yearSold)
SELECT postcode, round(avg(norm)), AVG(latitude), AVG(longitude), yearSold FROM clean_properties GROUP BY postcode, yearSold
