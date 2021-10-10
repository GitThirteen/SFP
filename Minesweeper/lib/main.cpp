/* Main Class */

#include <iostream>

#include "headers/Board.h"

int main() {
	Board mineField;
	mineField.generate(Difficulty::Intermediate);

	auto matrix = mineField.getMatrix();
	for (int col = 0; col < matrix.getHeight(); col++) {
		for (int row = 0; row < matrix.getWidth(); row++) {
			std::cout << matrix.getBoard()[col][row] << " ";
		};
		std::cout << "\n";
	}
}