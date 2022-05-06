package com.argz.issue3700

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun retrieveFile(view: View) {
        GlobalScope.launch {
            reproduceBug()
        }
    }

    suspend fun reproduceBug() {
        // STEP 1 - turn off network connection (wifi/mobile)
        val task = FirebaseStorage.getInstance()
            .getReference("potato.png")
            .getFile(File("potato.png"))
        task.addOnCanceledListener {
            Log.d(TAG, "reproduceBug: failure retrieval")
            Toast.makeText(this, "failure retrieval", Toast.LENGTH_SHORT).show()
            // even though task.cancel() returns true,
            // listener will not be called until network connection gets established
        }
        task.addOnCompleteListener {
            Log.d(TAG, "reproduceBug: successful retrieval")
            Toast.makeText(this, "successful retrieval", Toast.LENGTH_SHORT).show()
            // even though task.cancel() returns true,
            // listener will not be called until network connection gets established
        }
        Log.d(TAG, "initiate delay")
        delay(5000)
        Log.d(TAG, "delay finish")
        val isCanceled = task.cancel() // returns true, but not canceled
        Log.d(TAG, "task cancel finish")
    }
}