package com.evanfuhr.pokemondatabase.activities;

import android.app.SearchManager;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements PokemonListFragment.OnFragmentInteractionListener,
                SearchView.OnQueryTextListener{

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";
    @NonNls
    public static final String POKEMON = "Pokémon";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        //myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        setTitle(POKEMON);

        //FragmentManager fm = getFragmentManager();
        //PokemonListFragment pokemonListFragment = (PokemonListFragment) fm.findFragmentById(R.id.pokemonDetailsFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search_pokemon_list);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_set_game:
                onClickMenuSetGame(item);
                break;
            case R.id.action_search_pokemon_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    void onClickMenuSetGame(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), MENU_ITEM_NOT_IMPLEMENTED_YET, Toast.LENGTH_LONG);
        toast.show();
//        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(getString(R.string.saved_high_score), newHighScore);
//        editor.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
