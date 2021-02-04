import time
from web3 import Web3, HTTPProvider
import contract_abi

contract_address     = '0x270A1c19C4AbD659DD8569CbD5619325fF4d28E8'
wallet_private_key   = '187256e5e1929a10ba9dc330e14d921d4007933d61de80d10cca5532a727c7b4'
wallet_address       = '0x75566262BdbdE5A65a0f1C12b0c7e0cBE36e46bB'
smetz_address        = '0xA72690d8D98D908c1fcA3bE2Ec228EC2b7ceD191'

w3 = Web3(HTTPProvider("https://ropsten.infura.io/v3/7965cd31b63a42d584ba04c83affda15"))

abi = contract_abi.getContractAbi()

contract = w3.eth.contract(address=contract_address, abi=abi)

latestData = contract.functions.getCurrentOpinion().call()

print(latestData)