import os
import matplotlib.pyplot as plt


if __name__ == "__main__":
  script_path = os.path.dirname(__file__)

  with os.scandir(os.path.join(script_path, "../log")) as dir_iterator:
    for entry in dir_iterator:
      if not entry.is_file():
        continue

      filename_without_extension = entry.name[:-4]
      splitted_filename = filename_without_extension.split("-")
      number_of_philosophers = int(splitted_filename[1])
      results_per_philosopher = [[] for _ in range(number_of_philosophers)]

      with open(entry.path, "r") as file_log:
        for line in file_log:
          splitted_line = line.split(",")
          philosopher_index = int(splitted_line[0])
          waiting_time_avg_mcs = int(splitted_line[1])
          results_per_philosopher[philosopher_index].append(waiting_time_avg_mcs)
      plt.boxplot(results_per_philosopher)
      plt.show()
