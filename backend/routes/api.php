<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\CustomerController;
use App\Http\Controllers\PaymentMethodController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\ProductImageController;
use App\Http\Controllers\TransactionController;
use App\Http\Controllers\UserController;
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
        Route::get('{id}', [CategoryController::class, 'getCategory']);
        Route::post('create', [CategoryController::class, 'createCategory']);
        Route::put('update', [CategoryController::class, 'updateCategory']);
        Route::delete('{id}', [CategoryController::class, 'deleteCategory']);
    });
    Route::prefix('products')->group(function () {
        Route::get('/', [ProductController::class, 'getProducts']);
        Route::get('{id}', [ProductController::class, 'getProduct']);
        Route::post('/', [ProductController::class, 'createProduct']);
        Route::put('{id}', [ProductController::class, 'updateProduct']);
        Route::delete('{id}', [ProductController::class, 'deleteProduct']);
    });
    Route::prefix('product-images')->group(function () {
        Route::post('update', [ProductImageController::class, 'updateImages']);
    });
    Route::prefix('customers')->group(function () {
        Route::get('/', [CustomerController::class, 'getCustomers']);
        Route::post('/', [CustomerController::class, 'createCustomer']);
        Route::get('{id}', [CustomerController::class, 'getCustomer']);
        Route::delete('{id}', [CustomerController::class, 'deleteCustomer']);
        Route::put('{id}', [CustomerController::class, 'updateCustomer']);
    });
    Route::prefix('transactions')->group(function () {
        Route::get('/', [TransactionController::class, 'getTransactions']);
        Route::get('{id}', [TransactionController::class, 'getTransaction']);
        Route::post('/', [TransactionController::class, 'createTransaction']);
    });
    Route::prefix('users')->group(function () {
        Route::get('role', [UserController::class, 'getRole']);
        Route::get('validate', [UserController::class, 'validateToken']);
    });
    Route::prefix('payment-method')->group(function () {
        Route::get('/', [PaymentMethodController::class, 'getAllPaymentMethods']);
    });
});

Route::prefix('auth')->group(function () {
    Route::post('login', [AuthController::class, 'login']);
    Route::post('register', [AuthController::class, 'register']);
    Route::post('logout', [AuthController::class, 'logout'])->middleware('auth:sanctum');
    Route::post("change-password", [AuthController::class, 'changePassword'])->middleware('auth:sanctum');
});
