package FileExpo;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.nio.file.FileSystem;
import java.util.Enumeration;
/**
 * Created by Ashiqur Rahman on 4/9/2017.
 * this is a example of single ton
 * because we need only one tree view to this app.
 */
public class FileTree {

    static DefaultTreeModel ftree;
    static FileTree Ftree;
    private  FileTree()
    {
        ftree=this.getTree();
    }


    public static DefaultTreeModel getSingleTon()
    {
        if(Ftree==null)
        {
            Ftree=new FileTree();
        }
        return ftree;
    }


    private DefaultTreeModel getTree()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        for (File file : Main.currentDir.listFiles()) {
            root.add(new DefaultMutableTreeNode(file));
        }

        addChildNodes(root,0);
        return new DefaultTreeModel(root);
    }

    public void addChildNodes(DefaultMutableTreeNode root, int a) {
        try {
            Enumeration<?> enumeration = root.children();
            while (enumeration.hasMoreElements()) {
                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode) enumeration.nextElement();
                if(node.getChildCount()!=0) continue;
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    for (File child : file.listFiles()) {
                        node.add(new DefaultMutableTreeNode(child));
                    }
                    if (a > 0) addChildNodes(node, a - 1);
                }
            }
        }catch (NullPointerException n)
        {

        }
    }


}
