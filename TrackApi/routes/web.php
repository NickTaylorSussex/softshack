<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$app->get('/clean/{paramX}&{paramY}/{paramYear}&{paramRadious}&{paramLimit}', function ($paramX, $paramY, $paramYear, $paramRadious, $paramLimit) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold >= ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, $paramLimit");

    return $results;

});

$app->get('/cleanDebug/{paramX}&{paramY}/{paramYear}&{paramRadious}', function ($paramX, $paramY, $paramYear, $paramRadious) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold >= ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, 10000");

    return $results;

});

$app->get('/dirty/{paramX}&{paramY}/{paramYear}&{paramRadious}', function ($paramX, $paramY, $paramYear, $paramRadious) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_dirty_properties
    WHERE yearSold >= ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, 10000");

    return $results;

});

$app->get('/dirtyDebug/{paramX}&{paramY}/{paramYear}&{paramRadious}', function ($paramX, $paramY, $paramYear, $paramRadious) use ($app) {

    $results = DB::select("SELECT latitude, longitude, avgYearPostcodeNorm, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_dirty_properties
    WHERE yearSold >= ($paramYear) HAVING distance < ($paramRadious)
    ORDER BY distance LIMIT 0, 10000");

    return $results;

});

$app->get('/', function () use ($app) {
    return $app->version();
});
