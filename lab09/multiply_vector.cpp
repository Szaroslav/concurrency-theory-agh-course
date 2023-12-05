#include <iostream>

double * multiply_scalar(double *, const double);
double multiply_vector(const double *, const double *);

int main() {
  std::cout << "Vector multiplication" << std::endl;

  return 0;
}

double * multiply_scalar(double *vector, const double scalar) {
  const int N = sizeof(vector) / sizeof(double);

  #pragma omp parallel for
    for (int i = 0; i < N; i++) {
      vector[i] *= scalar;
    }

  return vector;
}

double multiply_vector(const double *vector1, const double *vector2) {
  const int N = sizeof(vector1) / sizeof(double),
            M = sizeof(vector2) / sizeof(double);

  if (N != M) {
    return 0;
  }

  double result;

  #pragma omp parallel for shared(result)
    for (int i = 0; i < N; i++) {
      result += vector1[i] * vector2[i];
    }

  return result;
}
