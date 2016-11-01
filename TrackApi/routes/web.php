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

$app->get('/{paramX}&{paramY}', function ($paramX, $paramY) use ($app) {

    $results = DB::select("SELECT *, ( 3959 * acos( cos( radians($paramX) )
    * cos( radians( latitude ) ) * cos( radians( longitude )
    - radians($paramY) ) + sin( radians($paramX) )
    * sin( radians( latitude ) ) ) ) AS distance FROM properties
    WHERE dateSold > '2016-01-01' HAVING distance < 90
    ORDER BY distance LIMIT 0 , 1000");

    return $results;

});


$app->get('/', function () use ($app) {
    return $app->version();
});
