#pragma once

#include <stdint.h>
#include <string>

class Config {
private:
	std::string GAME_TITLE = "Minesweeper";
	uint16_t WINDOW_WIDTH = 400;
	uint16_t WINDOW_HEIGHT = 400;

	Config() { }

public:
	static Config& get() {
		static Config instance;

		return instance;
	}

	std::string gameTitle() { return GAME_TITLE; }
	uint16_t windowWidth() { return WINDOW_WIDTH; }
	uint16_t windowHeight() { return WINDOW_HEIGHT; }

	// Config(Config const&)			= delete;
	// void operator=(Config const&)	= delete;
};