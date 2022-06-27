package com.example.loginvalidation.view
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.loginvalidation.databinding.ActivityHomeBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.loginvalidation.MainAdapter
import com.example.loginvalidation.R
import com.example.loginvalidation.RetrofitService
import com.example.loginvalidation.repository.MainRepository
import com.example.loginvalidation.roomdb.PersonDatabase
import com.example.loginvalidation.view.fragments.HomeFragment
import com.example.loginvalidation.view.fragments.LanguageFragment
import com.example.loginvalidation.view.fragments.LogoutFragment
import com.example.loginvalidation.view.fragments.RateFragment
import com.example.loginvalidation.viewModelFactories.HomeViewModelFactory
import com.example.loginvalidation.viewmodel.HomeActivityViewModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*


class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var viewModel: HomeActivityViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        this.title = "Home Page"
        val eMail : String? = intent.getStringExtra("userEmail")
        setUpView()
        val dao = PersonDatabase.getInstance(this).personDao()
        val repository = MainRepository(dao)
        //get viewmodel instance using ViewModelProvider.Factory
        viewModel = ViewModelProvider(this, HomeViewModelFactory(MainRepository(dao),eMail))[HomeActivityViewModel::class.java]

        val view : NavigationView = findViewById(R.id.nav_View)
        val headerView : View = view.getHeaderView(0)


        val navUserName = headerView.findViewById<View>(R.id.headerName) as TextView
        val navEmail = headerView.findViewById<View>(R.id.headerEmail) as TextView
        val navImage = headerView.findViewById<View>(R.id.headerImage) as CircleImageView
        viewModel.getUserDetails().observe(this, Observer {
            if(it != null){
//                navImage.setImageBitmap(stringToBitmap(it.image))
                (it.firstName +  it.lastName).also { navUserName.text = it }
                (it.email).also { navEmail.text = it }
            }
        })
        //set adapter in recyclerview
        mBinding.recyclerview.adapter = adapter

        //the observer will only receive events if the owner(activity) is in active state
        //invoked when movieList data changes
        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            adapter.setMovieList(it)
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })

        viewModel.getAllMovies()

        setUpView()

        mBinding.navView.setNavigationItemSelectedListener {
            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.language -> replaceFragment(LanguageFragment(), it.title.toString())
                R.id.rateUs -> replaceFragment(RateFragment(), it.title.toString())
                R.id.nav_share -> Toast.makeText(applicationContext,"Clicked share button",Toast.LENGTH_SHORT).show()
                R.id.contact -> Toast.makeText(applicationContext,"Clicked contact",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> replaceFragment(LogoutFragment(),it.title.toString())

            }
            true
        }

    }


    private fun replaceFragment(fragment: Fragment,title: String){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        mBinding.drawerLayout.closeDrawers()
        setTitle(title)

    }

    private fun setUpView(){
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout(){
        setSupportActionBar(mBinding.appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mBinding.drawerLayout,R.string.open,R.string.close)
        actionBarDrawerToggle.syncState()
/*
actionBarDrawerToggle.isDrawerIndicatorEnabled = true
mBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}