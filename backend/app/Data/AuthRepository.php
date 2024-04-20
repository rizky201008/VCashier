<?php

namespace App\Data;

use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Arr;
use Illuminate\Support\Facades\Auth;

class AuthRepository
{
    protected User $user;

    public function __construct()
    {
        $this->user = new User();
    }


    public function login(array $data): JsonResponse
    {
        if ($this->authenticate($data)) {
            $user = $this->user->where('email', $data['email'])->first();
            $ability = $user->role;
            $token = $this->createToken($user, $ability);

            return response()->json([
                'message' => 'Login success 🎉🎉🎉',
                'token' => $token
            ], 200);
        } else {
            return response()->json([
                'message' => 'Login failed please check your credential',
            ], 401);
        }
    }

    public function register(array $data): JsonResponse
    {
        $created = $this->createUser($data);

        $ability = ($created->role == 'admin') ? 'admin' : 'password';
        $token = $created->createToken('access-token', [$ability]);

        return response()->json([
            'message' => 'Create account success 🎉🎉🎉',
            'token' => $token->plainTextToken
        ], 200);
    }

    private function authenticate(array $data): bool
    {
        return Auth::attempt([
            'email' => $data['email'],
            'password' => $data['password']
        ]);
    }

    private function createToken(User $user, string $ability): string
    {
        return $user->createToken('access-token', [$ability])->plainTextToken;
    }

    private function createUser(array $data): User
    {
        return $this->user->create([
            'email' => $data['email'],
            'password' => $data['password'],
            'name' => $data['name'],
            'role' => $data['role']
        ]);
    }
}
