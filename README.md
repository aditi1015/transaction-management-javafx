# Transaction Management GUI

A sample banking transaction system with a graphical interface, built in Java using JavaFX.


## Features
- Deposit and withdraw money from an account
- Live balance display
- Transaction history, newest first, color-coded (green for deposits, red for withdrawals)
- Input validation with inline error messages (invalid numbers, negative amounts, insufficient funds)

## Tech Stack
- Java 17
- JavaFX 21
- Maven
- CSS for UI styling

## Project Structure
```
src/main/
├── java/bank/
│   ├── Account.java    # Account logic (deposit, withdraw, history)
│   └── BankApp.java    # JavaFX user interface
└── resources/
    └── style.css       # UI styling
```

