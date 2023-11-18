import os
from parser import InputParser


class FileReader:
  @classmethod
  def read(cls, path: str) -> tuple[list[str], list[str], list[str]]:
    if not os.path.exists(path) or not os.path.isfile(path):
      raise ValueError("File not found.")

    with open(path) as file:
      alphabet = cls.__clean_line(file.readline()).split(',')
      word     = cls.__clean_line(file.readline()).split(',')

      expressions: list[str] = []
      for line in file:
        expressions.append(InputParser.parse_line(line))

      return alphabet, word, expressions

  @classmethod
  def __clean_line(cls, line: str) -> str:
    return line.strip().replace(' ', '')
