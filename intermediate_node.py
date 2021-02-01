class IntermediateNode:

    #Calculates the off-chain code .
    #'window' is its local window w_i[t]
    def __init__(self, id):
        self.ID = id

    def offChainCode(self, window):
        print(window)

    def proofOfComputation(self, window):
        self.offChainCode(window)

