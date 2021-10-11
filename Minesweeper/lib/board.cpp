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
	if (diff < Difficulty::Beginner || diff > Difficulty::Expert) {
		return Difficulty::Invalid;
	}

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
	if (diff == Difficulty::Invalid) {
		std::string error = "Difficulty cannot be set to \"Invalid\"!";
		std::cerr << error << std::endl;
		throw new std::invalid_argument(error);
	}

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

void Board::generate(int width, int height, int mines) {
	if (mines >= (width * height)) {
		std::string error = "Mines greater or equal board size.";
		std::cerr << error << std::endl;
		throw new std::invalid_argument(error);
	}

	Matrix<int> matrix(width, height);
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

	std::vector<intPair> v = calcMinePos(rows, cols);
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
	std::vector<intPair> tileVec;
	std::vector<intPair> mineVec;

	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			tileVec.push_back(intPair(i, j));
		}
	}

	for (int i = 0; i < mines; i++) {
		int rand = randomIntBetweeen(0, tileVec.size() - 1);
		mineVec.push_back(tileVec[rand]);
		tileVec.erase(tileVec.begin() + rand);
	}

	return mineVec;
}

int Board::randomIntBetweeen(int min, int max) {
	return std::rand() % (max - min + 1) + min;
}