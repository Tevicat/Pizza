package com.example.bitsandpizzas

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PizzaDetailActivity : AppCompatActivity() {
    // val extraPizzaId = "pizzaId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
    }
}
