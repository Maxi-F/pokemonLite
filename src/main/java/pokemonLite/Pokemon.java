package pokemonLite;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.Serializable;

public class Pokemon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private String lastNameSavedInDB;
	private List<PokemonType> types;
	private int level;
	private List<String> abilities;
	private List<Pokemon> evolutions;
	
	
	public Pokemon(String aName, List<PokemonType> someTypes, int aLevel, List<String> someAbilities, List<Pokemon> someEvolutions) {
		setName(aName);
		types = someTypes;
		level = aLevel;
		abilities = someAbilities;
		evolutions = someEvolutions;
		this.hasBeenSaved();
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}
	
	private void setLevel(int aLevel) {
		this.level = aLevel;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<PokemonType> getTypes() {
		return types;
	}
	
	private void setTypes(List<PokemonType> someTypes) {
		this.types = someTypes;
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
	
	public List<Pokemon> getEvolutions() {
		return evolutions;	
	}

	public boolean isThePokemonName(String pokemonName) {
		return this.name.equals(pokemonName);
	}
	
	public boolean isThePokemonNameInDB(String pokemonNameInDB) {
		return this.lastNameSavedInDB.equals(pokemonNameInDB);
	}
	
	public void hasBeenSaved() {
		this.lastNameSavedInDB = this.name;
	}

	public String getNameInDB() {
		return this.lastNameSavedInDB;
	}

	public boolean thePokemonsNameAre(String aNameInDB, String aName) {
		return this.isThePokemonName(aName) || this.isThePokemonNameInDB(aNameInDB);
	}

	public void updateEvolutions(Pokemon aPokemon) {
		Optional<Pokemon> pokemonToUpdate = this.evolutions.stream()
					   					 				   .filter(pokemon -> pokemon.isThePokemonNameInDB(aPokemon.getNameInDB()))
					   					 				   .findFirst();
		
		if(pokemonToUpdate.isPresent()) {
			pokemonToUpdate.get().updateValues(aPokemon);
		}
	}

	private void updateValues(Pokemon aPokemon) {
		this.setName(aPokemon.getName());
		this.hasBeenSaved();
		this.setTypes(aPokemon.getTypes());
		this.setLevel(aPokemon.getLevel());
		this.setAbilities(aPokemon.getAbilities());
	}

	private void setAbilities(List<String> someAbilities) {
		this.abilities = someAbilities;
	}

	private List<String> getAbilities() {
		return this.abilities;
	}

	// Testing methods
	public boolean isTheSamePokemonAs(Pokemon aPokemon) {
		return this.thePokemonsNameAre(aPokemon.getNameInDB(), aPokemon.getName())
				&& this.hasSameNameTypesAndLevelAs(aPokemon)
				&& this.hasSameAbilitiesAndEvolutionsAs(aPokemon);
	}

	public boolean hasSameNameTypesAndLevelAs(Pokemon aPokemon) {
		return this.isThePokemonName(aPokemon.getName())
				&& this.level == aPokemon.getLevel()
				&& this.types.stream().allMatch(pokemonType -> aPokemon.hasType(pokemonType));
	}

	private boolean hasType(PokemonType aPokemonType) {
		return this.types.stream().anyMatch(pokemonType -> pokemonType == aPokemonType);
	}

	public boolean hasSameAbilitiesAndEvolutionsAs(Pokemon aPokemon) {
		return this.abilities.stream().allMatch(ability -> aPokemon.hasAbility(ability))
				&& this.evolutions.stream().allMatch(evolution -> aPokemon.hasEvolution(evolution));
	}

	public Boolean hasEvolution(Pokemon anEvolution) {
		return this.evolutions.stream().anyMatch(evolution -> evolution.isTheSamePokemonAs(anEvolution));
	}

	private Boolean hasAbility(String anAbility) {
		return this.abilities.stream().anyMatch(ability -> ability.equals(anAbility));
	}
}
