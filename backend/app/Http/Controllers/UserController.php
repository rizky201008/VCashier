<?php

namespace App\Http\Controllers;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class UserController extends Controller
{
    public function getRole(Request $request) : JsonResponse
    {
        return response()->json([
            'role' => $request->user()->role
        ]);
    }

    public function validateToken(Request $request): JsonResponse
    {
        return response()->json([
            'message' => 'Validated'
        ]);
    }
}
