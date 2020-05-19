package com.example.nba.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nba.Singletons;
import com.example.nba.presentation.controller.BullsController;
import com.example.nba.presentation.model.BullsPlayers;
import com.example.nba.R;
import com.example.nba.presentation.model.WarriorsPlayers;

import java.util.List;

public class Bulls extends AppCompatActivity {

    private static final String TAG = "Bulls";

    private RecyclerView recyclerView;
    private BullsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private BullsController bullsController;
    private List<BullsPlayers> bullsPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        bullsController = new BullsController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        bullsController.onStart();
    }

    public void showList(List<BullsPlayers> bullsList) {

        recyclerView = (RecyclerView) findViewById(R.id.teams_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BullsAdapter(bullsList, new BullsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BullsPlayers item) {
                bullsController.onItemClick(item);
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

    public void navigateToDetails(BullsPlayers bullsPlayers) {

        Intent myIntent = new Intent(Bulls.this, BullsDetails.class);
        myIntent.putExtra("bullsKey", Singletons.getGson().toJson(bullsPlayers));
        Bulls.this.startActivity(myIntent);
    }
}