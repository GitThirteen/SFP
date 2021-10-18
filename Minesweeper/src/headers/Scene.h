#pragma once

#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <stdint.h>
#include <algorithm>
#include <cmath>

class Scene {
public:
	Scene(GLFWwindow*);
	void render();
	void clear();
	void clear(uint8_t r, uint8_t g, uint8_t b, float opacity);
private:
	GLFWwindow* window;
};