#include "headers/Matrix.h"

#include <vector>

template<typename T>
Matrix<T>::Matrix() {
	Mat2D matrix(0, std::vector<T>(0, 0));
	this->board = matrix;
}

template<typename T>
Matrix<T>::Matrix(int width, int height) {
	Mat2D matrix(width, std::vector<T>(height, 0));
	this->board = matrix;
}

template<typename T>
double Matrix<T>::getWidth() {
	return this->board[0].size();
}

template<typename T>
double Matrix<T>::getHeight() {
	return this->board.size();
}

template<typename T>
void Matrix<T>::setBoard(Mat2D<T> board) {
	this->board = board;
}

template<typename T>
void Matrix<T>::setBoard(int x, int y) {
	Mat2D matrix(x, std::vector<T>(y, 0));
	this->board = matrix;
}

template<typename T>
Mat2D<T> Matrix<T>::getBoard() {
	return board;
}