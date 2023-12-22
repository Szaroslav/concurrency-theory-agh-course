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
