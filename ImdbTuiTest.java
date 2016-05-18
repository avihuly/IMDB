package com.course.imdb.tui;

import java.util.*;
import com.course.imdb.IMDB;
import com.course.imdb.Movie;

public class ImdbTuiTest {
	// Declaring the main IMDB instance
	private static IMDB imdb = new IMDB();

	// main
	public static void main(String[] args) {
		// Loading test movies
		IMDB.LoadTestMovies(imdb);

		System.out.println("Welcome to AVI's Movie DB");

		boolean flag = true;
		while (flag) {
			// mainMenu() returns the user choice as int
			switch (mainMenu()) {
			case 1:
				addMovieMenu();
				break;
			case 2:
				addActorMenu();
				break;
			case 3:
				allMoviesMenu();
				break;
			case 4:
				topMovieMenu();
				break;
			case 5:
				getMovieMenu();
				break;
			case 6:
				voteMenu();
				break;
			case 0:
				System.out.println("Bay Bay :)");
				flag = false;
				break;
			}
		}
	}

	// Returns int as the user Chosen function
	private static int mainMenu() {
		System.out.println();
		System.out.println("What would you like to do? (Selct a number)");
		System.out.println("1) Add Movie");
		System.out.println("2) Add actor");
		System.out.println("3) See all movies");
		System.out.println("4) See top movies");
		System.out.println("5) Get Movie");
		System.out.println("6) Vote");
		System.out.println("0) Exist");

		// in case of illegal value(not int / not in range) the method recurs
		try {
			int choice = Integer.parseInt(newUserInput());
			if (choice < 0 || choice > 6) {
				throw (new IllegalArgumentException());
			} else {
				return choice;
			}
		} catch (InputMismatchException | IllegalArgumentException e) {
			System.out.println("Please Enter a number between 0 - 6");
			return mainMenu();
		}
	}

	// Takes name and actors from user input
	private static void addMovieMenu() {
		String name;
		Set<String> actors = new HashSet<>();

		System.out.println();
		System.out.println("*****Add Movie Menu*****");
		System.out.println("Enter movie name");
		name = newUserInput();

		// Generating actor Set
		System.out.println("Enter actoers name's one by one (9 to stop):");
		boolean flag = true;
		while (flag) {
			String actor = newUserInput();
			if (!actor.equals("9")) {
				actors.add(actor);
			} else {
				flag = false;
			}
		}

		try {
			imdb.addMovie(name, actors);
			System.out.println("Movie successfully added");
		} catch (java.lang.IllegalArgumentException e) {
			System.out.println(e.getMessage());
			addMovieMenu();
		}
	}

	// Takes a movie name and actor name from user input
	private static void addActorMenu() {
		System.out.println();
		System.out.println("*******Add Actor Menu*******");
		System.out.println("Enter movie name:");
		try {
			Movie movie = imdb.getMovies().get(newUserInput());
			System.out.println("Enter actor name:");
			movie.addActor(newUserInput());
			System.out.println("Actor successfully added");
		} catch (IllegalArgumentException | NullPointerException e) {
			if (e instanceof IllegalArgumentException) {
				System.out.println(e.getMessage());
				addActorMenu();
			} else {
				System.out.println("the movie doesn't exist in DB");
				addActorMenu();
			}
		}
	}

	// Print all movie by alphabet order
	private static void allMoviesMenu() {
		System.out.println();
		System.out.println("*********All Movie*********");
		List<Movie> movieList = new ArrayList<>(imdb.getAll());
		Collections.sort(movieList);
		for (Movie movie : movieList) {
			System.out.println(movie);
		}
	}

	// Takes a name and top from user then prints top movies
	private static void topMovieMenu() {
		System.out.println();
		System.out.println("*******Top Movie Menu*******");
		System.out.println("Enter a number between 1 - " + imdb.getAll().size());
		try {
			for (Movie movie : imdb.getTop(Integer.parseInt(newUserInput()))) {
				System.out.println(movie);
			}
		} catch (IllegalArgumentException | InputMismatchException e) {
			topMovieMenu();
		}
	}

	// Takes movie name from user and prints it's details
	private static void getMovieMenu() {
		System.out.println();
		System.out.println("*******Get Movie Menu*******");
		System.out.println("Enter movie name:");
		String name = newUserInput();
		if (imdb.getMovies().get(name) != null) {
			System.out.println(imdb.getMovies().get(name));
		} else {
			System.out.println("The movie doesn't exist in DB");
			getMovieMenu();
		}
	}

	// Takes name and vote from user input
	private static void voteMenu() {
		String name;

		System.out.println();
		System.out.println("*******Vote Menu*******");
		System.out.println("Select movie to vote for: (Movie name)");
		name = newUserInput();
		System.out.println("Enter your vote: (0-10)");
		try {
			imdb.vote(name, Integer.parseInt(newUserInput()));
			System.out.println("Vote accepted");
		} catch (NullPointerException | InputMismatchException | IllegalArgumentException e) {
			if (e instanceof NullPointerException) {
				System.out.println("the Movie: " + name + " doesn't exist in DB");
				voteMenu();
			} else {
				System.out.println("Illegal vote - vote wasn't accepted");
				voteMenu();
			}
		}
	}

	// Get's String from user
	private static String newUserInput() {
		System.out.print("->");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

}
