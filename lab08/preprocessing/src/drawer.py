import graphviz
from alphabet import MatrixAlphabet


class GraphDrawer:
  def __init__(self, digraph: list[list[int]], alphabet: MatrixAlphabet) -> None:
    self.digraph  = digraph
    self.alphabet = alphabet

  def draw(self, file: str, self_loop: bool = False) -> None:
    digraph_dot = graphviz.Digraph(name="Hesse diagram")

    for u in range(len(self.digraph)):
      digraph_dot.node(str(u), self.alphabet[u])

    for u in range(len(self.digraph)):
      for v in self.digraph[u]:
        if not self_loop and u != v:
          digraph_dot.edge(str(u), str(v))
        elif self_loop:
          digraph_dot.edge(str(u), str(v))

    print(digraph_dot.source)

    digraph_dot.render(file, format="png", overwrite_source=True)
