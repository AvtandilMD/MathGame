package com.latsabidze.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var addition: Button
    lateinit var subtraction: Button
    lateinit var multi: Button
    lateinit var divide: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        divide = findViewById(R.id.buttonDiv)
        addition = findViewById(R.id.buttonAdd)
        subtraction = findViewById(R.id.buttonSub)
        multi = findViewById(R.id.buttonMulti)

        addition.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }
        subtraction.setOnClickListener {
            val intent = Intent(this@MainActivity, MinusActivity::class.java)
            startActivity(intent)
        }
        multi.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            startActivity(intent)
        }
        divide.setOnClickListener {
            val intent = Intent(this@MainActivity, DivideActivity::class.java)
            startActivity(intent)
        }
    }
    }
