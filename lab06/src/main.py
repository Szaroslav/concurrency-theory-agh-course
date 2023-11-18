import os
from file_reader import FileReader


if __name__ == "__main__":
  PATH           = os.path.dirname(__file__)
  DATA_PATH      = os.path.join(PATH, "../data")
  TEST_FILENAMES = [ "test1.txt", "test2.txt" ]

  for test_fn in TEST_FILENAMES:
    print(test_fn)
    print(FileReader.read(f"{DATA_PATH}/{test_fn}"))
