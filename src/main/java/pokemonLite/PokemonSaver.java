package pokemonLite;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PokemonSaver {
	private Gson gson;
	private String path;
	private Type pokemonType;
	
	public PokemonSaver(String filePath) {
		gson = new GsonBuilder().setPrettyPrinting().create();
		path = filePath;
		pokemonType = new TypeToken<List<Pokemon>>(){}.getType();
	}
	
	private List<Pokemon> setNewPokemonInfoToJson(Pokemon aPokemon) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		List<Pokemon> allPokemons = this.getAllPokemons();
		if(allPokemons.stream().anyMatch(pokemon -> pokemon.isThePokemonsName(aPokemon.getNameInDB()))) {
			allPokemons = this.updateInfoFrom(allPokemons, aPokemon);
		} else {
			allPokemons.add(aPokemon);	
		}
		aPokemon.hasBeenSaved();
		return allPokemons;
	}

	private List<Pokemon> updateInfoFrom(List<Pokemon> allPokemons, Pokemon aPokemon) {
		List<Pokemon> newPokemonList = allPokemons.stream()
												  .filter(pokemon -> !pokemon.isThePokemonsName(aPokemon.getNameInDB()))
												  .collect(Collectors.toList());
		newPokemonList.add(aPokemon);
		newPokemonList.forEach(pokemon -> pokemon.updateEvolutions(aPokemon));
		return newPokemonList;
	}

	public void savePokemon(Pokemon aPokemon) throws JsonIOException, IOException {
		List<Pokemon> allPokemons = this.setNewPokemonInfoToJson(aPokemon);
		FileWriter file = new FileWriter(this.path);
		gson.toJson(allPokemons, file);
		file.flush();
		file.close();
	}
	
	
	public Pokemon getPokemonInfo(String pokemonName) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return this.getAllPokemons()
				   .stream()
				   .filter(pokemon -> pokemon.isThePokemonsName(pokemonName))
				   .findFirst().get();
				   
	}
	
	public List<Pokemon> getAllPokemons() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return gson.fromJson(new FileReader(path), pokemonType);
	}
}
