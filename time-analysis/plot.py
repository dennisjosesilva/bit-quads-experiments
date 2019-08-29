import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def extract_num_of_pixels(dimension):
	w, h = dimension.split("x")
	w, h = int(w), int(h)
	return w * h

def add_num_pixels_column(df):
	df["npixels"] = df["size"].map(lambda row: extract_num_of_pixels(row))
	return df.sort_values(by=["npixels"])

chaincode = add_num_pixels_column(pd.read_csv("./chaincode/summary.csv", sep=";"))
im = add_num_pixels_column(pd.read_csv("./im/summary.csv", sep=";"))
non_inc_bitquads = add_num_pixels_column(pd.read_csv("./non-inc-bitquads/summary.csv", sep=";"))
non_opt_quads = add_num_pixels_column(pd.read_csv("./non-opt-quads/summary.csv", sep=";"))
opt_quads = add_num_pixels_column(pd.read_csv("./opt-quads/summary.csv", sep=";"))


steps = np.arange(opt_quads.shape[0])
# plt.plot(steps, np.log(opt_quads["mean_time"]), label="opt-quads")
# plt.plot(steps, np.log(im["mean_time"]), label="IM")
# plt.plot(steps, np.log(non_inc_bitquads["mean_time"]), label="NIQC")
# plt.plot(steps, np.log(non_opt_quads["mean_time"]), label="non-opt-quads")
# plt.plot(steps, np.log(chaincode["mean_time"]), label="CCM")

plt.margins(0.05, 0.3)
plt.xticks(np.arange(opt_quads.shape[0]), opt_quads["size"], rotation=30)
plt.subplots_adjust(bottom=0.15)

#plt.plot(steps,  chaincode["mean_time"], label="chaincode")
#plt.plot(steps, im["mean_time"], label="im")
#plt.plot(steps, non_inc_bitquads["mean_time"], label="non_inc_bitquads")
#plt.plot(steps, non_opt_quads["mean_time"], label="non_opt_quads")
plt.plot(steps, opt_quads["mean_time"], label="opt_quads")

# plt.ylim(0, 15000)

plt.legend()
plt.show()