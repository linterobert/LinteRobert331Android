package com.example.linterobert331

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class WorkoutsActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Workouts>
    private lateinit var searchView: SearchView
    private lateinit var workoutsAdapter: WorkoutsAdapter
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workouts)
        auth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.elevation = 0f
        val drawerLayout: DrawerLayout = findViewById(R.id.workoutsDrawerLayout)
        val navView: NavigationView = findViewById(R.id.workouts_nav_view)

        toggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> startActivity(Intent(this, MenuActivity::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.nav_map -> startActivity(Intent(this, ClientMapActivity::class.java))
                R.id.nav_workout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_logout -> {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_shareLocation -> {
                    val url = "https://www.youtube.com/watch?v=JZehdUU6VbQ"
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra("Share this profile", url)
                    val chooser = Intent.createChooser(intent, "Share using...")
                    startActivity(chooser)
                }
            }
            true
        }
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return false
            }
        })


        imageId = arrayOf(
            R.drawable.back1,
            R.drawable.back2,
            R.drawable.back3,
            R.drawable.chest1,
            R.drawable.chest2,
            R.drawable.chest3,
            R.drawable.shoulders1,
            R.drawable.shoulders2,
            R.drawable.shoulders3,
            R.drawable.biceps1,
            R.drawable.triceps1
        )

        heading = arrayOf(
            "Back: Cable Standing Row \n 10-50, 10-60, 6-70, 10-60, 10-50",
            "Back: Cable Seated Wide grip Row \n 10-50, 10-60, 6-70, 10-60, 10-50",
            "Back: Cable Wide Pulldown \n 10-50, 10-60, 6-70, 10-60, 10-50",
            "Chest: Barbell Bench Press \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Chest: Lever Chest Press \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Chest: Lever Pec Deck Fly \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Shoulders: Dumbbell Lateral Raise \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Shoulders: Lever Cable Shoulder Press \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Shoulders: Lever Lateral Raise \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Biceps: Barbell Reverse Preacher Curl \n 10-70, 10-80, 6-90, 10-80, 10-70",
            "Triceps: Cable Triceps Pushdown \n 10-70, 10-80, 6-90, 10-80, 10-70",

        )
        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Workouts>()
        getUserData()
    }

    private fun getUserData(){
        for(i in imageId.indices){
            val workout = Workouts(imageId[i], heading[i])
            newArrayList.add(workout)
        }

        newRecyclerView.adapter = WorkoutsAdapter(newArrayList)
    }

    private fun filterList(text: String){
        var filteredList: ArrayList<Workouts> = ArrayList();
        for(i in imageId.indices){
            val workout = Workouts(imageId[i], heading[i])
            if(heading[i].toLowerCase().contains(text.toLowerCase())){
                filteredList.add(workout)
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            newRecyclerView.adapter = WorkoutsAdapter(filteredList)
        }
    }
}