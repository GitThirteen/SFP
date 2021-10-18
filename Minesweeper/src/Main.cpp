/* Main Class */

#include <iostream>

#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include "headers/Board.h"
#include "headers/Utility.h"
#include "headers/Config.h"
#include "headers/Scene.h"

int main() {
	Config CONFIG = Config::get();

	Board mineField;
	mineField.generate(Difficulty::Expert);

	/* ------------------------- */
	// GLFW INIT
	/* ------------------------- */

	if (!glfwInit()) {
		Utility::throwError("GLFW init failed.");
	}

	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

	GLFWwindow* window = glfwCreateWindow(
		mineField.getMatrix().getWidth() * CONFIG.tileSize() + CONFIG.border(),
		mineField.getMatrix().getHeight() * CONFIG.tileSize() + CONFIG.border(),
		CONFIG.gameTitle().c_str(),
		NULL,
		NULL
	);
	if (!window) {
		Utility::throwError("Window init failed.");
	}

	glfwMakeContextCurrent(window);

	/* ------------------------- */
	// GLEW INIT
	/* ------------------------- */

	glewExperimental = true;
	GLenum glewResponse = glewInit();
	if (glewResponse != GLEW_OK) {
		Utility::throwError("GLEW init failed.");
	}

	/* ------------------------- */
	// RENDER GO BRRRRR
	/* ------------------------- */

	Scene scene(window);

	while (!glfwWindowShouldClose(window)) {
		scene.clear();
		scene.render();
	}

	/* ------------------------- */
	// EXIT
	/* ------------------------- */

	glfwDestroyWindow(window);
	glfwTerminate();

	return EXIT_SUCCESS;
}