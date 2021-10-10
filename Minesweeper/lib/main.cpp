/* Main Class */

#include <iostream>

#include "headers/Board.h"

int main() {
	Board mineField;
	mineField.generate(Difficulty::Beginner);

	auto matrix = mineField.getMatrix();
	for (int x = 0; x < matrix.getWidth(); x++) {
		for (int y = 0; y < matrix.getHeight(); y++) {
			std::cout << matrix.getBoard()[x][y] << " ";
		}
		std::cout << "\n";
	}
}