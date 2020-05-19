package com.example.nba.presentation.view.Lakers;

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
import com.example.nba.presentation.controller.LakersController;
import com.example.nba.presentation.model.LakersPlayers;
import com.example.nba.R;

import java.util.List;

public class Lakers extends AppCompatActivity  {

    private static final String TAG = "Lakers";

    private RecyclerView recyclerView;
    private LakersAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private LakersController lakersController;
    private List<LakersPlayers> lakersPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        lakersController = new LakersController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        lakersController.onStart();
    }

    public void showList(List<LakersPlayers> lakersList) {

        recyclerView = (RecyclerView) findViewById(R.id.teams_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new LakersAdapter(lakersList, new LakersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LakersPlayers item) {
                lakersController.onItemClick(item);
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

    public void navigateToDetails(LakersPlayers lakersPlayers) {

        Intent myIntent = new Intent(Lakers.this, LakersDetails.class);
        myIntent.putExtra("lakersKey", Singletons.getGson().toJson(lakersPlayers));
        Lakers.this.startActivity(myIntent);
    }
}
