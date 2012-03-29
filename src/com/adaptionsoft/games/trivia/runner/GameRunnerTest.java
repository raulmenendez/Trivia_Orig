package com.adaptionsoft.games.trivia.runner;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import com.adaptionsoft.games.uglytrivia.Game;
import static org.mockito.Mockito.*;

public class GameRunnerTest {
	
	private ByteArrayOutputStream outContent; 
	private Game game;
	private Random randomMock;
	
	@Before
	public void SetUp(){
		randomMock = mock(Random.class);
	}
	
	//Using output to verify behavior. It is a weak test instead. Any change on output test could fail.
	@Test
	public void one_play_for_player_one_and_all_answers_ok_has_6_gold_coins() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		game = new Game();
		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(2);
		game.add("Player1");
		
		GameRunner.play(game, randomMock);

		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));
		
		//verify(game,times(1)).wasCorrectlyAnswered();
		
	}
	
	// Using mock to verify behavior
	@Test
	public void one_play_for_player_one_but_answer_is_ko_so_penalty_box() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		game = mock(Game.class);
		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(7);
		game.add("Player1");
		
		GameRunner.play(game, randomMock);
		
		//assertEquals(true,outContent.toString().contains("Player1 was sent to the penalty box"));
		
		verify(game,times(1)).wrongAnswer();
		
			
	}
	
	@Test
	public void game_with_3_players_all_answers_ok_and_player_one_has_6_gold_coins() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Game gameSpy = spy(new Game());
		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(1);
		gameSpy.add("Player1");
		gameSpy.add("Player2");
		gameSpy.add("Player3");
		
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));
		
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	@Test
	public void game_with_3_players_some_answers_ok_and_player_two_has_6_gold_coins() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Game gameSpy = spy(new Game());
		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(1);
		gameSpy.add("Player1");
		gameSpy.add("Player2");
		gameSpy.add("Player3");
		
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player2 now has 6 Gold Coins."));
		
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
		verify(gameSpy,times(1)).wrongAnswer();
		
			
	}
	
	@Test
	public void game_with_3_players_some_answers_ok_and_player_three_has_6_gold_coins() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Game gameSpy = spy(new Game());
		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(7).thenReturn(1);
		gameSpy.add("Player1");
		gameSpy.add("Player2");
		gameSpy.add("Player3");
		
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player3 now has 6 Gold Coins."));
		
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
		verify(gameSpy,times(2)).wrongAnswer();
	}

			
}
