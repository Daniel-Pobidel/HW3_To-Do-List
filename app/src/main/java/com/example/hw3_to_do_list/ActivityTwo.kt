package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_two.*
import java.util.ArrayList

class ActivityTwo : AppCompatActivity() {

    private var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
    }

    fun returnToFirstActivity(view: View) //go back button
    {
        finish() // Stops and closes the second activity
    }

    fun saveMore(view: View) //continue adding to list button
    {
        if(addToListText.text.isEmpty())
        {
            Toast.makeText(this,"Please enter a task", Toast.LENGTH_SHORT).show()
        }
        else
        {
            list.add(addToListText.text.toString())
            addToListText.text.clear()
        }
    }

    fun returnDataToFirstActivity(view: View) //save and go back
    {
        // Need to create an intent to go back
        val myIntent = Intent()

        if(addToListText.text.isEmpty())
        {
            Toast.makeText(this,"Please enter a task", Toast.LENGTH_SHORT).show()
        }
        else
        {
            //add to list
            list.add(addToListText.text.toString())

            // Store any extra data in the intent
            myIntent.putStringArrayListExtra("task", list)

            // Set the activity's result to RESULT_OK
            setResult(Activity.RESULT_OK, myIntent)
            finish()
        }
    }

}
