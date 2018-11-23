<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
  return view('index');
})->name('home');

Route::get('/dishes', function () {
  return view('dishes');
})->name('dishes');

Route::get('/dishes/{dish}', function ($dish) {
  return view('display', ["DISH_ID"=>$dish]);
})->name('details');

Route::get('/cal', function () {
  return view('cal');
})->name('cal');

Auth::routes();

//Route::get('/home', 'HomeController@index')->name('home');
