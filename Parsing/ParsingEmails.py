# import python poplib module
import poplib
import email

# input email address, password and pop3 server domain or ip address
#user = input('Email: ')
#password = input('Password: ')
#pop3_server = input('POP3 server: ')

# connect to pop3 server:
server = poplib.POP3("outlook.office365.com")

# user account authentication
server.user("santos_bruno98@live.com.pt")
server.pass_("asdfgh5678")

##Fedding an text file with emails
f = open("Emails.txt","w+")

numMessages = len(server.list()[1])

for i in range(numMessages):
    (server_msg,body,octets) = server.retr(i+1)
    for j in body:
        try:
            msg = email.message_from_string(j.decode("utf-8"))
            msg.get_payload()
            print(msg.get_payload())

            while(open(f)):
                f.write(msg)
                
        except:
            pass
        
# Close file
f.close() 
# close pop3 server connection.
server.quit()



    
