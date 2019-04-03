import sys
import numpy as np
n = int(sys.argv[1])
matrix = np.random.rand(n, n)
np.savetxt('matrix{}.txt'.format(n),matrix,delimiter=' ',newline='\n')
np.savetxt('matrix{}inverse.txt'.format(n),np.linalg.inv(matrix),delimiter=' ',newline='\n')
