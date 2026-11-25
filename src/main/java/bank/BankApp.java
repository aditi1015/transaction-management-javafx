package bank;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankApp extends Application {

    private final Account account = new Account("ACC1001", "Sample User", 1000.00);
    private final ObservableList<String> historyItems = FXCollections.observableArrayList();
    private final Label balanceLabel = new Label();
    private final Label statusLabel = new Label();
    private final TextField amountField = new TextField();

    @Override
    public void start(Stage stage) {
        // ---------- Header ----------
        Label title = new Label("Bank Transaction System");
        title.getStyleClass().add("title");
        Label info = new Label(account.getHolderName() + "  •  " + account.getAccountNumber());
        info.getStyleClass().add("subtitle");
        VBox header = new VBox(4, title, info);
        header.getStyleClass().add("header");

        // ---------- Balance card ----------
        Label balanceCaption = new Label("CURRENT BALANCE");
        balanceCaption.getStyleClass().add("caption");
        balanceLabel.getStyleClass().add("balance");
        VBox balanceCard = new VBox(6, balanceCaption, balanceLabel);
        balanceCard.getStyleClass().add("card");

        // ---------- Actions card ----------
        Label actionCaption = new Label("NEW TRANSACTION");
        actionCaption.getStyleClass().add("caption");

        amountField.setPromptText("Enter amount (e.g. 500)");
        amountField.getStyleClass().add("amount-field");

        Button depositBtn = new Button("Deposit");
        depositBtn.getStyleClass().addAll("btn", "btn-deposit");
        Button withdrawBtn = new Button("Withdraw");
        withdrawBtn.getStyleClass().addAll("btn", "btn-withdraw");
        depositBtn.setMaxWidth(Double.MAX_VALUE);
        withdrawBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(depositBtn, Priority.ALWAYS);
        HBox.setHgrow(withdrawBtn, Priority.ALWAYS);
        depositBtn.setOnAction(e -> handle(true));
        withdrawBtn.setOnAction(e -> handle(false));

        HBox buttons = new HBox(10, depositBtn, withdrawBtn);

        statusLabel.getStyleClass().add("status");
        statusLabel.setManaged(false);
        statusLabel.setVisible(false);

        VBox actionCard = new VBox(10, actionCaption, amountField, buttons, statusLabel);
        actionCard.getStyleClass().add("card");

        // ---------- History card ----------
        Label historyCaption = new Label("TRANSACTION HISTORY");
        historyCaption.getStyleClass().add("caption");

        ListView<String> historyView = new ListView<>(historyItems);
        historyView.setPlaceholder(new Label("No transactions yet"));
        historyView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("tx-deposit", "tx-withdraw");
                if (empty || item == null) {
                    setText(null);
                    return;
                }
                setText(item);
                if (item.startsWith("Deposited")) {
                    getStyleClass().add("tx-deposit");
                } else if (item.startsWith("Withdrew")) {
                    getStyleClass().add("tx-withdraw");
                }
            }
        });
        VBox.setVgrow(historyView, Priority.ALWAYS);

        VBox historyCard = new VBox(10, historyCaption, historyView);
        historyCard.getStyleClass().add("card");
        VBox.setVgrow(historyCard, Priority.ALWAYS);

        // ---------- Assemble ----------
        VBox body = new VBox(16, balanceCard, actionCard, historyCard);
        body.setPadding(new Insets(20));
        VBox.setVgrow(body, Priority.ALWAYS);

        VBox root = new VBox(header, body);
        root.getStyleClass().add("root-pane");

        Scene scene = new Scene(root, 460, 680);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        refresh();

        stage.setTitle("Transaction Management");
        stage.setMinWidth(400);
        stage.setMinHeight(560);
        stage.setScene(scene);
        stage.show();
    }

    private void handle(boolean isDeposit) {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (isDeposit) {
                account.deposit(amount);
                showStatus(String.format("Deposited %.2f successfully", amount), true);
            } else {
                account.withdraw(amount);
                showStatus(String.format("Withdrew %.2f successfully", amount), true);
            }
            amountField.clear();
            refresh();
        } catch (NumberFormatException ex) {
            showStatus("Please enter a valid number.", false);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            showStatus(ex.getMessage(), false);
        }
    }

    private void refresh() {
        balanceLabel.setText(String.format("%,.2f", account.getBalance()));
        List<String> newestFirst = new ArrayList<>(account.getHistory());
        Collections.reverse(newestFirst);
        historyItems.setAll(newestFirst);
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().removeAll("status-ok", "status-error");
        statusLabel.getStyleClass().add(success ? "status-ok" : "status-error");
        statusLabel.setManaged(true);
        statusLabel.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}