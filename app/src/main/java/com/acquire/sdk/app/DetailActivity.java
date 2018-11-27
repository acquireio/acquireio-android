package com.acquire.sdk.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acquire.sdk.app.adapter.ViewPagerAdapter;
import com.acquire.sdk.app.fragment.ChartFrag;
import com.acquire.sdk.app.util.SelectedTab;

/**
 * Details activity .
 *
 * @author Nilay Dani
 */
public class DetailActivity extends BaseActivity {
    private static final String[] tabArray = {"Leads", "Returning Visitors"};//Tab title array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedTab(SelectedTab.HOME);
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        if (getIntent().getExtras().getBoolean("leads")) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (String tab : tabArray)
            adapter.addFrag(ChartFrag.newInstance(tab), tab);
        viewPager.setAdapter(adapter);
    }
}
