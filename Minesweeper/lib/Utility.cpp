#include "headers/Utility.h"

#include <iostream>

int Utility::throwError(std::string error) {
	std::cerr << error << std::endl;
	return EXIT_FAILURE;
}