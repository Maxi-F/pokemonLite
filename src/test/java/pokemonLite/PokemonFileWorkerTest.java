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

public class PokemonFileWorkerTest extends Fixture {
	
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
							.anyMatch(pokemon -> pokemon.thePokemonsNameAre(aPokemon.getNameInDB(), aPokemon.getName()));
	}

	private Boolean isAnEvolutionOfCharmander(Pokemon evolution, List<Pokemon> charmandersRealEvolutions) {
		return charmandersRealEvolutions.stream().anyMatch(anotherEvolution -> anotherEvolution.isTheSamePokemonAs(evolution));
	}
	
	@Test
	public void pikachuIsUpdatedToTheOneAndOnly() throws IOException, ClassNotFoundException {
		Pokemon pokemonInFileAfter = pokemonFileWorker.getPokemonInfo("theOneAndOnly");
		Assert.assertEquals(pikachu.getName(), pokemonInFileAfter.getName());
	}
	
	@Test
	public void allTestingPokemonsHaveBeenSaved() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		List<Pokemon> pokemonsThatGotSaved = new ArrayList<Pokemon>();
		List<Pokemon> savedPokemons = pokemonFileWorker.getAllPokemons();
		this.addAllTestingPokemonsTo(pokemonsThatGotSaved);
		
		Assert.assertTrue(this.allTestingPokemonsAreInJSON(savedPokemons, pokemonsThatGotSaved));
	}

	@Test
	public void mewtwoCanBeRetrievedFromTheDB() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Pokemon maybeMewtwo = pokemonFileWorker.getPokemonInfo("mewtwo");
		Assert.assertTrue(maybeMewtwo.isTheSamePokemonAs(mewtwo));
	}
	
	@Test
	public void raichusNameTypesAndLevelAreTheSameAsInDB() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Pokemon maybeRaichu = pokemonFileWorker.getPokemonInfo("Raichu");
		Assert.assertTrue(maybeRaichu.hasSameNameTypesAndLevelAs(raichu));
	}
	
	@Test
	public void bulbasaursAbilitiesAndEvolutionsAreTheSameAsInDB() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Pokemon maybeBulbasaur = pokemonFileWorker.getPokemonInfo("Bulbasaur");
		Assert.assertTrue(maybeBulbasaur.hasSameAbilitiesAndEvolutionsAs(bulbasaur));
	}
	
	@Test
	public void charmandersEvolutionsInfoAreRetrievedFromDB() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Pokemon maybeCharmander = pokemonFileWorker.getPokemonInfo("Charmander");
		List<Pokemon> charmandersEvolutionsInDB = maybeCharmander.getEvolutions();
		List<Pokemon> charmandersRealEvolutions = new ArrayList<Pokemon>(Arrays.asList(charmaleon, charizard));
		Assert.assertTrue(charmandersEvolutionsInDB.stream()
					 							   .allMatch(evolution -> this.isAnEvolutionOfCharmander(evolution, charmandersRealEvolutions)));
	}

	@Test
	public void greninjaCanBeAddedAndRetrievedFromDB() throws JsonIOException, IOException {
		pokemonFileWorker.savePokemon(greninja);
		Pokemon maybeGreninja = pokemonFileWorker.getPokemonInfo("Greninja");
		Assert.assertTrue(maybeGreninja.isTheSamePokemonAs(greninja));
	}
	
}
