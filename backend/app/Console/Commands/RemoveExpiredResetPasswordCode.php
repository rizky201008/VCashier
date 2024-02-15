<?php

namespace App\Console\Commands;

use Carbon\Carbon;
use App\Models\ResetPassword;
use Illuminate\Console\Command;

class RemoveExpiredResetPasswordCode extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'app:remove-expired-reset-password-code';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Remove expired password reset code';

    /**
     * Execute the console command.
     */
    public function handle()
    {
        $allData = ResetPassword::all();

        // Mendefinisikan batas waktu 5 menit dalam detik
        $timeLimit = 5 * 60; // 5 menit * 60 detik/menit

        foreach ($allData as $data) {
            // Konversi timestamp menjadi objek Carbon
            $timestampCarbon = Carbon::parse($data->created_at); // Gantilah your_timestamp_column dengan nama kolom timestamp di tabel Anda

            // Waktu saat ini
            $currentTime = Carbon::now();

            // Perbandingkan selisih waktu dalam detik
            $timeDifferenceInSeconds = $timestampCarbon->diffInSeconds($currentTime);

            // Jika waktu lebih dari 5 menit, hapus data
            if ($timeDifferenceInSeconds > $timeLimit) {
                $data->delete();
                $this->info("Expired reset password code deleted!");
            }
        }
        $this->info("Nothing data to delete!");
    }
}
