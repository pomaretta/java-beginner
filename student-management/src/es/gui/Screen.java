package es.gui;

/*

    Project     java-beginner
    Package     es.gui    
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2020-12-10

    DESCRIPTION
    
*/

import es.objects.Student;
import es.objects.Department;
import es.objects.departments;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class Screen extends JFrame {
    private JList studentList;
    private JButton buttonNew;
    private JButton buttonSave;
    private JCheckBox checkAdmitted;
    private JComboBox comboDepartments;
    private JTextField textFirstName;
    private JTextField textLastName;
    private JTextField textMail;
    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private DefaultListModel studentsList;

    Screen(){
        super("Contacts App");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        studentsList = new DefaultListModel<>();
        buttonSave.setEnabled(false);

        getDepartments();

        studentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listStudentSelection();
            }
        });
        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
    }

    private void saveStudent(){
        int studentNumber = this.studentList.getSelectedIndex();
        if(studentNumber >= 0){
            Student p = Student.retrieveStudents().get(studentNumber);
            p.setFirstName(textFirstName.getText());
            p.setLastName(textLastName.getText());
            p.setStudentMail(textMail.getText());
            p.setAdmitted(checkAdmitted.isSelected());
            p.setDepartment(departments.valueOf(comboDepartments.getSelectedItem().toString()));
            refreshStudentList();
        }
    }

    private void addStudent(){
        Student s;

        if(!textMail.getText().equals("")) {
            s = new Student(
                    textFirstName.getText(),
                    textLastName.getText(),
                    textMail.getText(),
                    departments.valueOf(comboDepartments.getSelectedItem().toString()),
                    checkAdmitted.isSelected()
            );
        } else {
            s = new Student(
                    textFirstName.getText(),
                    textLastName.getText(),
                    departments.valueOf(comboDepartments.getSelectedItem().toString()),
                    checkAdmitted.isSelected()
            );
        }
        refreshStudentList();
    }

    private void refreshStudentList(){
        studentsList.removeAllElements();
        System.out.println("CLEAR");
        for(Student student : Student.retrieveStudents()){
            System.out.println("Adding: " + student.getFirstName());
            studentsList.addElement(student.getFirstName());
        }

    }

    private void getDepartments(){
        for(departments department : departments.values()){
            comboDepartments.addItem(department);
        }
    }

    private void listStudentSelection(){
        int studentNumber = studentList.getSelectedIndex();
        if(studentNumber >= 0){
            Student p = Student.retrieveStudents().get(studentNumber);
            textFirstName.setText(p.getFirstName());
            textLastName.setText(p.getLastName());
            textMail.setText(p.getStudentMail());
            comboDepartments.setSelectedItem(p.getDepartment().toString());
            checkAdmitted.setSelected(p.getAdmitted());
            buttonSave.setEnabled(true);
        } else {
            buttonSave.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setVisible(true);
    }
}
