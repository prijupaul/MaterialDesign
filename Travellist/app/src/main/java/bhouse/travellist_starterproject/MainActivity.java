package bhouse.travellist_starterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toolbar;


public class MainActivity extends Activity implements TravelListAdapter.OnItemClickListener {

  private Menu menu;
  private boolean isListView;
  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private TravelListAdapter mAdapter;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    isListView = true;

     mRecyclerView = (RecyclerView)findViewById(R.id.list);
     mStaggeredLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
     mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
     mAdapter = new TravelListAdapter(this);
     mRecyclerView.setAdapter(mAdapter);
     mAdapter.setOnItemClickListener(this);
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      setUpActionBar();
  }

  private void setUpActionBar() {
      if (toolbar != null) {
          setActionBar(toolbar);
          getActionBar().setDisplayHomeAsUpEnabled(false);
          getActionBar().setDisplayShowTitleEnabled(true);
          getActionBar().setElevation(7);
      }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    this.menu = menu;
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_toggle) {
      toggle();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void toggle() {
    MenuItem item = menu.findItem(R.id.action_toggle);
    if (isListView) {
        mStaggeredLayoutManager.setSpanCount(2);
      item.setIcon(R.drawable.ic_action_list);
      item.setTitle("Show as list");
      isListView = false;
    } else {
        mStaggeredLayoutManager.setSpanCount(1);
      item.setIcon(R.drawable.ic_action_grid);
      item.setTitle("Show as grid");
      isListView = true;
    }
  }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);

        ImageView placeImage = (ImageView) view.findViewById(R.id.placeImage);
        LinearLayout placeNameHolder = (LinearLayout) view.findViewById(R.id.placeNameHolder);

        View navigationBar = findViewById(android.R.id.navigationBarBackground);
        View statusBar = findViewById(android.R.id.statusBarBackground);

        Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
        Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");

        Pair<View, String> navPair = Pair.create(navigationBar,
                Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
        Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
        Pair<View, String> toolbarPair = Pair.create((View)toolbar, "tActionBar");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                imagePair, holderPair);
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());

    }
}
