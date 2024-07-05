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

    function resetPassword($id): JsonResponse
    {
        try {
            $user = User::find($id);
            $user->password = bcrypt('password');
            $user->save();

            return response()->json([
                'message' => 'Password successfully reset'
            ], 200);

        } catch (\Exception $e) {
            return response()->json([
                'message' => "Failed to reset password " . $e->getMessage()
            ], 500);
        }
    }
}
