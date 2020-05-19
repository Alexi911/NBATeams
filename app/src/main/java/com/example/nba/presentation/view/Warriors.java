package com.example.nba.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nba.Singletons;
import com.example.nba.presentation.controller.WarriorsController;
import com.example.nba.R;
import com.example.nba.presentation.model.WarriorsPlayers;

import java.util.List;

public class Warriors extends AppCompatActivity {

    private static final String TAG = "Warriors";

    private RecyclerView recyclerView;
    private WarriorsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private WarriorsController warriorsController;
    private List<WarriorsPlayers> warriorsPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        warriorsController = new WarriorsController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        warriorsController.onStart();
    }

    public void showList(List<WarriorsPlayers> playersList) {

        recyclerView = (RecyclerView) findViewById(R.id.teams_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new WarriorsAdapter(playersList, new WarriorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WarriorsPlayers item) {
                warriorsController.onItemClick(item);
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void navigateToDetails(WarriorsPlayers warriorsPlayers) {

        Intent myIntent = new Intent(Warriors.this, WarriorsDetails.class);
        myIntent.putExtra("warriorsKey", Singletons.getGson().toJson(warriorsPlayers));
        Warriors.this.startActivity(myIntent);
    }
}
