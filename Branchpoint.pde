public class Branchpoint
{
  public Branchpoint[] next = new Branchpoint[0];
  public Dialogue[] dialogue = new Dialogue[0]; 
  
  public Branchpoint death;
  public String name;
  public int percentChance;
  String fileName= "";
  
  public Branchpoint(String n, Dialogue[] d)
  {
    dialogue = d;
    name = n;
  }
  public Branchpoint(String n, String d)
  {
    fileName = d;
    name = n;
  }
  public Branchpoint(String na, Dialogue[] d, Branchpoint[] n)
  {
    dialogue = d;
    next = n;
    name = na;
  }
  public Branchpoint(String na, Dialogue[] d, Branchpoint[] n, int p, Branchpoint de)
  {
    dialogue = d;
    next = n;
    percentChance = p;
    name = na;
    death = de;
  }
  
  public Branchpoint(String na, String d, Branchpoint[] n)
  {
    fileName = d;
    next = n;
    name = na;
  }
  public Branchpoint(String na, String d, Branchpoint[] n, int p, Branchpoint de)
  {
    fileName = d;
    next = n;
    percentChance = p;
    name = na;
    death = de;
  }
  
  public void Init()
  {
    
    if(fileName.equals(""))
      return;    
      
    String[] lines = loadStrings(fileName);
    dialogue = new Dialogue[lines.length];
    for(int i = 0; i < lines.length; i++)
    {
      String[] c = lines[i].split("/");
      
      dialogue[i] = new Dialogue(new Char(c[0]), c[1]);
    }
  }
}

public class Dialogue
{
  Char character; 
  String text; 
  
  Boolean rf = false;
  
  public Dialogue(Char c, String t)
  {
    character = c;
    text = t;
  }
  
  public Dialogue(Char c, String fileName, Boolean readFile)
  {
    rf = readFile;
    text = fileName;
    character = c;

  }
  
  public void Init()
  {
    if(!rf)
      return;
    
    String fileName = text;
    text = "";
    String[] lines = loadStrings(fileName);
    for(int i = 0; i < lines.length; i++)
    {
      text += lines[i];
    }
    
    rf = false;
  }
  
}

public class Char
{
   String name; 
   PImage icon; 
   
   public Char(String n)
   {
     name = n;
   }
   
   public Char(String n, PImage i)
   {
     name = n;
     icon = i;
   }
   
}
