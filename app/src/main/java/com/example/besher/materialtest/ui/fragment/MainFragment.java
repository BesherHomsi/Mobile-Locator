package com.example.besher.materialtest.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.adapters.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager mVpViewPager;
    private Toolbar mToolBar;
    private TabLayout mTablayout;
    private SilenceItemsListFragment eventFragment;
    private MapFramgent mapFragment;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        setUpView(root);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        mTablayout.setupWithViewPager(mVpViewPager);
        return root;
    }


    private void setUpView(View view) {
        mToolBar = (Toolbar) view.findViewById(R.id.tool_bar);
        mTablayout = (TabLayout) view.findViewById(R.id.tabs);
        mVpViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(mVpViewPager);


    }

    private void setupViewPager(ViewPager mVpViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        if (eventFragment == null)
            eventFragment = new SilenceItemsListFragment();
        if (mapFragment == null)
            mapFragment = new MapFramgent();
        adapter.addFragment(eventFragment, "Events");
        adapter.addFragment(mapFragment, "Locations");
        mVpViewPager.setAdapter(adapter);
        mVpViewPager.setOffscreenPageLimit(3);
    }


    public void addNewItem(SilenceItem silenceItem) {
        if (eventFragment != null) {
            eventFragment.addNewItem(silenceItem);
        }
    }
}
