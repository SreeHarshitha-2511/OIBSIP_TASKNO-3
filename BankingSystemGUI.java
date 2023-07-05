import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BankingSystemGUI extends JFrame implements ActionListener {
    private JLabel balanceLabel;
    private JList<String> transactionList;
    private DefaultListModel<String> transactionListModel;

    private JButton withdrawButton;
    private JButton depositButton;
    private JButton transferButton;
    private JButton historyButton;

    private double balance;
    private List<Transaction> transactionHistory;

    public BankingSystemGUI() {
        balance = 0;
        transactionHistory = new ArrayList<>();

        setTitle("Banking System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel(new GridLayout(5, 1));

        balanceLabel = new JLabel("Balance: " + balance);
        panel.add(balanceLabel);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        transferButton = new JButton("Transfer");
        transferButton.addActionListener(this);
        panel.add(transferButton);

        historyButton = new JButton("Transaction History");
        historyButton.addActionListener(this);
        panel.add(historyButton);

        transactionListModel = new DefaultListModel<>();
        transactionList = new JList<>(transactionListModel);
        JScrollPane scrollPane = new JScrollPane(transactionList);
        panel.add(scrollPane);

        add(panel);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: " + balance);
    }

    private void displayTransactionHistory() {
        transactionListModel.clear();

        for (Transaction transaction : transactionHistory) {
            transactionListModel.addElement(transaction.toString());
        }
    }

    private void performWithdrawal() {
        String amountString = JOptionPane.showInputDialog(this, "Enter the amount to withdraw:");
        try {
            double amount = Double.parseDouble(amountString);
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactionHistory.add(new Transaction("Withdrawal", amount));
                updateBalanceLabel();
                displayTransactionHistory();
                JOptionPane.showMessageDialog(this, "Withdrawal of " + amount + " successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performDeposit() {
        String amountString = JOptionPane.showInputDialog(this, "Enter the amount to deposit:");
        try {
            double amount = Double.parseDouble(amountString);
            if (amount > 0) {
                balance += amount;
                transactionHistory.add(new Transaction("Deposit", amount));
                updateBalanceLabel();
                displayTransactionHistory();
                JOptionPane.showMessageDialog(this, "Deposit of " + amount + " successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performTransfer() {
        String amountString = JOptionPane.showInputDialog(this, "Enter the amount to transfer:");
        try {
            double amount = Double.parseDouble(amountString);
            if (amount > 0 && amount <= balance) {
                String accountNumber = JOptionPane.showInputDialog(this, "Enter the recipient's account number:");
                // Perform transfer logic here
                // You can implement the logic to transfer the amount to the recipient's account
                // and update the transaction history accordingly
                // This example code assumes a successful transfer
                balance -= amount;
                transactionHistory.add(new Transaction("Transfer (To: " + accountNumber + ")", amount));
                transactionHistory.add(new Transaction("Transfer (From: " + accountNumber + ")", amount));
                updateBalanceLabel();
                displayTransactionHistory();
                JOptionPane.showMessageDialog(this, "Transfer of " + amount + " to account " + accountNumber + " successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            performWithdrawal();
        } else if (e.getSource() == depositButton) {
            performDeposit();
        } else if (e.getSource() == transferButton) {
            performTransfer();
        } else if (e.getSource() == historyButton) {
            displayTransactionHistory();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankingSystemGUI gui = new BankingSystemGUI();
            gui.setVisible(true);
        });
    }
}
