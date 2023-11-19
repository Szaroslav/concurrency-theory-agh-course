import os
from file_reader import FileReader
from relation import DependencyRelation, IndependencyRelation


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  DATA_PATH      = os.path.join(PATH, "../data")
  TEST_FILENAMES = [ "test1.txt", "test2.txt" ]

  for test_fn in TEST_FILENAMES:
    print(test_fn)
    alphabet, word, expressions = FileReader.read(f"{DATA_PATH}/{test_fn}")
    print(alphabet, word, expressions)
    dependency_relation = DependencyRelation(expressions, alphabet)
    dependency_relation.build()
    print(dependency_relation)
    independency_relation = IndependencyRelation(expressions, alphabet)
    independency_relation.build()
    print(independency_relation)
