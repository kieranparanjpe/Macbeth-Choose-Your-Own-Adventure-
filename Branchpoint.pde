public class Branchpoint
{
  public Branchpoint[] next = new Branchpoint[0];
  public Dialogue[] dialogue = new Dialogue[0]; 
  
  public Branchpoint death;
  public String name;
  public int percentChance;
  
  public Branchpoint(String n, Dialogue[] d)
  {
    dialogue = d;
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
