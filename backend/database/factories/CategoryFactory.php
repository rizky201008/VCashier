<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Category>
 */
class CategoryFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {

        $appLogic = new \App\Logic\App();

        $name = $this->faker->name;

        $slug = $appLogic->createSlug($name);

        return [
            'name' => $name,
            'slug' => $slug,
        ];
    }
}
