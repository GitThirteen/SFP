#include "headers/Board.h"

#include <iostream>
#include <vector>
#include <cmath>

typedef std::pair<int, int> intPair;

void Board::fill() {
	auto board = matrix.getBoard();
	int rows = board.size();
	int cols = board[0].size();

	std::vector<intPair> v = calcMinePos(rows, cols);

	for (intPair coords : v) {
		board[coords.first][coords.second] = -1;
	}

	for (int row = 0; row < rows; row++) {
		for (int col = 0; col < cols; col++) {
			if (board[row][col] == -1) {
				continue;
			}
				
			int counter = 0;
			for (int x = col - 1; x < 3; x++) {
				for (int y = row - 1; y < 3; y++) {
					if (x < 0 || x >= cols || y < 0 || y >= rows) {
						continue;
					}

					if (board[x][y] == -1) {
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
		int randX = randomIntBetweeen(0, cols);
		int randY = randomIntBetweeen(0, rows);
		v.push_back(intPair(randX, randY));
	}

	return v;
}

int Board::randomIntBetweeen(int min, int max) {
	return std::rand() % (max - min + 1) + min;
}

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
