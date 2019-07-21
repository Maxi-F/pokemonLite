package pokemonLite;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;

public class Fixture {
	protected Pokemon pikachu;
	protected Pokemon raichu;
	protected PokemonSaver saver;
	
	@Before
	public void init() throws FileNotFoundException {
		saver = new PokemonSaver("D:\\eclipse\\workspace\\pokemonLite\\test");
		raichu = new Pokemon("Raichu", 
								new ArrayList<PokemonType>(Arrays.asList(PokemonType.ELECTRIC)), 
								9, 
								new ArrayList<String>(Arrays.asList("Lightning Rod")), 
								new ArrayList<Pokemon>());
			
		pikachu = new Pokemon("Pikachu", 
								new ArrayList<PokemonType>(Arrays.asList(PokemonType.ELECTRIC)), 
								5, 
								new ArrayList<String>(Arrays.asList("Lightning Rod")), 
								new ArrayList<Pokemon>(Arrays.asList(raichu)));
	}
	
	
}
