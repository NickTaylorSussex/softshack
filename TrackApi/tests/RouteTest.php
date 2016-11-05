<?php

use Laravel\Lumen\Testing\DatabaseTransactions;

class RouteTest extends TestCase
{
    /**
     * A basic test example.
     *
     * @return void
     */
    public function testRoute()
    {
      $this->json('GET', '/clean/50.8409070&-0.1208230/2016&25')
        ->seeJson([
          'avgYearPostcodeNorm' => 100,
        ]);
    }
}
