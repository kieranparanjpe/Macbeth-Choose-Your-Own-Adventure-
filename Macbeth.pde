
/*Characters*/
public Char narrator = new Char("Narrator");
public final Char macbeth = new Char("Macbeth");

/*Dialogue*/
public final Dialogue a1b1d1 = new Dialogue(narrator, "Sample Introcfcccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
public final Dialogue a1b1d2 = new Dialogue(narrator, "This is next");

/*Branchpoints*/

public final Branchpoint third = new Branchpoint("third", new Dialogue[] {a1b1d1, a1b1d2});
public final Branchpoint second = new Branchpoint("second", new Dialogue[] {a1b1d1, a1b1d2});
public final Branchpoint first = new Branchpoint("Start", new Dialogue[] {a1b1d1, a1b1d2}, new Branchpoint[] {second, third});


public Branchpoint currentBranch;
public int currentDialogue = 0;
public String currentText = "";

private int millis;

private ArrayList<Button> buttons = new ArrayList<Button>();

public void setup()
{
  size(1280, 720);
  rectMode(CENTER);
  textAlign(LEFT);
  currentBranch = first;
  
}

public void draw()
{
  background(0);

  textSize(20);
  fill(255);
  
  for(int i = 0; i < buttons.size(); i++)
  {
     buttons.get(i).Draw();
     if(buttons.get(i).clicked)
     {
       currentBranch = currentBranch.next[i];
       buttons = new ArrayList<Button>();
       currentDialogue = 0;
       currentText = "";
     }
  }


  if (currentDialogue >= currentBranch.dialogue.length)
  {
    //if (currentBranch.next.length == 0)
      //currentBranch = null;
  }
  else
  {
    if (DisplayText(currentBranch.dialogue[currentDialogue].text))
    {
      if (millis() - millis > 1000)
      {
                  currentDialogue++; 

        if(currentDialogue >= currentBranch.dialogue.length)
        {
          int[] pos = new int[currentBranch.next.length];
          if(pos.length == 2){
            pos[0] = width/2 - 300;
            pos[1] = width/2 + 300;
          }
          else if(pos.length == 3)
          {
            pos[0] = width/2 - 450;
            pos[1] = width/2;
            pos[2] = width/2 - 450;
          }
          for(int i = 0; i < currentBranch.next.length; i++)
          {
             buttons.add(new Button(new PVector(pos[i], height * 0.75), new PVector(300, 200), 255, 200, currentBranch.next[i].name));
          }
        }
        else
        {
          currentText = "";
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

   text(currentBranch.dialogue[currentDialogue].character.name + ":", width/2, height/2 , 800, 500);

  textSize(20);

  
  if (text.equals(currentText))
  {
    text(currentText, width/2, height/2 + 75, 800, 500);
    return true;
  }


  if (millis() - millis < 30)
  {
    text(currentText, width/2, height/2 + 75, 800, 500);
    return false;
  }

  println(text);
  println(currentText);
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
