package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
    app.launch();
  }


  private void launch() {
    Scanner input = new Scanner(System.in);
    startUserInterface(input);
    input.close();
  }

  private void startUserInterface(Scanner input) {
	  boolean keepGoing = true;
	  while (keepGoing) {
		  System.out.println("Please select an option:");
		  System.out.println("1) Find film by ID");
		  System.out.println("2) Find film by Keyword");
		  System.out.println("3) Quit");
		  
		  int choice = input.nextInt();
		  input.nextLine();
		  
		  switch(choice) {
		  case 1:
			  printFilmIdSelector(input);
			  break;
		  case 2:
			  printFilmKeywordSelector(input);
			  break;
		  case 3:
		  default:
			  System.out.println("Goodbye!");
			  keepGoing = false;
		  }
	  }
  }
  
  private void printFilmIdSelector(Scanner input) {
	  System.out.println("Find a Film by ID:");
	  int filmId = input.nextInt();
	  input.nextLine();
	  Film film = db.findFilmById(filmId);
	  if (film == null) {
		  System.out.println("Film not found!");
	  } else {
		  System.out.println(film);
	  }
  }
  private void printFilmKeywordSelector(Scanner input) {
	  System.out.println("Find a Film by Keyword:");
	  String filmKeyword = input.nextLine();
	  List<Film> keyFilms = db.findFilmsByKeyword(filmKeyword);
	  if (keyFilms.size() > 0) {
		  System.out.println(keyFilms);
	  } else {
		  System.out.println("No matching data found!");
	  }
  }
  
}
