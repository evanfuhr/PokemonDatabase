package com.evanfuhr.pokemondatabase.activities.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.EggGroupDisplayActivity;
import com.evanfuhr.pokemondatabase.fragments.list.EggGroupListFragment;
import com.evanfuhr.pokemondatabase.models.EggGroup;

import org.jetbrains.annotations.NonNls;

public class EggGroupListActivity extends AppCompatActivity
        implements EggGroupListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String EGG_GROUP = "Egg Groups";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_group_list);

        setTitle(EGG_GROUP);
    }

    @Override
    public void onEggGroupListFragmentInteraction(EggGroup eggGroup) {

        // Build the intent to load the eggGroup display
        Intent intent = new Intent(this, EggGroupDisplayActivity.class);
        // Load the eggGroup id into the intent
        intent.putExtra(EggGroupDisplayActivity.EGG_GROUP_ID, eggGroup.getId());

        startActivity(intent);
    }
}
