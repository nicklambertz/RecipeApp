package de.nick.recipeapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.nick.recipeapp.R

open class BaseActivity : AppCompatActivity() {

    fun setContentWithBaseLayout(layoutResId: Int) {
        super.setContentView(R.layout.activity_base)

        val toolbar = findViewById<Toolbar>(R.id.appToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Rezepte-App"
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShowBackButton())

        val baseContainer = findViewById<LinearLayout>(R.id.baseContainer)
        val contentView = LayoutInflater.from(this).inflate(layoutResId, baseContainer, false)
        baseContainer.addView(contentView)
    }

    open fun shouldShowBackButton(): Boolean = false

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun setContentView(layoutResID: Int) {
        setContentWithBaseLayout(layoutResID)
    }
}