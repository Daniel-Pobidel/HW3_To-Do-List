package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_two.view.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 12
    private val TAG = "MainActivity"
    private val FILE_NAME = "TaskList"
    private val taskList = ArrayList<String>()
    lateinit var myAdapter: ArrayAdapter<String>
    private val MY_KEY = "taskList"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()

        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList)

        // set the adapter to listview
        task_list.adapter = myAdapter

        task_list.setOnItemLongClickListener{ parent, view, position, id ->
            taskList.removeAt(position)
            myAdapter.notifyDataSetChanged()
            save()
            if(taskList.isEmpty())
            {
                Toast.makeText(this,"All tasks completed!", Toast.LENGTH_SHORT).show()
            }
            return@setOnItemLongClickListener true
        }
    }


    fun openSecondActivity(view: View)
    {
        val myIntent = Intent(this, ActivityTwo::class.java)
        //myIntent.putStringArrayListExtra("list", taskList)
        startActivityForResult(myIntent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // come back from the second activity
            // extract the data from the intent
            val taskComingFromSecondActivity = data?.getStringArrayListExtra("task")

            Log.d(TAG, "task: $taskComingFromSecondActivity")

            // Do something with the data
            if (taskComingFromSecondActivity != null)
            {
                taskList.addAll(taskComingFromSecondActivity)
                myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList)
                // set the adapter to listview
                task_list.adapter = myAdapter


                save()
            }
        }
    }

    fun save()
    {
        // Create an instance of getSharedPreferences for edit
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Create an instance of Gson
        val gson = Gson()

        // toJson() method serializes the specified object into its equivalent Json representation.
        val taskListJson = gson.toJson(taskList)

        // Put the  Json representation, which is a string, into sharedPreferences
        editor.putString(MY_KEY, taskListJson)

        editor.apply() // Apply the changes

    }

    fun load()
    {
        // Create an instance of getSharedPreferences for retrieve the data
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Retrieve data using the key, default value is empty string in case no saved data in there
        val tasks = sharedPreferences.getString(MY_KEY, "") ?: ""

        if (tasks.isNotEmpty())
        {
            val gson = Gson()
            // create an object expression that descends from TypeToken
            // and then get the Java Type from that
            val sType = object : TypeToken<List<String>>() {}.type
            val savedTaskList = gson.fromJson<List<String>>(tasks, sType)

            Log.d(TAG, savedTaskList.toString())
            taskList.addAll(savedTaskList)

        }
    }
}
