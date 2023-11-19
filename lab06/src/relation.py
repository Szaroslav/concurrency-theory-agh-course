from abc import ABC, abstractmethod
import string


class AbstractRelation(ABC):
  set_symbol = "AR"

  def __init__(self, expressions: list[tuple[str, list[str]]], alphabet: list[str]) -> None:
    self.expressions = expressions
    self.alphabet    = alphabet
    self.results: list[tuple[str, str]] | None = None

  def __str__(self) -> str:
    relation_set = f"{self.set_symbol} = {{%s}}"

    if not self.results or len(self.results) == 0:
      return relation_set % " "

    formatted_results = list(map(lambda x: f"({x[0]}, {x[1]})", self.results))
    return relation_set % f" {', '.join(formatted_results)} "

  def _create_tuple_of_expressions(self, i: int, j: int) -> tuple[str, str]:
    return self.alphabet[i], self.alphabet[j]

  @abstractmethod
  def build(self) -> None:
    pass


class DependencyRelation(AbstractRelation):
  set_symbol = "D"

  # Override
  def build(self) -> None:
    self.results = []
    expressions_enumerate_list = list(enumerate(self.expressions))
    for i, (assigned_var, operating_vars) in expressions_enumerate_list:
      for j, (assigned_var_inner, operating_vars_inner) in expressions_enumerate_list:
        if (
             assigned_var == assigned_var_inner
          or assigned_var in operating_vars_inner
          or assigned_var_inner in operating_vars
        ):
          self.results.append(self._create_tuple_of_expressions(i, j))


class IndependencyRelation(AbstractRelation):
  set_symbol = "I"

  # Override
  def build(self) -> None:
    self.results = []
    expressions_enumerate_list = list(enumerate(self.expressions))
    for i, (assigned_var, operating_vars) in expressions_enumerate_list:
      for j, (assigned_var_inner, operating_vars_inner) in expressions_enumerate_list:
        if not (
             assigned_var == assigned_var_inner
          or assigned_var in operating_vars_inner
          or assigned_var_inner in operating_vars
        ):
          self.results.append(self._create_tuple_of_expressions(i, j))
