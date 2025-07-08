package Oyun;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class Card extends JLabel implements MouseListener
{
	TurnedCardManager turnedManager;
	Icon onYuz;
	Icon arkaYuz;
	boolean faceUp = false; 			// Baþlangýçta kart kapalý
	int num; 							
	int iconWidthHalf, iconHeightHalf; 	
	boolean mousePressedOnMe = false;
	
	
	
	public Card(TurnedCardManager turnedManager, Icon ön, Icon arka, int num)
	{
		// Baþlangýçta kartýn arkasýný göster
		super(arka);
		
		this.turnedManager = turnedManager;
		this.onYuz = ön;
		this.arkaYuz = arka;
		this.num = num;
		// Mouse Tiklerini almak için:
		this.addMouseListener(this);
		
		this.iconWidthHalf = arka.getIconWidth() / 2;
		this.iconHeightHalf = arka.getIconHeight() / 2;
	}
		
	
	public void turnUp()
	{
		
		 
		// Kart Zaten açýksa 
		if(this.faceUp) return;
		// kartýn dönmesi için izin
		this.faceUp = this.turnedManager.turnUp(this);
		// Kart Döndürüldü resmi deðiþtir
		if(this.faceUp) this.setIcon(this.onYuz);
	}
	
	
	public void turnDown()
	{
		
		// Kart zaten kapalý
		if(!this.faceUp) return;
		// Resmi deðiþtir
		this.setIcon(this.arkaYuz);
		//Kart þimdi kapalý
		this.faceUp = false;
	}
	
	
	public int getNum() { return num; }
	
	
	private boolean overIcon(int x, int y)
	{
		// Hesaplama
		int distX = Math.abs(x - (this.getWidth() / 2));
		int distY = Math.abs(y - (this.getHeight() / 2));
		// Dýþ simge bölgesi
		if(distX > this.iconWidthHalf || distY > this.iconHeightHalf )
			return false;
		// Ýç simge bölgesi
		return true;
	}
	
	
	public void mouseClicked(MouseEvent e)
	{
		// Mouse Simgenin üstünde kartý aç
		if(overIcon(e.getX(), e.getY())) this.turnUp();
	}
	
	
	public void mousePressed(MouseEvent e)
	{
		// Mouse tikini al
		if(overIcon(e.getX(), e.getY())) this.mousePressedOnMe = true;
	}
	
	
	public void mouseReleased(MouseEvent e)
	{
		if(this.mousePressedOnMe)
		{
			//Mouse artýk basýlý deðil
			this.mousePressedOnMe = false;
			// Mouse Tiki
			this.mouseClicked(e);
		}
		
	}
	
	public void mouseEntered(MouseEvent e) {}
	
	
	public void mouseExited(MouseEvent e)
	{
		// Önceki Mouse Tiklerini unut
		this.mousePressedOnMe = false;
	}
		
	
    
}