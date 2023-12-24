import numpy as np


class MatrixAlphabet:
  def __init__(self, N: int) -> None:
    self.N = N
    self.built = False
    self.symbols: list[str] = []
    self.__symbol_to_index_map: dict[str, int] = {}

  def __str__(self) -> str:
    if not self.built:
      raise ValueError("MatrixAlphabet is not built yet.")

    return "\n".join(self.symbols)

  def __getitem__(self, key: int | str) -> str:
    return self.symbols[key]

  def get_index_by_symbol(self, symbol: str) -> int:
    index = self.__symbol_to_index_map.get(symbol)
    if index is None:
      raise ValueError("Invalid symbol.")

    return index

  def build(self) -> None:
    for i in range(1, self.N):
      for k in range(i + 1, self.N + 1):
        self.__append(f"A_({i},{k})")
        for j in range(i, self.N + 2):
          self.__append(f"B_({i},{j},{k})")
        for j in range(i, self.N + 2):
          self.__append(f"C_({i},{j},{k})")

    self.built = True

  def __append(self, symbol: str) -> None:
    self.symbols.append(symbol)
    self.__symbol_to_index_map[symbol] = len(self.symbols) - 1
