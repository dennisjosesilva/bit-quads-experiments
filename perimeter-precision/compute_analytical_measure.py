import pandas as pd
import numpy as np


def compute_area(row):
	if row["type"] == "rectangle":
		return row["width"]*row["height"]
	elif row["type"] == "circle":
		return np.pi*row["radius"]*row["radius"]
	elif row["type"] == "ellipse":
		return np.pi * row["width"] * row["height"]

def compute_perimeter(row):
	if row["type"] == "rectangle":
		return 2.0*(row["width"] + row["height"])
	elif row["type"] == "circle":
		return 2.0*np.pi*row["radius"]
	elif row["type"] == "ellipse":
		b, a = sorted([row["width"], row["height"]])
		h = ((a - b)**2.0)/((a + b)**2)
		return np.pi * (a + b) * (1.0 + ((3.0*h)/(10 + np.sqrt(4.0 - 3.0*h))))



df = pd.read_csv("./draw-data.csv", sep=";")
out = pd.DataFrame()
out["node-id"] = df["node-id"]
out["type"] = df["type"]
out["area"] = df.apply(compute_area, axis=1)
out["perimeter"] = df.apply(compute_perimeter, axis=1)

out.to_csv("analytical-data.csv", sep=";")

