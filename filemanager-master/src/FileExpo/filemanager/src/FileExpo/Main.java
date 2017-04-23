package FileExpo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ashiqur Rahman on 4/9/2017.
 */
public class Main extends JFrame {

    public static File currentDir =new File(new File(".").getAbsolutePath()); //FileSystemView.getFileSystemView().getHomeDirectory();
    public static int choice = 1;
    public static JTextField Directory =new JTextField(new File(".").getAbsolutePath());
    Mediator mediator;
    Main() {
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        mediator=new Mediator();
        /**
         * tree making  start
         */
        DefaultTreeModel ftree;
        ftree= FileTree.getSingleTon();
        JTree tree = new JTree(ftree);
        tree.setCellRenderer(new TreecellRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        /***
         *action listener on tree node
         */
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            FileTree.Ftree.addChildNodes(node,0);
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
                    if(Main.choice==1)view.setText("   Table  ");
                    else view.setText("   Tiles  ");

                    mediator.changeView();
                }
        );


        JScrollPane treepane = new JScrollPane(tree);
        treepane.setPreferredSize(new Dimension(180, 500));
        treecontainer.add(Directory);
        treecontainer.add(view);
        treecontainer.add(treepane);





        JScrollPane filepane = factory.getcomponent(choice);




        JPanel mainPane = new JPanel( );
        mainPane.setSize(800,450);
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

class TreecellRenderer extends JLabel implements TreeCellRenderer
{
    public TreecellRenderer()
    {

    }
    @Override
    public Component getTreeCellRendererComponent(JTree jTree, Object o, boolean b, boolean b1, boolean b2, int i, boolean b3) {
        DefaultMutableTreeNode node =(DefaultMutableTreeNode)o;
        File file=(File)node.getUserObject();
        if(file!=null) {
            String name = file.getName();
            if (name == null) name = file.getAbsoluteFile().toString();
            setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
            setText(name);
        }else
        {
            setText(" ");
        }
        return this;
    }
}
