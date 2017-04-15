package FileExpo;

import javafx.scene.control.SplitPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
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

    public static File currentDir = new File(new File(".").getAbsolutePath());
    public static int choice = 0;
    Mediator mediator;
    Main() {
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        mediator=new Mediator();
        //ftable=new FileTable();//creating table here

        //DefaultMutableTreeNode n=new DefaultMutableTreeNode(currentDir);
        //ftable.addRow(n);
        //table=new JTable(ftable);
        //ftable.setColumnWidths(table);
        /**
         * tree making  start
         */
        DefaultTreeModel ftree;
        ftree= FileTree.getSingleTon();
        JTree tree = new JTree(ftree);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        /***
         *action listener on tree node
         */
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            FileTree.Ftree.addChildNodes(node,1);
            if (node == null) return;
            else
            {
                currentDir=(File)node.getUserObject();
                mediator.loadchange();

            }

        });

        Factory factory=new Factory(mediator);
        mediator.setFactory(factory);

        JPanel treecontainer=new JPanel();
        treecontainer.setLayout(new BoxLayout(treecontainer,BoxLayout.PAGE_AXIS));
        treecontainer.setSize(200,400);
        JButton view=new JButton("  Tiles  ");
        view.setPreferredSize(new Dimension(80,20));

        view.addActionListener(e->
                {
                    if(Main.choice==0)view.setText("   Table  ");
                    else view.setText("   Tiles  ");

                    mediator.changeView();
                }
        );


        JScrollPane treepane = new JScrollPane(tree);
        treepane.setPreferredSize(new Dimension(150, 500));
        treecontainer.add(view);
        treecontainer.add(treepane);




        JScrollPane filepane = factory.getcomponent(choice);




        JPanel mainPane = new JPanel( );
        mainPane.setSize(700,450);
        mainPane.add(treecontainer,0);
        mainPane.add(filepane,1);
        mediator.setFilepane(filepane);
        mediator.setSplitPane(mainPane);
        getContentPane().add(mainPane);
        setTitle("FILE MANAGER");



    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }


}
