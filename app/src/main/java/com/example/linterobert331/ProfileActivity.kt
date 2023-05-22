package com.example.linterobert331

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.Intent
import android.graphics.Outline
import android.media.RouteListingPreference
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewOutlineProvider
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = FirebaseAuth.getInstance()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.elevation = 0f
        val drawerLayout: DrawerLayout = findViewById(R.id.profileDrawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> startActivity(Intent(this, MenuActivity::class.java))
                R.id.nav_profile -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_map -> startActivity(Intent(this, ClientMapActivity::class.java))
                R.id.nav_workout -> startActivity(Intent(this, WorkoutsActivity::class.java))
                R.id.nav_logout -> {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }


            }
            true
        }

        var loginButton = findViewById<Button>(R.id.buttonEdit)
        loginButton.setOnClickListener{
            showCustomDialog();
        }
    }

    fun showCustomDialog() {
        val dialog = Dialog(this@ProfileActivity)
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true)
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.activity_edit_profile);

        val months = arrayOf(
            "Ian",
            "Feb",
            "Mar",
            "Apr",
            "Mai",
            "Iun",
            "Iul",
            "Aug",
            "Sep",
            "Oct",
            "Noi",
            "Dec"
        )

        val numberPicker = dialog.findViewById<NumberPicker>(R.id.heightPicker)
        numberPicker.minValue = 50
        numberPicker.maxValue = 100
        numberPicker.value = 60

        val dayPicker = dialog.findViewById<NumberPicker>(R.id.dayPicker)
        dayPicker.minValue = 1
        dayPicker.maxValue = 31
        dayPicker.value = 1

        var monthPicker = dialog.findViewById<NumberPicker>(R.id.monthPicker)
        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.value = 1
        monthPicker.displayedValues = months

        var yearPicker = dialog.findViewById<NumberPicker>(R.id.yearPicker)
        yearPicker.minValue = 1900
        yearPicker.maxValue = 2023
        yearPicker.value = 2000

        val submitButton: Button = dialog.findViewById(R.id.submit_button)
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}