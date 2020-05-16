package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.infusiblecoder.groceryadminfyp.R;

import Adapters.FlipperAdapter;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    //fipper
    private AdapterViewFlipper adapterViewFlipper;
    private Button btn_prev,btn_next;
    private static String[] news = {"News one","News two","News three"};
    private static int[] images = {R.drawable.bg_imgae,R.drawable.icon,R.drawable.news};
    private int postition =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adapterViewFlipper = findViewById(R.id.mFlipper);

        //createing adapter object

        FlipperAdapter adapter = new FlipperAdapter(HomeActivity.this,images,news);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sort_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view

        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.LEFT);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = new HomeFragment();
//        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:

                mDrawer.closeDrawers();
                break;
            case R.id.nav_category:
                toolbar.setTitle("Category");
//                openFragment(new AllLoansFragment());
////                menuItem.setChecked(true);
////                // Set action bar title
////                setTitle(menuItem.getTitle());
////                // Close the navigation drawer
                mDrawer.closeDrawers();
                break;
            case R.id.nav_my_orders:
//                openFragment(new LatestLoanFragment());
////                menuItem.setChecked(true);
////                // Set action bar title
////                setTitle(menuItem.getTitle());
////                // Close the navigation drawer
                mDrawer.closeDrawers();
                break;

            case R.id.nav_settnig:

                mDrawer.closeDrawers();
                break;
            case R.id.nav_logout:

                startActivity(new Intent(this, MainActivity.class));
                logout();
                finish();
                break;
            default:
                //fragmentClass = FirstFragment.class;
        }

    }

    public void logout(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}
