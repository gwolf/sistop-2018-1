import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileSystemView;

import javax.imageio.ImageIO;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.nio.channels.FileChannel;

import java.net.URL;

public class ManejadorArchivos 
{
    //Título de la ventana
    public static final String APP_TITLE = "Mini Sistema de Archivos";
    //Abre el open/edit/print para los archivos
    private Desktop desktop;
    private FileSystemView fileSystemView;
    //Archivo actualmente seleccionado
    private File currentFile;

    //Contenedor principal
    private JPanel gui;

    //Sistema archivos en árbol
    private JTree tree;
    private DefaultTreeModel treeModel;

    //Listar directorio
    private JTable table;
    private JProgressBar progressBar;
    private ModeloTablaArchivo modeloTablaArchivo;
    private ListSelectionListener listSelectionListener;
    private boolean cellSizesSet = false;
    private int rowIconPadding = 6;

    //Controles
    private JButton openFile;
    private JButton printFile;
    private JButton editFile;

    //Detalles archivos
    private JLabel fileName;
    private JTextField path;
    private JLabel date;
    private JLabel size;
    private JCheckBox readable;
    private JCheckBox writable;
    private JCheckBox executable;
    private JRadioButton isDirectory;
    private JRadioButton isFile;

    //Opciones GUI para nueva creacion de Directorio/Archivo
    private JPanel newFilePanel;
    private JRadioButton newTypeFile;
    private JTextField name;

    public Container getGui() 
    {
        if (gui==null) 
        {
            gui = new JPanel(new BorderLayout(3,3)); //obtenemos un nueg¿vo jpanel
            gui.setBorder(new EmptyBorder(5,5,5,5));

            //creamos objeto para la representación del sistema de archivos, toda la informacion es recopilada del sistema de archivos actual
            fileSystemView = FileSystemView.getFileSystemView();
            /*Las aplicaciones lanzadas corresponden a las que tienen asociados los archivos que queremos operar*/
            desktop = Desktop.getDesktop(); //objeto de Desktop que nos permite operaciones basicas con archivos


            JPanel detailView = new JPanel(new BorderLayout(3,3));

            table = new JTable(); //creamos un Jtable con caracteristicas
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoCreateRowSorter(true);
            table.setShowVerticalLines(false);

            listSelectionListener = new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) 
                {
                    int row = table.getSelectionModel().getLeadSelectionIndex();
                    setFileDetails( ((ModeloTablaArchivo)table.getModel()).getFile(row) );
                }
            };
            table.getSelectionModel().addListSelectionListener(listSelectionListener);
            JScrollPane tableScroll = new JScrollPane(table);
            Dimension d = tableScroll.getPreferredSize();
            tableScroll.setPreferredSize(new Dimension((int)d.getWidth(), (int)d.getHeight()/2));
            detailView.add(tableScroll, BorderLayout.CENTER);

            // arbol de archivos
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            treeModel = new DefaultTreeModel(root);

            TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent tse)
                {
                    DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                    showChildren(node);
                    setFileDetails((File)node.getUserObject());
                }
            };

            File[] roots = fileSystemView.getRoots(); 
            for (File fileSystemRoot : roots) //recorremos la(s) raiz buscando creando objetos de tipo nodo
            {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                root.add( node );
                File[] files = fileSystemView.getFiles(fileSystemRoot, true);
                for (File file : files) 
                {
                    if (file.isDirectory()) 
                    {
                        node.add(new DefaultMutableTreeNode(file));
                    }
                }
            }

            tree = new JTree(treeModel);
            tree.setRootVisible(false);
            tree.addTreeSelectionListener(treeSelectionListener);
            tree.setCellRenderer(new FileTreeCellRenderer());
            tree.expandRow(0);
            JScrollPane treeScroll = new JScrollPane(tree);

            tree.setVisibleRowCount(15);

            Dimension preferredSize = treeScroll.getPreferredSize();
            Dimension widePreferred = new Dimension(200,(int)preferredSize.getHeight());
            treeScroll.setPreferredSize( widePreferred );

            // detalles para un archivo
            JPanel fileMainDetails = new JPanel(new BorderLayout(4,2));
            fileMainDetails.setBorder(new EmptyBorder(0,6,0,6));

            JPanel fileDetailsLabels = new JPanel(new GridLayout(0,1,2,2));
            fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

            JPanel fileDetailsValues = new JPanel(new GridLayout(0,1,2,2));
            fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

            fileDetailsLabels.add(new JLabel("Archivo", JLabel.TRAILING));
            fileName = new JLabel();
            fileDetailsValues.add(fileName);
            fileDetailsLabels.add(new JLabel("Path/nombre", JLabel.TRAILING));
            path = new JTextField(5);
            path.setEditable(false);
            fileDetailsValues.add(path);
            fileDetailsLabels.add(new JLabel("Ultima Actualizacion", JLabel.TRAILING));
            date = new JLabel();
            fileDetailsValues.add(date);
            fileDetailsLabels.add(new JLabel("Tamanio archivo", JLabel.TRAILING));
            size = new JLabel();
            fileDetailsValues.add(size);
            fileDetailsLabels.add(new JLabel("Tipo", JLabel.TRAILING));

            JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING,4,0));

            isDirectory = new JRadioButton("Directorio");
            flags.add(isDirectory);

            isFile = new JRadioButton("Archivo");
            flags.add(isFile);
            fileDetailsValues.add(flags);

            JToolBar toolBar = new JToolBar();
            toolBar.setFloatable(false);

            openFile = new JButton("Abrir");
            openFile.setMnemonic('o');

            openFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) 
                {
                    try 
                    {
                        System.out.println("Abrir: " + currentFile);
                        desktop.open(currentFile); //intento por abrir el archivo en cuestion
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(openFile);

            editFile = new JButton("Editar");
            editFile.setMnemonic('e');
            editFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) 
                {
                    try 
                    {
                        desktop.edit(currentFile); //intendamos editar el archido
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                }
            });
            toolBar.add(editFile);

            printFile = new JButton("Imprimir");
            printFile.setMnemonic('p');
            printFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) 
                {
                    try 
                    {
                        desktop.print(currentFile); //se intenta imprimir el archivo
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                }
            });
            toolBar.add(printFile);

            // Comprobamos que sean permitidas las acciones.
            openFile.setEnabled(desktop.isSupported(Desktop.Action.OPEN));
            editFile.setEnabled(desktop.isSupported(Desktop.Action.EDIT));
            printFile.setEnabled(desktop.isSupported(Desktop.Action.PRINT));

            flags.add(new JLabel("::  Permisos"));
            readable = new JCheckBox("Leer  ");
            readable.setMnemonic('a');
            flags.add(readable);

            writable = new JCheckBox("Escribir  ");
            writable.setMnemonic('w');
            flags.add(writable);

            executable = new JCheckBox("Ejecutar");
            executable.setMnemonic('x');
            flags.add(executable);

            int count = fileDetailsLabels.getComponentCount();
            for (int ii=0; ii<count; ii++) 
            {
                fileDetailsLabels.getComponent(ii).setEnabled(false);
            }

            count = flags.getComponentCount();
            for (int ii=0; ii<count; ii++) 
            {
                flags.getComponent(ii).setEnabled(false);
            }

            JPanel fileView = new JPanel(new BorderLayout(3,3));

            fileView.add(toolBar,BorderLayout.NORTH);
            fileView.add(fileMainDetails,BorderLayout.CENTER);

            detailView.add(fileView, BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treeScroll,detailView);
            gui.add(splitPane, BorderLayout.CENTER);

            JPanel simpleOutput = new JPanel(new BorderLayout(3,3));
            progressBar = new JProgressBar();
            simpleOutput.add(progressBar, BorderLayout.EAST);
            progressBar.setVisible(false);

            gui.add(simpleOutput, BorderLayout.SOUTH);
        }
        return gui;
    }

    public void showRootFile() 
    {
        // Nos aseguramos que los archivos principales sean los que se muestran
        tree.setSelectionInterval(0,0);
    }

    private TreePath findTreePath(File find) 
    {
        for (int ii=0; ii<tree.getRowCount(); ii++) 
        {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)object;
            File nodeFile = (File)node.getUserObject();
            if (nodeFile==find) 
            {
                return treePath;
            }
        }
        // No lo encontró.
        return null;
    }

    private void showErrorMessage(String errorMessage, String errorTitle) 
    {
        JOptionPane.showMessageDialog(gui,errorMessage,errorTitle,JOptionPane.ERROR_MESSAGE);
    }

    private void showThrowable(Throwable t) 
    {
        t.printStackTrace();
        JOptionPane.showMessageDialog(gui,t.toString(),t.getMessage(),JOptionPane.ERROR_MESSAGE);
        gui.repaint();
    }

    //Actualizar si se realiza un edit.
    private void setTableData(final File[] files) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                if (modeloTablaArchivo==null) 
                {
                    modeloTablaArchivo = new ModeloTablaArchivo();
                    table.setModel(modeloTablaArchivo);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                modeloTablaArchivo.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                if (!cellSizesSet) 
                {
                    Icon icon = fileSystemView.getSystemIcon(files[0]);
                    table.setRowHeight( icon.getIconHeight()+rowIconPadding );
                    setColumnWidth(0,-1);
                    setColumnWidth(3,60);
                    table.getColumnModel().getColumn(3).setMaxWidth(120);
                    setColumnWidth(4,-1);
                    setColumnWidth(5,-1);
                    setColumnWidth(6,-1);
                    setColumnWidth(7,-1);
                    setColumnWidth(8,-1);
                    setColumnWidth(9,-1);

                    cellSizesSet = true;
                }
            }
        });
    }

    private void setColumnWidth(int column, int width) 
    {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (width<0) 
        {
            JLabel label = new JLabel( (String)tableColumn.getHeaderValue() );
            Dimension preferred = label.getPreferredSize();
            width = (int)preferred.getWidth()+14;
        }
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }


    private void showChildren(final DefaultMutableTreeNode node) 
    {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>(){
            @Override
            public Void doInBackground() 
            {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) 
                {
                    File[] files = fileSystemView.getFiles(file, true); 
                    if (node.isLeaf()) 
                    {
                        for (File hijo : files) 
                        {
                            if (hijo.isDirectory()) 
                            {
                                publish(hijo);
                            }
                        }
                    }
                    setTableData(files);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) 
            {
                for (File hijo : chunks) 
                {
                    node.add(new DefaultMutableTreeNode(hijo));
                }
            }

            @Override
            protected void done() 
            {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    //Actualizamos los detalles del archivo, obteniendo propiedades basicas de los objetos de tipo File
    private void setFileDetails(File file) 
    {
        currentFile = file;
        Icon icon = fileSystemView.getSystemIcon(file);
        fileName.setIcon(icon);
        fileName.setText(fileSystemView.getSystemDisplayName(file));
        path.setText(file.getPath());
        date.setText(new Date(file.lastModified()).toString()); //casteo xq devuelve el 'tiempo unix' :(
        size.setText(file.length() + " bytes");
        readable.setSelected(file.canRead());
        writable.setSelected(file.canWrite());
        executable.setSelected(file.canExecute());
        isDirectory.setSelected(file.isDirectory());

        isFile.setSelected(file.isFile());

        JFrame f = (JFrame)gui.getTopLevelAncestor();
        if (f!=null) 
        {
            f.setTitle(APP_TITLE +" :: " +fileSystemView.getSystemDisplayName(file) );
        }
        gui.repaint();
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                try 
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {}
                JFrame f = new JFrame(APP_TITLE);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ManejadorArchivos manejador = new ManejadorArchivos();
                f.setContentPane(manejador.getGui());

                try 
                {
                    URL urlBig = manejador.getClass().getResource("fb-icon-32x32.png");
                    URL urlSmall = manejador.getClass().getResource("fb-icon-16x16.png");
                    ArrayList<Image> images = new ArrayList<Image>();
                    images.add( ImageIO.read(urlBig) );
                    images.add( ImageIO.read(urlSmall) );
                    f.setIconImages(images);
                } catch(Exception e) {}

                f.pack();
                f.setLocationByPlatform(true);
                f.setMinimumSize(f.getSize());
                f.setVisible(true);

                manejador.showRootFile();
            }
        });
    }
}