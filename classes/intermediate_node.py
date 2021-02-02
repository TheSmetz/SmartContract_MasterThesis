class IntermediateNode:

    #Calculates the off-chain code.
    #'window' is its local window w_i[t]
    def __init__(self, id):
        self.ID = id

    #Generate Fresh Random Secret t,i
    def generateFreshRandomSecret(self):
        return 3
        
    #Calculate the off-chain computation
    def offChainCode(self, window):
        print(window)

    #Calculate the Proof of Computation
    def proofOfComputation(self, window):
        self.offChainCode(window)
