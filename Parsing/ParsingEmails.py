# import python poplib module
import poplib
import email

# input email address, password and pop3 server domain or ip address
user = input('Email: ')
password = input('Password: ')
#pop3_server = input('POP3 server: ')

# connect to pop3 server:
host = 'outlook.office365.com'
port = 995
server = poplib.POP3_SSL(host,port)
server.set_debuglevel(2)
# user account authentication
#server.user("santos_bruno98@live.com.pt")
#server.pass_("asdfgh5678")
server.user(user)
server.pass_(password)

##Fedding an text file with emails
#f = open("Emails.txt","w+")



numMessages = len(server.list())
#response,listings,octec_count = server.list()

for i in range(numMessages):
    ##Só está a fazer uma vez
    
    response,headerLines, bytes = server.retr(i+1)
    print('Message ID',headerLines[1])
    print('Date',headerLines[2])
    print('Reply ID',headerLines[3])
    print('To',headerLines[4])
    print('Subject',headerLines[5])
    print('MIME',headerLines[6])
    print('Content Type',headerLines[7])
    print('Response',response)
    #TODO: Abrir e escrever o email no ficheiro só os que são do Help Ticket

    #while(open(f)):
     #   f.write(response)  
#f.close()

server.quit()


    #improssora NAV1

