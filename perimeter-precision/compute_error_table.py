import pandas as pd
import numpy as np

def compute_error(Ve, Va):
	return 100.0 * (np.abs(Va - Ve) / Ve) #Ve - Va #

def compute_circularity(A, P):
	return (4.0*np.pi * A) / (P*P)

analytical = pd.read_csv("analytical-data.csv", sep=";").sort_values(["node-id"])
computed = pd.read_csv("output-perimeter-3.csv", sep=";").sort_values(["node-id"])

analytical = analytical.drop([0], axis=0)
computed = computed.drop([0], axis=0)

df = pd.DataFrame()

#print(analytical)
#print(computed)


df["node-id"] = computed["node-id"]
df["PM-perimeter"] = compute_error(analytical["perimeter"].values, computed["PM-perimeter"].values)
df["IM-perimeter"] = compute_error(analytical["perimeter"].values, computed["IM-perimeter"].values)
df["CCM-perimeter"] = compute_error(analytical["perimeter"].values, computed["CCM-perimeter"].values)

df["PM-area"] = compute_error(analytical["area"].values, computed["PM-area"].values)
df["IM-area"] = compute_error(analytical["area"].values, computed["IM-area"].values)

real_circularity = compute_circularity(analytical["area"].values, analytical["perimeter"].values)
pm_circularity = compute_circularity(computed["PM-area"], computed["PM-perimeter"])
im_circularity = compute_circularity(computed["IM-area"], computed["IM-perimeter"])

err_circ_pm = compute_error(real_circularity, pm_circularity).mean()
err_circ_im = compute_error(real_circularity, im_circularity).mean()


out = pd.DataFrame()

out["perimeter"] = np.array([df["PM-perimeter"].mean(), 
														 df["IM-perimeter"].mean(),
														 df["CCM-perimeter"].mean()])

out["area"] = np.array([df["PM-area"].mean(), df["IM-area"].mean(), None])
out["circularity"] = np.array([err_circ_pm, err_circ_im, None])

print(out)