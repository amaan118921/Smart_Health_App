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
import com.example.smarthealthconsultingapp.bottomSheet.InspectHealthBottomSheet
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
            key = Constants.PATIENT_HOME
            Bundle().apply {
                putString(Constants.AUTHENTICATE, key)
                addFragment(Constants.SPLASH_ID,this)
            }
        }
        else if(repo.getSharedPreferences(Constants.DOC_PHONE)!="") {
            key = Constants.DOC_HOME
            Bundle().apply {
                putString(Constants.AUTHENTICATE, key)
                addFragment(Constants.SPLASH_ID,this)
            }
        }
        else addFragment(Constants.CHOOSE_ACCOUNT_ID,null)
    }

    fun getReqFragmentManager(): FragmentManager = supportFragmentManager

    private fun addFragment(id: String, bundle: Bundle?) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            add(R.id.fragment_container_view, Constants.getFragmentClass(id), bundle, id)
            addToBackStack(id)
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
                R.id.btnInspect -> goToInspect()
                R.id.btnSearch -> goToSearch()
                else -> viewPager.setCurrentItem(3, true)
            }
        return false
    }

    private fun goToSearch() {
        addFragment(Constants.SEARCH_ID, null)
    }

    private fun goToInspect() {
        addFragment(Constants.INSPECT_ID, null)
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
            else -> btmNavigation.menu.getItem(3).isChecked = true
        }
    }

    fun hideBottomNav() {
        btmNavigation.makeGone()
    }

    fun showBottomNav() {
        btmNavigation.makeVisible()
    }

    fun getKey(): String?  {
        return key
    }

}