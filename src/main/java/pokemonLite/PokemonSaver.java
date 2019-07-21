package pokemonLite;

import javax.persistence.*;
import java.util.*;

public class PokemonSaver {
	private EntityManager managerOfEntities;
	
	public PokemonSaver() {
		managerOfEntities = Persistence.createEntityManagerFactory("$objectdb/db/pokemon-db.odb")
									   .createEntityManager();
	}
	
	public void savePokemon(Pokemon aPokemon) {
		managerOfEntities.persist(aPokemon);
	}
	
	public Pokemon getPokemonInfo(String pokemonName) {
		Query pokemonQuery = managerOfEntities.createQuery("SELECT p FROM Pokemon p WHERE p.name = :name", Pokemon.class)
											  .setParameter("name", pokemonName);
		return (Pokemon) pokemonQuery.getSingleResult();
	}
	
	public List<Pokemon> listAllPokemons() {
		Query pokemonQuery = managerOfEntities.createQuery("SELECT p FROM Pokemon p", Pokemon.class);
		return pokemonQuery.getResultList();
	}
}
