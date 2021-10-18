#pragma once

#include <stdint.h>
#include <string>

class Config {
private:
	std::string GAME_TITLE = "Minesweeper";
	uint16_t TILE_SIZE = 20;
	uint16_t BORDER = 100;

	Config() { }

public:
	static Config& get() {
		static Config instance;

		return instance;
	}

	std::string gameTitle() { return GAME_TITLE; }
	uint16_t tileSize() { return TILE_SIZE; }
	uint16_t border() { return BORDER; }

	// Config(Config const&)			= delete;
	// void operator=(Config const&)	= delete;
};