#include <iostream>

double f(const double);
double integrate(const double, const double, const int);

int main() {
  std::cout << "Vector multiplication" << std::endl;

  return 0;
}

double f(const double x) {
  return 1 / (x * x);
}

double integrate(const double a, const double b, const int steps_number) {
  const double step = (b - a) / steps_number;
  double result = 0;

  #pragma parallel for
  {
    for (int i = 0; i < steps_number; i++) {
      result += (f(a) + f(b)) / 2 * (b - a);
    }
  }

  return result;
}
