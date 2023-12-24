from abc import ABC, abstractmethod
import re
import networkx as nx

from alphabet import MatrixAlphabet


class AbstractRelation(ABC):
  set_symbol = "AR"

  def __init__(self, alphabet: MatrixAlphabet) -> None:
    self.alphabet = alphabet
    self.result: list[list[int]] | None = None

  def __bool__(self) -> bool:
    return self.result != None

  def __str__(self) -> str:
    relation_set = f"{self.set_symbol} = {{%s}}"

    if not self.result or len(self.result) == 0:
      return relation_set % " "

    formatted_results: list[str] = []
    for i in range(len(self.alphabet.symbols)):
      for j in self.result[i]:
        i_str, j_str = self.__create_tuple_of_expressions(i, j)
        formatted_results.append(f"({i_str}, {j_str})")

    return relation_set % f" {', '.join(formatted_results)} "

  def __create_tuple_of_expressions(self, i: int, j: int) -> tuple[str, str]:
    return self.alphabet.symbols[i], self.alphabet.symbols[j]

  @abstractmethod
  def build(self) -> None:
    pass


class DependencyRelation(AbstractRelation):
  set_symbol = "D"

  # Override
  def build(self) -> None:
    N = self.alphabet.N
    self.result = [[] for _ in self.alphabet.symbols]

    for idx, symbol in enumerate(self.alphabet.symbols):
      symbol_type = symbol[0]
      i = j = k = 0

      match = list(map(
        lambda index: int(index), re.findall(r"[0-9]+", symbol)))

      if symbol_type == 'A' and len(match) == 2:
        i, k = match
      elif (symbol_type == 'B' or symbol_type == 'C') and len(match) == 3:
        i, j, k = match
      else:
        print(f"[Warning] Symbol \"{symbol}\" has been omitted.")
        continue

      self.result[idx].append(idx)

      if symbol_type == 'A':
        if i > 1:
          self.result[self.alphabet.get_index_by_symbol(f"C{i - 1},{i},{i}")]    .append(idx)
          self.result[self.alphabet.get_index_by_symbol(f"C{i - 1},{i},{i + 1}")].append(idx)
        for j in range(i, N + 2):
          self.result[idx].append(self.alphabet.get_index_by_symbol(f"B{i},{j},{k}"))
      elif symbol_type == 'B':
        self.result[idx].append(self.alphabet.get_index_by_symbol(f"C{i},{j},{k}"))
      elif symbol_type == 'C':
        if j > i + 1 and i + 1 < N:
          if k + 1 <= N:
            self.result[idx].append(self.alphabet.get_index_by_symbol(f"B{i + 1},{j},{k + 1}"))
          if i + 1 < k:
            self.result[idx].append(self.alphabet.get_index_by_symbol(f"C{i + 1},{j},{k}"))


class IndependencyRelation(AbstractRelation):
  set_symbol = "I"

  def __init__(self, alphabet: MatrixAlphabet, dependency_relation: DependencyRelation) -> None:
    super().__init__(alphabet)
    self.dependency_relation = dependency_relation

  # Override
  def build(self) -> None:
    n = len(self.alphabet.symbols)
    self.result = [
      [i for i in range(n)]
      for _ in range(n)
    ]

    for i in range(n):
      for relation in self.dependency_relation.result[i]:
        try:
          self.result[i].remove(relation)
        except:
          pass
