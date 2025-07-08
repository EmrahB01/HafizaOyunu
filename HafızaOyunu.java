package Oyun;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class HafýzaOyunu implements ActionListener
{
	
	
	
	private JFrame mainFrame;					
	private Container mainContentPane;			
	private TurnCounterLabel turnCounterLabel;
	private ImageIcon cardIcon[]; // 0-7 arasý resimler 8. ise kart arkasý
	private String ResimDizini="futbol";
	
	
	private static void newMenuItem(String text, JMenu menu, ActionListener listener)
	{
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}
	
	
	private ImageIcon[] loadCardIcons()
	{
		// Resimler için dizi
		ImageIcon icon[] = new ImageIcon[9];
		
		for(int i = 0; i < 9; i++ )
		{
			// Dosyadan alýnan resmi ikon a atayýn
			String fileName = "resimler/"+ResimDizini+"/card" + i + ".jpg";
			icon[i] = new ImageIcon(fileName);
			//Resim uyuþmazsa error
			if(icon[i].getImageLoadStatus() == MediaTracker.ERRORED)
			{
				
				JOptionPane.showMessageDialog(this.mainFrame
					, "The image " + fileName + " could not be loaded."
					, "Hafýza Oyunu Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		return icon;
	}

	
	public HafýzaOyunu()
	{
		// Oyun Ekraný
		this.mainFrame = new JFrame("Hafýza Oyunu");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(900,900);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS)); 
		
		this.turnCounterLabel = new TurnCounterLabel();
		
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);
		// Menü
		JMenu gameMenu = new JMenu("Menü");
		menuBar.add(gameMenu);
		newMenuItem("Yeni Oyun", gameMenu, this);
		newMenuItem("Çýkýþ", gameMenu, this);


		this.cardIcon = loadCardIcons(); 
		
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getActionCommand().equals("Yeni Oyun")) yeniOyun();
		else if(e.getActionCommand().equals("Çýkýþ")) System.exit(0);
	}
	
	public static void randomizeIntArray(int[] a)
	{
		Random randomizer = new Random();
		// Dizi yineleme
		for(int i = 0; i < a.length; i++ )
		{
			// Takas etme
			int d = randomizer.nextInt(a.length);
			// swap the entries
			int t = a[d];
			a[d] = a[i];
			a[i] = t;
		}
	}
	
	
	public JPanel makeCards()
	{
		// 4x4 lük panel
		JPanel panel = new JPanel(new GridLayout(4, 4));
		
		TurnedCardManager turnedManager = new TurnedCardManager(this.turnCounterLabel);
		// Tüm kartlarýn arkasýna ayný resmi ata
		ImageIcon backIcon = this.cardIcon[8];
		
		//Dizi kartlarýna numara ver 0,0-1-1 ...
		int cardsToAdd[] = new int[16];
		for(int i = 0; i < 8; i++)
		{
			cardsToAdd[2*i] = i;
			cardsToAdd[2*i + 1] = i;
		}
		// Random
		randomizeIntArray(cardsToAdd);
		
		// Her Kartý nesne yap
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// 0-7 arasý random sayý
			int num = cardsToAdd[i];
			// Kart Nesnesi 
			Card newCard = new Card(turnedManager, this.cardIcon[num], backIcon, num);
			// Panele kartlarý ekle
			panel.add(newCard);
		}
		
		return panel;
	}
	
	
	public void yeniOyun()
	{
		// Dönüþü sýfýrla
		this.turnCounterLabel.reset();
		
		this.mainContentPane.removeAll();
		
        this.mainContentPane.add(makeCards());
		
		this.mainContentPane.add(this.turnCounterLabel);
		// Ýlk oyunda pencereyi göster
		this.mainFrame.setVisible(true);
	}
	
	


	
	
	
}

