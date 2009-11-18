package com.bagnet;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.junit.Test;

public class UITest {
	@Test
	public void test(BufferedImage image) {

		Icon icon = new ImageIcon(image);
		String input = (String) JOptionPane.showInputDialog(null, "Please enter the captcha text...", "Captcha Text", JOptionPane.QUESTION_MESSAGE, icon, null, null);
		System.out.println("Input provided: " + input);
		
		if (input == null) {
			// TODO: GET CAPTCHA AGAIN
		} else {
			// TODO: LOG IN WITH TEXT			
		}	
	}
}
