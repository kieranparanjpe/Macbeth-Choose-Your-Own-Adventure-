
/*Characters*/
public Char narrator = new Char("Narrator");
public final Char macbeth = new Char("Macbeth");

/*Dialogue*/ 
public final Dialogue a1b1d1 = new Dialogue(narrator, "Sample Intro");
public final Dialogue a1b1d2 = new Dialogue(narrator, "This is next");

/*Branchpoints*/ 

public final Branchpoint first = new Branchpoint(new Dialogue[] {a1b1d1, a1b1d2});


public Branchpoint currentBranch;
public int currentDialogue = 0;
public String currentText = "";

public void setup()
{
  size(1280, 720);
  currentBranch = first; 
}

public void draw()
{
  background(0);
  
  textSize(20);
  textAlign(CENTER);
  fill(255);

 
 
 if(currentDialogue >= currentBranch.dialogue.length)
 {
   if(currentBranch.next.length == 0)
     currentBranch = null;
 }
 else{
   if(DisplayText(currentBranch.dialogue[currentDialogue].text))
   {
    currentDialogue++; 
    currentText = "";
    delay(1000);
   }
 }
}

public Boolean DisplayText(String text)
{
  if(text.equals(currentText))
    return true;
    
    println(text);
    println(currentText);
    
  int l = currentText.length();
  
  currentText += text.charAt(l);
  text(currentText, width/2, height/2);
  
  delay(30);

  return false;
}

public int DisplayButtons()
{
  return 0;
}
