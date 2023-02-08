package com.meanwhile.cancellationtest

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helloText = findViewById<TextView>(R.id.hello)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {

                    when (it){
                        is UiState.Success -> helloText.text = it.text
                        is UiState.Error -> MaterialAlertDialogBuilder(this@MainActivity)
                            .setTitle("Error!")
                            .setMessage("I shouldn't be here :O")
                            .show()
                    }
                }
            }
        }
    }
}