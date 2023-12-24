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
    return f"({';'.join(map(lambda letter: self.dependency_relation.alphabet[letter], letters))})"

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
    return self.dependency_relation.result[letter]

  @staticmethod
  def __are_stacks_empty(stacks: list[list[int | None]]) -> bool:
    for stack in stacks:
      if len(stack) > 0:
        return False
    return True
