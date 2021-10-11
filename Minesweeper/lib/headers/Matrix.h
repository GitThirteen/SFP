#pragma once

#include <vector>

template<typename T>
using Mat2D = std::vector<std::vector<T>>;

template<typename T>
class Matrix {
public:
	Matrix<T>() {
		Mat2D<T> matrix(0, std::vector<T>(0, 0));
		this->board = matrix;
	}

	Matrix<T>(int width, int height) {
		Mat2D<T> matrix(height, std::vector<T>(width, 0));
		this->board = matrix;
	}

	int getWidth() {
		return this->board[0].size();
	}

	int getHeight() {
		return this->board.size();
	}

	void setBoard(Mat2D<T> board) {
		this->board = board;
	}

	void setBoard(int x, int y) {
		Mat2D<T> matrix(y, std::vector<T>(x, 0));
		this->board = matrix;
	}

	Mat2D<T> getBoard() {
		return board;
	}
private:
	Mat2D<T> board;
};