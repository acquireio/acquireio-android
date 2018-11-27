package com.acquire.sdk.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acquire.sdk.app.adapter.ViewPagerAdapter;
import com.acquire.sdk.app.fragment.InfoFrag;
import com.acquire.sdk.app.util.SelectedTab;

public class TestActivity extends BaseActivity implements InfoFrag.OnFragmentInteractionListener {
    private static final String[] tabArray = {"Personal Info", "Payment Method"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedTab(SelectedTab.TEST);
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (String tab : tabArray)
            adapter.addFrag(InfoFrag.newInstance(tab,""), tab);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
