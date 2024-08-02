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
//    app.test();
    app.launch();
  }

//  private void test() {
//    Film film = db.findFilmById(1);
//    Actor actor = db.findActorById(1);
//    System.out.println(film);
//    System.out.println(actor);
//  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    startUserInterface(input);
    input.close();
  }

  private void startUserInterface(Scanner input) {
	  System.out.println("Find a Film by ID:");
	  int filmId = input.nextInt();
	  input.nextLine();
	  Film film = db.findFilmById(filmId);
	  System.out.println(film);
	  
	  System.out.println("Find a Film by Keyword:");
	  String filmKeyword = input.nextLine();
	  List<Film> keyFilms = db.findFilmsByKeyword(filmKeyword);
	  System.out.println(keyFilms);
  }

}
