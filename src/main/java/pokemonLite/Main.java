package pokemonLite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import pokemonLite.PokemonFileWorker;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to Pokedex-Lite!");
		System.out.println("Please, enter the filePath where you want the pokemons to be saved to/read from:");
		String filePath = consoleReader.readLine();
		File checkingFile = new File(filePath);
		
		if(!checkingFile.exists()) {
			boolean isAValidFile = false;
			
			while(!isAValidFile) {
				try {
					FileWriter fileToWriteFirstArrayTo = new FileWriter(filePath);
					fileToWriteFirstArrayTo.append("[]");
					fileToWriteFirstArrayTo.flush();
					fileToWriteFirstArrayTo.close();
					isAValidFile = true;
				} catch (Exception ex) {
					System.out.println("File path was not valid. Please enter again a file path: ");
					filePath = consoleReader.readLine();
				}
			}
		}
		
		PokemonFileWorker fileWorker = new PokemonFileWorker(filePath);
		
		System.out.println("Type the action you want to make (MAYUS INCLUDED):");
		System.out.println("\"LIST\": See a list of all the pokemons in the pokedex");
		System.out.println("\"ADD\": Add a new pokemon to the pokedex");
		System.out.println("\"EDIT\": Edit and update a pokemon in the pokedex");
		System.out.println("\"TERMINATE\": Terminate the program");
		
		String command = consoleReader.readLine();
		while(!command.equals("TERMINATE")) {
			switch(command) {
				case "LIST":
					List<Pokemon> pokemons = fileWorker.getAllPokemons();
					pokemons.forEach(pokemon -> System.out.println(pokemon.toString()));
					break;
					
				case "ADD":
					System.out.println("Creating new pokemon...");
					System.out.println("Please enter the pokemon's name:");
					String pokemonsName = consoleReader.readLine();
					
					System.out.println("Please enter it's level:");
					int pokemonsLevel;
					try {
						pokemonsLevel = Integer.parseInt(consoleReader.readLine());
					} catch(NumberFormatException ex) {
						System.out.println("The level was not a number. Terminating creation process...");
						break;
					}
					
					System.out.println("Please enter its abilities separated by a coma");
					System.out.println("Like so: <ability1>,<ability2>,<ability3>");
					List<String> pokemonAbilities = Arrays.asList(consoleReader.readLine().split(","));
					
					System.out.println("Please enter its Types separated by a coma");
					System.out.println("Like so: <type1>,<type2>,<type3>");
					System.out.println(String.format("Possible types: %s", Arrays.asList(PokemonType.values())
																				 .stream()
																				 .map(type->type.toString())
																				 .collect(Collectors.toList())
																				 .toString()));
					
					List<PokemonType> pokemonTypes;
					try {
						pokemonTypes = Arrays.asList(consoleReader.readLine().split(","))
															   .stream()
															   .map(type -> PokemonType.valueOf(type.toUpperCase()))
															   .collect(Collectors.toList());
					} catch(IllegalArgumentException ex) {
						System.out.println("An argument was not well parsed. remember not to use spaces between the types!");
						System.out.println("terminating creation process...");
						break;
					}
					
					System.out.println("Please enter its evolutions separated by a coma");
					System.out.println("IF THERE ARE NO EVOLUTIONS TYPE \"NO\"");
					System.out.println("Like so: <evolution1>,<evolution2>,<evolution3>");
					System.out.println(String.format("Possible Evolutions: %s", fileWorker.getAllPokemons()
																						  .stream()
																						  .map(pokemon -> pokemon.getName())
																						  .collect(Collectors.toList()).toString()));
					
					String evolutionsAsString = consoleReader.readLine();
					List<Pokemon> evolutions = null;
					if(!evolutionsAsString.contentEquals("NO")) {
						try {
							evolutions = Arrays.asList(evolutionsAsString.split(","))
											   .stream()
											   .map(pokemon -> {
												   try {
													   return fileWorker.getPokemonInfo(pokemon);
												   } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
													   e.printStackTrace();
												   }
												   	   return null;
											   })
											   .collect(Collectors.toList());
						} catch(NoSuchElementException ex) {
							System.out.println("There was a pokemon evolution that was not found on the pokedex. Terminating creation process...");
						}
					} else {
						evolutions = new ArrayList<Pokemon>();
					}
					
					System.out.println("Creating new pokemon and saving in pokedex...");
					Pokemon newPokemon = new Pokemon(pokemonsName, pokemonTypes, pokemonsLevel, pokemonAbilities, evolutions);
					fileWorker.savePokemon(newPokemon);
					break;
					
				case "EDIT":
					System.out.println("Please enter the pokemon's name you want to edit");
					System.out.println(String.format("Possible Pokemons: %s", fileWorker.getAllPokemons()
																						.stream()
																						.map(pokemon -> pokemon.getName())
																						.collect(Collectors.toList()).toString()));
					
					String pokemonNameToEdit = consoleReader.readLine();
					Pokemon pokemonToEdit = null;
					try {
						pokemonToEdit = fileWorker.getPokemonInfo(pokemonNameToEdit);
					} catch(Exception ex) {
						System.out.println("There was a problem obtaining the pokemon's info. Cancelling editing process...");
						break;
					}
					
					System.out.println("Type the attribute you want to edit from the pokemon:");
					System.out.println("NAME");
					System.out.println("ADD ABILITY");
					System.out.println("REMOVE ABILITY");
					System.out.println("ADD EVOLUTION");
					System.out.println("REMOVE EVOLUTION");
					System.out.println("ADD TYPE");
					System.out.println("REMOVE TYPE");
					System.out.println("LEVEL");
					
					String attributeToChange = consoleReader.readLine();
					switch(attributeToChange) {
						case "NAME":
							System.out.println("enter new name");
							pokemonToEdit.setName(consoleReader.readLine());
							System.out.println("name changed successfully");
							break;
						case "LEVEL":
							System.out.println("enter new level");
							try {
								pokemonToEdit.setLevel(Integer.parseInt(consoleReader.readLine()));
								System.out.println("level changed successfully");	
							} catch(Exception ex) {
								System.out.println("Number not parseable");
							}
							break;
						case "ADD ABILITY":
							System.out.println("enter new ability");
							pokemonToEdit.addAbility(consoleReader.readLine());
							System.out.println("ability added successfully");
							break;
						case "ADD EVOLUTION":
							System.out.println("enter new evolution");
							System.out.println(String.format("Possible Pokemons: %s", fileWorker.getAllPokemons()
																								.stream()
																								.map(pokemon -> pokemon.getName())
																								.collect(Collectors.toList()).toString()));
							
							try {
								pokemonToEdit.addEvolution(fileWorker.getPokemonInfo(consoleReader.readLine()));
								System.out.println("evolution added successfully");
							} catch(Exception ex) {
								System.out.println("There was a problem obtaining the pokemon's evolution info. Cancelling editing process...");
							}
							break;
						case "ADD TYPE":
							System.out.println("enter new type");
							System.out.println(String.format("Possible types: %s", Arrays.asList(PokemonType.values())
									 													 .stream()
									 													 .map(type->type.toString())
									 													 .collect(Collectors.toList())
									 													 .toString()));
							
							try {
								pokemonToEdit.addType(PokemonType.valueOf(consoleReader.readLine()));
								System.out.println("Type added successfully");
							} catch(Exception ex) {
								System.out.println("The type was not a valid Type. Cancelling editing process...");
							}
							break;
						case "REMOVE ABILITY":
							System.out.println(String.format("Possible abilities to remove: %s", pokemonToEdit.getAbilities().toString()));
							pokemonToEdit.removeAbility(consoleReader.readLine());
							System.out.println("Ability removed successfully");
							break;
						case "REMOVE TYPE":
							System.out.println(String.format("Possible types to remove: %s", pokemonToEdit.getTypes()
																										  .stream()
																										  .map(type->type.toString())
																										  .collect(Collectors.toList())
																										  .toString()));
							try {
								pokemonToEdit.removeType(PokemonType.valueOf(consoleReader.readLine()));
								System.out.println("Type Removed successfully");
							} catch(Exception ex) {
								System.out.println("The type was not a valid Type. Cancelling editing process...");
							}
							break;
						case "REMOVE EVOLUTION":
							System.out.println(String.format("Possible evolutions to remove: %s", pokemonToEdit.getEvolutions()
																											   .stream()
																											   .map(evolution->evolution.getName())
																											   .collect(Collectors.toList()).toString()));
							
							try {
								pokemonToEdit.removeEvolution(fileWorker.getPokemonInfo(consoleReader.readLine()));
								System.out.println("Evolution removed successfully");
							} catch(Exception ex) {
								System.out.println("The evolution was not a valid pokemon. Cancelling editing process...");
							}
							break;
					}
					fileWorker.savePokemon(pokemonToEdit);
			}
			
			System.out.println("Type another command...");
			command = consoleReader.readLine();
		}
	}
}
