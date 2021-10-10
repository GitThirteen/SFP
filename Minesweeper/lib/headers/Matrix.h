#pragma once

#include <vector>

template<typename T>
using Mat2D = std::vector<std::vector<T>>;

template<typename T>
class Matrix {
public:
	Matrix<T>();
	Matrix<T>(int, int);

	int getWidth();
	int getHeight();
	void setBoard(Mat2D<T>);
	void setBoard(int, int);
	Mat2D<T> getBoard();

private:
	Mat2D<T> board;
};