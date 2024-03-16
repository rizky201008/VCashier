<?php

namespace App\Logic;

use Illuminate\Support\Str;

class App
{
    public function createSlug($name)
    {
        return Str::slug(round(microtime(true) * 1000) . '-' . $name, '-');
    }
}
