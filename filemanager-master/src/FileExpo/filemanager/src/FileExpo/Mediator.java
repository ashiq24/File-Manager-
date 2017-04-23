package FileExpo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Created by Ashiqur Rahman on 4/16/2017.
 */
public class Mediator {
    FileTable fileTable;

    TitlesModel titlesModel;
    JScrollPane filepane;
    JPanel mainPane;
    Factory factory;
    public Mediator() {

    }
    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public void setTitlesModel(TitlesModel titlesModel) {
        this.titlesModel = titlesModel;
    }

    public void setFilepane(JScrollPane filepane) {
        this.filepane = filepane;
    }

    public void setSplitPane(JPanel splitPane) {
        this.mainPane = splitPane;
    }
    public void loadchange()
    {
        Main.Directory.setText(Main.currentDir.getAbsolutePath());
        if(Main.choice==1)
        {
            fileTable.removeRows();
            fileTable.addRow(new DefaultMutableTreeNode(Main.currentDir));
            fileTable.fireTableDataChanged();
        }
        else
        {
            titlesModel.LoadChange(new DefaultMutableTreeNode(Main.currentDir));
        }
    }
    public void changeView()
    {
        Main.choice=Main.choice==1 ? 0 :1;
       mainPane.remove(1);
       mainPane.add(factory.getcomponent(Main.choice),1);
       mainPane.updateUI();
    }
}
