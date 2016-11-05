<?php

//TODO: Create stored procedures on the mysql database to be called from the routes rather then having to create a query everytime a route is called.

/**
* Listen for a get request, create a query for the processed_clean_properties table based on the Haversine formula.
*
* @param  decimal  $paramLatitude   Latitude coordinate
* @param  decimal  $paramLongitude  Longitude coordinate
* @param  int      $paramYear       Year of sale
* @param  int      $paramRadious    Radious used by the Haversine formul
* @param  int      $paramLimit      Limit for the number of results returned
*
* @return array   $results  Results of the SQL query
*/
$app->get('/clean/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold = ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});

//TODO: Delete for production.
$app->get('/cleanUnordered/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold = ($paramYear) HAVING distance < ($paramRadious)
    LIMIT 0, $paramLimit");

    return $results;

});

//TODO: Delete for production.
$app->get('/cleanDebug/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold = ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});

/**
* Listen for a get request, create a query for the processed_dirty_properties table based on the Haversine formula.
*
* @param  decimal  $paramLatitude   Latitude coordinate
* @param  decimal  $paramLongitude  Longitude coordinate
* @param  int      $paramYear       Year of sale
* @param  int      $paramRadious    Radious used by the Haversine formul
* @param  int      $paramLimit      Limit for the number of results returned
*
* @return array   $results  Results of the SQL query
*/
$app->get('/dirty/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_dirty_properties
    WHERE yearSold = ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});

//TODO: Delete for production.
$app->get('/dirtyDebug/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_dirty_properties
    WHERE yearSold = ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});

$app->get('/', function () use ($app) {
    return $app->version();
});
