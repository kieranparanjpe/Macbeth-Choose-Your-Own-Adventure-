public class Button
{
  public PVector pos;
  public PVector size;
  
  public int bgColour;
  public int stroke = #FF0000;
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
