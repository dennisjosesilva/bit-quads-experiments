import argparse
from os import listdir
from os.path import isfile, join

import pandas as pd

parser = argparse.ArgumentParser(description="Mean Calculator")
parser.add_argument("--in_dir", "-i", type=str, help="input directory")
parser.add_argument("--mean_file", "-m", type=str, help="output file")
parser.add_argument("--summary_file", "-s", type=str, help="summary file")
args = parser.parse_args()

csv_files = [f for f in listdir(args.in_dir) if isfile(join(args.in_dir, f))]

dfs = []
for csv in csv_files:
	filepath = join(args.in_dir, csv)
	df = pd.read_csv(filepath, sep=";")
	dfs.append(df)

fields = ["file", "size", "nnodes"]
times = [df["time"] for df in dfs]
df_times = pd.concat(times, axis=1)

final_df = pd.concat([dfs[0][fields],df_times["time"].mean(axis=1), df_times["time"].std(axis=1)], axis=1)
final_df.columns = ["file", "size", "nnodes", "mean_time", "std_time"]

summary_df = final_df.groupby("size")["mean_time"].mean()
summary_df.columns = ["size", "mean_time"]

final_df.to_csv(args.mean_file, sep=";", header=True)
print(f"##  SAVED {args.mean_file} ##")

summary_df.to_csv(args.summary_file, sep=";", header=True)
print(f"## SAVED {args.summary_file} ##")