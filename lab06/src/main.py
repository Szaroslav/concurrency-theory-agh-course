import os
from file_reader import FileReader
from relation import DependencyRelation, IndependencyRelation, DependencyWordRelation
from normal_form import FoataNF


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  DATA_PATH      = os.path.join(PATH, "../data")
  TEST_FILENAMES = [ "test1.txt", "test2.txt" ]

  for test_fn in TEST_FILENAMES:
    print(test_fn)
    alphabet, word, expressions = FileReader.read(f"{DATA_PATH}/{test_fn}")
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
