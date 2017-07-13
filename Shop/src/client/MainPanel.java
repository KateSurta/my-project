package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import facade.IShopFacade;
import facade.ShopFacade;
import model.Basket;
import model.Product;

public class MainPanel extends JFrame {
	private Long initialBasketId;
	private IShopFacade shopFacade;
	private JFrame frame;
	public JTextField qrCode;
	public JTextField nameProduct;
	public JTextField Quantity;
	public JButton addButton;
	public JPanel panel;
	public JLabel nameItem;
	public JLabel nameItem1;
	public JLabel nameItem2;
	public JLabel nameItem3;
	public JLabel paidAmount;
	public JLabel paidCost;
	public JLabel paidBusket;
	public JButton sendButton;
	public JButton deleteButton;
	public JTable jTabItem;
	Object[] headers = { "Name item", "Count", "Summa" };

	
	Object[][] data = {};

	
	public MainPanel() {
		qrCode = new JTextField(10);
		nameProduct = new JTextField(10);
		Quantity = new JTextField(2);
		addButton = new JButton("Add to cart");
		sendButton = new JButton("Pay");
		deleteButton = new JButton("Detete product");

	}

	public void initComponents() {
		shopFacade = ShopFacade.getInstance();
		frame = new JFrame("My Shop");
		frame.setSize(1200, 800);
		frame.setLayout(new GridLayout(1, 2));		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.PINK);
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.lightGray);
		Box box1 = Box.createVerticalBox();
		nameItem = new JLabel("Enter the QR-Code: ");
		box1.add(nameItem);
		box1.add(Box.createVerticalStrut(10));
		box1.add(qrCode);
		nameItem1 = new JLabel("Enter the name of product: ");
		box1.add(nameItem1);
		box1.add(Box.createVerticalStrut(10));
		box1.add(nameProduct);

		qrCode.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Double cost = shopFacade.getTotalCost(initialBasketId);
				System.out.println(cost);
			}
		});

		box1.add(Box.createVerticalStrut(10));
		nameItem2 = new JLabel("Enter the quantity product:");
		box1.add(nameItem2);
		box1.add(Box.createVerticalStrut(10));
		box1.add(Quantity);
		box1.add(Box.createVerticalStrut(10));
		box1.add(addButton);

		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Product product = null;
				Integer quantity = checkQuantity();
				if (checkInputData()) {

					if (qrCode.getText() != null && !"".equals(qrCode.getText().trim())) {
						product = shopFacade.getProductByCode(qrCode.getText());
					} else {
						product = shopFacade.getProductByName(nameProduct.getText());
					}
					if (null != product && null != quantity) {
						Double previousCost = shopFacade.getTotalCost(initialBasketId);
						shopFacade.addProduct(product.getId(), initialBasketId, quantity);
						Double cost = shopFacade.getTotalCost(initialBasketId);
						DefaultTableModel model = (DefaultTableModel) jTabItem.getModel();
						model.addRow(new Object[] { product.getName(), quantity, cost - previousCost });
						paidAmount.setText(String.valueOf(cost));
					}
				}
			}

			private boolean checkInputData() {
				StringBuilder errorMessage = new StringBuilder();
				if (qrCode.getText() == null || "".equals(qrCode.getText().trim())) {
					if (nameProduct.getText() == null || "".equals(nameProduct.getText().trim())) {
						errorMessage.append("Product name or QR code should be specified").append("\n");
					}
				} else if (nameProduct.getText() != null && !"".equals(nameProduct.getText().trim())) {
					errorMessage
							.append("QR code and Product name were specified both. Please specify QR code or Product name")
							.append("\n");
				}

				if (errorMessage.length() != 0) {
					JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Form is incorrect!",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
				return true;
			}

			 private Integer checkQuantity() {
				    if (Quantity.getText() != null) {
				     if ("".equals(Quantity.getText().trim())) {
				      return 1;
				     }
				     try {
				      return Integer.valueOf(Quantity.getText());
				     } catch (NumberFormatException e) {
				      JOptionPane.showMessageDialog(frame, "Quantity should be a number", "Form is incorrect!",
				        JOptionPane.ERROR_MESSAGE);
				     }
				    } 
				    return null;
				   }

		});

		box1.add(Box.createVerticalStrut(20));
		panel1.add(box1);
		Box box = Box.createVerticalBox();
		nameItem3 = new JLabel("Name and amount the product: ");
		box.add(nameItem3);
		box.add(Box.createVerticalStrut(10));
		DefaultTableModel model = new DefaultTableModel(data, headers);
		jTabItem = new JTable(model);
		box.add(new JScrollPane(jTabItem));
		box.add(Box.createVerticalStrut(10));
		paidCost = new JLabel("Total cost is: ");
		box.add(paidCost);
		paidAmount = new JLabel("");
		paidAmount.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		box.add(paidAmount);
		box.add(Box.createVerticalStrut(20));
		box.add(deleteButton);

		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DefaultTableModel model = (DefaultTableModel) jTabItem.getModel();
				int numRows = jTabItem.getSelectedRows().length;
				for (int i = 0; i < numRows; i++) {
					int selectedRow = jTabItem.getSelectedRow();
					String productName = model.getValueAt(selectedRow, 0).toString();
					Integer quantity = (Integer) model.getValueAt(selectedRow, 1);
					model.removeRow(selectedRow);
					Product product = shopFacade.getProductByName(productName);
					shopFacade.deleteProduct(product.getId(), initialBasketId, quantity);
				}
				Double cost = shopFacade.getTotalCost(initialBasketId);
				paidAmount.setText(String.valueOf(cost));

			}
		});

		box.add(Box.createVerticalStrut(50));
		paidBusket = new JLabel("Please, click the button 'Pay' if we get ready to pay your cart");
		box.add(paidBusket);
		box.add(Box.createVerticalStrut(20));
		box.add(sendButton);
		sendButton.setPreferredSize(new Dimension(60, 40));

		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(frame, "Payment is completed. Thanks for purchase!", "Send to pay",
						JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel) jTabItem.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				initialBasketId++;
				shopFacade.createBasket(initialBasketId);
				Quantity.setText("");
				qrCode.setText("");
				nameProduct.setText("");
				paidAmount.setText("");
			}

		});

		box.add(Box.createVerticalStrut(30));
		panel2.add(box);
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.EAST);

		class MyRunnable implements Runnable {
			public void run() {
				frame.setVisible(true);
				Basket basket = shopFacade.getLastBasket();
				
				if (basket == null) {
					initialBasketId = 1L;
					}
					else {
						
					
				if (basket.getTotalCost() == 0){
				initialBasketId = basket.getId();
				}
				else {
				initialBasketId = basket.getId() + 1L;
				}
				
					}
				shopFacade.createBasket(initialBasketId);
				}
		}
		MyRunnable mr = new MyRunnable();
		SwingUtilities.invokeLater(mr);
		frame.addWindowListener(new CloseHandler());

	}

}

class CloseHandler extends WindowAdapter {
	public void WindowClosing(WindowEvent e) {
		System.exit(0);
	}

}
