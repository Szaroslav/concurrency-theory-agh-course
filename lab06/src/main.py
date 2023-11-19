import os
from file_reader import FileReader
from relation import DependencyRelation, IndependencyRelation, DependencyWordRelation
from normal_form import FoataNF
from drawer import GraphDrawer


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  PATH_DATA      = os.path.join(PATH, "../data")
  PATH_OUTPUT    = os.path.join(PATH, "../output")
  TEST_FILENAMES = [ "test1", "test2" ]

  for test_fn in TEST_FILENAMES:
    print(test_fn)
    alphabet, word, expressions = FileReader.read(os.path.join(PATH_DATA, f"{test_fn}.txt"))
    word_int = list(map(lambda letter: ord(letter) - ord('a'), word))

    dependency_relation = DependencyRelation(expressions, alphabet)
    dependency_relation.build()
    print(dependency_relation)

    independency_relation = IndependencyRelation(expressions, alphabet)
    independency_relation.build()
    print(independency_relation)

    dependency_relation_word = DependencyWordRelation(word_int, dependency_relation, minimal=True)
    dependency_relation_word.build()

    foata_normal_form = FoataNF(word_int, dependency_relation, independency_relation)
    foata_normal_form.build()
    print(f"FNF: {foata_normal_form}")

    drawer = GraphDrawer(dependency_relation_word.results, word)
    drawer.draw(os.path.join(PATH_OUTPUT, f"{test_fn}.gv"))
