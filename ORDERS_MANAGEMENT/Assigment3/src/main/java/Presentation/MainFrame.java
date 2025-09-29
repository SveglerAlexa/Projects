package Presentation;

import Presentation.Controller.BillController;
import Presentation.Controller.ClientContoller;
import Presentation.Controller.OrderController;
import Presentation.Controller.ProductController;
import Presentation.View.BillView;
import Presentation.View.ClientView;
import Presentation.View.OrderView;
import Presentation.View.ProductView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static java.awt.AWTEventMulticaster.add;

/**
 * The MainFrame class serves as the main application window for the management system.
 * It initializes all the view panels and controllers for clients, products, orders, and bills,
 * and provides navigation between these views using a CardLayout.
 * This frame uses a split-pane layout where the left panel displays context-specific controls
 * and the main panel displays the selected view.</p>
 */

public class MainFrame extends JFrame {

    private JPanel buttonsPanel;
    private JPanel leftPanel;
    ///panoul care contine paginile
    private JPanel cardsPanel;
    ///layout-ul care permite comutarea intre pagini
    private CardLayout cardLayout;


    ///cheia unica pentru fiecare pagina
    public static final String CLIENT_VIEW = "ClientView";
    public static final String PRODUCT_VIEW = "ProductView";
    public static final String ORDER_VIEW = "OrderView";
    public static final String BILL_VIEW = "BillView";

    private ClientView clientView;
    private ProductView productView;
    private OrderView orderView;
    private BillView billView;

    public MainFrame() throws SQLException {

        setTitle("Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        clientView=new ClientView();
        productView = new ProductView();
        orderView = new OrderView();
        billView=new BillView();

        BillController billController=new BillController(billView);
        OrderController orderController = new OrderController(orderView,billController);
        ClientContoller clientController = new ClientContoller(clientView,orderController);
        ProductController productController = new ProductController(productView,orderController);
        orderController.setProductController(productController);




        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        cardsPanel.add(clientView, CLIENT_VIEW);
        cardsPanel.add(productView, PRODUCT_VIEW);
        cardsPanel.add(orderView, ORDER_VIEW);
        cardsPanel.add(billView,BILL_VIEW);

        ///panoul cu butoane de navigare
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton btnClient = new JButton("Client");
        JButton btnProduct = new JButton("Product");
        JButton btnOrder = new JButton("Order");
        JButton btnBill = new JButton("Bill");

        buttonsPanel.add(btnClient);
        buttonsPanel.add(btnProduct);
        buttonsPanel.add(btnOrder);
        buttonsPanel.add(btnBill);

        add(buttonsPanel, BorderLayout.NORTH);

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(200, 0));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, cardsPanel);
        splitPane.setDividerLocation(600); // dimensiune pentru panoul stanga
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(0);

        add(splitPane, BorderLayout.CENTER);



        ///actiune butoane
        btnClient.addActionListener(e -> {
            cardLayout.show(cardsPanel, CLIENT_VIEW);
            setLeftButtons(clientView.getPanel());
        });

        btnProduct.addActionListener(e -> {
            cardLayout.show(cardsPanel, PRODUCT_VIEW);
            setLeftButtons(productView.getPanel());
        });

        btnOrder.addActionListener(e -> {
            cardLayout.show(cardsPanel, ORDER_VIEW);
            setLeftButtons(orderView.getPanel());
        });

        btnBill.addActionListener(e -> {
            cardLayout.show(cardsPanel, BILL_VIEW);
            setLeftButtons(null);
        });


    }

    public void showCard(String name) {
        cardLayout.show(cardsPanel, name);
    }

    /**
     * Replaces the current contents of the left panel with the given panel.
     * Used to display context-specific buttons or controls depending on the current view.
     *
     * @param buttonsPanel the panel containing buttons or controls for the current view; if null, clears the panel
     */


    public void setLeftButtons(JPanel buttonsPanel) {
        leftPanel.removeAll();
        if (buttonsPanel != null) {
            leftPanel.add(buttonsPanel, BorderLayout.CENTER);
        }
        leftPanel.revalidate();
        leftPanel.repaint();
    }


    public static void main(String[] args) throws SQLException {

        MainFrame frame = new MainFrame();
        frame.setVisible(true);

    }
}
