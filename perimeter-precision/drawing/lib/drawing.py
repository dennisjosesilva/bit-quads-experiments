import numpy as np
import scipy.misc as mi

def circle(radius, graylevel=255):
    size = (radius * 2)
    f = np.zeros((size, size), np.bool)
    r, c = np.indices((size, size))
    r -= size//2
    c -= size//2
    f = (((r**2) + (c**2)) <= radius**2)
    return (f * graylevel).astype(np.uint8)

def ellipse(w,h, graylevel=255):
    w2 = 2*w
    h2 = 2*h
    f = np.zeros((w2, h2), np.bool)
    x, y = np.indices((w2, h2))

    x -= w
    y -= h
    
    ww = w*w
    hh = h*h
    xx= x*x
    yy = y*y

    f = (((xx*hh) + (yy*ww)) <= ww*hh)
    return (f * graylevel).astype(np.uint8)
    
