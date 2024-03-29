package pokemonLite;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class PokemonFileWorker {
	private Gson gson;
	private String path;
	private Type pokemonType;
	
	public PokemonFileWorker(String filePath) {
		gson = new GsonBuilder().setPrettyPrinting().create();
		path = filePath;
		pokemonType = new TypeToken<List<Pokemon>>(){}.getType();
	}
	
	public void setPath(String newFilePath) {
		this.path = newFilePath;
	}
	
	private List<Pokemon> setNewPokemonInfoToJson(Pokemon aPokemon) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		List<Pokemon> allPokemons = this.getAllPokemons();
		if(allPokemons.stream().anyMatch(pokemon -> pokemon.thePokemonsNameAre(aPokemon.getNameInDB(), aPokemon.getName()))) {
			allPokemons = this.updateInfoFrom(allPokemons, aPokemon);
		} else {
			allPokemons.add(aPokemon);	
		}
		aPokemon.hasBeenSaved();
		return allPokemons;
	}

	private List<Pokemon> updateInfoFrom(List<Pokemon> allPokemons, Pokemon aPokemon) {
		List<Pokemon> newPokemonList = allPokemons.stream()
												  .filter(pokemon -> !pokemon.thePokemonsNameAre(aPokemon.getNameInDB(), aPokemon.getName()))
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
				   .filter(pokemon -> pokemon.isThePokemonName(pokemonName))
				   .findFirst().get();
				   
	}
	
	public List<Pokemon> getAllPokemons() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return gson.fromJson(new FileReader(path), pokemonType);
	}
}
