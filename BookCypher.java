package Assign2;

import BasicIO.*;  // for IO classes
import static java.lang.Math.*;  // for math constants and functions

/** This class reads the data from the file and encrypts it into a matrix of integers and after that from the encrypted part it decrypts the data saves it ...
  *
  * @author <Meet Patel>
  * @version 1.0 (<18/02/21>)                                                        
  */

public class BookCypher {
  private ASCIIOutputFile matrix; // it will be used to display the random matrix of the codeobook generated  
  private ASCIIDataFile input;// it will be used to open the message.txt
  private ASCIIOutputFile out; // it is used to store the encrypted data
  private ASCIIDataFile open; // its is used to open the encrypted data 
  private ASCIIOutputFile out1;// it is used to print the decrypyted text 
  
  public BookCypher(){
    Node [] codebook = new Node[128]; // creates an node of size 128
    codebook(codebook);    
    display(codebook);
    encrypt(codebook);
    decrypt(codebook);
  }
  
  // this method creates a codebook  
  public void codebook(Node[] codebook){
    
    int k;
    // this for loop creates an array of nodes and initializes the first nodes data to 0 and next position to null
    for(int i=0;i<codebook.length;i++)
    {
      codebook[i]=new Node(0,null);
    }
    
    //this loop creates the random numbers from 0-127 and then find the valid indice in the codebook and stores it into the with the corresponding number from 0-2000
    for(int i=1;i<2001;i++)
    {
      k=(int)(codebook.length*random());
      addatend(i,codebook[k]);
    }
  }
  
  //this method is used to display the codebook
  public void display(Node [] codebook)
  {
    matrix = new ASCIIOutputFile();
    for(int i=0;i<codebook.length;i++)
    { 
      Node p=codebook[i].next; 
      //this loop is used to traverse till the end of the list and prints the data of each position through which it traverse
      while(p!=null)
      {
        matrix.writeInt(p.data);
        p=p.next;
      }  
      matrix.newLine();
    }
    matrix.close();
  }
  
  /// this method adds the element at the end of the list 
  public void addatend(int acode,Node list)
  { 
    list.data=list.data+1;
    Node head=list;
    //this loop is used to traverse till the end of the list and once it reaches the list to insert new node
    while(head.next!=null)
    {
      head=head.next;
    }
    head.next=new Node(acode,null);
    head=head.next;
  }
  
  //this method reads the text from the file and encrypts in the new output file
  public void encrypt(Node [] codebook)
  {
    input =new ASCIIDataFile();
    out = new ASCIIOutputFile();
    int index,d,g;
    char readC;
    Node p;
    for( ; ; )
    {
      readC=input.readC();
      if(input.isEOF())
      { 
        break; 
      }
      index=(int)(readC);   
      p=codebook[index];
      d=codebook[index].data;//finds the data in the header node
      g=(int)(d*random()+1);// it is used to generate random index 
      int k=0; //it is used to go to the element that is randomly selected
      /*this loop runs until k does not equals the random number generated if it find the index then it brreaks and write its data in output file i.e.encrypted.txt    */
      while(k!=g)
      {
        p=p.next;
        k++;
      }
      out.writeInt(p.data); 
    }
    
    input.close();
    out.close();    
  }
  
  //this method converts the encrypted file into the decrypted file 
  public void decrypt(Node[] codebook)
  {
    open =new ASCIIDataFile();
    out1 = new ASCIIOutputFile();
    Node n;
    int a;
    char ch;
    for(;;)
    {
      a=open.readInt();
      if(open.isEOF())
      { 
        break; 
      } 
      for(int i=0;i<codebook.length;i++)
      {
        n=codebook[i].next;
        /*this loop moves to the last of the list and if its finds the data which is equal to the number in the encrypted.txt 
        *then it converts the number in charater and write the character in the output file i.e. decrypted.txt   */
        while(n!=null)
        { 
          if(n.data==a)
          {
            ch=((char)(i));
            out1.writeC(ch);               
          }
          n=n.next;  
        }
      } 
    }
    open.close();
    out1.close();
  }
  public static void main(String args[]){ BookCypher b1= new BookCypher();};
}