package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface PokemonDataInterface {

    List<Pokemon> getAllPokemonWithTypes();

    List<Pokemon> getPokemon(Ability ability);

    List<Pokemon> getPokemon(EggGroup eggGroup);

    List<Pokemon> getPokemon(Location location);

    List<Pokemon> getPokemon(Move move);

    List<Pokemon> getPokemon(Type type);

    Pokemon getPokemon(Pokemon pokemon);

    Pokemon getPokemon(String name);

    Pokemon loadPokemonDetails(Pokemon pokemon);
}
