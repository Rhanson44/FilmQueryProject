package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String user = "student";
	private static final String pass = "student";
	
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sqltext = "SELECT film.title, film.release_year, film.rating, film.description, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sqltext);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				film = new Film();
				film.setTitle(rs.getString(1));
				film.setReleaseYear(rs.getInt(2));
				film.setRating(rs.getString(3));
				film.setDescription(rs.getString(4));
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(rs.getString(5));
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sqltext = "SELECT * FROM actor WHERE actor.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sqltext);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				actor = new Actor();
			    actor.setId(rs.getInt(1));
			    actor.setFirstName(rs.getString(2));
			    actor.setLastName(rs.getString(3));
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sqltext = "SELECT actor.id, actor.first_name, actor.last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film.id = film_actor.film_id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sqltext);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				Actor actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}
	
	@Override
	public List<Film> findFilmsByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		Connection conn;
		keyword = "%" + keyword + "%";
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sqltext = "SELECT film.id, film.title, film.release_year, film.rating, film.description, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";

			PreparedStatement stmt = conn.prepareStatement(sqltext);
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setReleaseYear(rs.getInt(3));
				film.setRating(rs.getString(4));
				film.setDescription(rs.getString(5));
				film.setLanguage(rs.getString(6));
				film.setActors(findActorsByFilmId(film.getId()));
				films.add(film);
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load MySQL driver");
			e.printStackTrace();
		}
	}

}
