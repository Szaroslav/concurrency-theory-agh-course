import string


class DependencyRelation:
  def __init__(self, expressions: list[tuple[str, list[str]]], alphabet: list[str]) -> None:
    self.expressions = expressions
    self.alphabet    = alphabet
    self.results: list[tuple[str, str]] | None = None

  def __str__(self) -> str:
    dependecy_set = "D = {%s}"

    if not self.results or len(self.results) == 0:
      return dependecy_set % " "

    formatted_results = list(map(lambda x: f"({x[0]}, {x[1]})", self.results))
    return dependecy_set % f" {', '.join(formatted_results)} "

  def build(self) -> list[tuple[str, str]]:
    self.results = []
    expressions_enumerate_list = list(enumerate(self.expressions))
    for i, (assigned_var, operating_vars) in expressions_enumerate_list:
      for j, (assigned_var_inner, operating_vars_inner) in expressions_enumerate_list:
        if (
             assigned_var == assigned_var_inner
          or assigned_var in operating_vars_inner
          or assigned_var_inner in operating_vars
        ):
          self.results.append(self.__create_tuple_of_expressions(i, j))

  def __create_tuple_of_expressions(self, i: int, j: int) -> tuple[str, str]:
    return self.alphabet[i], self.alphabet[j]
