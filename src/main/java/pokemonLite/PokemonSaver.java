package pokemonLite;

import java.io.*;
import java.util.*;

public class PokemonSaver {
	FileOutputStream fileOutput;
	FileInputStream fileInput;
	
	public PokemonSaver(String aPath) throws FileNotFoundException {
		fileOutput = new FileOutputStream(aPath);
		fileInput = new FileInputStream(aPath);
	}
	
	public void savePokemon(Pokemon aPokemon) throws IOException {
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(aPokemon);
		objectOutput.close();
	}
	
	public Pokemon getPokemonInfo(String pokemonName) throws IOException, ClassNotFoundException {
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			Pokemon aPokemon = (Pokemon) objectInput.readObject();
			while(aPokemon.isThePokemon(pokemonName)) {
				aPokemon = (Pokemon) objectInput.readObject();
			}
			return aPokemon;
	}
}
