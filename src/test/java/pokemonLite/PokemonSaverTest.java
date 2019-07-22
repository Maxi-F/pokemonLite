package pokemonLite;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class PokemonSaverTest extends Fixture {
	@Test
	public void pikachuIsUpdatedToTheOneAndOnly() throws IOException, ClassNotFoundException {
		Pokemon pokemonInFileAfter = saver.getPokemonInfo("theOneAndOnly");
		Assert.assertEquals(pikachu.getName(), pokemonInFileAfter.getName());
	}
	
	private void addAllTestingPokemonsTo(List<Pokemon> pokemonsThatGotSaved) {
		pokemonsThatGotSaved.add(pichu);
		pokemonsThatGotSaved.add(pikachu);
		pokemonsThatGotSaved.add(raichu);
		pokemonsThatGotSaved.add(bulbasaur);
		pokemonsThatGotSaved.add(ivysaur);
		pokemonsThatGotSaved.add(venosaur);
		pokemonsThatGotSaved.add(charmander);
		pokemonsThatGotSaved.add(charmaleon);
		pokemonsThatGotSaved.add(charizard);
		pokemonsThatGotSaved.add(mewtwo);
	}
	
	private boolean allTestingPokemonsAreInJSON(List<Pokemon> savedPokemons, List<Pokemon> pokemonsThatGotSaved) {
		return pokemonsThatGotSaved.stream()
								   .allMatch(pokemon -> this.pokemonIsInList(savedPokemons, pokemon));
	}
	
	private boolean pokemonIsInList(List<Pokemon> savedPokemons, Pokemon aPokemon) {
		return savedPokemons.stream()
							.anyMatch(pokemon -> pokemon.isThePokemonsName(aPokemon.getName()));
	}

	@Test
	public void allTestingPokemonsHaveBeenSaved() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		List<Pokemon> pokemonsThatGotSaved = new ArrayList<Pokemon>();
		List<Pokemon> savedPokemons = saver.getAllPokemons();
		this.addAllTestingPokemonsTo(pokemonsThatGotSaved);
		
		Assert.assertTrue(this.allTestingPokemonsAreInJSON(savedPokemons, pokemonsThatGotSaved));
	}

	@Test
	public void mewtwoCanBeRetrievedFromTheDB() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Pokemon maybeMewtwo = saver.getPokemonInfo("mewtwo");
		Assert.assertTrue(maybeMewtwo.isTheSamePokemonAs(mewtwo));
	}

}
