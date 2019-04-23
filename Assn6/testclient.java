import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

class testclient
{

public static void main(String args[])throws IOException
{
InetAddress addr=InetAddress.getByName("Localhost");
System.out.println(addr);

Socket connection=new Socket(addr,5000);

BufferedInputStream in=new BufferedInputStream(connection.getInputStream());
DataOutputStream out=new DataOutputStream(connection.getOutputStream());
Scanner scr=new Scanner(System.in);// this will be used to accept i/p from console

System.out.println(" client is Connected to server" + addr);
System.out.println("Enter the number of frames to be requested to the server");
int c=scr.nextInt();

out.write(c); // write no of frames on client socket
out.flush();

System.out.println("Enter the type of trans. Error=1 ; No Error=0");
int choice=scr.nextInt();
out.write(choice);  //write choice on socket

int check=0;
int i=0;
int j=0;

if(choice==0)
{
for(j=0;j<c;++j)
{
i=in.read();  //read all frames one by one from server
System.out.println("received frame no: "+i);
System.out.println("Sending acknowledgement for frame no: "+i);
out.write(i); //write ack  to socket
out.flush();
}
out.flush();
}
else
{
for(j=0;j<c;++j)
{
i=in.read();  //read 0,1,2,3 frame
if(i==check)
{
System.out.println("received frame no: "+i);
System.out.println("Sending acknowledgement for frame no: "+i);
out.write(i); //sent ack of frame 0,1
++check;
}
else
{
--j;
System.out.println("Discarded frame no: "+i);
System.out.println("Sending NEGATIVE ack");
out.write(-1);
}
out.flush();
}
}//end of else for error

in.close();
out.close();
System.out.println("Quiting");

}// end of main method
}// end of main class
