# CookIt — Recipe App
**An Android app to browse, search and favourite recipes from TheMealDB.**

## About
CookIt helps anyone who’s tired of deciding what to cook — or who already knows what they want and just needs the right recipe. Get a random meal, search by text, or pick a category; view ingredients and instructions, then save favourites locally for quick access later.
This project is made with beginners in mind and keeps things small and easy to understand.

## Features
- Get a random recipe.
- Search recipes by text.
- Browse recipes by simple category buttons.
- View recipe details (image, ingredients, instructions).
- Save favourites locally and view them (also offline).

## How it works
- The app fetches recipe data from the free public TheMealDB API.
- Images are loaded with Coil.
- Background work (network calls) is done with Kotlin coroutines so the app stays responsive.
- Favourites are stored locally as a small JSON list using SharedPreferences — no database setup required.

## Requirements
- Android Studio (latest stable recommended)
- Java 11 (project configured to use Java 11)
- Minimum Android version: API 24 (Android 7.0)
- Compile / target SDK: API 36
- Kotlin: 2.0.21

## Quick start
1. Clone the repository:
    - ```git clone https://github.com/your-username/your-repo.git```

2. Open the project in Android Studio (File → Open → choose the project folder).

3. Let Android Studio download dependencies and sync the project.

4. Run the app on a device or emulator using the green ▶ Run button.

## Minimal Android manifest note
The app needs permission to use the internet. Make sure AndroidManifest.xml contains:

```<uses-permission android:name="android.permission.INTERNET" />```

## Where favourites are stored
Favourited recipes are saved locally using SharedPreferences as a JSON list (serialized with Moshi). This keeps the app simple and avoids the need for a database.

## API - TheMealDB
The app uses TheMealDB base URL: https://www.themealdb.com/api/json/v1/1/

The code calls ```search.php``` and ```random.php``` endpoints to get recipes. No API key is required for these calls.

## Known limitations & possible improvements
- Ingredients are parsed up to 10 ingredient/measure pairs (matching the API response).
- There is no offline caching — the app needs a network connection to fetch recipes.
- Network calls are made directly from Activities; moving them to ViewModels would improve structure for larger apps.
- Category searching currently relies on the same search endpoint — you could switch to the API’s category endpoint for more precise results.

## Author
Nick Lambertz
