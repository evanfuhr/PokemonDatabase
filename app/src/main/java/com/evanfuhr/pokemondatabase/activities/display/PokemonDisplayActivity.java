package com.evanfuhr.pokemondatabase.activities.display;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.list.AbilityListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.EggGroupListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.EvolutionListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.LocationListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.MoveListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.TypeListFragment;
import com.evanfuhr.pokemondatabase.fragments.list.TypeMatchUpListFragment;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.Forme;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.EvolutionDialog;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;
import com.evanfuhr.pokemondatabase.utils.VersionManager;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;

public class PokemonDisplayActivity extends AppCompatActivity
        implements AbilityListFragment.OnListFragmentInteractionListener,
        TypeListFragment.OnListFragmentInteractionListener, MoveListFragment.OnListFragmentInteractionListener,
        TypeMatchUpListFragment.OnTypeMatchUpListFragmentInteractionListener, LocationListFragment.OnListFragmentInteractionListener,
        EvolutionListFragment.OnListFragmentInteractionListener, EggGroupListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";

    RelativeLayout mRelativeLayout;

    Pokemon mPokemon = new Pokemon();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_display);

        mRelativeLayout = findViewById(R.id.pokemon_display_activity);

        loadPokemon();
        setBackgroundColor();
        String title = "#" + mPokemon.getSpecies() + " " + mPokemon.getName();
        if (mPokemon.getForme() != null) {
            title = title + " - " + Forme.getShowdownForme(mPokemon.getForme());
        }
        setTitle(title);
    }

    private void loadPokemon() {
        PokemonDAO pokemonDAO = new PokemonDAO(this);
        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        mPokemon.setId(intent.getIntExtra(POKEMON_ID, 0));
        mPokemon = pokemonDAO.loadPokemonDetails(mPokemon);
        pokemonDAO.close();
    }

    private void setBackgroundColor() {
        TypeDAO typeDAO = new TypeDAO(this);

        //Create base background
        List<Type> rawTypes = typeDAO.getTypes(mPokemon);
        List<Type> types = new ArrayList<>();
        for (Type t : rawTypes) {
            types.add(typeDAO.getType(t));
        }
        GradientDrawable gd = PokemonUtils.getColorGradientByTypes(types);

        RelativeLayout pokemonDetailsActivity = findViewById(R.id.pokemon_display_activity);
        pokemonDetailsActivity.setBackground(gd);

        typeDAO.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
                new VersionManager(this).onClickMenuSetGame();
                break;
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onListFragmentInteraction(Ability ability) {
        // Build the intent to load the display
        Intent intent = new Intent(this, AbilityDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(AbilityDisplayActivity.ABILITY_ID, ability.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Type type) {
        // Build the intent to load the display
        Intent intent = new Intent(this, TypeDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(TypeDisplayActivity.TYPE_ID, type.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Move move) {
        // Build the intent to load the display
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(MoveDisplayActivity.MOVE_ID, move.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Location location) {
        // Build the intent to load the ability display
        Intent intent = new Intent(this, LocationDisplayActivity.class);
        // Load the ability id into the intent
        intent.putExtra(LocationDisplayActivity.LOCATION_ID, location.getId());

        startActivity(intent);
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {
        // Build the intent to load the display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        // Load the id into the intent
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    @Override
    public void onEvolutionSelected(Evolution evolution) {
        new EvolutionDialog(this).show(evolution);
    }

    @Override
    public void onEggGroupListFragmentInteraction(EggGroup eggGroup) {
        // Build the intent to load the display
        Intent intent = new Intent(this, EggGroupDisplayActivity.class);
        // Load the id into the intent
        intent.putExtra(EggGroupDisplayActivity.EGG_GROUP_ID, eggGroup.getId());

        startActivity(intent);
    }
}
