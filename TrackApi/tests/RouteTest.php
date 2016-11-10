<?php

use Laravel\Lumen\Testing\DatabaseTransactions;

class RouteTest extends TestCase
{
    /**
     * Test class for testing routes.
     *
     * @return void
     */
    public function testRoute()
    {
      //Test a valid route, expect true
      $this->json('GET', '/1234/clean/50.8409070&-0.1208230/2016&25&100&15')
        ->assertResponseOk();

      //Test a valid route
      $this->json('GET', '/dirty/50.8409070&-0.1208230/2016&25&100')
        ->assertResponseOk();

      //Test an invalid route
      $this->json('GET', '/clean/50.8409070&-0.1208230/2016&25&100')
        ->assertResponseStatus(404);

      //Test an invalid route
      $this->json('GET', '/1234/clean/50.8409070/2016&25&100&15')
        ->assertResponseStatus(404);

      //Test an invalid route
      $this->json('GET', '/50.8409070&-0.1208230/2016&25&100')
        ->assertResponseStatus(404);

      //Test an invalid route
      $this->json('GET', '/1234/clean/50.8409070&-0.1208230/2016&25')
        ->assertResponseStatus(404);

      //Test a valid route with invalid data
      $this->json('GET', '/1234/clean/0.000000&0.000000/2016&25&100&15')
        ->assertResponseStatus(500);

      //Test a valid route with invalid data
      $this->json('GET', '/1234/clean/999.000000&999.000000/2016&25&100&15')
        ->assertResponseStatus(500);
    }
}
