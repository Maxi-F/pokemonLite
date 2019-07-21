package pokemonLite;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PokemonTest {
	private Pokemon pikachu;
	private Pokemon raichu;
	
	@Before
	public void init() {
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
