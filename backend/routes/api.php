<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\DashboardController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\ProductImageController;
use App\Http\Controllers\TransactionController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\VariationController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use SebastianBergmann\CodeCoverage\Report\Html\Dashboard;

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

Route::group(['middleware' => 'auth:sanctum'], function () {
    Route::get('categories', [CategoryController::class, 'getCategories']);
    Route::get('categories/{slug}', [CategoryController::class, 'getCategory']);
    Route::post('categories', [CategoryController::class, 'createCategory']);
    Route::post('categories', [CategoryController::class, 'createCategory']);
    Route::put('categories', [CategoryController::class, 'updateCategory']);
    Route::delete('categories/{id}', [CategoryController::class, 'deleteCategory']);
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
});

Route::prefix('auth')->group(function () {
    Route::post('login', [AuthController::class, 'login']);
    Route::post('register', [AuthController::class, 'register']);
    Route::post('logout', [AuthController::class, 'logout'])->middleware('auth:sanctum');
    Route::post("change-password", [AuthController::class, 'changePassword'])->middleware('auth:sanctum');
});
