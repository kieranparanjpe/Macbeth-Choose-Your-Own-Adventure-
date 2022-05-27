public class Branchpoint
{
  public Branchpoint[] next = new Branchpoint[0];
  public Dialogue[] dialogue = new Dialogue[0]; 
  
  public String minigame = "";
  
  public Branchpoint(Dialogue[] d)
  {
    dialogue = d;
  }
  public Branchpoint(Dialogue[] d, Branchpoint[] n)
  {
    dialogue = d;
    next = n;
  }
  public Branchpoint(Dialogue[] d, Branchpoint[] n, String m)
  {
    dialogue = d;
    next = n;
    minigame = m;
  }
}

public class Dialogue
{
  Char character; 
  String text; 
  
  public Dialogue(Char c, String t)
  {
    character = c;
    text = t;
  }
  
  public Dialogue(Char c, String fileName, Boolean readFile)
  {
    character = c;
    String[] lines = loadStrings(fileName);
    for(int i = 0; i < lines.length; i++)
    {
      text += lines[i];
    }
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
