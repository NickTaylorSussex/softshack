from twilio.rest import TwilioRestClient
import requests
import time

ACCOUNT_SID = "ACa4cea8d86191d92d1b3398557b4311dc"
AUTH_TOKEN = "18b39108b5fc289c226b91cc580d6453"
client = TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)

def get_data():
    response = requests.get('http://138.68.151.94/flask').content
    if response.decode("utf-8") == "OK":
        return response
    else:
        client.messages.create(
            to="+447760331056",
            from_="+441346277054",
            body="Server isn't responding to requests",
        )
        return "No Response"

while True:
    get_data()
    time.sleep(1800)
