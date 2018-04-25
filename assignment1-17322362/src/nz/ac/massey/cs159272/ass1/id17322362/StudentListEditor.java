package nz.ac.massey.cs159272.ass1.id17322362;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


public class StudentListEditor {

		static File defaultFile = new File("");
		static List<Student> shownStudentList = new ArrayList<Student>();
		static JTabbedPane tp = new JTabbedPane();
		static Student shownStudent = new Student();
		static String fileName = "";
		static Map<Student,List> map = new HashMap<Student,List>();
		static List<Course> courseList = new ArrayList<>();
		static List<Address> addressList = new ArrayList<>();
		static DateFormat myFormat= new SimpleDateFormat("yyyy-MM-dd");
		
    public static void main(String[] args) {
    		
    		StudentStorage ss = new StudentStorage();
    		
		JFrame frame = new JFrame();
		frame.setTitle("Student Editor");
		frame.setLocation(100,100);
		frame.setSize(1000,500);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton exit = new JButton("Exit");
		JButton load = new JButton("Load");
		JButton save = new JButton("Save");
		JButton add = new JButton("Add");
		JButton clone = new JButton("Clone");
		toolbar.add(exit);
		toolbar.add(load);
		toolbar.add(save);
		toolbar.add(add);
		toolbar.add(clone);
		
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Student newStudent = new Student();
				shownStudentList.add(newStudent);
				frame.remove(tp);
				tp = new JTabbedPane();
				frame.getContentPane().add(tp,BorderLayout.CENTER);//assign tab panel TP
				for (Student s :shownStudentList) { 
					if (!courseList.contains(s.getCourse()) && s.getCourse() != null) {
						courseList.add(s.getCourse());
					}
					if (!addressList.contains(s.getAddress()) && s.getAddress() != null) {
						addressList.add(s.getAddress());
					}
				}					
				for (Student s : shownStudentList) {
					addIt(tp,s);
				}
			}
		});
		
		clone.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int tabIndex = tp.getSelectedIndex();
				try {
					Student newStudent = new Student();
					if (shownStudentList.get(tabIndex)!=null) {
						newStudent = shownStudentList.get(tabIndex).clone();
					}
					shownStudentList.add(newStudent);
					frame.remove(tp);
					tp = new JTabbedPane();
					frame.getContentPane().add(tp,BorderLayout.CENTER);//assign tab panel TP
					for (Student s :shownStudentList) { 
						if (!courseList.contains(s.getCourse()) && s.getCourse() != null) {
							courseList.add(s.getCourse());
						}
						if (!addressList.contains(s.getAddress()) && s.getAddress() != null) {
							addressList.add(s.getAddress());
						}
					}					
					for (Student s : shownStudentList) {
						addIt(tp,s);
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		// add event handle to load button
		load.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int i = fileChooser.showOpenDialog(null);
				if(i == JFileChooser.APPROVE_OPTION) {			
					try {
						//reset the static variables, to refresh the current panel
						tp.setVisible(false);
						shownStudentList = new ArrayList<Student>();
						fileName = "";
						courseList = new ArrayList<>();
						addressList = new ArrayList<>();	
						map = new HashMap<Student,List>();
						frame.remove(tp);
						tp = new JTabbedPane();
						File selectedFile = fileChooser.getSelectedFile();
						fileName = selectedFile.getName();
						//defaultFile = selectedFile;//assign defaultFile
						
						shownStudentList = (List<Student>) ss.fetch(selectedFile);//assign shownStudentList
						frame.getContentPane().add(tp,BorderLayout.CENTER);//assign tab panel TP
						for (Student s :shownStudentList) { 
							if (!courseList.contains(s.getCourse())) {
								courseList.add(s.getCourse());
							}
							if (!addressList.contains(s.getAddress())) {
								addressList.add(s.getAddress());
							}
						}					
						for (Student s : shownStudentList) {
							addIt(tp,s);
						}
						tp.setVisible(true);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {												
				Iterator<Student> iter = shownStudentList.iterator(); 
				while(iter.hasNext()){							
					Student key = iter.next();
					List value = (List)map.get(key);														
					if(value!=null && value.get(0)!=null) {
						JTextArea jta_id = (JTextArea)(value.get(0));
						key.setId(jta_id.getText());
					}
					if(value!=null && value.get(1)!=null) {
						JTextArea jta_firstName = (JTextArea)(value.get(1));
						key.setFirstName(jta_firstName.getText());
					}
					if(value!=null && value.get(2)!=null) {
						JTextArea jta_name = (JTextArea)(value.get(2));
						key.setName(jta_name.getText());
					}
					if(value!=null && value.get(3)!=null) {
						JTextArea jta_dob = (JTextArea)(value.get(3));
						try {
							key.setDob(myFormat.parse(jta_dob.getText()));
						} catch (ParseException e) {						
							e.printStackTrace();
						}
					}					
					
					try {
						ss.save(shownStudentList, fileName);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					
				}
				
				
			}	
		});
		
		frame.getContentPane().add(toolbar,BorderLayout.NORTH);
		frame.getContentPane().add(new JLabel("Version 16/April/2018_by Tom Tong"),BorderLayout.SOUTH);
		frame.setVisible(true);

	}
    
    
	public static void addIt(JTabbedPane tabbedPane, Student s) {
		JLabel label_1 = new JLabel("id:");
		JLabel label_2 = new JLabel("first name:");
		JLabel label_3 = new JLabel("name:");
		JLabel label_4 = new JLabel("date of birth:");
		JLabel label_5 = new JLabel("address:");
		JLabel label_6 = new JLabel("course:");
	    	JPanel panel = new JPanel();
	    	JTextArea ta_1 = new JTextArea();
		JTextArea ta_2 = new JTextArea();
		JTextArea ta_3 = new JTextArea();
		JTextArea ta_4 = new JTextArea();
		JPanel addressPane = new JPanel();
		
		addressPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel ta_5 = new JLabel();		
		JButton addressButton = new JButton("...");
		addressPane.add(ta_5);
		addressPane.add(addressButton);
		DefaultComboBoxModel<Course> model = new DefaultComboBoxModel<>();
		DefaultListModel<Address> listModel = new DefaultListModel<>();
		for (Address a : addressList) {
			listModel.addElement(a);
		}
		
		addressButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JScrollPane addressFrame=new JScrollPane();
				JFrame addressFrame = new JFrame("Address List");
				addressFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
				JButton saveAddress = new JButton("save");
				addressFrame.add(saveAddress);
				addressFrame.setSize(400,300);
				addressFrame.setLocation(addressButton.getLocation());
				JList address = new JList(listModel);
				JScrollPane scrlpane=new JScrollPane();
				
				
				addressFrame.add(scrlpane);
				scrlpane.setViewportView(address);
				addressFrame.setVisible(true);
		
				saveAddress.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Address newAddress = addressList.get(address.getSelectedIndex());
						s.setAddress(newAddress);
						ta_5.setText(s.getAddress().toString());
						
					}
					
				});
			}
		});
		
		JComboBox<Course> ta_6 = new JComboBox<>(model);
		List selectedFileTexts = new ArrayList();	
		
		selectedFileTexts.add(null);
		selectedFileTexts.add(null);
		selectedFileTexts.add(null);
		selectedFileTexts.add(null);
		selectedFileTexts.add(null);
		selectedFileTexts.add(null);	
		
		if (s.getId()!=null) {
			ta_1.setText(s.getId());
			selectedFileTexts.set(0,ta_1);
		}
		if (s.getFirstName()!=null) {
			ta_2.setText(s.getFirstName());
			selectedFileTexts.set(1,ta_2);
		}
		if (s.getName()!=null) {
			ta_3.setText(s.getName());
			selectedFileTexts.set(2,ta_3);
		}
		if (s.getDob()!=null) {
			ta_4.setText(myFormat.format(s.getDob()));
			selectedFileTexts.set(3,ta_4);
		}
	    if (s.getAddress()!=null) {
	    		ta_5.setText(s.getAddress().toString());
	    		selectedFileTexts.set(4,ta_5);	  
	    }
	    
	    	for (Course i: courseList) {
	    		model.addElement(i);
	    	}
	    	model.setSelectedItem(s.getCourse());
	    	selectedFileTexts.set(5,ta_6);
	    
	    
	    map.put(s, selectedFileTexts);
	    
	    panel.setLayout(new GridLayout(6,2,10,10));
	    panel.add(label_1);
	    panel.add(ta_1);
	    panel.add(label_2);
	    panel.add(ta_2);
	    panel.add(label_3);
	    panel.add(ta_3);
	    panel.add(label_4);
	    panel.add(ta_4);
	    panel.add(label_5);
	    panel.add(addressPane);
	    panel.add(label_6);
	    panel.add(ta_6);
	   
	    
	    tabbedPane.addTab(s.getFirstName()+" "+s.getName(), panel);
	   
	}

}
