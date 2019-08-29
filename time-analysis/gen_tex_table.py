import argparse
from os import listdir
from os.path import isfile, join

import pandas as pd

def extract_num_of_pixels(dimension):
	w, h = dimension.split("x")
	w, h = int(w), int(h)
	return w * h

def add_num_pixels_column(df):
	df["npixels"] = df["size"].map(lambda row: extract_num_of_pixels(row))
	return df.sort_values(by=["npixels"])

parser = argparse.ArgumentParser(description="Latex table calculator")
parser.add_argument("--table_file", "-t", type=str, help="latex table file")

args = parser.parse_args()

ccm = add_num_pixels_column(pd.read_csv("./chaincode/mean.csv", sep=";"))
im = add_num_pixels_column(pd.read_csv("./im/mean.csv", sep=";"))
niqc = add_num_pixels_column(pd.read_csv("./non-inc-bitquads/mean.csv", sep=";"))
nom = add_num_pixels_column(pd.read_csv("./non-opt-quads/mean.csv", sep=";"))
pm = add_num_pixels_column(pd.read_csv("./opt-quads/mean.csv", sep=";"))


out = pd.DataFrame()

out["filename"] = pm["file"]
out["size"] = pm["size"]
out["nnodes"] = pm["nnodes"]
out["IM"] = "$" + im["mean_time"].astype(int).astype(str) + r"\pm" + im["std_time"].astype(int).astype(str) + "$"
out["CCM"] = "$" + ccm["mean_time"].astype(int).astype(str) + r"\pm" + ccm["std_time"].astype(int).astype(str) + "$"
out["NIQC"] = "$" + niqc["mean_time"].astype(int).astype(str) + r"\pm" + niqc["std_time"].astype(int).astype(str) + "$"
out["NoM"] = "$" + nom["mean_time"].astype(int).astype(str) + r"\pm" + nom["std_time"].astype(int).astype(str) + "$"
out["PM"] = "$" + pm["mean_time"].astype(int).astype(str) + r"\pm" + pm["std_time"].astype(int).astype(str) + "$"


with open("table.tex", "w") as f:
	f.write(out.to_latex(escape=False))

print("------------------ NNODES ----------------------------------------")
print(pm["nnodes"].mean())

print()
print("--------------------- AVERAGE -----------------------------------")
print()


print("IM := ", im["mean_time"].mean())
print("CCM := ",  ccm["mean_time"].mean())
print("NIQC := ",  niqc["mean_time"].mean())
print("NoM := ", nom["mean_time"].mean())
print("PM := ", pm["mean_time"].mean())

print()
print("--------------------- STD DEV -----------------------------------")
print()


print("IM := ", im["std_time"].mean())
print("CCM := ",  ccm["std_time"].mean())
print("NIQC := ",  niqc["std_time"].mean())
print("NoM := ", nom["std_time"].mean())
print("PM := ", pm["std_time"].mean())


print()
print("--------------------- SPEED UP -----------------------------------")
print()

print("IM := ", im["mean_time"].mean() /  pm["mean_time"].mean())
print("CCM := ", ccm["mean_time"].mean() /  pm["mean_time"].mean())
print("NIQC := ", niqc["mean_time"].mean() /  pm["mean_time"].mean())
print("NoM := ", nom["mean_time"].mean() /  pm["mean_time"].mean())