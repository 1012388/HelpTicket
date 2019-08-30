#sendEmails.py

#Objectivo enviar relatórios para o engenheiro

#import poplib

import smtplib
from email.message import EmailMessage

# input email address, password and pop3 server domain or ip address
user = input('Email: ')
password = input('Password: ')
#pop3_server = input('POP3 server: ')

# connect to pop3 server:
host = 'outlook.office365.com'
port = 995
s= smtplib.STMP_SSL(host,port)
s.set_debuglevel(2)
# user account authentication
#server.user("santos_bruno98@live.com.pt")
#server.pass_("asdfgh5678")
s.login(user,password)
from_addr = user
to_addrs = input('To who? : ')

with open(textfile) as fp:
	email = EmailMessage()
	email.set_content(fp.read())

email['Subject']  = "relatórios dos técnicos"
email['From'] = from_addr
email['To'] = "mario.milheiro@coficab.com"

#curl -i 'https://helpticket-e00ce.firebaseio.com/tickets.json?orderBy="idTechnician"&equalTo="currentUserId"

#select orderBy
#'https://dinosaur-facts.firebaseio.com/dinosaurs.json?orderBy="height"&startAt=3&print=pretty'

#orderByChild("idTechnician").equalTo(currentUserId).orderByChild("requested_date").equalTo(currentDate.getTime().toString()).limitToFirst(1);
msg=email

s.sendmail(from_addr,to_addrs,msg)

s.quit()