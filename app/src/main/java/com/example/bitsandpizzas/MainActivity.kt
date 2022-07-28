package com.example.bitsandpizzas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var shareActionProvider: ShareActionProvider
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // ViewPager - листаем активити/фрагменты свайпами
        viewPager = findViewById(R.id.pager)
        //Создаем адаптер и назначаем его view, связывая view с источником данных
        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        //Получаем  вкладок и их названия
        val tabs: TabLayout = findViewById(R.id.tabs)
        val tabNames = resources.getStringArray(R.array.tabs)
        //Соединяем вкладки с вьюпейджер
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider
        setShareActionIntent("Want to join me for pizza?")
        return super.onCreateOptionsMenu(menu)
    }

    private fun setShareActionIntent(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        shareActionProvider.setShareIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_order -> {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Создаем адаптер страничного компонента фрагментов ( в конструкторе  activity или фрагмент - в зависимости от того , где размещается вьюпейджер)
    class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        //Функция определяет количество страниц в PagerView
        override fun getItemCount(): Int = 4

        //Функция определяет позицию и соответствующий ей фрагмент
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TopFragment()
                1 -> PizzaFragment()
                2 -> PastaFragment()
                3 -> StoresFragment()
                else -> TopFragment()
            }
        }
    }
}
