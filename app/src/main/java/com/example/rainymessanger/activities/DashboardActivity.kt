package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TabWidget
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.rainymessanger.R
import com.example.rainymessanger.adapters.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.collection.LLRBNode

class DashboardActivity : AppCompatActivity() {

    lateinit var mName: String
    lateinit var mViewPager: ViewPager
    lateinit var mTab: TabLayout
    lateinit var mSectionAdapter: SectionPagerAdapter

    companion object{
        val EXTRA_NAME = "name"

        fun newIntent(context: Context, name: String): Intent{
            var intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        findViews()
        if (intent.extras != null){
            mName = intent!!.extras!!.getString(EXTRA_NAME).toString()
        }
        mSectionAdapter = SectionPagerAdapter(supportFragmentManager)
        mViewPager.adapter = mSectionAdapter
        mTab.setupWithViewPager(mViewPager)
        mTab.setTabTextColors(R.color.blue, Color.WHITE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.menu_setting -> startActivity(SettingsActivity.newIntent(this))
            R.id.menu_logOut -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(RainyActivity.newIntent(this))
                finish()
            }
        }
        return true
    }
    private fun findViews(){
        mViewPager = findViewById(R.id.dashboard_view_pager)
        mTab = findViewById(R.id.dashboard_tab)
    }
}