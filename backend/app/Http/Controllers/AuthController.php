<?php

namespace App\Http\Controllers;

use App\Repository\AuthRepository;
use Illuminate\Http\Request;
use \Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{

    private AuthRepository $authRepository;

    public function __construct()
    {
        $this->authRepository = new AuthRepository();
    }

    function login(Request $req): JsonResponse
    {
        $req->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);

        return $this->authRepository->login($req->all());
    }

    function register(Request $req): JsonResponse
    {
        $req->validate([
            'email' => 'required|email|unique:users,email',
            'password' => 'required|min:8',
            'name' => 'required',
            'role' => 'required|in:cashier,warehouse'
        ]);

        $data = $req->all();
        return $this->authRepository->register($data);
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

    function resetPassword(Request $req): JsonResponse
    {
        $req->validate([
            'email' => 'required|email'
        ]);
        $user = $req->user();
        $user->password = bcrypt('password');
        $user->save();

        return response()->json([
            'message' => 'Password successfully reset'
        ], 200);
    }
}
