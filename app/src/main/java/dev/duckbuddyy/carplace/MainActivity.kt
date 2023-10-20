package dev.duckbuddyy.carplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.duckbuddyy.carplace.network.NetworkRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val response = NetworkRepository().getCars()
            println("Emirhan" + response)
        }
    }
}