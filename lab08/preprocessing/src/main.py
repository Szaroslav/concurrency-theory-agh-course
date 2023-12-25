import os
import sys
from alphabet import MatrixAlphabet
from file import File
from relation import DependencyRelation, IndependencyRelation
from normal_form import FoataNF
from drawer import GraphDrawer


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  PATH_DATA      = os.path.join(PATH, "../data")
  PATH_OUTPUT    = os.path.join(PATH, "../output")
  PATH_GAUSSIAN  = os.path.join(PATH, "../../gaussian-elimination")
  TEST_FILENAMES = [ "test1", "test2" ]

  test_filename = "test"
  if len(sys.argv) > 1:
    test_filename = sys.argv[1]

  print(test_filename)

  # Read input file, first line is number of variables (height of matrix).
  # The rest are columns of matrix, the last one column is column of values.
  N, matrix = File.read(os.path.join(PATH_DATA, f"{test_filename}.txt"))
  print(matrix)

  alphabet = MatrixAlphabet(N)
  alphabet.build()
  print(alphabet)

  # Create and print dependency relation (D).
  dependency_relation = DependencyRelation(alphabet)
  dependency_relation.build()
  print(dependency_relation)

  # Visualize results.
  drawer = GraphDrawer(dependency_relation.result, alphabet)
  drawer.draw(os.path.join(PATH_OUTPUT, f"{test_filename}.gv"))
  drawer.draw(os.path.join(PATH_OUTPUT, f"{test_filename}_loop.gv"), self_loop=True)

  # Create and print independency relation (I).
  independency_relation = IndependencyRelation(alphabet, dependency_relation)
  independency_relation.build()
  print(independency_relation)

  # Create and print Foata normal form based on the input word.
  all_symbols = [i for i in range(len(alphabet.symbols))]
  foata_normal_form = FoataNF(all_symbols, dependency_relation, independency_relation)
  foata_normal_form.build()
  print(f"FNF: {foata_normal_form}")
  File.write(os.path.join(PATH_GAUSSIAN, "src/main/resources/fnf.txt"), foata_normal_form)
