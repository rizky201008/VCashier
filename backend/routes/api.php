<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\CustomerController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\ProductImageController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return response()->json(['status' => 'ok']);
});

Route::group(['middleware' => 'auth:sanctum'], function () {
    Route::prefix('categories')->group(function () {
        Route::get('/', [CategoryController::class, 'getCategories']);
        Route::get('{slug}', [CategoryController::class, 'getCategory']);
        Route::post('create', [CategoryController::class, 'createCategory']);
        Route::put('update', [CategoryController::class, 'updateCategory']);
        Route::delete('{id}', [CategoryController::class, 'deleteCategory']);
    });
    Route::post('products', [ProductController::class, 'createProduct']);
    Route::put('products/{id}', [ProductController::class, 'updateProduct']);
    Route::delete('products/{id}', [ProductController::class, 'deleteProduct']);
    Route::get('products/{id}', [ProductController::class, 'getProduct']);
    Route::get('products', [ProductController::class, 'getProducts']);
    Route::group(['prefix' => 'product-images'], function () {
        Route::post('create', [ProductImageController::class, 'createImages']);
        Route::post('update', [ProductImageController::class, 'updateImages']);
        Route::delete('delete-all', [ProductImageController::class, 'deleteImages']);
        Route::delete('delete-one', [ProductImageController::class, 'deleteImage']);
    });
    Route::post('customers', [CustomerController::class, 'createCustomer']);
    Route::put('customers/{id}', [CustomerController::class, 'updateCustomer']);
    Route::delete('customers/{id}', [CustomerController::class, 'deleteCustomer']);
    Route::get('customers/{id}', [CustomerController::class, 'getCustomer']);
    Route::get('customers', [CustomerController::class, 'getCustomers']);
});

Route::prefix('auth')->group(function () {
    Route::post('login', [AuthController::class, 'login']);
    Route::post('register', [AuthController::class, 'register']);
    Route::post('logout', [AuthController::class, 'logout'])->middleware('auth:sanctum');
    Route::post("change-password", [AuthController::class, 'changePassword'])->middleware('auth:sanctum');
});
