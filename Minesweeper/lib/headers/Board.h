#pragma once

#include <vector>

#include "Matrix.h"

enum Difficulty { Beginner, Intermediate, Expert, Invalid };

class Board {
typedef std::pair<int, int> intPair;

public:
	Board();

	/**
	* Returns the Difficulty of this Board.
	* 
	* @return A Difficulty enum, or Difficulty::Invalid (3) if Board::generate()
	* had been called without a difficulty before.
	*/
	Difficulty getDiff();

	/**
	* Sets the matrix of this board.
	* 
	* @param[in] matrix: A matrix object
	*/
	void setMatrix(Matrix<int> matrix);
	Matrix<int> getMatrix();
	void setMines(int mines);

	/**
	* Initializes the playing field with all necessary parameters. 
	* Width, height and mines are set based on the difficulty.
	* 
	* @param[in] diff: The difficulty of the board
	* 
	* @throws std::invalid_argument Thrown if the difficulty is set to Difficulty::Invalid
	*/
	void generate(Difficulty diff);
	/**
	* Initializes the playing field.
	* 
	* @param[in] width: The width of the matrix
	* @param[in] height: The height of the matrix
	* @param[in] mines: The amount of bombs placed in the matrix
	*/
	void generate(int width, int height, int mines);

private:
	Difficulty diff;
	Matrix<int> matrix;
	int mines;

	void fill();
	std::vector<intPair> calcMinePos(int rows, int cols);
	int randomIntBetweeen(int min, int max);
};