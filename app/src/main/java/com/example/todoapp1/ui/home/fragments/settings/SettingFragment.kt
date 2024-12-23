package com.example.todoapp1.ui.home.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.todoapp1.R
import com.example.todoapp1.databinding.FragmentSettingBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import java.util.Locale

class SettingFragment : Fragment() {
    private lateinit var viewBinding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSettingBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.spinnerLanguage.onItemSelectedListener = object : OnItemSelectedListener,
            AdapterView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return true
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                changeAppLanguage(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        viewBinding.spinnerMode.onItemSelectedListener = object : OnItemSelectedListener,
            AdapterView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return true
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                changeAppMode(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun changeAppMode(position: Int) {
        if (position == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (position == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun changeAppLanguage(position: Int) {
        if (position == 1) {
            val local = LocaleListCompat.create(Locale("en"))
            AppCompatDelegate.setApplicationLocales(local)
        }
        if (position == 2) {
            val local = LocaleListCompat.create(Locale("ar"))
            AppCompatDelegate.setApplicationLocales(local)
        }
    }
}