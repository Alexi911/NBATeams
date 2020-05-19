package com.example.nba.presentation.view.Cavaliers;

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
import com.example.nba.presentation.controller.CavaliersController;
import com.example.nba.presentation.model.CavaliersPlayers;
import com.example.nba.R;

import java.util.List;

public class Cavaliers extends AppCompatActivity {

    private static final String TAG = "Cavaliers";

    private RecyclerView recyclerView;
    private CavaliersAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private CavaliersController cavaliersController;
    private List<CavaliersPlayers> cavaliersPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        cavaliersController = new CavaliersController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        cavaliersController.onStart();
    }

    public void showList(List<CavaliersPlayers> cavaliersList) {

        recyclerView = (RecyclerView) findViewById(R.id.teams_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CavaliersAdapter(cavaliersList, new CavaliersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CavaliersPlayers item) {
                cavaliersController.onItemClick(item);
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

    public void navigateToDetails(CavaliersPlayers cavaliersPlayers) {

        Intent myIntent = new Intent(Cavaliers.this, CavaliersDetails.class);
        myIntent.putExtra("cavaliersKey", Singletons.getGson().toJson(cavaliersPlayers));
        Cavaliers.this.startActivity(myIntent);
    }
}
