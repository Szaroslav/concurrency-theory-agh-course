import os
import re
import numpy as np
from normal_form import FoataNF


class File:
  @classmethod
  def read(cls, path: str) -> tuple[list[str], list[str], tuple[str, list[str]]]:
    if not os.path.exists(path) or not os.path.isfile(path):
      raise ValueError("File not found.")

    with open(path) as file:
      N = int(cls.__clean_line(file.readline()))

      matrix: np.ndarray = np.empty((N, N + 1), dtype=np.float64)
      for j, line in enumerate(file):
        for i, value in enumerate(map(lambda v: float(v), line.split(' '))):
          matrix[i, j] = value

      return N, matrix

  @classmethod
  def __clean_line(cls, line: str) -> str:
    return line.strip().replace(' ', '')

  @classmethod
  def write(cls, path: str, foata_normal_form: FoataNF) -> None:
    with open(path, "w") as file:
      for line in re.split(r"(\([ABC0-9,;]+\))", str(foata_normal_form)):
        if line != "":
          file.write(line[1:-1] + "\n")
