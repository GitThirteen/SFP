template<typename T>
Matrix<T>::Matrix(double width, double height) {
	this->width = width;
	this->height = height;
}

template<typename T>
void Matrix<T>::setWidth(double width) {
	this->width = width;
}

template <typename T>
double Matrix<T>::getWidth() {
	return width;
}

template <typename T>
void Matrix<T>::setHeight(double height) {
	this->height = height;
}

template <typename T>
double Matrix<T>::getHeight() {
	return height;
}