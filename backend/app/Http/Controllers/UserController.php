<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class UserController extends Controller
{
    public function getRole(Request $request): JsonResponse
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

    public function allUsers(Request $request): JsonResponse
    {
        return response()->json([
            'data' => User::all()
        ]);
    }

    public function deleteUser($id): JsonResponse {
        User::find($id)->delete();
        return response()->json([
            'message' => 'User deleted'
        ]);
    }
}
