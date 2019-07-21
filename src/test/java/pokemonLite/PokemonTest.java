package pokemonLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PokemonTest extends Fixture {
	@Test
	public void pikachuIsSaved() throws IOException, ClassNotFoundException {
		saver.savePokemon(pikachu);
		Pokemon pokemonInFile = saver.getPokemonInfo("Pikachu");
		Assert.assertEquals(pikachu.getName(), pokemonInFile.getName());
	}
}
