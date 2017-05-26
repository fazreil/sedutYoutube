package com.rohaizanroosley.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.rohaizanroosley.util.UserPreferrences;
import com.rohaizanroosley.util.Utilities;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblStatus;

	private JFrame jFrame;
	private JTextField txtYoutubeDlBinFolder;
	private JTextField txtFfmpegBinFolder;
	private JRadioButton rdoMp3;
	private JRadioButton rdoMp4;
	private JLabel lblYoutubedlVer;
	private JLabel lblFfmpegVer;
	private JButton btnStart;
	
	private Boolean ffmpegFound;
	private Boolean youtubeDlFound;
	
	public Main(){
		jFrame = new JFrame();
		jFrame.getContentPane().setBackground(new Color(0, 0, 0));
		
		jFrame.setTitle("Sedut Youtube!");
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBounds(0, 0, 800, 400);
		jFrame.setLocationRelativeTo(null);
		
		jFrame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(6, 44, 788, 288);
		jFrame.getContentPane().add(tabbedPane);
		
		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(Color.GRAY);
		tabbedPane.addTab("Main", null, pnlMain, null);
		pnlMain.setLayout(null);
		
		JLabel lblMain = new JLabel("Youtube URL:");
		lblMain.setForeground(new Color(0, 0, 0));
		lblMain.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMain.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblMain.setBounds(37, 32, 188, 28);
		pnlMain.add(lblMain);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(ffmpegFound && youtubeDlFound){
					btnStart.setEnabled(true);
				}else{
					btnStart.setEnabled(false);
				}
				
			}
		});
		textField.setBackground(new Color(192, 192, 192));
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textField.setBounds(237, 32, 395, 28);
		pnlMain.add(textField);
		textField.setColumns(10);
		
		JLabel lblDestinationFoler = new JLabel("Destination Folder:");
		lblDestinationFoler.setForeground(new Color(0, 0, 0));
		lblDestinationFoler.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDestinationFoler.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblDestinationFoler.setBounds(37, 72, 188, 28);
		pnlMain.add(lblDestinationFoler);
		
		textField_1 = new JTextField();
		textField_1.setBackground(new Color(192, 192, 192));
		textField_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textField_1.setBounds(237, 72, 395, 28);
		//textField_1.setEditable(false);
		pnlMain.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(UserPreferrences.getDestinationFolder());
		
		JButton btnSelect = new JButton("Browse");
		btnSelect.setBounds(644, 72, 117, 28);
		pnlMain.add(btnSelect);
		
		btnSelect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					final JFileChooser fc = new JFileChooser();
				    fc.setDialogTitle("Select destination folder");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					
					if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			            textField_1.setText(fc.getSelectedFile().getPath());
			            
			            UserPreferrences.saveDestinationFolder(fc.getSelectedFile().getPath());
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		
		btnStart = new JButton("START");
		btnStart.setEnabled(false);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater( new Runnable(){
					@Override
					public void run() {
						SwingWorker<LocalTime , Void> worker = new SwingWorker<LocalTime , Void>(){
							@Override
							protected LocalTime doInBackground() throws Exception {
								downloadYoutube();
								return null;
							}
						};
						worker.execute();
					}
				});

			}
		});
		btnStart.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		btnStart.setBounds(247, 153, 385, 72);
		pnlMain.add(btnStart);
		
		rdoMp3 = new JRadioButton("MP3 (Audio Only)");
		rdoMp3.setSelected(true);
		rdoMp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdoMp3.setSelected(true);
				rdoMp4.setSelected(false);
			}
		});
		rdoMp3.setBackground(new Color(192, 192, 192));
		rdoMp3.setForeground(new Color(0, 0, 0));
		rdoMp3.setBounds(237, 115, 141, 23);
		pnlMain.add(rdoMp3);
		
		rdoMp4 = new JRadioButton("MP4 (Audio & Video)");
		rdoMp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdoMp3.setSelected(false);
				rdoMp4.setSelected(true);
			}
		});
		rdoMp4.setBackground(new Color(192, 192, 192));
		rdoMp4.setForeground(new Color(0, 0, 0));
		rdoMp4.setBounds(387, 115, 159, 23);
		pnlMain.add(rdoMp4);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
			}
		});
		btnClear.setBounds(644, 32, 117, 28);
		pnlMain.add(btnClear);
		
		JPanel pnlSettings = new JPanel();
		pnlSettings.setBackground(new Color(128, 128, 128));
		tabbedPane.addTab("Setting", null, pnlSettings, null);
		pnlSettings.setLayout(null);
		
		JLabel lblSetting = new JLabel("youtube-dl Path:");
		lblSetting.setForeground(new Color(0, 0, 0));
		lblSetting.setBounds(33, 36, 206, 26);
		pnlSettings.add(lblSetting);
		
		JLabel lblFfmpegPath = new JLabel("ffmpeg Path:");
		lblFfmpegPath.setForeground(new Color(0, 0, 0));
		lblFfmpegPath.setBounds(33, 78, 107, 26);
		pnlSettings.add(lblFfmpegPath);
		
		JLabel lblThisProgramIs = new JLabel("This program is a UI wrapper for youtube-dl & ffmpeg.");
		lblThisProgramIs.setForeground(new Color(0, 0, 0));
		lblThisProgramIs.setBounds(33, 127, 594, 16);
		pnlSettings.add(lblThisProgramIs);
		
		txtYoutubeDlBinFolder = new JTextField();
		txtYoutubeDlBinFolder.setEditable(false);
		txtYoutubeDlBinFolder.setBounds(153, 35, 352, 28);
		pnlSettings.add(txtYoutubeDlBinFolder);
		txtYoutubeDlBinFolder.setColumns(10);
		txtYoutubeDlBinFolder.setText(UserPreferrences.getYoutubeDlBinFolder());
		
		txtFfmpegBinFolder = new JTextField();
		txtFfmpegBinFolder.setEditable(false);
		txtFfmpegBinFolder.setBounds(152, 77, 353, 28);
		pnlSettings.add(txtFfmpegBinFolder);
		txtFfmpegBinFolder.setColumns(10);
		txtFfmpegBinFolder.setText(UserPreferrences.getFfmpegBinFolder());
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					final JFileChooser fc = new JFileChooser();
				    fc.setDialogTitle("Select youtube-dl bin folder");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					
					if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			            txtYoutubeDlBinFolder.setText(fc.getSelectedFile().getPath());
			            
			            UserPreferrences.saveYoutubeDlBinFolder(fc.getSelectedFile().getPath());
			            
			            getYoutubeDlVersion();
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		btnBrowse.setBounds(510, 36, 117, 29);
		pnlSettings.add(btnBrowse);
		
		JButton btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					final JFileChooser fc = new JFileChooser();
				    fc.setDialogTitle("Select ffmpeg bin folder");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					
					if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			            txtFfmpegBinFolder.setText(fc.getSelectedFile().getPath());
			            
			            UserPreferrences.saveffmpegBinFolder(fc.getSelectedFile().getPath());
			            
			            getFfmpegVersion();
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		btnBrowse_1.setBounds(510, 78, 117, 29);
		pnlSettings.add(btnBrowse_1);
		
		JLabel lblThisProgramIs_1 = new JLabel("This program is for educational purpose only. *cough* Use at  *cough*  your  *cough*  own risk.  *cough* ");
		lblThisProgramIs_1.setForeground(new Color(255, 255, 0));
		lblThisProgramIs_1.setBounds(33, 156, 669, 16);
		pnlSettings.add(lblThisProgramIs_1);
		
		JLabel lblForInstallationGuide = new JLabel("For installation guide and updates, please click this button");
		lblForInstallationGuide.setForeground(Color.BLACK);
		lblForInstallationGuide.setBounds(33, 184, 386, 16);
		pnlSettings.add(lblForInstallationGuide);
		
		JButton btnInstallationGuide = new JButton("Open Web browser");
		btnInstallationGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			        Desktop.getDesktop().browse(new URL("http://wp.me/p8IL0O-1D").toURI());
			    } catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Error:",
					        JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		btnInstallationGuide.setBounds(412, 179, 171, 29);
		pnlSettings.add(btnInstallationGuide);
		
		lblStatus = new JLabel("Status: ");
		lblStatus.setForeground(new Color(192, 192, 192));
		lblStatus.setVerticalAlignment(SwingConstants.TOP);
		lblStatus.setBounds(16, 344, 762, 28);
		jFrame.getContentPane().add(lblStatus);
		
		JLabel lblYoutubedlVersion = new JLabel("youtube-dl version:");
		lblYoutubedlVersion.setForeground(new Color(192, 192, 192));
		lblYoutubedlVersion.setBounds(510, 17, 135, 26);
		jFrame.getContentPane().add(lblYoutubedlVersion);
		
		lblYoutubedlVer = new JLabel("not found");
		lblYoutubedlVer.setForeground(Color.LIGHT_GRAY);
		lblYoutubedlVer.setBounds(641, 17, 135, 26);
		jFrame.getContentPane().add(lblYoutubedlVer);
		
		JLabel lblFfmpegVersion = new JLabel("ffmpeg version:");
		lblFfmpegVersion.setForeground(Color.LIGHT_GRAY);
		lblFfmpegVersion.setBounds(75, 17, 106, 26);
		jFrame.getContentPane().add(lblFfmpegVersion);
		
		lblFfmpegVer = new JLabel("not found");
		lblFfmpegVer.setForeground(Color.LIGHT_GRAY);
		lblFfmpegVer.setBounds(193, 17, 128, 26);
		jFrame.getContentPane().add(lblFfmpegVer);
		
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run() {
				SwingWorker<LocalTime , Void> worker = new SwingWorker<LocalTime , Void>(){
					@Override
					protected LocalTime doInBackground() throws Exception {
						getYoutubeDlVersion();
						getFfmpegVersion();
						return null;
					}
				};
				worker.execute();
			}
		});
	}
	
	public void setVisible(Boolean val){
		jFrame.setVisible(val);
	}
	
	private void downloadYoutube(){
		List<String> command = prepareCommand();
		
		try {
			Process proc = Runtime.getRuntime().exec(command.toArray(new String[0]));
			BufferedReader reader =  
			      new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String line = "";
			while((line = reader.readLine()) != null) {
			    System.out.print(line + "\n");
			    
			    final String result = line; 
				SwingUtilities.invokeLater(() -> lblStatus.setText(result));
			}
			proc.waitFor();	
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> lblStatus.setText("Status: Error."));
		}
	}
	
	private void getYoutubeDlVersion(){
		List<String> command = new ArrayList<String>();
		
		if (System.getProperty("os.name").contains("Windows")) {
			command.add(UserPreferrences.getYoutubeDlBinFolder() + "\\youtube-dl.exe");
		}else{
			command.add(UserPreferrences.getYoutubeDlBinFolder() + "/youtube-dl");
		}
		
		command.add("--version");
		
		try {
			Process proc = Runtime.getRuntime().exec(command.toArray(new String[0]));
			BufferedReader reader =  
			      new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String line = "";
			while((line = reader.readLine()) != null) {
			    System.out.print(line + "\n");
			    
			    final String result = line; 
				SwingUtilities.invokeLater(() -> lblYoutubedlVer.setText(result));
				youtubeDlFound = true;
			}
			proc.waitFor();	
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> lblYoutubedlVer.setText("not found"));
			youtubeDlFound = false;
		}
	}

	private void getFfmpegVersion(){
		List<String> command = new ArrayList<String>();
		
		if (System.getProperty("os.name").contains("Windows")) {
			command.add(UserPreferrences.getFfmpegBinFolder() + "\\ffmpeg.exe");
		}else{
			command.add(UserPreferrences.getFfmpegBinFolder() + "/ffmpeg");
		}
		
		command.add("-version");
		
		try {
			Process proc = Runtime.getRuntime().exec(command.toArray(new String[0]));
			BufferedReader reader =  
			      new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String line = "";
			while((line = reader.readLine()) != null) {
			    System.out.print(line + "\n");
			    
			    final String result = line.substring(0, 19); 
				SwingUtilities.invokeLater(() -> lblFfmpegVer.setText(result));
				
				ffmpegFound = true;
				break; // get first line only
			}
			proc.waitFor();	
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> lblFfmpegVer.setText("not found"));
			ffmpegFound = false;
		}
	}
	
	private List<String> prepareCommand(){
		List<String> result = new ArrayList<String>();
		
		if (System.getProperty("os.name").contains("Windows")) {
			result.add(UserPreferrences.getYoutubeDlBinFolder() + "\\youtube-dl.exe");
			result.add("-x");
			result.add("-o");
			result.add(textField_1.getText() + "\\" + "%(title)s.%(ext)s");
		}else{
			result.add(UserPreferrences.getYoutubeDlBinFolder() + "/youtube-dl");
			result.add("-x");
			result.add("-o");
			result.add(textField_1.getText() + "/" + "%(title)s.%(ext)s");
		}
		
		if (rdoMp3.isSelected()){
			result.add("--audio-format");
			result.add("mp3");
		}else if (rdoMp4.isSelected()){
			result.add("--recode-video");
			result.add("mp4");	
		}
		
		result.add("--age-limit");
		result.add("0");
		result.add("-i");
		result.add("--prefer-ffmpeg");
		result.add("--ffmpeg-location");
		result.add(UserPreferrences.getFfmpegBinFolder());
		
		result.add(Utilities.prepareYoutubeLink(textField.getText()));
		
		return result;
	}
}
