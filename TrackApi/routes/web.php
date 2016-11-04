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

$app->get('/clean&{paramX}&{paramY}&{paramZ}', function ($paramX, $paramY, $paramZ) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold >= ($paramZ) HAVING distance < 100
    ORDER BY distance LIMIT 0 , 10000");

    return $results;

});

$app->get('/clean&{paramX}&{paramY}&{paramZ}&{rad}&{lim}', function ($paramX, $paramY, $paramZ, $rad, $lim) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_clean_properties
    WHERE yearSold >= ($paramZ) HAVING distance < ($rad)
    ORDER BY distance LIMIT 0 , ($lim)");

    return $results;

});

$app->get('/dirty&{paramX}&{paramY}', function ($paramX, $paramY) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM processed_dirty_properties
    WHERE yearSold > '2015' HAVING distance < 100
    ORDER BY distance LIMIT 0 , 1000");

    return $results;

});

$app->get('/', function () use ($app) {
    return $app->version();
});
