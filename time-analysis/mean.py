import argparse
from os import listdir
from os.path import isfile, join

import pandas as pd

parser = argparse.ArgumentParser(description="Mean Calculator")
parser.add_argument("--in_dir", "-i", type=str, help="input directory")
parser.add_argument("--out_file", "-o", type=str, help="output file")
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

final_df.to_csv(args.out_file, sep=";")
print(f"##  SAVED {args.out_file} ##")