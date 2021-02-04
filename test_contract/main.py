import time
from web3 import Web3, HTTPProvider
import contract_abi
import json
from multiprocessing import Process

contract_address    = '0x270A1c19C4AbD659DD8569CbD5619325fF4d28E8'
private_key_willy   = '187256e5e1929a10ba9dc330e14d921d4007933d61de80d10cca5532a727c7b4'
wallet_add_willy    = '0x75566262BdbdE5A65a0f1C12b0c7e0cBE36e46bB'
private_key_smetz   = "eee80ebcd6034c8c36f0d2f7d78397abd0d931246a012caf12371086833f2e73"
wallet_add_smetz    = '0xA72690d8D98D908c1fcA3bE2Ec228EC2b7ceD191'

w3 = Web3(HTTPProvider("https://ropsten.infura.io/v3/7965cd31b63a42d584ba04c83affda15"))
abi = contract_abi.getContractAbi()
contract = w3.eth.contract(address=contract_address, abi=abi)

def sendOpinion():
    opinion = input("Enter your opinion: ")
    nonce = w3.eth.getTransactionCount(wallet_add_willy)
    txn_dict = contract.functions.broadcastOpinion(opinion).buildTransaction({
        'chainId': 3,
        'gas': 140000,
        'gasPrice': w3.toWei('40', 'gwei'),
        'nonce': nonce,
    })
    signed_txn = w3.eth.account.signTransaction(txn_dict, private_key=private_key_willy)
    result = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
    while True:
        try:
            print(w3.eth.getTransactionReceipt(result))
            break
        except:
            time.sleep(3)

def getLastOpinion():
    latestData = contract.functions.getCurrentOpinion().call()
    return latestData

def watchBroadcastOpinion():
    event_filter = contract.events.OpinionBroadcast.createFilter(fromBlock='latest')
    while True:
        for event in event_filter.get_new_entries():
            print("Message sent from: ",event.args._soapboxer)
            print("Opinion: ",event.args._opinion)
    time.sleep(2)

sendOpinion()