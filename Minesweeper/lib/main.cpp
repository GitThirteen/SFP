/* Main Class */

#include <iostream>

#include "headers/Board.h"

int main() {
	Board mineField;
	mineField.generate(Difficulty::Intermediate);

	auto matrix = mineField.getMatrix();
	auto board = matrix.getBoard();
	for (int col = 0; col < matrix.getHeight(); col++) {
		for (int row = 0; row < matrix.getWidth(); row++) {
			if (board[col][row].hasMine()) {
				std::cout << "X" << " ";
			}
			else {
				std::cout << (int) board[col][row].getMineCount() << " ";
			}
			
		};
		std::cout << "\n";
	}

	//std::cout << mineField.getDiff() << std::endl;
}