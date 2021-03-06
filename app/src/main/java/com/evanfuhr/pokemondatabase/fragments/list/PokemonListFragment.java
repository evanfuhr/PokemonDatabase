package com.evanfuhr.pokemondatabase.fragments.list;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.AbilityDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.EggGroupDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.LocationDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.MoveDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.PokemonRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PokemonListFragment extends Fragment
    implements SearchView.OnQueryTextListener {

    private OnListFragmentInteractionListener mListener;

    Ability mAbility;
    EggGroup mEggGroup;
    Location mLocation;
    Move mMove;
    Type mType;

    boolean isListByAbility = false;
    boolean isListByEggGroup = false;
    boolean isListByLocation = false;
    boolean isListByMove = false;
    boolean isListByType = false;

    RecyclerView mRecyclerView;
    Button mToggle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_card_list, container, false);

        mToggle = view.findViewById(R.id.card_list_button);
        mToggle.setText(R.string.pokemon);
        mProgressBar = view.findViewById(R.id.progressBar);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(TypeDisplayActivity.TYPE_ID)) {
                mType = new Type();
                mType.setId(bundle.getInt(TypeDisplayActivity.TYPE_ID));
                isListByType = true;
                mToggle.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else if (bundle.containsKey(AbilityDisplayActivity.ABILITY_ID)) {
                mAbility = new Ability();
                mAbility.setId(bundle.getInt(AbilityDisplayActivity.ABILITY_ID));
                isListByAbility = true;
//                mToggle.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
            } else if (bundle.containsKey(MoveDisplayActivity.MOVE_ID)) {
                mMove = new Move();
                mMove.setId(bundle.getInt(MoveDisplayActivity.MOVE_ID));
                isListByMove = true;
//                mToggle.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
            } else if (bundle.containsKey(LocationDisplayActivity.LOCATION_ID)) {
                mLocation = new Location();
                mLocation.setId(bundle.getInt(LocationDisplayActivity.LOCATION_ID));
                isListByLocation = true;
//                mToggle.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
            } else if (bundle.containsKey(EggGroupDisplayActivity.EGG_GROUP_ID)) {
                mEggGroup = new EggGroup();
                mEggGroup.setId(bundle.getInt(EggGroupDisplayActivity.EGG_GROUP_ID));
                isListByEggGroup = true;
//                mToggle.setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.GONE);
            }
        } else {
            Log.i("PokemonListFragment Log", "No bundle");
            setHasOptionsMenu(true);
        }

        List<Pokemon> pokemons = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new PokemonRecyclerViewAdapter(pokemons, mListener));

        new PokemonLoader(getActivity()).execute("");

        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                else if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTypeMatchUpListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        PokemonRecyclerViewAdapter adapter = (PokemonRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.filter(newText);
        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPokemonListFragmentInteraction(Pokemon item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.menu_list, menu);

        SearchManager searchManager = (SearchManager)
        getActivity().getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search_list);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        if (!searchMenuItem.isActionViewExpanded()) {
            searchMenuItem.expandActionView();
        }
        else {
            searchMenuItem.collapseActionView();
        }

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class PokemonLoader extends AsyncTask<String, Void, List<Pokemon>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        PokemonLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Pokemon> doInBackground(String... strings) {

            boolean pokemonAreTyped = false;
            PokemonDAO pokemonDAO = new PokemonDAO(mContext);
            List<Pokemon> rawPokemon;
            List<Pokemon> typedPokemon = new ArrayList<>();

            if (isListByAbility) {
                rawPokemon = pokemonDAO.getPokemon(mAbility);
            } else if (isListByEggGroup) {
                rawPokemon = pokemonDAO.getPokemon(mEggGroup);
            } else if (isListByLocation) {
                rawPokemon = pokemonDAO.getPokemon(mLocation);
            } else if (isListByMove) {
                rawPokemon = pokemonDAO.getPokemon(mMove);
            } else if (isListByType) {
                rawPokemon = pokemonDAO.getPokemon(mType);
            } else {
                rawPokemon = pokemonDAO.getAllPokemonWithTypes();
                pokemonAreTyped = true;
            }

            if (!pokemonAreTyped) {
                TypeDAO typeDAO = new TypeDAO(mContext);
                for (Pokemon pokemon : rawPokemon) {
                    pokemon = pokemonDAO.getPokemon(pokemon);
                    pokemon.setTypes(typeDAO.getTypes(pokemon));
                    typedPokemon.add(pokemon);
                }
                pokemonDAO.close();
                typeDAO.close();
            }

            return rawPokemon;
        }

        @Override
        protected void onPostExecute(List<Pokemon> pokemon) {
            super.onPostExecute(pokemon);
            PokemonRecyclerViewAdapter adapter = (PokemonRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectPokemon(pokemon);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
