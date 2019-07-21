package pokemonLite;

import java.util.List;
import java.io.Serializable;

public class Pokemon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<PokemonType> types;
	private int level;
	private List<String> abilities;
	private List<Pokemon> evolutions;
	private PokemonSaver saver;
	
	public Pokemon(String aName, List<PokemonType> someTypes, int aLevel, List<String> someAbilities, List<Pokemon> someEvolutions) {
		setName(aName);
		types = someTypes;
		level = aLevel;
		abilities = someAbilities;
		evolutions = someEvolutions;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<PokemonType> getTypes() {
		return types;
	}
	
	public void addType(PokemonType aType) {
		types.add(aType);
	}
	
	public void removeType(PokemonType aType) {
		types.remove(aType);
	}
	
	public void addEvolution(Pokemon anEvolution) {
		evolutions.add(anEvolution);
	}

	public boolean isThePokemon(String pokemonName) {
		return this.name == pokemonName;
	}
}
