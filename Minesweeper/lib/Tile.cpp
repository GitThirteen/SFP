#include "headers/Tile.h"
#include "headers/Utility.h"

#include <stdint.h>
#include <string>
#include <bitset>

void Tile::setMineCount(uint8_t mines) {
	if (getMineCount() != 0) {
		Utility::throwError("Mine count has already been set!");
	}

	uint8_t values = mines << 3;
	this->bitMask |= values;
}
uint8_t Tile::getMineCount() {
	return ((this->bitMask & 0b11111000) >> 3);
}

void Tile::setMine(bool mine) {
	if (mine) {
		this->bitMask |= (1UL << 2);
	}
	else {
		uint8_t inverted = ~(1UL << 2);
		this->bitMask &= inverted;
	}
}
bool Tile::hasMine() {
	return (this->bitMask & 0b100) != 0;
}

void Tile::setFlag(bool flag) {
	if (flag) {
		this->bitMask |= (1UL << 1);
	}
	else {
		uint8_t inverted = ~(1UL << 1);
		this->bitMask &= inverted;
	}
}
bool Tile::hasFlag() {
	return (this->bitMask & 0b10) != 0;
}

void Tile::uncover() {
	this->bitMask |= 0b1;
}
bool Tile::isVisible() {
	return (this->bitMask & 0b1) != 0;
}