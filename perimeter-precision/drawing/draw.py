from lib.drawing import *
from imageio import imsave

f = np.zeros((1200, 1200), np.uint8)

f1 = circle(60, graylevel=80)
f2 = circle(120, graylevel=80)
f3 = circle(200, graylevel=80)

f[400:800,50:450] += f3
f[480:720,130:370] += f2
f[540:660,190:310] += f1

e1 = ellipse(300, 200, graylevel=80)
e2 = ellipse(200, 150, graylevel=80)
e3 = ellipse(80, 120, graylevel=80)

f[300:900,700:1100] += e1
f[400:800,750:1050] += e2
f[520:680,780:1020] += e3


# mi.imsave("ellipse.png", f)
imsave("ellipse.png", f)
