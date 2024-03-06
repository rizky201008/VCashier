<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use App\Models\User;
use Illuminate\Http\Request;
use App\Models\ResetPassword;
use App\Mail\ResetPasswordCode;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Mail;

class AuthController extends Controller
{
    function login(Request $req)
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

    function register(Request $req)
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

    function logout(Request $req)
    {
        // Revoke the token that was used to authenticate the current request...
        $req->user()->currentAccessToken()->delete();

        return response()->json([
            'message' => 'Successfully logged out!'
        ], 200);
    }

    function changePassword(Request $req)
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

    public function generateCode()
    {
        // Mendapatkan tanggal saat ini
        $dateTime = Carbon::now();

        $currentDate = date_format($dateTime, "d");

        // Mendapatkan jam saat ini
        $currentHour = str_pad($dateTime->hour, 2, '0', STR_PAD_LEFT);

        // Mendapatkan menit saat ini
        $currentMinute = str_pad($dateTime->minute, 2, '0', STR_PAD_LEFT);

        // Angka random 3 digit
        $randomDigits = str_pad(mt_rand(0, 999), 3, '0', STR_PAD_LEFT);

        // Menggabungkan semua komponen untuk mendapatkan kode 9 digit
        $generatedCode = $currentDate . $currentHour . $currentMinute . $randomDigits;

        // Simpan kode ke database atau lakukan operasi lain sesuai kebutuhan
        // DB::table('your_table')->insert(['code' => $generatedCode]);

        // Tampilkan atau kembalikan kode yang dihasilkan
        return $generatedCode;
    }
}
