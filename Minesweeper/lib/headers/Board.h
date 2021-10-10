#pragma once

#include <vector>

#include "Matrix.h"

enum Difficulty { Beginner, Intermediate, Expert };

class Board {
typedef std::pair<int, int> intPair;

private:
	Difficulty diff;
	Matrix<int> matrix;
	int mines;

	void fill();
	std::vector<intPair> calcMinePos(int, int);
	int randomIntBetweeen(int min, int max);
public:
	Board();

	Difficulty getDiff();
	void setMatrix(Matrix<int>);
	Matrix<int> getMatrix();
	void setMines(int);

	void generate(Difficulty);
};