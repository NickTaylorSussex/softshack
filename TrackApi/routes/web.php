<?php

//TODO: Create stored procedures on the mysql database to be called from the routes rather then having to create a query everytime a route is called.
//TODO: Create route to query all years.

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

    //Create the destination table variable that will be passed to the query based on the paramYear passed.
    $destinationTable = "processed_clean_" . strval($paramYear) . "_properties";

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM ($destinationTable)
    HAVING distance < ($paramRadious) ORDER BY distance LIMIT 0, $paramLimit");

    $mapGrid = false;
    $mapData = false;
    $radius = 0.001; //grid size is multiplied by 2.8 to convert from the heat map radius to lat/long values(works for my lat/long, maybe not yours). * 0.3 is arbitrary to avoid seeing the grid on the map.
    $string = json_encode($results);
    $json_a = json_decode($string, true);

    forEach($json_a as $key => $value){
      $row = intval(round(($value['latitude'] / $radius)));
      $column = intval(round(($value['longitude'] / $radius)/68*111)); //around 52.0;5.0 latitude needs to be scaled to make a square grid with the used longitude grid size
      if(isset($mapGrid[$row][$column])){
        $mapGrid[$row][$column] = round(($value['avgYearPostcodeNorm'] + $mapGrid[$row][$column]) / 2);
      } else {
        $mapGrid[$row][$column] = $value['avgYearPostcodeNorm'];
      }
    }

    forEach($mapGrid as $long => $array){
      forEach($array as $lat => $weight){
        $mapData[] = array(
          "latitude" => $long * $radius,
          "longitude" => ($lat * $radius)/111*68,
          "avgYearPostcodeNorm" => $weight
        );
      }
    }

    return $mapData;
    
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

//TODO: Remove for production, used for the default example test.
$app->get('/', function () use ($app) {
    return $app->version();
});
