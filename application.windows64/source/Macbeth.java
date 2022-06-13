import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Macbeth extends PApplet {


/*Characters*/
public Char narrator = new Char("Narrator");
public final Char macbeth = new Char("Macbeth");
public final Char witches = new Char("Witches");
public final Char duncan = new Char("Duncan");
public final Char ross = new Char("Ross");

/*Dialogue*/
public final Dialogue b1d1 = new Dialogue(narrator, "b1d1.txt", true);

/*Branchpoints*/

//public final Branchpoint b10 = new Branchpoint("Kill Witches", new Dialogue[] {a1b1d2});
public final Branchpoint b26 = new Branchpoint("Uncle", "b22.txt");
public final Branchpoint b25 = new Branchpoint("Brother", "b22.txt");
public final Branchpoint b24 = new Branchpoint("Cousin", "b23.txt");
public final Branchpoint b23 = new Branchpoint("Macbeth should fear Macduff", "b22.txt");
public final Branchpoint b22 = new Branchpoint("No one born of a woman will harm him", "b23.txt");
public final Branchpoint b20 = new Branchpoint("Kill Macduff's Family", "b20.txt", new Branchpoint[] {b24, b25, b26});
public final Branchpoint b19 = new Branchpoint("Don't Kill Macduff's Family", "b19.txt", new Branchpoint[] {b22, b23});
public final Branchpoint b32 = new Branchpoint("Fail Banquo", "b32.txt");
public final Branchpoint b12 = new Branchpoint("Kill Banquo & Fleance", "b12.txt", new Branchpoint[] {b22, b23}, 30, b32);
public final Branchpoint b11 = new Branchpoint("Don't Kill Banquo", "b11.txt");
public final Branchpoint b10 = new Branchpoint("Kill Banquo", "b10.txt", new Branchpoint[] {b19, b20}, 80, b32);
public final Branchpoint b9 = new Branchpoint("Fail to murder", "b9.txt");
public final Branchpoint b7 = new Branchpoint("Support The Murder", "b7.txt", new Branchpoint[] {b10, b11, b12});
public final Branchpoint b6 = new Branchpoint("Don't Support The Murder", "b6.txt");
public final Branchpoint b5 = new Branchpoint("Kill Duncan", "b5.txt", new Branchpoint[] {b10, b11, b12}, 75, b9);
public final Branchpoint b4 = new Branchpoint("Don't Kill Duncan", "b4.txt");
public final Branchpoint b3 = new Branchpoint("Believe Witches", "b3.txt", new Branchpoint[] {b4, b5});
public final Branchpoint b2 = new Branchpoint("Don't Believe Witches", "b2.txt", new Branchpoint[] {b6, b7});
public final Branchpoint b1 = new Branchpoint("Kill Witches", "b1.txt");
public final Branchpoint b0 = new Branchpoint("Start", "b0.txt", new Branchpoint[] {b1, b2, b3});


public Branchpoint currentBranch;
public int currentDialogue = 0;
public String currentText = "";

private int millis;

private ArrayList<Button> buttons = new ArrayList<Button>();

public int mode = 0;

Button start = new Button(new PVector(1280/2, 600), new PVector(400, 200), 255, 180, "Play");

public void setup()
{
  
  rectMode(CENTER);
  textAlign(LEFT);
  currentBranch = b0;
  InitD(b0);
}

private void InitD(Branchpoint current)
{

  current.Init();
  if (current.death != null)
    InitD(current.death);

  for (Branchpoint b : current.next)
  {
    InitD(b);
  }
}

public void draw()
{
  background(0);
  
  if(mode == 0)
  {
    textAlign(CENTER);
    
    textSize(50);
    fill(255);
    
    text("The Tragedy of: ", width/2, 200);
    
    textSize(110);
    fill(255);
    
    text("MACBETH", width/2, 400);
    
    start.Draw();
    
    if(start.clicked)
      mode = 1;
  }
  

  if (mode == 1)
  {

    textSize(20);
    fill(255);

    for (int i = 0; i < buttons.size(); i++)
    {
      buttons.get(i).Draw();
      if (buttons.get(i).clicked)
      {
        int pc = currentBranch.next[i].percentChance;
        if (pc != 0)
        {
          float r = random(0, 100);
          if (r <= pc)
          {
            currentBranch = currentBranch.next[i];
          } else
          {
            currentBranch = currentBranch.next[i].death;
            println("sofhs");
          }
        } else
        {
          currentBranch = currentBranch.next[i];
        }

        buttons = new ArrayList<Button>();
        currentDialogue = 0;
        currentText = "";
      }
    }


    if (currentDialogue >= currentBranch.dialogue.length)
    {
      textAlign(LEFT);

      fill(255);
      textSize(40);

      text(currentBranch.dialogue[currentDialogue - 1].character.name + ":", width/2, height/2, 800, 500);

      textSize(20);

      text(currentBranch.dialogue[currentDialogue - 1].text, width/2, height/2 + 75, 800, 500);
    } else
    {
      println(currentDialogue + " " + currentBranch.dialogue.length);
      if (DisplayText(currentBranch.dialogue[currentDialogue].text))
      {
        if (millis() - millis > 2500)
        {
          currentDialogue++; 

          if (currentDialogue >= currentBranch.dialogue.length)
          {
            int[] pos = new int[currentBranch.next.length];
            if (pos.length == 2) {
              pos[0] = width/2 - 300;
              pos[1] = width/2 + 300;
            } else if (pos.length == 3)
            {
              pos[0] = width/2 - 450;
              pos[1] = width/2;
              pos[2] = width/2 + 450;
            }
            for (int i = 0; i < currentBranch.next.length; i++)
            {
              buttons.add(new Button(new PVector(pos[i], height * 0.75f), new PVector(300, 200), 255, 200, currentBranch.next[i].name + (currentBranch.next[i].percentChance == 0 ? "" : " (" + currentBranch.next[i].percentChance + "%)")));
            }
            if(currentBranch.next.length == 0)
            {
              mode = 0;
              currentBranch = b0;
              currentText = "";
              currentDialogue = 0;
              millis = 0;
              start.clicked = false;
            }
          } else
          {
            currentText = "";
          }
        }
      }
    }
  }
}

public Boolean DisplayText(String text)
{
  textAlign(LEFT);

  fill(255);
  textSize(40);

  text(currentBranch.dialogue[currentDialogue].character.name + ":", width/2, height/2, 800, 500);

  textSize(20);


  if (text.equals(currentText))
  {
    text(currentText, width/2, height/2 + 75, 800, 500);
    return true;
  }


  if (millis() - millis < 3)
  {
    text(currentText, width/2, height/2 + 75, 800, 500);
    return false;
  }

  millis = millis();
  int l = currentText.length();

  currentText += text.charAt(l);
  text(currentText, width/2, height/2 + 75, 800, 500);

  return false;
}

public int DisplayButtons()
{
  return 0;
}
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
    ArrayList<Dialogue> d = new ArrayList<Dialogue>();
    for(int i = 0; i < lines.length; i++)
    {
      if(lines[i].equals(""))
        continue;
      if(lines[i].contains("/"))
      {
        String[] c = lines[i].split("/");

d.add(
        dialogue[i] = new Dialogue(new Char(c[0]), c[1]));
      }
      else
      {
        String[] c = lines[i].split(":", 2);
        
        String n = c[0];
        String f = n.substring(1, 2).toUpperCase();
        
        n = n.substring(2, n.length() - 1);
        n = f + n;
d.add(
        dialogue[i] = new Dialogue(new Char(n), c[1].substring(1, c[1].length())));
      }

    }
    
    dialogue = new Dialogue[d.size()];

    for(int i = 0; i < d.size(); i++)
    {
      dialogue[i] = d.get(i);
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
public class Button
{
  public PVector pos;
  public PVector size;
  
  public int bgColour;
  public int stroke = 0xffFF0000;
  public int hover;
  public String text;
  private int c;
  
  boolean mp;
  public boolean clicked;
  
  public Button(PVector p, PVector s, int c, int h, String t)
  {
    pos = p;
    size = s;
    bgColour = c;
    hover = h;
    text = t;
  }
  
  public void Draw()
  {
      textAlign(CENTER, CENTER);
      rectMode(CENTER);

    fill(c);
    stroke(stroke);
    strokeWeight(6);
    rect(pos.x, pos.y, size.x, size.y);
    fill(0);
    textSize(20);
    text(text, pos.x, pos.y, size.x, size.y);
    
    
    
    if(mouseX < pos.x + size.x / 2 && mouseX > pos.x - size.x / 2 && mouseY < pos.y + size.y / 2 && mouseY > pos.y - size.y / 2)
    {
      c = hover; 
      if(mp)
      {
       c = hover - 20; 
      }
      if(!mousePressed && mp)
      {
        clicked = true;
      }
      
      
    }
    else
    {
     c = bgColour; 
    }
    
    mp = mousePressed;
  }
  
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Macbeth" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
