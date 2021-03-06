package edu.uw.sudoku_solver.sudoku;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public class SudokuBoard {
	public static final Gson BUILDER = new GsonBuilder().setPrettyPrinting().create();

	private int[][] board;
	private List<Set<Integer>> rows;
	private List<Set<Integer>> cols;
	private List<Set<Integer>> squares;
	private int size;
	private int squareSize;

	public SudokuBoard(int[][] board) {
		this.board = board;
		rows = new LinkedList<Set<Integer>>();
		cols = new LinkedList<Set<Integer>>();
		squares = new LinkedList<Set<Integer>>();
		this.size = board.length;
		this.squareSize = (int) Math.sqrt(board.length);
		for (int i = 0; i < board.length; i++) {
			this.rows.add(new HashSet<Integer>());
			this.cols.add(new HashSet<Integer>());
			this.squares.add(new HashSet<Integer>());
		}
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (board[row][col] != 0) {
					rows.get(row).add(board[row][col]);
					cols.get(col).add(board[row][col]);
					squares.get(row / this.squareSize * this.squareSize + col / this.squareSize)
							.add(board[row][col]);
				}
			}
		}
	}

	public SudokuBoard(JsonArray jsonBoardArray) {
		this(BUILDER.fromJson(jsonBoardArray, int[][].class));
	}

	public int[][] getBoard() {
		return board;
	}

	public int get(int row, int col) {
		return board[row][col];
	}

	public boolean canEnter(int num, int row, int col) {
		return !this.rows.get(row).contains(num) && !this.cols.get(col).contains(num) && !this.squares
				.get(row / this.squareSize * this.squareSize + col / this.squareSize).contains(num);
	}

	public void set(int num, int row, int col) {
		this.rows.get(row).add(num);
		this.cols.get(col).add(num);
		this.squares.get(row / this.squareSize * this.squareSize + col / this.squareSize).add(num);
		this.board[row][col] = num;
	}

	public void remove(int row, int col) {
		int numToRemove = this.board[row][col];
		this.rows.get(row).remove(numToRemove);
		this.cols.get(col).remove(numToRemove);
		this.squares.get(row / this.squareSize * this.squareSize + col / this.squareSize)
				.remove(numToRemove);
		this.board[row][col] = 0;
	}

	public int getSize() {
		return size;
	}

	public int getSquareSize() {
		return squareSize;
	}

	public String toJson() {
		return BUILDER.toJson(board, board.getClass());
	}
}
