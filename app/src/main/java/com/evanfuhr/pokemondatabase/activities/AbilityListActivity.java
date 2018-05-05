package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.AbilityListFragment;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class AbilityListActivity extends AppCompatActivity
        implements AbilityListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String ABILITY = "Ability";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_list);

        setTitle(ABILITY);
    }

    @Override
    public void onListFragmentInteraction(Ability ability) {
        PokemonUtils.showLoadingToast(this);

        // Build the intent to load the player sheet
        Intent intent = new Intent(this, AbilityDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(AbilityDisplayActivity.ABILITY_ID, ability.getId());

        startActivity(intent);
    }
}
