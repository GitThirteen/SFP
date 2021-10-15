/* Main Class */

#include <iostream>

#define GLFW_INCLUDE_NONE
#include <GLFW/glfw3.h>
#include <GL/glew.h>

#include "headers/Board.h"
#include "headers/Utility.h"
#include "headers/Config.h"

int main() {
	Config CONFIG = Config::get();

	Board mineField;
	mineField.generate(Difficulty::Intermediate);

	/* ------------------------- */
	// GLFW INIT
	/* ------------------------- */

	if (!glfwInit()) {
		Utility::throwError("GLFW init failed.");
	}

	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

	GLFWwindow* window = glfwCreateWindow(
		CONFIG.windowWidth(),
		CONFIG.windowHeight(),
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
}