<?php

//TODO: Tidy up routes by moving some code to outside functions.
//TODO: Add zoom level as a parameter to the grid.
//TODO: Add request type to log.

/**
* Listen for a get request, create a query for the processed_clean_properties table based on the Haversine formula.
*
* @param  string   $androidId       Android device id
* @param  decimal  $paramLatitude   Latitude coordinate
* @param  decimal  $paramLongitude  Longitude coordinate
* @param  int      $paramYear       Year of sale
* @param  int      $paramRadious    Radious used by the Haversine formula
* @param  int      $paramLimit      Limit for the number of results returned
* @param  int      $paramZoom       Map zoom level
*
* @return array   $results  Results of the SQL query
*/
$app->get('/{androidId}/clean/{paramLatitude}&{paramLongitude}/{paramYear}&{paramRadious}&{paramLimit}&{paramZoom}', function ($androidId, $paramLatitude, $paramLongitude, $paramYear, $paramRadious, $paramLimit, $paramZoom) use ($app) {

    //Create the destination table variable that will be passed to the query based on the paramYear passed.
    $destinationTable = "processed_clean_" . strval($paramYear) . "_properties";
    //Haversine formula in SQL
    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM ($destinationTable)
    HAVING distance < ($paramRadious) ORDER BY distance LIMIT 0, $paramLimit");

    //Get current datetime
    $ldate = date('Y-m-d H:i:s');
    //Insert the request into the log table
    DB::insert('INSERT INTO request_log (androidId, requestLatitude, requestLongitude, requestTime) values (?, ?, ?, ?)', [$androidId, $paramLatitude, $paramLongitude, $ldate]);

    //Average values that fall within a grid cell
    $mapGrid = false;
    $mapData = false;
    $radius = 0.001;
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

/**
* Listen for a get request, create a query for the graph_properties table based on the Haversine formula.
*
* @param  string   $androidId       Android device id
* @param  decimal  $paramLatitude   Latitude coordinate
* @param  decimal  $paramLongitude  Longitude coordinate
* @param  int      $paramRadious    Radious used by the Haversine formula
* @param  int      $paramLimit      Limit for the number of results returned
*
* @return array   $results  Results of the SQL query
*/
$app->get('/{androidId}/graph/{paramLatitude}&{paramLongitude}/{paramRadious}&{paramLimit}', function ($androidId, $paramLatitude, $paramLongitude, $paramRadious, $paramLimit) use ($app) {

    //Haversine formula in SQL
    $results = DB::select("SELECT yearSold, averagePrice, norm, ( 3959 * acos( cos( radians($paramLatitude) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramLongitude) ) + sin( radians($paramLatitude) )
    * sin( radians( latitude ) ) ) ) AS distance FROM graph_properties
    HAVING distance < ($paramRadious) ORDER BY distance LIMIT 0, $paramLimit");

    //Get current datetime
    $ldate = date('Y-m-d H:i:s');
    //Insert the request into the log table
    DB::insert('INSERT INTO request_log (androidId, requestLatitude, requestLongitude, requestTime) values (?, ?, ?, ?)', [$androidId, $paramLatitude, $paramLongitude, $ldate]);

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
    WHERE yearSold > ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});
