<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\CustomerController;
use App\Http\Controllers\PaymentController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\ProductImageController;
use App\Http\Controllers\ProductLogsController;
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
        Route::post('/', [CategoryController::class, 'createCategory']);
        Route::put('/', [CategoryController::class, 'updateCategory']);
    });
    Route::prefix('products')->group(function () {
        Route::post('/', [ProductController::class, 'createProduct']);
        Route::put('/', [ProductController::class, 'updateProduct']);
    });
    Route::prefix('product-variations')->group(function () {
        Route::put('/', [ProductController::class, 'updateProductVariation']);
        Route::get('/{id}', [ProductController::class, 'getProductVariation']);
    });
    Route::prefix('product-images')->group(function () {
        Route::post('update', [ProductImageController::class, 'updateImages']);
        Route::post('add', [ProductImageController::class, 'addImage']);
    });
    Route::prefix('customers')->group(function () {
        Route::get('/', [CustomerController::class, 'getCustomers']);
        Route::post('/', [CustomerController::class, 'createCustomer']);
        Route::get('/{id}', [CustomerController::class, 'getCustomer']);
        Route::put('/', [CustomerController::class, 'updateCustomer']);
    });
    Route::prefix('transactions')->group(function () {
        Route::get('all-transactions', [TransactionController::class, 'getTransactions']);
        Route::get('get-transaction/{id}', [TransactionController::class, 'getTransaction']);
        Route::post('create-transaction', [TransactionController::class, 'createTransaction']);
        Route::put('update-transaction', [TransactionController::class, 'updateTransaction']);
        Route::get('reports', [TransactionController::class, 'transactionReport']);
    });
    Route::prefix('users')->group(function () {
        Route::get('role', [UserController::class, 'getRole']);
        Route::get('validate', [UserController::class, 'validateToken']);
        Route::get('lists', [UserController::class, 'allUsers']);
        Route::post("reset-password/{id}", [UserController::class, 'resetPassword']);
        Route::put('update', [UserController::class, 'updateUser']);
    });
    Route::prefix('payment')->group(function () {
        Route::get('methods', [PaymentController::class, 'getPaymentMethods']);
        Route::post('make-payment', [PaymentController::class, 'createTransactionPayment']);
        Route::post('create-va', [PaymentController::class, 'createVa']);
        Route::get('check-status/{id}', [PaymentController::class, 'checkStatus']);
    });
    Route::prefix('product-logs')->group(function () {
        Route::get('all', [ProductLogsController::class, 'getAllLogs']);
        Route::post('add', [ProductLogsController::class, 'addLog']);
    });
});
Route::get('products', [ProductController::class, 'getProducts']);
Route::get('products/{id}', [ProductController::class, 'getProduct']);
Route::post('midtrans', [PaymentController::class, 'midtrans']);

Route::prefix('auth')->group(function () {
    Route::post('login', [AuthController::class, 'login']);
    Route::post('register', [AuthController::class, 'register']);
    Route::post('logout', [AuthController::class, 'logout'])->middleware('auth:sanctum');
    Route::post("change-password", [AuthController::class, 'changePassword'])->middleware('auth:sanctum');
});
