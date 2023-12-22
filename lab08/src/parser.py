import re


class InputParser:
  @staticmethod
  def parse_line(input_line: str) -> tuple[str, list[str]]:
    input_line               = re.sub(r"[ 0-9\(\)]", '', input_line).strip()
    assigned_var, input_line = input_line.split(":=")
    operating_vars           = re.split(r"[\+\-\*\/\^]", input_line)

    return assigned_var, operating_vars
