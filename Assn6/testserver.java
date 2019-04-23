import java.io.*;
import java.net.*;
import java.util.*;
class testserver
{
public static void main(String args[])throws IOException
{
System.out.println("server Waiting for connection....");
InetAddress addr=InetAddress.getByName("Localhost");
ServerSocket ss=new ServerSocket(5000);

Socket client=new Socket();
client=ss.accept();

BufferedInputStream in=new BufferedInputStream(client.getInputStream());
DataOutputStream out=new DataOutputStream(client.getOutputStream());

System.out.println("Received request for sending frames");
int p=in.read();  //read no of frames sent by client

boolean f[]=new boolean[p];

int pc=in.read();  //read choice sent by client
System.out.println("Sending....");

if(pc==0)
{
for(int i=0;i<p;++i)
{
System.out.println("sending frame number "+i);
out.write(i); //send frame on server socket
out.flush();
System.out.println("Waiting for acknowledgement");
try
{
Thread.sleep(7000);
}
catch(Exception e){}

int a=in.read(); //read ack on servers socket from client
System.out.println("received acknowledgement for frame "+i+" as "+a);
}
out.flush();
}
else
{
for(int i=0;i<p;++i)
{
if(i==2)
{
System.out.println("sending frame no "+i);  //sent frame 2
}
else
{
System.out.println("sending frame no "+i);
out.write(i);  //write 0 and 1 and 3 frame
out.flush();
System.out.println("Waiting for acknowledgement ");
try
{
Thread.sleep(7000);
}
catch(Exception e){}

int a=in.read(); //Read NACK

if(a!=255)
{
System.out.println("received ack for frame no: "+i+" as "+a);
f[i]=true;
}
}// end of inner else
}// end of for

// check which frames have not been ack

for(int a=0;a<p;++a)
{
if(f[a]==false)
{
System.out.println("Resending frame "+a);
out.write(a);
out.flush();
System.out.println("Waiting for ack ");
try
{
Thread.sleep(5000);
}
catch(Exception e){}

int b=in.read();
System.out.println("received ack for frame no: "+a+" as "+b);
f[a]=true;
}
}
out.flush();
}// end of else which is for error 

in.close();
out.close();
client.close();
ss.close();
System.out.println("Quiting");

}// end main method
}// end main class
