#include "headers/Scene.h"

Scene::Scene(GLFWwindow* window) {
	this->window = window;
}

void Scene::render() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glfwWaitEvents();

	// Rendering goes here

	glfwSwapBuffers(this->window);
}

void Scene::clear() {
	glClearColor(255, 255, 255, 1);
}

void Scene::clear(uint8_t r, uint8_t g, uint8_t b, float opacity = 1) {
	float op = std::min(std::abs(opacity), (float) 1);
	glClearColor(r, g, b, opacity);
}