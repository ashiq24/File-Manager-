package FileExpo;

import javafx.scene.control.SplitPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ashiqur Rahman on 4/9/2017.
 */
public class Main extends JFrame {

    public static final String APP_TITLE = "FileMan";
    private FileSystemView fileSystemView;
    public static File currentDir = new File(new File(".").getAbsolutePath());
    /** currently selected File. */
    private File currentFile;

    /** Main GUI container */
    private JPanel gui;

    /** File-system tree. Built Lazily */
    private JTree tree;
    private DefaultTreeModel treeModel;

    /** Directory listing */
    private FileTable ftable;
    private JTable table;
    private JLabel fileName;
    private JLabel date;
    private JLabel size;
    Main() {
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        ftable=new FileTable();//creating table here

        DefaultMutableTreeNode n=new DefaultMutableTreeNode(currentDir);
        ftable.addRow(n);
        table=new JTable(ftable);
        ftable.setColumnWidths(table);
        /**
         * tree mode start
         */
        DefaultTreeModel ftree;
        ftree= FileTree.getSingleTon();
        JTree tree = new JTree(ftree);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //this is thies mode things
        TitlesModel Tm= new TitlesModel(new DefaultMutableTreeNode(currentDir));

        /***
         *action listener on tree node
         */
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            FileTree.Ftree.addChildNodes(node,1);
            if (node == null) return;

            ftable.removeRows();
           ftable.addRow(node);
           Tm.LoadChange(node);

           ftable.fireTableDataChanged();

        });
        JScrollPane pane = new JScrollPane(tree);
        pane.setPreferredSize(new Dimension(150, 300));


        JScrollPane pane1 =  Tm.pane;
        pane1.setPreferredSize(new Dimension(550, 300));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pane, pane1);

        getContentPane().add(splitPane);



    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }


}
