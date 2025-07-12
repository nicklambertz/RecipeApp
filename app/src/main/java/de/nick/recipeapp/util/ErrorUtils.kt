package de.nick.recipeapp.util

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import de.nick.recipeapp.R

object ErrorUtils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showNoResults(textView: TextView) {
        val context = textView.context
        textView.text = context.getString(R.string.error_no_results)
        textView.visibility = TextView.VISIBLE
    }

    fun handleFavoriteError(context: Context, e: Exception) {
        Log.e("Favorites", "Error with favorites function: ${e.message}", e)
        showToast(context, context.getString(R.string.error_favorites))
    }

    fun handleApiError(context: Context, e: Exception) {
        Log.e("API", "Error when retrieving recipes: ${e.message}", e)
        showToast(context, context.getString(R.string.error_api_request))
    }
}
