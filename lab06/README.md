# Trace theory
Home assignment

## Description
This project demonstrates, how concurrent tasks may be grouped by and run on different threads to achive better performance. **It only visualizes the method, doesn't implement it by running something concurrently**.

The program can be specified in 5 major steps (in chronological order):
- Creation of dependency relation ($D$).
- Creation of independency relation ($I$).
- Creation of minimal dependency relation of input word $DW$.
- Computation Foata normal form (FNF) of $DW$.
- Visualization of $DW$.


##### `main.py`
```py
import os
import sys
from file_reader import FileReader
from relation import DependencyRelation, IndependencyRelation, DependencyWordRelation
from normal_form import FoataNF
from drawer import GraphDrawer


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  PATH_DATA      = os.path.join(PATH, "../data")
  PATH_OUTPUT    = os.path.join(PATH, "../output")
  TEST_FILENAMES = [ "test1", "test2" ]

  test_filename = "test1"
  if len(sys.argv) > 1:
    test_filename = sys.argv[1]

  print(test_filename)

  # Read input file, first line is an alphabet, second one a word, rest are expressions.
  # The word is also converted to integer list.
  alphabet, word, expressions = FileReader.read(os.path.join(PATH_DATA, f"{test_filename}.txt"))
  word_int = list(map(lambda letter: ord(letter) - ord('a'), word))

  # Create and print dependency relation (D).
  dependency_relation = DependencyRelation(expressions, alphabet)
  dependency_relation.build()
  print(dependency_relation)

  # Create and print independency relation (I).
  independency_relation = IndependencyRelation(expressions, alphabet)
  independency_relation.build()
  print(independency_relation)

  # Create minimal dependency relation of the input word. 
  dependency_relation_word = DependencyWordRelation(word_int, dependency_relation, minimal=True)
  dependency_relation_word.build()

  # Create and print Foata normal form based on the input word.
  foata_normal_form = FoataNF(word_int, dependency_relation, independency_relation)
  foata_normal_form.build()
  print(f"FNF: {foata_normal_form}")

  # Visualize results.
  drawer = GraphDrawer(dependency_relation_word.results, word)
  drawer.draw(os.path.join(PATH_OUTPUT, f"{test_filename}.gv"))
```

##### `relation.py`
```py
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
```

#### Creation of dependency relation ($D$)
##### `main.py`
```py
  # Create and print dependency relation (D).
  dependency_relation = DependencyRelation(expressions, alphabet)
  dependency_relation.build()
  print(dependency_relation)
```

##### `relation.py`
```py
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
```

#### Creation of independency relation ($I$)
##### `main.py`
```py
  # Create and print independency relation (I).
  independency_relation = IndependencyRelation(expressions, alphabet)
  independency_relation.build()
  print(independency_relation)
```

##### `relation.py`
```py
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
```

#### Creation of minimal dependency relation of input word $DW$
##### `main.py`
```py
  # Create minimal dependency relation of the input word. 
  dependency_relation_word = DependencyWordRelation(word_int, dependency_relation, minimal=True)
  dependency_relation_word.build()
```

##### `relation.py`
```py
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
```

#### Computation Foata normal form (FNF) of $DW$
##### `main.py`
```py
  # Create and print Foata normal form based on the input word.
  foata_normal_form = FoataNF(word_int, dependency_relation, independency_relation)
  foata_normal_form.build()
  print(f"FNF: {foata_normal_form}")
```

##### `normal_form.py`
```py
from relation import DependencyRelation, IndependencyRelation

"""
Uses algorithm from chapter _2.4. a simple algorithm to compute normal forms_
from "Partial Commutation and Traces" by V. Diekert and Yves Metivier.
"""
class FoataNF:
  def __init__(
      self,
      word: list[int],
      dependency_relation: DependencyRelation,
      independency_relation: IndependencyRelation
  ) -> None:
    self.word                  = word
    self.dependency_relation   = dependency_relation
    self.independency_relation = independency_relation
    self.results: list[list[int]] | None = None

  def __str__(self) -> str:
    return "".join(map(self.__group_letters, self.results))
  
  def __group_letters(self, letters: list[int]) -> str:
    return f"({''.join(map(lambda letter: self.dependency_relation.alphabet[letter], letters))})"

  def build(self) -> None:
    stacks: list[list[int | None]] = [[] for _ in self.dependency_relation.alphabet]

    # Build stacks of input alphabet.
    for letter_word in reversed(self.word):
      stacks[letter_word].append(letter_word)
      dependent_letters = self.__get_dependent_letters(letter_word)
      for dependent_letter in dependent_letters:
        if dependent_letter == letter_word:
          continue
        stacks[dependent_letter].append(None)

    # Evaluate Foata normal from.
    self.results = []
    while not self.__are_stacks_empty(stacks):
      self.results.append([])
      # Letters to pop from top of the stacks.
      letters_to_pop = list(
        map(
          lambda stack: stack[-1],
          filter(lambda stack: len(stack) > 0 and stack[-1] is not None, stacks)
        )
      )
      # Pop desired letters and "stones", which are dependent with them.
      for letter_pop in letters_to_pop:
        self.results[-1].append(stacks[letter_pop].pop())
        dependent_letters = self.__get_dependent_letters(letter_pop)
        for dependent_letter in dependent_letters:
          if dependent_letter == letter_pop:
            continue
          stacks[dependent_letter].pop()

  def __get_dependent_letters(self, letter: int) -> list[int]:
    return self.dependency_relation.results[letter]

  @staticmethod
  def __are_stacks_empty(stacks: list[list[int | None]]) -> bool:
    for stack in stacks:
      if len(stack) > 0:
        return False
    return True
```

#### Visualization of $DW$
##### `main.py`
```py
  # Visualize results.
  drawer = GraphDrawer(dependency_relation_word.results, word)
  drawer.draw(os.path.join(PATH_OUTPUT, f"{test_filename}.gv"))
```

##### `drawer.py`
```py
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
```
