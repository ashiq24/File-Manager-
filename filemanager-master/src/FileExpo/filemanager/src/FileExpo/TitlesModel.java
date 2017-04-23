package FileExpo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;


class FilesIcon {


    String Filename;
    ImageIcon Image;

    public FilesIcon(String filename, ImageIcon image) {
        this.Filename = filename;
        this.Image = image;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public void setImage(ImageIcon image) {
        Image = image;
    }

    public String getFilename() {
        return Filename;
    }

    public ImageIcon getImage() {
        return Image;
    }
}


public class TitlesModel{

    public FilesIcon filesicon[];

    public JList FileList ;

    public  List<File> Files;

    JScrollPane pane;

    Mediator mediator;
    public TitlesModel(DefaultMutableTreeNode node , Mediator mediator) {
        this.mediator=mediator;
        /**
         * this is loading thing
         */

        FileList = new JList();
        LoadChange(node);
        FileList.setCellRenderer(new FileCellRenderer());
        FileList.setVisibleRowCount(-1);
        FileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Main.currentDir=Files.get(index);
                    mediator.loadchange();
                }
            }
        });
        FileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

    }

    JList getList()
    {
        return FileList;
    }

    /**
     * @param node
     * this is for loading changes and initial files
     */
    public  void LoadChange(DefaultMutableTreeNode node)
    {
        File file = (File) node.getUserObject();
        Files=new ArrayList<>();
        if(file.isDirectory()) {
            filesicon=new FilesIcon[file.listFiles().length];
            int i=0;
            for (File child : file.listFiles()) {
                Files.add(child);
                filesicon[i]= new FilesIcon(child.getName(),(ImageIcon)FileSystemView.getFileSystemView().getSystemIcon(child));
                i++;
            }
        }else
        {
            Files.add(file);
            filesicon=new FilesIcon[1];
            filesicon[0]= new FilesIcon(file.getName(),(ImageIcon)FileSystemView.getFileSystemView().getSystemIcon(file));
        }
       FileList.setListData(filesicon);
    }
}


class FileCellRenderer extends JLabel implements ListCellRenderer {

    public FileCellRenderer() {
        setOpaque(true);
        setIconTextGap(3);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        FilesIcon entry = (FilesIcon) value;
        JPanel box=new JPanel();
        JLabel iconpart=new JLabel(entry.getImage());
        JLabel textpart=new JLabel(entry.getFilename());
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.add(iconpart);
        box.add(textpart);

        return box;
    }
}