import json 

def getContractAbi():
    # Opening JSON file 
    f = open('abi.json')
    abi = json.load(f)
    return abi