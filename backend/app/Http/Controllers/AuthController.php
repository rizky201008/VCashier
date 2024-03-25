<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use \Illuminate\Http\JsonResponse;

class AuthController extends Controller
{
    function login(Request $req): JsonResponse
    {
        $req->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);

        if (Auth::attempt([
            'email' => $req->email,
            'password' => $req->password
        ])) {
            $user = User::where('email', $req->email)->first();
            $ability = ($user->role == 'admin') ? 'admin' : 'password';
            $token = $user->createToken('access-token', [$ability]);

            return response()->json([
                'message' => 'Login success 🎉🎉🎉',
                'token' => $token->plainTextToken
            ], 200);
        } else {
            return response()->json([
                'message' => 'Login failed please check your credential',
            ], 401);
        }
    }

    function register(Request $req) : JsonResponse
    {
        $req->validate([
            'email' => 'required|email|unique:users,email',
            'password' => 'required|min:8',
            'name' => 'required'
        ]);

        $created = User::create([
            'email' => $req->email,
            'password' => $req->password,
            'name' => $req->name
        ]);

        $ability = ($created->role == 'admin') ? 'admin' : 'password';
        $token = $created->createToken('access-token', [$ability]);

        return response()->json([
            'message' => 'Create account success 🎉🎉🎉',
            'token' => $token->plainTextToken
        ], 200);
    }

    function logout(Request $req): JsonResponse
    {
        // Revoke the token that was used to authenticate the current request...
        $req->user()->currentAccessToken()->delete();

        return response()->json([
            'message' => 'Successfully logged out!'
        ], 200);
    }

    function changePassword(Request $req): JsonResponse
    {
        $req->validate([
            'current_password' => 'required',
            'new_password' => 'required|min:8'
        ]);

        $user = $req->user();

        if (!Hash::check($req->current_password, $user->password)) {
            return response()->json([
                'message' => 'Current password is wrong'
            ], 401);
        }

        $user->password = bcrypt($req->new_password);
        $user->save();

        return response()->json([
            'message' => 'Password successfully updated'
        ], 200);
    }
}
