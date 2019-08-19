import argparse
from os import listdir
from os.path import isfile, join

import matplotlib.pyplot as plt

import pandas as pd
import numpy as np


def read_csv(filepath):
	df = pd.read_csv(filepath, sep=";")
	return df.sort_values(by=["nnodes"])

chaincode = read_csv("./chaincode/mean.csv")
im = read_csv("./im/mean.csv")
non_inc_bitquads = read_csv("./non-inc-bitquads/mean.csv")
non_opt_quads = read_csv("./non-opt-quads/mean.csv")
opt_quads = read_csv("./opt-quads/mean.csv")

# plt.plot(chaincode["nnodes"], chaincode["mean_time"], label="opt-quads")
# plt.plot(im["nnodes"], im["mean_time"], label="IM")
# plt.plot(non_inc_bitquads["nnodes"], non_inc_bitquads["mean_time"], label="NIQC")
# plt.plot(non_opt_quads["nnodes"], non_opt_quads["mean_time"], label="non-opt-quads")
# plt.plot(opt_quads["nnodes"], opt_quads["mean_time"], label="CCM")

plt.plot(chaincode["nnodes"], np.log(chaincode["mean_time"]), label="chaincode")
plt.plot(im["nnodes"], np.log(im["mean_time"]), label="IM")
plt.plot(non_inc_bitquads["nnodes"], np.log(non_inc_bitquads["mean_time"]), label="NIQC")
plt.plot(non_opt_quads["nnodes"], np.log(non_opt_quads["mean_time"]), label="non-opt-quads")
plt.plot(opt_quads["nnodes"], np.log(opt_quads["mean_time"]), label="opt-quads")


plt.margins(0.05, 0.3)

plt.legend()
plt.show()
