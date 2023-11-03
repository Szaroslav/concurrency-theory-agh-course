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
      task_name = splitted_filename[0]
      number_of_philosophers = int(splitted_filename[1])
      results_per_philosopher = [[] for _ in range(number_of_philosophers)]

      with open(entry.path, "r") as file_log:
        for line in file_log:
          splitted_line = line.split(",")
          philosopher_index = int(splitted_line[0])
          waiting_time_avg_mcs = int(splitted_line[1])
          results_per_philosopher[philosopher_index].append(waiting_time_avg_mcs)

      if number_of_philosophers > 64:
        continue

      fig, ax = plt.subplots(figsize=(16, 5))
      ax.set_title(f"{task_name.upper()}, liczba filozofów: {number_of_philosophers}")
      ax.set_ylabel("Średni czas oczekiwania [μs]")
      ax.set_xlabel("Filozof")
      ax.yaxis.grid(True)
      box_plot = ax.boxplot(results_per_philosopher, patch_artist=True)
      for box in box_plot["boxes"]:
        box.set_facecolor("grey")
      for median in box_plot["medians"]:
        median.set_color("pink")
      fig.savefig(os.path.join(script_path, "../results", f"{filename_without_extension}.png"))
      plt.close()
