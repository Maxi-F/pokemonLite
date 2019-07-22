package pokemonLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PokemonTest extends Fixture {
	@Test
	public void pikachuIsUpdated() throws IOException, ClassNotFoundException {
		Pokemon pokemonInFileBefore = saver.getPokemonInfo("Pikachu");
		System.out.println(pikachu.getName());
		saver.savePokemon(pikachu);
		System.out.println(pokemonInFile.getName());
		Assert.assertEquals(pikachu.getName(), pokemonInFile.getName());
	}
}
