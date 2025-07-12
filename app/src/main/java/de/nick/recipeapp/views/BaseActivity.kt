package de.nick.recipeapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.nick.recipeapp.R

open class BaseActivity : AppCompatActivity() {

    // Use a custom layout that includes a toolbar and the main content area
    fun setContentWithBaseLayout(layoutResId: Int) {
        super.setContentView(R.layout.activity_base)

        val toolbar = findViewById<Toolbar>(R.id.appToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShowBackButton())

        // Add the actual content below the toolbar
        val baseContainer = findViewById<LinearLayout>(R.id.baseContainer)
        val contentView = LayoutInflater.from(this).inflate(layoutResId, baseContainer, false)
        baseContainer.addView(contentView)
    }

    // Decide if the screen should show a back button
    open fun shouldShowBackButton(): Boolean = false

    // Handle toolbar back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    // Override to make sure the base layout is used
    override fun setContentView(layoutResID: Int) {
        setContentWithBaseLayout(layoutResID)
    }
}