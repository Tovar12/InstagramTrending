package com.ftovaro.instagramtrending.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ftovaro.instagramtrending.R;
import com.ftovaro.instagramtrending.adapters.DetailedPostAdapter;
import com.ftovaro.instagramtrending.model.InstagramPost;
import com.ftovaro.instagramtrending.utils.DataWrapper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Represents a more detailed post and shows information about It.
 * Created by FelipeTovar on 6/12/15.
 */
public class DetailedPostActivity extends AppCompatActivity {

    /** List of Instagram posts **/
    private ArrayList<InstagramPost> instagramPosts = new ArrayList<>();
    /** Toolbar of the activity **/
    private Toolbar toolbar;
    /** Allows to share with external apps **/
    private ShareActionProvider mShareActionProvider;
    /** Link of the current post **/
    private String linkPost;
    /** Creates the circles that shows the length of the total of posts **/
    private CirclePageIndicator circlePageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("post_list");
        instagramPosts.addAll(dw.getInstagramPosts());
        int id = getIntent().getIntExtra("id", 0);
        String title = instagramPosts.get(id).getTitle();
        linkPost = instagramPosts.get(id).getLink();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);

        ViewPager mPager = (ViewPager) findViewById(R.id.pagerDetailed);
        DetailedPostAdapter mPagerAdapter = new DetailedPostAdapter(getSupportFragmentManager(),
                instagramPosts);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(id);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlesIndicator);
        circlePageIndicator.setViewPager(mPager);
        circlePageIndicator.setCurrentItem(id);
        circlePageIndicator.setFillColor(ContextCompat.getColor(this, R.color.colorPrimary));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String title = instagramPosts.get(position).getTitle();
                toolbar.setTitle(title);
                setSupportActionBar(toolbar);
                linkPost = instagramPosts.get(position).getLink();
                circlePageIndicator.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_datailed_post, menu);
        // Get the menu item.
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareIntent();

        return true;
    }

    private void setShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, linkPost);
        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


}