import graphviz


class GraphDrawer:
  def __init__(self, digraph: list[list[int]], word: list[str]) -> None:
    self.digraph  = digraph
    self.word     = word

  def draw(self, file: str) -> None:
    digraph_dot = graphviz.Digraph(name="Hesse diagram")

    for u in range(len(self.digraph)):
      digraph_dot.node(str(u), self.word[u])

    for u in range(len(self.digraph)):
      for v in self.digraph[u]:
        digraph_dot.edge(str(u), str(v))

    print(digraph_dot.source)

    digraph_dot.render(file, format="png", overwrite_source=True)
