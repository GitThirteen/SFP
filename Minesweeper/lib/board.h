enum Difficulty { Beginner, Intermediate, Expert };

template<typename T>
struct Matrix {
public:
	Matrix<T>(double, double);

	void setWidth(double width);
	double getWidth();
	void setHeight(double height);
	double getHeight();
private:
	double width;
	double height;
	T board;
};

class Board {
public:
	Difficulty getDiff();
	void setMatrix(Matrix<int>);
	Matrix<int> getMatrix();
	void setMines(int);

	void generate(Difficulty);
private:
	Difficulty diff;
	Matrix<int> matrix;
	int mines;

	Board();
	void fill();
};

#include "Matrix.tpp"