from abc import ABC, abstractmethod
import networkx as nx

class AbstractRelation(ABC):
  set_symbol = "AR"

  def __init__(self, expressions: list[tuple[str, list[str]]], alphabet: list[str]) -> None:
    self.expressions = expressions
    self.alphabet    = alphabet
    self.results: list[list[int]] | None = None

  def __bool__(self) -> bool:
    return self.results != None

  def __str__(self) -> str:
    relation_set = f"{self.set_symbol} = {{%s}}"

    if not self.results or len(self.results) == 0:
      return relation_set % " "

    formatted_results: list[str] = []
    for i in range(len(self.alphabet)):
      for j in self.results[i]:
        i_str, j_str = self.__create_tuple_of_expressions(i, j)
        formatted_results.append(f"({i_str}, {j_str})")

    return relation_set % f" {', '.join(formatted_results)} "

  def __create_tuple_of_expressions(self, i: int, j: int) -> tuple[str, str]:
    return self.alphabet[i], self.alphabet[j]

  @abstractmethod
  def build(self) -> None:
    pass


class DependencyRelation(AbstractRelation):
  set_symbol = "D"

  # Override
  def build(self) -> None:
    self.results = [[] for _ in self.alphabet]
    expressions_enumerate_list = list(enumerate(self.expressions))
    for i, (assigned_var, operating_vars) in expressions_enumerate_list:
      for j, (assigned_var_inner, operating_vars_inner) in expressions_enumerate_list:
        if (
             assigned_var == assigned_var_inner
          or assigned_var in operating_vars_inner
          or assigned_var_inner in operating_vars
        ):
          self.results[i].append(j)


class IndependencyRelation(AbstractRelation):
  set_symbol = "I"

  # Override
  def build(self) -> None:
    self.results = [[] for _ in self.alphabet]
    expressions_enumerate_list = list(enumerate(self.expressions))
    for i, (assigned_var, operating_vars) in expressions_enumerate_list:
      for j, (assigned_var_inner, operating_vars_inner) in expressions_enumerate_list:
        if not (
             assigned_var == assigned_var_inner
          or assigned_var in operating_vars_inner
          or assigned_var_inner in operating_vars
        ):
          self.results[i].append(j)

"""
Uses NetworkX library to create minimal directed graph.
"""
class DependencyWordRelation(AbstractRelation):
  set_symbol = "DW"

  def __init__(
      self,
      word: list[int],
      dependency_relation: DependencyRelation,
      **kwargs: dict[str, any]
  ) -> None:
    super().__init__(dependency_relation.expressions, dependency_relation.alphabet)
    self.word                = word
    self.dependency_relation = dependency_relation
    self.results: list[list[int]] | None = None

    self.minimal = kwargs.get("minimal", False)

  # Override
  def __str__(self) -> str:
    return self.set_symbol

  # Override
  def build(self) -> None:
    if not self.dependency_relation:
      raise ValueError("Dependency relation is not builded.")

    # Create full directed acyclic relation graph of the input word.
    self.results = [[] for _ in self.word]
    for i, letter in enumerate(self.word):
      for j in range(i + 1, len(self.word)):
        if self.word[j] in self.dependency_relation.results[letter]:
          self.results[i].append(j)

    if self.minimal:
      # Reduce the graph.
      edge_list: list[tuple[int, int]] = []
      for u in range(len(self.word)):
        for v in self.results[u]:
          edge_list.append((u, v))

      digraph = nx.DiGraph(edge_list)
      reduced_digraph = nx.transitive_reduction(digraph)

      self.results = [[] for _ in self.word]
      for u, v in reduced_digraph.edges:
        self.results[u].append(v)
