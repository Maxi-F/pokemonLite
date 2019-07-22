package pokemonLite;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;

public class Fixture {
	protected Pokemon pichu;
	protected Pokemon pikachu;
	protected Pokemon raichu;
	protected Pokemon bulbasaur;
	protected Pokemon ivysaur;
	protected Pokemon venosaur;
	protected Pokemon charmander;
	protected Pokemon charmaleon;
	protected Pokemon charizard;
	protected Pokemon mewtwo;
	protected Pokemon greninja;
	
	protected PokemonFileWorker pokemonFileWorker;
	
	@Before
	public void init() throws IOException {
		File checkingFile = new File("test.json");
		
		if(!checkingFile.exists()) {
			FileWriter fileToWriteFirstArrayTo = new FileWriter("test.json");
			fileToWriteFirstArrayTo.append("[]");
			fileToWriteFirstArrayTo.flush();
			fileToWriteFirstArrayTo.close();
		}
		
		pokemonFileWorker = new PokemonFileWorker("test.json");
		
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
		
		pichu = new Pokemon("Pichu", 
				  			new ArrayList<PokemonType>(Arrays.asList(PokemonType.ELECTRIC)), 
				  			2, 
				  			new ArrayList<String>(Arrays.asList("Lightning Rod")), 
				  			new ArrayList<Pokemon>(Arrays.asList(pikachu)));
		
		
		venosaur = new Pokemon("Venosaur", 
							   new ArrayList<PokemonType>(Arrays.asList(PokemonType.GRASS, PokemonType.POISON)), 
							   11, 
							   new ArrayList<String>(Arrays.asList("Overgrow")), 
							   new ArrayList<Pokemon>());
			
		ivysaur = new Pokemon("Ivysaur", 
						      new ArrayList<PokemonType>(Arrays.asList(PokemonType.GRASS, PokemonType.POISON)), 
						      7, 
						      new ArrayList<String>(Arrays.asList("Overgrow")), 
						      new ArrayList<Pokemon>(Arrays.asList(venosaur)));
		
		bulbasaur = new Pokemon("Bulbasaur", 
								new ArrayList<PokemonType>(Arrays.asList(PokemonType.GRASS, PokemonType.POISON)), 
								3, 
								new ArrayList<String>(Arrays.asList("Overgrow")), 
								new ArrayList<Pokemon>(Arrays.asList(ivysaur)));
		
		charizard = new Pokemon("Charizard", 
								new ArrayList<PokemonType>(Arrays.asList(PokemonType.FIRE, PokemonType.FLYING)), 
								16, 
								new ArrayList<String>(Arrays.asList("Blaze")), 
								new ArrayList<Pokemon>());
		
		charmaleon = new Pokemon("Charmaleon", 
								 new ArrayList<PokemonType>(Arrays.asList(PokemonType.FIRE)), 
								 8, 
								 new ArrayList<String>(Arrays.asList("Blaze")), 
								 new ArrayList<Pokemon>(Arrays.asList(charizard)));
		
		
		charmander = new Pokemon("Charmander", 
				   				 new ArrayList<PokemonType>(Arrays.asList(PokemonType.FIRE)), 
				   				 4, 
				   				 new ArrayList<String>(Arrays.asList("Blaze")), 
				   				 new ArrayList<Pokemon>(Arrays.asList(charmaleon)));
		
		mewtwo = new Pokemon("mewtwo",
							 new ArrayList<PokemonType>(Arrays.asList(PokemonType.PSYCHIC)),
							 50,
							 new ArrayList<String>(Arrays.asList("Pressure", "Unnerve")),
							 new ArrayList<Pokemon>());
		
		greninja = new Pokemon("Greninja",
				 			   new ArrayList<PokemonType>(Arrays.asList(PokemonType.WATER, PokemonType.DARK)),
				 			   25,
				 			   new ArrayList<String>(Arrays.asList("Torrent", "Protean")),
				 			   new ArrayList<Pokemon>());
		
		pokemonFileWorker.savePokemon(raichu);
		pokemonFileWorker.savePokemon(pikachu);
		pokemonFileWorker.savePokemon(pichu);
		pokemonFileWorker.savePokemon(venosaur);
		pokemonFileWorker.savePokemon(ivysaur);
		pokemonFileWorker.savePokemon(bulbasaur);
		pokemonFileWorker.savePokemon(charizard);
		pokemonFileWorker.savePokemon(charmaleon);
		pokemonFileWorker.savePokemon(charmander);
		pokemonFileWorker.savePokemon(mewtwo);
		
		pikachu.setName("theOneAndOnly");
		pokemonFileWorker.savePokemon(pikachu);
	}
	
	
}
