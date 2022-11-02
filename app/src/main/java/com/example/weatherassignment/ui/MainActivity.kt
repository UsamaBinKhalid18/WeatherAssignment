package com.example.weatherassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.weatherassignment.R
import com.example.weatherassignment.database.ReadingsDataBase
import com.example.weatherassignment.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val sharedPreferences by lazy {
        getSharedPreferences("DBPrefs", MODE_PRIVATE)
    }
    private val KEY="populated"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            val populateDB =
                sharedPreferences
                    .getBoolean(KEY, false)
            if (!populateDB) {
                ReadingsDataBase.updateDataBase(this@MainActivity)
                sharedPreferences.edit().putBoolean(KEY, true).apply()
            }
        }

        supportFragmentManager.commit {
            add(R.id.main_fragment_container, ListFragment())
        }
    }
}
