package com.mzakialkhairi.manga.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.ui.home.HomeFragment
import com.mzakialkhairi.manga.ui.list_genre.ListGenreFragment
import com.mzakialkhairi.manga.ui.list_komik.ListKomikFragment
import com.mzakialkhairi.manga.ui.profile.ProfileFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ListKomikFragment()
            2 -> fragment = ListGenreFragment()
            3 -> fragment = ProfileFragment()

        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}