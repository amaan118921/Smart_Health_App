package com.example.smarthealthconsultingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager.widget.ViewPager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.ViewPagerAdapter
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var navHostFragment: NavHostFragment? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var key: String? = null

    @Inject
    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        authenticate()
    }

    private fun authenticate() {
        if(repo.getSharedPreferences(Constants.PHONE)!="") {
            addFragment(Constants.SPLASH_ID,null)
            key = Constants.PATIENT_HOME
        }
        else if(repo.getSharedPreferences(Constants.DOC_PHONE)!="") {
            addFragment(Constants.SPLASH_ID,null)
            key = Constants.DOC_HOME
        }
        else addFragment(Constants.CHOOSE_ACCOUNT_ID,null)
    }

    fun getReqFragmentManager(): FragmentManager = supportFragmentManager

    private fun addFragment(id: String, bundle: Bundle?) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, Constants.getFragmentClass(id), bundle, id)
        }
    }

    fun initViewPagerWithBottom() {
        initViewPager()
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        btmNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initViewPager() {
        viewPagerAdapter = navHostFragment?.fragmentManager?.let {
            key?.let { it1 -> ViewPagerAdapter(it, it1) }
        }
        viewPager?.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        changeBottomTabByPosition(position)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId) {
                R.id.btnHome -> viewPager.setCurrentItem(0, true)
                R.id.btnSearch -> viewPager.setCurrentItem(1, true)
                else -> viewPager.setCurrentItem(2, true)
            }
        return false
    }

    private fun changeBottomTabByPosition(position: Int) {
        when(position) {
            0 -> {
                btmNavigation.menu.getItem(0).isChecked = true
            }
            1-> {
                btmNavigation.menu.getItem(1).isChecked = true
            }
            2 -> {
                btmNavigation.menu.getItem(2).isChecked = true
            }
        }
    }

    fun hideBottomNav() {
        btmNavigation.makeGone()
    }

    fun showBottomNav() {
        btmNavigation.makeVisible()
    }

}