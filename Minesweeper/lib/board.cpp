/* Board Class */

#include "board.h"

#include <iostream>

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
	Matrix<int> matrix(0, 0);
	int mines;
	
	switch (diff) {
	case Beginner:
		matrix.setHeight(8);
		matrix.setWidth(8);
		mines = 10;
		break;
	case Intermediate:
		matrix.setHeight(16);
		matrix.setWidth(16);
		mines = 40;
		break;
	case Expert:
		matrix.setHeight(16);
		matrix.setWidth(30);
		mines = 99;
		break;
	}

	this->setMatrix(matrix);
	this->setMines(mines);
}

void Board::fill() {
	Matrix<int> matrix = this->matrix;
}