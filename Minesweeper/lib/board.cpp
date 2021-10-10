#include "headers/Board.h"

#include <iostream>
#include <vector>
#include <cmath>

typedef std::pair<int, int> intPair;

/* ------------------------- */
// Public
/* ------------------------- */

Board::Board() { }

Difficulty Board::getDiff() {
	return diff;
}

void Board::setMatrix(Matrix<int> matrix) {
	this->matrix = matrix;
}

Matrix<int> Board::getMatrix() {
	return matrix;
}

void Board::setMines(int mines) {
	this->mines = mines;
}

void Board::generate(Difficulty diff) {
	Matrix<int> matrix;
	int mines = 0;

	switch (diff) {
	case Beginner:
		matrix.setBoard(8, 8);
		mines = 10;
		break;
	case Intermediate:
		matrix.setBoard(16, 16);
		mines = 40;
		break;
	case Expert:
		matrix.setBoard(16, 30);
		mines = 99;
		break;
	}

	this->diff = diff;
	this->matrix = matrix;
	this->mines = mines;

	this->fill();
}

/* ------------------------- */
// Private
/* ------------------------- */

void Board::fill() {
	auto board = matrix.getBoard();
	int rows = matrix.getHeight();
	int cols = matrix.getWidth();

	std::vector<intPair> v = calcMinePos(rows - 1, cols - 1);
	for (auto coords : v) {
		board[coords.first][coords.second] = -1;
	}

	for (int row = 0; row < rows; row++) {
		for (int col = 0; col < cols; col++) {
			if (board[row][col] == -1) {
				continue;
			}

			int counter = 0;
			for (int x = col - 1; x <= col + 1; ++x) {
				for (int y = row - 1; y <= row + 1; ++y) {
					if ((x < 0 || x >= cols) || 
						(y < 0 || y >= rows) || 
						(x == col && y == row)) {
						continue;
					}

					if (board[y][x] == -1) {
						counter++;
					}
				}
			}

			board[row][col] = counter;
		}
	}

	matrix.setBoard(board);
}

std::vector<intPair> Board::calcMinePos(int rows, int cols) {
	std::vector<intPair> v;

	for (int i = 0; i < mines; i++) {
		int randY = randomIntBetweeen(0, rows);
		int randX = randomIntBetweeen(0, cols);

		v.push_back(intPair(randY, randX));
	}

	return v;
}

int Board::randomIntBetweeen(int min, int max) {
	return std::rand() % (max - min + 1) + min;
}