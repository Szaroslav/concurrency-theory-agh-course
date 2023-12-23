class MatrixAlphabet:
  def __init__(self, N: int) -> None:
    self.N = N
    self.built = False
    self.symbols: list[str] = []

  def __str__(self) -> str:
    if not self.built:
      raise ValueError("MatrixAlphabet is not built yet.")

    return "\n".join(self.symbols)

  def build(self) -> None:
    for i in range(1, self.N):
      for k in range(i + 1, self.N + 1):
        self.symbols.append(f"A_({i}, {k})")
        for j in range(i, self.N + 2):
          self.symbols.append(f"B_({i}, {j}, {k})")
        for j in range(i, self.N + 2):
          self.symbols.append(f"C_({i}, {j}, {k})")

    self.built = True
