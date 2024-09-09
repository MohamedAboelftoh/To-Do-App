package com.example.todoapp1.ui.home

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todoapp1.R
import com.example.todoapp1.databinding.ActivityHomeBinding
import com.example.todoapp1.ui.home.fragments.AddTaskFragment
import com.example.todoapp1.ui.home.fragments.ListFragment
import com.example.todoapp1.ui.home.fragments.SettingFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityHomeBinding
     var listFragment: ListFragment ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnNavigation.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.setting ->{
                    pushFragment(SettingFragment())
                }
                R.id.list -> {
                    listFragment = ListFragment()
                    pushFragment(listFragment!!)
                }
            }
            return@setOnItemSelectedListener true
        }
        viewBinding.btnNavigation.selectedItemId = R.id.list
        viewBinding.fab.setOnClickListener {
            showAddTask()
        }
    }

    private fun showAddTask() {
        val addTask = AddTaskFragment()
        addTask.onTaskAddedListener = object : AddTaskFragment.OnTaskAddedListener{
            override fun onTaskAdded() {
                listFragment?.loadTasks()
            }
        }
        addTask.show(supportFragmentManager,"")
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }


}