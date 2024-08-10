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

    public function updateUser(Request $request): JsonResponse
    {
        try {
            $data = $request->all();
            $user = User::find($data['id']);
            $user->update($data);
            return response()->json([
                'message' => 'User updated successfully'
            ], 200);
        } catch (\Exception $e) {
            return response()->json([
                'message' => "Failed to update user " . $e->getMessage()
            ], 500);
        }
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
