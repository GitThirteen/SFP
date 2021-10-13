#pragma once

#include <stdint.h>

class Tile {
public:
	void setMineCount(uint8_t);
	uint8_t getMineCount();
	void setMine(bool);
	bool hasMine();
	void setFlag(bool);
	bool hasFlag();
	void uncover();
	bool isVisible();
private:
	/**
	* Contains all necessary data of this tile. 
	* The first 5 bits save the amount of adjacent mines, 
	* while the last 3 bit each represent a flag each for 
	* if the tile has a mine, if the tile has a flag marker 
	* and if the tile has already been uncovered.
	*/
	uint8_t bitMask = 0;
};